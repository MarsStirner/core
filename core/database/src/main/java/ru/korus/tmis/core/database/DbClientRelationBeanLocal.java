package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.ClientContactContainer;
import ru.korus.tmis.core.entity.model.ClientRelation;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Методы для работы с таблицей s11r64.ClientRelation
 */
@Local
public interface DbClientRelationBeanLocal {
    /**
     * Получение всех связей пациента
     *
     * @param patientId Идентификатор пациента
     * @return Коллекцию связе пациента как Iterable[ClientRelation]
     * @see ClientRelation
     */
    List<ClientRelation> getAllClientRelations(int patientId);

    /**
     * Получение связи пациента по идентификатору
     *
     * @param id Идентификатор ClientRelation.id
     * @return Объект как ClientRelation
     * @throws CoreException
     * @see ClientRelation
     */
    ClientRelation getClientRelationById(int id) throws CoreException;

    /**
     * Получение связи пациента по идентификатору того, с кем связан.
     *
     * @param id Идентификатор того, с кем связан.
     * @return Объект как ClientRelation
     * @throws CoreException
     * @see ClientRelation
     */
    ClientRelation getClientRelationByRelativeId(int id) throws CoreException;

    /**
     * Создание/редактирование связи клиента с созданием/редактированием данных о том, с кем связан.
     *
     * @param id               Идентификатор связи.
     * @param rbRelationTypeId Идентификатор типа связи.
     * @param firstName        Имя с кем связан.
     * @param lastName         Фамилия с кем связан.
     * @param middleName       Отчество с кем связан.
     * @param contacts         Список контактов в виде коллекции ClientContactContainer
     * @param patient          Пациент
     * @param sessionUser      Авторизационные данные создающего запись.
     * @return Объект как ClientRelation
     * @throws CoreException
     * @see ClientRelation
     */
    ClientRelation insertOrUpdateClientRelation(
            int id,
            int rbRelationTypeId,
            String firstName,
            String lastName,
            String middleName,
            Iterable<ClientContactContainer> contacts,
            Patient patient,
            Staff sessionUser)
            throws CoreException;

    /**
     * Созданиесвязи клиента без создания/редактирования данных о том, с кем связан.
     *
     * @param id               Идентификатор связи.
     * @param rbRelationTypeId Идентификатор типа связи.
     * @param relative         С кем связан.
     * @param patient          Пациент.
     * @param sessionUser      Авторизационные данные создающего запись.
     * @return Объект как ClientRelation
     * @throws CoreException
     * @see ClientRelation
     */
    ClientRelation createClientRelationByRelativePerson(int rbRelationTypeId,
                                                        Patient relative,
                                                        Patient patient,
                                                        Staff sessionUser) throws CoreException;

    /**
     * Удаление связи пациента.
     *
     * @param id          Идентификатор связи.
     * @param sessionUser Авторизационные данные создающего запись.
     * @throws CoreException
     */
    void deleteClientRelation(int id,
                              Staff sessionUser)
            throws CoreException;

}
