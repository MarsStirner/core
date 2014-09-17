package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.persistence.criteria.Join;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 05.09.13, 17:51 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Entity
@NamedQueries({
        @NamedQuery(name = "Account.getAll",
                query = "SELECT a FROM Account a"),
        @NamedQuery(name = "Account.getUndeleted",
                query = "SELECT a FROM Account a WHERE a.deleted = false ORDER BY a.settleDate desc"),
        @NamedQuery(name = "Account.getPacketNumber",
                query = "SELECT (COUNT(a)+ 1) FROM Account a WHERE a.deleted = false " +
                        "AND a.begDate = :begDate AND a.endDate = :endDate AND a.contract.id = :contractId"),
        @NamedQuery(name = "Account.deleteAccount",
                query = "DELETE FROM Account a WHERE a.id = :id"),
        @NamedQuery(name = "Account.getByNumber",
                query = "SELECT a FROM Account a WHERE a.number = :number")
        })
@Table(name = "Account", schema = "", catalog = "")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Basic(optional = false)
    @Column(name = "modifyDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "contract_id")
    private Contract contract;

    @ManyToOne
    @JoinColumn(name = "payer_id")
    private Organisation payer;

    @Basic(optional = false)
    @Column(name = "settleDate")
    @Temporal(TemporalType.DATE)
    private Date settleDate;

    @Basic(optional = false)
    @Column(name = "number")
    private String number;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

    @Basic(optional = false)
    @Column(name = "amount")
    private Double amount;

    @Basic(optional = false)
    @Column(name = "uet")
    private Double uet;

    @Basic(optional = false)
    @Column(name = "sum")
    private Double sum;

    @Basic(optional = false)
    @Column(name = "exposeDate")
    @Temporal(TemporalType.DATE)
    private Date exposeDate;

    @Basic(optional = false)
    @Column(name = "payedAmount")
    private Double payedAmount;

    @Basic(optional = false)
    @Column(name = "payedSum")
    private Double payedSum;

    @Basic(optional = false)
    @Column(name = "refusedAmount")
    private Double refusedAmount;

    @Basic(optional = false)
    @Column(name = "refusedSum")
    private Double refusedSum;

    ////////////////////////////////////////////////////////////////////
    @Basic(optional = false)
    @Column(name = "begDate")
    @Temporal(TemporalType.DATE)
    private Date begDate;

    @Basic(optional = false)
    @Column(name = "endDate")
    @Temporal(TemporalType.DATE)
    private Date endDate;

    @Basic(optional = true)
    @Column(name = "note")
    private String note;

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getModifyDatetime() {
        return modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public Organisation getPayer() {
        return payer;
    }

    public void setPayer(Organisation payer) {
        this.payer = payer;
    }

    public Date getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(Date settleDate) {
        this.settleDate = settleDate;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Double getUet() {
        return uet;
    }

    public void setUet(Double uet) {
        this.uet = uet;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Date getExposeDate() {
        return exposeDate;
    }

    public void setExposeDate(Date exposeDate) {
        this.exposeDate = exposeDate;
    }

    public Double getPayedAmount() {
        return payedAmount;
    }

    public void setPayedAmount(Double payedAmount) {
        this.payedAmount = payedAmount;
    }

    public Double getPayedSum() {
        return payedSum;
    }

    public void setPayedSum(Double payedSum) {
        this.payedSum = payedSum;
    }

    public Double getRefusedAmount() {
        return refusedAmount;
    }

    public void setRefusedAmount(Double refusedAmount) {
        this.refusedAmount = refusedAmount;
    }

    public Double getRefusedSum() {
        return refusedSum;
    }

    public void setRefusedSum(Double refusedSum) {
        this.refusedSum = refusedSum;
    }
    //////////////////////////////////////////////////////


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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Account account = (Account) o;

        if (id != null ? !id.equals(account.id) : account.id != null) return false;

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

    public String getInfo() {
        return new StringBuilder("Account[id =")
                .append(id)
                .append(", number=\"")
                .append(number)
                .append("\" from [")
                .append(begDate != null ? begDate.toString() : "0000-00-00")
                .append("] to [")
                .append(endDate != null ? endDate.toString() : "0000-00-00")
                .append("] ]")
                .toString();
    }
}
