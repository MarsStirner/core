package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public interface DbOrgStructureBeanLocal {
    /**
     * Запрос на отделение по идентификатору.
     * @param id идентификатор записи.
     * @return Отделение как OrgStructure
     * @see OrgStructure
     * @throws CoreException
     */
    OrgStructure getOrgStructureById(int id)
            throws CoreException;
    /**
     * Запрос на список всех отделений.
     * @return список отделений как List<OrgStructure>
     * @throws CoreException
     */
    List<OrgStructure> getAllOrgStructures();

    /**
     * Запрос на список всех отделений с параметрами.
     * @return Список отделений как List<OrgStructure>
     * @param page Выводимая страница.
     * @param limit Максимальное количество типов оплаты в выводимом списке.
     * @param sortingField Поле для сортировки.
     * @param sortingMethod Метод для сортировки.
     * @param filter Фильтр значений списка.
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
     * @return колличество отделений как long
     * @param filter Фильтр значений списка.
     * @throws CoreException
     */
    long getCountAllOrgStructuresWithFilter(Object filter)
            throws CoreException;

    /**
     * Запрос на список типов действий по отделению
     * @param departmentId Идентификатор отделения.
     * @return Список типов действии отделения как Set<ActionType>
     * @throws CoreException
     */
    Set<ActionType> getActionTypeFilter(int departmentId)
            throws CoreException;

    /**
     * Запрос на отделение по койке.
     * @param bedId Идентификатор койки.
     * @return Отделение как OrgStructure
     * @throws CoreException
     */
    OrgStructure getOrgStructureByHospitalBedId(int bedId) throws CoreException;

    /**
     * Запрос на под-отделения начиная с указаного parentId.
     * @param parentId Идентификатор отделения для которго выводим все подотделения
     * @param recursive  рекурсивно
     * @return Список под-отделений
     * @throws CoreException
     */
    List<OrgStructure> getRecursiveOrgStructures(Integer parentId, boolean recursive) throws CoreException;

    /**
     * Запрос на получение ИД оргструктур по заданному адресу
     * @return Список ИД оргутруктур удовлятворяющих заданному адресу
     * @throws CoreException
     */
    List<Integer> getOrgStructureByAdress(String KLADRCode, String KLADRStreetCode, String number, String corpus,Integer flat) throws CoreException;
}
