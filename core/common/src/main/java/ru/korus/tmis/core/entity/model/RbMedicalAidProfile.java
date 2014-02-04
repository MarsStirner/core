package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "rbMedicalAidProfile")
@NamedQueries(
        {
                @NamedQuery(name = "RbMedicalAidProfile.findAll", query = "SELECT r FROM RbMedicalAidProfile r"),
                @NamedQuery(name = "RbMedicalAidProfile.getProfileById", query = "SELECT r FROM RbMedicalAidProfile r WHERE r.id = :id")
        })
public class RbMedicalAidProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, length = 16)
    private String code;

    @Column(nullable = false, length = 16)
    private String regionalCode;

    @Column(nullable = false, length = 64)
    private String name;

    public RbMedicalAidProfile() {
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getRegionalCode() {
        return regionalCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("RbMedicalAidProfile{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", regionalCode='").append(regionalCode).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
