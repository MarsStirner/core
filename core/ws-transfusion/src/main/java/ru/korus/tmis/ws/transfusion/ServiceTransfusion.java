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
     * произвольный текстовый комментарий
     * @param requestId - Идентификатор требования на выдачу КК (Action.id)
     * @param factDate - Фактическая дата/время выдачи КК в формате "yyyy-MM-dd"
     * @param orderIssueInfo
     *  @param orderComment
     * @return
     */
    public IssueResult
            setOrderIssueResult(
                    @WebParam(name = "requestId", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Integer requestId,
                    @WebParam(name = "factDate", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Date factDate, 
                    @WebParam(name = "components", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<OrderIssueInfo> components,
                    @WebParam(name = "orderComment", targetNamespace = "http://korus.ru/tmis/ws/transfusion") String orderComment);

    @WebMethod()
    public List<DivisionInfo> getDivisions();

    @WebMethod()
    public IssueResult
            setProcedureResult(@WebParam(name = "patientCredentials", targetNamespace = "http://korus.ru/tmis/ws/transfusion") PatientCredentials patientCredentials,
                    @WebParam(name = "ProcedureInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") ProcedureInfo procedureInfo,
                    @WebParam(name = "EritrocyteMass", targetNamespace = "http://korus.ru/tmis/ws/transfusion") EritrocyteMass eritrocyteMass,
                    @WebParam(name = "Measures", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<LaboratoryMeasure> measures,
                    @WebParam(name = "finalVolumeList", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<FinalVolume> finalVolumeList);

}
