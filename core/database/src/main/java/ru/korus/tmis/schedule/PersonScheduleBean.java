package ru.korus.tmis.schedule;

import com.google.common.collect.ImmutableList;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        23.12.13, 12:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class PersonScheduleBean implements PersonScheduleBeanLocal {

    @EJB
    private DbActionPropertyBeanLocal actionPropertyBean;

    @EJB
    private PatientQueueBeanLocal patientQueueBean;

    @EJB
    private DbStaffBeanLocal staffBean;

    @EJB
    private DbQuotingByTimeBeanLocal quotingByTimeBean;

    @EJB
    private DbEventBeanLocal eventBean;

    @EJB
    private DbActionBeanLocal actionBean;

    @EJB
    private DbQuotingBySpecialityBeanLocal quotingBySpecialityBean;

    @PersistenceContext(unitName = "s11r64")
    EntityManager em;

    //Logger
    //TODO change it
    private static final Logger logger = LoggerFactory.getLogger("ru.korus.tmis.communication");

    private static final SimpleDateFormat loggerDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static class PersonSchedule {
        /**
         * Врач, к которму приписан этот прием пациентов
         */
        private Staff doctor;

        /**
         * Action приема врача
         */
        private Action ambulatoryAction;

        /**
         * Дата приема врача
         */
        private Date ambulatoryDate;

        /**
         * Ограничения по времени на прием врача для заданного типа квоты
         */
        private List<QuotingByTime> quotingByTimeConstraints;

        /**
         * ActionProperty - ячейки времени
         */
        private List<APValueTime> times;

        /**
         * ActionProperty - записи на прием
         */
        private List<APValueAction> queue;

        /**
         * ActionProperty для queue
         */
        private ActionProperty queueAP;

        /**
         * Сформированный список талончиков
         */
        private List<Ticket> tickets;

        /**
         * Офис в котором будет происходить прием пациентов
         */
        private String office;

        /**
         * Время начала приема врача
         */
        private Date begTime;

        /**
         * Время окончания приема врача
         */
        private Date endTime;

        /**
         * План (насколько я понял - это ожидаемое количетсво талончиков = times.size())
         */
        private Integer plan;

        /**
         * Количесвто пациентов из других ЛПУ
         */
        private short externalCount;

        /**
         * Признак доступность записи на прием к врачу в этот день
         */
        private boolean available;

        /**
         * количество записанных экстренно
         */
        private short emergencyPatientCount = 0;

        /**
         * Количество записанных вне очереди
         */
        private short overQueueCount = 0;

        /**
         * Getters and Setters
         */
        public Date getAmbulatoryDate() {
            return ambulatoryDate;
        }

        public List<APValueTime> getTimes() {
            return times;
        }

        public List<Ticket> getTickets() {
            return tickets;
        }

        public String getOffice() {
            return office;
        }

        public Date getBegTime() {
            return begTime;
        }

        public Date getEndTime() {
            return endTime;
        }

        public Integer getPlan() {
            return plan;
        }

        public boolean isAvailable() {
            return available;
        }

        public List<APValueAction> getQueue() {
            return queue;
        }

    }

    public PersonSchedule newInstanceOfPersonSchedule(final Action ambulatoryAction) {
        PersonSchedule res = new PersonSchedule();
        res.doctor = ambulatoryAction.getEvent().getExecutor();
        res.ambulatoryAction = ambulatoryAction;
        res.ambulatoryDate = ambulatoryAction.getEvent().getSetDate();
        if (logger.isDebugEnabled()) {
            logger.info("PersonSchedule[{}] Action[{}] Doctor: {} {} {}",
                    loggerDateFormat.format(res.ambulatoryDate),
                    ambulatoryAction.getId(),
                    res.doctor.getLastName(),
                    res.doctor.getFirstName(),
                    res.doctor.getPatrName()
            );
        }
        return res;
    }

    public PersonSchedule newInstanceOfPersonSchedule(final Date requestedDate, final Staff doctor) {
        try {
            final Action ambulatoryAction = staffBean.getPersonActionsByDateAndType(doctor.getId(), requestedDate, "amb");
            return newInstanceOfPersonSchedule(ambulatoryAction);
        } catch (CoreException e) {
            return null;
        }
    }

    /**
     * Проверка на наличие у врача "Причины отсутствия в указанную дату"
     *
     * @return RbReasonOfAbsence - У врача есть причина отсутствия
     */
    public RbReasonOfAbsence getReasonOfAbsence(final PersonSchedule personSchedule) {
        try {
            final Action timelineAction = staffBean.getPersonActionsByDateAndType(
                    personSchedule.doctor.getId(),
                    personSchedule.ambulatoryDate,
                    "timeline"
            );
            if (timelineAction == null) {
                logger.info("Timeline action doesn't exists");
                return null;
            }
            logger.info("Timeline exists and is Action[{}]", timelineAction.getId());
            final Map<ActionProperty, List<APValue>> actionPropertyListMap = actionPropertyBean
                    .getActionPropertiesByActionIdAndTypeNames(timelineAction.getId(), ImmutableList.of("reasonOfAbsence"));
            if (actionPropertyListMap.isEmpty()) {
                logger.info("Timeline hasn't ReasonOfAbsence");
                return null;
            } else {
                final Iterator<ActionProperty> propertyIterator = actionPropertyListMap.keySet().iterator();
                if (propertyIterator.hasNext()) {
                    final List<APValue> reasonAPList = actionPropertyListMap.get(propertyIterator.next());
                    if (reasonAPList.isEmpty()) {
                        logger.info("AP exists, but AP_rbReasonOfAbsence not!");
                        return null;
                    } else {
                        logger.info("ReasonOfAbsence is [{}] ", reasonAPList.get(0).toString());
                        return ((APValueRbReasonOfAbsence) reasonAPList.get(0)).getValue();
                    }
                }
                return null;
            }
        } catch (Exception e) {
            logger.info("Timeline action doesn't exists");
            return null;
        }
    }

    private void getQuotingByTimeConstraints(final PersonSchedule personSchedule, final TypeOfQuota quotingType) {
        personSchedule.quotingByTimeConstraints = quotingByTimeBean
                .getQuotingByTimeConstraints(personSchedule.doctor.getId(), personSchedule.ambulatoryDate, quotingType.getValue());
        if (logger.isDebugEnabled()) {
            for (QuotingByTime qbt : personSchedule.quotingByTimeConstraints) {
                logger.info("QuotingByTime[{}]: START={}, END={}]",
                        qbt.getId(), qbt.getQuotingTimeStart(), qbt.getQuotingTimeEnd()
                );
            }
        }
    }

    /**
     * Получение и высталение свойств амбулаторного приема врача
     *
     * @throws ru.korus.tmis.core.exception.CoreException
     *          Ошибка во время получение некоторых свойств
     */
    private void getAmbulatoryProperties(final PersonSchedule personSchedule) throws CoreException {
        //Check required fields
        for (ActionProperty currentProperty : personSchedule.ambulatoryAction.getActionProperties()) {
            final String fieldName = currentProperty.getType().getName();
            //Fill AMB params without tickets and fill arrays to compute tickets
            final List<APValue> apValueList = actionPropertyBean.getActionPropertyValue(currentProperty);
            if (!apValueList.isEmpty()) {
                if ("begTime".equals(fieldName)) {
                    personSchedule.begTime = (Date) apValueList.get(0).getValue();
                } else if ("endTime".equals(fieldName)) {
                    personSchedule.endTime = (Date) apValueList.get(0).getValue();
                } else if ("office".equals(fieldName)) {
                    personSchedule.office = ((APValueString) apValueList.get(0)).getValue();
                } else if ("plan".equals(fieldName)) {
                    personSchedule.plan = ((APValueInteger) apValueList.get(0)).getValue();
                } else if ("times".equals(fieldName)) {
                    //Не преобразуем эти времена
                    for (APValue timevalue : apValueList) {
                        personSchedule.times.add((APValueTime) timevalue);
                    }
                } else if ("queue".equals(fieldName)) {
                    personSchedule.queueAP = currentProperty;
                    for (APValue queuevalue : apValueList) {
                        personSchedule.queue.add((APValueAction) queuevalue);
                    }
                    //Получаем количество экстренных и записанных вне очереди пациентов
                    emergencyPatientCount(personSchedule);
                }
                //Вывод всех свойств со значениями в лог
                if (logger.isDebugEnabled()) {
                    for (APValue apValue : apValueList) {
                        logger.info("ID={} NAME={} VALUE={}",
                                currentProperty.getId(), currentProperty.getType().getName(), apValue.getValue());
                    }
                }
            }
        }
        if (personSchedule.times.isEmpty()) {
            logger.error("No one times in AP. Throws NotFoundException.");
            throw new CoreException();
        }
        //Show warnings
        if (logger.isWarnEnabled()) {
            if (personSchedule.plan == null) {
                logger.warn("Schedule plan is not set in AP.");
            }
            if (personSchedule.begTime == null) {
                logger.warn("Schedule begTime is not set in AP.");
            }
            if (personSchedule.endTime == null) {
                logger.warn("Schedule endTime is not set in AP.");
            }
        }
    }


    /**
     * Подсчет количества пациентов, записанных вне очереди и экстренно
     */
    private void emergencyPatientCount(final PersonSchedule personSchedule) {
        for (APValueAction checkAction : personSchedule.queue) {
            if (checkAction != null) {
                Action chechActionValue = checkAction.getValue();
                if (chechActionValue != null) {
                    Short pacientInQueueType = chechActionValue.getPacientInQueueType();
                    if (pacientInQueueType != null) {
                        if (pacientInQueueType == (short) 1) {
                            personSchedule.emergencyPatientCount++;
                        } else {
                            if (pacientInQueueType == (short) 2) {
                                personSchedule.overQueueCount++;
                            }
                        }
                    }
                }
            }
        }
        logger.info("Founded {} emergency and {} out of turn actions", personSchedule.emergencyPatientCount, personSchedule.overQueueCount);
    }

    /**
     * Создание списка талончиков к врачу
     * Выборка свойств расписания из свойств действия
     * и формирование талончиков из двух списков (times и queue)
     */
    public void formTickets(final PersonSchedule personSchedule) throws CoreException {
        personSchedule.times = new ArrayList<APValueTime>();
        personSchedule.queue = new ArrayList<APValueAction>();
        getAmbulatoryProperties(personSchedule);

        personSchedule.tickets = new ArrayList<Ticket>(personSchedule.times.size());

        short queueIndex = personSchedule.emergencyPatientCount;
        Date currentTime;
        Date nextTime = personSchedule.times.get(0).getValue();

        for (short timeIndex = 0; timeIndex < personSchedule.times.size(); timeIndex++) {
            //Текущая ячейка времени
            currentTime = nextTime;
            //окончание текущей ячейки вермени
            if (personSchedule.times.size() > (timeIndex + 1)) {
                nextTime = personSchedule.times.get(timeIndex + 1).getValue();
            } else {
                nextTime = personSchedule.endTime;
            }
            final Action queueAction = (personSchedule.queue.size() > queueIndex) ?
                    personSchedule.queue.get(queueIndex).getValue() : null;
            final Ticket currentTicket = new Ticket();
            currentTicket.setBegTime(currentTime);
            currentTicket.setEndTime(nextTime);
            currentTicket.setTimeCellIndex(timeIndex);
            currentTicket.setQueueCellIndex(queueIndex);
            if (queueAction != null) {
                currentTicket.setFree(false);
                currentTicket.setPatient(queueAction.getEvent().getPatient());
                if (AppointmentType.PORTAL.equals(queueAction.getAppointmentType())
                        || AppointmentType.OTHER_LPU.equals(queueAction.getAppointmentType())) {
                    personSchedule.externalCount++;
                }
            } else {
                currentTicket.setFree(true);
            }
            currentTicket.setAvailable(currentTicket.isFree());
            personSchedule.tickets.add(currentTicket);
            queueIndex++;
        }
        if (logger.isDebugEnabled()) {
            for (Ticket currentTicket : personSchedule.tickets) {
                logger.info(currentTicket.getInfo());
            }
        }
    }


    /**
     * Выбирает и применяет ограничения по времени
     * (QuotingByTime врача)
     * на возвращаемый набор талончиков
     * (result.tickets)
     */
    public void takeConstraintsOnTickets(final PersonSchedule personSchedule, final TypeOfQuota quotingType) {
        //Пропустить ПРОВЕРКУ если НЕТ талончиков
        if (personSchedule.tickets.isEmpty()) {
            return;
        }
        getQuotingByTimeConstraints(personSchedule, quotingType);
        final short quota;
        final short quotedTickets;
        int quoteAvailable = 0;
        if (personSchedule.quotingByTimeConstraints.isEmpty()) {
            switch (quotingType) {
                /**
                 case FROM_REGISTRY:{
                 quota = doctor.getPrimaryQuota();
                 break;
                 }
                 case SECOND_VISIT:{
                 quota = doctor.getOwnQuota();
                 break;
                 }
                 case BETWEEN_CABINET:{
                 quota = doctor.getConsultancyQuota();
                 break;
                 }    **/
                case FROM_OTHER_LPU: {
                    quota = personSchedule.doctor.getExternalQuota();
                    quotedTickets = personSchedule.externalCount;
                    break;
                }
                case FROM_PORTAL: {
                    quota = personSchedule.doctor.getExternalQuota();
                    quotedTickets = personSchedule.externalCount;
                    break;
                }
                default: {
                    quota = 100;
                    quotedTickets = 0;
                    break;
                }
            }
            logger.info("Quota={} quotedTickets={} unit={}", quota, quotedTickets, personSchedule.doctor.getQuoteUnit());
            if (personSchedule.doctor.getQuoteUnit() != null) {
                if (personSchedule.doctor.getQuoteUnit() == 0) {
                    //%
                    quoteAvailable = quota - quotedTickets * 100 / personSchedule.tickets.size();
                } else {
                    //Абсолютное количество
                    quoteAvailable = quota - quotedTickets;
                }
                if (quoteAvailable < 1) {
                    for (Ticket currentTicket : personSchedule.tickets) {
                        currentTicket.setAvailable(false);
                    }
                }
            } else {
                logger.warn("Doctor quoteUnit is NULL, skip doctor quoting.");
            }
        } else {
            quota = -1;
            quoteAvailable = Math.max(0, (int) (quota * personSchedule.tickets.size() * 0.01) - personSchedule.externalCount);
            for (Ticket currentTicket : personSchedule.tickets) {
                boolean available = false;
                for (QuotingByTime qbt : personSchedule.quotingByTimeConstraints) {
                    //Проверяется не весь интервал, а только начало талончика
                    //Потому что "так было в 6098" бебебе
                    if (qbt.getQuotingTimeStart().getTime() != 0 && qbt.getQuotingTimeEnd().getTime() != 0) {
                        if (currentTicket.getBegTime().getTime() >= qbt.getQuotingTimeStart().getTime()
                                && currentTicket.getBegTime().getTime() <= qbt.getQuotingTimeEnd().getTime()
                                && currentTicket.isAvailable()) {
                            available = true;
                            break;
                        }
                    }
                }
                currentTicket.setAvailable(available);
            }
        }
        personSchedule.available = (quoteAvailable != 0);
        logger.info("Quota={} quoteAvailable={} externalCount={}", quota, quoteAvailable, personSchedule.externalCount);
        if (logger.isDebugEnabled()) {
            logger.info("After constraints:");
            for (Ticket currentTicket : personSchedule.tickets) {
                logger.info(currentTicket.getInfo());
            }
        }
    }

    /**
     * Возвращает первый свободный и доступный талончик врача поле заданного вермени
     *
     * @param checkDateTime время после которого ищется талончик
     * @return null если не найдено
     */
    public Ticket getFirstFreeTicketAfterDateTime(final PersonSchedule personSchedule, final long checkDateTime) {
        final long currentAmbulatoryDaty = DateConvertions.convertDateToUTCMilliseconds(personSchedule.ambulatoryDate);
        for (Ticket currentTicket : personSchedule.tickets) {
            if (currentTicket.isAvailable()
                    && currentTicket.isFree()
                    && currentTicket.getBegTime().after(
                    DateConvertions.convertUTCMillisecondsToLocalDate(checkDateTime - currentAmbulatoryDaty))
                    ) {
                return currentTicket;
            }
        }
        return null;
    }

    //@TransactionAttribute(TransactionAttributeType.NEVER)
    public EnqueuePatientResult enqueuePatientToTime(final PersonSchedule personSchedule,
                                                     final Date paramsDateTime,
                                                     final Patient patient,
                                                     final QueueActionParam queueActionParam
    ) {
        //Отсечение даты от запрошеного даты-времени записи
        final Date paramsTime = DateConvertions.convertUTCMillisecondsToLocalDate(new LocalTime(paramsDateTime).getMillisOfDay());
        final Date paramsDate = new LocalDate(paramsDateTime).toDate();
        logger.info("paramsDate is {}", paramsDate);
        logger.info("paramsTime is {}, millis={}", paramsTime, paramsTime.getTime());
        final boolean isUrgent = queueActionParam.getPacientInQueueType().equals(PacientInQueueType.URGENT);
        final boolean isOverQueue = queueActionParam.getPacientInQueueType().equals(PacientInQueueType.OVERQUEUE);

        if (isUrgent && checkMaxCito(personSchedule)) { //если срочная запись и достигнут лимит срочный приемов у врача
            logger.info("No more urgent ticket. Current numder of urgent ticket is {}.", personSchedule.emergencyPatientCount);
            return new EnqueuePatientResult().setSuccess(false)
                    .setMessage(CommunicationErrors.msgMaxCito.getMessage());
        }
        if (isUrgent && checkMaxOverQueue(personSchedule)) {  //если запись сверх очереди и достигнут лимит записи сверх очереди
            logger.info("No more 'over queue' ticket. Current numder of over queue ticket is {}.", personSchedule.overQueueCount);
            return new EnqueuePatientResult().setSuccess(false)
                    .setMessage(CommunicationErrors.msgMaxCito.getMessage());
        }
        for (Ticket currentTicket : personSchedule.tickets) {
            if (currentTicket.getBegTime().equals(paramsTime) ||
                    isUrgent || isOverQueue) { // если время записи совпадет с временем талочника или срочначя запись или запись сверх очереди
                logger.info("HIT! to {}", currentTicket.getInfo());
                if (currentTicket.isAvailable() && currentTicket.isFree()) {
                    //Нельзя записывать пациента, если на этот же день к этому же врачу он уже записывался
                    if (checkRepetitionToDoctor(personSchedule, patient)) {
                        logger.info("Detected repetition enqueue to this doctor. Cancelling enqueue.");
                        return new EnqueuePatientResult().setSuccess(false)
                                .setMessage(CommunicationErrors.msgPatientAlreadyQueued.getMessage());
                    }
                    if (patientQueueBean.checkPatientQueueByDateTime(
                            patient,
                            paramsDate,
                            currentTicket.getBegTime(),
                            currentTicket.getEndTime())
                            ) {
                        logger.info("Detected repetition enqueue to another doctor by same time. Cancelling enqueue.");
                        return new EnqueuePatientResult().setSuccess(false)
                                .setMessage(CommunicationErrors.msgPatientAlreadyQueuedToTime.getMessage());
                    }
                    //Если ячейка времени свободна, то создаём записи в таблицах Event, Action, ActionProperty_Action:
                    try {
                        //0 проверяем квоты!
                        if (queueActionParam.getHospitalUidFrom() != null && !queueActionParam.getHospitalUidFrom().isEmpty()) {
                            if (!checkAndDecrementQuotingBySpeciality(personSchedule.doctor.getSpeciality(), queueActionParam.getHospitalUidFrom())) {
                                logger.info("No coupons available for recording (by quotes on speciality)");
                                return new EnqueuePatientResult().setSuccess(false)
                                        .setMessage(CommunicationErrors.msgNoTicketsAvailable.getMessage());
                            }
                        }
                        //1) Создаем событие  (Event)
                        //1.a)Получаем тип события (EventType)
                        final EventType queueEventType = patientQueueBean.getQueueEventType();
                        //1.b)Сохраняем событие  (Event)
                        final Event queueEvent = eventBean.createEvent(
                                patient, queueEventType, personSchedule.doctor,
                                paramsDateTime, currentTicket.getEndTime()
                        );
                        logger.info("Event is {} ID={} UUID={}",
                                queueEvent, queueEvent.getId(), queueEvent.getUuid().getUuid());
                        //2) Создаем действие (Action)
                        //2.a)Получаем тип    (ActionType)
                        final ActionType queueActionType = patientQueueBean.getQueueActionType();
                        logger.info("ActionType is {} typeID={} typeName={}",
                                queueActionType, queueActionType.getId(), queueActionType.getName());
                        //2.b)Сохраняем действие  (Action)

                        final Action queueAction = actionBean.createAction(
                                queueActionType, queueEvent, personSchedule.doctor,
                                paramsDateTime, queueActionParam);

                        logger.info("Action is {} ID={} UUID={}",
                                queueAction, queueAction.getId(), queueAction.getUuid().getUuid());
                        // Заполняем ActionProperty_Action для 'queue' из Action='amb'
                        // Для каждого времени(times) из Action[приема врача]
                        // заполняем очередь(queue) null'ами если она не ссылается на другой Action,
                        // и добавлем наш запрос в эту очередь
                        // с нужным значением index, по которому будет происходить соответствие с ячейкой времени.
                        addActionToQueuePropertyValue(personSchedule, currentTicket.getQueueCellIndex(), queueAction);
                        logger.info("NEW QUEUE ACTION IS {}", queueAction.toString());
                        return new EnqueuePatientResult().setSuccess(true).setIndex(currentTicket.getQueueCellIndex())
                                .setMessage(CommunicationErrors.msgOk.getMessage()).setQueueId(queueAction.getId());
                    } catch (CoreException e) {
                        logger.error("CoreException while create new EVENT", e);
                        return new EnqueuePatientResult().setSuccess(false)
                                .setMessage(CommunicationErrors.msgUnknownError.getMessage());
                    }
                } else {
                    //Выбранный талончик занят
                    logger.error("This queue cell is NOT FREE OR NOT AVAILABLE");
                    break;
                }
            }
        }
        //У врача нету талончиков на запрошенную дату.
        logger.warn("Doctor has no tickets to this date:[{}]", paramsDateTime);
        return new EnqueuePatientResult().setSuccess(false)
                .setMessage(CommunicationErrors.msgTicketNotFound.getMessage());
    }

    /**
     * Отмена записи на прием к врачу
     *
     * @param queueAction запись, которую необходимо отменить
     */
    @Override
    public boolean dequeuePatient(final Action queueAction) {
        try {
            //Получение ActionProperty_Action соответствующего записи пациента к врачу (queue)
            APValueAction ambActionPropertyAction = actionPropertyBean.getActionProperty_ActionByValue(queueAction);
            logger.debug("Founded AP_A: {}", ambActionPropertyAction);
            switch (queueAction.getPacientInQueueType()) {
                //Обыкновенная запись на прием к врачу
                case 0: {
                    //Обнуление поля = отмена очереди
                    logger.debug("Action.pacientInQueueType = 0");
                    ambActionPropertyAction.setValue(null);
                    em.merge(ambActionPropertyAction);
                    logger.debug("AP_A.value set to NULL");
                    break;
                }
                //запись сверх очереди / экстренно
                case 1:
                case 2: {
                    //Удалить запись
                    final int removedIndex = ambActionPropertyAction.getId().getIndex();
                    final ActionProperty actionProperty = actionPropertyBean.getActionPropertyById(ambActionPropertyAction.getId().getId());
                    em.remove(ambActionPropertyAction);
                    final List<APValue> valueList = actionPropertyBean.getActionPropertyValue(actionProperty);
                    //сдвинуть все индексы последующих элементов
                    //в запросе использовалось ORDER BY index ASC (на 13.01.2014), поэтому список упоряочен по возрастанию индексов
                    for (APValue currentApValue : valueList) {
                        if (currentApValue instanceof APValueAction) {
                            APValueAction currentTypedApValue = (APValueAction) currentApValue;
                            if(currentTypedApValue.getId().getIndex() > removedIndex) {
                                //сдвигаем на одну позицию вверх
                                currentTypedApValue.getId().setIndex(currentTypedApValue.getId().getIndex()-1);
                                // меняем в БД
                                em.merge(currentTypedApValue);
                            }
                        }
                    }
                    break;
                }
            }
            // Связанное с записью на прием обращение пациента
            final Event queueEvent = queueAction.getEvent();
            //Выставляем флаг удаления у соответствующего действия пользователя
            logger.debug("Deleting Action[{}]", queueAction.getId());
            queueAction.setDeleted(true);
            queueAction.setModifyDatetime(new Date());
            em.merge(queueAction);
            em.flush();
            //Выставляем флаг удаления у соответствующего события пользователя
            logger.debug("Action deleted.");
            logger.debug("Deleting Event[{}]", queueEvent.getId());
            queueEvent.setDeleted(true);
            queueEvent.setModifyDatetime(new Date());
            em.merge(queueEvent);
            em.flush();
            logger.debug("Event deleted");
            return true;
        } catch (CoreException e) {
            logger.error("Error while getting ActionProperty_Action for Action[{}]", queueAction.getId());
            return false;
        }
    }

    private boolean checkMaxOverQueue(PersonSchedule personSchedule) {
        return personSchedule.doctor.getMaxOverQueue() <= personSchedule.overQueueCount;
    }

    private boolean checkMaxCito(PersonSchedule personSchedule) {
        return personSchedule.doctor.getMaxCito() <= personSchedule.emergencyPatientCount;
    }


    /**
     * Внесение действия( состояние в очереди пациента ) в БД
     *
     * @param index       Номер временного отрезка на которое происходит запись
     * @param queueAction Действие пациента, отвечающее за его состояние в очереди
     * @throws CoreException Ошибка сохранения действия в БД
     */
    private void addActionToQueuePropertyValue(final PersonSchedule personSchedule, final int index, final Action queueAction) throws CoreException {

        final PacientInQueueType pacientInQueueType = PacientInQueueType.newInstance(queueAction.getPacientInQueueType());
        if (personSchedule.queueAP == null) {
            logger.warn("Our enqueue is first to this doctor. Because queueActionProperty for doctorAction is null" +
                    " queueAMB.size()={}", personSchedule.queue.size());
            final ActionPropertyType queueAPType = patientQueueBean.getQueueActionPropertyType();
            if (queueAPType != null) {
                personSchedule.queueAP = actionPropertyBean.createActionProperty(personSchedule.ambulatoryAction, queueAPType);
            } else {
                return;
            }
        }
        logger.info("Queue ActionProperty = {}", personSchedule.queueAP.getId());
        for (int j = personSchedule.queue.size(); j < personSchedule.getPlan(); j++) {
            APValueAction newActionPropertyAction = new APValueAction(personSchedule.queueAP.getId(), j);
            if (pacientInQueueType.equals(PacientInQueueType.QUEUE) && (index == j)) {
                newActionPropertyAction.setValue(queueAction);
            } else {
                newActionPropertyAction.setValue(null);
            }
            //managerBean.persist(newActionPropertyAction);
            em.persist(newActionPropertyAction);
            personSchedule.queue.add(newActionPropertyAction);
        }

        if (pacientInQueueType.equals(PacientInQueueType.URGENT)) { //если запись срочная, то добавляем в начало и сдвигаем очередь на один
            ++personSchedule.emergencyPatientCount;
            APValueAction newActionPropertyAction = new APValueAction(personSchedule.queueAP.getId(), personSchedule.queue.size());
            em.persist(newActionPropertyAction);
            personSchedule.queue.add(newActionPropertyAction);
            for (int i = 1; i < personSchedule.queue.size(); ++i) {
                personSchedule.queue.get(i).setValue(personSchedule.queue.get(i - 1).getValue());
                em.merge(personSchedule.queue.get(i));
            }
            personSchedule.queue.get(0).setValue(queueAction);
            em.merge(personSchedule.queue.get(0));
        } else { //если запись в очередь на время или сверх очереди
            em.flush();
            APValueAction newActionPropertyAction = null;
            if (pacientInQueueType.equals(PacientInQueueType.OVERQUEUE)) { //если запись сверх очереди, то добавляем в конец
                ++personSchedule.overQueueCount;
                newActionPropertyAction = new APValueAction(personSchedule.queueAP.getId(), personSchedule.queue.size());
                personSchedule.queue.add(newActionPropertyAction);
                em.persist(newActionPropertyAction);
            } else { //если запись в очередь на время
                newActionPropertyAction = personSchedule.queue.get(index);
            }
            newActionPropertyAction.setValue(queueAction);
            logger.info("NewActionProperty [{} {} {}]",
                    newActionPropertyAction.getId().getId(),
                    newActionPropertyAction.getId().getIndex(),
                    newActionPropertyAction.getValue().getId());
            //managerBean.merge(newActionPropertyAction);
            em.merge(newActionPropertyAction);
            logger.info("All ActionProperty_Action's set successfully with index = {}", newActionPropertyAction.getId().getIndex());
        }
    }


    /**
     * Проверка не является записан ли указанный пациент в указанном приеме
     *
     * @param patient пациент, для которого выполняется проверка
     * @return true - пациент записан в запрошенный прием, false - еще не записан
     */

    private boolean checkRepetitionToDoctor(final PersonSchedule personSchedule, final Patient patient) {
        for (Ticket currentTicket : personSchedule.tickets) {
            if (patient.equals(currentTicket.getPatient())) {
                logger.info("Repetition enqueue.");
                return true;
            }
        }
        return false;
    }

    private boolean checkAndDecrementQuotingBySpeciality(
            final ru.korus.tmis.core.entity.model.Speciality speciality, final String organisationInfisCode) {
        List<QuotingBySpeciality> quotingBySpecialityList =
                quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(speciality, organisationInfisCode);
        if (quotingBySpecialityList.size() == 1) {
            logger.info("QuotingBySpeciality found and it is {}", quotingBySpecialityList);
            QuotingBySpeciality current = quotingBySpecialityList.get(0);
            if (current.getCouponsRemaining() > 0) {
                current.setCouponsRemaining(current.getCouponsRemaining() - 1);
                logger.info("QuotingBySpeciality coupons_remaining reduce by 1");
                em.merge(current);
                return  true;
            }
        }
        return false;
    }

    public Ticket getTicketByQueueIndex(final PersonSchedule personSchedule, final int queueIndex) {
        for (Ticket currentTicket : personSchedule.tickets) {
            if (currentTicket.getQueueCellIndex() == queueIndex) {
                return currentTicket;
            }
        }
        return null;
    }

    /**
     * Поиск и применение квот по специальности для заданного ЛПУ
     *
     * @param hospitalUidFrom инфис-код ЛПУ для которого требуется проверить квоты.
     */
    public boolean checkQuotingBySpeciality(final PersonSchedule personSchedule, final String hospitalUidFrom) {
        List<QuotingBySpeciality> quotingBySpecialityList =
                quotingBySpecialityBean.getQuotingBySpecialityAndOrganisation(personSchedule.doctor.getSpeciality(), hospitalUidFrom);
        //Квота должна быть единственной
        if (quotingBySpecialityList.size() == 1) {
            logger.info("QuotingBySpeciality[{}] founded.", quotingBySpecialityList.get(0).getId());
            if (quotingBySpecialityList.get(0).getCouponsRemaining() > 0) {
                //Еще есть свободные квоты
                return true;
            }
            //Квот нету, запрещаем доступ ко всем талончикам
        }
        //Квота не найдена или исчерпана -> запрет на доступность талончиков
        logger.info("All tickets are unavailable by QuotingBySpeciality");
        for (Ticket currentTicket : personSchedule.tickets) {
            currentTicket.setAvailable(false);
        }
        return false;
    }
}
