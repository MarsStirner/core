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
    @Column(name = "actionId", unique = true)
    private Integer actionId;

    @Basic(optional = false)
    @Column(name = "flatCode")
    private String flatCode;

    @Basic(optional = false)
    @Column(name = "status")
    private String status;


    public Pharmacy() {
    }

//    public Integer getId() {
//        return id;
//    }

    public int getActionId() {
        return actionId;
    }

    public String getFlatCode() {
        return flatCode;
    }

    public String getStatus() {
        return status;
    }

//    public void setId(Integer id) {
//        this.id = id;
//    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    public void setFlatCode(String flatCode) {
        this.flatCode = flatCode;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Pharmacy{" +
//                "id=" + id +
                ", actionId=" + actionId +
                ", flatCode='" + flatCode + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
