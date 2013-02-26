package ru.korus.tmis.ws.transfusion;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.xml.ws.WebServiceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService_Service;
import ru.korus.tmis.ws.transfusion.order.RegOrderIssueResult;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;
import ru.korus.tmis.ws.transfusion.order.TrfuPullable;
import ru.korus.tmis.ws.transfusion.procedure.SendProcedureRequest;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        26.02.2013, 12:20:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@Stateless
public class TransfusionBean {

    private static final Logger logger = LoggerFactory.getLogger(TransfusionBean.class);

    @EJB
    private RegOrderIssueResult tmp;

    @EJB
    private SendOrderBloodComponents sendOrderBloodComponents;

    @EJB
    private SendProcedureRequest sendProcedureRequest;

    /**
     * Полинг базы данных для поиска и передачи новых запросов в систему ТРФУ
     */
    @Schedule(hour = "*", minute = "*")
    public void pullDB() {
        try {
            final TransfusionMedicalService_Service service = new TransfusionMedicalService_Service();
            final TransfusionMedicalService transfusionMedicalService = service.getTransfusionMedicalService();
            sendOrderBloodComponents.pullDB(transfusionMedicalService);
            sendProcedureRequest.pullDB(transfusionMedicalService);

        } catch (final WebServiceException ex) {
            logger.error("The TRFU service is not available. Exception description: {}", ex.getMessage());
            ex.printStackTrace();
        }
    }
}
