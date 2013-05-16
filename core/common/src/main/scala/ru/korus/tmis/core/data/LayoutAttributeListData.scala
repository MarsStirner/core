package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import reflect.BeanProperty
import java.util.LinkedList
import ru.korus.tmis.core.entity.model.layout.{LayoutAttributeValue, LayoutAttribute}
import scala.collection.JavaConversions._

/**
 * Интерфейс для работы с разметкой медицинских документов
 * Author: idmitriev Systema-Soft
 * Date: 5/15/13 9:52 PM
 * Since: 1.0.1.10
 */
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
  @BeanProperty
  var name: String = ""                                     //Наименование атрибута
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
    this.name = attribute.getName
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
    this.layoutAttribute_id = value.getLayoutAttribute.getId.intValue()
    this.value = value.getValue
  }
}
