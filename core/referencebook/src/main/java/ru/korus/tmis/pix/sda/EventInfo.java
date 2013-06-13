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
    /**
     * Уникальный идентификатор в МИС
     */
    final private Integer id;
    /**
     * Краткое наименование ЛПУ
     */
    final private String orgName;
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
        this.id = event.getId();
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
        this.orgName = event.getOrganisation() == null ? null : event.getOrganisation().getFullName();
        this.type = event.getEventType().getId();
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
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

}
