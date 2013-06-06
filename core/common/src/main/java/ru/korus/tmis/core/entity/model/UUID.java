package ru.korus.tmis.core.entity.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 09.12.12
 * Time: 15:25
 * Таблица уникальных идентификаторов UUID
 */

@Entity
@Table(name = "UUID", catalog = "", schema = "")
public class UUID implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    @Basic(optional = false)
    @Column(name = "uuid", unique = true)
    private String uuid;


    public UUID() {
    }

    public UUID(String uuid) {
        this.uuid = uuid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid != null ? uuid : "";
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "UUID{" +
                "id=" + id +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
