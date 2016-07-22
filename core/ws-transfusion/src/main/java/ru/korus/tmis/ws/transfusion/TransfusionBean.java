package ru.korus.tmis.ws.transfusion;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbTrfuBloodComponentType;
import ru.korus.tmis.core.notification.NotificationBeanLocal;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.ws.transfusion.efive.ComponentType;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService_Service;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

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
@Path("/transfusion")
public class TransfusionBean {

    public static final String MODULE_PATH = "tmis-ws-transfusion/transfusion";

    private static final Logger log = LoggerFactory.getLogger(TransfusionBean.class);

    @EJB
    private SendOrderBloodComponents sendOrderBloodComponents;

    @EJB
    private NotificationBeanLocal notificationBeanLocal;

    @EJB
    private DbActionBeanLocal dbAction;


    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    private static List<RbTrfuBloodComponentType> convertToDb(final List<ComponentType> compBloodTypesTrfu) {
        final List<RbTrfuBloodComponentType> res = new LinkedList<>();
        for (final ComponentType compTrfu : compBloodTypesTrfu) {
            final RbTrfuBloodComponentType compDb = new RbTrfuBloodComponentType();
            compDb.setTrfuId(compTrfu.getId());
            compDb.setCode(compTrfu.getCode());
            compDb.setName(compTrfu.getValue());
            compDb.setUnused(false);
            res.add(compDb);
        }
        return res;
    }

    @PostConstruct
    void init() {
        notificationBeanLocal.addListener("TransfusionTherapy", MODULE_PATH);
    }

    /**
     * Полинг базы данных для поиска и передачи новых запросов в систему ТРФУ
     */
    //@Schedule(hour = "*", minute = "*/3", second = "15", persistent = false)
    public void pullDB() {
        log.info("Pooling db. Integration is {}, address = \'{}\'", ConfigManager.TrfuProp().isActive(), ConfigManager.TrfuProp().ServiceUrl());
        if (ConfigManager.TrfuProp().isActive() && StringUtils.isNotEmpty(ConfigManager.TrfuProp().ServiceUrl())) {
            try {
                final TransfusionMedicalService service = getWebService();
                if(service != null) {
                    log.debug("Service initialized : [{}]", service);
                    updateBloodCompTable(service);
                    sendOrderBloodComponents.pullDB(service);
                    //sendProcedureRequest.pullDB(service);
                } else {
                    log.error("Service is not initialized. Abort current pulling");
                }
            } catch (final Exception ex) {
                log.error("General exception in trfu integration: {}", ex);
            }
        }
    }

    private void updateBloodCompTable(final TransfusionMedicalService service) {
        log.info("Start sync BloodComponentTypes...");
        final List<RbTrfuBloodComponentType> localComponentTypeList = em.createNamedQuery(
                "rbTrfuBloodComponentType.findAll", RbTrfuBloodComponentType.class
        ).getResultList();
        final List<ComponentType> remoteComponentTypeList = service.getComponentTypes();
        log.info(
                "BloodComponentTypes received from TRFU. Count: {} [local = {}]", remoteComponentTypeList.size(), localComponentTypeList.size()
        );
        final List<RbTrfuBloodComponentType> remoteConvertedComponentTypeList = convertToDb(remoteComponentTypeList);
        for (final RbTrfuBloodComponentType local : localComponentTypeList) {
            if (remoteConvertedComponentTypeList.remove(local)) {
                local.setUnused(false);
            } else {
                local.setUnused(true);
                log.info("The blood components unused in TRFU. The component: {}", local);
            }
        }
        for (final RbTrfuBloodComponentType remoteUnsaved : remoteConvertedComponentTypeList) {
            log.info("Add new from remote: {} ", remoteUnsaved);
            em.persist(remoteUnsaved);
        }
        log.info("End sync BloodComponentTypes");
        em.flush();
    }

    @GET
    @Produces("application/javascript")
    public Response sendActionById(
            @QueryParam("actionId") int actionId
    ) {
        log.info("Call REST WS with actionId = {}", actionId);
        final Action action = dbAction.getById(actionId);
        if(action == null){
            log.error("Action[{}] not found", actionId);
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        int result = sendOrderBloodComponents.sendOrderBloodComponent(action, getWebService());
        log.info("End REST WS with actionId = {}. Result is {}", actionId, result);
        return Response.ok().entity("{\"result\"="+result+"}").build();
    }


    private TransfusionMedicalService getWebService() {
        try {
            return new TransfusionMedicalService_Service(new URL(ConfigManager.TrfuProp().ServiceUrl())).getTransfusionMedicalService();
        } catch (final WebServiceException ex) {
            log.error("The TRFU service is not available. Exception description: {}", ex.getMessage());
            return null;
        } catch (MalformedURLException ex) {
            log.error(
                    "The TRFU service is not available. URL from TrfuProp is invalid [\'{}\']. Full Exception description: {}",
                    ConfigManager.TrfuProp().ServiceUrl(),
                    ex.getMessage()
            );
            return null;
        }
    }


}
