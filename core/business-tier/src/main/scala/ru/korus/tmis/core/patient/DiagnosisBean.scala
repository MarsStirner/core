package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import ru.korus.tmis.core.logging.LoggingInterceptor
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.database.{DbEventBeanLocal, DbDiagnosisBeanLocal, DbDiagnosticBeanLocal, DbEventPersonBeanLocal}
import grizzled.slf4j.Logging
import ru.korus.tmis.util.I18nable
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Diagnostic, Diagnosis, Mkb}
import ru.korus.tmis.core.auth.AuthData
import scala.collection.JavaConversions._

/**
 * Методы для работы с диагнозами
 * @author idmitriev Systema-Soft
 */
@Interceptors(Array(classOf[LoggingInterceptor]))
@Stateless
class DiagnosisBean  extends DiagnosisBeanLocal
                        with Logging
                        with I18nable {

  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbEventBean: DbEventBeanLocal = _

  @EJB
  var dbDiagnosticBean: DbDiagnosticBeanLocal = _

  @EJB
  var dbDiagnosisBean: DbDiagnosisBeanLocal = _

  def insertOrUpdateDiagnosis(diagnosticId: Int,
                              eventId: Int,
                              diaTypeFlatCode: String,
                              diseaseCharacterId: Int,
                              mkbId: Int,
                              userData: AuthData) = {

    val event = dbEventBean.getEventById(eventId)
    val patient = event.getPatient
    var diagnosis: Diagnosis = null
    var diagnostic: Diagnostic = null


    val diagnosisId = if(diagnosticId>0) {
       dbDiagnosticBean.getDiagnosticById(diagnosticId).getDiagnosis.getId.intValue()
    } else 0

    diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(diagnosisId,
                                                        patient.getId.intValue(),
                                                        diaTypeFlatCode,
                                                        diseaseCharacterId,
                                                        mkbId,
                                                        userData
                                                       )
    if (diagnosis!=null){
      diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic(diagnosticId,
                                                             eventId,
                                                             diagnosis,
                                                             diaTypeFlatCode,
                                                             diseaseCharacterId,
                                                             userData
                                                            )
    }
    (diagnosis, diagnostic)
  }

  def insertDiagnoses(eventId: Int, mkbs: java.util.Map[String, java.util.Set[java.lang.Integer]], userData: AuthData) = {

    var entities = Set.empty[AnyRef]
    mkbs.foreach(f=>f._2.foreach(mkb=>{
      val value = insertOrUpdateDiagnosis(0, eventId, f._1, 3, mkb.intValue(), userData)
      if(value._1!= null)
        entities = entities + value._1
      if(value._2!= null)
        entities = entities + value._2
    }))
    entities
  }

  //TODO: Недоделано. Вопросы по редактированию к Саше.
  def updateDiagnoses(eventId: Int, mkbs: java.util.Map[String, java.util.Set[java.lang.Integer]], userData: AuthData) = {

    var entities = Set.empty[AnyRef]
    val diagnostics = dbDiagnosticBean.getDiagnosticsByEventIdAndTypes(eventId, mkbs.keySet())
    mkbs.foreach(f=>{
       //Получаем диагностики для этого эвента и rbDiagnosisType.flatCode = f._1
       val list = diagnostics.filter(p=> p.getDiagnosisType.getFlatCode.compareTo(f._1)==0).toList
       if (list.size == f._2.size){  //update diagnosis
         f._2.foreach(mkb=>{

         })
       }
       else if (list.size > f._2.size){ //update + delete diagnosis

       }
       else {  //update + insert diagnosis

       }
    })
    entities
  }

}
