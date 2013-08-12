package ru.korus.tmis.pix.sda;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.EntityMgr;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        09.07.2013, 16:37:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class EpicrisisInfo {

    private static final Logger logger = LoggerFactory.getLogger(PixInfo.class);

    /**
     * UUID события
     */
    private final String eventUuid;

    /**
     * код типа документа (по справочнику типов документов, поддерживаемому в МИС)
     */
    private final String code;

    /**
     * название типа документа
     */
    private final String docName;

    /**
     * Дата/время диагноза
     */
    private final XMLGregorianCalendar createDate;

    /**
     * текст документа
     */
    private final String text;

    /**
     * Идентификатор врача, зафиксировавшего данные
     */
    private final Integer personCreatedId;

    /**
     * Фамилия
     */
    final private String familyName;
    /**
     * Имя
     */
    final private String givenName;
    /**
     * Отчество
     */
    final private String middleName;

    public EpicrisisInfo(Action action, ClientInfo clientInfo, String orgName, DbActionPropertyBeanLocal apBean) {
        Event event = action.getEvent();
        assert event != null;
        final UUID uuid = event.getUuid();
        this.eventUuid = uuid != null ? uuid.getUuid() : null;

        this.code = action.getActionType().getMnemonic();
        this.docName = action.getActionType().getName();

        XMLGregorianCalendar createDate = null;
        try {
            createDate = EntityMgr.toGregorianCalendar(action.getEndDate());
        } catch (DatatypeConfigurationException e) {
        }
        this.createDate = createDate;

        Staff staff = action.getCreatePerson();
        this.personCreatedId = staff != null ? staff.getId() : null;
        this.familyName = staff != null ? staff.getLastName() : "";
        this.givenName = staff != null ? staff.getFirstName() : "";
        this.middleName = staff != null ? staff.getPatrName() : "";
        this.text = genEpicrisisText(action, clientInfo, orgName, apBean);
    }

    private String genEpicrisisText(Action action, ClientInfo clientInfo, String orgName, DbActionPropertyBeanLocal apBean) {
        final char XML_EOL = '\n';
        StringBuilder res = new StringBuilder();
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        res.append(orgName).append(' ')
                .append("Дата и время: ").append(createDate != null ? dateFormat.format(createDate.toGregorianCalendar().getTime()) : "???").append(' ')
                .append("Амбулаторная карта №").append(action.getEvent().getExternalId()).append(' ')
                .append("Ф.И.О. пациента: " + clientInfo.getFamilyName() + ' ' + clientInfo.getGivenName() + ' ' + clientInfo.getMiddleName())
                .append("Возраст: " + (createDate != null && clientInfo.getBirthDate() != null ? getAge(createDate, clientInfo.getBirthDate()) : "???"))
                .append(XML_EOL);
        Map<ActionProperty, List<APValue>> ap;
        try {
            ap = apBean.getActionPropertiesByActionId(action.getId());
            for (Map.Entry<ActionProperty, List<APValue>> prop : ap.entrySet()) {
                if (prop.getValue() != null && !prop.getValue().isEmpty()) {
                    res.append(prop.getKey().getType().getName() + ": " + prop.getValue().get(0).getValueAsString()).append(XML_EOL);
                }
            }
        } catch (CoreException e) {
            e.printStackTrace();
        }
        if (personCreatedId != null) {
            res.append("идентификатор врача: " + personCreatedId).append(XML_EOL);

        }
        res.append(" " + familyName + ' ' + givenName + ' ' + middleName).append(XML_EOL);
        return res.toString();
    }

    /**
     * @param createDate2
     * @param birthDate
     * @return
     */
    private String getAge(XMLGregorianCalendar createDate, XMLGregorianCalendar birthDate) {
        DateTime birth = new DateTime(birthDate.toGregorianCalendar());
        DateTime create = new DateTime(createDate.toGregorianCalendar());
        Integer years = Years.yearsBetween(birth, create).getYears();
        Integer months = Months.monthsBetween(birth, create).getMonths();
        Integer days = Days.daysBetween(birth, create).getDays();
        final String monthsName = months + " мес.";
        final String daysName = days + " дн.";
        if (years > 3) {
            return years + getAgesName(years);
        } else if (years >= 1) {
            return years + getAgesName(years) + (months > 0 ? (" " + monthsName) : "");
        } else if (months > 0) {
            return monthsName + (days > 0 ? (" " + daysName) : "");
        }
        return daysName;
    }

    private String getAgesName(final Integer value) {
        final Integer mod = value % 10;
        final String LET = " лет";
        if (10 < value && value < 20) {
            return LET;
        } else if (mod == 1) {
            return " год";
        } else if (mod != 0 && mod <= 4) {
            return " годa";
        }

        return LET;
    }

    /**
     * @return the eventUuid
     */
    public String getEventUuid() {
        return eventUuid;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the name
     */
    public String getDocName() {
        return docName;
    }

    /**
     * @return the createDate
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @return the personCreatedId
     */
    public Integer getPersonCreatedId() {
        return personCreatedId;
    }

    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

}
