package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import scala.Function1;

import javax.ejb.Local;
import java.util.List;

/**
 * Класс с методами для работы с таблицей s11r64.Client_Quoting
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbClientQuotingBean
 */

@Local
public interface DbClientQuotingBeanLocal {
    /**
     * Запрос на квоту по идентификатору.
     * @param id идентификатор записи.
     * @return возвращает квоту как ClientQuoting Entity
     * @see ClientQuoting
     * @throws CoreException
     */
    ClientQuoting getClientQuotingById(int id)
            throws CoreException;

    /**
     * Метод сохранения или редактирования в бд квоты пациента.
     * @param id идентификатор квоты.
     * @param rbQuotaTypeId тип квоты.
     * @param quotaStatusId статус квоты.
     * @param appealNumber номер обращения.
     * @param talonNumber номер талона.
     * @param stage этап.
     * @param request обращение (первичное или повторное).
     * @param mkb диагноз по МКБ.
     * @param patient пациент, для которого создаем квоту.
     * @param sessionUser пользователь, создающий квоту.
     * @return возвращает квоту как ClientQuoting
     * @see ClientQuoting
     * @throws CoreException
     */
    ClientQuoting insertOrUpdateClientQuoting(int id,
                                              int version,
                                              int rbQuotaTypeId,
                                              int quotaStatusId,
                                              int orgStructureId,
                                              String appealNumber,
                                              String talonNumber,
                                              int stage,
                                              int request,
                                              Mkb mkb,
                                              Patient patient,
                                              Event event,
                                              Staff sessionUser)
            throws CoreException;

    /**
     * Метод сохранения или редактирования в бд квоты пациента.
     * @param id идентификатор квоты.
     * @param sessionUser пользователь, создающий квоту.
     * @return возвращает квоту как ClientQuoting
     * @see ClientQuoting
     * @throws CoreException
     */
    void deleteClientQuoting(int id, Staff sessionUser)
            throws CoreException;

    /**
     * Метод получения списа квот для пациента
     * @param patientId идентификатор пациента.
     * @return возвращает список квот как List<ClientQuoting>
     * @see ClientQuoting
     * @throws CoreException
     */
    List<ClientQuoting> getAllClientQuotingForPatient (int patientId, int page, int limit, String sortingField, String sortingMethod, Object filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
