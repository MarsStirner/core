package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.14, 10:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DisabilitiesInfo {
    /**
     * Идентификатор в МИС
     */
    private final String id;

    /**
     * Дата/время ввода записи в МИС
     */
    private final XMLGregorianCalendar createDate;

    /**
     * Группа инвалидности
     */
    private final String group;

    /**
     * Признак ребенок-инвалид
     */
    private final Boolean isChild;

    /**
     * Действует с
     */
    private final XMLGregorianCalendar begDate;

     /**
     * Действует по
     */
    private final XMLGregorianCalendar endDate;

    /**
     * Установлена впервые в жизни
     */
    private final Boolean isFirstTime;

    public DisabilitiesInfo(ClientSocStatus clientSocStatus) {
        id = String.valueOf(clientSocStatus.getId());
        createDate = ClientInfo.getXmlGregorianCalendar(clientSocStatus.getCreateDatetime());
        begDate = ClientInfo.getXmlGregorianCalendar(clientSocStatus.getBegDate());
        endDate = ClientInfo.getXmlGregorianCalendar(clientSocStatus.getEndDate());
        group = //TODO;
        isChild = //TODO;
        isFirstTime = //TODO;
    }

    public String getId() {
        return id;
    }

    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    public String getGroup() {
        return group;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public XMLGregorianCalendar getBegDate() {
        return begDate;
    }

    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    public Boolean getIsFirstTime() {
        return isFirstTime;
    }
}
