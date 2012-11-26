package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity для работы из ORM с таблицей s11r64.QuotaType.
 * @author mmakankov
 * @since 1.0.0.48
 */
@Entity
@Table(name = "QuotaType", catalog = "", schema = "s11r64")
@NamedQueries(
        {
                @NamedQuery(name = "QuotaType.findAll", query = "SELECT cq FROM QuotaType cq")
        })
@XmlType(name = "QuotaType")
@XmlRootElement(name = "QuotaType")
public class QuotaType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "MKB")
    private Mkb mkb;

    @Column(name = "class")
    private Integer quotaClass;

    @Basic(optional = false)
    @Column(name = "createDatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDatetime;

    @ManyToOne
    @JoinColumn(name = "createPerson_id")
    private Staff createPerson;

    @Column(name = "teenOlder")
    private Integer teenOlder;

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

    public Mkb getMkb() {
        return mkb;
    }

    public void setMkb(Mkb mkb) {
        this.mkb = mkb;
    }

    public Integer getQuotaClass() {
        return quotaClass;
    }

    public void setQuotaClass(Integer quotaClass) {
        this.quotaClass = quotaClass;
    }

    public Date getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Date createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Staff getCreatePerson() {
        return createPerson;
    }

    public void setCreatePerson(Staff createPerson) {
        this.createPerson = createPerson;
    }

    public Integer getTeenOlder() {
        return teenOlder;
    }

    public void setTeenOlder(Integer teenOlder) {
        this.teenOlder = teenOlder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        QuotaType quotaType = (QuotaType) o;

        if (!id.equals(quotaType.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "ru.korus.tmis.core.entity.model.QuotaType[id=" + id + "]";
    }
}
