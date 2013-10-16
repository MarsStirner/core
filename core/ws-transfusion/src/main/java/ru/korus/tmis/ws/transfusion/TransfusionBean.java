package ru.korus.tmis.ws.transfusion;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService_Service;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;
import ru.korus.tmis.ws.transfusion.procedure.SendProcedureRequest;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        26.02.2013, 12:20:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Периодический опрос БД
 */
@Stateless
public class TransfusionBean {

    private static final Logger logger = LoggerFactory.getLogger(TransfusionBean.class);

    @EJB
    private SendOrderBloodComponents sendOrderBloodComponents;

    @EJB
    private SendProcedureRequest sendProcedureRequest;

    /**
     * Полинг базы данных для поиска и передачи новых запросов в систему ТРФУ
     */
    @Schedule(hour = "*", minute = "*", second = "15")
    public void pullDB() {
        try {
            logger.info("Pooling db...Trfu integration is {}", ConfigManager.TrfuProp().isActive());
            if (ConfigManager.TrfuProp().isActive() &&
                    ConfigManager.TrfuProp().ServiceUrl() != null && !"".equals(ConfigManager.TrfuProp().ServiceUrl().trim())) {

                final TransfusionMedicalService_Service service = new TransfusionMedicalService_Service();
                SecurityManager sm = System.getSecurityManager();
                System.setSecurityManager(null);
                final TransfusionMedicalService transfusionMedicalService = service.getTransfusionMedicalService();
                System.setSecurityManager(sm);
                sendOrderBloodComponents.pullDB(transfusionMedicalService);
                sendProcedureRequest.pullDB(transfusionMedicalService);
            }
        } catch (final WebServiceException ex) {
            logger.error("The TRFU service is not available. Exception description: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
