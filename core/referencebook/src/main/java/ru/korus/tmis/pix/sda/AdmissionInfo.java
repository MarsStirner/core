package ru.korus.tmis.pix.sda;

import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.joda.time.Days;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.HospitalBedBeanLocal;

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
    private static final CodeNameSystem IS_URGENT_ADMISSION = CodeNameSystem.newInstance("isUrgentAdmission", "Экстренность", null);
    private static final CodeNameSystem TIME_AFTER_FALLING_ILL = CodeNameSystem.newInstance("timeAfterFallingIll", "Доставлен в стационар от начала заболевания", null);
    private static final CodeNameSystem TRANSPORT_TYPE = CodeNameSystem.newInstance("transportType", "Вид транспортировки", null);
    private static final CodeNameSystem WARD = CodeNameSystem.newInstance("ward", "койка", null);
    private static final CodeNameSystem FINAL_DEPARTMENT = CodeNameSystem.newInstance("finalDepartment", "Отделение пребывания", null);
    private static final CodeNameSystem ADMISSION_REFERRAL = CodeNameSystem.newInstance("admissionReferral", "Кем доставлен", null);
    private static final CodeNameSystem ADMISSION_THIS_YEAR = CodeNameSystem.newInstance("admissionsThisYear", "Госпитализирован по поводу данного заболевания в текущем году", null);
    private final boolean urgent;
    private final CodeNameSystem timeAftreFalling;
    private final CodeNameSystem transportType;
    private final CodeNameSystem finalDepartment;
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

    private final CodeNameSystem department;

    private final EmployeeInfo admittingDoctor;

    private final CodeNameSystem admissionReferral;

    private final CodeNameSystem admissionsThisYear;

    public AdmissionInfo(Event event, Multimap<String, Action> actions, DbActionPropertyBeanLocal dbActionPropertyBeanLocal, HospitalBedBeanLocal hospitalBedBeanLocal) {
        this.dbActionPropertyBeanLocal = dbActionPropertyBeanLocal;
        final Action received = actions.get("received").iterator().hasNext() ? actions.get("received").iterator().next() : null;
        final List<CodeNameSystem> propsInfoMapRecieved = new LinkedList<CodeNameSystem>() {{
            add(IS_URGENT_ADMISSION);
            add(TIME_AFTER_FALLING_ILL);
            add(TRANSPORT_TYPE);
        }};
        APValue apValue = received == null ? null : getActionPropertyByCodeOrName(received, IS_URGENT_ADMISSION);
        this.urgent = apValue == null ? false : apValue.getValueAsString().equals("по экстренным показаниям");
        apValue = getActionPropertyByCodeOrName(received, TIME_AFTER_FALLING_ILL);
        this.timeAftreFalling = apValue == null ? null : RbManager.get(RbManager.RbType.PRK371,
                CodeNameSystem.newInstance(timeAfterFallingMap.get(timeAfterFallingMap.get(apValue.getValueAsString())), apValue.getValueAsString(), "1.2.643.5.1.13.2.1.1.537"));
        apValue = getActionPropertyByCodeOrName(received, TRANSPORT_TYPE);
        this.transportType = apValue == null ? null : CodeNameSystem.newInstance(null, apValue.getValueAsString(), null);
        apValue = getActionPropertyByCodeOrName(received, ADMISSION_REFERRAL);
        this.admissionReferral = apValue == null ? null : RbManager.get(RbManager.RbType.STR464,
                CodeNameSystem.newInstance(admissionReferralMap.get(admissionReferralMap.get(apValue.getValueAsString())), apValue.getValueAsString(), "1.2.643.5.1.13.2.1.1.281"));
        apValue = getActionPropertyByCodeOrName(received, ADMISSION_THIS_YEAR);
        this.admissionsThisYear = apValue == null ? null : RbManager.get(RbManager.RbType.C42007,
                CodeNameSystem.newInstance(admissionsThisYearMap.get(apValue.getValueAsString()), apValue.getValueAsString(), "1.2.643.5.1.13.2.1.1.109"));

        final Action movingLast = hospitalBedBeanLocal.getLastMovingActionForEventId(event.getId());
        APValueOrgStructure apValueOrgStructure = (APValueOrgStructure) getActionPropertyByCodeOrName(movingLast, FINAL_DEPARTMENT);
        this.finalDepartment = (apValueOrgStructure == null || apValueOrgStructure.getValue() == null) ?
                null : CodeNameSystem.newInstance(apValueOrgStructure.getValue().getCode(), apValueOrgStructure.getValue().getName(), null);

        final Action movingFirst = getFirstMoving(actions);
        apValueOrgStructure = (APValueOrgStructure) getActionPropertyByCodeOrName(movingFirst, FINAL_DEPARTMENT);
        this.department = (apValueOrgStructure == null || apValueOrgStructure.getValue() == null) ?
                null : CodeNameSystem.newInstance(apValueOrgStructure.getValue().getCode(), apValueOrgStructure.getValue().getName(), null);

        apValue = getActionPropertyByCodeOrName(movingLast, WARD);
        ward = apValue == null ? null : apValue.getValueAsString();
        bedDayCount = new Long(Days.daysBetween(new DateTime(event.getSetDate()), new DateTime(event.getExecDate())).getDays() + 1);
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


    private APValue getActionPropertyByCodeOrName(Action action, CodeNameSystem prop) {
        if (action == null) {
            return null;
        }
        try {
            for (ActionProperty actionProperty : action.getActionProperties()) {
                if ( prop.getCode() != null && prop.getCode().equals(actionProperty.getType().getCode())) {
                    final List<APValue> valueList = dbActionPropertyBeanLocal.getActionPropertyValue(actionProperty);
                    final APValue value = valueList.iterator().hasNext() ? valueList.iterator().next() : null;
                    if (value != null) {
                        return value;
                    }
                }
            }
            for (ActionProperty actionProperty : action.getActionProperties()) {
                if (prop.getName() != null && prop.getName().equals(actionProperty.getType().getName())) {
                    final List<APValue> valueList = dbActionPropertyBeanLocal.getActionPropertyValue(actionProperty);
                    final APValue value = valueList.iterator().hasNext() ? valueList.iterator().next() : null;
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

    public CodeNameSystem getTimeAftreFalling() {
        return timeAftreFalling;
    }

    public CodeNameSystem getTransportType() {
        return transportType;
    }

    public CodeNameSystem getDepartment() {
        return department;
    }

    public CodeNameSystem getFinalDepartment() {
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

    public CodeNameSystem getAdmissionReferral() {
        return admissionReferral;
    }


    public CodeNameSystem getAdmissionsThisYear() {
        return admissionsThisYear;
    }
}
