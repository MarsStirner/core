package ru.korus.tmis.core.entity.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "MKB")
@NamedQueries({
        @NamedQuery(name = "Mkb.findAll", query = "SELECT m FROM Mkb m"),
        @NamedQuery(name = "Mkb.findByCode", query = "SELECT m FROM Mkb m WHERE m.diagID = :code"),
        @NamedQuery(name = "Mkb.findById", query = "SELECT m FROM Mkb m WHERE m.id = :id")})
@XmlType(name = "mkb")
@XmlRootElement(name = "mkb")
public class Mkb implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "ClassID")
    private String classID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "ClassName")
    private String className;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "BlockID")
    private String blockID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "BlockName")
    private String blockName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "DiagID")
    private String diagID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 160)
    @Column(name = "DiagName")
    private String diagName;
    @Basic(optional = false)
    //@NotNull
    //@Size(min = 1, max = 1)
    @Column(name = "Prim")
    private String prim;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sex")
    private short sex;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 12)
    @Column(name = "age")
    private String age;
    @Column(name = "age_bu")
    private Boolean ageBu;
    @Column(name = "age_bc")
    private Short ageBc;
    @Column(name = "age_eu")
    private Boolean ageEu;
    @Column(name = "age_ec")
    private Short ageEc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "characters")
    private short characters;
    @Basic(optional = false)
    @NotNull
    @Column(name = "duration")
    private int duration;
    @Column(name = "service_id")
    private Integer serviceId;
    @Column(name = "MKBSubclass_id")
    private Integer mKBSubclassid;
    @Basic(optional = false)
    @Column(name = "deleted")
    private boolean deleted;


    /* @OneToMany(mappedBy = "mkb", cascade = CascadeType.ALL)
private List<Diagnosis> diagnosis =
  new LinkedList<Diagnosis>(); */

    public Mkb() {
    }

    public Mkb(Integer id) {
        this.id = id;
    }

    public Mkb(Integer id, String classID, String className, String blockID, String blockName, String diagID, String diagName, String prim, short sex, String age, short characters, int duration) {
        this.id = id;
        this.classID = classID;
        this.className = className;
        this.blockID = blockID;
        this.blockName = blockName;
        this.diagID = diagID;
        this.diagName = diagName;
        this.prim = prim;
        this.sex = sex;
        this.age = age;
        this.characters = characters;
        this.duration = duration;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClassID() {
        return classID;
    }

    public void setClassID(String classID) {
        this.classID = classID;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBlockID() {
        return blockID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getDiagID() {
        return diagID;
    }

    public void setDiagID(String diagID) {
        this.diagID = diagID;
    }

    public String getDiagName() {
        return diagName;
    }

    public void setDiagName(String diagName) {
        this.diagName = diagName;
    }

    public String getPrim() {
        return prim;
    }

    public void setPrim(String prim) {
        this.prim = prim;
    }

    public short getSex() {
        return sex;
    }

    public void setSex(short sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Boolean getAgeBu() {
        return ageBu;
    }

    public void setAgeBu(Boolean ageBu) {
        this.ageBu = ageBu;
    }

    public Short getAgeBc() {
        return ageBc;
    }

    public void setAgeBc(Short ageBc) {
        this.ageBc = ageBc;
    }

    public Boolean getAgeEu() {
        return ageEu;
    }

    public void setAgeEu(Boolean ageEu) {
        this.ageEu = ageEu;
    }

    public Short getAgeEc() {
        return ageEc;
    }

    public void setAgeEc(Short ageEc) {
        this.ageEc = ageEc;
    }

    public short getCharacters() {
        return characters;
    }

    public void setCharacters(short characters) {
        this.characters = characters;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getMKBSubclassid() {
        return mKBSubclassid;
    }

    public void setMKBSubclassid(Integer mKBSubclassid) {
        this.mKBSubclassid = mKBSubclassid;
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
        if (!(object instanceof Mkb)) {
            return false;
        }
        Mkb other = (Mkb) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.Mkb[ id=" + id + " ]";
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
