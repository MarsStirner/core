package ru.korus.tmis.pix.sda;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Years;
import ru.korus.tmis.core.entity.model.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        30.01.14, 13:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ServiceInfo {
    /**
     * Идентификатор в МИС
     */
    private final String id;

    /**
     * Автор записи (врач)
     */
    private final EmployeeInfo createdPerson;

    /**
     * Дата/время ввода записи в МИС и Дата/время оказания
     */
    private final XMLGregorianCalendar createDate;

    /**
     * Код и название услуги
     */
    private final CodeNamePair serviceCode;

    /**
     * Диагноз (код МКБ-10 и расшифровка)
     */
    private final CodeNamePair diagnosis;

    /**
     * Кол-во оказанных услуг
     */
    private final Double amount;

    /**
     * Исполнитель действия
     */
    private final EmployeeInfo person;

    /**
     * Вид медицинской помощи
     */
    private final CodeNamePair servType;

    /**
     * Отделение МО лечения из регионального справочник
     */
    private final CodeNamePair orgStruct;

    /**
     * Профиль оказанной медицинской помощи
     */
    private final CodeNamePair serviceProfile;

    /**
     * Признак детского профиля
     */
    private final Boolean isChildProfile;

    /**
     * Профиль койки
     */
    private final String bedProfile;

    public ServiceInfo(Action action) {
        id = String.valueOf(action.getId());
        createdPerson = EmployeeInfo.newInstance(action.getCreatePerson());
        createDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        final RbService service = action.getActionType().getService();
        serviceCode = service == null ? null : new CodeNamePair(service.getCode(), service.getName());
        amount = action.getAmount();
        person = EmployeeInfo.newInstance(action.getExecutor());
        final Event event = action.getEvent();
        final Staff executor = event == null ? null : event.getExecutor();
        final OrgStructure orgStructure = executor == null ? null : executor.getOrgStructure();
        orgStruct = orgStructure == null ? null : new CodeNamePair(orgStructure.getCode(), orgStructure.getName());
        Date birthDate = event.getPatient().getBirthDate();
        Date setDate =  event.getSetDate();
        Boolean isChild = null;
        if (birthDate != null && setDate != null) {
            isChild = Years.yearsBetween(new DateTime(birthDate), new DateTime(setDate)).getYears() < 18; //
        }
        isChildProfile =isChild;
        bedProfile = null; //TODO:
        diagnosis = null; //TODO;
        servType = null; //TODO;
        serviceProfile = null;//TODO: event.getEventType().getMedicalAidTypeId();
    }

    public String getId() {
        return id;
    }

    public EmployeeInfo getCreatedPerson() {
        return createdPerson;
    }

    public XMLGregorianCalendar getCreateDate() {
        return createDate;
    }

    public CodeNamePair getServiceCode() {
        return serviceCode;
    }

    public CodeNamePair getDiagnosis() {
        return diagnosis;
    }

    public Double getAmount() {
        return amount;
    }

    public EmployeeInfo getPerson() {
        return person;
    }

    public CodeNamePair getServType() {
        return servType;
    }

    public CodeNamePair getOrgStruct() {
        return orgStruct;
    }

    public CodeNamePair getServiceProfile() {
        return serviceProfile;
    }

    public Boolean getIsChildProfile() {
        return isChildProfile;
    }

    public String getBedProfile() {
        return bedProfile;
    }
}
