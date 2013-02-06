package ru.korus.tmis.core.entity.model.pharmacy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.12.12, 19:51 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "Pharmacy", catalog = "", schema = "")
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
    private String documentUUID;

    @Basic(optional = false)
    @Column(name = "result")
    private String result;

//    @Basic(optional = false)
//    @Column(name = "attempts")
    private int attempts = 0;


    public Pharmacy() {
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

    public String getDocumentUUID() {
        return documentUUID;
    }

    public void setDocumentUUID(String documentUUID) {
        this.documentUUID = documentUUID;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    public void setStatus(PharmacyStatus status) {
        this.status = status;
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
                ", documentUUID='" + documentUUID + '\'' +
                ", result='" + result + '\'' +
                ", attempts=" + attempts +
                '}';
    }
}
