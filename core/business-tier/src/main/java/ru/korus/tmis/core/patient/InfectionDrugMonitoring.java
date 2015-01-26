package ru.korus.tmis.core.patient;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        26.12.2014, 11:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@XmlType
@XmlRootElement
@JsonIgnoreProperties(ignoreUnknown = true)
public class InfectionDrugMonitoring implements Comparable {

    private String therapyName;

    private String drugName;

    private Date begDate;

    private Date endDate;

    public String getTherapyName() {
        return therapyName;
    }

    public void setTherapyName(String therapy) {
        this.therapyName = therapy;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Date getBegDate() {
        return begDate;
    }

    public void setBegDate(Date begDate) {
        this.begDate = begDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InfectionDrugMonitoring)) return false;

        InfectionDrugMonitoring that = (InfectionDrugMonitoring) o;

        if (begDate != null ? !begDate.equals(that.begDate) : that.begDate != null) return false;
        if (drugName != null ? !drugName.equals(that.drugName) : that.drugName != null) return false;
        if (therapyName != null ? !therapyName.equals(that.therapyName) : that.therapyName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = therapyName != null ? therapyName.hashCode() : 0;
        result = 31 * result + (drugName != null ? drugName.hashCode() : 0);
        result = 31 * result + (begDate != null ? begDate.hashCode() : 0);
        return result;
    }


    @Override
    public int compareTo(Object o) {
        if (o == null || !(o instanceof InfectionDrugMonitoring)) {
            return -1;
        } else if (((InfectionDrugMonitoring) o).getBegDate() == null) {
            return 1;
        }
        return ((InfectionDrugMonitoring) o).getBegDate().compareTo(begDate);
    }
}
