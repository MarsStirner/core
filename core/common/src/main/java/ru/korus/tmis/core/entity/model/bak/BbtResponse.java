package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 15:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "bbtResponse", catalog = "", schema = "")
public class BbtResponse implements Serializable {

    /**
     * id == actionId
     */
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "final")
    private Integer finalFlag;

    @Basic(optional = false)
    @Column(name = "defects")
    private String defects;

    @Basic(optional = false)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @Basic(optional = false)
    @Column(name = "codeLIS")
    private Integer codeLIS;


    public BbtResponse() {
    }

    public Integer getId() {
        return id;
    }

    public Integer getFinalFlag() {
        return finalFlag;
    }

    public String getDefects() {
        return defects;
    }

    public Integer getDoctorId() {
        return doctorId;
    }

    public Integer getCodeLIS() {
        return codeLIS;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFinalFlag(Integer finalFlag) {
        this.finalFlag = finalFlag;
    }

    public void setDefects(String defects) {
        this.defects = defects;
    }

    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    public void setCodeLIS(Integer codeLIS) {
        this.codeLIS = codeLIS;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BbtResponse{");
        sb.append("id=").append(id);
        sb.append(", finalFlag=").append(finalFlag);
        sb.append(", defects='").append(defects).append('\'');
        sb.append(", doctorId=").append(doctorId);
        sb.append(", codeLIS=").append(codeLIS);
        sb.append('}');
        return sb.toString();
    }
}
