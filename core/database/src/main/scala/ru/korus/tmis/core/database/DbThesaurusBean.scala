package ru.korus.tmis.core.database

import ru.korus.tmis.core.logging.db.LoggingInterceptor

import grizzled.slf4j.Logging
import javax.ejb.Stateless
import javax.interceptor.Interceptors
import javax.persistence.{EntityManager, PersistenceContext}

import scala.collection.JavaConversions._
import scala.collection.mutable.HashMap
import scala.collection.mutable.Map
import scala.collection.mutable.Set
import ru.korus.tmis.core.entity.model.{Thesaurus, Mkb}

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbThesaurusBean
  extends DbThesaurusBeanLocal
  with Logging {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  def getMkb = {
    em.createNamedQuery("Mkb.findAll",
                        classOf[Mkb])
    .getResultList
  }

  def getThesaurus = {
    em.createNamedQuery("Thesaurus.findAll",
                        classOf[Thesaurus])
    .getResultList
  }

  def getThesaurusByCode(code: Int): java.util.List[Thesaurus] = {
    val ts = getThesaurus();
    val tsm = ts.foldLeft(new HashMap[Int, Set[Thesaurus]])(
      (m, t) => {
        t.getGroupId match {
          case null => m
          case _ => {
            m.get(t.getGroupId.intValue) match {
              case None => {
                m.put(t.getGroupId.intValue, Set[Thesaurus](t))
                m
              }
              case Some(tt: Set[Thesaurus]) => {
                tt.add(t)
                m
              }
            }
          }
        }
      })

    val codeStr = code.toString()
    val opt = ts.find(t => (codeStr == t.getCode) && (t.getGroupId == null))
    opt match {
      case None => List.empty[Thesaurus]
      case Some(t: Thesaurus) => {
        t :: recThesaurus(t.getId.intValue, tsm)
      }
    }
  }

  def recThesaurus(grpId: Int, tsm: Map[Int, Set[Thesaurus]]): List[Thesaurus] = {
    val optChildren = tsm.get(grpId)
    val children = optChildren match {
      case None => return List.empty[Thesaurus]
      case Some(tt: Set[Thesaurus]) => tt.toList
    }

    val grandChildren = children.foldLeft(List.empty[Thesaurus])(
      (l, t) => recThesaurus(t.getId.intValue, tsm) ::: l)
    children ::: grandChildren
  }
}
