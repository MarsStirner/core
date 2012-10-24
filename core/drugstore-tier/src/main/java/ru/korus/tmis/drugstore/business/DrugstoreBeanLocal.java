package ru.korus.tmis.drugstore.business;

import ru.korus.tmis.drugstore.data.PrescriptionInfoList;

import javax.ejb.Local;
import javax.jws.WebMethod;

@Local
public interface DrugstoreBeanLocal {
    void notifyActionCreated(int actionId);

    void notifyPrescriptionChanged(PrescriptionInfoList changes);
}
