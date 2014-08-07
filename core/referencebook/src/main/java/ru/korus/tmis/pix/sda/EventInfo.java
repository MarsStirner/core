package ru.korus.tmis.pix.sda;

import java.util.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.common.collect.Multimap;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.patient.HospitalBedBeanLocal;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.06.2013, 13:07:05 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class EventInfo {
    private static final int MAX_ORG_NAME_LENGHT = 50;
    private static final String ORG_UNKNOWN = "unknown";
    public static final CodeNameSystem UNKNOWN_ORG = CodeNameSystem.newInstance("???", ORG_UNKNOWN, "1.2.643.5.1.13.2.1.1.178");
    private static final ru.korus.tmis.pix.sda.CodeNameSystem CODE_NAME_SYSTEM = CodeNameSystem.newInstance("???", ORG_UNKNOWN, "1.2.643.5.1.13.2.1.1.178");
    /**
     * UUID события
     */
    private final String eventUuid;
    /**
     * Краткое наименование ЛПУ
     */
    //final private String orgName;
    /**
     * Код ЛПУ из справочника 1.2.643.5.1.13.2.1.1.178 (MDR308)
     */
    final private CodeNameSystem orgOid;
    /**
     * Код типа обращения (rbRequestType.code)
     */
    final private String requestType;
    /**
     * Дата/время начала амбулаторного приема или поступления в стационар
     */
    final private XMLGregorianCalendar begDate;
    /**
     * Дата/время окончания амбулаторного приема или выписки из стационара
     */
    final private XMLGregorianCalendar endDate;
    /**
     * EventType
     */
    final private Integer type;

    final private String[] inpatient = { "clinic", "hospital", "stationary" };

    private final EmployeeInfo autorInfo;

    private final CodeNameSystem financeType;
    private final boolean atHome;
    private final CodeNameSystem orgStructure;
    private final AdmissionInfo admissionInfo;
    private final CodeNameSystem encounterResult;

    /**
     * Идентификатор текущего обращения
     */
    private final String eventId;

    /**
     * Исход заболевания
     */
    private final CodeNameSystem encounterOutcome;

    /**
     * Вид помощи
     */
    private final CodeNameSystem careType;

    /**
     * Номер истории болезни (номер медицинской карты)
     */
    private final String externalId;

    public EventInfo(Event event, Multimap<String, Action> actions, DbActionPropertyBeanLocal dbActionPropertyBeanLocal, HospitalBedBeanLocal hospitalBedBeanLocal) {
        final ru.korus.tmis.core.entity.model.UUID uuid = event.getUuid();
        this.eventUuid = uuid != null ? uuid.getUuid() : null;
        RbRequestType requestType = event.getEventType().getRequestType();
        this.requestType = requestType == null ? null : requestType.getCode();
        XMLGregorianCalendar begDate = null;
        XMLGregorianCalendar endDate = null;
        try {
            begDate = Database.toGregorianCalendar(event.getSetDate());
            endDate = Database.toGregorianCalendar(event.getExecDate());
        } catch (DatatypeConfigurationException e) {
        }

        this.begDate = begDate;
        this.endDate = endDate;
        //this.orgName = getOrgShortName(event);
        this.orgOid = getOrgOid(event);
        this.type = event.getEventType().getId();

        autorInfo = EmployeeInfo.newInstance(event.getCreatePerson());
        final RbFinance finance = event.getEventType().getFinance();
        financeType = finance == null ? null : RbManager.get(RbManager.RbType.rbFinance,
                CodeNameSystem.newInstance(finance.getCode(), finance.getName(), "1.2.643.5.1.13.2.1.1.528"));
        atHome = initAtHome(event.getEventType());
        orgStructure = initOrgStructByPerson(event.getExecutor());
        admissionInfo = new AdmissionInfo(event, actions, dbActionPropertyBeanLocal, hospitalBedBeanLocal);
        encounterResult = event.getResult() == null ? null : RbManager.get(RbManager.RbType.rbResult,
                CodeNameSystem.newInstance(event.getResult().getCode(), event.getResult().getName(), "1.2.643.5.1.13.2.1.1.123"));
        eventId = String.valueOf(event.getId());
        encounterOutcome = event.getAcheResult() == null ? null : RbManager.get(RbManager.RbType.rbAcheResult,
                CodeNameSystem.newInstance(event.getAcheResult().getCode(), event.getAcheResult().getName(), "1.2.643.5.1.13.2.1.1.357"));

        final RbMedicalAidType type = event.getEventType().getRbMedicalAidType();
        this.careType = type == null ? null : new CodeNameSystem(type.getCode(), type.getName());
        this.externalId = event.getExternalId();
    }

    private CodeNameSystem initOrgStructByPerson(Staff executor) {
        if(executor == null) {
            return null;
        }
        final OrgStructure orgStructure = executor.getOrgStructure();
        if(orgStructure == null) {
            return null;
        }
        return  new CodeNameSystem(orgStructure.getCode(), orgStructure.getName());
    }

    private boolean initAtHome(EventType event) {
        final RbScene rbScene = event.getRbScene();
        boolean res = false;
        if(rbScene != null && rbScene.getName() != null) {
            res = rbScene.getName().toLowerCase().contains("дом");
        }
        return res;
    }

    public EventInfo(CodeNameSystem defaultOrgShortName ) {
       // this.orgName = isEmptyString(defaultOrgShortName) ? ORG_UNKNOWN : defaultOrgShortName;
        eventUuid = null;
        requestType = null;
        begDate = null;
        endDate = null;
        type = null;
        orgOid = defaultOrgShortName;
        autorInfo = null;
        financeType = null;
        atHome = false;
        orgStructure = null;
        admissionInfo = null;
        encounterResult = null;
        eventId = null;
        encounterOutcome = null;
        careType = null;
        externalId = null;
    }

    private static boolean isEmptyString(String shortName) {
        return shortName == null || "".equals(shortName);
    }

    private static CodeNameSystem getOrgOid(Event event) {
        final Organisation organisation = event.getOrganisation();
        return getOrgCodeName(organisation);
    }

    public static CodeNameSystem getOrgCodeName(Organisation organisation) {
        if ( organisation == null || isEmptyString(organisation.getInfisCode())) {
            return UNKNOWN_ORG;
        } else {
            CodeNameSystem res = RbManager.get(RbManager.RbType.Organisation, CodeNameSystem.newInstance(organisation.getInfisCode(), organisation.getShortName(), null));
            if(res == null || res.getName() == null || res.getName().isEmpty()) {
                return UNKNOWN_ORG;
            }
            return res;
        }
    }

    /**
     * @return the id
     */
    public String getUuid() {
        return eventUuid;
    }

    /**
     * @return the orgName
     */
   /* public String getOrgName() {
        return orgName;
    }*/

    /**
     * @return the begDate
     */
    public XMLGregorianCalendar getBegDate() {
        return begDate;
    }

    /**
     * @return the endDate
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * @return the type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @return the requestType
     */
    public String getRequestType() {
        return requestType;
    }

    /**
     * @return
     */
    public boolean isInpatient() {
        return Arrays.asList(inpatient).indexOf(requestType) >= 0;
    }

    public CodeNameSystem getOrgOid() {
        return orgOid;
    }

    public EmployeeInfo getAutorInfo() {
        return autorInfo;
    }

    public CodeNameSystem getFinanceType() {
        return financeType;
    }

    public boolean isAtHome() {
        return atHome;
    }

    public CodeNameSystem getOrgStructure() {
        return orgStructure;
    }

    public AdmissionInfo getAdmissionInfo() {
        return admissionInfo;
    }

    public CodeNameSystem getEncounterResult() {
        return encounterResult;
    }

    public String getEventId() {
        return eventId;
    }

    public CodeNameSystem getEncounterOutcome() {
        return encounterOutcome;
    }

    public CodeNameSystem getCareType() {
        return careType;
    }

    public String getExternalId() {
        return externalId;
    }
}
