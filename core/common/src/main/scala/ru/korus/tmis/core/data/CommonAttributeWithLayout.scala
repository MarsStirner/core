package ru.korus.tmis.core.data

import java.util
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import scala.beans.BeanProperty
import java.util.LinkedList
import ru.korus.tmis.core.entity.model.layout.{LayoutAttributeValue, LayoutAttribute}
import scala.collection.JavaConversions._

/**
 * Интерфейс для работы с разметкой медицинских документов
 * Author: idmitriev Systema-Soft
 * Date: 5/15/13 9:52 PM
 * Since: 1.0.1.10
 */

class CommonAttributeWithLayout(id: Integer,
                                version: Integer,
                                name: String,
                                code: String,
                                aType: String,
                                mandatory: String,
                                readOnly: String,
                                scope: String,
                                props: Map[String, String],
                                relations: util.List[ActionPropertyRelationWrapper])
  extends CommonAttribute (id, version, name, code, aType, mandatory, readOnly, scope, null, props){

  @BeanProperty
  var layoutAttributeValues = new util.LinkedList[LayoutAttributeSimplifyDataContainer]

  @BeanProperty
  var attributeRelations: util.List[ActionPropertyRelationWrapper] = relations

  def this(id: Integer,
           version: Integer,
           name: String,
           code: String,
           aType: String,
           scope: String,
           props: Map[String, String],
           relations: util.List[ActionPropertyRelationWrapper],
           layout: List[LayoutAttributeValue],
           mandatory: String,
           readOnly: String,
           typeId: Integer) = {
    this(id, version, name, code, aType, mandatory, readOnly, scope, props, relations)
    layout.foreach(f=> this.layoutAttributeValues.add(new LayoutAttributeSimplifyDataContainer(f)))
    this.typeId = typeId
  }

  def this (ca: CommonAttribute,
            layout: List[LayoutAttributeValue],
            relations: util.List[ActionPropertyRelationWrapper] ) = {
    this(ca.id, ca.version, ca.name, ca.code, ca.`type`, ca.mandatory, ca.readOnly, ca.scope,  ca.getPropertiesMap, relations)
    this.tableValues = ca.tableValues
    layout.foreach(f=> this.layoutAttributeValues.add(new LayoutAttributeSimplifyDataContainer(f)))
  }
}

@XmlType(name = "layoutAttributeListData")
@XmlRootElement(name = "layoutAttributeListData")
@JsonIgnoreProperties(ignoreUnknown = true)
class LayoutAttributeListData {

  /*@BeanProperty
  var requestData: LayoutAttributeRequestData = _*/
  @BeanProperty
  var data: LinkedList[LayoutAttributeDataContainer] = new LinkedList[LayoutAttributeDataContainer]

  def this (attributes: java.util.List[LayoutAttribute]) {
    this()
    attributes.foreach(attribute => data.add(new LayoutAttributeDataContainer(attribute)))
  }
}

@XmlType(name = "layoutAttributeDataContainer")
@XmlRootElement(name = "layoutAttributeDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class LayoutAttributeDataContainer {

  @BeanProperty
  var id: Int = _                                           //Идентификатор атрибута
  //@BeanProperty
  //var name: String = ""                                     //Наименование атрибута
  @BeanProperty
  var title: String = _                                     //Название атрибута для отображения в интерфейсе
  @BeanProperty
  var description: String = ""                              //Описание атрибута и его использование
  @BeanProperty
  var code: String = ""                                     //Мнемокод атрибута
  @BeanProperty
  var typeName: String = ""                                 //Наименование типа поля, для которого этот атрибут применим
  @BeanProperty
  var measure: String = _                                   //Единица измерения атрибута
  @BeanProperty
  var defaultValue: String = ""                             //Значение по умолчанию

  def this (attribute: LayoutAttribute) {
    this()
    this.id = attribute.getId.intValue()
    //this.name = attribute.getName
    this.title = attribute.getTitle
    this.description = attribute.getDescription
    this.code = attribute.getCode
    this.typeName = attribute.getTypeName
    this.measure = attribute.getMeasure
    this.defaultValue = attribute.getDefaultValue
  }
}

@XmlType(name = "layoutAttributeSimplifyDataContainer")
@XmlRootElement(name = "layoutAttributeSimplifyDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class LayoutAttributeSimplifyDataContainer {

  @BeanProperty
  var id: Int = _
  @BeanProperty
  var layoutAttribute_id: Int = _
  @BeanProperty
  var value: String = _

  def this (value: LayoutAttributeValue) {
    this()
    this.id = value.getId.intValue()

    if(value.getLayoutAttribute == null)
      throw new Exception("Cannot find layout attribute of layout attribute value id=" + id)
    this.layoutAttribute_id = value.getLayoutAttribute.getId.intValue()

    this.value = value.getValue
  }
}
