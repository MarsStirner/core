package ru.korus.tmis.laboratory.bak.business;


import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface BakBusinessBeanLocal {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest(int actionId) throws CoreException;
}
