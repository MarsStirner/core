package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.DepartmentsDataFilter;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.OrgStructureAddress;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.AbstractListDataFilter;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface DbOrgStructureBeanLocal {
    /**
     * Запрос на отделение по идентификатору.
     *
     * @param id идентификатор записи.
     * @return Отделение как OrgStructure
     * @throws CoreException
     * @see OrgStructure
     */
    OrgStructure getOrgStructureById(int id)
            throws CoreException;

    /**
     * Запрос на список всех отделений.
     *
     * @return список отделений как List<OrgStructure>
     * @throws CoreException
     */
    List<OrgStructure> getAllOrgStructures();

    /**
     * Запрос на список всех отделений с параметрами.
     *
     * @param page          Выводимая страница.
     * @param limit         Максимальное количество типов оплаты в выводимом списке.
     * @param sorting  Для сортировки.
     * @param filter        Фильтр значений списка.
     * @return Список отделений как List<OrgStructure>
     * @throws CoreException
     */
    List<OrgStructure> getAllOrgStructuresByRequest(int limit,
                                                    int page,
                                                    String sorting,
                                                    ListDataFilter filter)
            throws CoreException;

    /**
     * Запрос на колличество отделений.
     *
     * @param filter Фильтр значений списка.
     * @return колличество отделений как long
     * @throws CoreException
     */
    long getCountAllOrgStructuresWithFilter(Object filter)
            throws CoreException;

    /**
     * Запрос на список типов действий по отделению
     *
     * @param departmentId Идентификатор отделения.
     * @return Список типов действии отделения как Set<ActionType>
     * @throws CoreException
     */
    Set<ActionType> getActionTypeFilter(int departmentId)
            throws CoreException;

    /**
     * Запрос на отделение по койке.
     *
     * @param bedId Идентификатор койки.
     * @return Отделение как OrgStructure
     * @throws CoreException
     */
    OrgStructure getOrgStructureByHospitalBedId(int bedId) throws CoreException;

    /**
     * Запрос на получение под-отделений начиная с указаного parentId.
     *
     * @param parentId  Идентификатор отделения для которго выводим все подотделения
     * @param recursive рекурсивно (выбираем все под-отделения, входящие в одно из выбраных под-отделений)
     * @param infisCode Принадлежащие одной структуре
     * @return Список под-отделений
     * @throws CoreException Если не найдено ни одной оргструктуры
     */
    List<OrgStructure> getRecursiveOrgStructures(int parentId, boolean recursive, String infisCode) throws CoreException;

    /**
     * Запрос на получение ИД оргструктур по заданному адресу
     *
     * @return Список ИД оргутруктур удовлятворяющих заданному адресу
     * @throws CoreException
     */
    List<Integer> getOrgStructureByAddress(String KLADRCode, String KLADRStreetCode, String number, String corpus, Integer flat) throws CoreException;

    /**
     * Получение списка работников заданной оргструктуры
     *
     * @param orgStructureId Оргструктура, в которой ищем работников
     * @param recursive      флаг рекурсии (при true- выборка работников еще и из подчиненных оргструктур)
     * @param infisCode      инфис-код организации
     * @return Список работников
     * @throws CoreException
     */
    List<Staff> getPersonnel(Integer orgStructureId, boolean recursive, String infisCode) throws CoreException;

    /**
     * Получение адресов Оргсруктуры
     *
     * @param currentOrgStructure оргструктура, для которой получаем имена
     * @return Список адресов оргструктуры
     */
    List<OrgStructureAddress> getOrgStructureAddressByOrgStructure(OrgStructure currentOrgStructure);
}
