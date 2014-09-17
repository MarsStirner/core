package ru.korus.tmis.ws.finance;

import javax.jws.WebParam;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.04.14, 16:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PaymentPrm {

    private Date datePaid;

    private String codeContract;

    private Date dateContract;

    private  Integer idTreatment;

    private PersonName paidName;

    private Date birthDate;

    private List<ServicePaidInfo> listService;

    public Date getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(Date datePaid) {
        this.datePaid = datePaid;
    }

    public String getCodeContract() {
        return codeContract;
    }

    public void setCodeContract(String codeContract) {
        this.codeContract = codeContract;
    }

    public Date getDateContract() {
        return dateContract;
    }

    public void setDateContract(Date dateContract) {
        this.dateContract = dateContract;
    }

    public Integer getIdTreatment() {
        return idTreatment;
    }

    public void setIdTreatment(Integer idTreatment) {
        this.idTreatment = idTreatment;
    }

    public PersonName getPaidName() {
        return paidName;
    }

    public void setPaidName(PersonName paidName) {
        this.paidName = paidName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<ServicePaidInfo> getListService() {
        return listService;
    }

    public void setListService(List<ServicePaidInfo> listService) {
        this.listService = listService;
    }
}
