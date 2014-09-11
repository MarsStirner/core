package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import scala.beans.BeanProperty
import java.util.Date
import ru.korus.tmis.core.entity.model.{ActionProperty, APValue, Action}
import ru.korus.tmis.core.exception.CoreException
import java.util
import scala.collection.JavaConversions._

/**
 * Контейнер для списка хирургических операций
 * Спецификация: https://docs.google.com/document/d/1wuIO-4QNwq3nD7NEUAFIxDIIOmYFp35pYb3evZ7imcM/edit#heading=h.78wgoy5wysa5
 *               строка в спецификации: 26
 * Author: idmitriev Systema-Soft
 * Date: 8/12/13 10:12 PM
 * Since: 1.0.1.31
 */
@XmlType(name = "surgicalOperationsListData")
@XmlRootElement(name = "surgicalOperationsListData")
@JsonIgnoreProperties(ignoreUnknown = true)
class SurgicalOperationsListData {

  @BeanProperty
  var data: java.util.LinkedList[SurgicalOperationInfoContainer] = new java.util.LinkedList[SurgicalOperationInfoContainer]

  def this(records: java.util.LinkedHashMap[ActionProperty, java.util.List[APValue]],
           mGetOperationTypes: (Int, util.List[String]) => util.Map[ActionProperty, java.util.List[APValue]]){
    this()
    val map = new java.util.LinkedHashMap[Action,util.ArrayList[(String, APValue)]]
    if (records!=null && records.size()>0){
      try {
        records.foreach(record => {
          if (record._1!=null && record._2!=null && record._2.size()>0) {
            val action = record._1.getAction
            val ap_value = record._2.get(0)
            if (!map.contains(action)){
              map.put(action, new util.ArrayList[(String, APValue)])
            }
            map.get(action).add((record._1.getType.getCode, ap_value))
          }
        })
        map.foreach(f=>{
          val operation = this.getAPValueByComparedCode(f._2, "operationName")
          val complication = this.getAPValueByComparedCode(f._2, "complicationName")
          val anesthesia = this.getAPValueByComparedCode(f._2, "methodAnesthesia")
          var code: String = ""
          if (mGetOperationTypes!=null){
            val operationTypes = mGetOperationTypes(f._1.getId.intValue(), asJavaList(List("OperationType")))
            if (operationTypes!=null && operationTypes.size()>0){
              val codes = operationTypes.iterator.next()._2
              if (codes!=null && codes.size()>0)
                code = codes.get(0).getValueAsString
            }
          }
          this.data.add(new SurgicalOperationInfoContainer(f._1, operation, complication, anesthesia, code))
        })
      } finally {
        map.clear()
      }
    }
  }

  private def getAPValueByComparedCode(record: util.ArrayList[(String, APValue)], code: String): APValue = {
    val finded = record.find(f=>f._1.compareTo(code)==0).getOrElse(null)
    if (finded!=null)
      finded._2
    else null
  }
}

@XmlType(name = "surgicalOperationInfoContainer")
@XmlRootElement(name = "surgicalOperationInfoContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class SurgicalOperationInfoContainer {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var begDate: Date = _
  @BeanProperty
  var endDate: Date = _
  @BeanProperty
  var surgeon: ExtendedPersonNameContainer = _
  @BeanProperty
  var operationName: String = ""
  @BeanProperty
  var operationCode: String = ""
  @BeanProperty
  var complicationName: String = ""
  @BeanProperty
  var methodAnesthesia: String = ""

  def this(action: Action, operation: APValue, complication: APValue, anesthesia: APValue, code: String) {
    this()
    try {
      if (action!=null) {
        this.id = action.getId.intValue()
        this.begDate = action.getBegDate
        this.endDate = action.getEndDate
        if(action.getExecutor!=null)
          this.surgeon = new ExtendedPersonNameContainer(action.getExecutor)
        if(operation!=null)
          this.operationName = operation.getValueAsString
        if(complication!=null)
          this.complicationName = complication.getValueAsString
        if(anesthesia!=null)
          this.methodAnesthesia = anesthesia.getValueAsString match {
            case "АМН" => "1"
            case "ЭТН" => "2"
            case value: String => value
          }
        this.operationCode = code
      }
    }
    catch {
      case ex: CoreException => {
        new CoreException("Undeclared error in: SurgicalOperationInfoContainer")
      }
    }
  }
}
