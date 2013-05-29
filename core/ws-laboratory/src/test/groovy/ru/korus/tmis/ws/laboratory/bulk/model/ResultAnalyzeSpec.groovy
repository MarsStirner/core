package ru.korus.tmis.ws.laboratory.bulk.model

import ru.korus.tmis.ws.laboratory.bulk.utils.DateUtils
import spock.lang.Shared
import spock.lang.Specification

import javax.xml.bind.JAXBContext
import javax.xml.bind.Marshaller

/**
 *
 * Тесты для модели данных результатов анализа
 *
 * class @see ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyze
 *
 * @author anosov@outlook.com 
 * date: 5/28/13
 */
class ResultAnalyzeSpec extends Specification {

    def imageValues = [new ImageValue(imageData: "xxx", imageString: "image string xxx")]

    def microSensitivity = [
            new MicroSensitivity(
                    MIC: "xxx",
                    antibioticActivityValue: "xxx",
                    antibioticLisId: "xxx",
                    antibioticName: "xxx")]

    def microValues = [
            new MicroValue(
                    organismConcetration: "xxx",
                    organismLisId: "xxx",
                    organismName: "xxx")]

    @Shared
    ResultAnalyze resultAnalyze

    @Shared
    String example

    def setup() {
        def date = DateUtils.XMLGregorianCalendarNow
        example = """<?xml version="1.0" encoding="UTF-8" standalone="yes"?><ns2:resultAnalyze xmlns:ns2="http://www.korusconsulting.ru"><deviceName>xxx</deviceName><imageValues><imageValues><imageString>image string xxx</imageString><imageData>xxx</imageData></imageValues></imageValues><indicatorCode>xxx</indicatorCode><indicatorName>xxx</indicatorName><microSensitivity><microSensitivity><antibioticLisId>xxx</antibioticLisId><antibioticName>xxx</antibioticName><MIC>xxx</MIC><antibioticActivityValue>xxx</antibioticActivityValue></microSensitivity></microSensitivity><microValues><microValues><organismLisId>xxx</organismLisId><organismName>xxx</organismName><organismConcetration>xxx</organismConcetration></microValues></microValues><resultComment>xxx</resultComment><resultNormString>xxx</resultNormString><resultNormalityIndex>1.1</resultNormalityIndex><resultSignDate>${date}</resultSignDate><resultStatus>xxx</resultStatus><resultUnit>xxx</resultUnit><resultValueText>xxx</resultValueText><valueType>3</valueType></ns2:resultAnalyze>"""

        resultAnalyze = new ResultAnalyze(
                deviceName: "xxx",
                imageValues: imageValues,
                indicatorCode: "xxx",
                indicatorName: "xxx",
                microSensitivity: microSensitivity,
                microValues: microValues,
                resultComment: "xxx",
                resultNormalityIndex: 1.1,
                resultNormString: "xxx",
                resultSignDate: date,
                resultStatus: "xxx",
                resultUnit: "xxx",
                resultValueText: "xxx",
                valueType: ResultAnalyze.ValueType.CONC_OF_MICROORG)
    }

    def "проверка маршаллинга Object -> XML"() {
        when:
        StringWriter sw = new StringWriter()
        JAXBContext jc = JAXBContext.newInstance(ResultAnalyze.class);
        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(resultAnalyze, sw);

        then:
        sw.toString() == example
    }
}
