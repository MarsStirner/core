package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.entity.model.{OrgStructure, Action}

class YIncomingRelocationDocument(
    action: Action,
    orgStructureUUID: String
) extends YAbstractActionBasedRelocationDocument(action) {

  val formattedEffectiveDate = formattedBegDate

  val soapOperation = "ProcessHL7v3Message"
  val soapAction = "urn:hl7-org:v3#MISExchange:ProcessHL7v3Message"
  val xsiType = "PRPA_IN402001UV02"

  val xml =
    <PRPA_IN402001UV02
        ITSVersion="XML_1.0"
        xsi:schemaLocation="urn:hl7-org:v3 PRPA_IN402001UV02.xsd"
        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns="urn:hl7-org:v3">
      <id root={xUUID.toString}/>
      <creationTime value={formattedDate}/>
      <interactionId extension="PRPA_IN402001UV02" root="2.16.840.1.113883.1.18"/>
      <processingCode code="P"/>
      <processingModeCode code="T"/>
      <acceptAckCode code="AL"/>
      <receiver typeCode="RCV">
        <device classCode="DEV" determinerCode="INSTANCE">
          <id nullFlavor="NI"/>
        </device>
      </receiver>
      <sender typeCode="SND">
        <device classCode="DEV" determinerCode="INSTANCE">
            <id nullFlavor="NI"/>
        </device>
      </sender>
      <controlActProcess classCode="CACT" moodCode="EVN">
        <subject typeCode="SUBJ">
          <inpatientEncounterEvent classCode="ENC" moodCode="EVN">
            <id root={eventUUID} extension={cardId}/>
            <code codeSystem="2.16.840.1.113883.5.4"
                    codeSystemName="actCode"
                    code="IMP"
                    displayName="Стационар"/>
            <statusCode code="active"/>
            <effectiveTime value={formattedEffectiveDate}/>
            <subject typeCode="SBJ" contextControlCode="OP">
              <patient classCode="PAT">
                <id code={patientUUID}/>
                <patientPerson classCode="PSN" determinerCode="INSTANCE">
                  <!-- <id extension={snils}/> -->
                  <name>
                    <given>{patient.getFirstName}</given>
                    <given>{patient.getPatrName}</given>
                    <family>{patient.getLastName}</family>
                  </name>
                  <administrativeGenderCode code={gender} codeSystem="2.16.840.1.113883.5.1"/>
                  <birthTime value={formattedBirthDate}/>
                </patientPerson>
              </patient>
            </subject>
            <admitter nullFlavor="NI" typeCode="ADM">
              <time nullFlavor="NI"/>
              <assignedPerson classCode="ASSIGNED"/>
            </admitter>
            <location typeCode="LOC">
              <time nullFlavor="NI"/>
              <statusCode code="active"/>
              <serviceDeliveryLocation classCode="SDLOC">
                <id  root={orgStructureUUID}/>
              </serviceDeliveryLocation>
            </location>
          </inpatientEncounterEvent>
        </subject>
      </controlActProcess>
    </PRPA_IN402001UV02>

  override def toXml = xml
}
