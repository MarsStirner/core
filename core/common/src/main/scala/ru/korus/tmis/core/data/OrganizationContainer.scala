package ru.korus.tmis.core.data

import ru.korus.tmis.core.entity.model.Organisation
import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import scala.beans.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 3/3/14
 * Time: 6:34 PM
 */
@XmlType(name = "OrganizationContainer")
@XmlRootElement(name = "OrganizationContainer")
class OrganizationContainer {

  @BeanProperty
  var id: Integer = _

  @BeanProperty
  var fullName: String = _

  @BeanProperty
  var shortName: String = _

  @BeanProperty
  var title: String = _

  @BeanProperty
  var infisCode: String = _

  @BeanProperty
  var obsoleteInfisCOde: String = _

  @BeanProperty
  var OKVED: String = _

  @BeanProperty
  var INN: String = _

  @BeanProperty
  var KPP: String = _

  @BeanProperty
  var OGRN: String = _

  @BeanProperty
  var OKATO: String = _

  @BeanProperty
  var OKPO: String = _

  @BeanProperty
  var FSS: String = _

  @BeanProperty
  var address: String = _

  @BeanProperty
  var chief: String = _

  @BeanProperty
  var phone: String = _

  @BeanProperty
  var accountant: String = _

  def this(org: Organisation) = {
    this()
    this.id = org.getId
    this.fullName = org.getFullName
    this.shortName = org.getShortName
    this.title = org.getTitle
    this.infisCode = org.getInfisCode
    this.obsoleteInfisCOde = org.getObsoleteInfisCode
    this.OKVED = org.getOkved
    this.INN = org.getInn
    this.KPP = org.getKpp
    this.OGRN = org.getOgrn
    this.OKATO = org.getOkato
    this.OKPO = org.getOkpo
    this.FSS = org.getFss
    this.address = org.getAddress
    this.chief = org.getChief
    this.phone = org.getPhone
    this.accountant = org.getAccountant
  }


}
