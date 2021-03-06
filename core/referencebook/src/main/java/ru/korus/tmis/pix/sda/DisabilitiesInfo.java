package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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
        final String code = clientSocStatus.getSocStatusType() == null ? null : clientSocStatus.getSocStatusType().getCode();
        if (code != null) {
            Map<String, String> mapGroup = new HashMap<String, String>() {{
                put("081","1");
                put("082","2");
                put("083","3");
            }};
            group = mapGroup.get(code);
            isChild = code.equals("084");
            isFirstTime = code.equals("088");
        } else {
            group = null;
            isChild = null;
            isFirstTime = null;
        }
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
