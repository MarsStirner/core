package ru.korus.tmis.ws.laboratory.bak.ws.client.bean;


import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface BakLaboratoryService {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest(int actionId) throws CoreException;
}
