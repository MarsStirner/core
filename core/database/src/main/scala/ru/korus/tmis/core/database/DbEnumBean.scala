package ru.korus.tmis.core.database

import ru.korus.tmis.core.entity.model.{Nomenclature, RbAnalysisStatus, DbEnumerable}

import javax.annotation.{Resource, PostConstruct}
import javax.ejb._
import javax.persistence.{EntityManager, PersistenceContext}
import javax.transaction.UserTransaction

import grizzled.slf4j.Logging
import scala.collection.JavaConversions._
import ru.korus.tmis.scala.util.I18nable

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
class DbEnumBean
  extends DbEnumBeanLocal
  with Logging
  with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var s11r64: EntityManager = _

  @Resource
  var tx: UserTransaction = _

  var enums: List[(String, Class[_ <: DbEnumerable])] =
    List[(String, Class[_ <: DbEnumerable])](
      ("s11r64", classOf[RbAnalysisStatus])
    )

  @PostConstruct
  def init() = {
    syncEnums_s11r64()
  }

  def syncEnums_s11r64() = {
    syncEnums(s11r64, "s11r64")
  }

  def syncEnums(em: EntityManager, EmId: String) = {
    tx.begin()
    try {
      enums.synchronized {
        enums.foreach(tuple => {
          val emId = tuple._1
          val e = tuple._2
          val ee = e.asInstanceOf[Class[DbEnumerable]]
          emId match {
            case EmId => processEnums(em, ee)
            case _ => {}
          }
        })
      }
      tx.commit()
    } catch {
      case ex: Exception => {
        tx.rollback()
      }
    }
  }

  def processEnums(em: EntityManager, e: Class[DbEnumerable]) = {
    val enums = em
      .createNamedQuery(e.getSimpleName + ".findAll")
      .getResultList
      .map(_.asInstanceOf[DbEnumerable])

    info("Reloading DB enums of " + e.getSimpleName)
    e.newInstance().loadEnums(enums)
  }
}
