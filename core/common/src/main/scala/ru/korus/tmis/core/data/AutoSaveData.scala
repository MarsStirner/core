package ru.korus.tmis.core.data

import scala.beans.BeanProperty
import java.util.Date
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import ru.korus.tmis.core.entity.model.AutoSaveStorage
import org.codehaus.jackson.annotate.JsonIgnoreProperties

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/22/14
 * Time: 4:02 PM
 */
@XmlType(name = "autoSaveInputData")
@XmlRootElement(name = "autoSaveInputData")
@JsonIgnoreProperties(ignoreUnknown = true)
class AutoSaveInputDataContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var text: String = _

}

@XmlType(name = "autoSaveOutputData")
@XmlRootElement(name = "autoSaveOutputData")
class   AutoSaveOutputDataContainer {

  @BeanProperty
  var id: String = _

  @BeanProperty
  var text: String = _

  @BeanProperty
  var modifyDatetime: Date = _

  def this(id: String, text: String, date: Date) = {
    this()
    this.id = id
    this.text = text
    this.modifyDatetime = date
  }

  def this(data: AutoSaveStorage) = {
    this(data.getId, data.getText, data.getModifyDatetime)
  }

}
