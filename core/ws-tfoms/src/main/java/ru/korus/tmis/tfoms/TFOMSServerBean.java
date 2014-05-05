package ru.korus.tmis.tfoms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbContractBeanLocal;
import ru.korus.tmis.core.database.common.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.database.common.DbOrganizationBeanLocal;
import ru.korus.tmis.core.database.common.DbPatientBeanLocal;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.*;

/**
 * User: EUpatov<br>
 * Date: 24.05.13 at 12:22<br>
 * Company: Korus Consulting IT<br>
 */
@Startup
@Singleton
@LocalBean
public class TFOMSServerBean {
    static final Logger logger = LoggerFactory.getLogger(TFOMSServerBean.class);
    private TFOMSServer server = null;

    @EJB
    //для работы со свободными переменными
    private DbRbSpecialVariablesPreferencesBeanLocal specialVariablesPreferencesBeanLocal = null;
    @EJB
    // Для работы с пациентами
    private DbPatientBeanLocal dbPatientBeanLocal = null;
    @EJB
    //для работы с организациями
    private DbOrganizationBeanLocal dbOrganizationBeanLocal = null;
    @EJB
    //для работы со счетами
    private DbAccountBeanLocal accountBeanLocal = null;
    @EJB
    //для работы с позициями  счета
    private DbAccountItemBeanLocal accountItemBeanLocal = null;
    @EJB
    //для работы с подразделениями ЛПУ
    private DbOrgStructureBeanLocal orgStructureBeanLocal = null;
    @EJB
    //Для работы с контрактами
    private DbContractBeanLocal contractBeanLocal = null;
    @EJB
    //Для работы с причинами отказа в оплате
    private DbRbPayRefuseTypeBeanLocal refuseTypeBeanLocal = null;

    @PostConstruct
    public void initialize() {
        logger.info("TFOMS ServerBean starting post construct.");
        try {
            server = TFOMSServer.getInstance();
            TFOMSServer.setSpecialPreferencesBean(specialVariablesPreferencesBeanLocal);
            ///////////////////////////////////////////////////////
            TFOMSServer.setOrganisationBean(dbOrganizationBeanLocal);
            TFOMSServer.setDbPatientBean(dbPatientBeanLocal);
            ///////////////////////////////////////////////////////
            TFOMSServer.setAccountBean(accountBeanLocal);
            TFOMSServer.setRefuseTypeBean(refuseTypeBeanLocal);
            TFOMSServer.setAccountItemBean(accountItemBeanLocal);
            TFOMSServer.setContractBean(contractBeanLocal);
            ///////////////////////////////////////////////////////
            TFOMSServer.setOrgStructureBean(orgStructureBeanLocal);

            server.startService();
        } catch (Exception exception) {
            logger.error("Exception while initialize TFOMS ServerBean", exception);
        }
        logger.info("TFOMS ServerBean end of post construct");
    }

    @PreDestroy
    public void destroy() {
        logger.warn("PreDestroy called around TFOMS ServerBean.");
        server.endWork();
    }

    public TFOMSServerBean() {
        logger.info("TFOMS ServerBean simple constructor called.");
    }

}
