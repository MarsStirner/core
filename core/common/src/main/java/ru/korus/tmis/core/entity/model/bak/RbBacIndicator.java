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
@Table(name = "rbBacIndicator")
public class RbBacIndicator  implements Serializable {

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

    public RbBacIndicator() {
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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "RbBacIndicator{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
