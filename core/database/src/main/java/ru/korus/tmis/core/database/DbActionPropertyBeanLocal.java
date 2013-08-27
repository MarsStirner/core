package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.*;

@Local
public interface DbActionPropertyBeanLocal {

    ActionProperty getActionPropertyById(int id)
            throws CoreException;

    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionId(int actionId)
            throws CoreException;

    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionIdAndTypeNames(int actionId, List<String> names)
            throws CoreException;

    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionIdAndTypeTypeNames(int actionId, List<String> names)
            throws CoreException;

    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionIdAndTypeCodes(int actionId, List<String> codes)
            throws CoreException;

    /**
     * Возвращает список свойств со значениями по идентификаторам из таблицы rbCoreActionProperty
     *
     * @param actionId Идентификатор записи в таблице Action
     * @param coreIds  Список значений идентификаторов из таблицы rbCoreActionProperty
     * @return Список значений ActionProperty
     * @throws CoreException
     * @see ActionProperty
     * @see APValue
     * @see CoreException
     * @since 1.0.0.43
     */
    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionIdAndRbCoreActionPropertyIds(int actionId, List<Integer> coreIds)
            throws CoreException;

    Map<ActionProperty, List<APValue>>
    getActionPropertiesByActionIdAndActionPropertyTypeCodes(int actionId, Set<String> codes)
            throws CoreException;

    /**
     * Возвращает список свойств со значениями из последнего Action внутри выбранного Event по идентификаторам из таблицы rbCoreActionProperty
     *
     * @param eventId Идентификатор обращения.
     * @param atIds   Список идентификаторов ActionType внутри данного обращения
     * @param coreIds Список значений идентификаторов из таблицы rbCoreActionProperty
     * @return
     * @throws CoreException
     * @see ActionProperty
     * @see APValue
     * @see CoreException
     * @since 1.0.0.43
     */
    Map<ActionProperty, List<APValue>>
    getActionPropertiesForEventByActionTypes(int eventId, Set<Integer> atIds, Set<Integer> coreIds)
            throws CoreException;

    /**
     * Возвращает список заданного количества свойств со значениями из последних Action внутри заданного списка Event с возможностью выбора статуса акшена по кодам акшенПропертиТайпов
     *
     * @param eventId Список идентификаторов обращения.
     * @param codes   Список кодов ActionPropertyType, по которым будет производиться поиск ActionProperty
     * @param countInGroup количество возвращяемых пропертей для каждого кода и каждого евента
     * @param needStatus Параметр, указывающий, нужна ли проверка на статус акшена (используется в мониторинге) a.status = 2
     * @return Набор свойств со значениями для списка евентов как LinkedHashMap<Integer, LinkedHashMap<ActionProperty, List<APValue>>>
     * @throws CoreException
     * @see ActionProperty
     * @see APValue
     * @see CoreException
     * @since 1.0.1.34
     */
    LinkedHashMap<Integer, LinkedHashMap<ActionProperty, List<APValue>>>
    getActionPropertiesByEventIdsAndActionPropertyTypeCodes(java.util.List<Integer> eventId, java.util.Set<String> codes, int countInGroup, boolean needStatus)
            throws CoreException;

    List<APValue>
    getActionPropertyValue(ActionProperty actionProperty)
            throws CoreException;

    ActionProperty createActionProperty(Action action,
                                        int aptId,
                                        AuthData userData)
            throws CoreException;

    ActionProperty createActionPropertyWithDate(Action a,
                                                int aptId,
                                                AuthData userData,
                                                Date now)
            throws CoreException;

    ActionProperty updateActionProperty(int id,
                                        int version,
                                        AuthData userData)
            throws CoreException;

    List<ActionProperty> getActionPropertiesByActionIdAndTypeId(int actionId, int typeId)
            throws CoreException;

    /**
     * Изменяет или создает свойство типа действия
     *
     * @param ap    ActionProperty
     * @param value значение
     * @return объект ORM, содержащий новое значение свойства
     */
    APValue setActionPropertyValue(ActionProperty ap,
                                   String value,
                                   int index)
            throws CoreException;

    /**
     * Создает свойство типа действия
     *
     * @param ap    ActionProperty
     * @param value значение
     * @return объект ORM, содержащий новое значение свойства
     */
    APValue createActionPropertyValue(ActionProperty ap,
                                      String value,
                                      int index)
            throws CoreException;

    ActionProperty createActionProperty(Action doctorAction, ActionPropertyType queueAPType) throws CoreException;

    APValueAction getActionPropertyValue_ActionByValue(Action action) throws CoreException;
    /**
     * Получение своства действия по значению действия  (ActionProperty_Action by ActionProperty_Action.value)
     *
     * @param action действие,  которое указано как значение в ActionProperty_Action (VALUE)
     * @return
     */
    APValueAction getActionProperty_ActionByValue(Action action) throws CoreException;
}
