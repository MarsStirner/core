package ru.korus.tmis.core.patient.impl;

import ru.korus.tmis.core.database.common.DbActionBean;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.InfectionDrugMonitoring;
import ru.korus.tmis.core.patient.MonitoringBeanLocal;
import ru.korus.tmis.core.values.InfectionControl;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        26.12.2014, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class MonitoringBean implements MonitoringBeanLocal {

    @EJB
    DbActionBeanLocal dbActionBean;

    @EJB
    DbActionPropertyBeanLocal dbActionPropertyBeanLocal;

    @Override
    public List<InfectionDrugMonitoring> getInfectionDrugMonitoring(Event event) throws CoreException {
        Set documents = new HashSet<String>(){{
            add("1_2_18"); add("1_2_19"); add("1_2_20"); add("1_2_22"); add("1_2_23");
        }};
        List<Action> actionList = dbActionBean.getActionsByTypeCodeAndEventId(documents, event.getId(), "a.begDate DESC", null);
        return getInfectionDrugMonitoring(actionList);
    }

    private List<InfectionDrugMonitoring> getInfectionDrugMonitoring(List<Action> actionList) throws CoreException {
        final String[] flatCodesPrefix = {"infectProphylaxis", "infectEmpiric", "infectTelic"};
        final List<InfectionDrugMonitoring> res = new ArrayList<InfectionDrugMonitoring>();
        if (actionList == null) {
            return res;
        }
        for (String prefix : flatCodesPrefix) {
            final List<InfectionDrugMonitoring> allMonitoring = new LinkedList<InfectionDrugMonitoring>();
            final InfectionDrugMonitoring infectionDrugMonitorings[] = new InfectionDrugMonitoring[8];
            for (Action action : actionList) {
                for (int index = 0; index < infectionDrugMonitorings.length; ++index) {
                    ActionProperty propDrugName = getPropDrugName(action, prefix, index);
                    if (propDrugName != null) {
                        InfectionDrugMonitoring infectionDrugMonitoring = toInfectionDrugMonitoring(action, propDrugName, prefix, index);
                        if (infectionDrugMonitorings[index] == null) { //если ранее не было противоинфекционной терапии с индексом index
                            addMonitoring(allMonitoring, infectionDrugMonitorings, index, infectionDrugMonitoring);
                        } else if (isCompleteDrugMonitoring(infectionDrugMonitorings[index], infectionDrugMonitoring)) {
                            //если установлена дата окончания предыдущей противоинфекционной терапии
                            allMonitoring.add(infectionDrugMonitoring);
                            infectionDrugMonitorings[index] = null;
                        } else if (isNewDrugMonitoring(infectionDrugMonitorings[index], infectionDrugMonitoring)) {
                            //если задана новая противоинфекционная терапия
                            allMonitoring.add(infectionDrugMonitorings[index]);
                            infectionDrugMonitorings[index] = null;
                            addMonitoring(allMonitoring, infectionDrugMonitorings, index, infectionDrugMonitoring);
                        }
                    }
                }
            }
            for (InfectionDrugMonitoring m : infectionDrugMonitorings) {
                if (m != null) {
                    allMonitoring.add(m);
                }
            }
            Collections.sort(allMonitoring);
            res.addAll(allMonitoring);
        }
        return res;
    }

    private boolean isNewDrugMonitoring(InfectionDrugMonitoring oldMonitoring, InfectionDrugMonitoring newMonitoring) {
        if (oldMonitoring.getDrugName() != newMonitoring.getDrugName() &&
                oldMonitoring.getTherapyName() != newMonitoring.getTherapyName() &&
                oldMonitoring.getBegDate() != newMonitoring.getBegDate()) {
            return true;
        }
        return false;
    }

    private boolean isCompleteDrugMonitoring(InfectionDrugMonitoring oldMonitoring, InfectionDrugMonitoring newMonitoring) {
        if (newMonitoring.getEndDate() == null ||
                oldMonitoring.getBegDate() != newMonitoring.getBegDate() ||
                oldMonitoring.getDrugName() != newMonitoring.getDrugName() ||
                oldMonitoring.getTherapyName() != newMonitoring.getTherapyName()) {
            return false;
        }
        return true;
    }

    private void addMonitoring(List<InfectionDrugMonitoring> allMonitoring, InfectionDrugMonitoring[] infectionDrugMonitorings, int index, InfectionDrugMonitoring infectionDrugMonitoring) {
        if (infectionDrugMonitoring.getEndDate() != null) {
            allMonitoring.add(infectionDrugMonitoring);
        } else {
            infectionDrugMonitorings[index] = infectionDrugMonitoring;
        }
    }

    private InfectionDrugMonitoring toInfectionDrugMonitoring(Action action, ActionProperty propDrugName, String prefix, Integer index) throws CoreException {
        InfectionDrugMonitoring res = new InfectionDrugMonitoring();
        List<APValue> actionPropValueList = dbActionPropertyBeanLocal.getActionPropertyValue(propDrugName);
        if (!actionPropValueList.isEmpty()
                && actionPropValueList.get(0).getValue() instanceof String) {
            res.setDrugName((String) actionPropValueList.get(0).getValue());
        }
        for (ActionProperty ap : action.getActionProperties()) {
            String code = ap.getType().getCode();
            if (code != null) {
                if (code.startsWith(prefix + "BeginDate_" + (index + 1))) {
                    Date date = getValue(actionPropValueList, ap);
                    res.setBegDate(date);
                } else if (code.startsWith(prefix + "EndDate_" + (index + 1))) {
                    Date date = getValue(actionPropValueList, ap);
                    res.setEndDate(date);
                } else if (code.equals(prefix)) {
                    res.setTherapyName(ap.getType().getName());
                }
            }
        }
        return res;
    }

    private Date getValue(List<APValue> actionPropValueList, ActionProperty ap) {
        try {
            List<APValue> valueList = dbActionPropertyBeanLocal.getActionPropertyValue(ap);
            if (!valueList.isEmpty()
                    && valueList.get(0).getValue() instanceof Date) {
                return (Date) valueList.get(0).getValue();
            }
        } catch (CoreException e) {
        }

        return null;
    }

    private ActionProperty getPropDrugName(Action action, String prefix, Integer index) {
        for (ActionProperty ap : action.getActionProperties()) {
            if (ap.getType() != null && ap.getType().getCode() != null
                    && ap.getType().getCode().startsWith(prefix + "Name_" + (index + 1))) {
                try {
                    List<APValue> actionPropValueList = dbActionPropertyBeanLocal.getActionPropertyValue(ap);
                    if (!actionPropValueList.isEmpty()
                            && actionPropValueList.get(0).getValue() instanceof String
                            && !((String) actionPropValueList.get(0).getValue()).isEmpty()) {
                        return ap;
                    }
                } catch (CoreException e) {
                }
            }
        }
        return null;
    }
}
