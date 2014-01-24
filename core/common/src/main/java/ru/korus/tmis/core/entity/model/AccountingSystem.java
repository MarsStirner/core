package ru.korus.tmis.core.entity.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * @author anosov
 *         Date: 07.09.13 15:09
 */
@Entity
@Table(name = "rbAccountingSystem", schema = "")
@XmlType(name = "accountingsystem")
@XmlRootElement(name = "accountingsystem")
public class AccountingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "isEditable")
    private boolean isEditable;

    @Basic(optional = false)
    @Column(name = "showInClientInfo")
    private boolean showInClientInfo;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean editable) {
        isEditable = editable;
    }

    public boolean isShowInClientInfo() {
        return showInClientInfo;
    }

    public void setShowInClientInfo(boolean showInClientInfo) {
        this.showInClientInfo = showInClientInfo;
    }
}
