package ru.korus.tmis.entity;

import nsi.V005Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор пола застрахованного (Pol)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V005_Pol", catalog = "", schema = "")
public class V005Pol implements Serializable {
    /**
     * Код пола застрахованного
     */
    @Id
    @Column(name = "idpol")
    private long id;

    /**
     * Наименование пола застрахованного
     */
    @Column(name = "polname")
    private String polName;

    public V005Pol() {
    }

    public static V005Pol getInstance(V005Type type) {
        return new V005Pol(
                type.getIDPOL(),
                type.getPOLNAME());
    }


    private V005Pol(long id, String polName) {
        this.id = id;
        this.polName = polName;
    }

    public long getId() {
        return id;
    }

    public String getPolName() {
        return polName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V005Pol{");
        sb.append("id=").append(id);
        sb.append(", polName='").append(polName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
