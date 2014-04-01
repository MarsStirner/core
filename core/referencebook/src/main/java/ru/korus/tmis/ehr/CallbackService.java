package ru.korus.tmis.ehr;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.ehr.ws.callback.BaseResponse;
import ru.korus.tmis.ehr.ws.callback.DocumentResponse;
import ru.korus.tmis.ehr.ws.callback.PatientResponse;
import ru.korus.tmis.ehr.ws.callback.RetrieveDocumentResponse;

import javax.annotation.Resource;
import javax.jws.HandlerChain;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.02.14, 15:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@WebService(serviceName = "CallbackService", portName = "CallbackServiceSoap", endpointInterface = "ru.korus.tmis.ehr.ws.callback.CallbackServiceSoap", targetNamespace = "urn:wsdl", wsdlLocation = "WEB-INF/wsdl/CallbackService.wsdl")
@HandlerChain(file = "handlers.xml")
public class CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(CallbackService.class);

    private static Map<String, RestCallback> restCallbackMap = new HashMap<String, RestCallback>();

    @Resource
    private WebServiceContext context;

    public void containerResponse(BaseResponse parameters) {
        logger.info("CallbackService.containerResponse: parameters = " + (new Gson()).toJson(parameters));
        final String messageId = (String) context.getMessageContext().get(CallbackHandler.RELATES_TO);
        logger.info("CallbackService.patientQueryResponse: messageId = " + messageId);
        if (restCallbackMap.get(messageId) != null) {
            restCallbackMap.get(messageId).containerResponse(parameters);
            restCallbackMap.remove(messageId);
        }
    }

    public void patientQueryResponse(PatientResponse parameters) {
        logger.info("CallbackService.patientQueryResponse: parameters = " + (new Gson()).toJson(parameters));
        final String messageId = (String) context.getMessageContext().get(CallbackHandler.RELATES_TO);
        logger.info("CallbackService.patientQueryResponse: messageId = " + messageId);
        if (restCallbackMap.get(messageId) != null) {
            restCallbackMap.get(messageId).patientQueryResponse(parameters);
            restCallbackMap.remove(messageId);
        }
    }

    public void documentQueryResponse(DocumentResponse parameters) {
        logger.info("CallbackService.documentQueryResponse: parameters = " + (new Gson()).toJson(parameters));
        final String messageId = (String) context.getMessageContext().get(CallbackHandler.RELATES_TO);
        logger.info("CallbackService.documentQueryResponse: messageId = " + messageId);
        if (restCallbackMap.get(messageId) != null) {
            restCallbackMap.get(messageId).documentQueryResponse(parameters);
            restCallbackMap.remove(messageId);
        }
    }

    public void retrieveDocumentQueryResponse(RetrieveDocumentResponse parameters) {
        logger.info("CallbackService.retrieveDocumentQueryResponse: parameters = " + (new Gson()).toJson(parameters));
        final String messageId = (String) context.getMessageContext().get(CallbackHandler.RELATES_TO);
        logger.info("CallbackService.retrieveDocumentQueryResponse: messageId = " + messageId);
        if (restCallbackMap.get(messageId) != null) {
            restCallbackMap.get(messageId).retrieveDocumentQueryResponse(parameters);
            restCallbackMap.remove(messageId);
        }
    }

    public static void setRestCallback(RestCallback restCallback, String messageId) {
        CallbackService.restCallbackMap.put("urn:uuid:" + messageId, restCallback);
    }

}
