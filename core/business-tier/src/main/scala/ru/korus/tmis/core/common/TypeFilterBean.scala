package ru.korus.tmis.core.common

import java.util

import ru.korus.tmis.core.entity.model.ActionType
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.database.common.{DbEventBeanLocal, DbOrgStructureBeanLocal}

@Stateless
class TypeFilterBean
  extends TypeFilterBeanLocal
 {

  @EJB
  var dbEvent: DbEventBeanLocal = _

  @EJB
  var dbOrgStructure: DbOrgStructureBeanLocal = _

  def filterActionTypes(actionTypes: java.util.Set[ActionType],
                        departmentId: Int,
                        eventId: Int): util.HashSet[ActionType] = {
    val filter =
      dbOrgStructure.getActionTypeFilter(departmentId) &
        dbEvent.getActionTypeFilter(eventId)

    val result = new java.util.HashSet[ActionType](actionTypes)
    result.retainAll(filter)
    result
  }
}
