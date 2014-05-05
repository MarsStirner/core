package ru.korus.tmis.tariff;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.joda.time.DateMidnight;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbContractBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.NoSuchEntityException;
import ru.korus.tmis.tariff.thriftgen.*;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 06.08.13, 17:27 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class TariffServer implements TARIFFService.Iface {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(TariffServer.class);
    //Beans
    private static DbRbDispInfoBeanLocal rbDispInfoBean;
    private static DbContractBeanLocal contractBean;
    private static DbRbMedicalKindBeanLocal rbMedicalKindBean;
    private static DbRbServiceFinanceBeanLocal rbServiceFinanceBean;
    private static DbRbMedicalAidUnitBeanLocal rbMedicalAidUnitBean;
    private static MedicalKindUnitBeanLocal medicalKindUnitBean;
    private static TariffSpecificBeanLocal specificBean;
    private static DbContractTariffBeanLocal tariffBean;
    private static DbContractSpecificationBeanLocal contractSpecificationBean;
    private static DbRbServiceUetBeanLocal rbServiceUetBean;
    //Singleton instance
    private static TariffServer instance;
    //Server
    private static TServer server;
    //Transport
    private static TServerSocket serverTransport;
    //THREAD properties
    //Listener thread
    private Thread requestListener = null;
    //Listener port
    private static final int PORT_NUMBER = 7913;
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.MIN_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;
    //MAXimum work threads in thread pool
    private static final int MAX_WORKER_THREADS = 5;
    private static final int MIN_WORKER_THREADS = 1;

    //Number of request
    private static int requestNum = 0;

    @Override
    public List<Result> updateTariffs(final List<Tariff> tariffs, final int contractId)
            throws TException {
        final int currentRequestNum = ++requestNum;
        logger.info("Start of Request#{} Call method -> updateTariffs[{}](contractId={}, tariffs={})", currentRequestNum, tariffs.size(), contractId, tariffs);
        Contract contract = null;
        try {
            contract = contractBean.getContractById(contractId);
        } catch (NoSuchEntityException e) {
            logger.error("No such contract[{}].", contractId);
        } catch (CoreException e) {
            logger.error("Exception while getting contract:", e);
        }
        if (contract != null && !contract.isDeleted()) {
            logger.debug("Contract is: Number[{}], Date[{}], resolution[{}]",
                    contract.getNumber(),
                    contract.getDate(),
                    contract.getResolution());
        } else {
            logger.debug("End of R#{}. No such contract or contract is deleted.");
            throw TariffServiceErrors.NO_SUCH_CONTRACT.getException();
        }
        //удаляй тариф из базы если его срок действия закончился год назад
        DateMidnight removeBeforeDate = new DateMidnight().minusYears(1);
        logger.info("Start to remove old Tariffs which endDate is before {}", removeBeforeDate.toString());
        int deletedTariffsCount = tariffBean.removeOldTariffs(removeBeforeDate.toDate());
        logger.info("Deleted {} old tariffs", deletedTariffsCount);

        //Получение фабрики тарифов
        final TariffFactory factory = TariffFactory.getInstance();
        int index = 0;

        final List<Result> result = new ArrayList<Result>(tariffs.size());
        for (Tariff currentTariff : tariffs) {
            index++;
            int currentTariffNumber = currentTariff.getNumber();
            if (logger.isDebugEnabled()) {
                logger.debug("Start of Tariff #{}-{}", index, currentTariffNumber);
                logger.debug("Data: {}", currentTariff);
            }
            TariffEntry extTariff = factory.getTariffEntry(currentTariff);
            if (!extTariff.fillVariables()) {
                Result currentResult = extTariff.getResult();
                logger.warn("T#{} Processing skipped. Error message is: {}", currentTariffNumber,
                        currentResult.getError().getMessage());
                result.add(currentResult);
                continue;
            }
            if (logger.isDebugEnabled()) {
                extTariff.printSummary();
            }
            if (!extTariff.fillNewTariffs(contract)) {
                Result currentResult = extTariff.getResult();
                logger.warn("T#{} Processing skipped. Error message is: {}", currentTariffNumber,
                        currentResult.getError().getMessage());
                result.add(currentResult);
                continue;
            } else {
                if (logger.isDebugEnabled()) {
                    extTariff.printFormedTariff();
                }
            }
            Helper.storeNewTariffsToDatabase(extTariff.getTariffList());
            logger.debug("Succesfully end of T#{}", currentTariffNumber);
            result.add(extTariff.getResult());
        }
        logger.info("End of R#{} updateTariffs. Return \"{}\" as result.", currentRequestNum, result);
        return result;
    }


    public TariffServer() {
        logger.info("Starting TariffServer initialize.");
        requestListener = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread
                try {
                    serverTransport = new TServerSocket(PORT_NUMBER);
                    TARIFFService.Processor<TariffServer> processor = new TARIFFService.Processor<TariffServer>(getInstance());
                    server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                            .protocolFactory(new TBinaryProtocol.Factory())
                            .processor(processor)
                            .minWorkerThreads(MIN_WORKER_THREADS)
                            .maxWorkerThreads(MAX_WORKER_THREADS));
                    logger.info("Starting server on port {}", PORT_NUMBER);
                    server.serve();
                } catch (Exception e) {
                    logger.error("Failed to start server on port {}. Exception={}", PORT_NUMBER, e.getMessage());
                    logger.error("EXception::", e);
                }
            }
        });
        requestListener.setName("Thrift-Service-Thread");
        requestListener.setPriority(SERVER_THREAD_PRIORITY);
        requestListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }


    public static TariffServer getInstance() {
        if (instance == null) {
            instance = new TariffServer();
        }
        return instance;
    }

    public void startService() {
        requestListener.start();
    }

    public void endWork() {
        logger.warn("TariffServer start closing");
        logger.info("Total request served={}", requestNum);
        server.stop();
        logger.warn("Server stopped.");
        serverTransport.close();
        logger.warn("Transport closed.");
        requestListener.interrupt();
        if (requestListener.isInterrupted()) logger.warn("ServerThread is interrupted successfully");
        if (requestListener.isAlive()) {
            logger.error("ServerThread is ALIVE?!?!?!");
            requestListener.setPriority(Thread.MIN_PRIORITY);
        }
        logger.info("All fully stopped.");
    }


    public static void setRbDispInfoBean(final DbRbDispInfoBeanLocal rbDispInfoBean) {
        TariffServer.rbDispInfoBean = rbDispInfoBean;
    }

    public static void setContractBean(final DbContractBeanLocal contractBean) {
        TariffServer.contractBean = contractBean;
    }

    public static void setRbMedicalKindBean(final DbRbMedicalKindBeanLocal rbMedicalKindBean) {
        TariffServer.rbMedicalKindBean = rbMedicalKindBean;
    }

    public static void setRbServiceFinanceBean(final DbRbServiceFinanceBeanLocal rbServiceFinanceBean) {
        TariffServer.rbServiceFinanceBean = rbServiceFinanceBean;
    }


    public static void setRbMedicalAidUnitBean(final DbRbMedicalAidUnitBeanLocal rbMedicalAidUnitBean) {
        TariffServer.rbMedicalAidUnitBean = rbMedicalAidUnitBean;
    }

    public static void setMedicalKindUnitBean(final MedicalKindUnitBeanLocal medicalKindUnitBean) {
        TariffServer.medicalKindUnitBean = medicalKindUnitBean;
    }

    public static void setSpecificBean(final TariffSpecificBeanLocal specificBean) {
        TariffServer.specificBean = specificBean;
    }

    public static void setTariffBean(final DbContractTariffBeanLocal tariffBean) {
        TariffServer.tariffBean = tariffBean;
    }

    public static DbRbDispInfoBeanLocal getRbDispInfoBean() {
        return rbDispInfoBean;
    }

    public static DbContractBeanLocal getContractBean() {
        return contractBean;
    }

    public static DbRbMedicalKindBeanLocal getRbMedicalKindBean() {
        return rbMedicalKindBean;
    }

    public static DbRbServiceFinanceBeanLocal getRbServiceFinanceBean() {
        return rbServiceFinanceBean;
    }

    public static DbRbMedicalAidUnitBeanLocal getRbMedicalAidUnitBean() {
        return rbMedicalAidUnitBean;
    }

    public static MedicalKindUnitBeanLocal getMedicalKindUnitBean() {
        return medicalKindUnitBean;
    }

    public static TariffSpecificBeanLocal getSpecificBean() {
        return specificBean;
    }

    public static DbContractTariffBeanLocal getTariffBean() {
        return tariffBean;
    }

    public static void setContractSpecificationBean(final DbContractSpecificationBeanLocal contractSpecificationBean) {
        TariffServer.contractSpecificationBean = contractSpecificationBean;
    }

    public static DbContractSpecificationBeanLocal getContractSpecificationBean() {
        return contractSpecificationBean;
    }

    public static void setRbServiceUetBean(DbRbServiceUetBeanLocal rbServiceUetBean) {
        TariffServer.rbServiceUetBean = rbServiceUetBean;
    }

    public static DbRbServiceUetBeanLocal getRbServiceUetBean() {
        return rbServiceUetBean;
    }

}
