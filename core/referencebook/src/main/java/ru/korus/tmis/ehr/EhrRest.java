package ru.korus.tmis.ehr;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.ehr.ws.*;
import ru.korus.tmis.ehr.ws.CodeAndName;
import ru.korus.tmis.ehr.ws.Employee;
import ru.korus.tmis.ehr.ws.callback.*;
import ru.korus.tmis.ehr.ws.callback.Error;
import ru.korus.tmis.pix.sda.SdaHandlerResolver;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Date;
import java.util.UUID;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.02.14, 14:34 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Path("/api")
public class EhrRest implements RestCallback {

    private static final Logger logger = LoggerFactory.getLogger(EhrRest.class);

    private final static ru.korus.tmis.ehr.ws.ObjectFactory EhrFactory = new ru.korus.tmis.ehr.ws.ObjectFactory();
    private final static ru.korus.tmis.ehr.ws.callback.ObjectFactory CallbackFactory = new ru.korus.tmis.ehr.ws.callback.ObjectFactory();
    private static final int TIMEOUT = 10000;

    private final Object lockPatientResponse = new Object();
    private PatientResponse patientResponse = null;

    private final Object lockDocumentResponse = new Object();
    private DocumentResponse documentResponse = null;

    private final Object lockRetrieveDocumentResponse = new Object();
    private RetrieveDocumentResponse retrieveDocumentResponse = null;
    private String messageUuid = null;

    private static class PatientQueryDoc {
        private String series = null;
        private String type = null;
        private String number = null;
    }

    private static class QueryInp {

        private PatientQueryHeaders headers = null;

        private static class PatientQueryHeaders {

            private String BinarySecurityToken = null;
        }

        private PatientQueryData data = null;

        private static class PatientQueryData {

            private String facilityCode = null;
            private PatientQueryInitiatedBy initiatedBy = null;

            private static class PatientQueryInitiatedBy {

                private PatientQuerySpeciality specialty = null;

                private static class PatientQuerySpeciality {
                    private String codingSystem = null;
                    private String code = null;
                    private String name = null;
                }

                private PatientQueryRole role;

                private static class PatientQueryRole {
                    private String codingSystem = null;
                    private String code = null;
                    private String name = null;
                }

                private String snils = null;
                private String familyName = null;
                private String givenName = null;
                private String middleName = null;

            }

            private PatientQueryParams params = null;

            private static class PatientQueryParams {
                private String gender = null;
                private String dobLow = null;
                private String dobHigh = null;
                private PatientQueryMrn mrn = null;

                private static class PatientQueryMrn {
                    private String root = null;
                    private String extension = null;
                }

                private String familyName = null;
                private String givenName = null;
                private String middleName = null;
                private String snils = null;
                private PatientQueryDoc omsPolicy = null;
                private PatientQueryDoc identityDocument = null;

                //document query param -------------------------
                private String status;
                private Date docDateLow;
                private Date docDateHigh;
                //----------------------------------------------

                //retrieve document query param  ---------------
                private String documentId;
                //----------------------------------------------
            }

        }
    }

    @POST
    @Consumes("application/json")
    @Path("/patientQuery/")
    public Response patientQuery(String input) {

        logger.info("request patientQuery: " + input);

        QueryIEMKServiceSoap port = getQueryIEMKServiceSoap(input);

        PatientQuery patientQuery = toEhrPatientQuery(input);

        CallbackService.setRestCallback(this, messageUuid);
        port.patientQuery(patientQuery);
        synchronized(lockPatientResponse) {
            try {
                lockPatientResponse.wait(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        String resJson = (new Gson()).toJson(patientResponse == null ? getPatientResponseNotFound() : patientResponse);
        logger.info("patientQuery response: " + resJson);
        patientResponse = null;
        return Response.status(Response.Status.OK).entity(resJson).build();
    }

    private QueryIEMKServiceSoap getQueryIEMKServiceSoap(String input) {

        //final Map jsonMap = new Gson().fromJson(input, Map.class);
        final String secureToken = getBinarySecurityToken(input);

        messageUuid = UUID.randomUUID().toString().toLowerCase();
        QueryIEMKService service = new QueryIEMKService();
        service.setHandlerResolver(new SdaHandlerResolver(secureToken, messageUuid));
        return service.getQueryIEMKServiceSoap();
    }

    private PatientResponse getPatientResponseNotFound() {
        PatientResponse res = CallbackFactory.createPatientResponse();
        final IEMKPatient iemkPatient = CallbackFactory.createIEMKPatient();
        res.setPatients(CallbackFactory.createArrayOfpatientIEMKPatient());
        res.getPatients().getPatient().add(iemkPatient);
        iemkPatient.setFamilyName("NotFound");
        iemkPatient.setGivenName("NotFound");
        iemkPatient.setMiddleName("NotFound");
        return res;
    }


    private static SeriesNumberAndType doEhrDoc(PatientQueryDoc omsPolicy) {
        final SeriesNumberAndType seriesNumberAndType = EhrFactory.createSeriesNumberAndType();
        seriesNumberAndType.setNumber(omsPolicy.number);
        seriesNumberAndType.setSeries(omsPolicy.series);
        seriesNumberAndType.setType(omsPolicy.type);
        return seriesNumberAndType;
    }

    private String getBinarySecurityToken(String input) {
        QueryInp queryInp = fromJson(input);
        return queryInp.headers.BinarySecurityToken;
    }

    @POST
    @Consumes("application/json")
    @Path("/documentQuery/")
    public Response documentQuery(String input) {
        //documentQuery: {"headers": {"BinarySecurityToken": "UmVlOWVldGk="}, "data": {"facilityCode": "1.2.643.5.1.13.3.25.58.47", "initiatedBy": {"specialty": {"codingSystem": "1.2.643.5.1.13.2.1.1.181", "code": "1135", "name": "\u0414\u0435\u0442\u0441\u043a\u0430\u044f \u0445\u0438\u0440\u0443\u0440\u0433\u0438\u044f"}, "role": {"codingSystem": "1.2.643.5.1.13.2.1.1.607", "code": "23", "name": "\u0432\u0440\u0430\u0447 - \u0434\u0435\u0442\u0441\u043a\u0438\u0439 \u0445\u0438\u0440\u0443\u0440\u0433"}, "snils": "128-971-396 05", "familyName": "\u0423\u0448\u0430\u043a\u043e\u0432", "givenName": "\u0415\u0432\u0433\u0435\u043d\u0438\u0439", "middleName": "\u0410\u043d\u0434\u0440\u0435\u0435\u0432\u0438\u0447"}, "params": {"patientMRN": {"root": "1.2.643.5.1.13.3.25.58.47", "extension": "1"}, "status": "SUBMITTED", "docDateLow": "2014-02-01", "docDateHigh": "2014-02-26"}}}
        logger.info("request documentQuery: " + input);
        QueryIEMKServiceSoap port = getQueryIEMKServiceSoap(input);

        DocumentQuery documentQuery  = toEhrDocumentQuery(input);

        CallbackService.setRestCallback(this, messageUuid);
        port.documentQuery(documentQuery);
        synchronized(lockDocumentResponse) {
            try {
                lockDocumentResponse.wait(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        String resJson = (new Gson()).toJson(documentResponse == null ? getDocumentResponseNotFound() : documentResponse);
        logger.info("documentQuery response: " + resJson);
        documentResponse = null;
        return Response.status(Response.Status.OK).entity(resJson).build();
    }

    private DocumentResponse getDocumentResponseNotFound() {
        DocumentResponse res = CallbackFactory.createDocumentResponse();
        res.setDocuments(CallbackFactory.createArrayOfdocumentIEMKDocument());
        final IEMKDocument iemkDocument = CallbackFactory.createIEMKDocument();
        res.getDocuments().getDocument().add(iemkDocument);
        iemkDocument.setDocName("NotFound");
        return res;
    }

    public static RetrieveDocumentQuery toEhrRetrieveDocumentQuery(String input) {
        RetrieveDocumentQuery res = EhrFactory.createRetrieveDocumentQuery();
        QueryInp queryInp = fromJson(input);
        if (queryInp.data != null) {
            res.setFacilityCode(queryInp.data.facilityCode);
            final QueryInp.PatientQueryData.PatientQueryInitiatedBy initiatedBy = queryInp.data.initiatedBy;
            if (initiatedBy != null) {
                final Employee employee = toEhrEmployee(initiatedBy);
                res.setInitiatedBy(employee);
            }
            final QueryInp.PatientQueryData.PatientQueryParams params = queryInp.data.params;
            if (params != null) {
                final DocumentParams documentParams = EhrFactory.createDocumentParams();
                res.setDocumentId(queryInp.data.params.documentId);
            }
        }
        return res;
    }


    public static DocumentQuery toEhrDocumentQuery(String input) {
        DocumentQuery res = EhrFactory.createDocumentQuery();
        QueryInp queryInp = fromJson(input);
        if (queryInp.data != null) {
            res.setFacilityCode(queryInp.data.facilityCode);
            final QueryInp.PatientQueryData.PatientQueryInitiatedBy initiatedBy = queryInp.data.initiatedBy;
            if (initiatedBy != null) {
                final Employee employee = toEhrEmployee(initiatedBy);
                res.setInitiatedBy(employee);
            }
            final QueryInp.PatientQueryData.PatientQueryParams params = queryInp.data.params;
            if (params != null) {
                final DocumentParams documentParams = EhrFactory.createDocumentParams();
                res.setParams(documentParams);
                if (params.mrn != null) {
                    documentParams.setPatientMRN(toEhrInstanceIdentifier(params));
                }
                documentParams.setStatus(queryInp.data.params.status);
                try {
                    documentParams.setDocDateLow(Database.toGregorianCalendar(queryInp.data.params.docDateLow));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                try {
                    documentParams.setDocDateHigh(Database.toGregorianCalendar(queryInp.data.params.docDateHigh));
                } catch (DatatypeConfigurationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        return res;
    }

    public static PatientQuery toEhrPatientQuery(String input) {
        QueryInp queryInp = fromJson(input);
        PatientQuery patientQuery = EhrFactory.createPatientQuery();

        if (queryInp.data != null) {
            patientQuery.setFacilityCode(queryInp.data.facilityCode);
            final QueryInp.PatientQueryData.PatientQueryInitiatedBy initiatedBy = queryInp.data.initiatedBy;
            if (initiatedBy != null) {
                patientQuery.setInitiatedBy(toEhrEmployee(initiatedBy));
            }
            final QueryInp.PatientQueryData.PatientQueryParams params = queryInp.data.params;
            if (params != null) {
                final PatientParams patientParams = EhrFactory.createPatientParams();
                patientQuery.setParams(patientParams);
                patientParams.setGender(params.gender);
                //TODO Add to request!
                //patientParams.setDobHigh(???);
                //patientParams.setDobLow(???);
                if (params.mrn != null) {
                    patientParams.setMrn(toEhrInstanceIdentifier(params));
                }
                patientParams.setFamilyName(params.familyName);
                patientParams.setGivenName(params.givenName);
                patientParams.setMiddleName(params.middleName);
                patientParams.setSnils(params.snils.trim());
                if (params.omsPolicy != null) {
                    patientParams.setOmsPolicy(doEhrDoc(params.omsPolicy));
                }
                if (params.identityDocument != null) {
                    patientParams.setIdentityDocument(doEhrDoc(params.identityDocument));
                }
            }
        }
        return patientQuery;
    }

    private static QueryInp fromJson(String input) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd").create();
        return gson.fromJson(input, QueryInp.class);
    }

    private static InstanceIdentifier toEhrInstanceIdentifier(QueryInp.PatientQueryData.PatientQueryParams params) {
        final InstanceIdentifier instanceIdentifier = EhrFactory.createInstanceIdentifier();
        instanceIdentifier.setRoot(params.mrn.root);
        instanceIdentifier.setExtension(params.mrn.extension);
        return instanceIdentifier;
    }

    private static Employee toEhrEmployee(QueryInp.PatientQueryData.PatientQueryInitiatedBy initiatedBy) {
        final Employee employee = EhrFactory.createEmployee();
        final QueryInp.PatientQueryData.PatientQueryInitiatedBy.PatientQuerySpeciality speciality = initiatedBy.specialty;
        if (speciality != null) {
            employee.setCode(speciality.code);
            employee.setCodingSystem(speciality.codingSystem);
            employee.setName(speciality.name);
        }
        if (initiatedBy.role != null) {
            final CodeAndName codeAndName = EhrFactory.createCodeAndName();
            employee.setRole(codeAndName);
            codeAndName.setCode(initiatedBy.role.code);
            codeAndName.setCodingSystem(initiatedBy.role.codingSystem);
            codeAndName.setName(initiatedBy.role.name);
        }
        employee.setSnils(initiatedBy.snils.trim());
        employee.setFamilyName(initiatedBy.familyName);
        employee.setGivenName(initiatedBy.givenName);
        employee.setMiddleName(initiatedBy.middleName);
        return employee;
    }

    @POST
    @Consumes("application/json")
    @Path("/retrieveDocumentQuery/")
    public Response retrieveDocumentQuery(String input) {
        logger.info("request retrieveDocumentQuery: " + input);
        QueryIEMKServiceSoap port = getQueryIEMKServiceSoap(input);

        RetrieveDocumentQuery retrieveDocumentQuery  = toEhrRetrieveDocumentQuery(input);

        CallbackService.setRestCallback(this, messageUuid);
        port.retrieveDocumentQuery(retrieveDocumentQuery);
        synchronized(lockRetrieveDocumentResponse) {
            try {
                lockRetrieveDocumentResponse.wait(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
        String resJson = (new Gson()).toJson(retrieveDocumentResponse == null ? getRetrieveDocumentResponseNotFound() : retrieveDocumentResponse);
        logger.info("retrieveDocumentQuery response: " + resJson);
        retrieveDocumentResponse = null;
        return Response.status(Response.Status.OK).entity(resJson).build();
    }

    private RetrieveDocumentResponse getRetrieveDocumentResponseNotFound() {
        RetrieveDocumentResponse res = CallbackFactory.createRetrieveDocumentResponse();
        final Error error = CallbackFactory.createError();
        error.setErrorText("NotFound");
        res.getError().add(error);
        return res;
    }

    @Override
    public void containerResponse(BaseResponse parameters) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void patientQueryResponse(PatientResponse parameters) {
        synchronized(lockPatientResponse) {
            patientResponse = parameters;
            lockPatientResponse.notify();
        }
    }

    @Override
    public void documentQueryResponse(DocumentResponse parameters) {
        synchronized(lockDocumentResponse) {
            documentResponse = parameters;
            lockDocumentResponse.notify();
        }
    }

    @Override
    public void retrieveDocumentQueryResponse(RetrieveDocumentResponse parameters) {
        synchronized(lockRetrieveDocumentResponse) {
            retrieveDocumentResponse = parameters;
            lockRetrieveDocumentResponse.notify();
        }
    }

}
