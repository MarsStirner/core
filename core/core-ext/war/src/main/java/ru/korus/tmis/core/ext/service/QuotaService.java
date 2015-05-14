package ru.korus.tmis.core.ext.service;

import ru.korus.tmis.core.ext.model.AuthData;
import ru.korus.tmis.core.ext.model.IdCodeNames;
import ru.korus.tmis.core.ext.model.quote.QuotaDataContainer;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 13:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface QuotaService {

    IdCodeNames getQuotaType(Integer mkbId, Integer eventId);

    IdCodeNames getPatientModel(Integer mkbId, Integer quotaTypeId);

    IdCodeNames getTreatment(Integer patientModelId);

    QuotaDataContainer saveQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData);

    QuotaDataContainer getQuota(Integer eventId);

    QuotaDataContainer getQuotaPrev(Integer eventId);

    QuotaDataContainer updateQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData);
}
