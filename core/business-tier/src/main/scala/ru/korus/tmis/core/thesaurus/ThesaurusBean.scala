package ru.korus.tmis.core.thesaurus

import ru.korus.tmis.core.data._
import ru.korus.tmis.core.database.{DbVersionBeanLocal, DbThesaurusBeanLocal}
import ru.korus.tmis.core.entity.model.{Mkb, Thesaurus}
import ru.korus.tmis.core.logging.db.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.{EJB, Stateless}
import javax.interceptor.Interceptors

import scala.collection.JavaConversions._

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class ThesaurusBean
  extends ThesaurusBeanLocal
  with Logging {

  @EJB
  var dbThesaurusBean: DbThesaurusBeanLocal = _

  @EJB
  var dbVersion: DbVersionBeanLocal = _

  def getThesaurusByCode(code: Int) = {
    buildThesaurusData(dbThesaurusBean.getThesaurusByCode(code))
  }

  def getThesaurus() = {
    buildThesaurusData(dbThesaurusBean.getThesaurus)
  }

  def getMkb() = {
    buildMkbData(dbThesaurusBean.getMkb)
  }

  def buildThesaurusData(ts: java.util.List[Thesaurus]) = {
    ts.foldLeft(new ThesaurusData(dbVersion.getGlobalVersion))(
      (data, t) => {
        val groupId = t.getGroupId match {
          case null => 0
          case groupId => groupId.intValue
        }

        val e = new ThesaurusEntry(t.getId.intValue,
                                   groupId,
                                   t.getCode,
                                   t.getName,
                                   t.getTemplate)

        data.add(e)
        data
      })
  }

  def buildMkbData(mkbs: java.util.List[Mkb]) = {
    mkbs.foldLeft(new ThesaurusData(dbVersion.getGlobalVersion))(
      (data, mkb) => {
        data add new ThesaurusEntry(mkb.getId.intValue,
                                    0,
                                    mkb.getDiagID,
                                    mkb.getDiagName,
                                    null)
        data
      })
  }
}
