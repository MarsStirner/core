package ru.korus.tmis.hsct;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import ru.korus.tmis.core.entity.model.hsct.QueueHsctRequest;

import javax.xml.bind.annotation.*;

/**
 * Author: Upatov Egor <br>
 * Date: 04.02.2016, 20:20 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@XmlType
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class QueueEntry {

    @XmlElement(name = "actionId")
    private Integer actionId;

    @XmlElement(name = "status")
    private String status;

    @XmlElement(name = "attempts")
    private Integer attempts;

    @XmlElement(name = "info")
    private String info;

    @XmlElement(name = "sendDateTime")
    private String sendDateTime;

    @XmlElement(name = "person")
    private String person;

    public QueueEntry() {
    }

    public QueueEntry(final QueueHsctRequest entry) {
        this.actionId = entry.getActionId();
        this.status = entry.getStatus().toString();
        this.attempts = entry.getAttempts();
        this.info = entry.getInfo();
        this.sendDateTime = entry.getSendDateTime().toString();
        this.person = entry.getPerson() == null ? null : entry.getPerson().getInfoString();
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(final Integer actionId) {
        this.actionId = actionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(final Integer attempts) {
        this.attempts = attempts;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String info) {
        this.info = info;
    }

    public String getSendDateTime() {
        return sendDateTime;
    }

    public void setSendDateTime(final String sendDateTime) {
        this.sendDateTime = sendDateTime;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(final String person) {
        this.person = person;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QueueEntry{");
        sb.append("actionId=").append(actionId);
        sb.append(", status='").append(status).append('\'');
        sb.append(", attempts=").append(attempts);
        sb.append(", info='").append(StringUtils.substring(info, 0, 10)).append('\'');
        sb.append(", sendDateTime='").append(sendDateTime).append('\'');
        sb.append(", person='").append(person).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
