package ru.korus.tmis.core.data

import java.util
import java.util.Date
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter
import javax.xml.bind.annotation.{XmlSeeAlso, XmlRootElement, XmlType}

import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.data.adapters.DateAdapter

import scala.beans.BeanProperty

/**
 * Базовый контейнер для данных
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 10/29/14
 * Time: 6:31 PM
 */
@XmlType(name = "therapiesContainer")
@XmlRootElement(name = "data")
@JsonIgnoreProperties(ignoreUnknown = true)
class ListContainer() {
  @BeanProperty var data: util.List[TherapyContainer] = _
}
