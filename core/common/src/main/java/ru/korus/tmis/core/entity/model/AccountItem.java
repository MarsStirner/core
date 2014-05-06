package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 05.09.13, 18:52 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "AccountItem.getByAccountId",
                query = "SELECT ai FROM AccountItem ai WHERE ai.master.id = :ACCOUNTID"),
        @NamedQuery(name = "AccountItem.getByActionId",
                query = "SELECT ai FROM AccountItem ai WHERE ai.action.id = :ACTIONID"),
        @NamedQuery(name = "AccountItem.deleteByAccountId",
                query = "UPDATE AccountItem ai SET ai.deleted = true WHERE ai.master.id = :ACCOUNTID"),
        @NamedQuery(name = "AccountItem.reexposeAllByActionId",
                query = "UPDATE AccountItem ai SET ai.reexposeItemId = :reexposeId " +
                        "WHERE ai.action.id = :actionId " +
                        "AND ai.id <> :reexposeId " +
                        "AND ai.deleted = false")
})
@Table(name = "Account_Item", schema = "", catalog = "")
public class AccountItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = false)
    private Account master;

    @Basic(optional = false)
    @Column(name = "serviceDate")
    @Temporal(TemporalType.DATE)
    private Date serviceDate;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Patient client;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = true)
    private Event event;

    @ManyToOne
    @JoinColumn(name = "action_id", nullable = true)
    private Action action;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = true)
    private RbService service;


    @ManyToOne
    @JoinColumn(name = "tariff_id", nullable = true)
    private ContractTariff tariff;

    @Basic(optional = false)
    @Column(name = "price")
    private Double price;

    @Basic(optional = false)
    @Column(name = "amount")
    private Double amount;

    @Basic(optional = false)
    @Column(name = "sum")
    private Double sum;


    @ManyToOne
    @JoinColumn(name = "unit_id", nullable = true)
    private RbMedicalAidUnit unit;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic(optional = false)
    @Column(name = "number")
    private String number;

    @ManyToOne
    @JoinColumn(name = "refuseType_id")
    private RbPayRefuseType refuseType;

    @Column(name = "reexposeItem_id")
    private Integer reexposeItemId;

    @Basic(optional = false)
    @Column(name = "note")
    private String note;


    @Basic(optional = false)
    @Column(name = "notUploadAnymore")
    private boolean notUploadAnymore = false;


    public AccountItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Account getMaster() {
        return master;
    }

    public void setMaster(Account master) {
        this.master = master;
    }

    public Date getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(Date serviceDate) {
        this.serviceDate = serviceDate;
    }

    public Patient getClient() {
        return client;
    }

    public void setClient(Patient client) {
        this.client = client;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public RbService getService() {
        return service;
    }

    public void setService(RbService service) {
        this.service = service;
    }

    public ContractTariff getTariff() {
        return tariff;
    }

    public void setTariff(ContractTariff tariff) {
        this.tariff = tariff;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }


    public RbMedicalAidUnit getUnit() {
        return unit;
    }

    public void setUnit(RbMedicalAidUnit unit) {
        this.unit = unit;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public RbPayRefuseType getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(RbPayRefuseType refuseType) {
        this.refuseType = refuseType;
    }

    public Integer getReexposeItemId() {
        return reexposeItemId;
    }

    public void setReexposeItemId(Integer reexposeItemId) {
        this.reexposeItemId = reexposeItemId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isNotUploadAnymore() {
        return notUploadAnymore;
    }

    public void setNotUploadAnymore(boolean notUploadAnymore) {
        this.notUploadAnymore = notUploadAnymore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountItem that = (AccountItem) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }
}
