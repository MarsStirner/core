package ru.korus.tmis.ws.risar;

import com.google.gson.Gson;

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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.*;

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
    @Produces(MediaType.TEXT_HTML + ";charset=UTF-8")
    public Response newExam(@PathParam(value = "actionId") Integer actionId) throws WebApplicationException {
        try {
            logger.info("RISAR notification. New exam with actionId: " + actionId);
            Action action = dbActionBean.getActionById(actionId);

            final Integer clientId = action.getEvent().getPatient().getId();
            logger.info("RISAR notification. MIS patient id: " + clientId);

            final List<ClientIdentification> clientIdentificationList =
                    em.createNamedQuery("ClientIdentification.findByPatientAndSystem").
                            setParameter("clientId", clientId).setParameter("code", "rs").setMaxResults(100).getResultList();
            logger.info("RISAR notification. RISAR identification count: " + clientIdentificationList.size());
            if (clientIdentificationList.isEmpty()) {
                clientIdentificationList.addAll(findByPatientInfo(action.getEvent().getPatient()));
            }
            for (ClientIdentification clientIdentification : clientIdentificationList) {
                logger.info("RISAR notification. RISAR identifier: " + clientIdentification.getIdentifier());
                sendExamToRisar(action, clientIdentification);
            }

        } catch (CoreException ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        } catch (Exception ex) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            logger.error(sw.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(sw.toString()).build();
        }

        return Response.ok().build();
    }

    private List<ClientIdentification> findByPatientInfo(Patient patient) throws CoreException {

        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("patientFIO[name]", patient.getFirstName());
        map.add("patientFIO[surname]", patient.getLastName());
        map.add("patientFIO[middlename]", patient.getPatrName());
        Date birthDate = patient.getBirthDate();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (birthDate != null) {
            String birthDateStr = simpleDateFormat.format(birthDate);
            map.add("birthDate", birthDateStr);
        }
        int index = 0;
        for (ClientDocument doc : patient.getClientDocuments()) {
            String codeType = doc.getDocumentType() == null ? null : doc.getDocumentType().getCode();
            addToRequest("codeType", codeType, index, map);
            addToRequest("number", doc.getNumber(), index, map);
            addToRequest("series", doc.getSerial(), index, map);
            Date createDate = doc.getDate();
            if (createDate != null) {
                String createDateStr = simpleDateFormat.format(createDate);
                if( !createDateStr.contains("1800")) {
                    addToRequest("registry_date", createDateStr, index, map);
                }
            }
            ++index;
        }
        logger.info("RISAR notification. Find patient param: " + map);
        final String url = ConfigManager.Risar().ServiceUrl() + "/api/patients/v2/find/";
        logger.info("RISAR notification. Find patientt url: " + url);
        String resultStr = rest.postForObject(url, map, String.class);
        logger.info("RISAR notification. Find result: " + resultStr);
        RisarResponse result =  new Gson().fromJson(resultStr, RisarResponse.class);
        if (!result.isOk()) {
            throw new CoreException("RISAR notification. Find patient error:" + result);
        }

        List<ClientIdentification> res = new LinkedList<ClientIdentification>();
        res.add(createNewIdentification(patient, result));
        return res;
    }

    private ClientIdentification createNewIdentification(Patient patient, RisarResponse result) throws CoreException {
        final ClientIdentification identification = new ClientIdentification();
        identification.setClient(patient);
        identification.setIdentifier(result.getPatientId());
        identification.setCreateDatetime(new Date());
        identification.setModifyDatetime(new Date());
        identification.setDeleted(false);
        identification.setCheckDate(new Date());
        final AccountingSystem accountingSystem = findOrCreateAccountingSystem();
        identification.setAccountingSystem(accountingSystem);
        em.persist(identification);
        return identification;
    }

    private AccountingSystem findOrCreateAccountingSystem() throws CoreException {
        List<AccountingSystem> res = em.createQuery("SELECT accountingSystem FROM AccountingSystem accountingSystem WHERE accountingSystem.code = 'rs'", AccountingSystem.class).
                setMaxResults(100).getResultList();
        if (res.isEmpty()) {
            throw new CoreException("RISAR notification. Patient registration error. Not found rbAccountingSystem.code = 'rs'");
        }
        return res.iterator().next();
    }

    private void addToRequest(String param, String value, int index, MultiValueMap<String, String> map) {
        if (value != null && !value.isEmpty()) {
            map.add("patientDocuments[" + index + "]" + "[" + param + "]", value);
        }
    }

    private void sendExamToRisar(Action action, ClientIdentification clientIdentification) throws CoreException {
        RestTemplate rest = new RestTemplate();
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("inspectationID", String.valueOf(action.getId()));
        map.add("patiendID", clientIdentification.getIdentifier());
        final Staff staff = action.getExecutor();
        if (staff != null) {
            map.add("doctorFIO[surname]", staff.getLastName());
            map.add("doctorFIO[name]", staff.getFirstName());
            final String patrName = staff.getPatrName();
            if (patrName != null && !patrName.isEmpty()) {
                map.add("doctorFIO[middlename]", patrName);
            }
            final Speciality speciality = staff.getSpeciality();
            if (speciality != null) {
                //map.add("position", post.getName());
                map.add("specialityCode", speciality.getOKSOCode());
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
                Object value = actionProp.entrySet().iterator().next().getValue().iterator().next();
                if (value instanceof APValueMKB) {
                    map.add("diagnosisCode", ((APValueMKB) value).getMkb().getDiagID());
                }
            }
            actionProp =
                    dbActionPropertyBeanLocal.getActionPropertiesByActionIdAndTypeCodes(action.getId(), Arrays.asList("recommendations", "resort"));
            if (!actionProp.isEmpty() && !actionProp.entrySet().iterator().next().getValue().isEmpty()) {
                Object value = actionProp.entrySet().iterator().next().getValue().iterator().next();
                if (value instanceof APValue) {
                    map.add("recommendations", ((APValue) value).getValueAsString());
                }
            }
        } catch (CoreException e) {
            logger.error("RISAR notification. Cannot set INN and title of organization.", e);
        }

        final Date date = action.getBegDate();
        if (date != null) {
            map.add("visitDate", (new SimpleDateFormat("yyyy-MM-dd")).format(date));
            map.add("visitTime", (new SimpleDateFormat("HH:mm:ss")).format(date));
        }

        logger.info("RISAR notification. Request param: " + map);
        final String url = ConfigManager.Risar().ServiceUrl() + "/api/patient/v1/saveInspectionResults/";
        logger.info("RISAR notification. Request url: " + url);
        String result = rest.postForObject(url, map, String.class);
        logger.info("RISAR notification. Request result: " + result);
        if (result.contains("\"failed\"")) {
            throw new CoreException("RISAR notification error:" + result);
        }
        //RisarResponse result = rest.postForObject(url, map, RisarResponse.class);
        /*\if (!result.isk()) {
            throw new CoreException(("RISAR notification error:" + result));
        }*/
    }

}
