package ru.korus.tmis.core.data

import javax.xml.bind.annotation.{XmlRootElement, XmlType}
import org.codehaus.jackson.annotate.JsonIgnoreProperties
import ru.korus.tmis.core.entity.model.Action
import scala.beans.BeanProperty
import java.util.Date

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 12/9/13
 * Time: 7:01 PM
 */
@XmlType(name = "actionDataContainer")
@XmlRootElement(name = "actionDataContainer")
@JsonIgnoreProperties(ignoreUnknown = true)
class ActionDataContainer {

  @BeanProperty
  var id: Int = _

  @BeanProperty
  var createDateTime: Date = _

  @BeanProperty
  var createPerson: PersonIdNameContainer = _

  @BeanProperty
  var modifyDateTime: Date = _

  @BeanProperty
  var modifyPerson: PersonIdNameContainer = _

  @BeanProperty
  var deleted: Boolean = _

  @BeanProperty
  var actionTypeId: Int = _

  @BeanProperty
  var eventId: Int = _

  @BeanProperty
  var directionDate: Date = _

  @BeanProperty
  var status:Short = _

  @BeanProperty
  var begDate: Date = _

  @BeanProperty
  var plannedEndDate: Date = _

  @BeanProperty
  var endDate: Date = _

  def this(action: Action) {
    this()
    id = action.getId
    createDateTime = action.getCreateDatetime
    createPerson = new PersonIdNameContainer(action.getCreatePerson)
    modifyDateTime = action.getModifyDatetime
    modifyPerson = new PersonIdNameContainer(action.getModifyPerson)
    deleted = action.getDeleted
    actionTypeId = action.getActionType.getId
    eventId = action.getEvent.getId
    directionDate = action.getDirectionDate
    status = action.getStatus
    begDate = action.getBegDate
    plannedEndDate = action.getPlannedEndDate
    endDate = action.getEndDate
  }
}
