package ru.korus.tmis.core.entity.model.hl7;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 04.12.12
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
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
    @Column(name = "status")
    private String status;

    @Basic(optional = false)
    @Column(name = "uuid")
    private String documentUUID;

    @Basic(optional = false)
    @Column(name = "result")
    private String result;

    public Pharmacy() {
    }

    public int getActionId() {
        return actionId;
    }

    public String getFlatCode() {
        return flatCode;
    }

    public String getStatus() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
                "actionId=" + actionId +
                ", flatCode='" + flatCode + '\'' +
                ", status='" + status + '\'' +
                ", documentUUID='" + documentUUID + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
