package ru.korus.tmis.ws.webmis.rest

import javax.xml.bind.annotation.XmlType
import beans.BeanProperty

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        03.06.14, 18:29 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
class LockData {

  @BeanProperty
  var id: Integer = _

  def this(id: Integer) {
    this()
    this.id = id
  }
}
