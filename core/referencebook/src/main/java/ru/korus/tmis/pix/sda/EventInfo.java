package ru.korus.tmis.pix.sda;

import java.util.Arrays;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.RbRequestType;

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

    public EventInfo(Event event) {
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

    }
    public EventInfo(String defaultOrgShortName ) {
        this.orgName = isEmptyString(defaultOrgShortName) ? ORG_UNKNOWN : defaultOrgShortName;
        eventUuid = null;
        requestType = null;
        begDate = null;
        endDate = null;
        type = null;
        orgOid = null;
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
}
