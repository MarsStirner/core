package ru.korus.tmis.ws.transfusion;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.order.OrderIssueInfo;
import ru.korus.tmis.ws.transfusion.order.RegOrderIssueResult;
import ru.korus.tmis.ws.transfusion.procedure.EritrocyteMass;
import ru.korus.tmis.ws.transfusion.procedure.FinalVolume;
import ru.korus.tmis.ws.transfusion.procedure.LaboratoryMeasure;
import ru.korus.tmis.ws.transfusion.procedure.ProcedureInfo;
import ru.korus.tmis.ws.transfusion.procedure.RegProcedureResult;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 30.01.2013, 12:40:04 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@WebService(endpointInterface = "ru.korus.tmis.ws.transfusion.ServiceTransfusion", targetNamespace = "http://korus.ru/tmis/ws/transfusion",
        serviceName = "TransfusionServiceImpl", portName = "portTransfusion", name = "nameTransfusion")
public class ServiceTransfusionImpl implements ServiceTransfusion {

    @EJB
    private Database database;

    private static final Logger logger = LoggerFactory.getLogger(ServiceTransfusionImpl.class);

    @EJB
    private RegOrderIssueResult regOrderIssueResult;

    @EJB
    private RegProcedureResult regProcedureResult;

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#setOrderIssueResult(ru.korus.tmis.ws.transfusion.order.OrderIssueInfo)
     */
    @Override
    public IssueResult setOrderIssueResult(@WebParam(name = "requestId", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final Integer requestId,
            @WebParam(name = "factDate", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final Date factDate,
            @WebParam(name = "components", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final List<OrderIssueInfo> components,
            @WebParam(name = "orderComment", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final String orderComment) {
        final IssueResult res = new IssueResult();
        logger.info("Entered in transfusion service 'setOrderIssueResult' with parameters: requestId: {}, factDate: {}, listOrderIssueInfo.size: {}",
                requestId, factDate, components == null ? null : components.size());
        if (requestId == null || components == null || components.isEmpty()) {
            res.setDescription("Error: illegal arguments for setOrderIssueResult");
            res.setResult(false);
            return res;

        }
        return regOrderIssueResult.save(requestId, factDate, components, orderComment);
    }

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#getDivisions()
     */
    @Override
    public List<DivisionInfo> getDivisions() {
        logger.info("Entered in transfusion service 'getDivisions()'");
        return getDivisionsFromDB();
    }

    /**
     * @see ru.korus.tmis.ws.transfusion.ServiceTransfusion#setProcedureResult(ru.korus.tmis.ws.transfusion.efive.PatientCredentials,
     *      ru.korus.tmis.ws.transfusion.procedure.ProcedureInfo, ru.korus.tmis.ws.transfusion.procedure.EritrocyteMass, java.util.List,
     *      ru.korus.tmis.ws.transfusion.procedure.FinalVolumeList)
     */
    @Override
    @WebMethod
    public IssueResult
            setProcedureResult(
                    @WebParam(name = "patientCredentials", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final PatientCredentials patientCredentials,
                    @WebParam(name = "ProcedureInfo", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final ProcedureInfo procedureInfo,
                    @WebParam(name = "EritrocyteMass", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final EritrocyteMass eritrocyteMass,
                    @WebParam(name = "Measures", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final List<LaboratoryMeasure> measures,
                    @WebParam(name = "finalVolumeList", targetNamespace = "http://korus.ru/tmis/ws/transfusion") final List<FinalVolume> finalVolume) {
        logger.info("Entered in transfusion service 'setProcedureResult' with parameters: {}; {}; {}; measures.size = {}; finalVolume.size = {}",
                patientCredentials, procedureInfo, eritrocyteMass, measures != null ? measures.size() : null, finalVolume != null ? finalVolume.size() : null);
        return regProcedureResult.save(patientCredentials, procedureInfo, eritrocyteMass, measures, finalVolume);
    }

    /**
     * Получит список подразделений {OrgStructure}
     *
     * @return список подразделений
     */
    public List<DivisionInfo> getDivisionsFromDB() {
        final List<DivisionInfo> res = new LinkedList<DivisionInfo>();
        EntityManager em = database.getEntityMgr();
        final List<OrgStructure> structs = em.createQuery("SELECT s FROM OrgStructure s WHERE s.deleted = 0", OrgStructure.class).getResultList();
        for (final OrgStructure struct : structs) {
            final DivisionInfo info = new DivisionInfo();
            info.setId(struct.getId());
            info.setName(struct.getName());
            res.add(info);
        }

        return res;
    }

}
