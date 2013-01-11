package ru.korus.tmis.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.OrgStructure;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;


/**
 * User: eupatov
 * Date: 25.12.12 at 14:35
 */
@Startup
@Singleton
@LocalBean
public class CommunicationServerBean {
    final static Logger logger = LoggerFactory.getLogger(CommunicationServerBean.class);

    @EJB
    DbOrgStructureBeanLocal dbOrgStructureBeanLocal = null;

   @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    CommServer server=null;

    @PostConstruct
    public void initialize() {
        try {
            logger.info("CommunicationServerBean starting post construct.");
            logger.debug("Link to OrgStructureBean = {}", dbOrgStructureBeanLocal);
            server=CommServer.getInstance();
            server.setOrgStructureBean(dbOrgStructureBeanLocal);
            server.startService();
            logger.info("CommunicationServerBean end of post construct");
        } catch (Exception exception) {
            logger.error("exc " +exception , exception);
        }

    }

    public CommunicationServerBean() {
    }
}
