package ru.korus.tmis.core.patient

import javax.interceptor.Interceptors
import javax.ejb.{EJB, Stateless}
import ru.korus.tmis.core.database.{DbDiagnosisBeanLocal, DbDiagnosticBeanLocal}
import grizzled.slf4j.Logging
import javax.persistence.{EntityManager, PersistenceContext}
import ru.korus.tmis.core.entity.model.{Diagnostic, Diagnosis, Mkb}
import ru.korus.tmis.core.auth.AuthData
import scala.collection.JavaConversions._
import ru.korus.tmis.core.data.DiagnosesListData
import ru.korus.tmis.scala.util.I18nable
import ru.korus.tmis.core.database.common.DbEventBeanLocal

/**
 * Методы для работы с диагнозами
 * @author idmitriev Systema-Soft
 */
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

  private val ID_CREATE = 0
  private val ID_MODIFY = 1
  private val ID_NONE = 2
  private val ID_MODIFY_WITH_CREATE_DIAGNOSIS = 3

  def insertDiagnosis(diagnosticId: Int,
                      eventId: Int,
                      diaTypeFlatCode: String,
                      diseaseCharacterId: Int,
                      description: String,
                      mkbId: Int,
                      diseaseStageId: Int,
                      userData: AuthData) = {

    val event = dbEventBean.getEventById(eventId)
    val patient = event.getPatient
    var diagnostic: Diagnostic = null
    var diagnosis: Diagnosis = null
    var oldDiagnostic: Diagnostic = null

    val option =
      if(diagnosticId>0) {
        oldDiagnostic = dbDiagnosticBean.getDiagnosticById(diagnosticId)
        if(oldDiagnostic!=null){
          if (oldDiagnostic.getDiagnosis!=null){
              if(oldDiagnostic.getDiagnosis.getMkb.getId.intValue()!=mkbId)   //МКБ разные (история назначений)
                ID_CREATE
              else {
                if(oldDiagnostic.getNotes.compareTo(description)!=0) ID_MODIFY
                else ID_NONE
              }
          }
          else {
            if(mkbId>0) ID_MODIFY_WITH_CREATE_DIAGNOSIS
            else {
              if(oldDiagnostic.getNotes.compareTo(description)!=0) ID_MODIFY
              else ID_NONE
            }
          }
        } else ID_CREATE
      } else ID_CREATE

    option match {
      case ID_CREATE => {
        this.deleteDiagnosis(eventId, diaTypeFlatCode)
        diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(0,
                                                            patient.getId.intValue(),
                                                            diaTypeFlatCode,
                                                            diseaseCharacterId,
                                                            mkbId,
                                                            userData)
        diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic( 0,
                                                                eventId,
                                                                diagnosis,
                                                                diaTypeFlatCode,
                                                                diseaseCharacterId,
                                                                diseaseStageId,
                                                                description,
                                                                userData)
      }
      case ID_MODIFY => {
        diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic( diagnosticId,
                                                                eventId,
                                                                oldDiagnostic.getDiagnosis,
                                                                diaTypeFlatCode,
                                                                diseaseCharacterId,
                                                                diseaseStageId,
                                                                description,
                                                                userData)
      }
      case ID_MODIFY_WITH_CREATE_DIAGNOSIS => {
        diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(0,
                                                            patient.getId.intValue(),
                                                            diaTypeFlatCode,
                                                            diseaseCharacterId,
                                                            mkbId,
                                                            userData)
        diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic( diagnosticId,
                                                                eventId,
                                                                diagnosis,
                                                                diaTypeFlatCode,
                                                                diseaseCharacterId,
                                                                diseaseStageId,
                                                                description,
                                                                userData)
      }
      case _=> {}
    }
    (diagnostic, diagnosis)
  }

  def insertDiagnoses(eventId: Int, mkbs: java.util.Map[String, java.util.Set[AnyRef]], userData: AuthData) = {

    var entities = List.empty[AnyRef]
    mkbs.foreach(f => {
      val set = f._2.asInstanceOf[java.util.Set[(java.lang.Integer, String, java.lang.Integer, java.lang.Integer, java.lang.Integer)]]
      if (set==null || set.size()<=0) {   //Значения пустые для диагноза (помечаем как удалены)
        this.deleteDiagnosis(eventId, f._1)
      }
      else {
        set.foreach(mkb=>{
          val value = insertDiagnosis(mkb._1.intValue(),                                    //diagnosticId
            eventId,
            f._1,
            if (mkb._4.intValue() > 0) mkb._4.intValue() else 3,  // characterId     //3,
            mkb._2,                                               // description
            mkb._3.intValue(),                                    // mkbId
            mkb._5.intValue(),                                    // stageId
            userData)
          if(value._1!= null)
            entities = value._1 :: entities
          if(value._2!= null)
            entities = value._2 :: entities
        })
      }
    })
    entities
  }

  //Спецификация: https://docs.google.com/spreadsheet/ccc?key=0Amfvj7P4xELWdFRJRnR1LVhTdG5BSFZKRnZnNWNlNHc#gid=1
  def getDiagnosesByAppeal (eventId: Int) = {
    val diagnostics = dbDiagnosticBean.getDiagnosticsByEventId(eventId)
    if (diagnostics!=null && diagnostics.size()>0) {
      //(Возможно понадобится)
      //Вернем по одному последнему диагнозу для выбранных типов (согласно спецификации)
      /*Set("assignment", "aftereffect", "attendant", "admission", "clinical", "final").foreach( diaType => {
        val diagnosticsByType = diagnostics.filter(p => p.getDiagnosisType.getFlatCode.compareTo(diaType)==0)
        if (diagnosticsByType!=null && diagnosticsByType.size>0) {
          diagnostics.removeAll(diagnosticsByType)
          val diagnosticByLastDate = diagnosticsByType.find(p => p.getCreateDatetime.getTime ==
                                                                 diagnosticsByType.map(_.getCreateDatetime.getTime)
                                                                                  .foldLeft(Long.MinValue)((i,m)=>m.max(i)))
                                               .getOrElse(null) //Диагностика последняя по дате создания
          if(diagnosticByLastDate!=null) {
            diagnostics.add(diagnosticByLastDate)
          }
        }
      })*/
      //Вывод всех диагнозов с иными мнемониками
      new DiagnosesListData(diagnostics)
    }
    else new DiagnosesListData()
  }

  def deleteDiagnosis(eventId: Int,
                      diaTypeFlatCode: String) = {
    var lastDiag = dbDiagnosticBean.getLastDiagnosticByEventIdAndType(eventId, diaTypeFlatCode)
    if (lastDiag != null) {
      lastDiag.setDeleted(true)
      val diagi = lastDiag.getDiagnosis
      if (diagi != null) {
        diagi.setDeleted(true)
        em.merge(diagi)
      }
      em.merge(lastDiag)
    }
    true
  }
}
