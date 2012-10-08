package ru.korus.tmis.drugstore.data

import ru.korus.tmis.core.entity.model.Action

class YMovingRelocationDocument(
                                 action: Action,
                                 fromUUID: String,
                                 toUUID: String
                                 ) extends YAbstractActionBasedRelocationDocument(action) {

  val soapOperation = "ProcessHL7v3Message"
  val soapAction = "urn:hl7-org:v3#MISExchange:ProcessHL7v3Message"
  val xsiType = "PRPA_IN302011UV02"

  val xml =
    <PRPA_IN302011UV02
    ITSVersion="XML_1.0"
    xsi:schemaLocation="urn:hl7-org:v3 PRPA_IN302011UV02.xsd"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns="urn:hl7-org:v3">
      <id root={xUUID.toString}/>
      <creationTime value={formattedDate}/>
      <interactionId extension="PRPA_IN302011UV02" root="2.16.840.1.113883.1.18"/>
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
          <encounterEvent classCode="ENC" moodCode="EVN">
            <id root={eventUUID} extension={cardId}/>
            <subject typeCode="SBJ">
              <patient classCode="PAT">
                <id root={patientUUID}/>
              </patient>
            </subject>
            <location1 typeCode="LOC">
              <time value={formattedBegDate}/> <!-- когда выбыл -->
              <statusCode code="active"/>
              <serviceDeliveryLocation classCode="SDLOC">
                <id root={fromUUID}/> <!-- UUID подразделения -->
              </serviceDeliveryLocation>
            </location1>
            <location2 typeCode="LOC">
              <!-- куда переводится -->
              <time value={formattedEndDate}/> <!-- когда прибыл -->
              <statusCode code="active"/> <!-- фиксированное значение -->
              <serviceDeliveryLocation classCode="SDLOC">
                <id root={toUUID}/> <!-- UUID подразделения -->
              </serviceDeliveryLocation>
            </location2>
          </encounterEvent>
        </subject>
      </controlActProcess>
    </PRPA_IN302011UV02>

  override def toXml = xml
}
