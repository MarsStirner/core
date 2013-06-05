package ru.korus.tmis.laboratory.bak.bean;


import javax.ejb.Local;

@Local
public interface BakLaboratoryBeanLocal {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest();
}
