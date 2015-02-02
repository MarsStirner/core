package ru.korus.tmis.prescription;

import ru.korus.tmis.core.database.DbRbUnitBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.DbDrugChartBeanLocal;
import ru.korus.tmis.core.pharmacy.DbDrugIntervalCompParam;
import ru.korus.tmis.core.pharmacy.DbDrugIntervalCompParamLocal;
import ru.korus.tmis.core.pharmacy.DbPharmacyBeanLocal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.05.14, 16:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@XmlRootElement
public class PrescriptionsData {

    private List<PrescriptionData> data = new LinkedList<PrescriptionData>();
    private String message = null;


    public PrescriptionsData(Event event,
                             DbDrugChartBeanLocal dbDrugChartBeanLocal,
                             DbDrugIntervalCompParamLocal dbDrugIntervalCompParam,
                             DbPharmacyBeanLocal dbPharmacyBeanLocal,
                             DbRbUnitBeanLocal dbRbUnitBeanLocal,
                             DbActionPropertyBeanLocal dbActionPropertyBeanLocal) throws CoreException {
        List<Action> actionList = dbPharmacyBeanLocal.getPrescriptionForEvent(event);
        getPrescriptionDate(actionList, dbDrugChartBeanLocal, dbDrugIntervalCompParam, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
    }

    private void getPrescriptionDate(List<Action> actionList,
                                     DbDrugChartBeanLocal dbDrugChartBeanLocal,
                                     DbDrugIntervalCompParamLocal dbDrugIntervalCompParam,
                                     DbPharmacyBeanLocal dbPharmacyBeanLocal,
                                     DbRbUnitBeanLocal dbRbUnitBeanLocal,
                                     DbActionPropertyBeanLocal dbActionPropertyBeanLocal) throws CoreException {
        for (Action action : actionList) {
            data.add(new PrescriptionData(action, dbDrugChartBeanLocal, dbDrugIntervalCompParam, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal));
        }
    }

   /* public PrescriptionsData(Event event,
                             String message,
                             DbDrugChartBeanLocal dbDrugChartBeanLocal,
                             DbPharmacyBeanLocal dbPharmacyBeanLocal,
                             DbRbUnitBeanLocal dbRbUnitBeanLocal,
                             DbActionPropertyBeanLocal dbActionPropertyBeanLocal) throws CoreException {
        this(event,
                dbDrugChartBeanLocal,
                dbPharmacyBeanLocal,
                dbRbUnitBeanLocal,
                dbActionPropertyBeanLocal);
        this.message = message;
    }*/


    public PrescriptionsData() {

    }

    public PrescriptionsData(PrescriptionFilter prescriptionFilter,
                             DbDrugChartBeanLocal dbDrugChartBeanLocal,
                             DbDrugIntervalCompParamLocal dbDrugIntervalCompParam,
                             DbPharmacyBeanLocal dbPharmacyBeanLocal,
                             DbRbUnitBeanLocal dbRbUnitBeanLocal,
                             DbActionPropertyBeanLocal dbActionPropertyBeanLocal) throws CoreException {
        Date begDate = new Date(prescriptionFilter.getDateRangeMin() * 1000L);
        Date endDate = new Date(prescriptionFilter.getDateRangeMax() * 1000L);
        List<Action> actionList = filterByDepartmentId(prescriptionFilter.getDepartmentId(), dbPharmacyBeanLocal.getPrescriptionForTimeInterval(begDate, endDate));
        getPrescriptionDate(actionList, dbDrugChartBeanLocal, dbDrugIntervalCompParam, dbPharmacyBeanLocal, dbRbUnitBeanLocal, dbActionPropertyBeanLocal);
        filter(prescriptionFilter);
        sort(prescriptionFilter);
    }

    private void sort(final PrescriptionFilter prescriptionFilter) {
        if (prescriptionFilter.getGroupBy() == null) {
            return;
        }
        Comparator<PrescriptionData> comp = null;
        Collections.sort(data,
                new Comparator<PrescriptionData>() {
                    @Override
                    public int compare(PrescriptionData o1, PrescriptionData o2) {
                        if (o1 == o2) {
                            return 0;
                        }
                        final PrescriptionGroupBy groupBy = prescriptionFilter.getGroupBy();
                        if (groupBy.equals(PrescriptionGroupBy.moa)) {
                            return compareByMoa(o1, o2);
                        } else if (groupBy.equals(PrescriptionGroupBy.client)) {
                            return compareByPatientName(o1, o2);
                        } else if (groupBy.equals(PrescriptionGroupBy.createPerson)) {
                            return compareByCreationPerson(o1, o2);
                        } else if (groupBy.equals(PrescriptionGroupBy.interval)) {
                            return 0;
                        } else if (groupBy.equals(PrescriptionGroupBy.name)) {
                            return compareByActionTypeName(o1, o2);
                        }
                        return 0;
                    }
                });
    }

    private int compareByActionTypeName(PrescriptionData o1, PrescriptionData o2) {
        Integer res = compareObject(o1.getActionTypeId(), o2.getActionTypeId());
        if(res == null) {
            return o1.getActionTypeId().compareTo(o2.getActionTypeId());
        }
        return res;
    }

    private int compareByCreationPerson(PrescriptionData o1, PrescriptionData o2) {
        Integer res = compareObject(o1.getCreatePerson(), o2.getCreatePerson());
        if(res == null) {
            res = compareObject(o1.getCreatePerson().getLastName(), o2.getCreatePerson().getLastName());
            if(res == null) {
                res = o1.getCreatePerson().getLastName().compareTo(o2.getCreatePerson().getLastName());
            }
        }
        return res;
    }

    private int compareByPatientName(PrescriptionData o1, PrescriptionData o2) {
        Integer res = compareObject(o1.getClient(), o2.getClient());
        if(res == null) {
            res = compareObject(o1.getClient().getLastName(), o2.getClient().getLastName());
            if(res == null) {
                res = o1.getClient().getLastName().compareTo(o2.getClient().getLastName());
            }
        }
        return res;
    }


    private int compareByMoa(PrescriptionData o1, PrescriptionData o2) {
        String moa1 = getMoa(o1);
        String moa2 = getMoa(o2);
        Integer res = compareObject(moa1, moa2);
        if(res == null) {
            res = moa1.compareTo(moa2);
        }
        return res;
    }

    private Integer compareObject(Object o1, Object o2) {
        if ((o1 == null && o2 == null)) {
            return 0;
        }
        if (o1 == null) {
            return 1;
        }
        if (o2 == null) {
            return 2;
        }
        return null;
    }

    private List<Action> filterByDepartmentId(Integer departmentId, List<Action> prescriptionForTimeInterval) {
        if (departmentId == null) {
            return prescriptionForTimeInterval;
        }
        List<Action> toRemove = new LinkedList<Action>();
        for (Action action : prescriptionForTimeInterval) {
            if (action.getEvent() != null
                    && action.getEvent().getOrganisation() != null
                    && departmentId.equals(action.getEvent().getOrganisation().getId())) {
                toRemove.add(action);
            }
        }
        prescriptionForTimeInterval.removeAll(toRemove);
        return toRemove;
    }

    private void filter(PrescriptionFilter prescriptionFilter) {
        List<PrescriptionData> toRemove = new LinkedList<PrescriptionData>();
        for (PrescriptionData curData : data) {
            if (!(admissionCompare(prescriptionFilter.getAdmissionId(), curData)
                    && drugCompare(prescriptionFilter.getDrugName(), curData)
                    && patientLastNameCompare(prescriptionFilter.getPatientName(), curData)
                    && setPersonLastNameCompare(prescriptionFilter.getSetPersonName(), curData))) {
                toRemove.add(curData);
            }
        }
        data.removeAll(toRemove);
    }

    private boolean setPersonLastNameCompare(String setPersonName, PrescriptionData curData) {
        if (setPersonName == null) {
            return true;
        }
        return curData.getSetPerson() != null && curData.getSetPerson().getLastName() != null && curData.getSetPerson().getLastName().contains(setPersonName);
    }

    private boolean patientLastNameCompare(String patientName, PrescriptionData curData) {
        if (patientName == null || curData.getClient() == null) {
            return true;
        }
        return curData.getClient() != null && curData.getClient().getLastName() != null && curData.getClient().getLastName().contains(patientName);
    }

    private boolean drugCompare(String drugName, PrescriptionData curData) {
        if (drugName == null) {
            return true;
        }
        for (DrugData drugData : curData.getDrugs()) {
            if (drugData.getName() != null && drugData.getName().contains(drugName)) {
                return true;
            }
        }
        return false;
    }

    private boolean admissionCompare(Integer admissionId, PrescriptionData curData) {
        if (admissionId == null) {
            return true;
        }
        String moaId = getMoa(curData);
        return moaId != null && moaId.equals(String.valueOf(admissionId));
    }

    private String getMoa(PrescriptionData curData) {
        for (PropertiesData prop : curData.getProperties()) {
            if (Constants.MOA.equals(prop.getCode())) {
                return prop.getValueId();
            }
        }
        return null;
    }

    public List<ru.korus.tmis.prescription.PrescriptionData> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
