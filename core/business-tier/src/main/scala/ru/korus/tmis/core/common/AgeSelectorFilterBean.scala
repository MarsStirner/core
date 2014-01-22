package ru.korus.tmis.core.common

import ru.korus.tmis.core.logging.LoggingInterceptor


import grizzled.slf4j.Logging
import javax.ejb.{Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._
import ru.korus.tmis.core.entity.model.{Event, ActionType}

import ru.korus.tmis.core.age.AgeSelector

import ru.korus.tmis.scala.util.CloneUtils
import ru.korus.tmis.core.database.common.DbOrgStructureBeanLocal

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class AgeSelectorFilterBean
  extends AgeSelectorFilterBeanLocal
  with Logging {


  def filterActionTypes(actionTypes: java.util.Set[ActionType],
                        event: Event): java.util.Set[ActionType] = {
    val cal = event.getPatient.getBirthDate

    CloneUtils.clone(actionTypes).filter {
      case it =>
        AgeSelector(it.getAge).check(cal)
    }.map {
      case it =>
        it.getActionPropertyTypes.retainAll(it.getActionPropertyTypes.filter {
          apt => AgeSelector(apt.getAge).check(cal)
        })
        it
    }
  }
}
