package ru.korus.tmis.vmp.service;

import ru.korus.tmis.vmp.model.AuthData;
import ru.korus.tmis.vmp.model.IdCodeNames;
import ru.korus.tmis.vmp.model.quote.QuotaDataContainer;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.04.2015, 13:59 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface QuotaService {

    IdCodeNames getQuotaType(Integer mkbId);

    IdCodeNames getPatientModel(Integer mkbId, Integer quotaTypeId);

    IdCodeNames getTreatment(Integer patientModelId);

    QuotaDataContainer saveQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData);

    QuotaDataContainer getQuota(Integer eventId);

    QuotaDataContainer getQuotaPrev(Integer eventId);

    QuotaDataContainer updateQuota(Integer eventId, QuotaDataContainer quotaData, AuthData authData);
}
