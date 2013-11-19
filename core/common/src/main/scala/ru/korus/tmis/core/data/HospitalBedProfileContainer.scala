package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.map.annotate.JsonView
import ru.korus.tmis.core.entity.model.RbHospitalBedProfile
import reflect.BeanProperty

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 11/19/13
 * Time: 12:35 PM
 */

/**
 * Контейнер данных, описывающих профиль койки
 */
@XmlType(name = "hospitalBedProfileData")
@XmlRootElement(name = "hospitalBedProfileData")
class HospitalBedProfileContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var code: String = _

  @BeanProperty
  var serviceId: Int = _

  def this(profile: RbHospitalBedProfile) = {
    this()
    id = profile.getId.intValue
    name = profile.getName
    code = profile.getCode
    if(profile.getServiceId != null)
      serviceId = profile.getServiceId.intValue
  }

}

class HospitalBedProfilesListContainer {

  @BeanProperty
  var profiles: java.util.ArrayList[HospitalBedProfileContainer] =
    new java.util.ArrayList[HospitalBedProfileContainer]

  def this(profiles: java.lang.Iterable[RbHospitalBedProfile]) = {
    this()
    val it = profiles.iterator()
    while(it.hasNext) {
      this.profiles.add(new HospitalBedProfileContainer(it.next()))
    }
  }

}
