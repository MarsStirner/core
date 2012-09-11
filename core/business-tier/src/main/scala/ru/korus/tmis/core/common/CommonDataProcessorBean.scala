package ru.korus.tmis.core.common

import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database._
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.event._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.logging.db.LoggingInterceptor
import ru.korus.tmis.util.{I18nable, StringId, ConfigManager}

import grizzled.slf4j.Logging
import java.lang.String
import javax.ejb.{Stateless, EJB}
import javax.enterprise.event.Event
import javax.enterprise.inject.Any
import javax.inject.Inject
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class CommonDataProcessorBean
  extends CommonDataProcessorBeanLocal
  with Logging
  with I18nable {

  type CA = CommonAttribute

  val AWI = ConfigManager.AWI
  val APWI = ConfigManager.APWI

  @EJB
  var appLock: AppLockBeanLocal = _

  @EJB
  var dbAction: DbActionBeanLocal = _

  @EJB
  var dbActionType: DbActionTypeBeanLocal = _

  @EJB
  var dbActionProperty: DbActionPropertyBeanLocal = _

  @EJB
  var dbManager: DbManagerBeanLocal = _

  @EJB
  var dbVersion: DbVersionBeanLocal = _

  //////////////////////////////////////////////////////////////////////////////

  @Inject
  @Any
  var actionEvent: Event[Notification] = _

  //////////////////////////////////////////////////////////////////////////////

  def createActionForEventFromCommonData(eventId: Int,
                                         data: CommonData,
                                         userData: AuthData): java.util.List[Action] = {

    if (data == null) {
      throw new CoreException(ConfigManager.ErrorCodes.InvalidCommonData,
                              i18n("error.invalidCommonData"))
    }

    var result = List.empty[Action]
    var entities = Set.empty[AnyRef]

    data.entity.foreach(entity => {
      try {

        val action = dbAction.createAction(eventId,
                                           entity.id.intValue,
                                           userData)
        val actionType = dbActionType.getActionTypeById(entity.id.intValue)
        val aw = new ActionWrapper(action)

        // Collect all ActionProperties sent from the client
        val aps = entity.group.foldLeft(List.empty[CommonAttribute])(
          (list, group) => list ++ group.attribute)

        // Create sent APs
        val apList = aps.foldLeft(
          List.empty[(ActionProperty, CommonAttribute)]
        )((list, attribute) => {
          if (AWI.isSupported(attribute.name)) {
            list
          } else {
            val ap = dbActionProperty.createActionProperty(
              action,
              attribute.id.intValue,
              userData)
            new ActionPropertyWrapper(ap).set(attribute)
            (ap, attribute) :: list
          }
        })

        // Create empty APs not sent from the client
        val emptyApts = actionType.getActionPropertyTypes
        emptyApts.removeAll(apList.map(_._1.getType))

        val emptyApList = emptyApts.map(
          apt => {
            dbActionProperty.createActionProperty(
              action,
              apt.getId.intValue,
              userData)
          })

        entities = entities + action
        dbManager.persistAll(List(action))

        // Save sent AP values
        val apvList = apList.foldLeft(
          List.empty[APValue]
        )((list, entry) => {
          val (ap, attribute) = entry
          (attribute.properties.get("valueId"), attribute.properties.get("value")) match {
            case (None | Some(null) | Some(""), None | Some(null) | Some("")) => {
              val apv = dbActionProperty.setActionPropertyValue(
                ap,
                ap.getType.getDefaultValue)
              apv :: list
            }
            case (None | Some(null) | Some(""), Some(value)) => {
              val apv = dbActionProperty.setActionPropertyValue(
                ap,
                value)
              apv :: list
            }
            case (Some(valueId), _) => {
              val apv = dbActionProperty.setActionPropertyValue(
                ap,
                valueId)
              apv :: list
            }
          }
        })

        entities = (entities /: apvList)(_ + _)
        dbManager.persistAll(apvList)

        // Save empty AP values (set to default values)
        val emptyApvList = emptyApList.map(
          ap => {
            dbActionProperty.setActionPropertyValue(
              ap,
              ap.getType.getDefaultValue)
          })

        entities = (entities /: emptyApvList)(_ + _)
        dbManager.persistAll(emptyApvList)

        // Set AWI values
        aps.foreach(attribute => {
          if (AWI.isSupported(attribute.name)) {
            aw.set(attribute)
          }
        })

        val updatedAction =
          dbManager.mergeAll[Action](List(action)).iterator().next()
        entities = entities - action + updatedAction

        result = updatedAction :: result

      } catch {
        case e: Exception => {
          dbManager.removeAll(entities)
          throw e
        }
      }
    })

    val r = dbManager.detachAll[Action](result).toList
    r.foreach(a => {
      val values = dbActionProperty.getActionPropertiesByActionId(a.getId.intValue)
      actionEvent.fire(new CreateActionNotification(a, values))
    })
    return r
  }

  def modifyActionFromCommonData(actionId: Int,
                                 data: CommonData,
                                 userData: AuthData): java.util.List[Action] = {

    if (data == null) {
      throw new CoreException(ConfigManager.ErrorCodes.InvalidCommonData,
                              i18n("error.invalidCommonData"))
    }

    var result = List[Action]();
    var entities = Set[AnyRef]()

    val oldAction = Action.clone(dbAction.getActionById(actionId))
    val oldValues = dbActionProperty.getActionPropertiesByActionId(oldAction.getId.intValue)
    val lockId = appLock.acquireLock("Action",
                                     actionId,
                                     oldAction.getIdx,
                                     userData);

    try {
      data.entity.filter(_.id == actionId).foreach(entity => {
        val a = dbAction.updateAction(entity.id.intValue,
                                      entity.version.intValue,
                                      userData)
        val aw = new ActionWrapper(a)

        // очистить часы
        a.resetAssignmentHours()

        result = a :: result
        entities = entities + a

        val aps = entity.group.foldLeft(List.empty[CommonAttribute])(
          (list, group) => list ++ group.attribute)

        aps.foreach(attribute => {
          val id = attribute.id

          if (AWI.isSupported(attribute.name)) {
            aw.set(attribute)
          } else {
            (attribute.properties.get("valueId"), attribute.properties.get("value")) match {

              case (None | Some(null) | Some(""), None | Some(null)) => {
                val ap = dbActionProperty.getActionPropertyById(
                  id.intValue)
                new ActionPropertyWrapper(ap).set(attribute)
                entities = entities + ap
              }

              case (None | Some(null) | Some(""), Some(value)) => {
                val ap = dbActionProperty.updateActionProperty(
                  id.intValue,
                  attribute.version.intValue,
                  userData)
                val apv = dbActionProperty.setActionPropertyValue(
                  ap,
                  value)
                new ActionPropertyWrapper(ap).set(attribute)
                entities = entities + ap + apv.unwrap
              }

              case (Some(valueId), _) => {
                val ap = dbActionProperty.updateActionProperty(
                  id.intValue,
                  attribute.version.intValue,
                  userData)
                val apv = dbActionProperty.setActionPropertyValue(
                  ap,
                  valueId)
                new ActionPropertyWrapper(ap).set(attribute)
                entities = entities + ap + apv.unwrap
              }

            }
          }
        })
      })

      result = dbManager
               .mergeAll(entities)
               .filter(result.contains(_))
               .map(_.asInstanceOf[Action])
               .toList

      val r = dbManager.detachAll[Action](result).toList
      r.foreach(newAction => {
        val newValues = dbActionProperty.getActionPropertiesByActionId(newAction.getId.intValue)
        actionEvent.fire(new ModifyActionNotification(oldAction,
                                                      oldValues,
                                                      newAction,
                                                      newValues))
      })
      return r

    } finally {
      appLock.releaseLock(lockId)
    }
  }

  def changeActionStatus(eventId: Int,
                         actionId: Int,
                         status: Short) = {
    val action = dbAction.updateActionStatus(actionId, status)
    val r = dbManager.mergeAll[Action](List(action))

    val ActionStatus = ConfigManager.ActionStatus.immutable
    
    status match {
      case ActionStatus.Canceled => {
        r.foreach(a => {
          val values = dbActionProperty.getActionPropertiesByActionId(a.getId.intValue)
          actionEvent.fire(new CancelActionNotification(a, values))
        })
      }
    }

    true
  }

  def fromActionTypes(types: java.util.Set[ActionType],
                      typeName: String,
                      converter: (ActionPropertyType) => CommonAttribute) = {
    types.foldLeft(
      new CommonData(0, dbVersion.getGlobalVersion)
    )((data, at) => {
      val entity = new CommonEntity(at.getId,
                                    0,
                                    at.getName,
                                    typeName,
                                    at.getGroupId,
                                    null,
                                    at.getCode)
      val group = new CommonGroup
      dbActionType.getActionTypePropertiesById(at.getId.intValue).foreach(
        (apt) => group add converter(apt)
      )
      data add (entity add group)
    })
  }

  def fromActions(actions: java.util.List[Action],
                  actionName: String,
                  converters: java.util.List[(Action) => CommonGroup]) = {
    actions.foldLeft(
      new CommonData
    )((data, action) => {
      data add converters.foldLeft(
        new CommonEntity(action.getId,
                         action.getVersion,
                         action.getActionType.getName,
                         actionName,
                         action.getActionType.getId,
                         action.getStatus,
                         action.getActionType.getCode)
      )((entity, converter) => entity add converter(action))
    })
  }

  def addAttributes(group: CommonGroup,
                    wrapper: ActionWrapper,
                    attributeNames: java.util.List[StringId]) = {
    attributeNames.foldLeft(group)(
      (group, attributeName) => group add wrapper.get(attributeName)
    )
  }
}
