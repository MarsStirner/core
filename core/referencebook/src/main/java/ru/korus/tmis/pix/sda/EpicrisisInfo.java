package ru.korus.tmis.pix.sda;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Years;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.ActionPropertyType;
import ru.korus.tmis.core.exception.CoreException;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;

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
    private final CodeNameSystem docType;
    private final String text;

    public EpicrisisInfo(Action action, ClientInfo clientInfo, DbActionPropertyBeanLocal apBean) {
        this.code = action.getActionType().getMnemonic();
        this.docName = action.getActionType().getName();
        this.createDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        this.id = String.valueOf(action.getId());
        this.createdPerson = EmployeeInfo.newInstance(action.getExecutor());
        this.endDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        CodeNameSystem docType = null;
        //TODO: использовать ПУС!
        if ("EPI".equals(action.getActionType().getMnemonic())) {
            docType = CodeNameSystem.newInstance("1", "Эпикриз стационара", "1.2.643.5.1.13.2.1.1.646");
        } else if ("EPIAMB".equals(action.getActionType().getMnemonic())) {
            docType = CodeNameSystem.newInstance("2", "Амбулаторный эпикриз", "1.2.643.5.1.13.2.1.1.646");
        } else if ("DIR".equals(action.getActionType().getMnemonic())) {
            docType = CodeNameSystem.newInstance("3", "Направление", "1.2.643.5.1.13.2.1.1.646");
        }
        this.docType = docType;
        this.text = initText(action, apBean);
    }

    private String initText(Action action, DbActionPropertyBeanLocal apBean) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);
        printStream.println("<html>\n" +
                "<head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"></head>\n" +
                "<body>" +
                "<table style=\"text-align: left;\" 2=\"\" cellspacing=\"2\">");
        printStream.println(" <tbody>");
        try {
            Map<ActionProperty, List<APValue>> propertyMap = apBean.getActionPropertiesByActionId(action.getId());
            for (Map.Entry<ActionProperty, List<APValue>> entry : propertyMap.entrySet()) {
                final ActionPropertyType type = entry.getKey().getType();
                if (!entry.getValue().isEmpty() && type != null && type.getName() != null && !"".equals(type.getName())) {
                    printStream.println("  <tr>");
                    printStream.print("   <td>");
                    printStream.print(type.getName());
                    printStream.println("   </td>");
                    for (APValue apValue : entry.getValue()) {
                        if (apValue != null && apValue.toString() != null && !"".equals(apValue.toString())) {
                            printStream.print("   <td>");
                            printStream.print(apValue.getValueAsString());
                            printStream.println("   </td>");
                        }
                    }
                    printStream.print("  </tr>");
                }
            }
            printStream.println(" </tbody>");
            printStream.println("</table></body>\n" +
                    "</html>");
            return outputStream.toString();
        } catch (CoreException ex) {
            return null;
        }

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

    public CodeNameSystem getDocType() {
        return docType;
    }

    public String getId() {
        return id;
    }


    public String getText() {
        return text;
    }
}
