package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbCoreActionProperty;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DbRbCoreActionPropertyBeanLocal {

    RbCoreActionProperty getRbCoreActionPropertyByActionTypeIdAndCorePropertyName(int actionTypeId, String cpName)
            throws CoreException;

    RbCoreActionProperty getRbCoreActionPropertyByActionTypeIdAndActionPropertyTypeId(int actionTypeId, int actionPropertyTypeId)
            throws CoreException;

    List<RbCoreActionProperty> getRbCoreActionPropertiesByActionTypeId(int actionTypeId)
            throws CoreException;

    RbCoreActionProperty getRbCoreActionPropertiesByActionPropertyTypeId(int actionPropertyTypeId)
            throws CoreException;

    RbCoreActionProperty getRbCoreActionPropertiesById(int id) throws CoreException;
}
