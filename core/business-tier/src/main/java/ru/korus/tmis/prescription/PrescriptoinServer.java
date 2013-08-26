package ru.korus.tmis.prescription;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
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
 * Description:  <br>
 */
public class PrescriptoinServer implements PrescriptionExchange.Iface {
    static final Logger logger = LoggerFactory.getLogger(PrescriptoinServer.class);

    private Thread thread;
    private TThreadPoolServer server;
    private TServerSocket serverTransport;

    public PrescriptoinServer() {
    }

    public void startServer(final int port, final int maxThreads) {
        try {
            thread = new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {
                                logger.info("Start server on port {}", port);
                                serverTransport = new TServerSocket(port);
                                PrescriptionExchange.Processor<PrescriptoinServer> processor = new PrescriptionExchange.Processor<PrescriptoinServer>(new PrescriptoinServer());
                                server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                                        .protocolFactory(new TBinaryProtocol.Factory())
                                        .processor(processor)
                                        .maxWorkerThreads(maxThreads));
                                server.serve();
                            } catch (TTransportException e) {
                                logger.error("TTransportException: " + e, e);
                            }
                        }
                    }
            );

            thread.start();
        } catch (Exception e) {
            logger.error("Exception: " + e, e);
        }
    }

    public void stopServer() {
        serverTransport.close();
        server.stop();
    }


    @Override
    public PrescriptionList getPrescriptionList(int eventId) throws EmptyPrescrListException, TException {
        logger.info("---- Get prescription ----");
        System.out.println("eventId = " + eventId);
        PrescriptionList prescriptionList = new PrescriptionList();
        Prescription elem = new Prescription();
        elem.setActInfo(new ActionData(1));
        elem.addToDrugComponents(new DrugComponent(1.0, 1, System.currentTimeMillis()));
        prescriptionList.addToPrescriptionList(elem);
        return prescriptionList;
    }

    @Override
    public void save(PrescriptionList prescrList) throws SavePrescrListException, TException {
        logger.info("---- Save prescription ----");
    }
}
