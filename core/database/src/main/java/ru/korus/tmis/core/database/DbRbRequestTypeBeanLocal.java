package ru.korus.tmis.core.database;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;
import javax.ejb.Local;

/**
 * Методы для работы с таблицей s11r64.rbFinance.
 * @author Ivan Dmitriev
 * @since 1.0.0.45
 */
@Local
public interface DbRbRequestTypeBeanLocal {

    /**
     * Запрос на справочники типов обращений.
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sorting для сортировки.
     * @param filter Фильтр значений списка.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return
     * @throws CoreException
     */
    java.util.LinkedList<Object> getAllRbRequestTypesWithFilter(int page, int limit, String sorting, ListDataFilter filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
