package ru.korus.tmis.communication;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.core.database.DbStaffBeanLocal;
import ru.korus.tmis.core.database.common.DbPatientBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Schedule;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.schedule.CommunicationErrors;
import ru.korus.tmis.schedule.PersonSchedule;
import ru.korus.tmis.schedule.TypeOfQuota;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 13.11.13, 18:38 <br>
 * Company: Korus Consulting IT <br>
 * Description: Класс в который вынесена часть внутренних методов для работы КС<br>
 */
public class CommunicationHelper {

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(CommunicationHelper.class);
    private static RbTimeQuotingType BETWEEN_CABINET_QUOTING_TYPE;
    private static RbTimeQuotingType FROM_OTHER_LPU_QUOTING_TYPE;
    private static RbTimeQuotingType FROM_PORTAL_QUOTING_TYPE;
    private static RbTimeQuotingType FROM_REGISTRY_QUOTING_TYPE;
    private static RbTimeQuotingType SECOND_VISIT_QUOTING_TYPE;

    /**
     * Конвертация строки с указанием возраста в Дату
     *
     * @param agePart строка с указанием возраста
     * @return Дата рождения, соответствующая этому возрасту (от сейчас)
     */
    public static Date convertPartOfAgeStringToDate(final String agePart) throws NumberFormatException {
        try {
            if (agePart.contains("Д") || agePart.contains("д")) {
                int days = Integer.parseInt(agePart.substring(0, agePart.length() - 1));
                return new DateTime().minusDays(days).toDate();
            }
            if (agePart.contains("Г") || agePart.contains("г")) {
                int years = Integer.parseInt(agePart.substring(0, agePart.length() - 1));
                return new DateTime().minusYears(years).toDate();
            }
            if (agePart.contains("Н") || agePart.contains("н")) {
                int weeks = Integer.parseInt(agePart.substring(0, agePart.length() - 1));
                return new DateTime().minusWeeks(weeks).toDate();
            }
            if (agePart.contains("М") || agePart.contains("м")) {
                int months = Integer.parseInt(agePart.substring(0, agePart.length() - 1));
                return new DateTime().minusMonths(months).toDate();
            }
            throw new NumberFormatException("Неопознаный отрезок времени");
        } catch (NumberFormatException e) {
            logger.warn("Incorrect conversion of a string to a number. Return current date.");
        }
        return new Date();
    }

    /**
     * фильтрации возрастов
     *
     * @param age       возрастные ограничения ("{NNN{д|н|м|г}-{MMM{д|н|м|г}}" -
     *                  с NNN дней/недель/месяцев/лет по MMM дней/недель/месяцев/лет;
     *                  пустая нижняя или верхняя граница - нет ограничения снизу или сверху)
     * @param birthDate дата рождения пациента
     * @return Подходит ли пациент под ограничения, если строка неверна синтаксически, то вернет true
     */
    private static boolean checkAge(final String age, final Date birthDate) {
        //Parse age
        String[] ageArray = age.split("-", 2);
        if (ageArray.length > 2) {
            logger.warn("Value of age=\"{}\" in rbSpeciality table is strange, return true for check.", age);
            return true;
        }
        Date[] ageDateArray = new Date[ageArray.length];
        for (int i = 0; i < ageArray.length; i++) {
            ageDateArray[i] = CommunicationHelper.convertPartOfAgeStringToDate(ageArray[i]);
            logger.debug("DATE ={}", ageDateArray[i]);
        }
        switch (ageDateArray.length) {
            case 1: {
                return birthDate.before(ageDateArray[0]);
            }
            case 2: {
                return (birthDate.before(ageDateArray[0]) && birthDate.after(ageDateArray[1]));
            }
            default: {
                return true;
            }
        }
    }

    /**
     * подходит ли пол и возраст для данного врача
     *
     * @param patient    пациент
     * @param speciality специальность врача
     * @return результат проверки
     */
    public static boolean checkApplicable(
            final Patient patient, final ru.korus.tmis.core.entity.model.Speciality speciality
    ) {
        logger.debug("SPECIALITY age=\"{}\" sex=\"{}\"", speciality.getAge(), speciality.getSex());
        if (speciality.getSex() != (short) 0 && speciality.getSex() != patient.getSex()) {
            return false;
        } else {
            if (!speciality.getAge().isEmpty()) {
                return CommunicationHelper.checkAge(speciality.getAge(), patient.getBirthDate());
            } else {
                return true;
            }
        }
    }


    /**
     * Перевод из численного отображения пола к строковому
     *
     * @param sex 1="male", 2="male", X=""
     * @return строковое представление пола
     */
    public static String getSexAsString(final int sex) {
        switch (sex) {
            case 1: {
                return "male";
            }
            case 2: {
                return "female";
            }
            default: {
                return "";
            }
        }
    }

    /**
     * Проверка корректности значений параметров
     *
     * @param params параметры
     * @param result в случае ошибки будет заполнена
     * @return флажок корректности ( false = некорректно )
     */
    public static boolean checkAddPatientParams(final AddPatientParameters params, final PatientStatus result) {
        boolean allParamsIsSet = true;
        final StringBuilder errorMessage = new StringBuilder();
        if (!params.isSetLastName() || params.getLastName().length() == 0) {
            allParamsIsSet = false;
            errorMessage.append("Фамилия должна быть указана\n");
        }
        if (!params.isSetFirstName() || params.getFirstName().length() == 0) {
            allParamsIsSet = false;
            errorMessage.append("Имя должно быть указано\n");
        }
        if (!params.isSetPatrName() || params.getPatrName().length() == 0) {
            params.setPatrName("");
        }
        if (!params.isSetDocumentNumber() || !params.isSetDocumentSerial() || !params.isSetDocumentTypeCode() || params.getDocumentTypeCode()
                .isEmpty()) {
            allParamsIsSet = false;
            errorMessage.append("Не указан документ\n");
        }
        if (!allParamsIsSet) {
            result.setMessage(errorMessage.toString());
        }
        return allParamsIsSet;
    }


    public static TypeOfQuota getTypeOfQuota(final ScheduleParameters parameters) {
        if (parameters.isSetQuotingType()) {
            switch (parameters.getQuotingType()) {
                case BETWEEN_CABINET: {
                    return TypeOfQuota.BETWEEN_CABINET;
                }
                case FROM_OTHER_LPU: {
                    return TypeOfQuota.FROM_OTHER_LPU;
                }
                case FROM_PORTAL: {
                    return TypeOfQuota.FROM_PORTAL;
                }
                case FROM_REGISTRY: {
                    return TypeOfQuota.FROM_REGISTRY;
                }
                case SECOND_VISIT: {
                    return TypeOfQuota.SECOND_VISIT;
                }
                default: {
                    //НЕ должно выполнятся
                    return TypeOfQuota.FROM_REGISTRY;
                }
            }
        } else if (parameters.isSetHospitalUidFrom() && !parameters.getHospitalUidFrom().isEmpty()) {
            return TypeOfQuota.FROM_OTHER_LPU;
        } else {
            return TypeOfQuota.FROM_PORTAL;
        }
    }

    public static RbTimeQuotingType getQuotingType(final EnqueuePatientParameters parameters) {
        if (parameters.isSetQuotingType()) {
            switch (parameters.getQuotingType()) {
                case BETWEEN_CABINET: {
                    return BETWEEN_CABINET_QUOTING_TYPE;
                }
                case FROM_OTHER_LPU: {
                    return FROM_OTHER_LPU_QUOTING_TYPE;
                }
                case FROM_PORTAL: {
                    return FROM_PORTAL_QUOTING_TYPE;
                }
                case FROM_REGISTRY: {
                    return FROM_REGISTRY_QUOTING_TYPE;
                }
                case SECOND_VISIT: {
                    return SECOND_VISIT_QUOTING_TYPE;
                }
                default: {
                    //НЕ должно выполнятся
                    return FROM_REGISTRY_QUOTING_TYPE;
                }
            }
        } else if (parameters.isSetHospitalUidFrom() && !parameters.getHospitalUidFrom().isEmpty()) {
            return FROM_OTHER_LPU_QUOTING_TYPE;
        } else {
            return FROM_PORTAL_QUOTING_TYPE;
        }
    }

    public static RbTimeQuotingType getQuotingType(final ScheduleParameters parameters) {
        if (parameters.isSetQuotingType()) {
            switch (parameters.getQuotingType()) {
                case BETWEEN_CABINET: {
                    return BETWEEN_CABINET_QUOTING_TYPE;
                }
                case FROM_OTHER_LPU: {
                    return FROM_OTHER_LPU_QUOTING_TYPE;
                }
                case FROM_PORTAL: {
                    return FROM_PORTAL_QUOTING_TYPE;
                }
                case FROM_REGISTRY: {
                    return FROM_REGISTRY_QUOTING_TYPE;
                }
                case SECOND_VISIT: {
                    return SECOND_VISIT_QUOTING_TYPE;
                }
                default: {
                    //НЕ должно выполнятся
                    return FROM_REGISTRY_QUOTING_TYPE;
                }
            }
        } else if (parameters.isSetHospitalUidFrom() && !parameters.getHospitalUidFrom().isEmpty()) {
            return FROM_OTHER_LPU_QUOTING_TYPE;
        } else {
            return FROM_PORTAL_QUOTING_TYPE;
        }
    }

    public static TypeOfQuota getTypeOfQuota(final GetTimeWorkAndStatusParameters parameters) {
        if (parameters.isSetHospitalUidFrom() && !parameters.getHospitalUidFrom().isEmpty()) {
            return TypeOfQuota.FROM_OTHER_LPU;
        } else {
            return TypeOfQuota.FROM_PORTAL;
        }
    }

    public static String convertDotPatternToSQLLikePattern(final String dotPattern) {
        if (dotPattern != null && dotPattern.length() > 0) {
            return dotPattern.replaceAll("(\\.{3})|(\\*)", "%").replaceAll("\\.", "_");
        } else {
            return "";
        }
    }

    public static AppointmentType getAppointmentType(EnqueuePatientParameters params) {
        if (params.isSetHospitalUidFrom() && !params.getHospitalUidFrom().isEmpty()) {
            return AppointmentType.OTHER_LPU;
        } else {
            return AppointmentType.PORTAL;
        }
    }

    public static List<PersonSchedule> groupSchedulesByDate(final List<Schedule> scheduleList, final Staff person) {
        final Map<LocalDate, List<Schedule>> groupedList = new LinkedHashMap<LocalDate, List<Schedule>>(scheduleList.size());
        for (Schedule currentSchedule : scheduleList) {
            final LocalDate key = new LocalDate(currentSchedule.getDate());
            if (!groupedList.containsKey(key)) {
                final List<Schedule> newList = new ArrayList<Schedule>(2);
                newList.add(currentSchedule);
                groupedList.put(key, newList);
            } else {
                groupedList.get(key).add(currentSchedule);
            }
        }
        final List<PersonSchedule> personScheduleList = new ArrayList<PersonSchedule>(groupedList.size());
        for (Map.Entry<LocalDate, List<Schedule>> entry : groupedList.entrySet()) {
            personScheduleList.add(new PersonSchedule(entry.getKey(), person, entry.getValue()));
        }
        return personScheduleList;
    }

    public static int getTotalTicketCount(final List<Schedule> scheduleList) {
        int count = 0;
        for (Schedule current : scheduleList) {
            count += current.getNumTickets();
        }
        return count;
    }

    public static Patient findPatientById(final DbPatientBeanLocal patientBean, final int patientId)
            throws ru.korus.tmis.communication.thriftgen.NotFoundException {
        try {
            final Patient result = patientBean.getPatientById(patientId);
            if (result.getDeleted()) {
                logger.error("Patient[{}] is marked as deleted", patientId);
                throw new NotFoundException(CommunicationErrors.msgInvalidClientId.getMessage());
            }
            //проверить жив ли пациент
            if (!patientBean.isAlive(result)) {
                logger.warn("Unfortunately this patient is dead.");
                throw new NotFoundException(CommunicationErrors.msgPatientMarkedAsDead.getMessage());
            }
            return result;
        } catch (CoreException e) {
            logger.error("Not founded any patient with id={}", patientId);
            throw new NotFoundException(CommunicationErrors.msgWrongPatientId.getMessage());
        }
    }

    public static Staff findPersonById(final DbStaffBeanLocal staffBean, final int personId)
            throws ru.korus.tmis.communication.thriftgen.NotFoundException {
        final Staff result = staffBean.getStaffById(personId);
        if (result == null) {
            logger.error("Not founded any person with id={}", personId);
            throw new NotFoundException(CommunicationErrors.msgWrongDoctorId.getMessage());
        }
        if (result.getDeleted()) {
            logger.error("Person[{}] is marked as deleted", personId);
            throw new NotFoundException(CommunicationErrors.msgWrongDoctorId.getMessage());
        }
        return result;
    }

    public static void setQuotingTypeList(final List<RbTimeQuotingType> quotingTypeList) {
        logger.error("INIT WITH {}", quotingTypeList);
        for (RbTimeQuotingType current : quotingTypeList) {
            if (RbTimeQuotingType.FROM_REGISTRY_QUOTING_TYPE_CODE.equals(current.getCode())) {
                FROM_REGISTRY_QUOTING_TYPE = current;
            } else if (RbTimeQuotingType.SECOND_VISIT_QUOTING_TYPE_CODE.equals(current.getCode())) {
                SECOND_VISIT_QUOTING_TYPE = current;
            } else if (RbTimeQuotingType.BETWEEN_CABINET_QUOTING_TYPE_CODE.equals(current.getCode())) {
                BETWEEN_CABINET_QUOTING_TYPE = current;
            } else if (RbTimeQuotingType.FROM_OTHER_LPU_QUOTING_TYPE_CODE.equals(current.getCode())) {
                FROM_OTHER_LPU_QUOTING_TYPE = current;
            } else if (RbTimeQuotingType.FROM_PORTAL_QUOTING_TYPE_CODE.equals(current.getCode())) {
                FROM_PORTAL_QUOTING_TYPE = current;
            }
        }
    }


}
