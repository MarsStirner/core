package ru.korus.tmis.laboratory.bulk.bean;


import javax.ejb.Local;

@Local
public interface BulkLaboratoryBeanLocal {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest();
}
