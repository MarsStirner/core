package ru.korus.tmis.core.database.common

import java.text.SimpleDateFormat
import java.util.Date

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.database.DbDiagnosticBeanLocal
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException

import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import scala.collection.JavaConversions._
import scala.collection.mutable.LinkedHashMap
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

//
@Stateless
class DbActionPropertyBean
  extends DbActionPropertyBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _


  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionPropertyType: DbActionPropertyTypeBeanLocal = _

  @EJB
  var dbbDiagnosticBeanLocal: DbDiagnosticBeanLocal = _

  private val FILTER_ID = 0
  private val FILTER_NAME = 1
  private val FILTER_CODE = 2
  private val FILTER_TYPENAME = 3

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertyById(id: Int) = {
    val result = em.createQuery(ActionPropertyFindQuery,
      classOf[ActionProperty])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.ActionPropertyNotFound,
          i18n("error.actionPropertyNotFound").format(id))
      }
      case size => {
        val actionProperty = result.iterator.next()

        actionProperty
      }
    }
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionId(actionId: Int) = {
    val result = em.createQuery(ActionPropertiesByActionIdQuery,
      classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  def getActionPropertyValue(actionProperty: ActionProperty) = {
    val APValueRLSType = classOf[APValueRLS]
    actionProperty.getValueClass match {
      case null => {
        error("ActionProperty with NULL valueClass: " + actionProperty)
        scala.List.empty[APValue]
      }
      case APValueRLSType => {
        // Получаем целочисленные значения РЛС, которые соответствуют значениям ПК из rls.vNomen
        val apvs = getPropertyValues(actionProperty.getId.intValue, APValueRLSType.getSimpleName)
        // Достаем для каждого значения строку из РЛС и передаем в обертку
        val result = apvs.foldLeft(scala.List.empty[APValue])((l, v) => {
          val apvRls = v.asInstanceOf[APValueRLS]
          val ns = getRLSNomenclature(apvRls.getKey)
          val vals: scala.List[APValue] = ns.map((n) => {
            val w = new APValueRLSWrapper(apvRls, n)
            w.asInstanceOf[APValue]
          }).toList
          vals ::: l
        })
        result
      }
      case apValueClass => {
        val apvs = getPropertyValues(actionProperty.getId.intValue, apValueClass.getSimpleName)
        val result = apvs.map((v) => v.asInstanceOf[APValue])
        result
      }
    }
  }

  def createActionProperty(a: Action, aptId: Int, staff: Staff) = {
    val apt = dbActionPropertyType.getActionPropertyTypeById(aptId)
    val now = new Date()
    val ap = new ActionProperty

    ap.setCreateDatetime(now)
    ap.setModifyDatetime(now)
    //TODO: временно подсовываю пустой Staff когда нет AuthData
    if (staff != null) {
      ap.setCreatePerson(staff)
      ap.setCreateDatetime(now)
      ap.setModifyPerson(staff)
      ap.setModifyDatetime(now)
    }

    ap.setAction(a)
    ap.setType(apt)

    ap
  }

  def createActionPropertyWithDate(a: Action, aptId: Int, staff: Staff, now: Date): ActionProperty = {
    val apt = dbActionPropertyType.getActionPropertyTypeById(aptId)
    if(apt == null) {
      return null;
    }
    val ap = new ActionProperty

    ap.setCreateDatetime(now)
    ap.setModifyDatetime(now)
    //TODO: временно подсовываю пустой Staff когда нет AuthData
    if (staff != null) {
      ap.setCreatePerson(staff)
      ap.setCreateDatetime(now)
      ap.setModifyPerson(staff)
      ap.setModifyDatetime(now)
    }

    ap.setAction(a)
    ap.setType(apt)

    ap
  }

  def updateActionProperty(id: Int, version: Int, staff: Staff) = {
    val ap = getActionPropertyById(id)

    ap.setModifyPerson(staff)
    ap.setModifyDatetime(new Date)
    ap.setVersion(version)

    ap
  }

  def setActionPropertyValue(ap: ActionProperty, value: String, index: Int = 0) = {
    val apvs = getActionPropertyValue(ap)

    apvs.size match {
      case 0 => {
        // Если не нашли значение, то создаем новое, если не флатДиректори
        if ((ap.getType.getTypeName.compareTo("FlatDirectory") != 0 && ap.getType.getTypeName.compareTo("FlatDictionary") != 0) ||
          (value != null && value.compareTo("") != 0)) {
          createActionPropertyValue(ap, value, index)
        }
        else null
      }
      case _ => {
        if (ap.getType.getIsVector) {
          createActionPropertyValue(ap, value, index)
        } else {
          val apv = apvs.get(0)
          if (value != null) {
            val apt = ap.getType()
            if ("Reference".equals(apt.getTypeName)) {
              if (apv.unwrap().setValue(toRefValue(ap, value)) ) apv else null
            } else {
              if (apv.unwrap.setValueFromString(value)) apv else null
            }
          }
          else {
            em remove apv
            null
          }
        }
      }
    }
  }

  def toRefValue(ap: ActionProperty, value: String): String = {
    val code = value.split("-")(0).trim
    val res = em.createNativeQuery("SELECT `id` FROM %s WHERE `code` = '%s'".format(ap.getType.getValueDomain, code)).getResultList
    if(res.isEmpty) {
      return ""
    }
    String.valueOf(res(0))
  }

  def fromRefValue(apt: ActionPropertyType, value: String): String = {
    val code = value.split("-")(0).trim
    val data = em.createNativeQuery("SELECT `code`, `name` FROM %s WHERE `id` = %s".format(apt.getValueDomain, value)).getResultList
    var res = if (data.isEmpty) {
      value
    } else {
      toCodeAndName(data(0).asInstanceOf[Array[Object]])
    }
    res
  }

  def convertTableValue(apt: ActionPropertyType, value: String): java.util.LinkedList[java.util.LinkedList[String]] = {
    val res = new java.util.LinkedList[java.util.LinkedList[String]]
    val rbAPTableFieldList = em.createNamedQuery("RbAPTableField.findByCode", classOf[RbAPTableField]).setParameter("code", apt.getValueDomain).getResultList
    val rbAPTable = rbAPTableFieldList.get(0).getRbAptable
    val prmList = rbAPTableFieldList.foldLeft("")( (b,a) => {
      val s = if (b.isEmpty) "" else ","
      val name = if (a.getReferenceTable == null) a.getRbAptable.getTableName +  "." + a.getFieldName else a.getReferenceTable + ".name"
      b + s + (if(name.equals("trfuOrderIssueResult.stickerUrl")) "CONCAT('" + ConfigManager.TrfuProp.StickerBaseUrl + "'," + name + ")" else name)
    })
    val tblList =  rbAPTableFieldList.foldLeft(rbAPTable.getTableName)( (b,a) => {
      if (a.getReferenceTable == null) {
        b
      } else {
           b + " INNER JOIN " + a.getReferenceTable + " ON " + a.getReferenceTable + ".id=" + rbAPTable.getTableName + "." + a.getFieldName
      }
    })
    val query: String = "SELECT %s FROM %s WHERE %s.%s=%s".format(prmList, tblList, rbAPTable.getTableName, rbAPTable.getMasterField, value)
    val data = em.createNativeQuery(query).getResultList
    if (data.isEmpty) {
      res.add(new java.util.LinkedList[String])
    }

    data.foreach(d => {
      res.add(new java.util.LinkedList[String])
      d.asInstanceOf[Array[Object]].foreach(v => {res.getLast.add("" + v)})
    })
    res
  }

  def convertValue(apt: ActionPropertyType, value: String): java.util.LinkedList[java.util.LinkedList[String]] = {
    if ("Table".equals(apt.getTypeName)) {
      return convertTableValue(apt, value)
    } else if ("Diagnosis".equals(apt.getTypeName)) {
      return convertDiagValue(value)
    }
    val res = new java.util.LinkedList[java.util.LinkedList[String]]
    res.add(new java.util.LinkedList[String])
    if ("Reference".equals(apt.getTypeName)) {
      res.get(0).add(fromRefValue(apt, value));
    } else {
      res.get(0).add(value);
    }
    res
  }

  def convertScope(propertyType: ActionPropertyType) = {
    propertyType.getTypeName match {
      case "Reference" => getScopeForReference(propertyType)
      case "Table" => getScopeForTable(propertyType)
      case "Diagnosis" => getScopeForDiagnosis(propertyType)
      case _ => propertyType.getValueDomain
    }
  }

  def getScopeForReference(propertyType: ActionPropertyType) = {
    val rbData = em.createNativeQuery("SELECT `code`, `name` FROM %s".format(propertyType.getValueDomain)).getResultList
    val resList: java.util.List[Array[Object]] = rbData.asInstanceOf[java.util.List[Array[Object]]]
    //преобразуем результат SQL запроса в CSV формат вида "<code> - <name>, <code> - <name>, ..." и при наличии в названии ',' заменяем на "(....)"
    resList.foldLeft("")((b, a) => {
      var v: String = toCodeAndName(a)
      b + v + ","
    })
  }

  def getScopeForTable(propertyType: ActionPropertyType) = {
    val rbAPTableFieldList = em.createNamedQuery("RbAPTableField.findByCode", classOf[RbAPTableField]).setParameter("code", propertyType.getValueDomain).getResultList
    //преобразуем результат SQL запроса в CSV формат вида "<code> - <name>, <code> - <name>, ..." и при наличии в названии ',' заменяем на "(....)"
    rbAPTableFieldList.foldLeft("")((b, a) => {
      b + a.getName + ","
    })
  }

  def getScopeForDiagnosis(propertyType: ActionPropertyType) = {
    val diagTableHeader = Array(
      "Дата начала", "Дата окончания", 	"МКБ", "Тип",	"Характер",	"Результат", "Исход",  "Врач",	"Примечание"
    )
    diagTableHeader.foldLeft("")((b, a) => {b + a + ","})
  }



  def convertDiagValue(value: String): java.util.LinkedList[java.util.LinkedList[String]] = {
    val res = new java.util.LinkedList[java.util.LinkedList[String]]
    try {
      val formatter = new SimpleDateFormat("yyyy-MM-dd")
      val d: Diagnostic = dbbDiagnosticBeanLocal.getDiagnosticById(Integer.parseInt(value))
      val diagnosis: Diagnosis = d.getDiagnosis
      if(d != null && diagnosis != null) {
        res.add(new java.util.LinkedList[String])
        res.get(0).add(if (diagnosis.getSetDate == null) "" else formatter.format(diagnosis.getSetDate))
        res.get(0).add(if (diagnosis.getEndDate == null) "" else formatter.format(diagnosis.getSetDate))
        res.get(0).add(if (diagnosis.getMkb == null) "" else diagnosis.getMkb.getDiagID)
        res.get(0).add(if (diagnosis.getDiagnosisType == null) "" else diagnosis.getDiagnosisType.getName)
        res.get(0).add(if (diagnosis.getCharacter == null) "" else diagnosis.getCharacter.getName)
        res.get(0).add(if (d.getResult == null) "" else d.getResult.getName)
        res.get(0).add(if (d.getAcheResult == null) "" else d.getAcheResult.getName)
        res.get(0).add(if (d.getDiagnosis.getPerson == null) "" else d.getDiagnosis.getPerson.getFullName)
        res.get(0).add(d.getNotes)
      }
    } catch {
      case e: NumberFormatException =>
        throw new CoreException("Невозможно обработать значение свойства типа 'Diagnosis: " + value)
    }
    res
  }

  def toCodeAndName(a: Array[Object]): String = {
    var v = a(0).asInstanceOf[String] + " - " + a(1).asInstanceOf[String]
    v = if (v.indexOf(", ") > 0) {
      v.replace(", ", " (") + ")"
    } else v
    v
  }


  def createActionPropertyValue(ap: ActionProperty, value: String, index: Int = 0) = {
    val cls = ap.getValueClass
    if (cls == null)
      throw new CoreException(i18n("error.actionPropertyTypeClassNotFound").format(ap.getId))

    if (value != null) {
      val apv = cls.newInstance.asInstanceOf[APValue]
      // Устанваливаем id, index
      apv.linkToActionProperty(ap, index)
      var v = if ("Reference".equals(ap.getType.getTypeName)) {
        toRefValue(ap, value)
      } else {
        value
      }
      // Записываем значение
      v = checkAuto(ap, v)
      if (apv.setValueFromString(v)) apv else null
    } else
      null
  }

  ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  /**
   * Получить APValue в простом случае по id и имени entity-класса
   */
  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getPropertyValues(id: Int, entityName: String) = {
    val query = APValueQuery.format(entityName)
    val apvs = em.createQuery(query)
      .setParameter("id", id)
      .getResultList

    apvs
  }

  /**
   * @param code поле code в таблице rls.vNomen
   */
  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getRLSNomenclature(code: Int): java.util.List[Nomenclature] = {
    em.createQuery("SELECT n FROM Nomenclature n WHERE n.code = :code",
      classOf[Nomenclature])
      .setParameter("code", code)
      .getResultList
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeNames(actionId: Int, names: java.util.List[String]) = {
    this.getActionPropertiesByActionIdAndCustomParameters(actionId, names, FILTER_NAME)
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeCodes(actionId: Int, codes: java.util.List[String]) = {
    this.getActionPropertiesByActionIdAndCustomParameters(actionId, codes, FILTER_CODE)
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeTypeNames(actionId: Int, codes: java.util.List[String]) = {
    this.getActionPropertiesByActionIdAndCustomParameters(actionId, codes, FILTER_TYPENAME)
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndTypeId(actionId: Int, typeId: Int) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndTypeIdQuery,
      classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("typeId", typeId)
      .getResultList


    result
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndRbCoreActionPropertyIds(actionId: Int, ids: java.util.List[java.lang.Integer]) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndRbCoreActionPropertyIds, classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("ids", asJavaCollection(ids))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndActionPropertyTypeCodes(actionId: Int, codes: java.util.Set[String]) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndTypeCodesQuery, classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("codes", asJavaCollection(codes))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByActionIdAndActionPropertyTypeCodesWithoutDel(actionId: Int, codes: java.util.Set[String]) = {
    val result = em.createQuery(ActionPropertiesByActionIdAndTypeCodesQuery2, classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("codes", asJavaCollection(codes))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesForEventByActionTypes(eventId: Int, atIds: java.util.Set[java.lang.Integer], coreIds: java.util.Set[java.lang.Integer]) = {
    val result = em.createQuery(ActionPropertiesForEventByActionTypesQuery, classOf[Array[AnyRef]])
      .setParameter("eventId", eventId)
      .setParameter("atIds", asJavaCollection(atIds))
      .setParameter("coreIds", asJavaCollection(coreIds))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap(0).asInstanceOf[ActionProperty], getActionPropertyValue(ap(0).asInstanceOf[ActionProperty]))
        map
      }
    )
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertiesByEventIdsAndActionPropertyTypeCodes(eventIds: java.util.List[java.lang.Integer], codes: java.util.Set[String], cntRead: Int, needStatus: Boolean) = {

    val sqlCodes = convertCollectionToSqlString(asJavaCollection(codes))
    val sqlEventIds = convertCollectionToSqlString(asJavaCollection(eventIds))
    var status = ""
    if (needStatus) {
      status = "AND a.status = 2"
    }
    val result = em.createNativeQuery(ActionPropertiesByEventIdAndActionPropertyTypeCodesQueryEx.format(sqlEventIds, sqlCodes, status))
      //.setParameter(1, new java.util.LinkedList(eventIds map(f => f.toString)))
      //.setParameter(2, sqlCodes)
      .setParameter(3, cntRead)
      .getResultList
    val resulted = result.foldLeft(new java.util.LinkedHashMap[java.lang.Integer, java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]]])(
      (map, ap) => {
        val eventId = ap.asInstanceOf[Array[Object]](0).asInstanceOf[java.lang.Integer]

        if (!map.contains(eventId))
          map.put(eventId, new java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]])

        val apId = ap.asInstanceOf[Array[Object]](1).asInstanceOf[java.lang.Integer]
        val app = getActionPropertyById(apId.intValue())
        val values = getActionPropertyValue(app)

        val apvMap = map.get(eventId)
        if (values != null && values.size() > 0)
          apvMap.put(app, values)
        map
      })
    resulted
  }

  private def convertCollectionToSqlString(collection: java.util.Collection[_]) = {
    var sqlStr: String = ""
    collection.foreach(el => sqlStr = if (sqlStr.isEmpty) "'" + el.toString + "'" else sqlStr + ",'" + el.toString + "'")
    sqlStr
  }

  private def getActionPropertiesByActionIdAndCustomParameters(actionId: Int, parameters: java.util.List[String], filterMode: Int) = {
    val result = em.createQuery(
      ActionPropertiesByActionIdAndNamesQuery.format(filterMode match {
        case FILTER_ID => "id"
        case FILTER_NAME => "name"
        case FILTER_CODE => "code"
        case FILTER_TYPENAME => "typeName"
        case _ => "id"
      }
      ), classOf[ActionProperty])
      .setParameter("actionId", actionId)
      .setParameter("names", asJavaCollection(parameters))
      .getResultList

    result.foldLeft(LinkedHashMap.empty[ActionProperty, java.util.List[APValue]])(
      (map, ap) => {

        map.put(ap, getActionPropertyValue(ap))
        map
      }
    )
  }

  def getCountOfActionsWithAppointmentType(appointmentType: Int) {
    val result = em.createQuery(ActionsWithAppointmentTypeQuery,
      classOf[Action])
      .setParameter("appointmentType", appointmentType)
      .getSingleResult
  }

  //@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
  def getActionPropertyValue_ActionByValue(action: Action): APValueAction = {
    val result = em.createQuery(ActionProperty_ActionByValue, classOf[APValueAction]).setParameter("VALUE", action).getResultList
    result.size match {
      case 0 => {
        null
      }
      case size => {

        result(0)
      }
    }
  }

  val ActionsWithAppointmentTypeQuery = """
    SELECT COUNT(a)
    FROM
      ActionProperty ap
        JOIN ap.action a
    WHERE
      a.appointmentType = :appointmentType
    ORDER BY
      v.id.index ASC
                                        """

  val ActionPropertiesByEventIdAndActionPropertyTypeCodesQueryEx =
    """
      SELECT counted.idd2, counted.apidd2
      FROM
      (
        SELECT grouped.idd as idd2, grouped.apidd as apidd2,
            if(
                @typex=CONCAT(grouped.idd,grouped.codee),
                @rownum:=@rownum+1,
                @rownum:=1+LEAST(0,@typex:=CONCAT(grouped.idd,grouped.codee))
            ) rown
        FROM
        (
                    SELECT e.id as idd, apt.code as codee, ap.createDatetime as acr, ap.id as apidd
                    FROM
                      ActionProperty ap
                        INNER JOIN Action a ON ap.action_id = a.id
                        INNER JOIN Event e ON a.event_id = e.id
                        INNER JOIN ActionPropertyType apt ON ap.type_id = apt.id,
                        (SELECT @rownum:=1, @typex:='_') counter
                    WHERE
                      e.id IN (%s)
                    AND
                      a.deleted = 0
                    AND
                      apt.code IN (%s)
                    %s
                    AND
                      apt.deleted = 0
                    AND (
                      exists (
                          SELECT ap_d.id
                          FROM
                              ActionProperty_Double ap_d
                          WHERE
                              ap_d.id = ap.id
                          AND
                              ap_d.value != '0.0'
                      )
                      OR
                      exists (
                          SELECT ap_i.id
                          FROM
                              ActionProperty_Integer ap_i
                          WHERE
                              ap_i.id = ap.id
                      )
                      OR
                      exists (
                          SELECT ap_s.id
                          FROM
                              ActionProperty_String ap_s
                          WHERE
                              ap_s.id = ap.id
                      )
                      OR
                      exists (
                          SELECT ap_b.id
                          FROM
                              ActionProperty_HospitalBed ap_b
                          WHERE
                              ap_b.id = ap.id
                      )
                      OR
                      exists (
                          SELECT ap_os.id
                          FROM
                              ActionProperty_OrgStructure ap_os
                          WHERE
                              ap_os.id = ap.id
                      )
                    )
                    GROUP BY idd, apt.code, ap.createDatetime DESC
        ) grouped
      ) counted
      WHERE rown <= ?3
    """
  //ORDER BY ap.id DESC
  val ActionPropertyFindQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.id = :id
    AND
      ap.deleted = 0
                                """

  val APValueQuery = """
    SELECT v
    FROM
      %s v
    WHERE
      v.id.id = :id
    ORDER BY
      v.id.index ASC
                     """

  val ActionPropertiesByActionIdQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.deleted = 0
    ORDER BY
      ap.actionPropertyType.idx ASC
                                        """

  val ActionPropertiesByActionIdAndNamesQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.%s IN :names
    AND
      ap.deleted = 0
    ORDER BY
      ap.actionPropertyType.idx ASC
                                                """

  val ActionPropertiesByActionIdAndTypeIdQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.id = :typeId
    AND
      ap.deleted = 0
                                                 """

  val ActionPropertiesByActionIdAndTypeCodesQuery = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.code IN :codes
    AND
      ap.deleted = 0
                                                    """

  val ActionPropertiesByActionIdAndTypeCodesQuery2 = """
    SELECT ap
    FROM
      ActionProperty ap
    WHERE
      ap.action.id = :actionId
    AND
      ap.actionPropertyType.code IN :codes
                                                     """

  val ActionPropertiesByActionIdAndRbCoreActionPropertyIds =
    """
      SELECT ap
      FROM
        ActionProperty ap
      WHERE
        ap.action.id = :actionId
      AND
        ap.actionPropertyType.id IN (
          SELECT cap.actionPropertyType.id
          FROM RbCoreActionProperty cap
          WHERE cap.actionType.id = ap.action.actionType.id
          AND cap.id IN :ids
        )
      AND
        ap.deleted = 0
    """
  val ActionPropertiesForEventByActionTypesQuery =
    """
      SELECT ap, MAX(a.createDatetime)
      FROM
        ActionProperty ap
          JOIN ap.action a
          JOIN a.actionType at
          JOIN a.event e
      WHERE
        e.id = :eventId
      AND at.id IN :atIds
      AND
        ap.actionPropertyType.id IN (
          SELECT cap.actionPropertyType.id
          FROM RbCoreActionProperty cap
          WHERE cap.actionType.id = at.id
          AND cap.id IN :coreIds
        )
      AND
        ap.deleted = 0
      GROUP BY ap
    """

  def createActionProperty(action: Action, actionPropertyType: ActionPropertyType): ActionProperty = {
    val now = new Date
    val newActionProperty = new ActionProperty()
    //Инициализируем структуру Event
    try {
      newActionProperty.setCreateDatetime(now);
      newActionProperty.setCreatePerson(null);
      newActionProperty.setModifyPerson(null);
      newActionProperty.setModifyDatetime(now);
      newActionProperty.setDeleted(false);
      newActionProperty.setAction(action);
      newActionProperty.setType(actionPropertyType)
      newActionProperty.setNorm("");
      //1. Инсертим
      em.persist(newActionProperty);
    }
    catch {
      case ex: Exception => throw new CoreException("error while creating action ");
    }
    em.flush();
    newActionProperty
  }

  val ActionProperty_ActionByValue = """
    SELECT ap_act
    FROM APValueAction ap_act
    WHERE ap_act.value = :VALUE
                                     """

  def getActionProperty_ActionByValue(action: Action): APValueAction = {
    em.createQuery(ActionProperty_ActionByValue, classOf[APValueAction]).setParameter("VALUE", action).getSingleResult
  }

  override def calcAuto(ap: ActionProperty): String = {
    var res: Integer = 1;
    val apvList = prevValueList(ap)
    if (!apvList.isEmpty) {
      res = apvList.get(0) + 1;
      for (i <- 1 to apvList.size()) {
        if(res == apvList.get(i)) {
          res = apvList.get(i) + 1
        } else {
          return String.valueOf(res)
        }
      }
    }
    String.valueOf(res)
  }

  def prevValueList(ap: ActionProperty): java.util.List[Integer] = {
    val cal = java.util.Calendar.getInstance();
    cal.setTimeInMillis(ap.getCreateDatetime.getTime)
    cal.set(cal.get(java.util.Calendar.YEAR), 0, 1, 0, 0)
    val apIdList = em.createNamedQuery("ActionProperty.ByTypeIdAndDate", classOf[Integer])
      .setParameter("aptId", ap.getType.getId)
      .setParameter("begDate", cal.getTime)
      .setMaxResults(1).getResultList
    val apvList = if (!apIdList.isEmpty) {
      em.createNamedQuery("APValueInteger.getValueById", classOf[Integer])
        .setParameter("fromId", apIdList.get(0))
        .getResultList
    } else {
      new java.util.ArrayList[Integer]()
    }
    apvList
  }

  def checkAutoValue(ap: ActionProperty, value: Int) = {
    val apvList = prevValueList(ap)
    apvList.foreach( v => if (v == value) {
      //Номер операции 533 уже используется
      throw new CoreException(0x0106, ap.getType.getName + " " + value + "  уже используется")
    })
  }

  def checkAuto(ap: ActionProperty, s: String): String = {
    if (ap.getType.getTypeName == "Integer" ) {
      if(s == "авто") {
        return calcAuto(ap)
      } else if (ap.getType.getValueDomain == "'авто'") {
        checkAutoValue(ap, Integer.parseInt(s))
      }
    }
    s
  }

}
