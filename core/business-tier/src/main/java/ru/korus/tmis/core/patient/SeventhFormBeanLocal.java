package ru.korus.tmis.core.patient;

import javax.ejb.Local;
import ru.korus.tmis.core.data.FormOfAccountingMovementOfPatientsData;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;

@Local
public interface SeventhFormBeanLocal {

    /**
     * Форма 007
     * Спецификация: https://docs.google.com/document/d/1a0AYF8QVpEMl_pKRcFDnP2vQzRmO-IkcG5JNStEcjMI/edit#heading=h.a2hialy1qshb
     * Примечание: Если не задано beginDate и endDate, то берутся предыдущие мед.сутки (вчера 8:00 - сегодня 7:59),
     * если задано beginDate, то endDate считается как: beginDate плюс сутки минус 1 минута,
     * если задано endDate, то beginDate считается как: endDate минус сутки плюс 1 минута.
     * @param departmentId Идентификатор отделения.
     * @param beginDate Дата начала выборки.
     * @param endDate Дата окончания выборки.
     * @return Форма 007 как FormOfAccountingMovementOfPatientsData
     * @throws CoreException
     */
    FormOfAccountingMovementOfPatientsData getForm007LinearView(int departmentId,
                                                                long beginDate,
                                                                long endDate,
                                                                java.util.List<Integer> profileBeds) throws CoreException;
}
