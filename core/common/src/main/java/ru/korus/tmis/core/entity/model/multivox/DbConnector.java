package ru.korus.tmis.core.entity.model.multivox;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 02.08.2016, 12:22 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Entity
@Table(name="DbConnector")
public class DbConnector {
    /**
     * Идентификатор (ключ) записи. Технический ключ используемый ORM надстройкой ПО АРИС "MultiVox"
     */
    @Id
    @Column(name = "oID", nullable = false)
    private String oID;

    /**
     * Идентификатор сообщения
     */
    @Column(name = "UID", nullable = false)
    private String UID;

    /**
     * Идентификатор сообщения связанного с данным сообщением.
     * Например, для сообщения о наличии результатов исследования - это  значение поля UID сообщения назначения
     */
    @Column(name = "ReplyUID")
    private String replyUID;

    /**
     * Идентификатор системы-источника сообщения
     */
    @Column(name = "Source", nullable = false)
    @Enumerated(EnumType.STRING)
    private System source;

    /**
     * Идентификатор системы-назначения сообщения
     */
    @Column(name = "Destination", nullable = false)
    @Enumerated(EnumType.STRING)
    private System destination;


    /**
     * Тип сообщения
     */
    @Column(name = "Type")
    private String type = "ORM^O01";


    /**
     * Способ кодировки сообщения
     */
    @Column(name = "ContentType")
    private String ContentType;

    /**
     * Собственно сообщение
     */
    @Column(name = "Message", nullable = false)
    private String message;

    /**
     * Время создания сообщения
     */
    @Column(name="SendTime", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;

    /**
     * Время передачи сообщения между системами. Заполняется сервисом обмена
     */
    @Column(name="ExchangeTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date exchangeTime;

    /**
     * Время обработки (признак обработки) сообщения получателем.
     * Используется получателем, например, для того, чтобы определить, что это сообщение уже обрабатывалось
     */
    @Column(name="ProcTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date procTime;

    /**
     * Собственно сообщение
     */
    @Column(name = "ErrorText")
    private String errorText;

    public DbConnector() {
    }

    public String getoID() {
        return oID;
    }

    public void setoID(final String oID) {
        this.oID = oID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(final String UID) {
        this.UID = UID;
    }

    public String getReplyUID() {
        return replyUID;
    }

    public void setReplyUID(final String replyUID) {
        this.replyUID = replyUID;
    }

    public System getSource() {
        return source;
    }

    public void setSource(final System source) {
        this.source = source;
    }

    public System getDestination() {
        return destination;
    }

    public void setDestination(final System destination) {
        this.destination = destination;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public String getContentType() {
        return ContentType;
    }

    public void setContentType(final String contentType) {
        ContentType = contentType;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(final Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getExchangeTime() {
        return exchangeTime;
    }

    public void setExchangeTime(final Date exchangeTime) {
        this.exchangeTime = exchangeTime;
    }

    public Date getProcTime() {
        return procTime;
    }

    public void setProcTime(final Date procTime) {
        this.procTime = procTime;
    }

    public String getErrorText() {
        return errorText;
    }

    public void setErrorText(final String errorText) {
        this.errorText = errorText;
    }

    public enum System {
        RIS,
        MIS
    }
}
