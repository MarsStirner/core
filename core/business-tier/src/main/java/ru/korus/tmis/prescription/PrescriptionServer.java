package ru.korus.tmis.prescription;

import ch.epfl.lamp.compiler.msil.util.Table;
import com.google.common.collect.ImmutableSet;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.pharmacy.DbUUIDBean;
import ru.korus.tmis.prescription.thservice.*;
import ru.korus.tmis.prescription.thservice.DrugComponent;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.08.13, 17:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Сервер, реализующий функционал по работе с Листами Назначений (далее ЛН) <br>
 */
public class PrescriptionServer implements PrescriptionExchange.Iface {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(PrescriptionServer.class);
    //Beans
    private static DbEventBeanLocal eventBean = null;
    private static DbActionBeanLocal actionBean = null;
    private static DbDrugChartBeanLocal drugChartBean = null;
    private static DbActionPropertyBeanLocal actionPropertyBean = null;
    private static DbDrugComponentBeanLocal drugComponentBean = null;
    private static DbActionTypeBeanLocal actionTypeBean = null;
    private static DbStaffBeanLocal staffBean = null;
    private static DbRbUnitBeanLocal unitBean = null;
    private static DbActionPropertyTypeBeanLocal actionPropertyTypeBean = null;
    //Singleton instance
    private static PrescriptionServer instance;
    private static TServer server;
    private static TServerSocket serverTransport;
    //THREAD properties
    //Listener thread
    private Thread prescriptionListener = null;
    //Listener port
    private static int PORT_NUMBER;
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.MIN_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;
    //max size of threadpool
    private static int MAX_WORKER_THREADS;

    //Number of request
    private static int requestNum = 0;

    //Обновления остатков
    private static BalanceOfGoodsInfo balanceOfGoodsInfo = null;


    @Override
    public PrescriptionList getPrescriptionList(final int eventId) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getPrescriptionList(eventId = {})", currentRequestNum, eventId);
        Event requestedEvent = null;
        try {
            requestedEvent = eventBean.getEventById(eventId);
        } catch (CoreException e) {
            logger.warn("CoreException while getting event by id.");
        }
        PrescriptionList result = new PrescriptionList(new ArrayList<Prescription>());
        if (requestedEvent == null) {
            logger.error("End of #{}, return empty PrescriptionList. Because Event[{}] not exists.", currentRequestNum, eventId);
            return result;
        } else {
            logger.debug("Event[{}] exists.", eventId);
            result.setEventId(eventId);
        }
        List<Action> actionList = actionBean.getActionsByTypeFlatCodeAndEventId(eventId, Constants.PRESCRIPTION_TYPE_FLAT_CODE);
        for (Action currentAction : actionList) {
            logger.debug("Start of parsing Action[{}]", currentAction);
            PrescriptionHelper prescription = new PrescriptionHelper(currentAction);
            //Заполнение данных назначения
            prescription.fillActionData();
            if (logger.isDebugEnabled()) {
                logger.debug("ActionInfo: {}", prescription.getPrescription().getActInfo());
            }
            //Заполение интервалов и компонент
            prescription.fillDrugIntervals();
            //добавление сформированного назначения в лист
            result.addToPrescriptionList(prescription.getPrescription());
        }
        logger.info("End of #{} getPrescriptionList. Return ({})", currentRequestNum, result);
        return result;
    }

    @Override
    public void save(PrescriptionList prescrList) throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> save(prescrList = {})", currentRequestNum, prescrList);
        List<Prescription> receivedPrescriptionList = prescrList.getPrescriptionList();
        if (receivedPrescriptionList.isEmpty()) {
            logger.warn("Received prescriptionList is empty, so nothing to do.");
            return;
        }
        Event requestedEvent = null;
        try {
            requestedEvent = eventBean.getEventById(prescrList.getEventId());
            if (requestedEvent == null) {
                logger.error("End of #{}. Because Event[{}] not exists.", currentRequestNum, prescrList.getEventId());
                return;
            } else {
                logger.debug("Event[{}] exists.", prescrList.getEventId());
            }
        } catch (CoreException e) {
            logger.warn("End of #{}. CoreException while getting event by id.", currentRequestNum);
            return;
        }

        for (Prescription currentPrescription : receivedPrescriptionList) {
            ActionData currentActionInfo = currentPrescription.getActInfo();
            logger.debug("Start processing - {}", currentPrescription);
            if (currentPrescription.isSetActInfo() && !currentActionInfo.isSetId()) {
                //Абсолютно новое назначение
                ActionType recievedActionType = null;
                Staff recievedPerson = null;
                try {
                    recievedActionType = actionTypeBean.getActionTypeById(currentActionInfo.getActionType_id());
                    if(currentActionInfo.isSetSetPerson_id()){
                        recievedPerson = staffBean.getStaffById(currentActionInfo.getSetPerson_id());
                    }
                } catch (CoreException e) {
                    if(recievedActionType == null){
                        logger.error("SKIP: Incorrect ActionInfo.actionType.id[{}] has  {}",
                                currentActionInfo.getActionType_id(), currentPrescription);
                        continue;
                    } else if(recievedPerson == null){
                        logger.warn("PERSON_ID is NOT set or incorrect.");
                    }
                }
                Action newPrescriptionAction = Helper.createPrescriptionAction(
                        requestedEvent,
                        recievedActionType,
                        recievedPerson,
                        currentActionInfo);
                //TODO persistAction
                if(currentActionInfo.isSetMoa()){
                    try {
                        final ActionProperty moaAP = actionPropertyBean.createActionProperty(
                                newPrescriptionAction,
                                actionPropertyTypeBean.getActionPropertyTypeByActionTypeIdAndTypeCode(newPrescriptionAction.getActionType().getId(), Constants.MOA, false));
                        //TODO persist MOA_AP
                        final APValue moaAPV = actionPropertyBean.createActionPropertyValue(moaAP, currentActionInfo.getMoa(), 0);
                        //TODO persist MOA_APV
                    } catch (CoreException e) {
                        logger.error("Exception while forming ActionProperty: ", e);
                    }
                    logger.warn("NOT READY: persist AP & APV MOA[{}]", currentActionInfo.getMoa());
                }
                if(currentActionInfo.isSetVoa()){
                    try {
                        final ActionProperty voaAP = actionPropertyBean.createActionProperty(
                                newPrescriptionAction,
                                actionPropertyTypeBean.getActionPropertyTypeByActionTypeIdAndTypeCode(newPrescriptionAction.getActionType().getId(), Constants.VOA, false));
                        //TODO persist VOA_AP
                        final APValue voaAPV = actionPropertyBean.createActionPropertyValue(voaAP, currentActionInfo.getVoa(), 0);
                        //TODO persist VOA_APV
                    } catch (CoreException e) {
                        logger.error("Exception while forming ActionProperty: ", e);
                    }
                    logger.warn("NOT READY: persist AP & APV VOA[{}]", currentActionInfo.getMoa());
                }
                //Сохранение препаратов в этом назначении
                for(DrugComponent currentDrugComponent : currentPrescription.getDrugComponents()){
                    try {
                        ru.korus.tmis.core.entity.model.DrugComponent drugComponentToSave =
                                Helper.createEntityDrugComponentFromThriftDrugComponent(currentDrugComponent, newPrescriptionAction);
                        //TODO persist DrugComponent
                        logger.warn("NOT READY: persist DrugComponent");
                    } catch (CoreException e) {
                        logger.warn("Something is incorrect while convert To DrugComponent Entity: ", e);
                        continue;
                    }
                }
                for(DrugInterval currentDrugInterval : currentPrescription.getDrugIntervals()){
                    DrugChart drugChartToSave =
                            Helper.createEntityDrugChartFromThriftDrugInterval(currentDrugInterval, newPrescriptionAction);
                    //TODO persist DrugChart
                    logger.warn("NOT READY: persist DrugChart");
                    for(DrugIntervalExec currentDrugIntervalExec : currentDrugInterval.getExecIntervals()){
                        DrugChart executionIntervalToSave =
                                Helper.createEntityDrugChartFromThriftDrugIntervalExecution(
                                        currentDrugIntervalExec,
                                        drugChartToSave,
                                        newPrescriptionAction);
                        //TODO persist DrugChart
                        logger.warn("NOT READY: persist DrugChartExec");
                    }
                }
            }
        }
        logger.info("End of #{} save. Save is successfully", currentRequestNum);
    }

    @Override
    public boolean updateBalanceOfGoods(List<Integer> drugIds) throws TException {
        logger.info("Call method -> updateBalanceOfGoods; drugIds.size(): {}", drugIds.size());
        return balanceOfGoodsInfo.update(drugIds);
    }

    public static PrescriptionServer getInstance(int port, int maxThreadCount) {
        if (instance == null) {
            instance = new PrescriptionServer(port, maxThreadCount);
        } else {
            if (PORT_NUMBER != port) {
                logger.error("PrescriptionServer is already has another port [{}], requested was [{}]", PORT_NUMBER, port);
            }
        }
        return instance;
    }

    public PrescriptionServer(final int port, final int maxThreads) {
        PORT_NUMBER = port;
        MAX_WORKER_THREADS = maxThreads;
        prescriptionListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    serverTransport = new TServerSocket(PORT_NUMBER);
                    PrescriptionExchange.Processor<PrescriptionServer> processor = new PrescriptionExchange.Processor<PrescriptionServer>(instance);
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .protocolFactory(new TBinaryProtocol.Factory())
                            .processor(processor)
                            .maxWorkerThreads(MAX_WORKER_THREADS));
                    logger.info("Start server on port {}", PORT_NUMBER);
                    server.serve();
                } catch (TTransportException e) {
                    logger.error("TTransportException: ", e);
                }
            }
        }
        );
        prescriptionListener.setName("Thrift-Prescription-Thread");
        prescriptionListener.setPriority(SERVER_THREAD_PRIORITY);
        prescriptionListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }

    public void startServer() {
        prescriptionListener.start();
    }

    public void endWork() {
        logger.warn("PrescriptionServer start closing");
        logger.info("Total request served={}", requestNum);
        server.stop();
        logger.warn("Server stopped.");
        serverTransport.close();
        logger.warn("Transport closed.");
        prescriptionListener.interrupt();
        if (prescriptionListener.isInterrupted()) {
            logger.warn("ServerThread is interrupted successfully");
        }
        if (prescriptionListener.isAlive()) {
            try {
                logger.error("Wait for a second to Thread interrupt");
                prescriptionListener.join(1000);
            } catch (InterruptedException e) {
            }
            if (prescriptionListener.isAlive()) {
                logger.error("ServerThread is STILL ALIVE?! Setting MinimalPriority to the Thread");
                prescriptionListener.setPriority(Thread.MIN_PRIORITY);
            }
        }
        logger.info("All fully stopped.");
    }

    public static void setActionBean(DbActionBeanLocal actionBean) {
        PrescriptionServer.actionBean = actionBean;
    }

    public static void setEventBean(DbEventBeanLocal eventBean) {
        PrescriptionServer.eventBean = eventBean;
    }

    public static void setDrugChartBean(DbDrugChartBeanLocal drugChartBean) {
        PrescriptionServer.drugChartBean = drugChartBean;
    }

    public static DbDrugChartBeanLocal getDrugChartBean() {
        return drugChartBean;
    }

    public static void setActionPropertyBean(DbActionPropertyBeanLocal actionPropertyBean) {
        PrescriptionServer.actionPropertyBean = actionPropertyBean;
    }

    public static DbActionPropertyBeanLocal getActionPropertyBean() {
        return actionPropertyBean;
    }

    public static void setDrugComponentBean(DbDrugComponentBeanLocal drugComponentBean) {
        PrescriptionServer.drugComponentBean = drugComponentBean;
    }

    public static DbDrugComponentBeanLocal getDrugComponentBean() {
        return drugComponentBean;
    }

    public static DbEventBeanLocal getEventBean() {
        return eventBean;
    }

    public static DbActionBeanLocal getActionBean() {
        return actionBean;
    }

    public static DbActionTypeBeanLocal getActionTypeBean() {
        return actionTypeBean;
    }

    public static void setActionTypeBean(DbActionTypeBeanLocal actionTypeBean) {
        PrescriptionServer.actionTypeBean = actionTypeBean;
    }

    public static DbStaffBeanLocal getStaffBean() {
        return staffBean;
    }

    public static void setStaffBean(DbStaffBeanLocal staffBean) {
        PrescriptionServer.staffBean = staffBean;
    }

    public static void setUnitBean(DbRbUnitBeanLocal unitBean) {
        PrescriptionServer.unitBean = unitBean;
    }

    public static DbRbUnitBeanLocal getUnitBean() {
        return unitBean;
    }

    public static void setActionPropertyTypeBean(DbActionPropertyTypeBeanLocal actionPropertyTypeBean) {
        PrescriptionServer.actionPropertyTypeBean = actionPropertyTypeBean;
    }

    public static DbActionPropertyTypeBeanLocal getActionPropertyTypeBean() {
        return actionPropertyTypeBean;
    }

    public static void setBalanceOfGoodsInfo(BalanceOfGoodsInfo balanceOfGoodsInfo) {
        PrescriptionServer.balanceOfGoodsInfo = balanceOfGoodsInfo;
    }
}
