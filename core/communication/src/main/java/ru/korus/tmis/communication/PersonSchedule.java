package ru.korus.tmis.communication;

import com.google.common.collect.ImmutableList;
import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.EnqueuePatientStatus;
import ru.korus.tmis.communication.thriftgen.QuotingType;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 14.11.13, 15:59 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class PersonSchedule {
    //Logger
    private static final Logger logger = LoggerFactory.getLogger(PersonSchedule.class);

    /**
     * Врач, к которму приписан этот прием пациентов
     */
    private final Staff doctor;

    /**
     * Action приема врача
     */
    private final Action ambulatoryAction;

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
    private short outOfTurnCount = 0;


    public PersonSchedule(final Staff doctor, final Action ambulatoryAction) {
        this.doctor = doctor;
        this.ambulatoryAction = ambulatoryAction;
        this.ambulatoryDate = ambulatoryAction.getEvent().getSetDate();
        if (logger.isDebugEnabled()) {
            logger.debug("PersonSchedule[{}] Action[{}] Doctor: {} {} {}",
                    new SimpleDateFormat("yyyy-MM-dd").format(ambulatoryDate),
                    ambulatoryAction.getId(),
                    doctor.getLastName(),
                    doctor.getFirstName(),
                    doctor.getPatrName()
            );
        }
        this.times = new ArrayList<APValueTime>();
        this.queue = new ArrayList<APValueAction>();
        this.tickets = new ArrayList<Ticket>();
    }

    /**
     * Проверка на наличие у врача "Причины отсутствия в указанную дату"
     *
     * @return true - У врача есть причина отсутствия
     */
    public boolean checkReasonOfAbscence() {
        Action timelineAction = null;
        try {
            timelineAction = CommServer.getStaffBean().getPersonActionsByDateAndType(
                    doctor.getId(),
                    ambulatoryDate,
                    "timeline"
            );
            logger.debug("Timeline exists and is Action[{}]", timelineAction.getId());
            final Map<ActionProperty, List<APValue>> actionPropertyListMap = CommServer.getActionPropertyBean()
                    .getActionPropertiesByActionIdAndTypeNames(timelineAction.getId(), ImmutableList.of("reasonOfAbsence"));
            if (actionPropertyListMap.isEmpty()) {
                logger.debug("Timeline hasn't ReasonOfAbsence");
                return false;
            } else {
                final Iterator<ActionProperty> propertyIterator = actionPropertyListMap.keySet().iterator();
                if(propertyIterator.hasNext()) {
                    final List<APValue> reasonAPList = actionPropertyListMap.get(propertyIterator.next());
                    if(reasonAPList.isEmpty()) {
                        logger.debug("AP exists, but AP_rbReasonOfAbsence not!");
                        return false;
                    } else {
                        logger.debug("ReasonOfAbsence is [{}] ", reasonAPList.get(0).toString());
                        return true;
                    }
                }
                return false;
            }
        } catch (Exception e) {
            if (timelineAction == null) {
                logger.debug("Timeline action doesn't exists");
            }
            return false;
        }
    }

    private void getQuotingByTimeConstraints(final QuotingType quotingType) {
        quotingByTimeConstraints = CommServer.getQuotingByTimeBean()
                .getQuotingByTimeConstraints(doctor.getId(), ambulatoryDate, quotingType.getValue());
        if (logger.isDebugEnabled()) {
            for (QuotingByTime qbt : quotingByTimeConstraints) {
                logger.debug("QuotingByTime [Id={}, START={}, END={}]",
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
    private void getAmbulatoryProperties() throws CoreException {
        for (ActionProperty currentProperty : ambulatoryAction.getActionProperties()) {
            final String fieldName = currentProperty.getType().getName();
            //Fill AMB params without tickets and fill arrays to compute tickets
            final List<APValue> apValueList = CommServer.getActionPropertyBean().getActionPropertyValue(currentProperty);
            if (!apValueList.isEmpty()) {
                final APValue value = apValueList.get(0);
                if ("begTime".equals(fieldName)) {
                    begTime = (Date) value.getValue();
                } else if ("endTime".equals(fieldName)) {
                    endTime = (Date) value.getValue();
                } else if ("office".equals(fieldName)) {
                    office = ((APValueString) value).getValue();
                } else if ("plan".equals(fieldName)) {
                    plan = ((APValueInteger) value).getValue();
                } else if ("times".equals(fieldName)) {
                    //Не преобразуем эти времена
                    for (APValue timevalue : apValueList) {
                        times.add((APValueTime) timevalue);
                    }
                } else if ("queue".equals(fieldName)) {
                    queueAP = currentProperty;
                    for (APValue queuevalue : apValueList) {
                        queue.add((APValueAction) queuevalue);
                    }
                    //Получаем количество экстренных и записанных вне очереди пациентов
                    getEmergencyPatientCount();
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
        //Check required fields
        if(times.isEmpty()){
            logger.error("No one times in AP. Throws NotFoundException...");
            throw new CoreException();
        }
        //Show warnings
        if(plan == null){
            logger.warn("Schedule plan is not set in AP...");
        }
        if(begTime == null){
            logger.warn("Schedule begTime is not set in AP...");
        }
        if(endTime == null){
            logger.warn("Schedule endTime is not set in AP...");
        }
    }


    /**
     * Подсчет количества пациентов, записанных вне очереди и экстренно
     */
    private void getEmergencyPatientCount() {
        for (APValueAction checkAction : queue) {
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
    }

    /**
     * Создание списка талончиков к врачу
     * Выборка свойств расписания из свойств действия
     * и формирование талончиков из двух списков (times и queue)
     */
    public void formTickets() throws CoreException {
        getAmbulatoryProperties();
        tickets = new ArrayList<Ticket>(times.size());
        short queueIndex = emergencyPatientCount;
        for (short timeIndex = 0; timeIndex < times.size(); timeIndex++) {
            //Текущая ячейка времени
            final Date currentTime = times.get(timeIndex).getValue();
            //окончание текущей ячейки вермени
            final Date nextTime;
            if (times.size() > (timeIndex + 1)) {
                nextTime = times.get(timeIndex + 1).getValue();
            } else {
                nextTime = endTime;
            }
            final Action queueAction = (queue.size() > queueIndex) ?
                    queue.get(queueIndex).getValue() : null;
            final Ticket currentTicket = new Ticket();
            currentTicket.setBegTime(currentTime);
            currentTicket.setEndTime(nextTime);
            currentTicket.setTimeCellIndex(timeIndex);
            currentTicket.setQueueCellIndex(queueIndex);
            if (queueAction != null) {
                currentTicket.setFree(false);
                currentTicket.setPatient(queueAction.getEvent().getPatient());
                if (queueAction.getAssigner() != null) {
                    externalCount++;
                }
            } else {
                currentTicket.setFree(true);
            }
            currentTicket.setAvailable(currentTicket.isFree());
            tickets.add(currentTicket);
            queueIndex++;
        }
        if (logger.isDebugEnabled()) {
            for (Ticket currentTicket : tickets) {
                logger.debug(currentTicket.getInfo());
            }
        }
    }


    /**
     * Выбирает и применяет ограничения по времени
     * (QuotingByTime врача)
     * на возвращаемый набор талончиков
     * (result.tickets)
     */
    public void takeConstraintsOnTickets(final QuotingType quotingType) {
        //Пропустить ПРОВЕРКУ если НЕТУ талончиков
        if(tickets.isEmpty()){
            return;
        }
        getQuotingByTimeConstraints(quotingType);
        final short quota;
        final short quotedTickets;
        int quoteAvailable = 0;
        if (quotingByTimeConstraints.isEmpty()) {
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
                    quota = doctor.getExternalQuota();
                    quotedTickets = externalCount;
                    break;
                }
                case FROM_PORTAL: {
                    quota = doctor.getExternalQuota();
                    quotedTickets = externalCount;
                    break;
                }
                default: {
                    quota = 100;
                    quotedTickets = 0;
                    break;
                }
            }
            logger.debug("Quota={} quotedTickets={} unit={}", quota, quotedTickets, doctor.getQuoteUnit());
            if (doctor.getQuoteUnit() != null) {
                if (doctor.getQuoteUnit() == 0) {
                    //%
                    quoteAvailable = quota - quotedTickets * 100 / tickets.size();
                } else {
                    //Абсолютное количество
                    quoteAvailable = quota - quotedTickets;
                }
                if (quoteAvailable < 1) {
                    for (Ticket currentTicket : tickets) {
                        currentTicket.setAvailable(false);
                    }
                }
            } else {
                logger.warn("Doctor quoteUnit is NULL, skip doctor quoting.");
            }
        } else {
            quota = -1;
            quoteAvailable = Math.max(0, (int) (quota * tickets.size() * 0.01) - externalCount);
            for (Ticket currentTicket : tickets) {
                boolean available = false;
                for (QuotingByTime qbt : quotingByTimeConstraints) {
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
        this.available = (quoteAvailable != 0);
        logger.debug("Quota={} quoteAvailable={} externalCount={}", quota, quoteAvailable, externalCount);
        if (logger.isDebugEnabled()) {
            logger.debug("After constraints:");
            for (Ticket currentTicket : tickets) {
                logger.debug(currentTicket.getInfo());
            }
        }
    }

    /**
     * Возвращает первый свободный и доступный талончик врача поле заданного вермени
     *
     * @param checkDateTime время после которого ищется талончик
     * @return null если не найдено
     */
    public Ticket getFirstFreeTicketAfterDateTime(final long checkDateTime) {
        final long currentAmbulatoryDaty = DateConvertions.convertDateToUTCMilliseconds(ambulatoryDate);
        for (Ticket currentTicket : tickets) {
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

    /**
     * Getters and Setters
     */
    public Staff getDoctor() {
        return doctor;
    }

    public Action getAmbulatoryAction() {
        return ambulatoryAction;
    }

    public Date getAmbulatoryDate() {
        return ambulatoryDate;
    }

    public void setAmbulatoryDate(Date ambulatoryDate) {
        this.ambulatoryDate = ambulatoryDate;
    }

    public List<APValueTime> getTimes() {
        return times;
    }

    public void setTimes(List<APValueTime> times) {
        this.times = times;
    }

    public List<APValueAction> getQueue() {
        return queue;
    }

    public void setQueue(List<APValueAction> queue) {
        this.queue = queue;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }

    public Date getBegTime() {
        return begTime;
    }

    public void setBegTime(Date begTime) {
        this.begTime = begTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getPlan() {
        return plan;
    }

    public void setPlan(Integer plan) {
        this.plan = plan;
    }

    public void setEmergencyPatientCount(short emergencyPatientCount) {
        this.emergencyPatientCount = emergencyPatientCount;
    }

    public short getOutOfTurnCount() {
        return outOfTurnCount;
    }

    public void setOutOfTurnCount(short outOfTurnCount) {
        this.outOfTurnCount = outOfTurnCount;
    }

    public List<QuotingByTime> getQuotingByTimeConstraints() {
        return quotingByTimeConstraints;
    }

    public void setQuotingByTimeConstraints(List<QuotingByTime> quotingByTimeConstraints) {
        this.quotingByTimeConstraints = quotingByTimeConstraints;
    }

    public short getExternalCount() {
        return externalCount;
    }

    public void setExternalCount(short externalCount) {
        this.externalCount = externalCount;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public EnqueuePatientStatus enqueuePatientToTime(
            final Date paramsDateTime,
            final Patient patient,
            final String hospitalUidFrom,
            final String note
    ) {
        //Отсечение даты от запрошеного даты-времени записи
        final Date paramsTime = DateConvertions.convertUTCMillisecondsToLocalDate(new LocalTime(paramsDateTime).getMillisOfDay());
        final Date paramsDate = new LocalDate(paramsDateTime).toDate();
        logger.debug("paramsDate is {}", paramsDate);
        logger.debug("paramsTime is {}, millis={}", paramsTime, paramsTime.getTime());
        for (Ticket currentTicket : tickets) {
            if (currentTicket.getBegTime().equals(paramsTime)) {
                logger.info("HIT! to {}", currentTicket.getInfo());
                if (currentTicket.isAvailable() && currentTicket.isFree()) {
                    //Нельзя записывать пациента, если на этот же день к этому же врачу он уже записывался
                    if (checkRepetitionToDoctor(patient)) {
                        logger.info("Detected repetition enqueue to this doctor. Cancelling enqueue.");
                        return new EnqueuePatientStatus().setSuccess(false)
                                .setMessage(CommunicationErrors.msgPatientAlreadyQueued.getMessage());
                    }
                    if (CommServer.getPatientQueueBean().checkPatientQueueByDateTime(
                            patient,
                            paramsDate,
                            currentTicket.getBegTime(),
                            currentTicket.getEndTime())
                            ) {
                        logger.info("Detected repetition enqueue to another doctor by same time. Cancelling enqueue.");
                        return new EnqueuePatientStatus().setSuccess(false)
                                .setMessage(CommunicationErrors.msgPatientAlreadyQueuedToTime.getMessage());
                    }
                    //Если ячейка времени свободна, то создаём записи в таблицах Event, Action, ActionProperty_Action:
                    try {
                        //0 проверяем квоты!
                        if (hospitalUidFrom != null && !hospitalUidFrom.isEmpty()) {
                            if (!checkAndDecrementQuotingBySpeciality(doctor.getSpeciality(), hospitalUidFrom)) {
                                logger.info("No coupons available for recording (by quotes on speciality)");
                                return new EnqueuePatientStatus().setSuccess(false)
                                        .setMessage(CommunicationErrors.msgNoTicketsAvailable.getMessage());
                            }
                        }
                        //1) Создаем событие  (Event)
                        //1.a)Получаем тип события (EventType)
                        final EventType queueEventType = CommServer.getPatientQueueBean().getQueueEventType();
                        //1.b)Сохраняем событие  (Event)
                        final Event queueEvent = CommServer.getEventBean().createEvent(
                                patient, queueEventType, doctor,
                                paramsDateTime, currentTicket.getEndTime()
                        );
                        logger.debug("Event is {} ID={} UUID={}",
                                queueEvent, queueEvent.getId(), queueEvent.getUuid().getUuid());
                        //2) Создаем действие (Action)
                        //2.a)Получаем тип    (ActionType)
                        final ActionType queueActionType = CommServer.getPatientQueueBean().getQueueActionType();
                        logger.debug("ActionType is {} typeID={} typeName={}",
                                queueActionType, queueActionType.getId(), queueActionType.getName());
                        //2.b)Сохраняем действие  (Action)

                        final Action queueAction = CommServer.getActionBean().createAction(
                                queueActionType, queueEvent, doctor,
                                paramsDateTime, hospitalUidFrom, (note == null ? "" : note));
                        logger.debug("Action is {} ID={} UUID={}",
                                queueAction, queueAction.getId(), queueAction.getUuid().getUuid());
                        // Заполняем ActionProperty_Action для 'queue' из Action='amb'
                        // Для каждого времени(times) из Action[приема врача]
                        // заполняем очередь(queue) null'ами если она не ссылается на другой Action,
                        // и добавлем наш запрос в эту очередь
                        // с нужным значением index, по которому будет происходить соответствие с ячейкой времени.
                        addActionToQueuePropertyValue(currentTicket.getQueueCellIndex(), queueAction);
                        logger.info("NEW QUEUE ACTION IS {}", queueAction.toString());
                        return new EnqueuePatientStatus().setSuccess(true).setIndex(currentTicket.getQueueCellIndex())
                                .setMessage(CommunicationErrors.msgOk.getMessage()).setQueueId(queueAction.getId());
                    } catch (CoreException e) {
                        logger.error("CoreException while create new EVENT", e);
                        return new EnqueuePatientStatus().setSuccess(false)
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
        return new EnqueuePatientStatus().setSuccess(false)
                .setMessage(CommunicationErrors.msgTicketNotFound.getMessage() + "  [" + paramsDateTime.toString() + "]");
    }


    /**
     * Внесение действия( состояние в очереди пациента ) в БД
     *
     * @param queueAction Действие пациента, отвечающее за его состояние в очереди
     * @param index       Номер временного отрезка на которое происходит запись
     * @throws CoreException Ошибка сохранения действия в БД
     */
    private void addActionToQueuePropertyValue(final int index, final Action queueAction) throws CoreException {

        if (queueAP == null) {
            logger.warn("Our enqueue is first to this doctor. Because queueActionProperty for doctorAction is null" +
                    " queueAMB.size()={}", queue.size());
            final ActionPropertyType queueAPType = CommServer.getPatientQueueBean().getQueueActionPropertyType();
            if (queueAPType != null) {
                queueAP = CommServer.getActionPropertyBean().createActionProperty(ambulatoryAction, queueAPType);
            } else {
                return;
            }
        }
        logger.debug("Queue ActionProperty = {}", queueAP.getId());
        for (int j = queue.size(); j < index; j++) {
            APValueAction newActionPropertyAction = new APValueAction(queueAP.getId(), j);
            newActionPropertyAction.setValue(null);
            CommServer.getManagerBean().persist(newActionPropertyAction);
        }

        APValueAction newActionPropertyAction = new APValueAction(queueAP.getId(), index);
        newActionPropertyAction.setValue(queueAction);
        logger.debug("NewActionProperty [{} {} {}]",
                newActionPropertyAction.getId().getId(),
                newActionPropertyAction.getId().getIndex(),
                newActionPropertyAction.getValue().getId());
        if (queue.size() < index) {
            logger.debug("Persist!");
            CommServer.getManagerBean().persist(newActionPropertyAction);
        } else {
            logger.debug("Merge!");
            CommServer.getManagerBean().merge(newActionPropertyAction);
        }
        logger.debug("All ActionProperty_Action's set successfully with index = {}", newActionPropertyAction.getId().getIndex());
    }


    /**
     * Проверка не является записан ли указанный пациент в указанном приеме
     *
     * @param patient пациент, для которого выполняется проверка
     * @return true - пациент записан в запрошенный прием, false - еще не записан
     */

    private boolean checkRepetitionToDoctor(final Patient patient) {
        for (Ticket currentTicket : tickets) {
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
                CommServer.getQuotingBySpecialityBean().getQuotingBySpecialityAndOrganisation(speciality, organisationInfisCode);
        if (quotingBySpecialityList.size() == 1) {
            logger.info("QuotingBySpeciality found and it is {}", quotingBySpecialityList);
            QuotingBySpeciality current = quotingBySpecialityList.get(0);
            if (current.getCouponsRemaining() > 0) {
                current.setCouponsRemaining(current.getCouponsRemaining() - 1);
                logger.debug("QuotingBySpeciality coupons_remaining reduce by 1");
                try {
                    CommServer.getManagerBean().merge(current);
                    return true;
                } catch (CoreException e) {
                    logger.error("Error while merge quoting.", e);
                }
            }
        }
        return false;
    }

    public Ticket getTicketByQueueIndex(final int queueIndex) {
        for (Ticket currentTicket : tickets) {
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
    public boolean checkQuotingBySpeciality(final String hospitalUidFrom) {
        List<QuotingBySpeciality> quotingBySpecialityList =
                CommServer.getQuotingBySpecialityBean().getQuotingBySpecialityAndOrganisation(doctor.getSpeciality(), hospitalUidFrom);
        //Квота должна быть единственной
        if (quotingBySpecialityList.size() == 1) {
            logger.debug("QuotingBySpeciality[{}] founded.", quotingBySpecialityList.get(0).getId());
            if (quotingBySpecialityList.get(0).getCouponsRemaining() > 0) {
                //Еще есть свободные квоты
                return true;
            }
            //Квот нету, запрещаем доступ ко всем талончикам
        }
        //Квота не найдена или исчерпана -> запрет на доступность талончиков
        logger.debug("All tickets are unavailable by QuotingBySpeciality");
        for (Ticket currentTicket : tickets) {
            currentTicket.setAvailable(false);
        }
        return false;
    }
}
