package ru.korus.tmis.entity;

import nsi.V007Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Номенклатура МО (NomMO)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V007_NomMO", catalog = "", schema = "")
public class V007NomMO implements Serializable {
    /**
     * Код типа медицинской организации
     */
    @Id
    @Column(name = "idnmo")
    private long id;

    /**
     * Наименование типа медицинской организации
     */
    @Column(name = "nmoname")
    private String nmoName;

    /**
     * Дата начала действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "datebeg")
    private Date dateBegin;

    /**
     * Дата окончания действия записи
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "dateend")
    private Date dateEnd;

    public V007NomMO() {
    }

    private V007NomMO(long id, String nmoName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.nmoName = nmoName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static V007NomMO getInstance(V007Type type) {
        return new V007NomMO(
                type.getIDNMO(),
                type.getNMONAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNmoName() {
        return nmoName;
    }

    public void setNmoName(String nmoName) {
        this.nmoName = nmoName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public void setDateBegin(Date dateBegin) {
        this.dateBegin = dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V007NomMO{");
        sb.append("id=").append(id);
        sb.append(", nmoName='").append(nmoName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
