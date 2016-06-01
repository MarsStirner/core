package ru.korus.tmis.lis.innova.logic;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbDiagnosticBeanLocal;
import ru.korus.tmis.core.database.DbJobTicketBeanLocal;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbCustomQueryLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.innova.config.Constants;
import ru.korus.tmis.lis.innova.config.LisInnovaSettings;
import ru.korus.tmis.lis.innova.ws.generated.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
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
    private DbJobTicketBeanLocal jobTicketBean;

    @EJB
    private DbTakenTissueJournalBeanLocal dbTTJBean;

    @EJB
    private DbDiagnosticBeanLocal dbDiagnosticBean;

    @EJB
    private DbCustomQueryLocal dbCustomQuery;

    @EJB
    private DbEventBeanLocal eventBean;


    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    public String sendTakenTissueJournal(final TakenTissue ttj, final long logNumber) {
        log.info("#{} Start process TTJ[{}]", logNumber, ttj.getId());
        final String INNOVA_LAB_CODE = LisInnovaSettings.getLabCode();
        final List<Action> actionList = dbTTJBean.getActionsByTTJAndLaboratoryCode(ttj.getId(), INNOVA_LAB_CODE);
        if (actionList.isEmpty()) {
            log.warn("#{} End process TTJ[{}]: No Action found for Laboratory[{}]", logNumber, ttj.getId(), INNOVA_LAB_CODE);
            return String.format("No Action found for Laboratory[%s]", INNOVA_LAB_CODE);
        }
        log.info("#{} Found {} actions for Laboratory[{}]", logNumber, actionList.size(), INNOVA_LAB_CODE);
        for (Action action : actionList) {
            log.info("#{} Action[{}] - \'{}\'", logNumber, action.getId(), action.getActionType().getName());
        }
        final Event event = getEventByActionList(actionList, logNumber);
        final Patient patient = ttj.getPatient();
        final Staff doctor = actionList.get(0).getAssigner();
        log.info("#{} Event[{}]", logNumber, event.getId());
        final PatientInfo patientInfo = getPatientInfo(patient);
        final DiagnosticRequestInfo diagnosticRequestInfo = getDiagnosticRequestInfo(ttj, event, doctor, logNumber);
        final BiomaterialInfo biomaterialInfo = getBiomaterialInfo(ttj);
        final ArrayOfOrderInfo arrayOfOrderInfo = getArrayOfOrderInfo(actionList, logNumber);
        try {
            final QueryAnalysisService queryAnalysisService = new QueryAnalysisService(LisInnovaSettings.getServiceURL());
            queryAnalysisService.setHandlerResolver(new JaxWsHandlerResolver());
            final IqueryAnalysis service = queryAnalysisService.getFTMISEndpoint();
            final Integer result = service.queryAnalysis(patientInfo, diagnosticRequestInfo, biomaterialInfo, arrayOfOrderInfo);
            if (result == 0) {
                for (Action action : actionList) {
                    actionBean.updateActionStatusWithFlush(action.getId(), (short) 2);
                }
                ttj.setNote("Sended successfully at "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                ttj.setStatus(2);
                em.merge(ttj);
                return "SENDED";
            }
            return "ERROR";

        } catch (Exception e) {
            log.error("#{} End process TTJ[{}], Exception ",logNumber, ttj.getId(), e);
            ttj.setNote("Error: "+e.getMessage());
            em.merge(ttj);
            return e.getMessage();
        }

    }

    private ArrayOfOrderInfo getArrayOfOrderInfo(final List<Action> actionList, final long logNumber) {
        final ObjectFactory of = new ObjectFactory();
        final ArrayOfOrderInfo result = of.createArrayOfOrderInfo();
        for (Action action : actionList) {
            final OrderInfo item = getOrderInfo(action);
            if (item != null) {
                result.getOrderInfo().add(item);
            } else {
                log.warn("#{} Action[{}] not converted to OrderInfo!", logNumber, action.getId());
            }
        }
        if (result.getOrderInfo().isEmpty()) {
            log.error("#{} ArrayOfOrderInfo is empty.", logNumber);
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
            if(!property.getDeleted() && property.getIsAssigned()) {
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

    private DiagnosticRequestInfo getDiagnosticRequestInfo(TakenTissue ttj, Event e, Staff doctor, long logNumber) {
        final ObjectFactory of = new ObjectFactory();
        final DiagnosticRequestInfo result = of.createDiagnosticRequestInfo();
        result.setOrderMisId(ttj.getId());
        // номер истории болезни eventid
        result.setOrderCaseId(of.createDiagnosticRequestInfoOrderCaseId(e.getExternalId()));
        // код финансирования
        result.setOrderFinanceId(dbCustomQuery.getFinanceId(e));
        // CreateDate (datetime) -- дата создания направления врачом (Action.createDatetime)
        result.setOrderMisDate(date2xmlGC(ttj.getDatetimeTaken()));
        // PregnancyDurationWeeks (int) -- срок беременности пациентки (в неделях)
        result.setOrderPregnat(e.getPregnancyWeek() * 7);
        final Mkb diagnosis = getDiagnosisForEvent(e, logNumber);
        if (diagnosis != null) {
            result.setOrderDiagCode(of.createDiagnosticRequestInfoOrderDiagCode(diagnosis.getDiagID()));
            result.setOrderDiagText(of.createDiagnosticRequestInfoOrderDiagText(diagnosis.getDiagName()));
        }
        // DepartmentName (string) -- название подразделения - отделение
        // DepartmentCode (string) -- уникальный код подразделения (отделения)
        try {
            final OrgStructure department = e.getOrgStructure() != null ? e.getOrgStructure() : eventBean.getOrgStructureForEvent(e.getId());
            result.setOrderDepartmentMisId(of.createDiagnosticRequestInfoOrderDepartmentMisId(department.getCode()));
            result.setOrderDepartmentName(of.createDiagnosticRequestInfoOrderDepartmentName(department.getName()));
        } catch (CoreException ex) {
            log.error("#{} Handled Exception while getting OrgStructure for Event[{}]: {}", logNumber, e.getId(), ex.getMessage());
        }
        // Comment (string) (необязательно) – произвольный текстовый комментарий к направлению
        result.setOrderComment(of.createDiagnosticRequestInfoOrderComment(ttj.getNote()));
        // TODO Врач необходимо будет согласовать перенос этого внутрь OrderInfo, т.к. должно браться из Action.assgner
        result.setOrderDoctorFamily(of.createDiagnosticRequestInfoOrderDoctorFamily(doctor.getLastName()));
        result.setOrderDoctorName(of.createDiagnosticRequestInfoOrderDoctorName(doctor.getFirstName()));
        result.setOrderDoctorPatronum(of.createDiagnosticRequestInfoOrderDoctorPatronum(doctor.getPatrName()));
        result.setOrderDoctorMisId(of.createDiagnosticRequestInfoOrderDoctorMisId(doctor.getId().toString()));
        return result;
    }

    private Mkb getDiagnosisForEvent(final Event e, final long logNumber) {
        try {
            final List<Diagnostic> diagnostics = dbDiagnosticBean.getDiagnosticsByEventIdAndTypes(
                    e.getId(), Constants.DIAGNOSIS_CODES_FOR_SEND
            );
            if (diagnostics.isEmpty()) {
                log.warn("#{} No diagnosis found for Event[{}]", logNumber, e.getId());
            } else {
                final Diagnosis diag = diagnostics.iterator().next().getDiagnosis();
                return diag.getMkb();
            }
        } catch (CoreException ex) {
            log.error("#{} Handled Exception while getting diagnosis for Event[{}]: {}", logNumber, e.getId(), ex.getMessage());
        }
        return null;
    }


    private String getDateAsString(final Date date) {
        return new SimpleDateFormat("dd.MM.yyyy").format(date);
    }

    private XMLGregorianCalendar date2xmlGC(Date date) {
        final GregorianCalendar c = new GregorianCalendar();
        c.setTime(date);
        return new XMLGregorianCalendarImpl(c);
    }


    private Event getEventByActionList(final List<Action> actions, final long logNumber) {
        final Set<Event> set = new LinkedHashSet<>(actions.size());
        for (Action action : actions) {
            set.add(action.getEvent());
        }
        if (set.size() != 1) {
            log.error("#{} Event is not unique in this Actions! Events = {}", logNumber, set);
        }
        return set.iterator().next();
    }


}
