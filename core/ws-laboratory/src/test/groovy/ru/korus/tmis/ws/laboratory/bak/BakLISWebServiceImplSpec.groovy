package ru.korus.tmis.ws.laboratory.bak

import ru.korus.tmis.ws.laboratory.bak.model.ImageValue
import ru.korus.tmis.ws.laboratory.bak.model.MicroSensitivity
import ru.korus.tmis.ws.laboratory.bak.model.MicroValue
import ru.korus.tmis.ws.laboratory.bak.model.ResultAnalyze
import ru.korus.tmis.ws.laboratory.bak.utils.DateUtils
import spock.lang.Shared
import spock.lang.Specification
/**
 * Тестовый класс для проверки WS ответов от ЛИС
 *
 * @author anosov@outlook.com 
 * date: 5/26/13
 */
class BakLISWebServiceImplSpec extends Specification {
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

}
