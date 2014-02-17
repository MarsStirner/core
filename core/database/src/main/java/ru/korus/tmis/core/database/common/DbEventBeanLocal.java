package ru.korus.tmis.core.database.common;

import com.google.common.collect.Multimap;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.*;
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
     *
     * @param id Идентификатор обращения.
     * @return Данные о обращении как Event entity
     * @throws CoreException
     * @see Event
     */
    Event getEventById(int id) throws CoreException;

    /**
     * Получение действий для обращения по списку flatCodes
     * @param event
     * @param codes
     * @return
     * @throws CoreException
     */
    Multimap<String, Action> getActionsByTypeCode(Event event, Set<String> codes)
            throws CoreException;

    void setExecPersonForEventWithId(int eventId, Staff execPerson) throws CoreException;

    Set<ActionType> getActionTypeFilter(int eventId) throws CoreException;

    OrgStructure getOrgStructureForEvent(int eventId) throws CoreException;

    Event createEvent(int patientId, int appealTypeId, Date begDate, Date endDate, int contractId, AuthData authData) throws CoreException;

    Event createEvent(Patient patient, EventType eventType, Staff person, Date begDate, Date endDate) throws CoreException;

    EventType getEventTypeById(int eventTypeId) throws CoreException;

    List<Event> getEventsForPatient(int patientId) throws CoreException;

    List<Event> getEventsForPatientWithExistsActionByType(int patientId, String code) throws CoreException;

    int getEventTypeIdByFDRecordId(int fdRecordId) throws CoreException;

    /**
     * Запрос на справочники типов обращений.
     *
     * @param page          Выводимая страница.
     * @param limit         Максимальное количество типов оплаты в выводимом списке.
     * @param sortingField  Поле для сортировки.
     * @param sortingMethod Метод для сортировки.
     * @param filter        Фильтр значений списка.
     * @param setRecCount   Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return Список типов обращений как java.util.LinkedList[EventType]
     * @throws CoreException
     * @see EventType
     */
    java.util.List<EventType> getEventTypesByRequestTypeIdAndFinanceId(int page, int limit, String sortingField, String sortingMethod, Object filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;

    EventType getEventTypeByCode(String code)
            throws CoreException;

    /**
     * Запрос на список поступивших за период (список госпитализаций)
     * @param page Фильтр номера выводимой страницы.
     * @param limit Максимальное количество выводим записей.
     * @param sortingField  Наименование поля для сортировки.
     * @param sortingMethod Метод сортировки.
     * @param filter Фильтр выводимых значений как Object
     * @return Отсортированный и отфильтрованный список обращений на госпитализацию за период.
     * @throws CoreException
     * @since 1.0.0.43
     */
    List<Event> getAllAppealsForReceivedPatientByPeriod(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    /**
     * Метод возвращает количество обращений.
     * @param filter Фильтр значений возвращаемого списка как Object.
     * @return Количество обращений.
     * @throws CoreException
     */
    long getCountOfAppealsForReceivedPatientByPeriod (Object filter) throws CoreException;
}
