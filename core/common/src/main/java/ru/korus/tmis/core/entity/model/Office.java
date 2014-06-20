package ru.korus.tmis.core.entity.model;

import javax.persistence.*;

/**
 * Author: Upatov Egor <br>
 * Date: 11.06.2014, 16:25 <br>
 * Company: Korus Consulting IT <br>
 * Description: Кабинет  <br>
 */
@Entity
@Table(name = "Office")
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orgStructure_id", nullable = true)
    private OrgStructure orgStructure;

    public Office() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Office office = (Office) o;

        if (!code.equals(office.code)) return false;
        if (!id.equals(office.id)) return false;

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
        final StringBuilder sb = new StringBuilder("Office[");
        sb.append(id);
        sb.append("]{code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        if(orgStructure != null) {
            sb.append(", orgStructure[").append(orgStructure.getId()).append(']');
        }  else {
            sb.append(", orgStructure=null");
        }
        sb.append('}');
        return sb.toString();
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

    public OrgStructure getOrgStructure() {
        return orgStructure;
    }

    public void setOrgStructure(OrgStructure orgStructure) {
        this.orgStructure = orgStructure;
    }
}
