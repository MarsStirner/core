package ru.korus.tmis.ws.drugstore;


import ru.korus.tmis.drugstore.data.PrescriptionInfoList;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        targetNamespace = "http://korus.ru/tmis/drugstore",
        name = "drugstore"
)
public interface DrugstoreWebService {
    @WebMethod
    void notifyActionCreated(int actionId);

    @WebMethod
    void notifyPrescriptionChanged(PrescriptionInfoList changes);
}
