package ru.korus.tmis.prescription;



/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        22.05.14, 10:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PrescriptionFilter {
    private static final String NOT_SELECTED = "not-selected";
    final private Long dateRangeMin;
    final private Long dateRangeMax;
    final private PrescriptionGroupBy groupBy;
    final private Integer admissionId;
    final private String drugName;
    final private String patientName;
    final private String setPersonName;
    final private Integer departmentId;

    public PrescriptionFilter(Long dateRangeMin,
                              Long dateRangeMax,
                              PrescriptionGroupBy groupBy,
                              String admissionId,
                              String drugName,
                              String patientName,
                              String setPersonName,
                              String departmentId) {
        this.dateRangeMin = dateRangeMin;
        this.dateRangeMax = dateRangeMax;
        this.groupBy = groupBy;
        this.admissionId = admissionId == null || NOT_SELECTED.equals(admissionId) ? null : Integer.valueOf(admissionId);
        this.departmentId = departmentId == null || NOT_SELECTED.equals(departmentId) ? null : Integer.valueOf(departmentId);
        this.drugName = drugName;
        this.patientName = patientName;
        this.setPersonName = setPersonName;
    }

    public Long getDateRangeMin() {
        return dateRangeMin;
    }

    public Long getDateRangeMax() {
        return dateRangeMax;
    }

    public PrescriptionGroupBy getGroupBy() {
        return groupBy;
    }

    public Integer getAdmissionId() {
        return admissionId;
    }

    public String getDrugName() {
        return drugName;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getSetPersonName() {
        return setPersonName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }
}
