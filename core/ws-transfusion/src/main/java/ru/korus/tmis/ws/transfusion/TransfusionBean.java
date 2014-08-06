package ru.korus.tmis.ws.transfusion;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ru.korus.tmis.core.notification.NotificationBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;
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
@Singleton
@Controller
@RequestMapping("/")
public class TransfusionBean {

    public static final String MODULE_PATH = "tmis-ws-transfusion/transfusion";

    private static final Logger logger = LoggerFactory.getLogger(TransfusionBean.class);

    @EJB
    private SendOrderBloodComponents sendOrderBloodComponents;

    @EJB
    private SendProcedureRequest sendProcedureRequest;

    @EJB
    private NotificationBeanLocal notificationBeanLocal;

    @PostConstruct
    void init(){
        notificationBeanLocal.addListener("TransfusionTherapy", MODULE_PATH);
    }
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
        } catch (final Exception ex) {
            logger.error("General exception in trfu integration: {}", ex.getMessage());
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public String getMovie(@PathVariable String name) {
        return "hello:" + name;
    }
}
