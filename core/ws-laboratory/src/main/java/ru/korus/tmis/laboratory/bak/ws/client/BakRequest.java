package ru.korus.tmis.laboratory.bak.ws.client;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.DirectionBeanLocal;
import ru.korus.tmis.laboratory.bak.BakRequestService;
import ru.korus.tmis.laboratory.bak.business.BakBusinessBeanLocal;

import javax.ejb.EJB;
import javax.jws.WebService;

/**
 * @author anosov
 *         Date: 18.06.13 18:12
 */

@WebService(
  endpointInterface = "ru.korus.tmis.laboratory.bak.BakRequestService",
  targetNamespace = "http://korus.ru/tmis/client-bak",
  serviceName = "service-bak-request",
  portName = "service-bak-request",
  name = "service-bak-request")
public class BakRequest implements BakRequestService {

    @EJB
    private BakBusinessBeanLocal bakBusinessBean;

    @EJB
    private DirectionBeanLocal directionBeanLocal;

    @Override
    public void sendAnalysisRequest(int actionId) throws CoreException {
        directionBeanLocal.sendJMSLabRequest(actionId);
        //bakBusinessBean.sendLisAnalysisRequest(actionId);
    }
}
