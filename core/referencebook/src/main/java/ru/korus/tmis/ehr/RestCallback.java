package ru.korus.tmis.ehr;

import ru.korus.tmis.ehr.ws.callback.BaseResponse;
import ru.korus.tmis.ehr.ws.callback.DocumentResponse;
import ru.korus.tmis.ehr.ws.callback.PatientResponse;
import ru.korus.tmis.ehr.ws.callback.RetrieveDocumentResponse;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        20.02.14, 16:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface RestCallback {
    void containerResponse(BaseResponse parameters);

    void patientQueryResponse(PatientResponse parameters);

    void documentQueryResponse(DocumentResponse parameters);

    void retrieveDocumentQueryResponse(RetrieveDocumentResponse parameters);
}
