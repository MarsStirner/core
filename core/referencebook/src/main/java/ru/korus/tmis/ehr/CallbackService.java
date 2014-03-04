package ru.korus.tmis.ehr;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import ru.korus.tmis.ehr.ws.callback.BaseResponse;
import ru.korus.tmis.ehr.ws.callback.DocumentResponse;
import ru.korus.tmis.ehr.ws.callback.PatientResponse;
import ru.korus.tmis.ehr.ws.callback.RetrieveDocumentResponse;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.02.14, 15:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@WebService(serviceName = "CallbackService", portName = "CallbackServiceSoap", endpointInterface = "ru.korus.tmis.ehr.ws.callback.CallbackServiceSoap", targetNamespace = "urn:wsdl", wsdlLocation = "WEB-INF/wsdl/CallbackService.wsdl")
public class CallbackService {

    private static final Logger logger = LoggerFactory.getLogger(CallbackService.class);

    private static RestCallback restCallback = null;

    @Resource
    private WebServiceContext context;

    public void containerResponse(BaseResponse parameters) {
        logger.info("CallbackService.containerResponse: parameters = " + (new Gson()).toJson(parameters) );
        List<Element> list =
                (List<Element>) context.getMessageContext().get(MessageContext.REFERENCE_PARAMETERS);
        if (restCallback != null) {
            restCallback.containerResponse(parameters);
        }
    }

    public void patientQueryResponse(PatientResponse parameters) {
        logger.info("CallbackService.patientQueryResponse: parameters = " + (new Gson()).toJson(parameters) );
        List<Element> list =
                (List<Element>) context.getMessageContext().get(MessageContext.REFERENCE_PARAMETERS);
        if (restCallback != null) {
            restCallback.patientQueryResponse(parameters);
        }
    }

    public void documentQueryResponse(DocumentResponse parameters) {
        logger.info("CallbackService.documentQueryResponse: parameters = " + (new Gson()).toJson(parameters) );
        if (restCallback != null) {
            restCallback.documentQueryResponse(parameters);
        }
    }

    public void retrieveDocumentQueryResponse(RetrieveDocumentResponse parameters) {
        logger.info("CallbackService.retrieveDocumentQueryResponse: parameters = " + (new Gson()).toJson(parameters) );
        if (restCallback != null) {
            restCallback.retrieveDocumentQueryResponse(parameters);
        }
    }

    public static void setRestCallback(RestCallback restCallback) {
        CallbackService.restCallback = restCallback;
    }


}
