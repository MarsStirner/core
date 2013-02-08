package ru.korus.tmis.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;

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
    @EJB(beanName = "DbOrganizationBean")
    private DbOrganizationBeanLocal dbOrganizationBeanLocal = null;
    @EJB(beanName = "DbActionPropertyBean")
    private DbActionPropertyBeanLocal dbActionPropertyBeanLocal = null;
    @EJB(beanName = "DbQuotingByTimeBean")
    private DbQuotingByTimeBeanLocal dbQuotingByTimeBeanLocal = null;
    @EJB(beanName = "DbManagerBean")
    private DbManagerBeanLocal dbManagerBeanLocal = null;
    @EJB(beanName = "DbEventBean")
    private DbEventBeanLocal dbEventBeanLocal = null;
    @EJB(beanName = "DbActionBean")
    private DbActionBeanLocal dbActionBeanLocal = null;

    private CommServer server = null;

    @PostConstruct
    public void initialize() {
        logger.info("CommunicationServerBean starting post construct.");
        logger.debug("Link to OrgStructureBean = {}", dbOrgStructureBeanLocal);
        logger.debug("Link to PatientBean = {}", dbPatientBeanLocal);
        logger.debug("Link to OrganizationBean = {}", dbOrganizationBeanLocal);
        logger.debug("Link to ActionPropertyBean = {}", dbActionPropertyBeanLocal);
        logger.debug("Link to QuotingByTimeBean = {}", dbQuotingByTimeBeanLocal);
        try {
            server = CommServer.getInstance();
            CommServer.setOrgStructureBean(dbOrgStructureBeanLocal);
            CommServer.setPatientBean(dbPatientBeanLocal);
            CommServer.setStaffBean(dbStaffBeanLocal);
            CommServer.setSpecialityBean(dbQuotingBySpecialityBeanLocal);
            CommServer.setOrganisationBean(dbOrganizationBeanLocal);
            CommServer.setActionPropertyBean(dbActionPropertyBeanLocal);
            CommServer.setQuotingByTimeBean(dbQuotingByTimeBeanLocal);
            CommServer.setManagerBean(dbManagerBeanLocal);
            CommServer.setEventBean(dbEventBeanLocal);
            CommServer.setActionBean(dbActionBeanLocal);
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
