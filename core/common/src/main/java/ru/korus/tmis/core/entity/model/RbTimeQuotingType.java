package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 11.06.2014, 16:41 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

@Entity
@Table(name = "rbTimeQuotingType")
@NamedQueries({
        @NamedQuery(name = "RbTimeQuotingType.findAll", query = "SELECT qt FROM RbTimeQuotingType qt")
})
public class RbTimeQuotingType {

    //TODO хз как лучше их захардкодить по коду
    public static final String FROM_REGISTRY_QUOTING_TYPE_CODE = "1";
    public static final String SECOND_VISIT_QUOTING_TYPE_CODE = "2";
    public static final String BETWEEN_CABINET_QUOTING_TYPE_CODE = "3";
    public static final String FROM_OTHER_LPU_QUOTING_TYPE_CODE = "4";
    public static final String FROM_PORTAL_QUOTING_TYPE_CODE = "5";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RbTimeQuotingType() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RbTimeQuotingType that = (RbTimeQuotingType) o;

        if (!code.equals(that.code)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbTimeQuotingType[");
        sb.append(id);
        sb.append("]{code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
