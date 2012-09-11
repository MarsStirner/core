package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Класс для представления роли (профиля в терминах предыдущих версий системы).
 */
@Entity
@Table(name = "rbUserProfile")
@NamedQueries(
        {
                @NamedQuery(name = "Role.findAll", query = "SELECT r FROM Role r")
        })
@XmlType(name = "role")
@XmlRootElement(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

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

    ////////////////////////////////////////////////////////////////////////////
    // Custom mappings
    ////////////////////////////////////////////////////////////////////////////

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "rbUserProfile_Right",
               joinColumns = {@JoinColumn(name = "master_id")},
               inverseJoinColumns = {@JoinColumn(name = "userRight_id")})
    private Set<UserRight> rights = new LinkedHashSet<UserRight>();

    public Set<UserRight> getRights() {
        return rights;
    }

    public void setRights(final Set<UserRight> rights) {
        this.rights = rights;
    }

    ////////////////////////////////////////////////////////////////////////////
    // End of custom mappings
    ////////////////////////////////////////////////////////////////////////////

    public Role() {
    }

    public Role(Integer id) {
        this.id = id;
    }

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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Role)) {
            return false;
        }
        Role other = (Role) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Role[id=" + id + "]";
    }
}
