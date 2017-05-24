package ru.bars.open.tmis.lis.innova.logic;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.bars.open.tmis.lis.innova.config.LisInnovaSettings;
import ru.bars.open.tmis.lis.innova.ws.generated.*;
import ru.korus.tmis.core.database.DbClientAddressBeanLocal;
import ru.korus.tmis.core.database.DbDiagnosisBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbCustomQueryLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.new_diagnosis.EventDiagnosis;
import ru.korus.tmis.core.entity.model.new_diagnosis.RbDiagnosisKind;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.BindingProvider;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 12.05.2016, 19:21 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Stateless(name = "LisInnovaBean")
public class LisInnovaBean {

    private static final Logger log = LoggerFactory.getLogger("LIS_INNOVA");

    @EJB
    private DbActionBeanLocal actionBean;

    @EJB
    private DbTakenTissueJournalBeanLocal dbTTJBean;

    @EJB
    private DbDiagnosisBeanLocal diagnosisBean;

    @EJB
    private DbCustomQueryLocal dbCustomQuery;

    @EJB
    private DbEventBeanLocal eventBean;

    @EJB
    private DbActionPropertyBeanLocal actionPropertyBean;


    @EJB
    private DbClientAddressBeanLocal clientAddressBean;


    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    public String sendTakenTissueJournal(final TakenTissue ttj) {
        log.info("Start process TTJ[{}]", ttj.getId());
        final String INNOVA_LAB_CODE = LisInnovaSettings.getLabCode();
        final List<Action> actionList = dbTTJBean.getActionsByTTJAndLaboratoryCode(ttj.getId(), INNOVA_LAB_CODE);
        if (actionList.isEmpty()) {
            log.warn("End process TTJ[{}]: No Action found for Laboratory[{}]", ttj.getId(), INNOVA_LAB_CODE);
            return String.format("No Action found for Laboratory[%s]", INNOVA_LAB_CODE);
        }
        log.info("Found {} actions for Laboratory[{}]", actionList.size(), INNOVA_LAB_CODE);
        for (Action action : actionList) {
            log.info("Action[{}] - \'{}\'", action.getId(), action.getActionType().getName());
        }
        final Event event = getEventByActionList(actionList);
        final EventType eventType = event.getEventType();
        final Patient patient = event.getPatient();
        final Staff doctor = getDoctorForLis(event, actionList);
        log.info("Event[{}]-\'[{}] {}\', Doctor[{}]-\'{}\'",
                event.getId(),
                eventType.getId(),
                eventType.getName(),
                doctor.getId(),
                doctor.getFullName()
        );
        final Map<EventDiagnosis, Diagnostic> diagnosisMap = diagnosisBean.getEventDiagnosisWithActualDiagnostic(event, ttj.getDatetimeTaken());
        if (diagnosisMap == null || diagnosisMap.isEmpty()) {
            log.warn("No diagnosis found for Event[{}] on date[{}]", event.getId(), ttj.getDatetimeTaken());
        } else {
            for (Map.Entry<EventDiagnosis, Diagnostic> x : diagnosisMap.entrySet()) {
                final EventDiagnosis eventDiagnosis = x.getKey();
                final Diagnostic diagnostic = x.getValue();
                log.debug("EventDiagnosis[{}]: {} {} Diagnosis[{}] Diagnostic[{}] {}",
                        eventDiagnosis.getId(),
                        eventDiagnosis.getDiagnosisType(),
                        eventDiagnosis.getDiagnosisKind(),
                        eventDiagnosis.getDiagnosis().getId(),
                        diagnostic.getId(),
                        diagnostic.getMkb()
                );
            }
        }

        final PatientInfo patientInfo = getPatientInfo(patient);
        final DiagnosticRequestInfo diagnosticRequestInfo = getDiagnosticRequestInfo(ttj, event, doctor, diagnosisMap);
        final BiomaterialInfo biomaterialInfo = getBiomaterialInfo(ttj);
        final ArrayOfOrderInfo arrayOfOrderInfo = getArrayOfOrderInfo(actionList);
        final ArrayOfExtraField arrayOfExtraField = getExtraFields(patient, event, actionList, diagnosisMap, ttj.getDatetimeTaken());
        diagnosticRequestInfo.setExtraFields(arrayOfExtraField);
        try {
            final Integer result = getWebService().queryAnalysis(patientInfo, diagnosticRequestInfo, biomaterialInfo, arrayOfOrderInfo);
            if (result == 0) {
                for (Action action : actionList) {
                    if (action.getStatus() == 0) {
                        actionBean.updateActionStatusWithFlush(action.getId(), (short) 1);
                    }
                }
                ttj.setNote("Sended successfully at " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                ttj.setStatus(3);
                em.merge(ttj);
                return "SENDED";
            }
            return "ERROR";

        } catch (Exception e) {
            log.error("End process TTJ[{}], Exception ", ttj.getId(), e);
            ttj.setNote("Error: " + e.getMessage());
            ttj.setStatus(4);
            em.merge(ttj);
            return e.getMessage();
        }

    }

    private IqueryAnalysis getWebService() {
        final QueryAnalysisService queryAnalysisService = new QueryAnalysisService(getClass().getClassLoader().getResource("lis_innova_single_wsdl_v2_withExtraFields.wsdl"));
        queryAnalysisService.setHandlerResolver(new JaxWsHandlerResolver());
        final IqueryAnalysis result = queryAnalysisService.getFTMISEndpoint();
        // Timeout in millis
        int requestTimeout = 10000;
        int connectTimeout = 1000;

        final Map<String, Object> requestContext = ((BindingProvider) result).getRequestContext();
        requestContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, LisInnovaSettings.getServiceURL().toExternalForm());
        //https://java.net/jira/browse/JAX_WS-1166
        // Weblogic JAX-WS properties
        requestContext.put("com.sun.xml.ws.connect.timeout", connectTimeout);
        requestContext.put("com.sun.xml.ws.request.timeout", requestTimeout);
        // JDK JAX-WS properties
        requestContext.put("com.sun.xml.internal.ws.connect.timeout", connectTimeout);
        requestContext.put("com.sun.xml.internal.ws.request.timeout", requestTimeout);
        // JBOSS CXF JAX-WS properties, warning these might change in the future (CXF-5261)
        requestContext.put("javax.xml.ws.client.connectionTimeout", connectTimeout);
        requestContext.put("javax.xml.ws.client.receiveTimeout", requestTimeout);

        log.info("WS initialized: {}", LisInnovaSettings.getServiceURL().toExternalForm());
        return result;
    }

    /**
     * @param patient
     * @param event
     * @param actionList
     * @param eventDiagnosisSet
     * @return
     * @see <a href="https://jira.bars-open.ru/browse/TMIS-1492">Тикет на доп поля в протоколе</a>
     */
    private ArrayOfExtraField getExtraFields(Patient patient, Event event, List<Action> actionList, Map<EventDiagnosis, Diagnostic> eventDiagnosisSet, Date actualDate) {
        final ObjectFactory of = new ObjectFactory();
        final ArrayOfExtraField result = of.createArrayOfExtraField();
        // diagnosisMIS	String	Диагноз(ы) пациента
        if (eventDiagnosisSet != null && !eventDiagnosisSet.isEmpty()) {
            // Тип диагноза: МКБ + Расшифровка из МКБ+ Description. Пример: Основной: С91.0 Острый лимфобластный лейкоз (Тра-та-та я дескрипшн);
            // Разделять через ;
            final ExtraField diagnosisMis = of.createExtraField();
            diagnosisMis.setName("diagnosisMIS");
            final StringBuilder sb = new StringBuilder();
            for (Iterator<Map.Entry<EventDiagnosis, Diagnostic>> iterator = eventDiagnosisSet.entrySet().iterator(); iterator.hasNext(); ) {
                final Map.Entry<EventDiagnosis, Diagnostic> entry = iterator.next();
                final Mkb mkb = entry.getValue().getMkb();
                sb.append(entry.getKey().getDiagnosisKind().getName()).append(": ").append(mkb.getDiagID()).append(" ").append(mkb.getDiagName());
                if(StringUtils.isNotEmpty(entry.getValue().getDiagnosis_description())){
                    sb.append('(').append(entry.getValue().getDiagnosis_description()).append(')');
                }
                if (iterator.hasNext()) {
                    sb.append("; ");
                }
            }
            diagnosisMis.setValue(sb.toString());
            result.getExtraField().add(diagnosisMis);
        }

        // Экшен - исследование на вич
        Action hivAction = null;
        for (Action action : actionList) {
            if (hivAction == null) {
                for (ActionPropertyType apt : action.getActionType().getActionPropertyTypes()) {
                    if ("hiv".equals(apt.getCode())) {
                        log.info("HIV research found: Action[{}] - \'{}\'", action.getId(), action.getActionType().getName());
                        hivAction = action;
                        break;
                    }
                }
            }
        }

        // registartionAddressMIS	String	Адрес регистрации пациента, при направлении его на исследование ВИЧ
        if (hivAction != null) {
            for (ClientAddress x : patient.getActiveClientAddresses()) {
                if (x.getAddressType() == 0) {
                    final ExtraField registartionAddressMis = of.createExtraField();
                    registartionAddressMis.setName("registartionAddressMIS");
                    registartionAddressMis.setValue(clientAddressBean.getFullAddressString(x));
                    result.getExtraField().add(registartionAddressMis);
                    break;
                }
            }
        }
        // contingentCodeMIS  String  Код контингента пациента, при направлении его на исследование ВИЧ
        if (hivAction != null) {
            for (ActionProperty ap : hivAction.getActionProperties()) {
                if ("contingent".equals(ap.getType().getCode())) {
                    try {
                        List<APValue> values = actionPropertyBean.getActionPropertyValue(ap);
                        if (values != null && !values.isEmpty() && StringUtils.isNotEmpty(values.get(0).getValueAsString())) {
                            final ExtraField contingentCodeMIS = of.createExtraField();
                            contingentCodeMIS.setName("contingentCodeMIS");
                            contingentCodeMIS.setValue(values.get(0).getValueAsString());
                            result.getExtraField().add(contingentCodeMIS);
                        } else {
                            log.debug("ExtraField[contingentCodeMIS]: empty AP value");
                        }
                    } catch (CoreException e) {
                        log.error("Exception while computing ExtraField[contingentCodeMIS]", e);
                    }
                    break;
                }
            }
        }

        // observationCommentMIS  String  Комментарий к исследованию
        // Это может быть и Action.note - если коммент ко всему исследование и ActionProperty.note если коммент к тесту.
        {
            final StringBuilder sb = new StringBuilder();
            for (final Action action : actionList) {
                if (StringUtils.isNotEmpty(action.getNote())) {
                    sb.append('[').append(action.getNote()).append(']');
                }
                for (ActionProperty property : action.getActionProperties()) {
                    // Проверка что свойство назначено в исследовании - экшене
                    if (!property.getDeleted()
                            && property.getIsAssigned()
                            && StringUtils.isNotEmpty(property.getNote())
                            && property.getType().getTest() != null) {
                        sb.append('[').append(property.getNote()).append(']');
                    }
                }
            }
            if(StringUtils.isNotEmpty(sb.toString())) {
                final ExtraField observationCommentMIS = of.createExtraField();
                observationCommentMIS.setName("observationCommentMIS");
                observationCommentMIS.setValue(sb.toString());
                result.getExtraField().add(observationCommentMIS);
            }
        }


        for (ExtraField x : result.getExtraField()) {
            log.info("ExtraField[{}]='{}'", x.getName(), x.getValue());
        }
        return result;
    }


    private Staff getDoctorForLis(final Event event, final List<Action> actionList) {
        //https://jira.bars-open.ru/browse/TMIS-1286
        /* 1. Передавать в ЛИС id и ФИО, подразделение фактически назначившего исследование врача только в случае,
               если тип финансирования у текущего обращения "Административное разрешение";
           2. Во всех случаях, за исключением пункта 1 передавать в ЛИС id и ФИО, подразделение лечащего врача,
                указанного в обращении (Event.execPerson_id).  */
        Staff result = null;
        final EventType eventType = event.getEventType();
        if (eventType.getFinance() != null
                && LisInnovaSettings.getAdministrativePermissionRbFinanceCode().equals(eventType.getFinance().getCode())) {
            result = actionList.get(0).getAssigner();
        }
        return result != null ? result : event.getExecutor();
    }

    private ArrayOfOrderInfo getArrayOfOrderInfo(final List<Action> actionList) {
        final ObjectFactory of = new ObjectFactory();
        final ArrayOfOrderInfo result = of.createArrayOfOrderInfo();
        for (Action action : actionList) {
            final OrderInfo item = getOrderInfo(action);
            if (item != null) {
                result.getOrderInfo().add(item);
            } else {
                log.warn("Action[{}] not converted to OrderInfo!", action.getId());
            }
        }
        if (result.getOrderInfo().isEmpty()) {
            log.error("ArrayOfOrderInfo is empty.");
        }
        return result;
    }

    private OrderInfo getOrderInfo(final Action action) {
        final ObjectFactory of = new ObjectFactory();
        final OrderInfo result = of.createOrderInfo();
        final ActionType actionType = action.getActionType();
        result.setDiagnosticCode(of.createOrderInfoDiagnosticCode(actionType.getCode()));
        result.setDiagnosticName(of.createOrderInfoDiagnosticName(actionType.getCode()));
        result.setOrderPriority(action.getIsUrgent() ? 1 : 0);
        final ArrayOfTindicator arrayOfTindicator = of.createArrayOfTindicator();
        for (ActionProperty property : action.getActionProperties()) {
            // Проверка что свойство назначено в исследовании - экшене
            if (!property.getDeleted() && property.getIsAssigned()) {
                final RbTest test = property.getType().getTest();
                if (test != null) {
                    final Tindicator tindicator = of.createTindicator();
                    tindicator.setIndicatorCode(of.createTindicatorIndicatorCode(test.getCode()));
                    tindicator.setIndicatorName(of.createTindicatorIndicatorName(test.getName()));
                    arrayOfTindicator.getTindicator().add(tindicator);
                }
            }
        }
        if (arrayOfTindicator.getTindicator().isEmpty()) {
            log.warn("#{} Action has not Tindicator and must be skipped");
            return null;
        }
        result.setIndicators(of.createOrderInfoIndicators(arrayOfTindicator));
        return result;
    }

    private BiomaterialInfo getBiomaterialInfo(final TakenTissue ttj) {
        final ObjectFactory of = new ObjectFactory();
        final BiomaterialInfo result = of.createBiomaterialInfo();
        final RbTissueType tissueType = ttj.getType();
        result.setOrderBiomaterialCode(of.createBiomaterialInfoOrderBiomaterialCode(tissueType.getCode()));
        result.setOrderBiomaterialName(of.createBiomaterialInfoOrderBiomaterialName(tissueType.getName()));
        result.setOrderBarCode(of.createBiomaterialInfoOrderBarCode(String.valueOf(ttj.getBarcode())));
        result.setOrderPrefBarCode(ttj.getPeriod());
        result.setOrderProbeDate(date2xmlGC(ttj.getDatetimeTaken()));
        result.setOrderBiomaterialComment(of.createBiomaterialInfoOrderBiomaterialComment(ttj.getNote()));
        return result;
    }


    private PatientInfo getPatientInfo(Patient patient) {
        final ObjectFactory of = new ObjectFactory();
        final PatientInfo result = of.createPatientInfo();
        result.setPatientMisId(patient.getId());
        result.setPatientFamily(of.createPatientInfoPatientFamily(patient.getLastName()));
        result.setPatientName(of.createPatientInfoPatientName(patient.getFirstName()));
        result.setPatientPatronum(of.createPatientInfoPatientPatronum(patient.getPatrName()));
        result.setPatientBirthDate(of.createPatientInfoPatientBirthDate(getDateAsString(patient.getBirthDate())));
        result.setPatientSex((int) patient.getSex());
        return result;
    }

    private DiagnosticRequestInfo getDiagnosticRequestInfo(final TakenTissue ttj, final Event e, final Staff doctor, final Map<EventDiagnosis, Diagnostic> diagnoisMap) {
        final ObjectFactory of = new ObjectFactory();
        final DiagnosticRequestInfo result = of.createDiagnosticRequestInfo();
        result.setOrderMisId(ttj.getId());
        // номер истории болезни eventid
        result.setOrderCaseId(of.createDiagnosticRequestInfoOrderCaseId(e.getExternalId()));
        // код финансирования
        result.setOrderFinanceId(dbCustomQuery.getFinanceId(e));
        // CreateDate (datetime) -- дата создания направления врачом (TTJ.datetimeTaken)
        result.setOrderMisDate(date2xmlGC(ttj.getDatetimeTaken()));
        // PregnancyDurationWeeks (int) -- срок беременности пациентки (в неделях)
        result.setOrderPregnat(e.getPregnancyWeek() * 7);
        // Основной диагноз
        if (diagnoisMap != null && !diagnoisMap.isEmpty()) {
            for (Map.Entry<EventDiagnosis, Diagnostic> x : diagnoisMap.entrySet()) {
                if (RbDiagnosisKind.MAIN.equals(x.getKey().getDiagnosisKind().getCode())) {
                    final Mkb mkb = x.getValue().getMkb();
                    result.setOrderDiagCode(of.createDiagnosticRequestInfoOrderDiagCode(mkb.getDiagID()));
                    result.setOrderDiagText(of.createDiagnosticRequestInfoOrderDiagText(mkb.getDiagName()));
                    break;
                }
            }
        }

        // DepartmentName (string) -- название подразделения - отделение
        // DepartmentCode (string) -- уникальный код подразделения (отделения)
        final OrgStructure department = eventBean.getOrgStructureForEvent(e);
        result.setOrderDepartmentMisId(of.createDiagnosticRequestInfoOrderDepartmentMisId(String.valueOf(department.getId())));
        result.setOrderDepartmentName(of.createDiagnosticRequestInfoOrderDepartmentName(department.getName()));

        // Comment (string) (необязательно) – произвольный текстовый комментарий к направлению
        result.setOrderComment(of.createDiagnosticRequestInfoOrderComment(ttj.getNote()));
        // TODO Врач необходимо будет согласовать перенос этого внутрь OrderInfo, т.к. должно браться из Action.assgner
        result.setOrderDoctorFamily(of.createDiagnosticRequestInfoOrderDoctorFamily(doctor.getLastName()));
        result.setOrderDoctorName(of.createDiagnosticRequestInfoOrderDoctorName(doctor.getFirstName()));
        result.setOrderDoctorPatronum(of.createDiagnosticRequestInfoOrderDoctorPatronum(doctor.getPatrName()));
        result.setOrderDoctorMisId(of.createDiagnosticRequestInfoOrderDoctorMisId(doctor.getId().toString()));
        return result;
    }


    private String getDateAsString(final Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    private XMLGregorianCalendar date2xmlGC(Date date) {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return new XMLGregorianCalendarImpl(c);
    }


    private Event getEventByActionList(final List<Action> actions) {
        final Set<Event> set = new LinkedHashSet<>(actions.size());
        for (Action action : actions) {
            set.add(action.getEvent());
        }
        if (set.size() != 1) {
            log.error("Event is not unique in this Actions! Events = {}", set);
        }
        return set.iterator().next();
    }


}
