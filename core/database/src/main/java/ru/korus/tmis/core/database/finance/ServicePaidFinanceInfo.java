package ru.korus.tmis.core.database.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 13:10 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ServicePaidFinanceInfo {
    /**
     * ID услуги в МИС (в БД МИС  - Action.id)
     */
    private Integer idAction;

    /**
     * код услуги в МИС (в БД МИС  - rbService.code)
     */
    private String codeService;

    /**
     * наименование услуги в МИС (в БД МИС  - rbService.name)
     */
    private String nameService;

    /**
     * количество (в БД МИС - Action.amount)
     */
    private Double amount;

    /**
     * сумма скидки   (в БД МИС - EventPayment.sumDesc)
     */
    private Double sumDisc;

    /**
     * сумма оплаты за услугу (в БД МИС - EventPayment. actionSum)
     */
    private Double sum;

    /**
     * ID пациента в МИС (в БД МИС – Client.id)
     */
    private String codePatient;

    /**
     *  ФИО пациента
     */
    private PersonFIO patientName;

    /**
     *  Тип оплаты (наличный = false, безналичный = true)
     */
    private boolean  typePayment;

    public Integer getIdAction() {
        return idAction;
    }

    public String getCodeService() {
        return codeService;
    }

    public String getNameService() {
        return nameService;
    }

    public Double getAmount() {
        return amount;
    }

    public Double getSumDisc() {
        return sumDisc;
    }

    public Double getSum() {
        return sum == null ? 0.0 : sum;
    }

    public String getCodePatient() {
        return codePatient;
    }

    public PersonFIO getPatientName() {
        return patientName;
    }

    public boolean isTypePayment() {
        return typePayment;
    }

    public void setIdAction(Integer idAction) {
        this.idAction = idAction;
    }

    public void setCodeService(String codeService) {
        this.codeService = codeService;
    }

    public void setNameService(String nameService) {
        this.nameService = nameService;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setSumDisc(Double sumDisc) {
        this.sumDisc = sumDisc;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public void setCodePatient(String codePatient) {
        this.codePatient = codePatient;
    }

    public void setPatientName(PersonFIO patientName) {
        this.patientName = patientName;
    }

    public void setTypePayment(boolean typePayment) {
        this.typePayment = typePayment;
    }
}
