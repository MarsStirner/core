package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 05.09.13, 19:05 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Entity
@Table(name = "rbPayRefuseType", schema = "", catalog = "")
@NamedQueries(
        @NamedQuery(name = "RbRefuseType.findByCode", query = "SELECT rt FROM RbPayRefuseType rt WHERE rt.code = :code")
)
public class RbPayRefuseType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "finance_id", nullable = false)
    private RbFinance finance;

    @Basic(optional = false)
    @Column(name = "rerun")
    private Short rerun;


    public RbPayRefuseType() {
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

    public RbFinance getFinance() {
        return finance;
    }

    public void setFinance(RbFinance finance) {
        this.finance = finance;
    }

    public Short getRerun() {
        return rerun;
    }

    public void setRerun(Short rerun) {
        this.rerun = rerun;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbPayRefuseType that = (RbPayRefuseType) o;

        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return this.getClass().getName() + "{id=" + id + "}";
    }
}
