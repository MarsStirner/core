package ru.korus.tmis.core.common

import ru.korus.tmis.core.entity.model.ActionType

import grizzled.slf4j.Logging
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.database.common.{DbOrgStructureBeanLocal, DbEventBeanLocal}

@Stateless
class TypeFilterBean
  extends TypeFilterBeanLocal
  with Logging {

  @EJB
  var dbEvent: DbEventBeanLocal = _

  @EJB
  var dbOrgStructure: DbOrgStructureBeanLocal = _

  def filterActionTypes(actionTypes: java.util.Set[ActionType],
                        departmentId: Int,
                        eventId: Int) = {
    val filter =
      dbOrgStructure.getActionTypeFilter(departmentId) &
        dbEvent.getActionTypeFilter(eventId)

    val result = new java.util.HashSet[ActionType](actionTypes)
    result.retainAll(filter)
    result
  }
}
