package ru.korus.tmis.core.entity.model;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "rbMedicalAidType")
@NamedQueries(
        {
                @NamedQuery(name = "RbMedicalAidType.findAll", query = "SELECT r FROM RbMedicalAidType r"),
                @NamedQuery(name = "RbMedicalAidType.getProfileById", query = "SELECT r FROM RbMedicalAidType r WHERE r.id = :id")
        })
public class RbMedicalAidType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, length = 8)
    private String code;

    @Column(nullable = false, length = 64)
    private String name;

    public RbMedicalAidType() {
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbMedicalAidType{");
        sb.append("name='").append(name).append('\'');
        sb.append(", code='").append(code).append('\'');
        sb.append(", id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
