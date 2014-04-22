package ru.korus.tmis.ws.finance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.database.finance.DbEventLocalContractLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.HospitalBedBeanLocal;
import ru.korus.tmis.core.transmit.Sender;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.ws.finance.odvd.ObjectFactory;
import ru.korus.tmis.ws.finance.odvd.Table;
import ru.korus.tmis.ws.finance.odvd.WsPoliclinic;
import ru.korus.tmis.ws.finance.odvd.WsPoliclinicPortType;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 9:32 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class FinancePullBean implements FinancePullBeanLocal, Sender {

    @EJB
    private TransmitterLocal transmitterLocal;

    @EJB
    DbEventLocalContractLocal dbEventLocalContractLocal;

    @EJB
    HospitalBedBeanLocal hospitalBedBeanLocal;

    private WsPoliclinicPortType port = null;

    private static final Logger logger = LoggerFactory.getLogger(FinancePullBean.class);

    @Override
    public void setPort(WsPoliclinicPortType port) {
        this.port = port;
    }

    @Override
    public void pullDb() {
        try {
            logger.info("1C ODVD integration entry...");
            if (ConfigManager.Finance().isFinanceActive()) {
                logger.info("1C ODVD integration is active...");
                logger.info("1C ODVD integration send new events-..");
                transmitterLocal.send(this, EventsToODVD.class, "EventsToODVD.ToSend");
                logger.info("1C ODVD integration send service...");
                transmitterLocal.send(this, ActionToODVD.class, "ActionToODVD.ToSend");
            } else {
                logger.info("1C ODVD integration is disabled...");
            }
        } catch (Exception ex) {
            logger.error("1C ODVD integration internal error.", ex);
        }

    }

    @Override
    public void sendEntity(Object entity) throws CoreException {
        assert entity instanceof EventsToODVD || entity instanceof ActionToODVD;

        if (entity instanceof EventsToODVD) {  // передаем в 1С новое платное обращение
            final EventsToODVD eventsToODVD = (EventsToODVD) entity;
            sendNewEvent(entity, eventsToODVD);
        } else { // передаем в 1С информацию об оказанной услуги
            final ActionToODVD actionsToODVD = (ActionToODVD) entity;
            sendNewAction(entity, actionsToODVD);
        }
    }

    private void sendNewAction(Object entity, ActionToODVD actionsToODVD) throws CoreException {
        logger.info("processing ActionToODVD.event_id = ", actionsToODVD.getActionId());
        List<Action> actionList = new LinkedList<Action>();
        final Action action = actionsToODVD.getAction();
        if (action == null) {
            final String msg = "action not found";
            logger.error(msg);
            throw new CoreException(msg);
        }
        actionList.add(action);
        final Event event = action.getEvent();
        if (event == null) {
            final String msg = "event not found";
            logger.error(msg);
            throw new CoreException(msg);

        }
        sendClosedActions(event, actionList);
    }

    public void sendClosedActions(Event event, List<Action> actionList) {
        assert event != null;
        assert !actionList.isEmpty();
        getPort().putService(BigInteger.valueOf(event.getId()), OdvdBuilder.toOdvdTableActions(actionList, hospitalBedBeanLocal));
    }



    private void sendNewEvent(Object entity, EventsToODVD eventsToODVD) throws CoreException {
        logger.info("processing EventsToODVD.event_id = {}", eventsToODVD.getEventId());
        Event event = eventsToODVD.getEvent();
        if (event == null) {
            final String msg = "event not found: entry: " + entity;
            logger.error(msg);
            throw new CoreException(msg);
        }
        Patient patient = event.getPatient();
        if (patient == null) {
            final String msg = "patient not found";
            logger.error(msg);
            throw new CoreException(msg);
        }
        EventLocalContract eventLocalContract = dbEventLocalContractLocal.getByEventId(event.getId());

        try {
            final BigInteger idTreatment = BigInteger.valueOf(event.getId());
            final String numTreatment = event.getExternalId();
            final XMLGregorianCalendar dateTreatment = Database.toGregorianCalendar(event.getCreateDatetime());
            final String codeContract = eventLocalContract == null ? null : eventLocalContract.getNumberContract();
            final String codePatient = String.valueOf(patient.getId());

            //TODO не по протоколу (ФИО должны быть отдельно)
            final String patientName = String.format("%s %s %s", nullToEmpty(patient.getFirstName()),
                    nullToEmpty(patient.getPatrName()),
                    nullToEmpty(patient.getLastName()));
            //TODO не по протоколу (ФИО должны быть отдельно)

            final String paidName = eventLocalContract == null ? "unknown" : String.format("%s %s %s", nullToEmpty(eventLocalContract.getFirstName()),
                    nullToEmpty(eventLocalContract.getPatrName()),
                    nullToEmpty(eventLocalContract.getLastName()));
            getPort().putTreatment(idTreatment,
                    numTreatment,
                    dateTreatment,
                    codeContract,
                    codePatient,
                    patientName,
                    paidName);
        } catch (DatatypeConfigurationException e) {
            final String msg = "wrong event.createDate";
            logger.error(msg, e);
            throw new CoreException(msg);
        }
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }


    private WsPoliclinicPortType getPort() {
        if (port == null) {
            WsPoliclinic service = new WsPoliclinic();
            setPort(service.getWsPoliclinicSoap());
        }
        return port;
    }
}
