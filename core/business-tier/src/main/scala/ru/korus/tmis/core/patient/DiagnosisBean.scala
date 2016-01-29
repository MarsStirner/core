package ru.korus.tmis.core.patient

import java.util.Date
import javax.ejb.{EJB, Stateless}
import javax.persistence.{EntityManager, PersistenceContext}

import grizzled.slf4j.Logging
import org.apache.commons.lang.ObjectUtils
import ru.korus.tmis.core.data.DiagnosesListData
import ru.korus.tmis.core.database.common.DbEventBeanLocal
import ru.korus.tmis.core.database.{DbDiagnosisBeanLocal, DbDiagnosticBeanLocal}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.scala.util.I18nable

import scala.collection.JavaConversions._

/**
 * Методы для работы с диагнозами
 * @author idmitriev Systema-Soft
 */
@Stateless
class DiagnosisBean extends DiagnosisBeanLocal
with Logging
with I18nable {

  private val ID_CREATE = 0
  private val ID_MODIFY = 1
  private val ID_NONE = 2
  private val ID_MODIFY_WITH_CREATE_DIAGNOSIS = 3


  @PersistenceContext(unitName = "s11r64")
  var em: EntityManager = _

  @EJB
  var dbEventBean: DbEventBeanLocal = _

  @EJB
  var dbDiagnosticBean: DbDiagnosticBeanLocal = _

  @EJB
  var dbDiagnosisBean: DbDiagnosisBeanLocal = _

  /**
   * Получить диагностику по ее идентифкатору
   * @param id  идентифкатор диагностики
   * @return сущность \ null если не найдено
   */
  override def getDiagnostic(id: Int): Diagnostic = {
    if (id > 0) em.find(classOf[Diagnostic], id) else null
  }


  def insertDiagnosis(diagnosticId: Int,
                      eventId: Int,
                      action: Action,
                      diaTypeFlatCode: String,
                      diseaseCharacterId: Int,
                      description: String,
                      mkbId: Int,
                      diseaseStageId: Int,
                      userData: Staff) = {
    logger.info("Insert diagnosis :: %d, %s, %s, %d, %s, %d, %d".format(eventId, action, diaTypeFlatCode, diseaseCharacterId, description, mkbId, diseaseStageId))
    val event = dbEventBean.getEventById(eventId)
    val patient = event.getPatient
    var diagnostic: Diagnostic = null
    var diagnosis: Diagnosis = null
    var oldDiagnostic: Diagnostic = null
    val listOldDiag = getOldDiagByActionAndType(action, diaTypeFlatCode)

    var isNewDiag = false
    val option =
      if (diagnosticId > 0) {
        oldDiagnostic = dbDiagnosticBean.getDiagnosticById(diagnosticId)
        if (oldDiagnostic != null) {
          isNewDiag = oldDiagnostic.getDiagnosis == null || oldDiagnostic.getDiagnosis.getMkb.getId.intValue() != mkbId
          if (oldDiagnostic.getDiagnosis != null) {
            if (oldDiagnostic.getDiagnosis.getMkb.getId.intValue() != mkbId) //МКБ разные (история назначений)
              ID_CREATE
            else {
              if (oldDiagnostic.getNotes.compareTo(description) != 0) ID_MODIFY
              else ID_NONE
            }
          }
          else {
            if (mkbId > 0) ID_MODIFY_WITH_CREATE_DIAGNOSIS
            else {
              if (oldDiagnostic.getNotes.compareTo(description) != 0) ID_MODIFY
              else ID_NONE
            }
          }
        } else ID_CREATE
      } else if (listOldDiag.isEmpty) {
        ID_CREATE
      } else {
        oldDiagnostic = listOldDiag.get(0)
        isNewDiag = oldDiagnostic.getDiagnosis == null || oldDiagnostic.getDiagnosis.getMkb.getId.intValue() != mkbId
        ID_MODIFY
      }
    option match {
      case ID_CREATE => {
        this.deleteDiagnosis(eventId, diaTypeFlatCode)
        diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(0,
          patient.getId.intValue(),
          diaTypeFlatCode,
          diseaseCharacterId,
          mkbId,
          userData)
        if (diagnosis != null) {
          diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic(0,
            eventId,
            action,
            diagnosis,
            diaTypeFlatCode,
            diseaseCharacterId,
            diseaseStageId,
            description,
            userData,
            true)
        }
      }
      case ID_MODIFY => {
        diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(oldDiagnostic.getDiagnosis.getId,
          patient.getId.intValue(),
          diaTypeFlatCode,
          diseaseCharacterId,
          mkbId,
          userData)
        diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic(oldDiagnostic.getId,
          eventId,
          action,
          oldDiagnostic.getDiagnosis,
          diaTypeFlatCode,
          diseaseCharacterId,
          diseaseStageId,
          description,
          userData,
          isNewDiag)
      }
      case ID_MODIFY_WITH_CREATE_DIAGNOSIS => {
        diagnosis = dbDiagnosisBean.insertOrUpdateDiagnosis(0,
          patient.getId.intValue(),
          diaTypeFlatCode,
          diseaseCharacterId,
          mkbId,
          userData)
        diagnostic = dbDiagnosticBean.insertOrUpdateDiagnostic(diagnosticId,
          eventId,
          action,
          diagnosis,
          diaTypeFlatCode,
          diseaseCharacterId,
          diseaseStageId,
          description,
          userData,
          isNewDiag)
      }
      case _ => {}
    }
    (diagnostic, diagnosis)
  }

  def insertDiagnoses(eventId: Int, action: Action, mkbs: java.util.Map[String, java.util.Set[AnyRef]], userData: Staff) = {

    var entities = List.empty[AnyRef]
    mkbs.foreach(f => {
      val set = f._2.asInstanceOf[java.util.Set[(java.lang.Integer, String, java.lang.Integer, java.lang.Integer, java.lang.Integer)]]
      if (set == null || set.size() <= 0) {
        //Значения пустые для диагноза (помечаем как удалены)
        this.deleteDiagnosis(eventId, f._1)
      }
      else {
        set.foreach(mkb => {
          val value = insertDiagnosis(mkb._1.intValue(), //diagnosticId
            eventId,
            action,
            f._1,
            if (mkb._4.intValue() > 0) mkb._4.intValue() else 3, // characterId     //3,
            mkb._2, // description
            mkb._3.intValue(), // mkbId
            mkb._5.intValue(), // stageId
            userData)
          if (value._1 != null)
            entities = value._1 :: entities
          if (value._2 != null)
            entities = value._2 :: entities
        })
      }
    })
    entities
  }

  //Спецификация: https://docs.google.com/spreadsheet/ccc?key=0Amfvj7P4xELWdFRJRnR1LVhTdG5BSFZKRnZnNWNlNHc#gid=1
  def getDiagnosesByAppeal(eventId: Int) = {
    val diagnostics = dbDiagnosticBean.getDiagnosticsByEventId(eventId)
    if (diagnostics != null && diagnostics.size() > 0) {
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

  def getOldDiagByActionAndType(action: Action, diaTypeFlatCode: String): java.util.List[Diagnostic] = {
    em.createNamedQuery("Diagnostic.findByActionIdAndType", classOf[Diagnostic])
      .setParameter("actionId", if (action == null) 0 else action.getId)
      .setParameter("flatCode", diaTypeFlatCode)
      .getResultList
  }

  def deleteDiagnosis(eventId: Int,
                      diaTypeFlatCode: String) = {
    val lastDiag = dbDiagnosticBean.getLastDiagnosticByEventIdAndType(eventId, diaTypeFlatCode)
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

  /**
   * Запись нового диагноза
   * @param staff Пользователь, создающий диагноз
   * @param client Пациент, которому ставиться новый диагноз
   * @param diagnosisType Тип диагноза
   * @param character Характер заболевания
   * @param mkb Запись из справочника МКБ
   * @return созданный диагноз  (after persist)
   */
  override def insertDiagnosis(staff: Staff, client: Patient, diagnosisType: RbDiagnosisType, character: RbDiseaseCharacter, mkb: Mkb): Diagnosis = {
    val result: Diagnosis = new Diagnosis
    val now: Date = new Date
    result.setCreateDatetime(now)
    result.setCreatePerson(staff)
    result.setModifyDatetime(now)
    result.setModifyPerson(staff)
    result.setDeleted(false)
    result.setPatient(client)
    result.setDiagnosisType(diagnosisType)
    result.setCharacter(character)
    result.setMkb(mkb)
    result.setMkbExCode("")
    result.setDispanser(null)
    result.setTraumaType(null)
    result.setSetDate(null)
    result.setEndDate(now)
    result.setModId(null)
    result.setPerson(staff)
    em.persist(result)
    result
  }

  override def modifyDiagnosis(staff: Staff, client: Patient, diagnosisType: RbDiagnosisType, character: RbDiseaseCharacter, mkb: Mkb, oldVersion: Diagnosis): Diagnosis = {
    if (oldVersion == null) {
      insertDiagnosis(staff, client, diagnosisType, character, mkb)
    } else {
      //Флажок, указывающий что хотя-бы одно из полей нужно записать в БД
      var changed = false
      if (!ObjectUtils.equals(oldVersion.getMkb, mkb)) {
        changed = true
        oldVersion.setMkb(mkb)
      }
      if (!ObjectUtils.equals(oldVersion.getPerson, staff)) {
        changed = true
        oldVersion.setPerson(staff)
      }
      if (!ObjectUtils.equals(oldVersion.getCharacter, character)) {
        changed = true
        oldVersion.setCharacter(character)
      }
      if (!ObjectUtils.equals(oldVersion.getPatient, client)) {
        //TODO это ведь невозможно!, может стоить бросать исключение
        changed = true
        oldVersion.setPatient(client)
      }
      if (!ObjectUtils.equals(oldVersion.getDiagnosisType, diagnosisType)) {
        //TODO это ведь невозможно!, может стоить бросать исключение
        changed = true
        oldVersion.setDiagnosisType(diagnosisType)
      }
      if (changed) {
        oldVersion.setModifyDatetime(new Date)
        oldVersion.setModifyPerson(staff)
        em.merge(oldVersion)
      } else {
        oldVersion
      }
    }
  }

  /**
   * Запись новой дианостики
   * @param staff Пользователь, создающий дианостику
   * @param event Обращение, в рамках которого создается диагностика
   * @param action Действие, в рамках которого создается диагностика
   * @param diagnosis Диагноз на который должна ссылаться диагностика
   * @param diagnosisType Тип диагноза
   * @param character Характер заболевания
   * @param description Описание диагностики
   * @return созданный диагностика (after persist)
   */
  override def insertDiagnostic(staff: Staff, event: Event, action: Action, diagnosis: Diagnosis, diagnosisType: RbDiagnosisType, character: RbDiseaseCharacter, description: String): Diagnostic = {
    val now: Date = new Date
    val result: Diagnostic = new Diagnostic
    result.setCreateDatetime(now)
    result.setCreatePerson(staff)
    result.setModifyDatetime(now)
    result.setModifyPerson(staff)
    result.setDeleted(false)
    result.setEvent(event)
    result.setDiagnosis(diagnosis)
    result.setDiagnosisType(diagnosisType)
    result.setCharacter(character)
    result.setStage(null)
    result.setPhase(null)
    result.setDispanser(null)
    result.setSanatorium(0)
    result.setHospital(0)
    result.setTraumaType(null)
    result.setSpeciality(staff.getSpeciality)
    result.setPerson(staff)
    result.setHealthGroup(null)
    result.setResult(null)
    result.setSetDate(now)
    result.setEndDate(null)
    result.setNotes(description)
    result.setAcheResult(null)
    result.setAction(action)
    em.persist(result)
    result
  }

  override def modifyDiagnostic(
                                 staff: Staff,
                                 event: Event,
                                 action: Action,
                                 diagnosis: Diagnosis,
                                 diagnosisType: RbDiagnosisType,
                                 character: RbDiseaseCharacter,
                                 description: String,
                                 oldVersion: Diagnostic): Diagnostic = {
    if (oldVersion == null) {
      insertDiagnostic(staff, event, action, diagnosis, diagnosisType, character, description)
    } else {
      //Флажок, указывающий что хотя-бы одно из полей нужно записать в БД
      var changed = false
      if (!ObjectUtils.equals(oldVersion.getEvent, event)) {
        //TODO это ведь невозможно!, может стоить бросать исключение
        changed = true
        oldVersion.setEvent(event)
      }
      if (!ObjectUtils.equals(oldVersion.getAction, action)) {
        changed = true
        oldVersion.setAction(action)
      }
      if (!ObjectUtils.equals(oldVersion.getNotes, description)) {
        changed = true
        oldVersion.setNotes(description)
      }
      if (!ObjectUtils.equals(oldVersion.getPerson, staff)) {
        changed = true
        oldVersion.setPerson(staff)
        oldVersion.setSpeciality(if (staff != null ) staff.getSpeciality else null)
      }
      if (!ObjectUtils.equals(oldVersion.getCharacter, character)) {
        changed = true
        oldVersion.setCharacter(character)
      }
      if (!ObjectUtils.equals(oldVersion.getDiagnosis, diagnosis)) {
        changed = true
        oldVersion.setDiagnosis(diagnosis)
      }
      if (!ObjectUtils.equals(oldVersion.getDiagnosisType, diagnosisType)) {
        //TODO это ведь невозможно!, может стоить бросать исключение
        changed = true
        oldVersion.setDiagnosisType(diagnosisType)
      }
      if (changed) {
        oldVersion.setModifyDatetime(new Date)
        oldVersion.setModifyPerson(staff)
        em.merge(oldVersion)
      } else {
        oldVersion
      }
    }
  }

  override def deleteDiagnosis(event: Event, diagnosticId: Int): Diagnostic = {
     val diagnostic = if(diagnosticId > 0) em.find(classOf[Diagnostic], diagnosticId) else null
    if(diagnostic == null) {
      null
    } else {
      if(event == null || event.equals(diagnostic.getEvent) || !diagnostic.isDeleted){
        // небольшая проверка на то что удаляемая диагностика относится к нашему обращению или обращение не задано, или у диагностики уже стоит флаг удаления
        diagnostic.setDeleted(true)
        em.merge(diagnostic)
        val diagnosis = diagnostic.getDiagnosis
        if(!diagnosis.getDeleted) {
          diagnosis.setDeleted(true)
          em.merge(diagnosis)
        }
      }
      diagnostic
    }
  }
}
