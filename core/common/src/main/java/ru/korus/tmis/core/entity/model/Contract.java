package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 31.01.13
 * Time: 11:58

 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Contract")
@NamedQueries(
        {
                @NamedQuery(name = "Contract.findAll", query = "SELECT c FROM Contract c"),
                @NamedQuery(name = "Contract.findByOrganisationId",
                        query = "SELECT c FROM Contract c WHERE c.recipientId = :ORGANISATIONID")
        })
@XmlType(name = "contract")
@XmlRootElement(name = "contract")
public class Contract implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

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
    private boolean deleted;

    @Basic(optional = false)
    @Column(name = "number")
    private String number;

    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Basic(optional = false)
    @Column(name = "recipient_id")
    private int recipientId;

    @Basic(optional = false)
    @Column(name = "recipientAccount_id")
    private int recipientAccountId;

    @Basic(optional = false)
    @Column(name = "recipientKBK")
    private String recipientKBK;

    @Basic(optional = false)
    @Column(name = "payer_id")
    private int payerId;

    @Basic(optional = false)
    @Column(name = "payerAccount_id")
    private int payerAccountId;

    @Basic(optional = false)
    @Column(name = "payerKBK")
    private String payerKBK;

    @Column(name = "begDate" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date begDate;

    @Column(name = "endDate" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "finance_id")
    private RbFinance finance;

    @Basic(optional = false)
    @Column(name = "grouping")
    private String grouping;

    @Basic(optional = false)
    @Column(name = "resolution")
    private String resolution;

    @Basic(optional = false)
    @Column(name = "format_id")
    private int formatId;

    @Basic(optional = false)
    @Column(name = "exposeUnfinishedEventVisits")
    private boolean exposeUnfinishedEventVisits;

    @Basic(optional = false)
    @Column(name = "exposeUnfinishedEventActions")
    private boolean exposeUnfinishedEventActions;

    @Basic(optional = false)
    @Column(name = "visitExposition")
    private boolean visitExposition;

    @Basic(optional = false)
    @Column(name = "actionExposition")
    private boolean actionExposition;

    @Basic(optional = false)
    @Column(name = "exposeDiscipline")
    private boolean exposeDiscipline;

    @Basic(optional = false)
    @Column(name = "priceList_id")
    private int priceListId;

    @Basic(optional = false)
    @Column(name = "coefficient")
    private double coefficient;

    @Basic(optional = false)
    @Column(name = "coefficientEx")
    private double coefficientEx;

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

    public int getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(int recipientId) {
        this.recipientId = recipientId;
    }

    public int getRecipientAccountId() {
        return recipientAccountId;
    }

    public void setRecipientAccountId(int recipientAccountId) {
        this.recipientAccountId = recipientAccountId;
    }

    public String getRecipientKBK() {
        return recipientKBK;
    }

    public void setRecipientKBK(String recipientKBK) {
        this.recipientKBK = recipientKBK;
    }

    public int getPayerId() {
        return payerId;
    }

    public void setPayerId(int payerId) {
        this.payerId = payerId;
    }

    public int getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(int payerAccountId) {
        this.payerAccountId = payerAccountId;
    }

    public String getPayerKBK() {
        return payerKBK;
    }

    public void setPayerKBK(String payerKBK) {
        this.payerKBK = payerKBK;
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

    public RbFinance getFinance() {
        return finance;
    }

    public void setFinance(RbFinance finance) {
        this.finance = finance;
    }

    public String getGrouping() {
        return grouping;
    }

    public void setGrouping(String grouping) {
        this.grouping = grouping;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public int getFormatId() {
        return formatId;
    }

    public void setFormatId(int formatId) {
        this.formatId = formatId;
    }

    public boolean isExposeUnfinishedEventVisits() {
        return exposeUnfinishedEventVisits;
    }

    public void setExposeUnfinishedEventVisits(boolean exposeUnfinishedEventVisits) {
        this.exposeUnfinishedEventVisits = exposeUnfinishedEventVisits;
    }

    public boolean isExposeUnfinishedEventActions() {
        return exposeUnfinishedEventActions;
    }

    public void setExposeUnfinishedEventActions(boolean exposeUnfinishedEventActions) {
        this.exposeUnfinishedEventActions = exposeUnfinishedEventActions;
    }

    public boolean isVisitExposition() {
        return visitExposition;
    }

    public void setVisitExposition(boolean visitExposition) {
        this.visitExposition = visitExposition;
    }

    public boolean isActionExposition() {
        return actionExposition;
    }

    public void setActionExposition(boolean actionExposition) {
        this.actionExposition = actionExposition;
    }

    public boolean isExposeDiscipline() {
        return exposeDiscipline;
    }

    public void setExposeDiscipline(boolean exposeDiscipline) {
        this.exposeDiscipline = exposeDiscipline;
    }

    public int getPriceListId() {
        return priceListId;
    }

    public void setPriceListId(int priceListId) {
        this.priceListId = priceListId;
    }

    public double getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(double coefficient) {
        this.coefficient = coefficient;
    }

    public double getCoefficientEx() {
        return coefficientEx;
    }

    public void setCoefficientEx(double coefficientEx) {
        this.coefficientEx = coefficientEx;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contract contract = (Contract) o;

        if (!id.equals(contract.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.Contract[id=" + id + "]";
    }
}
