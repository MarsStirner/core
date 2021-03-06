package ru.korus.tmis.laboratory.altey.business

import ru.korus.tmis.core.database._
import common._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.laboratory.altey.accept2.{AnalysisResult => AResult1}
import ru.korus.tmis.laboratory.altey.{ws => lab}

import grizzled.slf4j.Logging
import java.lang.String
import javax.ejb.{Stateless, EJB}
import javax.interceptor.Interceptors
import javax.xml.datatype.{DatatypeFactory, XMLGregorianCalendar}

import scala.collection.JavaConversions._
import java.text.SimpleDateFormat
import javax.xml.ws.BindingProvider
import scala.Some
import ru.korus.tmis.scala.util.{Types, General, I18nable, ConfigManager}
import Types.{JList, JSet}
import java.util.{ArrayList, GregorianCalendar, Collections}
import javax.xml.ws.handler.{MessageContext, Handler}
import ru.korus.tmis.core.logging.slf4j.soap.LoggingHandler
import ru.korus.tmis.util.{CompileTimeConfigManager}
import javax.xml.namespace.QName
import java.net.{PasswordAuthentication, Authenticator}
import java.util.{LinkedList, Date}
import ru.korus.tmis.laboratory.altey.request._
import ru.korus.tmis.laboratory.altey.accept.AnalysisResult

import javax.xml.rpc.Stub

import org.apache.axis.client.{Stub => AxisStub}

import scala.collection.mutable
import ru.korus.tmis.scala.util.{General, I18nable, ConfigManager}
import scala.Some
import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import scala.language.reflectiveCalls

@Stateless
class AlteyLaboratoryBean extends AlteyBusinessBeanLocal with Logging with I18nable {
  @EJB
  var dbActionBean: DbActionBeanLocal = _

  @EJB
  var dbActionTypeBean: DbActionTypeBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbCustomQuery: DbCustomQueryLocal = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  var dbManager: DbManagerBeanLocal = _


  def getLabWs(): lab.IntegrationCorusPortType = {
    import lab._

    try {
      // Вызываем удаленный веб-сервис

      import ConfigManager._

      if (Laboratory.User != null && Laboratory.Password != null) {
        Authenticator.setDefault(new Authenticator() {
          override def getPasswordAuthentication(): PasswordAuthentication = {
            info("Authentication requested")
            return new PasswordAuthentication(Laboratory.User, Laboratory.Password.toCharArray);
          }
        });
      }

      import CompileTimeConfigManager.Laboratory._

      val service = Option(Laboratory.WSDLUrl).map {
        it =>
          info("LIS WSDL URL specified: overriding standard url to '" + it.toString + "'")
          new IntegrationCorus(it, new QName(Namespace, ServiceName))
      }.getOrElse {
        warn("LIS WSDL URL not specified: using local WSDL")
        val url = this.getClass.getResource("/labisws.wsdl")
        new IntegrationCorus(url, new QName(Namespace, ServiceName))
      }
      val ws = service.getIntegrationCorusSoap

      ws match {
        case bp: BindingProvider => {

          Option(Laboratory.ServiceUrl).foreach {
            url =>
              bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, url.toString)
          }

          Option(Laboratory.User).foreach {
            value =>
              bp.getRequestContext().put(BindingProvider.USERNAME_PROPERTY, value)
          }

          Option(Laboratory.Password).foreach {
            value =>
              bp.getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, value)
          }


          val binding = bp.getBinding()

          val handlerChain = Option(binding.getHandlerChain).getOrElse(new LinkedList[Handler[_ <: MessageContext]]())

          if (!handlerChain.contains(LoggingHandler)) handlerChain.add(LoggingHandler)

          binding.setHandlerChain(handlerChain)
          ws
        }
        case _ => error("Cannot set up port as binding provider"); ws
      }

    } catch {
      case e: Throwable => {
        error("Error while creating LIS service endpoint", e)
        throw e
      }
    }
  }



  // get bio from Tissue (LIS)
  def getBiomaterialInfo(action: Action, tissues: JSet[Tissue]): BiomaterialInfo = {
    action.getTissue.headOption match {
      case Some(t) => {
        info("Biomaterial:BarCode=" + t.getBarcode)
        info("Biomaterial:SamplingDate=" + t.getDate)
        val tt = t.getTissueType
        info("Biomaterial:Code=" + tt.getCode)
        info("Biomaterial:Name=" + tt.getName)
        BiomaterialInfo(
          Option(tt.getCode),
          Option(tt.getName),
          Option(t.getBarcode),
          None,
          None, // no takenTissue.id for LIS
          Option(t.getDate),
          Option("")
        )
      }
      case None => throw new CoreException(i18n("error.tissueForActionNotFound", action.getId))
    }
  }


  // get bio from TakenTissue (LIS2)
  def getBiomaterialInfo(action: Action, takenTissue: TakenTissue): BiomaterialInfo = {

    Option(action.getTakenTissue) match {
      case Some(t) => {
        info("Biomaterial:BarCode=" + t.getId)
        info("Biomaterial:SamplingDate=" + t.getDatetimeTaken)
        val tt = t.getType
        info("Biomaterial:Code=" + tt.getCode)
        info("Biomaterial:Name=" + tt.getName)
        BiomaterialInfo(
          Option(tt.getCode),
          Option(tt.getName),
          Option(t.getBarcode.toString), // barcode for LIS2 is taken from id
          Option(t.getPeriod),
          None, // no takenTissue.id for LIS
          Option(t.getDatetimeTaken),
          Option(t.getNote)
        )
      }
      case None => throw new CoreException(i18n("error.tissueForActionNotFound", action.getId))
    }
  }

  def getOrderInfo(a: Action, at: ActionType): OrderInfo = {
    // Код исследования
    val code = at.getCode
    info("OrderInfo:Code=" + code)
    // Наименование исследования
    val name = at.getName
    info("OrderInfo:Name=" + name)

    // Флаг срочности
    val priority = if (a.getIsUrgent) OrderInfo.OrderPriority.Urgent else OrderInfo.OrderPriority.Normal
    info("OrderInfo:Urgent=" + priority)

    // Показатели
    val apts = dbActionTypeBean.getActionTypePropertiesById(at.getId.intValue)
    val res = apts.find(apt => {
      apt.getTest != null
    })
    res.isEmpty match {
      // Если для данного исследования не определены показатели из rbTest, то не нужно отправлять анализ в ЛИС
      case true => throw new CoreException(i18n("error.noTestsForAction", a.getId))
      case false => {}
    }

    val aptsSet = apts.toSet // для правильной работы JavaConversion
    // Получаем map из APT в AP
    val apsMap = a.getActionPropertiesByTypes(aptsSet)
    // Фильтруем map чтобы найти показатели/методы

    val indicators = apsMap.collect {
      case (apt, ap) if (apt.getTest != null && (apt.getIsAssignable == false || ap.getIsAssigned == true)) =>
        info("ap.id=" + ap.getId + " apt.name=" + apt.getName +
          " code=" + apt.getTest.getCode + " name=" + apt.getTest.getName)

        val code = apt.getTest.getCode
        val name = apt.getTest.getName
        IndicatorMetodic(Option(name), Option(code))
    }

    OrderInfo(Option(code), Option(name), Option(priority), indicators.toList)
  }

  def getPatientInfo(patient: Patient): PatientInfo = {
    val misId = patient.getId.intValue
    info("Patient:Code=" + patient.getId)
    // LastName (string) -- фамилия
    val lastName = patient.getLastName
    info("Patient:Family=" + patient.getLastName)
    // FirstName (string) -- имя
    val firstName = patient.getFirstName
    info("Patient:FirstName=" + patient.getFirstName)
    // MiddleName (string) -- отчество
    val patrName = patient.getPatrName
    info("Patient:MiddleName=" + patient.getPatrName)
    // BirthDate (datetime) -- дата рождения
    val birthDate = patient.getBirthDate
    info("Patient:BirthDate=" + patient.getBirthDate)
    // Sex (enum) -- пол (мужской/женский/не определен)
    val sex = Sex.valueOf(patient.getSex)
    info("Patient:Sex=" + patient.getSex)

    PatientInfo(misId,
      Option(lastName),
      Option(firstName),
      Option(patrName),
      Option(birthDate),
      sex)
  }

  def getDiagnosticRequestInfo(a: Action): DiagnosticRequestInfo = {
    // Id (long) -- уникальный идентификатор направления в МИС (Action.id)
    val id = (a.getId.intValue)
    info("Request:Id=" + a.getId.intValue)

    // номер истории болезни eventid
    val orderCaseId = "" + a.getEvent.getExternalId
    info("Request:OrderCaseId=" + orderCaseId)

    // код финансирования
    val orderFinanceId = getFinanceId(a.getEvent)
    info("Request:OrderFinanceId=" + orderFinanceId)

    // CreateDate (datetime) -- дата создания направления врачом (Action.createDatetime)
    val date = a.getCreateDatetime
    info("Request:CreateDate=" + a.getCreateDatetime)

    // PregnancyDurationWeeks (int) -- срок беременности пациентки (в неделях)
    val pregMin = (a.getEvent.getPregnancyWeek * 7)
    val pregMax = (a.getEvent.getPregnancyWeek * 7)
    info("Request:PregnancyDurationWeeks=" + a.getEvent.getPregnancyWeek)

    // Diagnosis
    val diagnosis = getDiagnosis(a.getEvent)
    val (diagCode, diagName) = diagnosis match {
      case null => {
        warn("Request: no diagnosis found for event #" + a.getEvent.getId)
        ("", "")
      }
      case _ => {
        // Diagnosis (string) -- диагноз (текст из МКБ) (последний из обоснования; если нет, то из первичного осмотра)
        info("Request:Diagnosis=" + diagnosis)
        // МКБ (string) -- диагноз (код из МКБ) (последний из обоснования; если нет, то из первичного осмотра)
        info("Request:mkbCode=" + diagnosis._1)
        diagnosis
      }
    }

    // Comment (string) (необязательно) – произвольный текстовый комментарий к направлению
    val comment = (a.getNote)
    info("Request:Comment" + comment)

    val department = getOrgStructureByEvent(a.getEvent)
    // DepartmentName (string) -- название подразделения - отделение
    // DepartmentCode (string) -- уникальный код подразделения (отделения)
    val (depName, depCode) = department match {
      case null => {
        info("Request:DepartmentName=<empty>")
        info("Request:DepartmentCode=<empty>")
        ("", "")
      }
      case _ => {
        info("Request:DepartmentName=" + department.getName)
        //todo изменено 03.10 hack
        info("Request:DepartmentCode=" + department.getId)
        (department.getName, "" + department.getId)
      }
    }

    val (drLastName, drFirstName, drMiddleName, drCode) = a.getAssigner match {
      case null => {
        ("", "", "", null)
      }
      case _ => {
        (a.getAssigner.getLastName, a.getAssigner.getFirstName, a.getAssigner.getPatrName, a.getAssigner.getId)
      }
    }
    // DoctorLastName, DoctorFirstName, DoctorMiddleName (string) -- ФИО врача
    info("Request.DoctorLastName=" + drLastName)
    info("Request.DoctorFirstName=" + drFirstName)
    info("Request.DoctorMiddleName=" + drMiddleName)
    // DoctorCode (string) -- уникальный код или идентификатор врача
    info("Request.DoctorCode=" + drCode)

    import General.NumberImplicits._

    DiagnosticRequestInfo(
      id,
      Option(orderCaseId),
      Option(orderFinanceId),
      Option(date),
      Option(pregMin),
      Option(pregMax),
      Option(diagCode),
      Option(diagName),
      Option(comment),
      Option(depName),
      Option(depCode),
      Option(drLastName),
      Option(drFirstName),
      Option(drMiddleName),
      drCode
    )
  }

  /**
   * Получить информацио о источнике финансирования
   * @param e событие карточка пациента
   * @return код финансирования
   */
  def getFinanceId(e: Event): Int = {
    val id = dbCustomQuery.getFinanceId(e)
    id.intValue()
  }

  def getXmlStringForDate(date: java.util.Date): String = {
    val c = new SimpleDateFormat("dd.MM.yyyy")
    c.format(date)
  }

  def getXmlGregCal(date: java.util.Date): XMLGregorianCalendar = {
    val c = new GregorianCalendar
    c.setTime(date)
    val gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(c)
    gc
  }

  def getOrgStructureByEvent(e: Event): OrgStructure = {
    val hospitalBeds = dbCustomQuery.getHospitalBedsByEvents(
      Collections.singletonList(e))
    hospitalBeds.get(e) match {
      case null => {
        warn("Hospital bed not found for " + e)
      }
      case hospitalBed: ActionProperty => {
        val apvs = dbActionProperty.getActionPropertyValue(hospitalBed)
        apvs.foreach(
          (apv) => {
            if (apv.getValue.isInstanceOf[OrgStructureHospitalBed]) {
              val bed = apv.getValue.asInstanceOf[OrgStructureHospitalBed]
              return bed.getMasterDepartment
            }
          })
      }
    }

    null
  }

  /**
   * Получить диагноз по МКБ
   *
   * Возвращает пару (код, текстовое описание диагноза) по МКБ или null
   */
  def getDiagnosis(e: Event): (String, String) = {
    // Получаем тип свойства действия для диагноза
    val diagnosisAPT = asScalaSet(dbActionPropertyType.getDiagnosisAPT)

    // Выбираем диагнозы
    val diagMap = dbCustomQuery.getDiagnosisSubstantiationByEvents(Collections.singletonList(e))
    val a = diagMap.get(e) match {
      case null => {
        warn("Diagnosis not found")
        return null
      }
      case d: Action => d
    }

    // Получаем список пар (APT, AP)
    val aps = a.getActionPropertiesByTypes(diagnosisAPT)
    // Получаем список APValue
    val apvals = aps.foldLeft(scala.collection.immutable.List[APValue]())((ps, pair) => {
      val ap = pair._2
      val apvs = dbActionProperty.getActionPropertyValue(ap).toList
      apvs ::: ps
    })

    apvals.size match {
      case 0 => null
      case x: Int => {
        val pair = apvals.get(0)
        var res = pair.getValueAsString.replaceAll("<(.)+?>", "")
        res = res.replaceAll("<(\n)+?>", "")
        res = res.replaceAll("\\&.*?\\;", "")
        if (res.length > 150) {
          res = res.take(147) + "..."
        }
        ("", res)
      }
    }

    /*
        val mkbs = apvals.filter(apv => apv.isInstanceOf[APValueMKB])
        mkbs.size match {
          case 0 => return null
          case x: Int => {
            val mkbValue = mkbs.get(0).asInstanceOf[APValueMKB]
            val mkb = mkbValue.getMkb()
            return (mkb.getDiagID, mkb.getDiagName)
          }
        }
    */
  }

  def setAnalysisResults(a: Action, results: List[AnalysisResult], finished: Boolean, biomaterialDefects: String) = {
    // Сохраняем результаты анализов
    val entities = scala.collection.mutable.Buffer[AnyRef](a)
    import General.typedEquality

    results.foreach {
      r =>
        info("Applying analysis result " + r)
        // Находим ActionProperty для данного показателя

        val aps_byCode = a.getActionProperties.filter {
          ap =>
            val check: Option[Boolean] = for (
              apt <- Option(ap.getType);
              tst <- Option(apt.getTest);
              code <- Option(tst.getCode);
              rcode <- r.code
            ) yield code =!= rcode

            check.getOrElse(false)
        }

        info("Action properties found by code: " + aps_byCode)

        val aps_byName = a.getActionProperties.filter {
          ap =>
            val check: Option[Boolean] = for (
              apt <- Option(ap.getType);
              tst <- Option(apt.getTest);
              name <- Option(tst.getName);
              rname <- r.name
            ) yield name =!= rname

            check.getOrElse(false)
        }

        info("Action properties found by name: " + aps_byName)

        val aps = (aps_byCode union aps_byName).distinct

        info("Total action properties found: " + aps)

        aps.foreach(ap => {
          // Записываем значение

          info("Writing action property #" + ap.getId + " to " + r.value)

          val apv = r.value.get0.map {
            txt => dbActionProperty.setActionPropertyValue(ap, txt, 0)
          }
          apv.foreach {
            entities += _
          }

          info("Writing norm to " + r.norm)

          // Устанавливаем норму
          r.norm.foreach {
            ap.setNorm(_)
          }



          // Находим единицу изменения
          val unit = for (code <- r.unitCode; un <- Option(dbCustomQuery.getUnitByCode(code))) yield un
          info("Writing unit to " + (unit map {
            _.getCode
          }))

          unit.foreach {
            ap.setUnit(_)
          }

          entities += ap
        })

        // Изображения
        r.value.get1.getOrElse(Nil) match {
          case List(image) => {
            // Получаем actionProperty по названию
            val aps = a.getActionProperties.filter(ap => {
              ap.getType match {
                case null => false
                case at: ActionPropertyType => at.getName.startsWith(i18n("db.actionProperty.imageName"))
              }
            })
            // Записываем значение
            aps.foreach(ap => {
              // TODO: сохранять также imageString
              // Передаем base64 строку, которая в APValueImage сохраняется в byte[]
              val apv = image.imageData.map {
                it => dbActionProperty.setActionPropertyValue(ap, it, 0)
              }
              apv.foreach {
                entities += _
              }
            })

          }
          case _ => {}
        }

        val microOrganismResults = r.value.get2.getOrElse(Nil)
        microOrganismResults.foreach(r => {
          val organismName = r.organismName
          val organismConcentration = r.organismConcentration
          val apsFound = aps.filter(ap => {
            val check = for (apt <- Option(ap.getType); tst <- Option(apt.getTest); oname <- organismName)
            yield oname =!= tst.getName
            check.getOrElse(false)
          })
          apsFound.foreach(ap => {
            val apv = organismConcentration.map {
              txt => dbActionProperty.setActionPropertyValue(ap, txt, 0)
            }
            apv.foreach {
              entities += _
            }
          })
        })


        val sensitivityResults = r.value.get3.getOrElse(Nil)
        sensitivityResults.foreach(r => {
          val antibioticName = r.name
          val apsFound = aps.filter(ap => {
            val check = for (apt <- Option(ap.getType); tst <- Option(apt.getTest); aname <- antibioticName)
            yield aname =!= tst.getName
            check.getOrElse(false)
          })

          // Записываем значение
          apsFound.foreach(ap => {
            val apv = r.mic match {
              case None => dbActionProperty.setActionPropertyValue(ap, r.value.getOrElse(""), 0)
              case Some(mic) => dbActionProperty.setActionPropertyValue(ap, r.value.getOrElse("") + "/" + mic, 0)
            }
            entities += apv
          })
        })
    }

    // Сохраняем дефекты биоматериала в комментарий к действию
    a.setNote(biomaterialDefects)
    // Изменяем статус действия на "Закончено"
    if (finished) a.setStatus(ActionStatus.FINISHED.getCode)

    // Сохраняем изменившиеся сущности в БД
    dbManager.mergeAll(entities)

    if (finished) 0 else 1
  }

  def setLisAnalysisResults(requestId: Int,
                            finished: Boolean,
                            results: JList[AResult1],
                            biomaterialDefects: String) = {
    // Получаем действие направления
    info("SetAnalysisResults requested with id = " + requestId)

    import General.cast_implicits

    val a = dbActionBean.getActionById(requestId)
    val res: mutable.Buffer[AnalysisResult] = results map {
      _.castTo[AnalysisResult]
    }

    setAnalysisResults(a, res.toList, finished, biomaterialDefects)
  }



  def checkNull[T](o: T, message: String): T = {
    o match {
      case null => throw new CoreException(message)
      case _ => o
    }
  }



  def getAnalysisRequest(actionId: Int) = {
    val a = dbActionBean.getActionById(actionId)

    val at = a.getActionType
    if (at.getId == -1) throw new CoreException(i18n("error.noTypeForAction", a.getId))

    if (at.getTypeClass != Integer.valueOf(i18n("db.action.diagnosticClass")).intValue) {
      throw new CoreException(i18n("error.invalidActionClass", a.getId, at.getTypeClass))
    }

    info("sendLisAnalysisRequest actionId=" + actionId)

    // Patient section
    val event = checkNull(a.getEvent, i18n("error.noEventForAction", a.getId))
    val patient = checkNull(event.getPatient, i18n("error.noPatientForEvent", event.getId))

    val patientInfo = getPatientInfo(patient)

    // Request section
    val requestInfo = getDiagnosticRequestInfo(a)

    // Biomaterial section
    val biomaterialInfo = getBiomaterialInfo(a, a.getTakenTissue)

    // Order section
    val orderInfo = getOrderInfo(a, at)

    (patientInfo, requestInfo, biomaterialInfo, orderInfo)
  }

  def sendLisAnalysisRequest(actionId: Int) = {
    val (patientInfo, requestInfo, biomaterialInfo, orderInfo) = getAnalysisRequest(actionId)

    info {
      """Lis data:
        |  %s
        |  %s
        |  %s
        |  %s
      """.stripMargin format(
        patientInfo.toString,
        requestInfo.toString,
        biomaterialInfo.toString,
        orderInfo.toString
        )
    }

    info("connecting to LIS webservice...")

    val labws = getLabWs()

    info("sending to LIS webservice...")
    try {
      labws.queryAnalysis(patientInfo, requestInfo, biomaterialInfo, orderInfo)
      info("successfully interacted with LIS webservice...")
    } catch {
      case e: Throwable => error("Error responce from LIS webservice", e)
    }
  }



}
