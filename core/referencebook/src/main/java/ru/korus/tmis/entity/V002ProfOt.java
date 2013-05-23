package ru.korus.tmis.entity;

import nsi.V002Type;
import ru.korus.tmis.utils.DateUtil;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Классификатор профилей оказанной медицинской помощи (ProfOt)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_V002_ProfOt", catalog = "", schema = "")
public class V002ProfOt implements Serializable {
    /**
     * Код профильного отделения
     */
    @Id
    @Column(name = "idpr")
    private long id;

    /**
     * Наименование профильного отделения
     */
    @Column(name = "prname")
    private String prName;

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

    public V002ProfOt() {
    }

    private V002ProfOt(long id, String prName, Date dateBegin, Date dateEnd) {
        this.id = id;
        this.prName = prName;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
    }

    public static V002ProfOt getInstance(V002Type type) {
        return new V002ProfOt(
                type.getIDPR(),
                type.getPRNAME(),
                DateUtil.getDate(type.getDATEBEG()),
                DateUtil.getDate(type.getDATEEND())
        );

    }

    public long getId() {
        return id;
    }

    public String getPrName() {
        return prName;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("V002ProfOt{");
        sb.append("id=").append(id);
        sb.append(", prName='").append(prName).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
