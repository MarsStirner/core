package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.util.Objects;

/**
 * Author: Upatov Egor <br>
 * Date: 04.06.2014, 15:37 <br>
 * Company: Korus Consulting IT <br>
 * Description: тип талона (dbtool 175)<br>
 */

@Entity
@Table(name = "rbAttendanceType")
@NamedQueries(
        {
                @NamedQuery(name = "RbAttendanceType.findAll", query = "SELECT r FROM RbAttendanceType r")
        })
public class RbAttendanceType implements Comparable<RbAttendanceType> {
    @Transient
    public static final String CITO_CODE = "CITO";
    @Transient
    public static final String EXTRA_CODE = "extra";
    @Transient
    public static final String NORMAL_CODE = "planned";

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

    public RbAttendanceType() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RbAttendanceType that = (RbAttendanceType) o;

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
        final StringBuilder sb = new StringBuilder("RbAttendanceType[");
        sb.append(id);
        sb.append("] {code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(RbAttendanceType o) {
        if (Objects.equals(code, o.getCode())) {
            return 0;
        }
        switch (code) {
            case CITO_CODE:
                return -1;
            case EXTRA_CODE:
                return 1;
            default:
                return 0;
        }
    }
}
