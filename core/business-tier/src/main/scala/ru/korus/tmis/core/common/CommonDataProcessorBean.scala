package ru.korus.tmis.core.common

import ru.korus.tmis.core.auth.{AuthStorageBeanLocal, AuthData}
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import common.{DbActionPropertyTypeBeanLocal, DbActionPropertyBeanLocal, DbManagerBeanLocal, DbActionBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.{Stateless, EJB}
import javax.enterprise.event.Event
import javax.enterprise.inject.Any
import javax.inject.Inject
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import java.util.{Calendar, Date}
import ru.korus.tmis.core.patient.DiagnosisBeanLocal
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import org.joda.time._
import scala.Predef._
import ru.korus.tmis.scala.util.StringId
import scala.Some
import ru.korus.tmis.core.entity.model.ActionStatus
import javax.persistence.{EntityManager, PersistenceContext}
import scala.List
import scala.collection.JavaConverters._
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class CommonDataProcessorBean
  extends CommonDataProcessorBeanLocal
  with Logging
  with I18nable {

  type CA = CommonAttribute

  val AWI = ConfigManager.AWI
  val APWI = ConfigManager.APWI

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var appLock: AuthStorageBeanLocal = _

  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbVersion: DbVersionBeanLocal = _

  @EJB
  var diagnosisBean: DiagnosisBeanLocal = _

  @EJB
  var dbCalendarExceptionsBean: DbCalendarExceptionsBeanLocal = _

  @EJB
  var dbLayoutAttributeValueBean: DbLayoutAttributeValueBeanLocal = _

  @EJB
  var dbActionPropertyTypeBeanLocal: DbActionPropertyTypeBeanLocal =  _

  

  //////////////////////////////////////////////////////////////////////////////

  def createActionForEventFromCommonData(eventId: Int,
                                         data: CommonData,
                                         userData: AuthData): java.util.List[Action] = {

    if (data == null) {
      throw new CoreException(ConfigManager.ErrorCodes.InvalidCommonData,
        i18n("error.invalidCommonData"))
    }

    var result = List.empty[Action]
    var entities = Set.empty[AnyRef]

    data.entity.foreach(entity => {
      try {

        // Collect all ActionProperties sent from the client
        val aps = entity.group.foldLeft(List.empty[CommonAttribute])(
          (list, group) => list ++ group.attribute)

        var multiplicity: Int = 1
        var beginDate: Date = null
        var endDate: Date = null
        var plannedEndDate: Date = null
        var finance: Int = -1
        val now = new Date()
        var assignerId: Int = -1
        var executorId: Int = -1
        //var toOrder: Boolean = false

        aps.foreach(attribute => {
          if (attribute.name == AWI.Multiplicity.toString) {
            multiplicity = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => 1
              case Some(x) => x.toInt
            }
          }
          else if (attribute.name == AWI.assessmentBeginDate.toString) {
            beginDate = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => null
              case Some(x) => ConfigManager.DateFormatter.parse(x)
            }
          }
          else if (attribute.name == AWI.assessmentEndDate.toString) {
            endDate = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => null
              case Some(x) => ConfigManager.DateFormatter.parse(x)
            }
          }
          else if (attribute.name == AWI.finance.toString) {
            finance = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => 0
              case Some(x) => x.toInt
            }
          }
          else if (attribute.name == AWI.plannedEndDate.toString) {
            plannedEndDate = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => null
              case Some(x) => ConfigManager.DateFormatter.parse(x)
            }
          }
          else if (attribute.name == AWI.assignerId.toString) {
            //ид. направившего врача
            assignerId = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => 0
              case Some(x) => x.toInt
            }
          }
          else if (attribute.name == AWI.executorId.toString) {
            //ид. исполнителя
            executorId = attribute.getPropertiesMap.get(APWI.Value.toString) match {
              case None | Some("") => 0
              case Some(x) => x.toInt
            }
          }
          /*else if (attribute.name == AWI.toOrder.toString) {
            toOrder = attribute.properties.get(APWI.Value.toString) match {
              case None | Some("") => false
              case Some(x) => x.toBoolean
            }
          }*/
        })

        var i = 0
        while (i < multiplicity) {

          val action = dbAction.createAction(eventId,
            entity.getTypeId.intValue,
            userData)

          /* ActionType.id = 139 - Осмотр врача приемного отделения (первичная госпитализация)
             ActionType.id = 112 - Поступление
             ActionType.id = 139 - Осмотр врача приемного отделения (повторная госпитализация)
           */
          val isPrimaryAction = entity.typeId.intValue == 139 || entity.typeId.intValue == 112 || entity.typeId.intValue == 2456
          if (isPrimaryAction) {
            action.setStatus(ActionStatus.FINISHED.getCode) //TODO: Материть Александра!
          }
          //plannedEndDate
          if (finance > 0) action.setFinanceId(finance)
          //выбран направивший врач вручную (https://docs.google.com/spreadsheet/ccc?key=0Au-ED6EnawLcdHo0Z3BiSkRJRVYtLUxhaG5uYkNWaGc#gid=6 (строка 57))
          if (assignerId > 0) action.setAssigner(new Staff(assignerId))
          if (executorId > 0) action.setExecutor(new Staff(executorId))
          //action.setToOrder(toOrder)
          //Если пришли значения Даты начала и дата конца, то перепишем дефолтные
          //Для первичного осмотра в качестве дефолтных значений вставим текущее время(не понравилось - убираю (WEBMIS-837: Документ создается сразу же закрытым))
          if (beginDate != null) action.setBegDate(beginDate) else if (isPrimaryAction) action.setBegDate(now)
          if (endDate != null) {
            action.setEndDate(endDate)
            action.setStatus(ActionStatus.FINISHED.getCode) //WEBMIS-880
          } //else if (isPrimaryAction) action.setEndDate(now)
          if (plannedEndDate != null) action.setPlannedEndDate(plannedEndDate)


          val actionType = dbActionType.getActionTypeById(entity.id.intValue)
          val aw = new ActionWrapper(action)

          // Collect all ActionProperties sent from the client
          val aps = entity.group.foldLeft(List.empty[CommonAttribute])(
            (list, group) => list ++ group.attribute)

          // Create sent APs
          val apList = aps.foldLeft(
            List.empty[(ActionProperty, CommonAttribute)]
          )((list, attribute) => {
            if (AWI.isSupported(attribute.name)) {
              list
            } else {
              if(attribute.typeId == null && attribute.code != null) {
                val apt: ActionPropertyType = dbActionPropertyTypeBeanLocal.getActionPropertyTypeByActionTypeIdAndTypeCode(actionType.getId, attribute.code, false)
                if (apt != null) {
                 attribute.typeId = apt.getId;
                }
              }

              val ap = if(attribute.typeId == null) null else dbActionProperty.createActionPropertyWithDate(
                action,
                attribute.typeId.intValue, //TODO attribute.typeId ??????????
                userData,
                now)
              if (ap != null) {
                new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope).set(attribute)
                (ap, attribute) :: list
              } else {
                list
              }
            }
          })

          // Create empty APs not sent from the client
          val emptyApts = actionType.getActionPropertyTypes
          emptyApts.removeAll(apList.map(_._1.getType))

          val emptyApList = new java.util.LinkedList[ActionProperty]
          emptyApts.foreach(
            apt => {
              if (!apt.getIsAssignable) {
                emptyApList.add(dbActionProperty.createActionPropertyWithDate(action,
                  apt.getId.intValue,
                  userData,
                  now))
              }
            })

          entities = entities + action
          dbManager.persistAll(List(action))

          // Save sent AP values
          val apvList = apList.foldLeft(
            List.empty[APValue]
          )((list, entry) => {
            toActionPropertyValue(entry, list)
          })

          //
          entities = (entities /: apvList)(_ + _)
          dbManager.persistAll(apvList)
          //Сохранение диагнозов в таблицу Диагностик
          //var apsForDiag = new util.LinkedList[ActionProperty]()
          //apList.foreach(f => apsForDiag.add(f._1))
          dbManager.persistAll(this.saveDiagnoses(eventId,
            apList.map(_._1).toList,
            apvList,
            userData))

          // Save empty AP values (set to default values)
          //Для FlatDictionary (FlatDirectory) нету значения по умолчанию, внутри релэйшн по значению валуе, дефолт значение решил не писать
          val emptyApvList = emptyApList.filter(p => p.getType.getTypeName.compareTo("FlatDictionary") != 0 && p.getType.getTypeName.compareTo("FlatDirectory") != 0).map(
            ap => {
              dbActionProperty.setActionPropertyValue(ap, ap.getType.getDefaultValue, 0)
            })

          entities = (entities /: emptyApvList)(_ + _)
          dbManager.persistAll(emptyApvList)

          // Set AWI values
          aps.foreach(attribute => {
            if (AWI.isSupported(attribute.name)) {
              aw.set(attribute)
            }
          })

          val updatedAction =
            dbManager.mergeAll[Action](List(action)).iterator().next()
          entities = entities - action + updatedAction

          result = updatedAction :: result

          i = i + 1
        }

      } catch {
        case e: Exception => {
          dbManager.removeAll(entities)
          throw e
        }
      }
    })

    val r = dbManager.detachAll[Action](result).toList
    /*
    r.foreach(a => {
      val values = dbActionProperty.getActionPropertiesByActionId(a.getId.intValue)
      actionEvent.fire(new CreateActionNotification(a, values))
    })
    */
    r
  }


  /**
   * Метод получения APValue из входных данных в виде [[ru.korus.tmis.core.data.CommonAttribute]]
   * @param entry Пара свойство и его представление как [[ru.korus.tmis.core.data.CommonAttribute]]
   * @param list Список значений, к которому будут добавлены новые полученные значения
   * @return Список на входе включая вновь полученные элементы
   */
  def toActionPropertyValue(entry: (ActionProperty, CommonAttribute), list: List[APValue]): List[APValue] = {
    val (ap, attribute) = entry
    (attribute.getPropertiesMap.get("valueId"), attribute.getPropertiesMap.get("value")) match {
      case (None | Some(null) | Some(""), None | Some(null) | Some("")) => {
        if (ap.getType.getTypeName.compareTo("FlatDirectory") != 0 &&
          ap.getType.getTypeName.compareTo("FlatDictionary") != 0 &&
          !ap.getType.getTypeName.equals("Table")) {
          if (ap.getType.getDefaultValue.compareTo("нет") != 0) {
            //костылик для мкб
            val apv = dbActionProperty.setActionPropertyValue(ap,
              null,
              0)
            if (apv != null)
              apv :: list
            else
              list
          } else list
        } else list
      }
      case (None | Some(null) | Some(""), Some(value)) => {
        if(ap.getType.getTypeName.equals("Date") && attribute.getPropertiesMap.get("value").getOrElse("").equals("0000-00-00 00:00:00"))
          list
        else {
        val apv = dbActionProperty.setActionPropertyValue(
          ap,
          value,
          0)
        apv :: list
        }
      }
      case (Some(valueId), _) => {
        val apv = dbActionProperty.setActionPropertyValue(
          ap,
          valueId,
          0)
        apv :: list
      }
    }
  }

  def modifyActionFromCommonData(actionId: Int,
                                 data: CommonData,
                                 userData: AuthData): java.util.List[Action] = {

    if (data == null) {
      throw new CoreException(ConfigManager.ErrorCodes.InvalidCommonData,
        i18n("error.invalidCommonData"))
    }

    var result = List[Action]()
    var entities = Set[AnyRef]()

    val oldAction = Action.clone(dbAction.getActionById(actionId))
    val lockId = appLock.acquireLock("Action",
      actionId,
      oldAction.getIdx,
      userData)
    var eventId = 0
    try {
      data.entity.filter(_.id == actionId).foreach(entity => {
        val a = dbAction.updateAction(entity.id.intValue,
          entity.version.intValue,
          userData)
        val aw = new ActionWrapper(a)

        // очистить часы
        a.resetAssignmentHours()

        val aps = entity.group.foldLeft(List.empty[CommonAttribute])(
          (list, group) => list ++ group.attribute)

        //Если пришли значения Даты начала и дата конца, то перепишем дефолтные
        var beginDate: Date = null
        var endDate: Date = null
        var plannedEndDate: Date = null
        var finance: Int = -1
        var assignerId: Int = -1
        var executorId: Int = -1
        //var toOrder: Boolean = false

        var res = aps.find(p => p.name == AWI.assessmentBeginDate.toString).getOrElse(null)
        if (res != null) {
          beginDate = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => null
            case Some(x) => ConfigManager.DateFormatter.parse(x)
          }
        }
        res = aps.find(p => p.name == AWI.assessmentEndDate.toString).getOrElse(null)
        if (res != null) {
          endDate = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => null
            case Some(x) => ConfigManager.DateFormatter.parse(x)
          }
        }
        res = aps.find(p => p.name == AWI.finance.toString).getOrElse(null)
        if (res != null) {
          finance = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => 0
            case Some(x) => x.toInt
          }
        }
        res = aps.find(p => p.name == AWI.plannedEndDate.toString).getOrElse(null)
        if (res != null) {
          plannedEndDate = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => null
            case Some(x) => ConfigManager.DateFormatter.parse(x)
          }
        }
        res = aps.find(p => p.name == AWI.assignerId.toString).getOrElse(null)
        if (res != null) {
          assignerId = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => 0
            case Some(x) => x.toInt
          }
        }
        res = aps.find(p => p.name == AWI.executorId.toString).getOrElse(null)
        if (res != null) {
          executorId = res.getPropertiesMap.get(APWI.Value.toString) match {
            case None | Some("") => 0
            case Some(x) => x.toInt
          }
        }
        /*res = aps.find(p => p.name == AWI.toOrder.toString).getOrElse(null)
        if (res != null) {
          toOrder = res.properties.get(APWI.Value.toString) match {
            case None | Some("") => false
            case Some(x) => x.toBoolean
          }
        }*/

        if (beginDate != null) a.setBegDate(beginDate)

        if (finance > 0) a.setFinanceId(finance)
        //a.setToOrder(toOrder)
        if (plannedEndDate != null) a.setPlannedEndDate(plannedEndDate)
        if (assignerId > 0) a.setAssigner(new Staff(assignerId))
        if (executorId > 0) a.setExecutor(new Staff(executorId))

        result = a :: result
        entities = entities + a

        aps.foreach(attribute => {
          val id = attribute.id

          if (AWI.isSupported(attribute.name)) {
            aw.set(attribute)
          } else {
            (attribute.getPropertiesMap.get("valueId"), attribute.getPropertiesMap.get("value")) match {

              case (None | Some(null) | Some(""), None | Some("") | Some(null)) => {
                val ap = dbActionProperty.getActionPropertyById(
                  id.intValue)
                new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope).set(attribute)

                //Удаление значений свойств, если они присутствуют
                dbManager.removeAll(dbActionProperty.getActionPropertyValue(ap))

                entities = entities + ap
              }

              case (None | Some(null) | Some(""), Some(value)) => {
                val ap = dbActionProperty.updateActionProperty(
                  id.intValue,
                  attribute.version.intValue,
                  userData)
                var apv: APValue = null

                if (ap.getType.getTypeName.compareTo("MKB") == 0 && (value == null || value.isEmpty)) {
                  val apvs = dbActionProperty.getActionPropertyValue(ap)
                  if (apvs != null && apvs.size() > 0) {
                    dbManager.removeAll(apvs.map(_.unwrap()))
                  }
                } else {
                  apv = dbActionProperty.setActionPropertyValue(ap, value, 0)
                }

                new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope).set(attribute)
                if (apv != null) {
                  entities = entities + ap + apv.unwrap
                } else {
                  entities = entities + ap
                }
              }

              case (Some(valueId), _) => {
                val ap = dbActionProperty.updateActionProperty(
                  id.intValue,
                  attribute.version.intValue,
                  userData)
                val apv = dbActionProperty.setActionPropertyValue(
                  ap,
                  valueId,
                  0)
                new ActionPropertyWrapper(ap, dbActionProperty.convertValue, dbActionProperty.convertScope).set(attribute)
                entities = entities + ap + apv.unwrap
              }

            }
          }
        })
        if (endDate != null) {
          a.setEndDate(endDate)
          a.setStatus(ActionStatus.FINISHED.getCode) //WEBMIS-880
        }
        eventId = a.getEvent.getId.intValue()
      })

      result = dbManager
        .mergeAll(entities)
        .filter(result.contains(_))
        .map(_.asInstanceOf[Action])
        .toList

      val r = dbManager.detachAll[Action](result).toList

      //Сохранение диагнозов в таблицу Диагностик
      //var apsForDiag = new util.LinkedList[ActionProperty]()
      //entities.foreach(f => if (f.isInstanceOf[ActionProperty]) apsForDiag.add(f.asInstanceOf[ActionProperty]))
      dbManager.persistAll(this.saveDiagnoses(eventId,
        entities.filter(_.isInstanceOf[ActionProperty]).map(_.asInstanceOf[ActionProperty]).toList,
        entities.filter(_.isInstanceOf[APValue]).map(_.asInstanceOf[APValue]).toList,
        userData))

      /*
      r.foreach(newAction => {
        val newValues = dbActionProperty.getActionPropertiesByActionId(newAction.getId.intValue)
        actionEvent.fire(new ModifyActionNotification(oldAction,
          oldValues,
          newAction,
          newValues))
      })
      */
      r

    } finally {
      appLock.releaseLock(lockId)
    }
  }

  private def saveDiagnoses(eventId: Int, apList: java.util.List[ActionProperty], apValue: java.util.List[APValue], userData: AuthData): java.util.List[AnyRef] = {

    var map = Map.empty[String, java.util.Set[AnyRef]]
    val characterAP = apList.find(p => p.getType.getCode != null && p.getType.getCode != null && p.getType.getCode.compareTo(i18n("db.apt.documents.codes.diseaseCharacter")) == 0).getOrElse(null)
    val stageAP = apList.find(p => p.getType.getCode != null && p.getType.getCode != null && p.getType.getCode.compareTo(i18n("db.apt.documents.codes.diseaseStage")) == 0).getOrElse(null)
    var isStageSaved = false
    var preCharacterId = 0
    var preStageId = 0

    //val characterAPV =  if (characterAP!=null) dbActionProperty.getActionPropertyValue(characterAP) else null
    val characterAPV = if (characterAP != null) {
      apValue.filter(apv =>
        apv.unwrap().isInstanceOf[APValueString] &&
        apv.unwrap().asInstanceOf[APValueString].getId.getId.equals(characterAP.getId)
      ).toList
    } else null
    if (characterAPV != null && characterAPV.size > 0 && characterAPV.get(0).getValueAsId.compareTo("") != 0) {
      preCharacterId = Integer.valueOf(characterAPV.get(0).getValueAsId).intValue()
    }

    //val stageAPV = if (stageAP!=null) dbActionProperty.getActionPropertyValue(stageAP) else null
    val stageAPV = if (stageAP != null) {
      apValue.filter(apv => apv.unwrap().isInstanceOf[APValueString] &&
        apv.unwrap().asInstanceOf[APValueString].getId.getId.equals(stageAP.getId)
      ).toList
    } else null
    if (stageAPV != null && stageAPV.size > 0 && stageAPV.get(0).getValueAsId.compareTo("") != 0) {
      preStageId = Integer.valueOf(stageAPV.get(0).getValueAsId).intValue()
    }

    apList.foreach(ap => {
      var characterId = 0
      var stageId = 0
      if (ap.getType.getTypeName.compareTo("MKB") == 0) {
        val descriptionAP = apList.find(p => ap.getType.getCode != null && p.getType.getCode != null && !ap.equals(p) && ap.getType.getCode.contains(p.getType.getCode) /*p.getType.getCode.compareTo(ap.getType.getCode.substring(0, ap.getType.getCode.size - 3))==0*/).getOrElse(null)
        if (ap.getType.getCode != null) {
          if (ap.getType.getCode.compareTo(i18n("appeal.diagnosis.diagnosisKind.finalMkb")) == 0) {
            characterId = preCharacterId
            stageId = preStageId
            isStageSaved = true
          } else if (ap.getType.getCode.compareTo(i18n("appeal.diagnosis.diagnosisKind.mainDiagMkb")) == 0 && !isStageSaved) {
            if (apList.find(p => ap.getType.getCode != null && p.getType.getCode != null && p.getType.getCode.compareTo(i18n("appeal.diagnosis.diagnosisKind.finalMkb")) == 0).getOrElse(null) == null) {
              characterId = preCharacterId
              stageId = preStageId
            }
          }

          //val mkbAPV = dbActionProperty.getActionPropertyValue(ap)
          //val descAPV = if (descriptionAP!=null) dbActionProperty.getActionPropertyValue(descriptionAP) else null
          val mkbAPV = apValue.filter(apv => apv.unwrap().isInstanceOf[APValueMKB] &&
            apv.unwrap().asInstanceOf[APValueMKB].getId.getId.equals(ap.getId)
            ).toList
          val descAPV = apValue.filter(apv => apv.unwrap().isInstanceOf[APValueString] &&
            descriptionAP != null &&
            apv.unwrap().asInstanceOf[APValueString].getId.getId.equals(descriptionAP.getId)
          ).toList
          if (mkbAPV != null && mkbAPV.size > 0 && mkbAPV.get(0).getValueAsId.compareTo("") != 0) {
            map += (ap.getType.getCode -> Set[AnyRef]((-1, //TODO: ???Всегда создавать новые записи в истории в т.ч. и при редактировании
              if (descAPV != null && descAPV.size > 0) descAPV.get(0).getValueAsString else "",
              Integer.valueOf(mkbAPV.get(0).getValueAsId),
              characterId,
              stageId)))
          } else {
            map += (ap.getType.getCode -> null)
          }
        }
      }
    })
    val diag = diagnosisBean.insertDiagnoses(eventId, mapAsJavaMap(map), userData)
    diag
  }

  def changeActionStatus(eventId: Int,
                         actionId: Int,
                         status: Short) = {
    val action = dbAction.updateActionStatus(actionId, status)
    dbManager.mergeAll[Action](List(action))

    val ActionStatus = ConfigManager.ActionStatus.immutable

    status match {
      case ActionStatus.Canceled => {
        /*
        r.foreach(a => {
          val values = dbActionProperty.getActionPropertiesByActionId(a.getId.intValue)
          actionEvent.fire(new CancelActionNotification(a, values))
        })
        */
      }
      case _ => {

      }
    }
    true
  }

  def fromActionTypes(types: java.util.Set[ActionType],
                      typeName: String,
                      converter: (ActionPropertyType) => CommonAttribute) = {
    types.foldLeft(
      new CommonData(0, dbVersion.getGlobalVersion)
    )((data, at) => {
      val entity = new CommonEntity(at.getId,
        0,
        at.getName,
        typeName,
        at.getGroupId,
        null,
        at.getCode,
        at.getFlatCode,
        at.getMnemonic,
        at.getContext)
      val group = new CommonGroup
      dbActionType.getActionTypePropertiesById(at.getId.intValue).foreach(
        (apt) => group add converter(apt)
      )
      data add (entity add group)
    })
  }



  def converterFromList(list: java.util.List[String], apt: ActionPropertyType) = {

    val map = list.foldLeft(Map.empty[String, String])(
      (str_key, el) => {
        val key = el
        val value = if (key == APWI.Value.toString) {
          apt.getDefaultValue
        }
        else if (key == APWI.ValueId.toString) {
          apt.getDefaultValue
        }
        else if (key == APWI.Unit.toString) {
          apt.getUnit.getName
        }
        else if (key == APWI.Norm.toString) {
          apt.getNorm
        }
        else if (key == APWI.IsAssignable.toString) {
          apt.getIsAssignable.toString
        }
        else {
          ""
        }
        str_key + (key -> value)
      })

    val typeName =  apt.getTypeName match {
      case "Reference" => "String"
      case _           => apt.getTypeName
    }

    new CommonAttributeWithLayout(apt.getId,
      0,
      apt.getName,
      apt.getCode,
      typeName,
      dbActionProperty.convertScope(apt), //apt.getConstructorValueDomain,
      map,
      (for(r <- apt.getActionPropertyRelation) yield new ActionPropertyRelationWrapper(r)).toList.asJava,
      dbLayoutAttributeValueBean.getLayoutAttributeValuesByActionPropertyTypeId(apt.getId.intValue()).toList,
      apt.isMandatory.toString,
      apt.isReadOnly.toString)
  }

  def fromActionTypesForWebClient(actionType: ActionType,
                                  typeName: String,
                                  listForSummary: java.util.List[StringId],
                                  listForConverter: java.util.List[String],
                                  patient: Patient) = {
    val data = new CommonData(0, dbVersion.getGlobalVersion)
    val entity = new CommonEntity(actionType.getId.intValue(),
      0,
      actionType.getName,
      typeName,
      actionType.getId.intValue(),
      null,
      actionType.getCode,
      actionType.getFlatCode,
      actionType.getMnemonic,
      actionType.getContext)
    //***
    val group0 = new CommonGroup(0, "Summary")
    val a = new Action()
    a.setId(0)
    a.setActionType(actionType)
    initPlannedEndDate(actionType, a)
    addAttributes(
      group0,
      new ActionWrapper(a),
      /*attributes*/ listForSummary)

    entity add group0
    //***
    val group = new CommonGroup(1, "Details")
    dbActionType.getActionTypePropertiesById(actionType.getId.intValue).foreach(
      (apt) => {
        if (patient == null)
          group add converterFromList(listForConverter, apt)
        else if (checkActionPropertyTypeForPatientAgeAndSex(patient, apt)) {
          group add converterFromList(listForConverter, apt)
        }
      }
    )
    data add (entity add group)

  }


  def initPlannedEndDate(actionType: ActionType, a: Action) {
    //Запишем планируемую дату в акшен по логике https://korusconsulting.atlassian.net/browse/WEBMIS-1013
    val date = Calendar.getInstance()
    date.setTime(new Date())

    actionType.getDefaultPlannedEndDate match {
      case 0 => {
        a.setPlannedEndDate(null)
      }
      case 1 => {
        date.add(Calendar.DAY_OF_YEAR, 1)
        date.set(Calendar.HOUR_OF_DAY, 7)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        a.setPlannedEndDate(date.getTime)
      }
      case 2 => {
        date.set(Calendar.HOUR_OF_DAY, 7)
        date.set(Calendar.MINUTE, 0)
        date.set(Calendar.SECOND, 0)
        date.set(Calendar.MILLISECOND, 0)
        var isOver = false
        while (!isOver) {
          date.add(Calendar.DAY_OF_YEAR, 1)
          val isWeekDay = date.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && date.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
          val isHoliday = dbCalendarExceptionsBean.getHolideyByDate(date.getTime) != null
          val isPerenos = dbCalendarExceptionsBean.getPerenosByDate(date.getTime) != null
          if ((isWeekDay && ((isHoliday && isPerenos) || !isHoliday)) || (!isWeekDay && !isHoliday && isPerenos)) {
            isOver = true
          }
        }
        a.setPlannedEndDate(date.getTime)
      }
      case 3 => {
        if (date.get(Calendar.HOUR) != 23) {
          //if (date.get(Calendar.MINUTE) != 0) {
          date.add(Calendar.HOUR_OF_DAY, 1)
          //}
          date.set(Calendar.MINUTE, 0)
          date.set(Calendar.SECOND, 0)
          date.set(Calendar.MILLISECOND, 0)
        } else {
          date.set(Calendar.MINUTE, 59)
          date.set(Calendar.SECOND, 0)
          date.set(Calendar.MILLISECOND, 0)
        }
        a.setPlannedEndDate(date.getTime)
      }
    }
  }

  def fromActions(actions: java.util.List[Action],
                  actionName: String,
                  converters: java.util.List[(Action) => CommonGroup]) = {
    actions.foldLeft(
      new CommonData
    )((data, action) => {
      data add converters.foldLeft(
        new CommonEntity(action.getId,
          action.getVersion,
          action.getActionType.getName,
          actionName,
          action.getActionType.getId,
          action.getStatus,
          action.getActionType.getCode,
          action.getActionType.getFlatCode,
          action.getActionType.getMnemonic,
          action.getActionType.getContext)
      )((entity, converter) => entity add converter(action))
    })
  }

  def addAttributes(group: CommonGroup,
                    wrapper: ActionWrapper,
                    attributeNames: java.util.List[StringId]) = {
    attributeNames.foldLeft(group)(
      (group, attributeName) => group add wrapper.get(attributeName)
    )
  }

  def defineAgeOfPatient(patient: Patient): CustomCalendar = {
    if (patient != null) {
      val now = Calendar.getInstance()
      now.setTime(new Date())
      val birth = Calendar.getInstance()
      birth.setTime(if (patient != null) patient.getBirthDate else new Date())


      val age = Calendar.getInstance()
      val year = Years.yearsBetween(new DateTime(patient.getBirthDate), new DateTime(new Date())).getYears
      val month = Months.monthsBetween(new DateTime(patient.getBirthDate), new DateTime(new Date())).getMonths
      val week = Weeks.weeksBetween(new DateTime(patient.getBirthDate), new DateTime(new Date())).getWeeks
      val day = Days.daysBetween(new DateTime(patient.getBirthDate), new DateTime(new Date())).getDays

      age.set(year, month, day, 0, 0)

      new CustomCalendar(year, month, week, day)
    } else {
      null
    }
  }

  /**
   * Извращенный метод определения соответствия возраста и пола пациента и action property type
   * @param patient Пациент, для которого проводится определение соответствия
   * @param apt Тип свойства, для которого проводится проверка соответствия
   * @return true - если свойство соответствует возрасту и полу пациента и false, если не соответствует, в случае, если
   *         пол у пациента или типа свойства не задан - возвращается true
   */
  def checkActionPropertyTypeForPatientAgeAndSex(patient: Patient, apt: ActionPropertyType): Boolean = {
    if(patient == null || apt == null)
      throw new IllegalArgumentException("Входящие параметры не могут быть null")

    val age = defineAgeOfPatient(patient)
    // Проверяем формат, в котором задана нижняя граница (днях, неделях, месяцах или годах)
    // и сверяем возраст пациента с заданной границей
    val lowLimit = apt.getAge_bu match {
      case 1 => age.getDay   > apt.getAge_bc   // Дни
      case 2 => age.getWeek  > apt.getAge_bc   // Недели
      case 3 => age.getMonth > apt.getAge_bc   // Месяцы
      case 4 => age.getWeek  > apt.getAge_bc   // Года
      case _ => true //Если значение не задано - то будем считать, что возраст пациента удовлетворяет
    }

    // Аналогично, но в обратную сторону проверяем соответствие возраста верхней границе
    val topLimit = apt.getAge_eu match {
      case 1 => age.getDay   < apt.getAge_ec
      case 2 => age.getWeek  < apt.getAge_ec
      case 3 => age.getMonth < apt.getAge_ec
      case 4 => age.getYear  < apt.getAge_ec
      case _ => true
    }

    // Проверяем соответствие пола пациента
    val sex =
      if (apt.getSex == 0 || patient.getSex == 0)
        true
      else
        apt.getSex == patient.getSex


    lowLimit && topLimit && sex
  }


}
