package ru.korus.tmis.entity;

import nsi.V008Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор видов медицинской помощи (VidMp)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V008_VidMp", catalog = "", schema = "")
public class V008VidMp implements Serializable {
    /**
     * Код вида медицинской помощи
     */
    @Id
    @Column(name = "idvmp")
    private long id;

    /**
     * Наименование вида медицинской помощи
     */
    @Column(name = "vmpname")
    private String vmpName;

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

    public V008VidMp() {
    }

    private V008VidMp(long id, String vmpName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.vmpName = vmpName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static V008VidMp getInstance(V008Type type) {
        return new V008VidMp(
                type.getIDVMP(),
                type.getVMPNAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );
    }

    public long getId() {
        return id;
    }

    public String getVmpName() {
        return vmpName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V008VidMp{");
        sb.append("id=").append(id);
        sb.append(", vmpName='").append(vmpName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
