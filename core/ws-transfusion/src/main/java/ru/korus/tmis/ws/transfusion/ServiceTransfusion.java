package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.order.OrderIssueInfo;
import ru.korus.tmis.ws.transfusion.procedure.EritrocyteMass;
import ru.korus.tmis.ws.transfusion.procedure.FinalVolume;
import ru.korus.tmis.ws.transfusion.procedure.LaboratoryMeasure;
import ru.korus.tmis.ws.transfusion.procedure.ProcedureInfo;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.2013, 12:20:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */

@WebService(targetNamespace = "http://korus.ru/tmis/ws/transfusion", name = "TransfusionService")
public interface ServiceTransfusion {
    @WebMethod()
    /**
     * Передача извещения о результатах выполнения требования КК
     * 
     * @param requestId - Идентификатор требования на выдачу КК (Action.id)
     * @param factDate - Фактическая дата/время выдачи КК в формате "yyyy-MM-dd"
     * @param orderIssueInfo – паспортные данные выданного(-ых) компонентов крови 
     * @param orderComment - произвольный текстовый комментарий системы ТРФУ
     * @return - результат регистрации требования
     */
    IssueResult setOrderIssueResult(@WebParam(name = "requestId", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Integer requestId, @WebParam(
            name = "factDate", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Date factDate, @WebParam(name = "components",
            targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<OrderIssueInfo> components, @WebParam(name = "orderComment",
            targetNamespace = "http://korus.ru/tmis/ws/transfusion") String orderComment);

    /**
     * Запрос списка отделений {OrgStructure}
     * 
     * @return - списк отделений
     */
    @WebMethod()
    List<DivisionInfo> getDivisions();

    /**
     * Передача извещения о результатах проведения процедуры
     * 
     * @param patientCredentials
     *            - информация о пациенте
     * @param procedureInfo
     *            - информация о проведенной процедуре
     * @param eritrocyteMass
     *            - информауия о эритроцитной массе
     * @param measures
     *            - список лабораторных измерений
     * @param finalVolumeList
     *            - список финальных объемов
     * @return результат регистрации итогов проведения процедуры
     */
    @WebMethod()
    IssueResult
            setProcedureResult(
                    @WebParam(name = "patientCredentials", targetNamespace = "http://korus.ru/tmis/ws/transfusion") PatientCredentials patientCredentials,
                    @WebParam(name = "ProcedureInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") ProcedureInfo procedureInfo,
                    @WebParam(name = "EritrocyteMass", targetNamespace = "http://korus.ru/tmis/ws/transfusion") EritrocyteMass eritrocyteMass,
                    @WebParam(name = "Measures", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<LaboratoryMeasure> measures,
                    @WebParam(name = "finalVolumeList", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<FinalVolume> finalVolumeList);

}
