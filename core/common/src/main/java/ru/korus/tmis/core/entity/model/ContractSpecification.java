package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 31.01.13
 * Time: 15:11
 * To change this template use File | Settings | File Templates.
 */

@Entity
@Table(name = "Contract_Specification")
@NamedQueries(
        {
                @NamedQuery(name = "ContractSpecification.findAll", query = "SELECT c FROM ContractSpecification c")
        })
@XmlType(name = "contractSpecification")
@XmlRootElement(name = "contractSpecification")
public class ContractSpecification implements Serializable, Cloneable {
        private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;

    @ManyToOne
    @JoinColumn(name = "master_id")
    private Contract master;

    @ManyToOne
    @JoinColumn(name = "eventType_id")
    private EventType eventType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Contract getMaster() {
        return master;
    }

    public void setMaster(Contract master) {
        this.master = master;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContractSpecification that = (ContractSpecification) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.database.model.ContractSpecification[id=" + id + "]";
    }
}
