package ru.korus.tmis.ws.laboratory.bak.ws.client;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.laboratory.bak.ws.client.bean.BakLaboratoryService;

import javax.ejb.EJB;
import javax.jws.WebService;

/**
 * @author anosov
 *         Date: 18.06.13 18:12
 */

@WebService(
  endpointInterface = "ru.korus.tmis.ws.laboratory.bak.ws.client.BakClientWS",
  targetNamespace = "http://korus.ru/tmis/client-bak",
  serviceName = "tmis-client-bak",
  portName = "client-bak",
  name = "client-bak")
public class BakClient implements BakClientWS {

    @EJB
    BakLaboratoryService bakLaboratoryBean;

    @Override
    public void sendAnalysisRequest(int actionId) throws CoreException {
        bakLaboratoryBean.sendLisAnalysisRequest(actionId);
    }
}
