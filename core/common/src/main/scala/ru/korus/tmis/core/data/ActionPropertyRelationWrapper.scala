package ru.korus.tmis.core.data

import java.util

import ru.korus.tmis.core.entity.model._

import scala.collection.JavaConverters._
import ru.korus.tmis.core.exception.CoreException
import javax.xml.bind.annotation.{XmlRootElement, XmlType}

import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/28/14
 * Time: 6:08 PM
 */
@XmlType(name = "actionPropertyRelationWrapper")
@XmlRootElement(name = "actionPropertyRelationWrapper")
class ActionPropertyRelationWrapper(relation: ActionPropertyRelation) {

  @BeanProperty
  val subject: Integer = relation.subjectType.getId

  @BeanProperty
  val relationType: ActionPropertyRelationType = relation.getRelationType

  @BeanProperty
  val relationState: ActionPropertyRelationState = relation.getRelationState

  @BeanProperty
  val value: util.Set[ActionPropertyRelationValueWrapper] = (for(y <- relation.getValues.asScala) yield new ActionPropertyRelationValueWrapper(y)).asJava

}

@XmlType(name = "actionPropertyRelationValueWrapper")
@XmlRootElement(name = "actionPropertyRelationValueWrapper")
class ActionPropertyRelationValueWrapper(v : ActionPropertyRelationValue) {

  @BeanProperty
  var valueType: String = _

  @BeanProperty
  var value: Object = _

  (v.valueDate, v.valueFloat, v.valueString, v.refType) match {
    case (d: java.util.Date, null, null, null) => valueType = "date"; value = d
    case (null, f: java.lang.Float, null, null) => valueType = "float"; value = f
    case (null, null, s: String, null) => valueType = "string"; value = s
    case (null, null, null, ref: ActionPropertyRelationValueRefType) => valueType = ref.toString; value = v.valueReference
    case _ => throw new CoreException("Invalid entry in the table AP_RelationValue with id = " + v.getId)
  }



}
