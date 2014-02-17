package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rbTest")
@NamedQueries(
        {
                @NamedQuery(name = "RbTest.findAll", query = "SELECT r FROM RbTest r"),
                @NamedQuery(name = "RbTest.findById", query = "SELECT r FROM RbTest r WHERE r.id = :id")
        })
@XmlType(name = "rbTest")
@XmlRootElement(name = "rbTest")
public class RbTest implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "name")
    private String name;

    @OneToOne
    @PrimaryKeyJoinColumn(name="id", referencedColumnName="test_id")
    private RbLaboratoryTest rbLaboratoryTest;

    public RbTest() {
    }

    public RbTest(Integer id) {
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

    public RbLaboratoryTest getRbLaboratoryTest() {
        return rbLaboratoryTest;
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
        if (!(object instanceof RbTest)) {
            return false;
        }
        RbTest other = (RbTest) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.RbTest[id=" + id + "]";
    }

}
