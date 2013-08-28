package ru.korus.tmis.prescription;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.prescription.thservice.*;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.08.13, 17:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Сервер, реализующий функционал по работе с Листами Назначений (далее ЛН) <br>
 */
public class PrescriptionServer implements PrescriptionExchange.Iface {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(PrescriptionServer.class);

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



    @Override
    public PrescriptionList getPrescriptionList(final int eventId) throws EmptyPrescrListException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> getPrescriptionList(eventId = {})", currentRequestNum, eventId);

        PrescriptionList result = new PrescriptionList();
        logger.info("End of #{} getPrescriptionList. Return ({})", currentRequestNum, result);
        return result;
    }

    @Override
    public void save(PrescriptionList prescrList) throws SavePrescrListException, TException {
        final int currentRequestNum = ++requestNum;
        logger.info("#{} Call method -> save(prescrList = {})", currentRequestNum, prescrList);

        PrescriptionList result = new PrescriptionList();
        logger.info("End of #{} save. Save is successfully", currentRequestNum);
    }

    public static PrescriptionServer getInstance(int port, int maxThreadCount) {
        if(instance == null){
            instance = new PrescriptionServer(port, maxThreadCount);
        } else {
            if(PORT_NUMBER != port){
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
}
