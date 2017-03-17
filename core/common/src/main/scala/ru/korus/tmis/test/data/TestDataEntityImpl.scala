package ru.korus.tmis.test.data

import java.util

import ru.korus.tmis.core.entity.model._
import java.util.Date

import kladr.{Kladr, Street}
import ru.korus.tmis.core.auth.AuthData

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 8/2/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
class TestDataEntityImpl extends TestDataEntity {

  final val testId = 1

  def  getTestDefaultAction(actionId: Int): Action =  {
    val action = new Action(actionId)
    val date = new Date()
    action.setEvent(getTestDefaultEvent())
    action.setAssigner(getTestDefaultStaff())
    action.setIsUrgent(false)
    action.setActionType(getTestDefaultActionType)
    action.setTakenTissue(getTestDefaultTakenTissue)
    action.setCreateDatetime(date)
    action.setBegDate(date)
    action.setPlannedEndDate(date)
    action.setEndDate(date)

    action
  }

  def  getTestDefaultAction(actionId: Int, at: ActionType): Action =  {
    val action = getTestDefaultAction(actionId)
    action.setActionType(at)
    action
  }

  def  getTestAction(actionId: Int, event: Event, assigner: Staff, atype: ActionType, ttissue: TakenTissue, urgent: Boolean): Action =  {
    val action = getTestDefaultAction(actionId)

    action.setEvent(event)
    action.setAssigner(assigner)
    action.setIsUrgent(urgent)
    action.setActionType(atype)
    action.setTakenTissue(ttissue)

    action
  }

  def getTestDefaultTakenTissue: TakenTissue = {
     val tt = new TakenTissue(testId)
     tt.setBarcode(1)
     tt
  }

  def getTestDefaultTakenTissue(id: Int): TakenTissue = {
    val tt = new TakenTissue(id)
    tt.setBarcode(1)
    tt
  }

  def getTestDefaultActionType(): ActionType = {
    val at = new ActionType(testId)
    at.setTestTubeType(getTestDefaultTestTubeType)
    at.setCode("")
    at.setFlatCode("test_flatCode")
    at
  }

  def getTestDefaultActionType(id: Int, flatCode: String): ActionType = {
    val at = getTestDefaultActionType
    at.setId(id)
    at.setFlatCode(flatCode)
    at
  }

  def getTestDefaultTestTubeType: RbTestTubeType = {
    val ttt  = new RbTestTubeType()
    ttt.setId(testId)
    ttt.setName("test name")
    ttt.setVolume(0.00)
    ttt.setCovCol("test covCol")
    ttt.setColor("test Color")
    ttt
  }

  def getTestDefaultEvent(): Event =  {
    val event = new Event(testId)
    event.setPatient(getTestDefaultPatient)
    event.setExternalId("test_external_id")
    event
  }

  def getTestDefaultEvent(eventId: Int): Event =  {
    val event = getTestDefaultEvent
    event.setId(eventId)
    event
  }

  def getTestDefaultStaff(): Staff =  {
    val person = new Staff(testId)
    person.setLastName("Test last name")
    person.setFirstName("Test first name")
    person.setPatrName("Test middle name")
    person
  }

  def getTestDefaultStaff(id: Int): Staff =  {
    val person = getTestDefaultStaff
    person.setId(id)
    person
  }

  def getTestDefaultPatient(): Patient =  {
    val patient = new Patient(testId)
    patient.setLastName("Test last name")
    patient.setFirstName("Test first name")
    patient.setPatrName("Test middle name")
    patient.setSex(0)
    patient.setBirthDate(new Date(0))
    patient
  }

  def getTestDefaultPatient(id: Int): Patient =  {
    val patient= getTestDefaultPatient
    patient.setId(id)
    patient
  }

  def getTestDefaultJob(): Job =  {
    val job = new Job()
    job.setId(testId)
    job.setOrgStructure(getTestDefaultOrgStructure)
    job
  }

  def getTestDefaultJob(id: Int): Job =  {
    val job= getTestDefaultJob
    job.setId(id)
    job
  }

  def getTestJob(id: Int, org: OrgStructure): Job =  {
    val job= getTestDefaultJob(id)
    job.setOrgStructure(org)
    job
  }

  def getTestDefaultJobTicket(): JobTicket =  {
    val job_ticket= new JobTicket()
    job_ticket.setId(testId)
    job_ticket.setJob(getTestDefaultJob)
    job_ticket.setDatetime(new Date(0))
    job_ticket.setStatus(0)
    job_ticket.setNote("test note")
    job_ticket.setLabel("test label")
    job_ticket
  }

  def getTestDefaultJobTicket(id: Int): JobTicket =  {
    val job_ticket= getTestDefaultJobTicket
    job_ticket.setId(id)
    job_ticket
  }

  def getTestJobTicket(id: Int, job: Job, date: Date, status: Int, label: String, note: String): JobTicket =  {
    val job_ticket= getTestDefaultJobTicket(id)
    job_ticket.setJob(job)
    job_ticket.setDatetime(date)
    job_ticket.setStatus(status)
    job_ticket.setNote(note)
    job_ticket.setLabel(label)
    job_ticket
  }

  def getTestDefaultOrgStructure(): OrgStructure =  {
    val org = new OrgStructure(testId)
    org.setName("test name")
    org
  }

  def getTestDefaultOrgStructure(id: Int): OrgStructure =  {
    val org= getTestDefaultOrgStructure
    org.setId(id)
    org
  }

  def getTestDefaultActionTypeTissueType(): ActionTypeTissueType =  {
    val att = new ActionTypeTissueType()
    att.setId(testId)
    att.setAmount(0)
    att.setUnit(getTestDefaultRbUnit)
    att.setTissueType(getTestDefaultRbTissueType())
    att
  }

  def getTestDefaultActionTypeTissueType(id: Int): ActionTypeTissueType =  {
    val attt= getTestDefaultActionTypeTissueType
    attt.setId(id)
    attt
  }

  def getTestActionTypeTissueType(id: Int, amount: Int, tissueType: RbTissueType, unit: RbUnit): ActionTypeTissueType = {
    val attt= getTestDefaultActionTypeTissueType(id)
    attt.setAmount(amount)
    attt.setUnit(unit)
    attt.setTissueType(tissueType)
    attt
  }

  def getTestDefaultRbTissueType(): RbTissueType =  {
    val tt = new RbTissueType(testId)
    tt.setName("test name")
    tt
  }

  def getTestDefaultRbTissueType(id: Int): RbTissueType =  {
    val tt= getTestDefaultRbTissueType
    tt.setId(id)
    tt
  }

  def getTestRbTissueType(id: Int, name: String): RbTissueType = {
    val tt= getTestDefaultRbTissueType(id)
    tt.setName(name)
    tt
  }

  def getTestDefaultRbUnit(): RbUnit =  {
    val unit = new RbUnit(testId)
    unit.setName("test name")
    unit
  }

  def getTestDefaultRbUnit(id: Int): RbUnit =  {
    val unit= getTestDefaultRbUnit
    unit.setId(id)
    unit
  }

  def getTestRbUnit(id: Int, name: String): RbUnit = {
    val unit= getTestDefaultRbUnit(id)
    unit.setName(name)
    unit
  }

  def getTestDefaultOrgStructureHospitalBed(): OrgStructureHospitalBed = {
    val bed = new OrgStructureHospitalBed(testId)
    bed.setName("test name")
    bed.setCode("test code")
    bed.setMasterDepartment(getTestDefaultOrgStructure)
    bed
  }

  def getTestDefaultOrgStructureHospitalBed(id: Int): OrgStructureHospitalBed = {
    val bed = getTestDefaultOrgStructureHospitalBed
    bed.setId(id)
    bed
  }

  def getTestDefaultActionProperty(): ActionProperty = {
    val ap = new ActionProperty(testId)
    ap
  }

  def getTestDefaultActionProperty(id: Int): ActionProperty = {
    val ap = getTestDefaultActionProperty
    ap.setId(id)
    ap
  }

  def getTestDefaultActionProperty(id: Int, apt: ActionPropertyType): ActionProperty = {
    val ap = getTestDefaultActionProperty(id)
    ap.setType(apt)
    ap
  }

  def getTestDefaultActionPropertyWithValues(): (ActionProperty, util.LinkedList[APValue]) = {
    val ap = new ActionProperty(testId)
    val values = new java.util.LinkedList[APValue]()
    (ap, values)
  }

  def getTestDefaultActionPropertyWithValues(id: Int, values: java.util.List[APValue]): (ActionProperty, util.LinkedList[APValue]) = {
    val apWithValues =  getTestDefaultActionPropertyWithValues
    apWithValues._1.setId(id)
    apWithValues._2.addAll(values)
    apWithValues
  }

  def getTestDefaultAPValueHospitalBed: APValueHospitalBed = {
    val apBed = new APValueHospitalBed()
    apBed.setValue(getTestDefaultOrgStructureHospitalBed)
    apBed
  }

  def getTestDefaultAPValueHospitalBed(id: Int): APValueHospitalBed = {
    val apBed = new APValueHospitalBed()
    apBed.setValue(getTestDefaultOrgStructureHospitalBed(id))
    apBed
  }

  def getTestDefaultAPValueMkb: APValueMKB = {
    val mkb = new APValueMKB()
    mkb.setValue(1.toString)
    mkb
  }

  def getTestDefaultAPValueMkb(id: Int): APValueMKB = {
    val mkb = new APValueMKB()
    mkb.setValue(id.toString)
    mkb
  }

  def getTestDefaultAPValueOrgStructure: APValueOrgStructure = {
    val apBed = new APValueOrgStructure()
    apBed.setValue(getTestDefaultOrgStructure)
    apBed
  }

  def getTestDefaultAPValueOrgStructure(id: Int): APValueOrgStructure = {
    val apBed = new APValueOrgStructure()
    apBed.setValue(getTestDefaultOrgStructure(id))
    apBed
  }

  def getTestDefaultAPValueTime: APValueTime = {
    val apBed = new APValueTime()
    apBed.setValue(new Date())
    apBed
  }

  def getTestDefaultAPValueTime (time: Date): APValueTime = {
    val apBed = new APValueTime ()
    apBed.setValue(time)
    apBed
  }

  def getTestDefaultAPValueString: APValueString = {
    val apStr = new APValueString()
    apStr.setValue("test_string")
    apStr
  }

  def getTestDefaultAPValueString (str: String): APValueString = {
    val apStr = new APValueString()
    apStr.setValue(str)
    apStr
  }

  def getTestDefaultActionPropertyType(id: Int, code: String): ActionPropertyType = {
    val apt = new ActionPropertyType(id)
    apt.setCode(code)
    apt
  }

  def getTestDefaultEventPerson(id: Int, event: Event, person: Staff): EventPerson = {
    val ep = new EventPerson()
    ep.setId(id)
    ep.setEvent(event)
    ep.setPerson(person)
    ep.setBegDate(new Date())
    ep.setEndDate(new Date())
    ep
  }

  def getTestDefaultBloodHistory(id: Int, bloodType: RbBloodType, person: Staff): BloodHistory = {
    val bh = new BloodHistory()
    bh.setId(id)
    bh.setBloodType(bloodType)
    bh.setPerson(person)
    bh.setBloodDate(new Date(0))
    bh
  }

  def getTestDefaultRbBloodType(id: Int): RbBloodType = {
    val bt = new RbBloodType()
    bt.setId(id)
    bt
  }

  def  getTestDefaultDiagnostic(): Diagnostic = {
    val dia = new Diagnostic(testId)
    dia.setDiagnosisType(getTestDefaultDiagnosisType)
    dia.setTraumaType(getTestDefaultRbTraumaType)
    dia.setNotes("test_notes")
    dia.setDiagnosis(getTestDefaultDiagnosis)
    dia
  }

  def  getTestDefaultDiagnostic(id: Int): Diagnostic = {
    val dia = getTestDefaultDiagnostic
    dia.setId(id)
    dia
  }

  def getTestDefaultDiagnosisType: RbDiagnosisType = {
    val dt = new RbDiagnosisType(testId)
    dt.setName("test_name")
    dt.setFlatCode("test_flatcode")
    dt
  }

  def getTestDefaultRbTraumaType: RbTraumaType = {
    val tt = new RbTraumaType(testId)
    tt.setName("test_name")
    tt
  }

  def getTestDefaultDiagnosis: Diagnosis = {
    val ds = new Diagnosis(testId)
    ds.setMkb(getTestDefaultMkb)
    ds
  }

  def getTestDefaultMkb: Mkb = {
    val mkb = new Mkb(testId)
    mkb.setDiagID("test_diagId")
    mkb.setDiagName("test_diagName")
    mkb
  }

  def getTestDefaultMkb(id: Int): Mkb = {
    val mkb = getTestDefaultMkb
    mkb.setId(id)
    mkb
  }

  def getTestDefaultKladr(): Kladr = {
    val kladr = new Kladr()
    kladr.setCode("testCode")
    kladr.setName("test_streetName")
    kladr
  }

  def getTestDefaultStreet(): Street = {
    val street = new Street()
    street.setCode("testCode")
    street.setName("test_streetName")
    street
  }

}
