package ru.korus.tmis.laboratory.bak

import java.util.{Date, GregorianCalendar}
import javax.ejb.{ActivationConfigProperty, MessageDriven}
import javax.xml.bind.JAXBElement
import javax.xml.datatype.{DatatypeConfigurationException, DatatypeFactory, XMLGregorianCalendar}
import javax.xml.namespace.QName
import javax.xml.ws.Holder

import org.slf4j.{Logger, LoggerFactory}
import ru.korus.tmis.core.entity.model._
import ru.korus.tmis.core.exception.CoreException
import ru.korus.tmis.laboratory.bak.service._
import ru.korus.tmis.laboratory.bak.ws.client.BakSend
import ru.korus.tmis.laboratory.bak.ws.client.handlers.SOAPEnvelopeHandlerResolver
import ru.korus.tmis.lis.data.jms.LISMessageReceiver
import ru.korus.tmis.lis.data.{BiomaterialInfo, DiagnosticRequestInfo, IndicatorMetodic, LaboratoryCreateRequestData, OrderInfo}
import ru.korus.tmis.scala.util.ConfigManager
import ru.korus.tmis.util.Utils
import ru.korus.tmis.util.logs.ToLog
import collection.JavaConversions._

import scala.collection.JavaConverters._

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 7/9/14
 * Time: 4:42 PM
 */
@MessageDriven(
  mappedName = "LaboratoryTopic",
  activationConfig = Array(
    new ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"))
)
class MessageDrivenListener extends LISMessageReceiver {
  private final val logger: Logger = LoggerFactory.getLogger(classOf[MessageDrivenListener])
  private final val ROOT: String = "2.16.840.1.113883.1.3"
  private final val GUID: String = String.valueOf(System.currentTimeMillis / 1000)
  private final val FACTORY_BAK: ObjectFactory = new ObjectFactory
  private final val CUSTODIAN_NAME: String = "ФГБУ &quot;ФНКЦ ДГОИ им. Дмитрия Рогачева&quot; Минздрава России"
  private final val DATE_FORMAT: String = "YYYY-MM-dd HH:mm"
  private final val LAB_CODES: Array[String] = Array( "0102", "0103", "0104", "0105")


  override protected def sendToLis(data: LaboratoryCreateRequestData): Unit = {
    sendRequestToLIS(data)
  }

  override protected def getLaboratoryCode: java.util.List[String] =  LAB_CODES.toSeq

  private def sendRequestToLIS(a: LaboratoryCreateRequestData) {
    logger.info(System.getProperty(BakSend.CGM_BAK_URL_SYSTEM_PROPERTY))
    try {
      val document = createDocument(a)
      logger.info("Query: " + Utils.marshallMessage(document, "ru.korus.tmis.laboratory.bak.service"))
      logger.info("Sending...")
      val service: BakSendService = createCGMService
      val id: Holder[Integer] = new Holder[Integer](1)
      val guid: Holder[String] = new Holder[String](GUID)
      service.queryAnalysis(document, id, guid)
      logger.info("Result id[" + id.value + "], guid [" + guid.value + "]")
    } catch {
      case e: Throwable =>
        logger.error("Sending error: " + e.getMessage, e)
        throw e
    }
  }

  private def createDocument(data: LaboratoryCreateRequestData): HL7Document = {
    val document: HL7Document = new HL7Document
    createTypeId(document)
    createMedDocId(document, data.getRequestInfo)
    document.setCode(null)
    document.setTitle("Заказ лабораторных исследований")
    createEffectiveTimeRequest(document, data.getRequestInfo)
    createConfLevel(document, "N")
    createLanguageDoc(document, "ru-RU")
    createOrderStatus(document, data.getAction)
    createRecordTarget(document, data.getPatient, data.getEvent)
    createDocAuthor(document, data.getAction, data.getRequestInfo)
    createComponentOf(document, data.getPatient)
    createBody(document, data.getBiomaterialInfo, data.getOrderInfo, data.getPatient, data.getRequestInfo, data.getAction, data.getEvent)
    document
  }

  private def createTypeId(document: HL7Document) {
    val typeId: HL7Document.TypeId = new HL7Document.TypeId
    typeId.setExtention("POCD_HD000040")
    typeId.setRoot(ROOT)
    document.setTypeId(typeId)
  }

  private def createMedDocId(document: HL7Document, requestInfo: DiagnosticRequestInfo) {
    val id: HL7Document.Id = new HL7Document.Id
    id.setRoot(ROOT)
    id.setExtention(String.valueOf(requestInfo.getOrderMisId))
    document.setId(id)
  }

  private def createEffectiveTimeRequest(document: HL7Document, requestInfo: DiagnosticRequestInfo) {
    val effectiveTime: HL7Document.EffectiveTime = new HL7Document.EffectiveTime
    effectiveTime.setValue(requestInfo.getOrderMisDate.toString(DATE_FORMAT))
    document.setEffectiveTime(effectiveTime)
  }

  private def createConfLevel(document: HL7Document, levelCode: String) {
    val confidentialityCode: HL7Document.ConfidentialityCode = new HL7Document.ConfidentialityCode
    confidentialityCode.setCode(levelCode)
    confidentialityCode.setCodeSystem("2.16.840.1.113883.5.25")
    document.setConfidentialityCode(confidentialityCode)
  }

  private def createLanguageDoc(document: HL7Document, langCode: String) {
    val languageCode: HL7Document.LanguageCode = new HL7Document.LanguageCode
    languageCode.setCode(langCode)
    document.setLanguageCode(languageCode)
  }

  private def createOrderStatus(document: HL7Document, action: Action) {
    val versionNumber: HL7Document.VersionNumber = new HL7Document.VersionNumber
    versionNumber.setValue(String.valueOf(action.getStatus))
    document.setVersionNumber(versionNumber)
  }

  private def createRecordTarget(document: HL7Document, patientInfo: Patient, event: Event) {
    val recordTarget: RecordTargetInfo = new RecordTargetInfo
    recordTarget.setTypeCode("RCT")
    val patientRole: PatientRoleInfo = new PatientRoleInfo
    patientRole.setClassCode("PAT")
    if(!patientInfo.getClientAddresses.isEmpty) {
      patientRole.setAddr(patientInfo.getClientAddresses.get(0).getFreeInput)
    }
    val patientId: PatientIDInfo = new PatientIDInfo
    patientId.setExtension(String.valueOf(patientInfo.getId))
    patientId.setRoot(GUID)
    patientRole.setId(patientId)
    val patient: PatientInfo = new PatientInfo
    patient.setClassCode("PSN")
    patient.setDeterminerCode("INSTANCE")
    val name: NameInfo = new NameInfo
    name.setName(patientInfo.getFirstName)
    name.setSuffix(patientInfo.getPatrName)
    name.setFamily(patientInfo.getLastName)
    patient.setName(name)
    val administrativeGenderCode: AdministrativeGenderCodeInfo = new AdministrativeGenderCodeInfo
    administrativeGenderCode.setCode(patientInfo.getSex + "")
    patient.setAdministrativeGenderCode(administrativeGenderCode)
    val birthTime: BirthTimeInfo = new BirthTimeInfo
    val c: GregorianCalendar = new GregorianCalendar
    c.setTime(patientInfo.getBirthDate)
    var xmlBirthTime: XMLGregorianCalendar = null
    try {
      xmlBirthTime = DatatypeFactory.newInstance.newXMLGregorianCalendar(c)
    }
    catch {
      case e: DatatypeConfigurationException => logger.error(e.getMessage)
    }
    birthTime.setValue(xmlBirthTime)
    patient.setBirthTime(birthTime)
    patientRole.setPatient(patient)
    val providerOrganization: ProviderOrganizationInfo = new ProviderOrganizationInfo
    providerOrganization.setClassCode("ORG")
    providerOrganization.setDeterminerCode("INSTANCE")
    val orgId: LpuIDInfo = new LpuIDInfo
    val organisation: Organisation = event.getOrganisation
    orgId.setRoot(organisation.getUuid.getUuid)
    providerOrganization.setId(orgId)
    providerOrganization.setName(organisation.getTitle)
    patientRole.setProviderOrganization(providerOrganization)
    recordTarget.setPatientRole(patientRole)
    document.setRecordTarget(recordTarget)
  }

  private def createDocAuthor(document: HL7Document, action: Action, requestInfo: DiagnosticRequestInfo) {
    val author: AuthorInfo = new AuthorInfo
    author.setTypeCode("AUT")
    val execDate: Date = action.getCreateDatetime
    if (execDate != null) {
      val time: DateTimeInfo = new DateTimeInfo
      var xmlTime: XMLGregorianCalendar = null
      val c1: GregorianCalendar = new GregorianCalendar
      c1.setTime(execDate)
      try {
        xmlTime = DatatypeFactory.newInstance.newXMLGregorianCalendar(c1)
      }
      catch {
        case e: DatatypeConfigurationException => logger.error(e.getMessage)
      }
      time.setValue(xmlTime)
      author.setTime(time)
    }
    val assignedAuthor: AssignedAuthorInfo = new AssignedAuthorInfo
    assignedAuthor.setClassCode("ASSIGNED")
    val doctorId: DoctorIdInfo = new DoctorIdInfo
    val docId: Integer = requestInfo.getOrderDoctorMisId
    val doctor: Staff = requestInfo.getOrderDoctorMis
    if (doctor == null) {
      throw new CoreException("Не указан автор назначения")
    }
    doctorId.setExtension(doctor.getId.toString)
    doctorId.setRoot(if (doctor.getUuid != null) doctor.getUuid.getUuid else "")
    assignedAuthor.setId(doctorId)
    val assignedPerson: AssignedPersonInfo = new AssignedPersonInfo
    assignedPerson.setClassCode("PSN")
    assignedPerson.setDeterminerCode("INSTANCE")
    val assignedName: NameInfo = new NameInfo
    assignedName.setName(doctor.getFirstName)
    assignedName.setFamily(doctor.getLastName)
    assignedName.setSuffix(doctor.getPatrName)
    assignedPerson.setName(assignedName)
    assignedAuthor.setAssignedPerson(assignedPerson)
    val representedOrganization: RepresentedOrganizationInfo = new RepresentedOrganizationInfo
    if (doctor.getOrgStructure != null && doctor.getOrgStructure.getUuid != null) {
      val reporgIDInfo: ReporgIDInfo = new ReporgIDInfo
      reporgIDInfo.setRoot(doctor.getOrgStructure.getUuid.getUuid)
      representedOrganization.setId(reporgIDInfo)
    }
    representedOrganization.setName(CUSTODIAN_NAME)
    assignedAuthor.setRepresentedOrganization(representedOrganization)
    author.setAssignedAuthor(assignedAuthor)
    document.setAuthor(author)
  }

  private def createComponentOf(document: HL7Document, patientInfo: Patient) {
    val componentOf: ComponentOfInfo = new ComponentOfInfo
    val encompassingEncounter: EncompassingEncounterInfo = new EncompassingEncounterInfo
    val eeIdInfo: EeIdInfo = new EeIdInfo
    eeIdInfo.setRoot(CUSTODIAN_NAME)
    eeIdInfo.setExtension(patientInfo.getId.toString)
    encompassingEncounter.setId(eeIdInfo)
    val code: EeCodeInfo = new EeCodeInfo
    code.setCodeSystem("2.16.840.1.113883.5.4")
    code.setCodeSystemName("actCode")
    code.setCode("IMP")
    code.setDisplayName("Inpatient encounter")
    encompassingEncounter.setCode(code)
    val effectiveTime: EffectiveTimeInfo = new EffectiveTimeInfo
    effectiveTime.setNullFlavor("NI")
    encompassingEncounter.setEffectiveTime(effectiveTime)
    componentOf.setEncompassingEncounter(encompassingEncounter)
    document.setComponentOf(componentOf)
  }

  private def createBody(document: HL7Document, biomaterialInfo: BiomaterialInfo, orderInfo: OrderInfo, patient: Patient, requestInfo: DiagnosticRequestInfo, action: Action, eventInfo: Event) {
    val component: ComponentInfo = new ComponentInfo
    val structuredBody: StructuredBodyInfo = new StructuredBodyInfo
    val subComponentInfo: SubComponentInfo = FACTORY_BAK.createSubComponentInfo
    subComponentInfo.getEntry.add(EntryFactory.createEntryBiomaterial(biomaterialInfo, action))
    val jaxbElement: JAXBElement[SubComponentInfo] = new JAXBElement[SubComponentInfo](QName.valueOf("component"), classOf[SubComponentInfo], subComponentInfo)
    structuredBody.getContent.add(jaxbElement)
    val subComponentInfo2: SubComponentInfo = FACTORY_BAK.createSubComponentInfo
    val section: SectionInfo = new SectionInfo
    section.getEntry.add(EntryFactory.createEntry(eventInfo.getOrganisation.getUuid.getUuid, "OBS", "RQO", requestInfo.getOrderDepartmentMisCode, requestInfo.getOrderDepartmentName))
    section.getEntry.add(EntryFactory.createEntry(action.getUuid.getUuid, "OBS", "RQO", action.getIsUrgent + "", ""))
    section.getEntry.add(EntryFactory.createEntry("", "OBS", "RQO", requestInfo.getOrderDiagCode, requestInfo.getOrderDiagText))
    section.getEntry.add(EntryFactory.createEntry(eventInfo.getEventType.getFinance.getName, "OBS", "RQO", orderInfo.getDiagnosticCode, orderInfo.getDiagnosticName))
    for (indicatorMetodic: IndicatorMetodic <- orderInfo.getIndicatorList.asScala) {
      section.getEntry.add(EntryFactory.createEntry("", "OBS", "RQO", indicatorMetodic.getCode, indicatorMetodic.getName))
    }
    subComponentInfo2.setSection(section)
    val jaxbElement2: JAXBElement[SubComponentInfo] = new JAXBElement[SubComponentInfo](QName.valueOf("component"), classOf[SubComponentInfo], subComponentInfo2)
    structuredBody.getContent.add(jaxbElement2)
    val subComponentInfo3: SubComponentInfo = FACTORY_BAK.createSubComponentInfo
    val section3: SectionInfo = new SectionInfo
    if (patient.getSex == 2) {
      section3.getEntry.add(EntryFactory.createEntryPregnat("", requestInfo))
    }
    subComponentInfo3.setSection(section3)
    val jaxbElement3: JAXBElement[SubComponentInfo] = new JAXBElement[SubComponentInfo](QName.valueOf("component"), classOf[SubComponentInfo], subComponentInfo3)
    structuredBody.getContent.add(jaxbElement3)
    val subComponentInfo4: SubComponentInfo = FACTORY_BAK.createSubComponentInfo
    val section4: SectionInfo = new SectionInfo
    section4.getEntry.add(EntryFactory.createEntryComment("", action.getTakenTissue.getNote))
    subComponentInfo4.setSection(section4)
    val jaxbElement4: JAXBElement[SubComponentInfo] = new JAXBElement[SubComponentInfo](QName.valueOf("component"), classOf[SubComponentInfo], subComponentInfo4)
    structuredBody.getContent.add(jaxbElement4)
    component.setStructuredBody(structuredBody)
    document.setComponent(component)
  }

  private[laboratory] object EntryFactory {

    private[laboratory] def createEntry(root: String, classCode: String, moodCode: String, code: String, displayName: String): EntryInfo = {
      val entry: EntryInfo = new EntryInfo
      val observation: ObservationInfo = new ObservationInfo
      observation.setClassCode(classCode)
      observation.setMoodCode(moodCode)
      observation.setNegationInd("false")
      val id: ObsIdInfo = new ObsIdInfo
      id.setRoot(root)
      id.setExtension("1")
      observation.setId(id)
      val codeInfo: ObsCodeInfo = new ObsCodeInfo
      codeInfo.setCode(code)
      codeInfo.setDisplayName(displayName)
      observation.setCode(codeInfo)
      entry.setObservation(observation)
      entry
    }

    private[laboratory] def createEntryPregnat(code: String, requestInfo: DiagnosticRequestInfo): EntryInfo = {
      val entry: EntryInfo = new EntryInfo
      val observation: ObservationInfo = new ObservationInfo
      observation.setClassCode("OBS")
      observation.setMoodCode("EVN")
      val codeInfo: ObsCodeInfo = new ObsCodeInfo
      codeInfo.setCode(code)
      val translation: ObsTranslationInfo = new ObsTranslationInfo
      translation.setDisplayName("средний срок беременности, в неделях")
      codeInfo.setTranslation(translation)
      observation.setCode(codeInfo)
      val value: ObsValueInfo = new ObsValueInfo
      value.setUnit("нед")
      value.setValue(requestInfo.getOrderPregnatMin + " ~ " + requestInfo.getOrderPregnatMax)
      observation.setValue(value)
      entry.setObservation(observation)
      entry
    }

    private[laboratory] def createEntryComment(code: String, comment: String): EntryInfo = {
      val entry: EntryInfo = new EntryInfo
      val observation: ObservationInfo = new ObservationInfo
      observation.setClassCode("OBS")
      observation.setMoodCode("EVN")
      val codeInfo: ObsCodeInfo = new ObsCodeInfo
      codeInfo.setCode(code)
      val translation: ObsTranslationInfo = new ObsTranslationInfo
      translation.setDisplayName(comment)
      codeInfo.setTranslation(translation)
      observation.setCode(codeInfo)
      val value: ObsValueInfo = new ObsValueInfo
      value.setValue(comment)
      observation.setValue(value)
      entry.setObservation(observation)
      entry
    }

    private[laboratory] def createEntryBiomaterial(biomaterialInfo: BiomaterialInfo, action: Action): EntryInfo = {
      val entry: EntryInfo = new EntryInfo
      val observation: ObservationInfo = new ObservationInfo
      observation.setClassCode("OBS")
      observation.setMoodCode("ENT")
      val effectiveTime: ObsEffectiveTimeInfo = new ObsEffectiveTimeInfo
      var xmlTime2: XMLGregorianCalendar = null
      val c2: GregorianCalendar = new GregorianCalendar
      c2.setTime(biomaterialInfo.getOrderProbeDate.toDate)
      try {
        xmlTime2 = DatatypeFactory.newInstance.newXMLGregorianCalendar(c2)
      }
      catch {
        case e: DatatypeConfigurationException => // Why is it empty?
      }
      effectiveTime.setValue(xmlTime2)
      observation.setEffectiveTime(effectiveTime)
      entry.setObservation(observation)
      val specimen: SpecimenInfo = new SpecimenInfo
      val specimenRole: SpecimenRoleInfo = new SpecimenRoleInfo
      val srIdInfo: SrIdInfo = new SrIdInfo
      srIdInfo.setRoot(biomaterialInfo.getOrderBarCode)
      specimenRole.setId(srIdInfo)
      val specimenPlayingEntity: SpecimenPlayingEntityInfo = new SpecimenPlayingEntityInfo
      val spCodeInfo: SpCodeInfo = new SpCodeInfo
      spCodeInfo.setCode(biomaterialInfo.getOrderBiomaterialCode)
      val spTranslationInfo: SpTranslationInfo = new SpTranslationInfo
      spTranslationInfo.setDisplayName(biomaterialInfo.getOrderBiomaterialname)
      spCodeInfo.setTranslation(spTranslationInfo)
      specimenPlayingEntity.setCode(spCodeInfo)
      val quantityInfo: SpQuantityInfo = new SpQuantityInfo
      val takenTissue: TakenTissue = action.getTakenTissue
      quantityInfo.setValue(String.valueOf(takenTissue.getAmount))
      specimenPlayingEntity.setQuantity(quantityInfo)
      val text: SpTextInfo = new SpTextInfo
      val unit: RbUnit = takenTissue.getUnit
      if (unit != null) {
        text.setValue(unit.getId.toString)
        specimenPlayingEntity.setText(text)
      }
      val spUnitInfo: SpUnitInfo = new SpUnitInfo
      val tissueType: RbTissueType = takenTissue.getType
      if (tissueType != null) {
        spCodeInfo.setCode(tissueType.getName)
      }
      specimenPlayingEntity.setUnit(spUnitInfo)
      specimenRole.setSpecimenPlayingEntity(specimenPlayingEntity)
      specimen.setSpecimenRole(specimenRole)
      observation.setSpecimen(specimen)
      entry.setObservation(observation)
      entry
    }
  }

  /**
   * Создание CGM-сервиса для запросов в ЛИС
   *
   * @return BakSend - сервис для выполнения запросов
   * @see BakSendService
   */
  private def createCGMService: BakSendService = {
    val BakSend: BakSend = new BakSend
    BakSend.setHandlerResolver(new SOAPEnvelopeHandlerResolver)
    BakSend.getService
  }

}
