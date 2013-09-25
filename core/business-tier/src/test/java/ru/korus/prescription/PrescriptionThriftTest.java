package ru.korus.prescription;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransportException;
import ru.korus.tmis.prescription.thservice.*;

import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        20.08.13, 17:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class PrescriptionThriftTest {

    class Servak implements PrescriptionExchange.Iface {

        @Override
        public PrescriptionList getPrescriptionList(int eventId) throws  TException {
            System.out.println("eventId = " + eventId);
            PrescriptionList prescriptionList = new PrescriptionList();
            Prescription elem = new Prescription();
            elem.setActInfo(new ActionData());
            prescriptionList.addToPrescriptionList(elem);
            return prescriptionList;
        }

        @Override
        public void save(PrescriptionList prescrList) throws SavePrescrListException, TException {

        }

        @Override
        public boolean updateBalanceOfGoods(List<Integer> drugIds) throws TException {
            return false;  //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public void createServer() throws TTransportException {
        TServerSocket serverTransport = new TServerSocket(8383);
        PrescriptionExchange.Processor<Servak> processor = new PrescriptionExchange.Processor<Servak>(new Servak());
        TThreadPoolServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport)
                .protocolFactory(new TBinaryProtocol.Factory())
                .processor(processor)
                .maxWorkerThreads(5));
        //logger.info("Starting server on port {}", PORT_NUMBER);

        server.serve();


    }


    public static void main(String[] args) throws TException, InterruptedException {
        // сервер
//        final PrescriptionThriftTest t = new PrescriptionThriftTest();
//        new Thread(
//                new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            System.out.println("--------");
//                            t.createServer();
//                            System.out.println("created");
//                        } catch (TTransportException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//        ).start();
//
//        Thread.sleep(1000);

        // клиент
        final TSocket trans = new TSocket("127.0.0.1", 8383);
        trans.open();
        PrescriptionExchange.Client client = new PrescriptionExchange.Client(new TBinaryProtocol(trans));


        PrescriptionList prescriptionList = client.getPrescriptionList(111);
        System.out.println("prescriptionList = " + prescriptionList);
        trans.close();

    }
}
