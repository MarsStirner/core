package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbQuotaStatus;
import ru.korus.tmis.core.exception.CoreException;
import scala.Function1;

/**
 * Класс с методами для работы с таблицей s11r64.RbQuotaStatus
 * @author mmakankov
 * @since 1.0.0.48
 * @see DbRbQuotaStatusBean
 */
public interface DbRbQuotaStatusBeanLocal {
    /**
     * Запрос на статус квоты по идентификатору.
     * @param id идентификатор записи.
     * @return
     * @throws CoreException
     */
    RbQuotaStatus getRbQuotaStatusById(int id)
            throws CoreException;

    /**
     * Запрос на справочники статусов квот.
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sortingField Поле для сортировки.
     * @param sortingMethod Метод для сортировки.
     * @param filter Фильтр значений списка.
     * @param setRecCount Делегируемый метод для перезаписи общего количества элементов по запросу.
     * @return
     * @throws CoreException
     */
    java.util.LinkedList<Object> getAllRbQuotaStatusWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter, Function1<Long, Boolean> setRecCount)
            throws CoreException;
}
