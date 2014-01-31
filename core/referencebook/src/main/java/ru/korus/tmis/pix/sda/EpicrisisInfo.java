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
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.core.exception.CoreException;

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

    private static final Logger logger = LoggerFactory.getLogger(SdaBuilder.class);

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
     * Идентификатор в МИС
     */
    private final String id;

    /**
     * Автор записи (врач)
     */
    private final EmployeeInfo createdPerson;

    /**
     * Дата/время ввода записи в МИС
     */
    private final XMLGregorianCalendar endDate;

    /**
     * Тип документа
     */
    private final CodeNamePair docType;

    public EpicrisisInfo(Action action, ClientInfo clientInfo, String orgName, DbActionPropertyBeanLocal apBean) {
        this.code = action.getActionType().getMnemonic();
        this.docName = action.getActionType().getName();
        this.createDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        this.id = String.valueOf(action.getId());
        this.createdPerson =  EmployeeInfo.newInstance(action.getCreatePerson());
        this.endDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        CodeNamePair docType = null;
        if( "EPI".equals(action.getActionType().getMnemonic())) {
            docType = new CodeNamePair("1", "Эпикриз стационара");
        } else if ("EPIAMB".equals(action.getActionType().getMnemonic())) {
            docType = new CodeNamePair("2", "Амбулаторный эпикриз");
        }  else if ("DIR".equals(action.getActionType().getMnemonic())) {
            docType = new CodeNamePair("3", "Направление");
        }
        this.docType = docType;
    }

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

    public String getCode() {
        return code;
    }

    public String getDocName() {
        return docName;
    }

    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    public EmployeeInfo getCreatedPerson() {
        return createdPerson;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    public CodeNamePair getDocType() {
        return docType;
    }

    public String getId() {
        return id;
    }
}
