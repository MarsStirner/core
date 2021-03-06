package ru.korus.tmis.core.database

import common.DbManagerBeanLocal
import scala.collection.JavaConversions._
import javax.interceptor.Interceptors

import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{FlushModeType, EntityManager, PersistenceContext}
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.core.entity.model.{ActionTypeTissueType, Action, Event, TakenTissue}
import java.util.Date
import javax.annotation.Nullable
import ru.korus.tmis.scala.util.{CAPids, I18nable, ConfigManager}
import ru.korus.tmis.core.auth.AuthStorageBeanLocal
import scala.language.reflectiveCalls

/**
 * Методы для работы с TakenTissueJournal
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */


@Stateless
class DbTakenTissueBean extends DbTakenTissueBeanLocal
                        with Logging
                        with I18nable
                        with CAPids {

    @PersistenceContext(unitName = "s11r64")
    var em: EntityManager = _

    @EJB
    var appLock: AuthStorageBeanLocal = _

    @EJB
    private var dbManager: DbManagerBeanLocal = _

  def insertOrUpdateTakenTissue(id: Int, action: Action): TakenTissue = {
    var tissue: TakenTissue = null
    if (id > 0) {
      tissue = getTakenTissueById(id)
    }
    else {
      tissue = new TakenTissue
      tissue.setPatient(action.getEvent.getPatient)
      tissue.setExternalId(action.getEvent.getExternalId)
    }
    tissue.setNote("")
    tissue.setAmount(0)
    tissue.setDatetimeTaken(action.getPlannedEndDate)
    tissue.setType(getActionTypeTissueTypeByMasterId(action.getActionType.getId.intValue()).getTissueType)
    tissue
  }

  @Nullable
  def getActionTypeTissueTypeByMasterId(actionTypeId: Int) = {
    val result = em.createQuery(ActionTypeTissueTypeByMasterIdQuery, classOf[ActionTypeTissueType])
      .setParameter("actionTypeId", actionTypeId)
      .getResultList

    result.size match {
      case 0 => {
        null
      }
      case size => {

        result(0)
      }
    }
  }

  @Nullable
  private def getLastTakenTissueJournalBarCode = {
     val result = em.createQuery(LastTakenTissueQuery, classOf[TakenTissue])
      .getSingleResult

    if (result != null) {

      result
    } else {
      null
    }
  }

  def getTakenTissueById(id: Int): TakenTissue = {
    val result = em.createQuery(TakenTissueByIdQuery, classOf[TakenTissue])
      .setParameter("id", id)
      .getResultList

    result.size match {
      case 0 => {
        throw new CoreException(
          ConfigManager.ErrorCodes.TakenTissueNotFound,
          i18n("error.TakenTissueNotFound").format(id))
      }
      case size => {

        result(0)
      }
    }
  }

  val TakenTissueByIdQuery =
    """
      SELECT tt
      FROM
        TakenTissue tt
      WHERE
        tt.id = :id
    """

  val LastTakenTissueQuery =
    """
      SELECT tt
      FROM
        TakenTissue tt
      WHERE
        tt.id = (SELECT MAX(tt2.id)
          FROM TakenTissue tt2)
    """

  val ActionTypeTissueTypeByMasterIdQuery =
    """
      SELECT attt
      FROM
        ActionTypeTissueType attt
      WHERE
        attt.actionType.id = :actionTypeId
    """
}
