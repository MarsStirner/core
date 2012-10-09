package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import java.util.Map;
import javax.ejb.Local;

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
}
