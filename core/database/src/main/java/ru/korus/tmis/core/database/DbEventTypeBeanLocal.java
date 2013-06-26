package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

import javax.ejb.Local;

/**
 * Методы для работы с таблицей s11r64.EventType
 * @author idmitriev Systema-Soft
 */
@Local
public interface DbEventTypeBeanLocal {

    /**
     * Запрос на тип обращения по идентификатору
     * @param eventTypeId  Идентификатор типа обращения.
     * @return Тип обращения как entity EventType
     * @throws CoreException
     */
    EventType getEventTypeById(int eventTypeId) throws CoreException;

    /**
     * Запрос на справочники типов обращений.
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sorting Поле для сортировки.
     * @param filter Фильтр значений списка как ListDataFilter.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return Список типов обращений как java.util.LinkedList[EventType]
     * @throws ru.korus.tmis.core.exception.CoreException
     * @see ru.korus.tmis.core.entity.model.EventType
     */
    java.util.List<EventType> getEventTypesByRequestTypeIdAndFinanceId(int page, int limit, String sorting, ListDataFilter filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
