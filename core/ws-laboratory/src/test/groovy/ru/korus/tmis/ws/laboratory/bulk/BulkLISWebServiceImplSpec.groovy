package ru.korus.tmis.ws.laboratory.bulk

import ru.korus.tmis.ws.laboratory.bulk.model.ImageValue
import ru.korus.tmis.ws.laboratory.bulk.model.MicroSensitivity
import ru.korus.tmis.ws.laboratory.bulk.model.MicroValue
import ru.korus.tmis.ws.laboratory.bulk.model.ResultAnalyze
import ru.korus.tmis.ws.laboratory.bulk.utils.DateUtils
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

/**
 * Тестовый класс для проверки WS ответов от ЛИС
 *
 * @author anosov@outlook.com 
 * date: 5/26/13
 */
class BulkLISWebServiceImplSpec extends Specification {
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
    String MORE_40_STRING = "a" * 42;

    @Shared
    String MORE_150_STRING = "a"* 152;

    def setup() {
        def date = DateUtils.XMLGregorianCalendarNow

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

    def "проверка валидации результатов анализа"() {
        when:
        BulkLISWebServiceImpl webService = new BulkLISWebServiceImpl()
        webService.setAnalysisResults(12, "xxx", "xxx", true, resultAnalyze, "xxx", 12, "xxx", "xxx")

        then:
        webService.validate()

    }

    def "проверка валидации результатов анализа #2"() {
        when:
        BulkLISWebServiceImpl webService = new BulkLISWebServiceImpl()
        webService.setAnalysisResults(12, MORE_40_STRING, "xxx", true, resultAnalyze, "xxx", 12, "xxx", "xxx")

        then:
        !webService.validate()

        and:
        webService.errors.size() == 1

        and:
        webService.errors.first() == "Длинна или значение поля orderBarCode не должно быть больше 40"
    }

}
