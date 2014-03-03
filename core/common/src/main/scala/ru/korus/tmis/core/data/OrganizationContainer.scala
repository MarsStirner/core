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
class OrganizationContainer(org: Organisation) {

  @BeanProperty
  var id: Integer = org.getId

  @BeanProperty
  var fullName: String = org.getFullName

  @BeanProperty
  var shortName: String = org.getShortName

  @BeanProperty
  var title: String = org.getTitle

  @BeanProperty
  var infisCode: String = org.getInfisCode

  @BeanProperty
  var obsoleteInfisCOde: String = org.getObsoleteInfisCode

  @BeanProperty
  var OKVED: String = org.getOkved

  @BeanProperty
  var INN: String = org.getInn

  @BeanProperty
  var KPP: String = org.getKpp

  @BeanProperty
  var OGRN: String = org.getOgrn

  @BeanProperty
  var OKATO: String = org.getOkato

  @BeanProperty
  var OKPO: String = org.getOkpo

  @BeanProperty
  var FSS: String = org.getFss

  @BeanProperty
  var address: String = getAddress

  @BeanProperty
  var chief: String = getChief

  @BeanProperty
  var phone: String = getPhone

  @BeanProperty
  var accountant: String = getAccountant

}
