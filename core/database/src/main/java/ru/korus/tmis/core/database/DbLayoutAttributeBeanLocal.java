package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.layout.LayoutAttribute;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Интерфейс для работы с разметкой медицинских документов
 * Author: idmitriev Systema-Soft
 * Date: 5/15/13 8:43 PM
 * Since: 1.0.1.10
 */
@Local
public interface DbLayoutAttributeBeanLocal {

    /**
     * Весь справочник
     * @return List<LayoutAttribute>
     * @throws CoreException
     */
    List<LayoutAttribute> getAllLayoutAttributes() throws CoreException;
}
