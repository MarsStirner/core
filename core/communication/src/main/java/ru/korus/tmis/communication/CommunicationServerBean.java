package ru.korus.tmis.communication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.*;
import ru.korus.tmis.core.database.epgu.EPGUTicketBeanLocal;
import ru.korus.tmis.schedule.PatientQueueBeanLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;
import ru.korus.tmis.schedule.ScheduleBeanLocal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

/**
 * User: eupatov
 * Date: 25.12.12 at 14:35
 */
@Startup
@Singleton
@LocalBean
public class CommunicationServerBean {
    static final Logger logger = LoggerFactory.getLogger(CommunicationServerBean.class);

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
    @EJB(beanName = "DbActionBean")
    private DbActionBeanLocal dbActionBeanLocal = null;

    @EJB(beanName = "PatientQueueBean")
    private PatientQueueBeanLocal patientQueueBeanLocal = null;

    @EJB(beanName = "DbClientDocumentBean")
    private DbClientDocumentBeanLocal dbClientDocumentBeanLocal = null;
    @EJB(beanName = "DbRbDocumentTypeBean")
    private DbRbDocumentTypeBeanLocal dbRbDocumentTypeBeanLocal = null;

    @EJB(beanName = "DbClientPolicyBean")
    private DbClientPolicyBeanLocal dbClientPolicyBeanLocal = null;
    @EJB(beanName = "DbRbPolicyTypeBean")
    private DbRbPolicyTypeBeanLocal dbRbPolicyTypeBeanLocal = null;

    @EJB(beanName = "EPGUTicketBean")
    private EPGUTicketBeanLocal ticketBeanLocal = null;

    @EJB(beanName = "PersonScheduleBean")
    private PersonScheduleBeanLocal personScheduleBeanLocal = null;

    @EJB(beanName = "ScheduleBean")
    private ScheduleBeanLocal scheduleBeanLocal = null;


    private CommunicationServer communicationServer = null;

    @PostConstruct
    public void initialize() {
        logger.info("CommunicationServerBean starting post construct.");
        CommunicationHelper.setQuotingTypeList(scheduleBeanLocal.getQuotingTypeList());
        try {
            communicationServer = CommunicationServer.getInstance();
            CommunicationServer.setOrgStructureBean(dbOrgStructureBeanLocal);
            CommunicationServer.setPatientBean(dbPatientBeanLocal);
            CommunicationServer.setStaffBean(dbStaffBeanLocal);
            CommunicationServer.setSpecialityBean(dbQuotingBySpecialityBeanLocal);
            CommunicationServer.setOrganisationBean(dbOrganizationBeanLocal);
            CommunicationServer.setPatientQueueBean(patientQueueBeanLocal);
            ////////////////////////////////////////////////////////////
            CommunicationServer.setDocumentBean(dbClientDocumentBeanLocal);
            CommunicationServer.setDocumentTypeBean(dbRbDocumentTypeBeanLocal);
            CommunicationServer.setPolicyBean(dbClientPolicyBeanLocal);
            CommunicationServer.setPolicyTypeBean(dbRbPolicyTypeBeanLocal);
            CommunicationServer.setQueueTicketBean(ticketBeanLocal);
            ////////////////////////////////////////////////////////////
            CommunicationServer.setScheduleBean(scheduleBeanLocal);
            communicationServer.startService();
        } catch (Exception exception) {
            logger.error("Exception while initialize CommunicationBean", exception);
        }
        logger.info("CommunicationServerBean end of post construct");
    }



    @PreDestroy
    public void destroy() {
        logger.warn("PreDestroy called around CommunicationServerBean.");
        communicationServer.endWork();
    }


    public CommunicationServerBean() {
        logger.info("CommunicationServerBean simple constructor called.");
    }
}
