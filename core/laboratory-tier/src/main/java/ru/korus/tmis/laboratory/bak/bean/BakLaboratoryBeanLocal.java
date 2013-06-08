package ru.korus.tmis.laboratory.bak.bean;


import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface BakLaboratoryBeanLocal {

    /**
     * Отправить в ЛИС запрос анализа
     */
    void sendLisAnalysisRequest() throws CoreException;
}
