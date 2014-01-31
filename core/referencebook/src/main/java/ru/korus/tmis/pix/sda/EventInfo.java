package ru.korus.tmis.pix.sda;

import java.util.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import com.google.common.collect.Multimap;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;

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
    /**
     * UUID события
     */
    private final String eventUuid;
    /**
     * Краткое наименование ЛПУ
     */
    final private String orgName;
    /**
     * Код ЛПУ из справочника 1.2.643.5.1.13.2.1.1.178 (MDR308)
     */
    final private String orgOid;
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

    private final CodeNamePair financeType;
    private final boolean atHome;
    private final CodeNamePair orgStructure;
    private final AdmissionInfo admissionInfo;
    private final CodeNamePair encounterResult;

    /**
     * Идентификатор текущего обращения
     */
    private final String eventId;

    public EventInfo(Event event, Multimap<String, Action> actions, DbActionPropertyBeanLocal dbActionPropertyBeanLocal) {
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
        this.orgName = getOrgShortName(event);
        this.orgOid = getOrgOid(event);
        this.type = event.getEventType().getId();

        autorInfo = EmployeeInfo.newInstance(event.getCreatePerson());
        final RbFinance finance = event.getEventType().getFinance();
        financeType = finance == null ? null : new CodeNamePair(finance.getCode(), finance.getName());
        atHome = initAtHome(event.getEventType());//TODO: Impl:  rbScene.name<=rbScene.id<=EventType.scene_id<=EventType.id<=Event.id (если значение содержит "дом" то передаем "true", иначе "false")
        orgStructure = initOrgStructByPerson(event.getExecutor());
        admissionInfo = new AdmissionInfo(event, actions, dbActionPropertyBeanLocal);
        encounterResult = event.getResult() == null ? null : new CodeNamePair(event.getResult().getCode(), event.getResult().getName());
        eventId = String.valueOf(event.getId());
    }

    private CodeNamePair initOrgStructByPerson(Staff executor) {
        if(executor == null) {
            return null;
        }
        final OrgStructure orgStructure = executor.getOrgStructure();
        if(orgStructure == null) {
            return null;
        }
        return  new CodeNamePair(orgStructure.getCode(), orgStructure.getName());
    }

    private boolean initAtHome(EventType event) {
        //TODO: Impl:  rbScene.name<=rbScene.id<=EventType.scene_id<=EventType.id<=Event.id (если значение содержит "дом" то передаем "true", иначе "false")
        return false;
    }

    public EventInfo(String defaultOrgShortName ) {
        this.orgName = isEmptyString(defaultOrgShortName) ? ORG_UNKNOWN : defaultOrgShortName;
        eventUuid = null;
        requestType = null;
        begDate = null;
        endDate = null;
        type = null;
        orgOid = null;
        autorInfo = null;
        financeType = null;
        atHome = false;
        orgStructure = null;
        admissionInfo = null;
        encounterResult = null;
        eventId = null;
    }

    /**
     * @param event
     * @return
     */
    public static String getOrgShortName(Event event) {
        if (event.getOrganisation() == null || isEmptyString(event.getOrganisation().getShortName())) {
            return ORG_UNKNOWN;
        } else {
            String res = event.getOrganisation().getShortName();
            return res.substring(0, Math.min(MAX_ORG_NAME_LENGHT, res.length()));
        }
    }

    private static boolean isEmptyString(String shortName) {
        return shortName == null || "".equals(shortName);
    }

    private static String getOrgOid(Event event) {
        if ( event.getOrganisation() == null || isEmptyString(event.getOrganisation().getOid())) {
            return ORG_UNKNOWN;
        } else {
            return event.getOrganisation().getOid();
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
    public String getOrgName() {
        return orgName;
    }

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

    public String getOrgOid() {
        return orgOid;
    }

    public EmployeeInfo getAutorInfo() {
        return autorInfo;
    }

    public CodeNamePair getFinanceType() {
        return financeType;
    }

    public boolean isAtHome() {
        return atHome;
    }

    public CodeNamePair getOrgStructure() {
        return orgStructure;
    }

    public AdmissionInfo getAdmissionInfo() {
        return admissionInfo;
    }

    public CodeNamePair getEncounterResult() {
        return encounterResult;
    }

    public String getEventId() {
        return eventId;
    }
}
