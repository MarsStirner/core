package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the EventPayment database table.
 */
@Entity
@Table(name = "Event_Payment")
public class EventPayment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private int id;

    @Column(nullable = false)
    private double actionSum;

    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;

    @Column(nullable = false, length = 32)
    private String cashBox;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "master_id", nullable = false)
    private Event event;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date modifyDatetime;

    @ManyToOne
    @JoinColumn(name = "modifyPerson_id")
    private Staff modifyPerson;

    @Column(length = 64)
    private String numberCreditCard;

    @Column(length = 64)
    private String settlementAccount;

    @Column(nullable = false)
    private double sum;

    @Column(nullable = false)
    private byte typePayment;

    //bi-directional many-to-one association to RbCashOperation
    @ManyToOne
    @JoinColumn(name = "cashOperation_id")
    private RbCashOperation rbCashOperation;

    @ManyToOne
    @JoinColumn(name = "action_id")
    private Action action;

    @ManyToOne
    @JoinColumn(name = "service_id")
    private RbService service;

    public EventPayment() {
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getActionSum() {
        return this.actionSum;
    }

    public void setActionSum(double actionSum) {
        this.actionSum = actionSum;
    }

    public Bank getBank() {
        return this.bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public String getCashBox() {
        return this.cashBox;
    }

    public void setCashBox(String cashBox) {
        this.cashBox = cashBox;
    }

    public Date getCreateDatetime() {
        return this.createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return this.createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean getDeleted() {
        return this.deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Event getEvent() {
        return this.event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Date getModifyDatetime() {
        return this.modifyDatetime;
    }

    public void setModifyDatetime(Date modifyDatetime) {
        this.modifyDatetime = modifyDatetime;
    }

    public Staff getModifyPerson() {
        return this.modifyPerson;
    }

    public void setModifyPerson(Staff modifyPerson) {
        this.modifyPerson = modifyPerson;
    }

    public String getNumberCreditCard() {
        return this.numberCreditCard;
    }

    public void setNumberCreditCard(String numberCreditCard) {
        this.numberCreditCard = numberCreditCard;
    }

    public String getSettlementAccount() {
        return this.settlementAccount;
    }

    public void setSettlementAccount(String settlementAccount) {
        this.settlementAccount = settlementAccount;
    }

    public double getSum() {
        return this.sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public byte getTypePayment() {
        return this.typePayment;
    }

    public void setTypePayment(byte typePayment) {
        this.typePayment = typePayment;
    }

    public RbCashOperation getRbCashOperation() {
        return this.rbCashOperation;
    }

    public void setRbCashOperation(RbCashOperation rbCashOperation) {
        this.rbCashOperation = rbCashOperation;
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

}