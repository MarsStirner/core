package ru.korus.tmis.core.entity.model.bak;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 17:37 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Entity
@Table(name = "rbAntibiotic", catalog = "", schema = "")
public class RbAntibiotic implements Serializable {

    @Id
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    @Basic(optional = false)
    @Column(name = "code")
    private String code;

    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    public RbAntibiotic() {
    }

    public Integer getId() {
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
        return "RbAntibiotic{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

