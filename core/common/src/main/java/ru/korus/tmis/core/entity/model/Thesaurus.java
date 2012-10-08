package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@Entity
@Table(name = "rbThesaurus")
@NamedQueries(
        {
                @NamedQuery(name = "Thesaurus.findAll", query = "SELECT t FROM Thesaurus t"),
                @NamedQuery(name = "Thesaurus.findByGroupId",
                        query = "SELECT t FROM Thesaurus t WHERE t.groupId = :groupId"),
                @NamedQuery(name = "Thesaurus.findById",
                        query = "SELECT t FROM Thesaurus t WHERE t.id = :id"),
                @NamedQuery(name = "Thesaurus.findByCode",
                        query = "SELECT t FROM Thesaurus t WHERE t.code = :code AND t.groupId IS NULL")
        })
@XmlType(name = "thesaurus")
@XmlRootElement(name = "thesaurus")
public class Thesaurus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "group_id")
    private Integer groupId;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "template")
    private String template;

    public Thesaurus() {
    }

    public Thesaurus(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
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

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
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
        if (!(object instanceof Thesaurus)) {
            return false;
        }
        Thesaurus other = (Thesaurus) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Thesaurus[id=" + id + "]";
    }
}
