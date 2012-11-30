package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;
import scala.Function1;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Методы для работы с таблицей s11r64.Event
 */
@Local
public interface DbEventBeanLocal {

    /**
     * Получение данных о обращении по идентификатору
     * @param id Идентификатор обращения.
     * @return Данные о обращении как Event entity
     * @throws CoreException
     * @see Event
     */
    Event getEventById(int id)
            throws CoreException;

    Set<ActionType> getActionTypeFilter(int eventId)
            throws CoreException;

    OrgStructure getOrgStructureForEvent(int eventId)
            throws CoreException;

    Event createEvent(int patientId, int appealTypeId, Date begDate, Date endDate, AuthData authData)
            throws CoreException;


    EventType getEventTypeById(int eventTypeId)
            throws CoreException;

    List<Event> getEventsForPatient(int patientId)
            throws CoreException;

    List<Event> getEventsForPatientWithExistsActionByType(int patientId, String code)
            throws CoreException;

    int getEventTypeIdByFDRecordId(int fdRecordId)
            throws CoreException;

    /**
     * Запрос на справочники типов обращений.
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sortingField Поле для сортировки.
     * @param sortingMethod Метод для сортировки.
     * @param filter Фильтр значений списка.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return Список типов обращений как java.util.LinkedList[EventType]
     * @throws CoreException
     * @see EventType
     */
    java.util.List<EventType> getEventTypesByRequestTypeIdAndFinanceId(int page, int limit, String sortingField, String sortingMethod, Object filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
