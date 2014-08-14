package ru.korus.tmis.ws.risar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ClientIdentification;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        14.08.14, 10:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Path("/api/notification")
public class RisarRestService {

    private static final Logger logger = LoggerFactory.getLogger(RisarRestService.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    private DbActionBeanLocal dbActionBean = null;

    @PUT
    @Path("/new/exam/{actionId}")
    public String newExam(@PathParam(value = "actionId") Integer actionId) throws CoreException {
        logger.info("RISAR notification. new exam with actionId: " + actionId);
        Action action = dbActionBean.getActionById(actionId);

        final Integer clientId = action.getEvent().getPatient().getId();
        logger.info("RISAR notification. MIS patient id: " + clientId);

        final List<ClientIdentification> clientIdentificationList =
                em.createNamedQuery("ClientIdentification.findByPatientAndSystem").
                        setParameter("clientId", clientId).setParameter("code", "rs").setMaxResults(100).getResultList();
        logger.info("RISAR notification. RISAR identification count: " + clientIdentificationList.size());
        for (ClientIdentification clientIdentification : clientIdentificationList) {
            logger.info("RISAR notification. RISAR identifier: " + clientIdentification.getIdentifier());
            sendExamToRisar(action, clientIdentification);
        }
        return "ok";
    }

    private void sendExamToRisar(Action action, ClientIdentification clientIdentification) {

    }
}
