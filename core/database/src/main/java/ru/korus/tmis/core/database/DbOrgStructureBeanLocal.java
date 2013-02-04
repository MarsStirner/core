package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

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
     * @param sortingField  Поле для сортировки.
     * @param sortingMethod Метод для сортировки.
     * @param filter        Фильтр значений списка.
     * @return Список отделений как List<OrgStructure>
     * @throws CoreException
     */
    List<OrgStructure> getAllOrgStructuresByRequest(int limit,
                                                    int page,
                                                    String sortingField,
                                                    String sortingMethod,
                                                    Object filter)
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
     * Запрос на под-отделения начиная с указаного parentId.
     *
     * @param parentId  Идентификатор отделения для которго выводим все подотделения
     * @param recursive рекурсивно
     * @param infisCode Принадлежащие одной структуре
     * @return Список под-отделений
     * @throws CoreException
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
     * Запрос на получение сотрудников по заданному подразделению
     *
     * @return Список сотрудников удовлятворяющих заданному адресу
     * @throws CoreException
     */
    List<Staff> getPersonnel(Integer orgStructureId, boolean recursive, String infisCode) throws CoreException;

}
