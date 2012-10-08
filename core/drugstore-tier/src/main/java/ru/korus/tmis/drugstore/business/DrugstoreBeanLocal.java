package ru.korus.tmis.drugstore.business;

import ru.korus.tmis.drugstore.data.PrescriptionInfoList;

import javax.ejb.Local;

@Local
public interface DrugstoreBeanLocal {
    void notifyActionCreated(int actionId);

    void notifyPrescriptionChanged(PrescriptionInfoList changes);
}
