package ru.korus.tmis.communication;

import com.google.common.collect.ImmutableList;
import org.joda.time.DateMidnight;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.Amb;
import ru.korus.tmis.communication.thriftgen.QuotingType;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

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

    private List<APValueTime> times;
    private List<APValueAction> queue;
    private List<Ticket> tickets;

    private String office;
    private Date begTime;
    private Date endTime;
    private Integer plan;

    private short externalCount;
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
                    .getActionPropertiesByActionIdAndTypeTypeNames(timelineAction.getId(), ImmutableList.of("reasonOfAbsence"));
            if (actionPropertyListMap.isEmpty()) {
                logger.debug("Timeline hasn't ReasonOfAbsence");
                return false;
            } else {
                final Iterator<ActionProperty> propertyIterator = actionPropertyListMap.keySet().iterator();
                return propertyIterator.hasNext() && !actionPropertyListMap.get(propertyIterator.next()).isEmpty();
            }
        } catch (Exception e) {
            if (timelineAction == null) {
                logger.debug("Timeline action doesnt exists");
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
                    times = new ArrayList<APValueTime>(apValueList.size());
                    //Не преобразуем эти времена
                    for (APValue timevalue : apValueList) {
                        times.add((APValueTime) timevalue);
                    }
                } else if ("queue".equals(fieldName)) {
                    queue = new ArrayList<APValueAction>(apValueList.size());
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
     * Создание списка талончиков к врачу из двух списков (times и queue)
     *
     * @return Количество внешних обращений (из других ЛПУ)
     */
    public void formTickets() throws CoreException {
        getAmbulatoryProperties();
        tickets = new ArrayList<Ticket>(times.size());
        int queueIndex = emergencyPatientCount;
        for (int timeIndex = 0; timeIndex < times.size(); timeIndex++) {
            //Текущая ячейка времени
            final Date currentTime = times.get(timeIndex).getValue();
            //окончание текущей ячейки вермени
            final Date nextTime;
            if (times.size() < timeIndex + 1) {
                nextTime = times.get(timeIndex + 1).getValue();
            } else {
                nextTime = endTime;
            }
            final Action queueAction = (queue.size() > queueIndex) ?
                    queue.get(queueIndex).getValue() : null;
            final Ticket currentTicket = new Ticket();
            currentTicket.setBegTime(currentTime);
            currentTicket.setEndTime(nextTime);
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
    }


    /**
     * Применяет ограничения по времени (QuotingByTime врача) на возвращаемый набор талончиков(result.tickets)
     */
    public void takeConstraintsOnTickets(QuotingType quotingType) {
        getQuotingByTimeConstraints(quotingType);
        final short quota = quotingByTimeConstraints.isEmpty() ? doctor.getExternalQuota() : -1;
        //TODO абсолютное количество или проценты? должно зависеть от Person.quoteUnit
        final int quoteAvailable = Math.max(0, (int) (quota * tickets.size() * 0.01) - externalCount);
        this.available = (quoteAvailable != 0);
        for (Ticket currentTicket : tickets) {
            boolean available = false;
            for (QuotingByTime qbt : quotingByTimeConstraints) {
                if (qbt.getQuotingTimeStart().getTime() != 0 && qbt.getQuotingTimeEnd().getTime() != 0) {
                    if (currentTicket.getBegTime().after(qbt.getQuotingTimeStart()) && currentTicket.getEndTime().before(qbt.getQuotingTimeEnd())
                            && currentTicket.isAvailable() && quota == -1 && quoteAvailable > 0) {
                        available = true;
                        break;
                    }
                }
            }
            currentTicket.setAvailable(available);
        }

    }


    public Ticket getFirstFreeTicketAfterDateTime(final long checkDateTime) {
        final long currentAmbulatoryDaty = DateConvertions.convertDateToUTCMilliseconds(ambulatoryDate);
        for (Ticket currentTicket : tickets) {
            if (currentTicket.isAvailable() && currentTicket.isFree() && currentTicket.getBegTime().after(DateConvertions.convertUTCMillisecondsToLocalDate(checkDateTime - currentAmbulatoryDaty))) {
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
}
