package ru.korus.tmis.core.database

import common.{DbRbBloodTypeBeanLocal, DbPatientBeanLocal}
import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.BloodHistory
import scala.collection.JavaConversions._
import ru.korus.tmis.core.auth.AuthData
import ru.korus.tmis.core.exception.CoreException
import java.util.Date
import ru.korus.tmis.scala.util.{I18nable, ConfigManager}
import scala.language.reflectiveCalls

@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DbBloodHistoryBean extends DbBloodHistoryBeanLocal
                            with Logging
                            with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbPatientBean: DbPatientBeanLocal = _
  @EJB
  var dbRbBloodTypeBean: DbRbBloodTypeBeanLocal = _

  def getBloodHistoryByPatient(patientId: Int) = {
    val result = em.createNamedQuery("BloodHistory.findByPatientId", classOf[BloodHistory])
                   .setParameter("patientId", patientId)
                   .getResultList
    result.foreach(em.detach(_))
    result
  }

  def createBloodHistoryRecord(patientId: Int, bloodTypeId: Int, bloodDate: Date, userData: AuthData) = {
     try {
       if(patientId>0){
         if (bloodTypeId>0){
           if(userData!=null){
             val patient = dbPatientBean.getPatientById(patientId)
             val bloodType = dbRbBloodTypeBean.getRbBloodTypeById(bloodTypeId)

             val record = new BloodHistory
             if(bloodDate!=null)
               record.setBloodDate(bloodDate)
             else
               record.setBloodDate(new Date())
             record.setPatient(patient)
             record.setBloodType(bloodType)
             record.setPerson(userData.getUser)

             record
           }
           else {
             throw new CoreException(ConfigManager.ErrorCodes.InvalidAuthData,
                                     "createBloodHistoryRecord >>> " +
                                       i18n("error.staffNotFound"))
           }
         }
         else {
           throw new CoreException(ConfigManager.ErrorCodes.BloodTypeIsNull,
                                   "createBloodHistoryRecord >>> " +
                                     i18n("error.bloodTypeIsNull" +
                                       ": Не задана группа крови"))
         }
       }
       else {
         throw new CoreException(ConfigManager.ErrorCodes.PatientIsNull,
                                 "createBloodHistoryRecord >>> " +
                                   i18n("error.patientIsNull" + ": " +
                                     "Не задан пациент, которому назначается группа крови."))
       }
     }  catch {
       case e: Exception => {
         throw new CoreException(-1, e.getMessage())
       }
     }
  }
}
