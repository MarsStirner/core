package ru.korus.tmis.tariff;

/**
 * Author: Upatov Egor <br>
 * Date: 06.08.13, 16:07 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.database.common.DbContractBeanLocal;
import ru.korus.tmis.core.entity.model.RbServiceFinance;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * User: EUpatov<br>
 * Date: 24.05.13 at 12:22<br>
 * Company: Korus Consulting IT<br>
 */
@Startup
@Singleton
@LocalBean
public class TariffServerBean {
    static final Logger logger = LoggerFactory.getLogger(TariffServerBean.class);
    private TariffServer server = null;

    @EJB
    private DbContractBeanLocal contractBeanLocal = null;
    @EJB
    private DbContractTariffBeanLocal tariffBeanLocal = null;
    @EJB
    private DbRbMedicalKindBeanLocal rbMedicalKindBeanLocal = null;
    @EJB
    private DbRbServiceFinanceBeanLocal rbServiceFinanceBeanLocal = null;
    @EJB
    private DbRbDispInfoBeanLocal rbDispInfoBeanLocal = null;
    @EJB
    private DbRbMedicalAidUnitBeanLocal rbMedicalAidUnitBeanLocal = null;
    @EJB
    private MedicalKindUnitBeanLocal medicalKindUnitBeanLocal = null;
    @EJB
    private TariffSpecificBeanLocal specificBeanLocal = null;
    @EJB
    private DbContractSpecificationBeanLocal contractSpecificationBeanLocal;
    @EJB
    private DbRbServiceUetBeanLocal rbServiceUetBeanLocal = null;


    @PostConstruct
    public void initialize() {
        logger.info("TariffServerBean starting post construct.");
        try {
            server = TariffServer.getInstance();
            ///////////////////////////////////////////////////////
            TariffServer.setRbDispInfoBean(rbDispInfoBeanLocal);
            TariffServer.setContractBean(contractBeanLocal);
            TariffServer.setTariffBean(tariffBeanLocal);
            TariffServer.setRbMedicalKindBean(rbMedicalKindBeanLocal);
            TariffServer.setRbServiceFinanceBean(rbServiceFinanceBeanLocal);
            TariffServer.setRbMedicalAidUnitBean(rbMedicalAidUnitBeanLocal);
            TariffServer.setMedicalKindUnitBean(medicalKindUnitBeanLocal);
            TariffServer.setSpecificBean(specificBeanLocal);
            TariffServer.setContractSpecificationBean(contractSpecificationBeanLocal);
            TariffServer.setRbServiceUetBean(rbServiceUetBeanLocal);
            ///////////////////////////////////////////////////////
            server.startService();
        } catch (Exception exception) {
            logger.error("Exception while initialize TariffServerBean:", exception);
        }
        logger.info("TariffServerBean end of post construct.");
    }

    @PreDestroy
    public void destroy() {
        logger.warn("PreDestroy called around TariffServerBean.");
        server.endWork();
    }

    public TariffServerBean() {
        logger.info("TariffServerBean simple constructor called.");
    }
}
