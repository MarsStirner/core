package ru.korus.tmis.core.entity.model.pharmacy;

import javax.persistence.*;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.12.12, 19:51 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "Pharmacy")
public class Pharmacy implements Serializable {

    private static final long serialVersionUID = 1L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(name = "id")
//    private Integer id;

    @Id
    @Basic(optional = false)
    @Column(name = "actionId")
    private Integer actionId;

    @Basic(optional = false)
    @Column(name = "flatCode")
    private String flatCode;

    @Basic(optional = false)
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PharmacyStatus status;

    @Basic(optional = false)
    @Column(name = "uuid")
    private byte[] uuid;

    @Basic(optional = false)
    @Column(name = "result")
    private String result;

    @Basic(optional = false)
    @Column(name = "attempts")
//    @Transient
    private int attempts = 0;

    @Basic(optional = false)
    @Column(name = "error_string")
//    @Transient
    private String errorString = "";

    public Pharmacy() {
    }

    public String getErrorString() {
        return errorString;
    }

    public void setErrorString(String errorString) {
        this.errorString = errorString;
    }

    public int getActionId() {
        return actionId;
    }

    public String getFlatCode() {
        return flatCode;
    }

    public PharmacyStatus getStatus() {
        return status;
    }

    public UUID getUuid() {
        final ByteBuffer bb = ByteBuffer.wrap(uuid);
        long high = bb.getLong();
        long low = bb.getLong();
        return new UUID(high, low);
    }

    public void setUuid(UUID uuid) {
        final ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        this.uuid =  bb.array();
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    public void setStatus(PharmacyStatus status) {
        if(status == PharmacyStatus.ERROR && this.status == PharmacyStatus.RESEND) {
            return;
        }
        this.status = status;
        if (status.equals(PharmacyStatus.COMPLETE)) {
            errorString = "";
        }
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getAttempts() {
        return attempts;
    }

    public void incAttempts() {
        this.attempts++;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "actionId=" + actionId +
                ", flatCode='" + flatCode + '\'' +
                ", status=" + status +
                ", uuid='" + getUuid().toString() + '\'' +
                ", result='" + result + '\'' +
                ", attempts=" + attempts +
                ", errorString='" + errorString + '\'' +
                '}';
    }
}
