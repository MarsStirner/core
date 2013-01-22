package ru.korus.tmis.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.database.DbPatientBeanLocal;
import ru.korus.tmis.core.database.DbQuotingBySpecialityBeanLocal;
import ru.korus.tmis.core.database.DbStaffBeanLocal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * User: eupatov
 * Date: 25.12.12 at 14:35
 */
@Startup
@Singleton
@LocalBean
public class CommunicationServerBean {
    final static Logger logger = LoggerFactory.getLogger(CommunicationServerBean.class);
    @EJB(beanName = "DbOrgStructureBean")
    private DbOrgStructureBeanLocal dbOrgStructureBeanLocal = null;
    @EJB(beanName = "DbPatientBean")
    private DbPatientBeanLocal dbPatientBeanLocal = null;
    @EJB(beanName = "DbStaffBean")
    private DbStaffBeanLocal dbStaffBeanLocal = null;
    @EJB(beanName = "DbQuotingBySpecialityBean")
    private DbQuotingBySpecialityBeanLocal dbQuotingBySpecialityBeanLocal = null;

    private CommServer server = null;

    @PostConstruct
    public void initialize() {
        logger.info("CommunicationServerBean starting post construct.");
        logger.debug("Link to OrgStructureBean = {}", dbOrgStructureBeanLocal);
        logger.debug("Link to PatientBean = {}", dbPatientBeanLocal);
        try {
            server = CommServer.getInstance();
            CommServer.setOrgStructureBean(dbOrgStructureBeanLocal);
            CommServer.setPatientBean(dbPatientBeanLocal);
            CommServer.setStaffBean(dbStaffBeanLocal);
            CommServer.setSpecialityBean(dbQuotingBySpecialityBeanLocal);
            server.startService();
        } catch (Exception exception) {
            logger.error("Exception while initialize CommunicationBean", exception);
        }
        logger.info("CommunicationServerBean end of post construct");
    }

    @PreDestroy
    public void destroy() {
        logger.warn("PreDestroy called around CommunicationServerBean.");
        server.endWork();
    }


    public CommunicationServerBean() {
        logger.info("CommunicationServerBean simple constructor called.");
    }
}
