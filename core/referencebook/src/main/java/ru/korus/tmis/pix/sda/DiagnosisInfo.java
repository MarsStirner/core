package ru.korus.tmis.pix.sda;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Diagnosis;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.entity.model.RbDiagnosisType;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.util.EntityMgr;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        09.07.2013, 16:18:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class DiagnosisInfo {
    /**
     * UUID события
     */
    private final String eventUuid;

    /**
     * Идентификатор врача, зафиксировавшего данные
     */
    private final Integer personCreatedId;

    /**
     * ФИО врача, зафиксировавшего данные (Фамилия + Имя + Отчество)
     */
    private final String personCreatedName;

    /**
     * Дата/время диагноза
     */
    private final XMLGregorianCalendar createDate;

    /**
     * Код МКБ
     */
    private final String mkb;

    /**
     * Диагноз
     */
    private final String diagName;

    public DiagnosisInfo(Event event, Diagnosis diagnosis, RbDiagnosisType diagnosisType) {
        final UUID uuid = event.getUuid();
        this.eventUuid = uuid != null ? uuid.getUuid() : null;
        final Staff person = diagnosis.getPerson();
        this.personCreatedId = person != null ? person.getId() : null;
        this.personCreatedName = person != null ? person.getFullName() : null;
        XMLGregorianCalendar createDate = null;
        try {
            createDate = EntityMgr.toGregorianCalendar(diagnosis.getCreateDatetime());
        } catch (DatatypeConfigurationException e) {
        }
        this.createDate = createDate;
        final Mkb mkb = diagnosis.getMkb();
        this.mkb = mkb != null ? mkb.getDiagID() : null;
        this.diagName = (mkb != null ? mkb.getDiagName() : "").trim() + "(" + (diagnosisType != null ? diagnosisType.getName() : "") + ")";
    }

    /**
     * @return the eventUuid
     */
    public String getEventUuid() {
        return eventUuid;
    }

    /**
     * @return the personCreatedId
     */
    public Integer getPersonCreatedId() {
        return personCreatedId;
    }

    /**
     * @return the personCreatedName
     */
    public String getPersonCreatedName() {
        return personCreatedName;
    }

    /**
     * @return the createDate
     */
    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    /**
     * @return the mkb
     */
    public String getMkb() {
        return mkb;
    }

    /**
     * @return the diagName
     */
    public String getDiagName() {
        return diagName;
    }

}
