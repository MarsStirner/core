package ru.korus.tmis.communication;

import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.exception.CoreException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 13.11.13, 18:38 <br>
 * Company: Korus Consulting IT <br>
 * Description: Класс в который вынесена часть внутренних методов для работы КС<br>
 */
public class CommunicationHelper {

    //Logger
    private static final Logger logger = LoggerFactory.getLogger(CommunicationHelper.class);

    /**
     * Конвертация строки с указанием возраста в Дату
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
     * @param patient    пациент
     * @param speciality специальность врача
     * @return результат проверки
     */
    public static boolean checkApplicable(
            final Patient patient, final ru.korus.tmis.core.entity.model.Speciality speciality) {
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
     * Применяет ограничения по времени (QuotingByTime врача) на возвращаемый набор талончиков(result.tickets)
     * @param constraints Ограничения по времени
     * @param ticketList  Список талончиков
     */
    public static void takeConstraintsOnTickets(final List<QuotingByTime> constraints, final List<Ticket> ticketList) {
        for (Ticket currentTicket : ticketList) {
            int available = 0;
            for (QuotingByTime qbt : constraints) {
                if (qbt.getQuotingTimeStart().getTime() != 0 && qbt.getQuotingTimeEnd().getTime() != 0) {
                    long qbtStartTime = DateConvertions.convertDateToUTCMilliseconds(qbt.getQuotingTimeStart());
                    long qbtEndTime = DateConvertions.convertDateToUTCMilliseconds(qbt.getQuotingTimeEnd());
                    if (currentTicket.getTime() >= qbtStartTime && currentTicket.getTime() <= qbtEndTime
                            && currentTicket.available == 1) {
                        available = 1;
                        break;
                    }
                }
            }
            currentTicket.setAvailable(available);
        }
    }

    /**
     * Перевод из численного отображения пола к строковому
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
        if (!allParamsIsSet) {
            result.setMessage(errorMessage.toString());
        }
        return allParamsIsSet;
    }

    /**
     * Проверка повторная ли запись этого пациента к этому врачу на сегодня
     * @param queueAMB список талончиков
     * @param personId ид пациента для проверки
     * @return false-еще не был записан, true- уже есть запись на сегодня
     */
    public static boolean checkRepetitionTicket(List<APValueAction> queueAMB, int personId) {
        if (queueAMB.isEmpty()) {
            return false;
        } else {
            for (APValueAction currentActionInQueueActions : queueAMB) {
                final Action queueAction = currentActionInQueueActions.getValue();
                if (queueAction != null) {
                    //Проверка на существование пациента при получении очереди
                    final Event queueEvent = queueAction.getEvent();
                    if (queueEvent != null) {
                        if (queueEvent.getPatient() == null) {
                            logger.warn("Not have any patient who own this action [{}] and this event [{}]",
                                    queueAction, queueEvent);
                        } else {
                            if (queueEvent.getPatient().getId() == personId) {
                                logger.info("Repetition queue detected. Reject enqueue.");
                                return true;
                            }
                        }
                    } else {
                        logger.warn("Patient queue action [{}] hasn't event.", queueAction);
                    }
                } //END OF IF (action is not null)
            } //END OF FOR
            return false;
        }
    }

    public static QuotingType getQuotingType(final ScheduleParameters parameters){
        if(parameters.isSetQuotingType()){
            return parameters.getQuotingType();
        } else if (parameters.isSetHospitalUidFrom() && !parameters.getHospitalUidFrom().isEmpty()){
            return QuotingType.FROM_OTHER_LPU;
        } else {
            return QuotingType.FROM_PORTAL;
        }
    }


    /**
     * Подсччет количества пациентов, записанных вне очереди и экстренно
     * @param queueAMB записи пациентов на прием
     * @return количество экстренных записей на прием к врачу
     */
    public static short getEmergencyPatientCount(List<APValueAction> queueAMB) {
        /**
         * количество записанных экстренно
         */
        short emergencyPatientCount = 0;
        /**
         * Количество записанных вне очереди
         */
        short outOfTurnCount = 0;

        for (APValueAction checkAction : queueAMB) {
            if (checkAction != null) {
                Action chechActionValue = checkAction.getValue();
                if (chechActionValue != null) {
                    Short pacientInQueueType = chechActionValue.getPacientInQueueType();
                    if (pacientInQueueType != null) {
                        if (pacientInQueueType == (short) 1) {
                            emergencyPatientCount++;
                        } else {
                            if (pacientInQueueType == (short) 2) {
                                outOfTurnCount++;
                            }
                        }
                    }
                }
            }
        }
        logger.debug("Founded {} emergency and {} out of turn actions", emergencyPatientCount, outOfTurnCount);
        return emergencyPatientCount;
    }


    /**
     * Получение ограничений врача на прием в заданную дату
     *
     * @param person          Врач для которого выбираются ограничения
     * @param constraintDate  Дата, на момент которой ищутся ограничения
     * @return список ограничений
     */
    public static List<QuotingByTime> getPersonConstraints(final Staff person, final Date constraintDate, final ru.korus.tmis.communication.thriftgen.QuotingType quotingType) {
        //2. Проверяем есть ли «причина отсутствия» этого врача в указанную дату _getReasonOfAbsence
        Action timelineAction = null;
        try {
            timelineAction = CommServer.getStaffBean().getPersonActionsByDateAndType(person.getId(), constraintDate, "timeline");
            if (logger.isDebugEnabled()) {
                if (timelineAction != null && CommServer.getActionPropertyBean().getActionPropertyValue(timelineAction.getActionProperties().get(0)).get(0) != null) {
                    logger.debug("TIMELINE ACTION [ ID={}, ACT_TYPE={}, EVENT={}, NOTE={}]", timelineAction.getId(),
                            timelineAction.getActionType().getName(), timelineAction.getEvent().getId(), timelineAction.getNote());
                }
            }
        } catch (Exception e) {
            logger.warn("Timeline action doesnt exists");
        }
        if (timelineAction == null) {
            final List<QuotingByTime> constraints = CommServer.getQuotingByTimeBean().getQuotingByTimeConstraints(person.getId(), constraintDate, quotingType.getValue());
            if (logger.isDebugEnabled()) {
                for (QuotingByTime qbt : constraints) {
                    logger.debug("QuotingByTime [Id={}, Person={}, DATE={}, START={}, END={}, TYPE={}]",
                            qbt.getId(), qbt.getDoctor().getLastName(),
                            new DateMidnight(qbt.getQuotingDate()), new DateTime(qbt.getQuotingTimeStart()),
                            new DateTime(qbt.getQuotingTimeEnd()), qbt.getQuotingType());
                }
            }
            return constraints;
        }
        return new ArrayList<QuotingByTime>(0);
    }

    /**
     * Получение талончика
     * @param action Действие связанное с приемом врача ("amb")
     * @param quota  Значение квоты
     * @return Новый талончик с заполненными полями
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    public static Amb getAmbInfo(final Action action, final short quota) throws CoreException {
        final List<APValueTime> times = new ArrayList<APValueTime>();
        final List<APValueAction> queue = new ArrayList<APValueAction>();
        final List<Ticket> tickets = new ArrayList<Ticket>();
        //fill Amb structure and lists
        final Amb result = getAmbulatoryProperties(action, times, queue);
        //Количество пациентов, записанных вне очереди
        final short emergencyPatientCount = CommunicationHelper.getEmergencyPatientCount(queue);
        //COMPUTE TICKETS to list and evaluate externalCount
        final short externalCount = computeTickets(action, times, queue, tickets, emergencyPatientCount);
        // http://miswiki.ru/   Получение талончиков _getTickets()
        //TODO абсолютное количество или проценты? должно зависеть от Person.quoteUnit
        final int available = Math.max(0, (int) (quota * tickets.size() * 0.01) - externalCount);

        if (quota != -1 && available < 1) {
            for (Ticket ticket : tickets) {
                ticket.setAvailable(0);
            }
        }
        return result.setAvailable(available).setTickets(tickets);
    }

    /**
     * Создание списка талончиков к врачу из двух списков (times и queue)
     *
     * @param action  Действие (прием врача)
     * @param times   Список временных интервалов
     * @param queue   Список бронированых заявок, индекс соответствует временному интервалу
     * @param tickets Список талончиков для заполнения
     * @return Количество внешних обращений (из других ЛПУ)
     */
    public static short computeTickets(final Action action, final List<APValueTime> times, final List<APValueAction> queue,
                                 final List<Ticket> tickets, final int emergencyCount) {
        short externalCount = 0;
        for (int i = 0; i < times.size(); i++) {
            final APValueTime currentTime = times.get(i);
            final Action queueAction = (queue.size() > emergencyCount + i) ? queue.get(emergencyCount + i).getValue() : null;
            short free;
            if (currentTime != null) {
                if (queueAction != null) {
                    free = 0;
                    if (queueAction.getAssigner() != null) {
                        externalCount++;
                    }
                } else {
                    free = 1;
                }
                final Ticket newTicket = new Ticket();
                newTicket.setTime(DateConvertions.convertDateToUTCMilliseconds(currentTime.getValue()));
                newTicket.setFree(free).setAvailable(free);
                if (free == 0) {
                    //талончик занят, выясняем кем
                    if (logger.isDebugEnabled()) {
                        logger.debug("Queue ACTIONID={}", queueAction.getId());
                    }
                    final Patient queuePatient = queue.get(emergencyCount + i).getValue().getEvent().getPatient();
                    if (queuePatient != null) {
                        newTicket.setPatientId(queuePatient.getId())
                                .setPatientInfo(new StringBuilder(queuePatient.getLastName())
                                        .append(" ").append(queuePatient.getFirstName())
                                        .append(" ").append(queuePatient.getPatrName()).toString());
                    } else {
                        newTicket.setPatientId(0)
                                .setPatientInfo("НЕИЗВЕСТНЫЙ ПОЛЬЗОВАТЕЛЬ (возможно удален из БД напрямую)");
                    }
                }
                tickets.add(newTicket);
            }
        }
        return externalCount;
    }

    /**
     * Получение и высталение свойств амбулаторного приема врача
     *
     * @param action Событие отвечающее за прием врача
     * @param times  Временные интревалы (Свойства приема врача)
     * @param queue  Элементы очереди к врачу на прием  (Свойства приема врача)[индексы совпадают с временными интервалами]
     * @throws CoreException Ошибка во время получение некоторых свойств
     */
    public static Amb getAmbulatoryProperties(final Action action, final List<APValueTime> times, final List<APValueAction> queue) throws CoreException {
        String fieldName;
        final Amb ambulatoryInfo = new Amb();
        for (ActionProperty currentProperty : action.getActionProperties()) {
            fieldName = currentProperty.getType().getName();
            //Fill AMB params without tickets and fill arrays to compute tickets
            final List<APValue> apValueList = CommServer.getActionPropertyBean().getActionPropertyValue(currentProperty);
            if (!apValueList.isEmpty()) {
                final APValue value = apValueList.get(0);
                if ("begTime".equals(fieldName)) {
                    ambulatoryInfo.setBegTime(DateConvertions.convertDateToUTCMilliseconds((Date) value.getValue()));
                } else if ("endTime".equals(fieldName)) {
                    ambulatoryInfo.setEndTime(DateConvertions.convertDateToUTCMilliseconds((Date) value.getValue()));
                } else if ("office".equals(fieldName)) {
                    ambulatoryInfo.setOffice(((APValueString) value).getValue());
                } else if ("plan".equals(fieldName)) {
                    ambulatoryInfo.setPlan(((APValueInteger) value).getValue());
                } else if ("times".equals(fieldName)) {
                    //Не преобразуем эти времена
                    for (APValue timevalue : apValueList) {
                       times.add((APValueTime) timevalue);
                    }
                } else if ("queue".equals(fieldName)) {
                    for (APValue queuevalue : apValueList) {
                        queue.add((APValueAction) queuevalue);
                    }
                }
                //Вывод всех свойств со значениями в лог
                if (logger.isDebugEnabled()) {
                    for (APValue apValue : apValueList) {
                        logger.debug("ID={} NAME={} VALUE={}",
                                currentProperty.getId(), currentProperty.getType().getName(), apValue.getValue());
                    }
                }
            }
        }
        //END OF ###Fill AMB params without tickets and fill arrays to compute tickets
        return ambulatoryInfo;
    }
}
