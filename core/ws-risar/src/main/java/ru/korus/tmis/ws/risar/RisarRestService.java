package ru.korus.tmis.ws.risar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.08.14, 10:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Stateless
@Path("/api/notification")
public class RisarRestService {

    private static final Logger logger = LoggerFactory.getLogger(RisarRestService.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    private DbActionBeanLocal dbActionBean;

    @EJB
    DbOrganizationBeanLocal organizationBeanLocal;

    @EJB
    DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    @PUT
    @Path("/new/exam/{actionId}")
    public String newExam(@PathParam(value = "actionId") Integer actionId) {
        try {
            logger.info("RISAR notification. New exam with actionId: " + actionId);
            Action action = dbActionBean.getActionById(actionId);

            final Integer clientId = action.getEvent().getPatient().getId();
            logger.info("RISAR notification. MIS patient id: " + clientId);

            final List<ClientIdentification> clientIdentificationList =
                    em.createNamedQuery("ClientIdentification.findByPatientAndSystem").
                            setParameter("clientId", clientId).setParameter("code", "rs").setMaxResults(100).getResultList();
            logger.info("RISAR notification. RISAR identification count: " + clientIdentificationList.size());
            for (ClientIdentification clientIdentification : clientIdentificationList) {
                logger.info("RISAR notification. RISAR identifier: " + clientIdentification.getIdentifier());
                sendExamToRisar(action, clientIdentification);
            }
        } catch (CoreException ex) {
            throw new WebApplicationException(ex);
        }
        return "ok";
    }

    private void sendExamToRisar(Action action, ClientIdentification clientIdentification) throws CoreException {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("inspectationID", String.valueOf(action.getId()));
        map.add("patiendID", clientIdentification.getIdentifier());
        final Staff staff = action.getModifyPerson();
        if (staff != null) {
            map.add("doctorFIO[surname]", staff.getLastName());
            map.add("doctorFIO[name]", staff.getFirstName());
            final String patrName = staff.getPatrName();
            if (patrName != null && !patrName.isEmpty()) {
                map.add("doctorFIO[middlename]", patrName);
            }
            final RbPost post = staff.getPost();
            if (post != null) {
                map.add("position", post.getName());
            }
        }
        try {
            Organisation organization = organizationBeanLocal.getOrganizationById(ConfigManager.Common().OrgId());
            map.add("inspectLPUName", organization.getShortName());
            map.add("INN", organization.getInn());
            final String[] diagCode = {"diagnosis",
                    "admissionMkb",
                    "assocDiagMkb",
                    "assocDiagMkbPat",
                    "complDi1_1_01agMkbPat",
                    "diagComplMkb",
                    "diagReceivedMkb",
                    "mainDiag",
                    "mainDiagMkb",
                    "mainDiagMkbPat",
                    "mainReasonODMkb"};

            Map<ActionProperty, List<APValue>> actionProp =
                    dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeCodes(action.getId(), Arrays.asList(diagCode));
            if (!actionProp.isEmpty() && !actionProp.entrySet().iterator().next().getValue().isEmpty()) {
                Object value = actionProp.entrySet().iterator().next().getValue().iterator().next().getValue();
                if (value instanceof APValueMKB) {
                    map.add("diagnosisCode", ((APValueMKB) value).getMkb().getDiagID());
                }
            }
            actionProp =
                    dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeCodes(action.getId(), Arrays.asList("recommended", "resort"));
            if (!actionProp.isEmpty() && !actionProp.entrySet().iterator().next().getValue().isEmpty()) {
                Object value = actionProp.entrySet().iterator().next().getValue().iterator().next().getValue();
                if (value instanceof APValue) {
                    map.add("recommendations", ((APValue) value).getValueAsString());
                }
            }
        } catch (CoreException e) {
            logger.error("RISAR notification. Cannot set INN and title of organization.", e);
        }

        map.add("visitDate", (new SimpleDateFormat("yyyy-MM-dd")).format(action.getModifyDatetime()));
        map.add("visitTime", (new SimpleDateFormat("HH:mm:ss")).format(action.getModifyDatetime()));

        logger.info("RISAR notification. Request param: " + map);
        final String url = ConfigManager.Risar().ServiceUrl() + "/api/patient/v1/saveInspectionResults/";
        String result = rest.postForObject(url, map, String.class);
        logger.info("RISAR notification. Request url: " + url + " Request result: " + result);
        if (result.contains("\"failed\"")) {
            throw new CoreException("RISAR notification error:" + result);
        }
        //RisarResponse result = rest.postForObject(url, map, RisarResponse.class);
        /*\if (!result.isk()) {
            throw new CoreException(("RISAR notification error:" + result));
        }*/
    }
}
