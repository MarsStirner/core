package ru.korus.tmis.laboratory.bak.ws.client;


import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface BakLaboratoryService {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest(int actionId) throws CoreException;
}
