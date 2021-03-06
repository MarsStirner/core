package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.entity.model.*;
import com.google.common.collect.Multimap;
import org.joda.time.DateTime;
import org.joda.time.Years;
import ru.korus.tmis.core.database.DbQueryBeanLocal;
import ru.korus.tmis.core.database.RbMedicalAidProfileBeanLocal;
import ru.korus.tmis.core.database.RbMedicalAidTypeBeanLocal;

import ru.korus.tmis.core.patient.HospitalBedBeanLocal;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.List;

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
    private final CodeNameSystem serviceCode;

    /**
     * Диагноз (код МКБ-10 и расшифровка)
     */
    private final CodeNameSystem diagnosis;

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
    private final CodeNameSystem servType;

    /**
     * Отделение МО лечения из регионального справочник
     */
    private final CodeNameSystem orgStruct;

    /**
     * Профиль оказанной медицинской помощи
     */
    private final CodeNameSystem serviceProfile;

    /**
     * Признак детского профиля
     */
    private final Boolean isChildProfile;

    /**
     * Профиль койки
     */
    private final String bedProfile;

    public ServiceInfo(final Action action,
                       final Multimap<String, Action> actionsByTypeCode,
                       final DbQueryBeanLocal dbCustomQueryBean,
                       final RbMedicalAidProfileBeanLocal rbMedicalAidProfileBean,
                       final HospitalBedBeanLocal hospitalBedBeanLocal) {
        this.id = String.valueOf(action.getId());
        this.createdPerson = EmployeeInfo.newInstance(action.getCreatePerson());
        this.createDate = ClientInfo.getXmlGregorianCalendar(action.getEndDate());
        final ActionType actionType = action.getActionType();
        final RbService service = actionType.getService();
        this.serviceCode = service == null ? null : RbManager.get(RbManager.RbType.SST365,
                CodeNameSystem.newInstance(service.getCode(), service.getName(), "1.2.643.5.1.13.2.1.1.473"));
        this.amount = action.getAmount();
        this.person = EmployeeInfo.newInstance(action.getExecutor());
        final Event event = action.getEvent();
        final Staff executor = event == null ? null : event.getExecutor();
        final OrgStructure orgStructure = executor == null ? null : executor.getOrgStructure();
        this.orgStruct = orgStructure == null ? null : CodeNameSystem.newInstance(orgStructure.getCode(), orgStructure.getName(), null);
        Date birthDate = event == null ? null : event.getPatient().getBirthDate();
        Date setDate = event == null ? null : event.getSetDate();
        Boolean isChild = null;
        if (birthDate != null && setDate != null) {
            isChild = Years.yearsBetween(new DateTime(birthDate), new DateTime(setDate)).getYears() < 18; //
        }
        this.isChildProfile = isChild;

        final Action moving =  hospitalBedBeanLocal.getLastMovingActionForEventId(event.getId());
        this.bedProfile = moving == null ? null : dbCustomQueryBean.getBedProfileName(moving); // 450

        CodeNameSystem diag = null;
        final List<Diagnostic> diagnostics = action.getEvent().getDiagnostics();
        for (Diagnostic d : diagnostics) {
            if (d.getStage() != null) {
                if (d.getStage().getId() == 1 || d.getStage().getId() == 4) {
              /*
              Из переписки с Александром Мартыновым:
                В записи о диагнозе Diagnostic есть поле stage_id – ссылка на идентификатор id записи таблицы rbDiseaseStage с данными о типах диагнозах.
                Если stage_id не заполнен, нужно анализировать поле Diagnostic.diagnosisType_id – ссылку на rbDiagnosisType.id. Если diagnosisType_id = 2,
                то диагноз основной, если diagnosisType_id = 1, то диагноз заключительный

                получаю список Diagnostic для нужного Event,
                проверяю stage_id = 1, тогда использую этот Diagnostic, иначе
                анализирую  поле Diagnostic.diagnosisType_id – ссылку на rbDiagnosisType.id.
                Если diagnosisType_id = 2, то диагноз основной, если diagnosisType_id = 1, то диагноз заключительный
              */
                    diag = DiagnosisInfo.toMKB308Diagnosis(d);
                }
            } else {
                if (d.getDiagnosisType().getId() == 1 || d.getDiagnosisType().getId() == 2) {
                    diag = DiagnosisInfo.toMKB308Diagnosis(d);
                }
            }
        }
        this.diagnosis = diag;

        final RbMedicalAidType type = action.getEvent().getEventType().getRbMedicalAidType();
        this.servType = type == null ? null : CodeNameSystem.newInstance(type.getCode(), type.getName(), null);

        final RbMedicalAidProfile profile = service != null && service.getMedicalAidProfile() == null ? null : service.getMedicalAidProfile();
        this.serviceProfile = (profile == null) ? null : CodeNameSystem.newInstance(profile.getCode(), profile.getName(), null);
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

    public CodeNameSystem getServiceCode() {
        return serviceCode;
    }

    public CodeNameSystem getDiagnosis() {
        return diagnosis;
    }

    public Double getAmount() {
        return amount;
    }

    public EmployeeInfo getPerson() {
        return person;
    }

    public CodeNameSystem getServType() {
        return servType;
    }

    public CodeNameSystem getOrgStruct() {
        return orgStruct;
    }

    public CodeNameSystem getServiceProfile() {
        return serviceProfile;
    }

    public Boolean getIsChildProfile() {
        return isChildProfile;
    }

    public String getBedProfile() {
        return bedProfile;
    }
}
