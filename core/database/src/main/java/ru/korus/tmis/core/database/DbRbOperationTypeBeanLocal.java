package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbOperationType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

import javax.ejb.Local;

/**
 * Методы для работы с таблицей s11r64.rbOperationType.
 * @author Ivan Dmitriev
 * @since 1.0.1.19
 */
@Local
public interface DbRbOperationTypeBeanLocal {

    /**
     * Запрос на справочники типов операций.
     * @param page Выводимая страница.
     * @param limit Максимальное количество в выводимом списке.
     * @param sorting для сортировки.
     * @param filter Фильтр значений списка.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    java.util.LinkedList<Object> getAllRbOperationTypeWithFilter(int page,
                                                                 int limit,
                                                                 String sorting,
                                                                 ListDataFilter filter,
                                                                 Function1<Long, Boolean> setRecCount) throws CoreException;
}
