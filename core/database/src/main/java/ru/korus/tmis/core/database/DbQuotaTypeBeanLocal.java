package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.QuotaType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

import javax.ejb.Local;

/**
 * Класс с методами для работы с таблицей s11r64.QuotaType
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbQuotaTypeBean
 */
@Local
public interface DbQuotaTypeBeanLocal {
    /**
     * Запрос на тип квоты по идентификатору.
     * @param id идентификатор записи.
     * @return
     * @throws CoreException
     */
    QuotaType getQuotaTypeById(int id)
            throws CoreException;

    /**
     * Запрос на тип квоты по коду.
     * @param code уникальный код записи.
     * @return
     * @throws CoreException
     */
    QuotaType getQuotaTypeByCode(String code) throws CoreException;

    /**
     * Запрос на справочники типов квот.
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sorting Для сортировки.
     * @param filter Фильтр значений списка.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return
     * @throws CoreException
     */
    java.util.List<QuotaType> getAllQuotaTypesWithFilter(int page, int limit, String sorting, ListDataFilter filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
