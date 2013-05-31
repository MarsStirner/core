package ru.korus.tmis.laboratory.bulk.model

import spock.lang.Shared
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 * Тесты для модели @see ru.korus.tmis.laboratory.bulk.model.PatientInfo
 *
 * @author anosov@outlook.com 
 * date: 5/26/13
 */
class PatientInfoSpec extends Specification {

    @Shared PatientInfo fullPatientInfo = new PatientInfo();
    @Shared PatientInfo piecePatientInfo = new PatientInfo();

    @Shared String CUSTODIAN = "x101"
    @Shared String PATIENT_ADDRESS = "просп. Добролюбова, 18, 501, Санкт-Петербург"
    @Shared Date PATIENT_BIRTH_DATE = new Date().parse("dd.MM.yyyy", "10.12.2001")
    @Shared String PATIENT_NAME = "John"
    @Shared String PATIENT_FAMILY = "Doe"
    @Shared String PATIENT_PATRONUM = "Felix"
    @Shared String PATIENT_NUMBER = "8e87d8de8"
    @Shared PatientInfo.Gender PATIENT_SEX = PatientInfo.Gender.MEN
    @Shared Integer PATIENT_MIS_ID = 87987987978

    def setup() {
        fullPatientInfo.custodian = CUSTODIAN
        fullPatientInfo.patientAddress = PATIENT_ADDRESS
        fullPatientInfo.patientBirthDate = PATIENT_BIRTH_DATE
        fullPatientInfo.patientName = PATIENT_NAME
        fullPatientInfo.patientFamily = PATIENT_FAMILY
        fullPatientInfo.patientPatronum = PATIENT_PATRONUM
        fullPatientInfo.patientMisId = PATIENT_MIS_ID
        fullPatientInfo.patientNumber = PATIENT_NUMBER
        fullPatientInfo.patientSex = PATIENT_SEX

        piecePatientInfo.patientAddress = PATIENT_ADDRESS
        piecePatientInfo.patientName = PATIENT_NAME
        piecePatientInfo.patientFamily = PATIENT_FAMILY
        piecePatientInfo.patientPatronum = PATIENT_PATRONUM
        piecePatientInfo.patientMisId = PATIENT_MIS_ID
        piecePatientInfo.patientNumber = PATIENT_NUMBER
        piecePatientInfo.patientSex = PATIENT_SEX
    }

    def "проверка маршалинга из Java-класса в XML"() {
        given:
        JAXBContext jaxbContext = JAXBContext.newInstance(PatientInfo.class);
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        // output pretty printed
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        StringWriter sw = new StringWriter();
        jaxbMarshaller.marshal(fullPatientInfo, sw);

        def outXML = '''<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<ns2:patientInfo xmlns:ns2="http://www.korusconsulting.ru">
    <patientMisId>2088642058</patientMisId>
    <patientFamily>Doe</patientFamily>
    <patientName>John</patientName>
    <patientPatronum>Felix</patientPatronum>
    <patientBirthDate>10.12.2001</patientBirthDate>
    <patientSex>1</patientSex>
    <custodian>x101</custodian>
    <patientAddress>просп. Добролюбова, 18, 501, Санкт-Петербург</patientAddress>
    <patientNumber>8e87d8de8</patientNumber>
</ns2:patientInfo>
'''

        expect:
        sw.toString() == outXML
    }

    def "проверка обязательности полей"() {
        expect:
        piecePatientInfo.hasErrors()
    }
}
