package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbAnalysisStatus")
@NamedQueries(
        {
                @NamedQuery(name = "RbAnalysisStatus.findAll", query = "SELECT r FROM RbAnalysisStatus r")
        })
@XmlType(name = "rbAnalysisStatus")
@XmlRootElement(name = "rbAnalysisStatus")
public class RbAnalysisStatus
        implements Serializable, DbEnumerable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "statusName")
    private String statusName;

    public RbAnalysisStatus() {
    }

    public RbAnalysisStatus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RbAnalysisStatus)) {
            return false;
        }
        RbAnalysisStatus other = (RbAnalysisStatus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbAnalysisStatus[id=" + id + "]";
    }

    private static Map<Integer, RbAnalysisStatus> dbEnumsById =
            new HashMap<Integer, RbAnalysisStatus>();

    private static Map<String, RbAnalysisStatus> dbEnumsByName =
            new HashMap<String, RbAnalysisStatus>();

    public static RbAnalysisStatus getById(final Integer id) {
        return dbEnumsById.get(id);
    }

    public static RbAnalysisStatus getByName(final String name) {
        return dbEnumsByName.get(name);
    }

    @Override
    public void loadEnums(final Collection<Object> enums) {
        Map<Integer, RbAnalysisStatus> newDbEnumsById =
                new HashMap<Integer, RbAnalysisStatus>();
        Map<String, RbAnalysisStatus> newDbEnumsByName =
                new HashMap<String, RbAnalysisStatus>();
        for (Object e : enums) {
            if (e instanceof RbAnalysisStatus) {
                RbAnalysisStatus ras = (RbAnalysisStatus) e;
                newDbEnumsById.put(ras.id, ras);
                newDbEnumsByName.put(ras.statusName, ras);
            }
        }
        dbEnumsById = newDbEnumsById;
        dbEnumsByName = newDbEnumsByName;
    }
}
