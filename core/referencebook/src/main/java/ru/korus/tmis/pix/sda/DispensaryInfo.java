package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.entity.model.Diagnostic;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.01.14, 23:22 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Диспансерное наблюдение
 */
public class DispensaryInfo {
    /**
     * постановка/снятие на диспансерное наблюдение (true - постановка)
     */
    private final Boolean started;

    /**
     * Дата постановки/снятия на диспансерное наблюдение
     */
    private XMLGregorianCalendar date;

    /**
     * Кто осуществил постановку/снятие на диспансерное наблюдение
     */
    private EmployeeInfo person;

    public DispensaryInfo(Diagnostic diagnostic) {
        final String code = diagnostic.getDispanser().getCode();
        final List<String> setCode = Arrays.asList("2", "6");
        final List<String> removeCode = Arrays.asList("3", "4", "5");
        started = setCode.contains(code) ? true : (removeCode.contains(code) ? false : null);
        date = ClientInfo.getXmlGregorianCalendar(diagnostic.getSetDate());
        person = EmployeeInfo.newInstance(diagnostic.getPerson());
    }

    public Boolean isStarted() {
        return started;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public EmployeeInfo getPerson() {
        return person;
    }
}
