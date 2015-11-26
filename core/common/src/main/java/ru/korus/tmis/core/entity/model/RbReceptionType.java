package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 14:24 <br>
 * Company: Korus Consulting IT <br>
 * Description: Тип приема (на дому \ амбулаторно) (dbtool 175) <br>
 */

@Entity
@Table(name = "rbReceptionType")
@NamedQueries(
        {
                @NamedQuery(name = "rbReceptionType.findAll", query = "SELECT r FROM RbReceptionType r")
        })
public class RbReceptionType {
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

    public RbReceptionType() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RbReceptionType that = (RbReceptionType) o;

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
        final StringBuilder sb = new StringBuilder("RbReceptionType[");
        sb.append(id);
        sb.append("] {code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
