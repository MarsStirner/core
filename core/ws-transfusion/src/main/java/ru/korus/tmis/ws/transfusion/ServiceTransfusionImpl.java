package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.order.OrderIssueInfo;
import ru.korus.tmis.ws.transfusion.order.RegOrderIssueResult;
import ru.korus.tmis.ws.transfusion.procedure.EritrocyteMass;
import ru.korus.tmis.ws.transfusion.procedure.FinalVolume;
import ru.korus.tmis.ws.transfusion.procedure.LaboratoryMeasure;
import ru.korus.tmis.ws.transfusion.procedure.ProcedureInfo;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 30.01.2013, 12:40:04 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@WebService(endpointInterface = "ru.korus.tmis.ws.transfusion.ServiceTransfusion", targetNamespace = "http://korus.ru/tmis/ws/transfusion",
        serviceName = "TransfusionServiceImpl", portName = "portTransfusion", name = "nameTransfusion")
public class ServiceTransfusionImpl implements ServiceTransfusion {

    private static final Logger logger = LoggerFactory.getLogger(ServiceTransfusionImpl.class);
    
    @EJB
    RegOrderIssueResult regOrderIssueResult;

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#setOrderIssueResult(ru.korus.tmis.ws.transfusion.order.OrderIssueInfo)
     */
    @Override
    public IssueResult
            setOrderIssueResult(
                    @WebParam(name = "requestId", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Integer requestId,
                    @WebParam(name = "factDate", targetNamespace = "http://korus.ru/tmis/ws/transfusion") Date factDate, 
                    @WebParam(name = "listOrderIssueInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<OrderIssueInfo> listOrderIssueInfo) {
        IssueResult res = new IssueResult();
        if(requestId == null || factDate == null || listOrderIssueInfo == null || listOrderIssueInfo.isEmpty()) {
            res.setDescription("Error: illegal arguments for setOrderIssueResult" );
        }
        logger.info("Entered in transfusion service 'setOrderIssueResult' with parameters: requestId: {}, factDate: {}, listOrderIssueInfo.size: {}", listOrderIssueInfo.size());
        return res;//regOrderIssueResult.save(orderIssueInfo);
    }

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#getDivisions()
     */
    @Override
    public List<DivisionInfo> getDivisions() {
        logger.info("Entered in transfusion service 'getDivisions()'");
        return Database.getDivisions();
    }

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#setProcedureResult(ru.korus.tmis.ws.transfusion.efive.PatientCredentials,
     * ru.korus.tmis.ws.transfusion.procedure.ProcedureInfo, ru.korus.tmis.ws.transfusion.procedure.EritrocyteMass, java.util.List,
     * ru.korus.tmis.ws.transfusion.procedure.FinalVolumeList)
     */
    @Override
    @WebMethod
    public IssueResult
            setProcedureResult(@WebParam(name = "patientCredentials", targetNamespace = "http://korus.ru/tmis/ws/transfusion") PatientCredentials patientCredentials,
                    @WebParam(name = "ProcedureInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") ProcedureInfo procedureInfo,
                    @WebParam(name = "EritrocyteMass", targetNamespace = "http://korus.ru/tmis/ws/transfusion") EritrocyteMass eritrocyteMass,
                    @WebParam(name = "Measures", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<LaboratoryMeasure> measures,
                    @WebParam(name = "finalVolumeList", targetNamespace = "http://korus.ru/tmis/ws/transfusion") List<FinalVolume> finalVolume) {
        logger.info("Entered in transfusion service 'setProcedureResult' with parameters: {}; {}; {}; measures.size = {}; finalVolume.size = {}",
                patientCredentials, procedureInfo, eritrocyteMass, measures != null ? measures.size() : null, finalVolume != null
                        ? finalVolume.size() : null);
        // TODO Auto-generated method stub
        return new IssueResult();
    }

}
