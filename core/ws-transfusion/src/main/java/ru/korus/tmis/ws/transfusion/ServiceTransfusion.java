package ru.korus.tmis.ws.transfusion;

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
    public IssueResult
            setOrderIssueResult(@WebParam(name = "orderIssueInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") OrderIssueInfo orderIssueInfo);

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
