package ru.korus.tmis.communication;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.communication.thriftgen.Communications;
import ru.korus.tmis.communication.thriftgen.OrgStructure;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.exception.CoreException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * User: eupatov
 * Date: 17.12.12 at 14:55
 */

public class CommServer implements Communications.Iface {
    //Logger
    final static Logger logger = LoggerFactory.getLogger(CommServer.class);
    //Beans
    private static DbOrgStructureBeanLocal orgStructureBean = null;

    //Singleton instance
    private static CommServer instance;

    //THREAD properties
    //Listener thread
    Thread communicationListener = null;
    //Listener port
    private static final int PORT_NUMBER = 7911;
    //Listener thread priority
    private static final int SERVER_THREAD_PRIORITY = Thread.MIN_PRIORITY;
    //launch as daemon?
    private static final boolean SERVER_THREAD_IS_DAEMON = false;


    @Override
    public List<OrgStructure> getOrgStructures(int id, boolean recursive) throws TException {

        logger.info("getOrgStructures called with params id={} recursive={} link to ejb={}", id, recursive, orgStructureBean);

        List<OrgStructure> result = new LinkedList<OrgStructure>();
        List<ru.korus.tmis.core.entity.model.OrgStructure> allStructures = orgStructureBean.getAllOrgStructures();
        //FROM TOP ENTITIES
        if (id == 0) {

        }


        //Get a top_level entity
        ru.korus.tmis.core.entity.model.OrgStructure root_element = null;
        try {
            root_element = orgStructureBean.getOrgStructureById(id);
        } catch (CoreException e) {
            logger.warn("No orgStructures Found by id=" + id + " with some exception", e);
            return result;
        }
        //if it doesnt exist-> return null
        if (root_element == null) {
            logger.warn("No orgStructures Found by id={}", id);
            return result;
        }
        result.add(parseOrgStructureToThriftStruct(root_element)); //parse and add root_entity
        logger.info("Root Element={}", root_element);

        //recursive   from parent down to childrens
        if (recursive) {
            List<ru.korus.tmis.core.entity.model.OrgStructure> all_entities = orgStructureBean.getAllOrgStructures();
            all_entities.remove(root_element);

            ArrayList<Integer> parent_ids = new ArrayList<Integer>(1);
            parent_ids.add(root_element.getId());
            ArrayList<Integer> next_parent_ids = new ArrayList<Integer>(1);
            int sub_level = 0;
            //Level of parent
            // 0=root_element
            // 1=root_childrens
            // 2=childrens of each root_children
            // etc....
            boolean contain = true;
            while (contain) {
                contain = false;
                sub_level++;
                for (ru.korus.tmis.core.entity.model.OrgStructure current : all_entities) {
                    if (parent_ids.contains(current.getParentId()) && current.getAvailableForExternal() > 0) {
                        result.add(parseOrgStructureToThriftStruct(current));
                        next_parent_ids.add(current.getId());
                        contain = true;
                        logger.debug("Add Element={} at sublevel={}", current, sub_level);
                    }
                }
                parent_ids.clear();
                // parent_ids=next_parent_ids; pointers!
                parent_ids.addAll(next_parent_ids);
                //remove next_parent_ids from all_entities???
                next_parent_ids.clear();
                //sub_level check
                if (sub_level > 50) {
                    logger.warn("Sublevels greater then 50!? Something is incorrect.");
                    contain = false;
                }
            }
        }
        logger.info("Returning {} OrgStrucutres", result.size());
        return result;
    }


    private OrgStructure parseOrgStructureToThriftStruct(ru.korus.tmis.core.entity.model.OrgStructure item) {

        OrgStructure result = new OrgStructure().setId(item.getId()).setCode(item.getCode());
        if (item.getAddress() != null) result.setAdress(item.getAddress());
        if (item.getName() != null) result.setName(item.getName());
        if (item.getParentId() != null) result.setParent_id(item.getParentId());
        return result;
    }


    public CommServer() {
        logger.info("Starting Communication Servlet initialize.");
        communicationListener = new Thread(new Runnable() {
            @Override
            public void run() {
                //Thread
                try {
                    TServerSocket serverTransport = new TServerSocket(PORT_NUMBER);
                    Communications.Processor processor = new Communications.Processor(getInstance());
                    TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));
                    logger.info("Starting server on port {}", PORT_NUMBER);
                    server.serve();
                } catch (Exception e) {
                    logger.error("Failed to start server on port {}. Exception={}", PORT_NUMBER, e.getMessage());
                    if (logger.isDebugEnabled()) e.printStackTrace();
                }
            }
        });
        communicationListener.setName("Thrift-Service-Thread");
        communicationListener.setPriority(SERVER_THREAD_PRIORITY);
        communicationListener.setDaemon(SERVER_THREAD_IS_DAEMON);
    }


    public void setOrgStructureBean(DbOrgStructureBeanLocal dbOrgStructureBeanLocal) {
        this.orgStructureBean = dbOrgStructureBeanLocal;
    }

    public static CommServer getInstance() {
        if (instance == null) instance = new CommServer();
        return instance;
    }

    public void startService() {
        communicationListener.start();
    }
}
