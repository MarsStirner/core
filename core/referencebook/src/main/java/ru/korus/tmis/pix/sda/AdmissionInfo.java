package ru.korus.tmis.pix.sda;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.joda.time.Days;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.01.14, 9:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class AdmissionInfo {
    private static final CodeNamePair IS_URGENT_ADMISSION = new CodeNamePair("isUrgentAdmission", "Экстренность");
    private static final CodeNamePair TIME_AFTER_FALLING_ILL = new CodeNamePair("timeAfterFallingIll", "Доставлен в стационар от начала заболевания");
    private static final CodeNamePair TRANSPORT_TYPE = new CodeNamePair("transportType", "Вид транспортировки");
    private static final CodeNamePair WARD = new CodeNamePair("ward", "койка");
    private static final CodeNamePair FINAL_DEPARTMENT = new CodeNamePair("finalDepartment", "Отделение поступления");
    private static final CodeNamePair ADMISSION_REFERRAL = new CodeNamePair("admissionReferral", "Кем доставлен");
    private static final CodeNamePair ADMISSION_THIS_YEAR = new CodeNamePair("admissionsThisYear", "Госпитализирован по поводу данного заболевания в текущем году");
    private final boolean urgent;
    private final CodeNamePair timeAftreFalling;
    private final CodeNamePair transportType;
    private final CodeNamePair finalDepartment;
    private final String ward;
    private final Long bedDayCount;
    private final EmployeeInfo attendingDoctor;
    private final DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    private final Map<String, String> timeAfterFallingMap = new HashMap<String, String>() {{
        put("в первые 6 часов", "1");
        put("течении 7-24 часов", "2");
        put("позднее 24-х часов", "3");
    }};
    private final Map<String, String> admissionReferralMap = new HashMap<String, String>() {{
        put("СМП", "1");
        put("Самостоятельно", "2");
    }};
    private final Map<String, String> admissionsThisYearMap = new HashMap<String, String>() {{
        put("первично", "1");
        put("повторно", "2");
    }};

    private final CodeNamePair department;

    private final EmployeeInfo admittingDoctor;

    private final CodeNamePair admissionReferral;
    private final CodeNamePair admissionsThisYear;

    public AdmissionInfo(Event event, Multimap<String, Action> actions, DbActionPropertyBeanLocal dbActionPropertyBeanLocal) {
        this.dbActionPropertyBeanLocal = dbActionPropertyBeanLocal;
        final Action recieved = actions.get("recieved").iterator().next();
        final List<CodeNamePair> propsInfoMapRecieved = new LinkedList<CodeNamePair>() {{
            add(IS_URGENT_ADMISSION);
            add(TIME_AFTER_FALLING_ILL);
            add(TRANSPORT_TYPE);
        }};
        APValue apValue = getActionPropertyByCodeOrName(recieved, IS_URGENT_ADMISSION);
        urgent = apValue == null ? false : apValue.getValueAsString().equals("по экстренным показаниям");
        apValue = getActionPropertyByCodeOrName(recieved, TIME_AFTER_FALLING_ILL);
        timeAftreFalling = apValue == null ? null : new CodeNamePair(apValue.getValueAsString(), timeAfterFallingMap.get(apValue.getValueAsString()));
        apValue = getActionPropertyByCodeOrName(recieved, TRANSPORT_TYPE);
        transportType = apValue == null ? null : new CodeNamePair(null, apValue.getValueAsString());
        apValue = getActionPropertyByCodeOrName(recieved, ADMISSION_REFERRAL);
        admissionReferral = apValue == null ? null : new CodeNamePair(apValue.getValueAsString(), admissionReferralMap.get(apValue.getValueAsString()));
        apValue = getActionPropertyByCodeOrName(recieved, ADMISSION_THIS_YEAR);
        admissionsThisYear = apValue == null ? null : new CodeNamePair(apValue.getValueAsString(), admissionsThisYearMap.get(apValue.getValueAsString()));

        Action moving = getLastMoving(actions);
        APValueOrgStructure apValueOrgStructure = (APValueOrgStructure) getActionPropertyByCodeOrName(moving, FINAL_DEPARTMENT);
        finalDepartment = (apValueOrgStructure == null && apValueOrgStructure.getValue() == null) ?
                null : new CodeNamePair(apValueOrgStructure.getValue().getCode(), apValueOrgStructure.getValue().getName());

        moving = getFirstMoving(actions);
        apValueOrgStructure = (APValueOrgStructure) getActionPropertyByCodeOrName(moving, FINAL_DEPARTMENT);
        department = (apValueOrgStructure == null && apValueOrgStructure.getValue() == null) ?
                null : new CodeNamePair(apValueOrgStructure.getValue().getCode(), apValueOrgStructure.getValue().getName());

        apValue = getActionPropertyByCodeOrName(moving, WARD);
        ward = apValue == null ? null : apValue.getValueAsString();
        bedDayCount = new Long(Days.daysBetween(new DateTime(event.getSetDate()), new DateTime(event.getExecDate())).getDays());
        //Лечащий врач
        attendingDoctor = EmployeeInfo.newInstance(event.getExecutor());
        //Врач приемного отделения
        admittingDoctor = EmployeeInfo.newInstance(event.getAssigner());
    }

    private Action getFirstMoving(Multimap<String, Action> actions) {
        Action res = null;
        for (Action action : actions.get("moving")) {
            if (res == null || action.getCreateDatetime().compareTo(res.getCreateDatetime()) < 0) {
                res = action;
            }
        }
        return res;
    }

    private Action getLastMoving(Multimap<String, Action> actions) {
        Action res = null;
        for (Action action : actions.get("moving")) {
            if (res == null || action.getCreateDatetime().compareTo(res.getCreateDatetime()) > 0) {
                res = action;
            }
        }
        return res;
    }

    private APValue getActionPropertyByCodeOrName(Action recieved, CodeNamePair prop) {
        try {
            for (ActionProperty actionProperty : recieved.getActionProperties()) {
                if (prop.getCode().equals(actionProperty.getType().getCode())) {
                    final APValue value;
                    value = dbActionPropertyBeanLocal.getActionPropertyValue(actionProperty).iterator().next();
                    if (value != null) {
                        return value;
                    }
                }
            }
            for (ActionProperty actionProperty : recieved.getActionProperties()) {
                if (prop.getName().equals(actionProperty.getType().getName())) {
                    final APValue value = dbActionPropertyBeanLocal.getActionPropertyValue(actionProperty).iterator().next();
                    if (value != null) {
                        return value;
                    }
                }
            }
            return null;
        } catch (CoreException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            return null;
        }
    }


    public boolean isUrgent() {
        return urgent;
    }

    public CodeNamePair getTimeAftreFalling() {
        return timeAftreFalling;
    }

    public CodeNamePair getTransportType() {
        return transportType;
    }

    public CodeNamePair getDepartment() {
        return department;
    }

    public CodeNamePair getFinalDepartment() {
        return finalDepartment;
    }

    public String getWard() {
        return ward;
    }

    public Long getBedDayCount() {
        return bedDayCount;
    }

    public EmployeeInfo getAttendingDoctor() {
        return attendingDoctor;
    }

    public EmployeeInfo getAdmittingDoctor() {
        return admittingDoctor;
    }

    public CodeNamePair getAdmissionReferral() {
        return admissionReferral;
    }


    public CodeNamePair getAdmissionsThisYear() {
        return admissionsThisYear;
    }
}
