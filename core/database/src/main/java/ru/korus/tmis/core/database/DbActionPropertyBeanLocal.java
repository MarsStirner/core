package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    List<APValue>
    getActionPropertyValue(ActionProperty actionProperty)
            throws CoreException;

    ActionProperty createActionProperty(Action action,
                                        int aptId,
                                        AuthData userData)
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

    /**
     * Получение своства действия по значению действия  (ActionProperty_Action by ActionProperty_Action.value)
     *
     * @param action действие,  которое указано как значение в ActionProperty_Action (VALUE)
     * @return
     */
    APValueAction getActionProperty_ActionByValue(Action action) throws CoreException;
}
