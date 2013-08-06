package ru.korus.tmis.ws

import ru.korus.tmis.core.entity.model._
import java.util.Date
import java.util

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 8/2/13
 * Time: 11:02 AM
 * To change this template use File | Settings | File Templates.
 */
class TestDataEntityImpl extends TestDataEntity {

  final val testId = 1

  def  getTestDefaultAction(actionId: Int) =  {
    val action = new Action(actionId)

    action.setEvent(getTestDefaultEvent())
    action.setAssigner(getTestDefaultStaff())
    action.setIsUrgent(false)
    action.setActionType(getTestDefaultActionType)
    action.setTakenTissue(getTestDefaultTakenTissue)

    action
  }

  def  getTestAction(actionId: Int, event: Event, assigner: Staff, atype: ActionType, ttissue: TakenTissue, urgent: Boolean) =  {
    val action = getTestDefaultAction(actionId)

    action.setEvent(event)
    action.setAssigner(assigner)
    action.setIsUrgent(urgent)
    action.setActionType(atype)
    action.setTakenTissue(ttissue)

    action
  }

  def getTestDefaultTakenTissue = {
     val tt = new TakenTissue(testId)
     tt.setBarcode(1)
     tt
  }

  def getTestDefaultTakenTissue(id: Int) = {
    val tt = new TakenTissue(id)
    tt.setBarcode(1)
    tt
  }

  def getTestDefaultActionType() = {
    val at = new ActionType(testId)
    at.setTestTubeType(getTestDefaultTestTubeType)
    at
  }

  def getTestDefaultTestTubeType = {
    val ttt  = new RbTestTubeType()
    ttt.setId(testId)
    ttt.setName("test name")
    ttt.setVolume(0.00)
    ttt.setCovCol("test covCol")
    ttt.setColor("test Color")
    ttt
  }

  def getTestDefaultEvent() =  {
    val event = new Event(testId)
    event.setPatient(getTestDefaultPatient)
    event.setExternalId("test_external_id")
    event
  }

  def getTestDefaultEvent(eventId: Int) =  {
    val event = getTestDefaultEvent
    event.setId(eventId)
    event
  }

  def getTestDefaultStaff() =  {
    val person = new Staff(testId)
    person.setLastName("Test last name")
    person.setFirstName("Test first name")
    person.setPatrName("Test middle name")
    person
  }

  def getTestDefaultStaff(id: Int) =  {
    val person = getTestDefaultStaff
    person.setId(id)
    person
  }

  def getTestDefaultPatient() =  {
    val patient = new Patient(testId)
    patient.setLastName("Test last name")
    patient.setFirstName("Test first name")
    patient.setPatrName("Test middle name")
    patient.setSex(0)
    patient.setBirthDate(new Date(0))
    patient
  }

  def getTestDefaultPatient(id: Int) =  {
    val patient= getTestDefaultPatient
    patient.setId(id)
    patient
  }

  def getTestDefaultJob() =  {
    val job = new Job()
    job.setId(testId)
    job.setOrgStructure(getTestDefaultOrgStructure)
    job
  }

  def getTestDefaultJob(id: Int) =  {
    val job= getTestDefaultJob
    job.setId(id)
    job
  }

  def getTestJob(id: Int, org: OrgStructure) =  {
    val job= getTestDefaultJob(id)
    job.setOrgStructure(org)
    job
  }

  def getTestDefaultJobTicket() =  {
    val job_ticket= new JobTicket()
    job_ticket.setId(testId)
    job_ticket.setJob(getTestDefaultJob)
    job_ticket.setDatetime(new Date(0))
    job_ticket.setStatus(0)
    job_ticket.setNote("test note")
    job_ticket.setLabel("test label")
    job_ticket
  }

  def getTestDefaultJobTicket(id: Int) =  {
    val job_ticket= getTestDefaultJobTicket
    job_ticket.setId(id)
    job_ticket
  }

  def getTestJobTicket(id: Int, job: Job, date: Date, status: Int, label: String, note: String) =  {
    val job_ticket= getTestDefaultJobTicket(id)
    job_ticket.setJob(job)
    job_ticket.setDatetime(date)
    job_ticket.setStatus(status)
    job_ticket.setNote(note)
    job_ticket.setLabel(label)
    job_ticket
  }

  def getTestDefaultOrgStructure() =  {
    val org = new OrgStructure(testId)
    org.setName("test name")
    org
  }

  def getTestDefaultOrgStructure(id: Int) =  {
    val org= getTestDefaultOrgStructure
    org.setId(id)
    org
  }

  def getTestDefaultActionTypeTissueType() =  {
    val att = new ActionTypeTissueType()
    att.setId(testId)
    att.setAmount(0)
    att.setUnit(getTestDefaultRbUnit)
    att.setTissueType(getTestDefaultRbTissueType())
    att
  }

  def getTestDefaultActionTypeTissueType(id: Int) =  {
    val attt= getTestDefaultActionTypeTissueType
    attt.setId(id)
    attt
  }

  def getTestActionTypeTissueType(id: Int, amount: Int, tissueType: RbTissueType, unit: RbUnit) = {
    val attt= getTestDefaultActionTypeTissueType(id)
    attt.setAmount(amount)
    attt.setUnit(unit)
    attt.setTissueType(tissueType)
    attt
  }

  def getTestDefaultRbTissueType() =  {
    val tt = new RbTissueType(testId)
    tt.setName("test name")
    tt
  }

  def getTestDefaultRbTissueType(id: Int) =  {
    val tt= getTestDefaultRbTissueType
    tt.setId(id)
    tt
  }

  def getTestRbTissueType(id: Int, name: String) = {
    val tt= getTestDefaultRbTissueType(id)
    tt.setName(name)
    tt
  }

  def getTestDefaultRbUnit() =  {
    val unit = new RbUnit(testId)
    unit.setName("test name")
    unit
  }

  def getTestDefaultRbUnit(id: Int) =  {
    val unit= getTestDefaultRbUnit
    unit.setId(id)
    unit
  }

  def getTestRbUnit(id: Int, name: String) = {
    val unit= getTestDefaultRbUnit(id)
    unit.setName(name)
    unit
  }

  def getTestDefaultOrgStructureHospitalBed() = {
    val bed = new OrgStructureHospitalBed(testId)
    bed.setName("test name")
    bed.setCode("test code")
    bed.setMasterDepartment(getTestDefaultOrgStructure)
    bed
  }

  def getTestDefaultOrgStructureHospitalBed(id: Int) = {
    val bed = getTestDefaultOrgStructureHospitalBed
    bed.setId(id)
    bed
  }

  def getTestDefaultActionProperty() = {
    val ap = new ActionProperty(testId)
    ap
  }

  def getTestDefaultActionPropertyWithValues() = {
    val ap = new ActionProperty(testId)
    val values = new util.LinkedList[APValue]()
    (ap, values)
  }

  def getTestDefaultActionPropertyWithValues(id: Int, values: util.List[APValue]) = {
    val apWithValues =  getTestDefaultActionPropertyWithValues
    apWithValues._1.setId(id)
    apWithValues._2.addAll(values)
    apWithValues
  }

  def getTestDefaultAPValueHospitalBed = {
    val apBed = new APValueHospitalBed()
    apBed.setValue(getTestDefaultOrgStructureHospitalBed)
    apBed
  }

  def getTestDefaultAPValueHospitalBed(id: Int) = {
    val apBed = new APValueHospitalBed()
    apBed.setValue(getTestDefaultOrgStructureHospitalBed(id))
    apBed
  }

}
