package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Versions", catalog = "", schema = "")
@NamedQueries(
        {
                @NamedQuery(name = "DbVersions.findAll",
                        query = "SELECT d FROM DbVersions d ORDER BY d.id")
        })
public class DbVersions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "table")
    private String table;

    @Basic(optional = false)
    @Column(name = "version")
    private int version;

    public DbVersions() {
    }

    public DbVersions(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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
        if (!(object instanceof DbVersions)) {
            return false;
        }
        DbVersions other = (DbVersions) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.DbVersions[id=" + id + "]";
    }
}
