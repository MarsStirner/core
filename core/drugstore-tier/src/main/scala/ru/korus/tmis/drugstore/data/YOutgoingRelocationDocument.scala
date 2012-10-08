package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.entity.model.Action

class YOutgoingRelocationDocument(
                                   action: Action
                                   ) extends YAbstractActionBasedRelocationDocument(action) {

  val formattedEffectiveDate = formattedBegDate

  val soapOperation = "ProcessHL7v3Message"
  val soapAction = "urn:hl7-org:v3#MISExchange:ProcessHL7v3Message"
  val xsiType = "PRPA_IN402003UV02"

  val xml =
    <PRPA_IN402003UV02
    ITSVersion="XML_1.0"
    xsi:schemaLocation="urn:hl7-org:v3 PRPA_IN402003UV02.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="urn:hl7-org:v3">
      <id root={xUUID}/>
      <creationTime value={formattedDate}/>
      <interactionId extension="PRPA_IN402003UV02" root="2.16.840.1.113883.1.18"/>
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
            <statusCode code="completed"/>
            <effectiveTime nullFlavor='NI'/>
            <subject typeCode="SBJ">
              <patient classCode="PAT">
                <id code={patientUUID.toString}/>
                <patientPerson>
                  <id extension={snils}/>
                  <name>
                    <!-- ФИО пациента -->
                    <given>
                      {patient.getFirstName}
                    </given>
                    <given>
                      {patient.getPatrName}
                    </given>
                    <family>
                      {patient.getLastName}
                    </family>
                  </name>
                  <administrativeGenderCode code={gender} codeSystem="2.16.840.1.113883.5.1"/>
                  <birthTime value={formattedBirthDate}/>
                </patientPerson>
              </patient>
            </subject>
          </inpatientEncounterEvent>
        </subject>
      </controlActProcess>
    </PRPA_IN402003UV02>

  override def toXml = xml
}
