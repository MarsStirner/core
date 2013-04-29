package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.BloodHistory;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Сервисы для работы с BloodHistory
 * Спецификация: https://docs.google.com/document/d/1y3qprunilr88wDMQbyT4TVY1GgNr9qAwkVerGd5NLQI
 * Author: idmitriev Systema-Soft
 * Date: 4/11/13 11:12 PM
 * Since: 1.0.1.1
 */
@Local
public interface DbBloodHistoryBeanLocal {

    /**
     * Метод для получения списка записей изменения группы крови у пациента
     * @param patientId Идентификатор пациента
     * @return List<BloodHistory>
     * @throws CoreException
     */
    List<BloodHistory> getBloodHistoryByPatient(int patientId) throws CoreException;

    /**
     * Создание записи о группе крови для пациента
     * @param patientId Идентификатор пациента
     * @param bloodTypeId Идентификатор группы крови
     * @param userData Авторизационные данные врача
     * @return BloodHistory
     * @throws CoreException
     */
    BloodHistory createBloodHistoryRecord(int patientId, int bloodTypeId, Date bloodDate, AuthData userData) throws CoreException;
}
