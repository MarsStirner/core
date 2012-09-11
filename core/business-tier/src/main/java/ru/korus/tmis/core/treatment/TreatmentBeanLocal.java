package ru.korus.tmis.core.treatment;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.CompactRlsData;
import ru.korus.tmis.core.data.RlsData;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;
import javax.ejb.Local;

@Local
public interface TreatmentBeanLocal {

    RlsData getRlsList()
            throws CoreException;

    CompactRlsData getCompactRlsList()
            throws CoreException;

    /**
     * Получить иерархический список типов назначений по идентификатору
     * обращения.
     *
     * @param eventId  идентификатор обращения
     * @param userData информация о текущем пользователе
     *
     * @return список назначений
     */
    CommonData getTreatmentTypes(int eventId,
                                 AuthData userData)
            throws CoreException;

    /**
     * Получить иерархический список всеъ возможных типов назначений.
     *
     * @return список назначений
     */
    CommonData getAllTreatmentTypes()
            throws CoreException;

    CommonData createTreatmentForEventId(int eventId,
                                         CommonData treatment,
                                         AuthData userData)
            throws CoreException;

    CommonData modifyTreatmentById(int treatmentId,
                                   CommonData treatment,
                                   AuthData userData)
            throws CoreException;

    /**
     * Получить информацию по назначениям.
     *
     * @param eventId      идентификатор обращения
     * @param actionTypeId идентификатор типа назначения
     * @param beginDate    дата и время начала запрашиваемого интервала времени
     * @param endTime      дата и время окончания запрашиваемого интервала
     *                     времени
     *
     * @return список назначений заданного типа за интересующий интервал времени
     *         для данного обращения
     */
    CommonData getTreatmentInfo(int eventId,
                                Integer actionTypeId,
                                Date beginDate,
                                Date endTime)
            throws CoreException;

    CommonData getTreatmentById(int treatmentId)
            throws CoreException;

    CommonData verifyDrugTreatment(int eventId,
                                   int actionId,
                                   int drugId)
            throws CoreException;

    /**
     * Отменить назначение
     *
     * @param eventId  идентификатор обращения
     * @param actionId идентификатор действия типа назначение
     */
    void revokeTreatment(int eventId,
                         int actionId)
            throws CoreException;
}
