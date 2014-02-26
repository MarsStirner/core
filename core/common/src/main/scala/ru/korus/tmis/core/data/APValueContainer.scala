package ru.korus.tmis.core.data

import ru.korus.tmis.core.entity.model.APValue
import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 2/11/14
 * Time: 8:29 PM
 */
class APValueContainer {

  @BeanProperty
  var valueAsString: String = _

  @BeanProperty
  var valueAsId: String = _


  def this(value: APValue) = {
    this()
    if(value != null) {
      valueAsId = value.getValueAsId
      valueAsString = value.getValueAsString
    }
  }
}
