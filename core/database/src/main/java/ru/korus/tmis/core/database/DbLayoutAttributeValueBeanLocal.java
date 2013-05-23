package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.layout.LayoutAttributeValue;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Интерфейс для работы с разметкой медицинских документов
 * Author: idmitriev Systema-Soft
 * Date: 5/15/13 12:48 PM
 * Since: 1.0.1.10
 */
@Local
public interface DbLayoutAttributeValueBeanLocal {

    /**
     * Весь справочник
     * @return List<LayoutAttribute>
     * @throws CoreException
     */
    List<LayoutAttributeValue> getLayoutAttributeValuesByActionPropertyTypeId(int aptId) throws CoreException;
}
