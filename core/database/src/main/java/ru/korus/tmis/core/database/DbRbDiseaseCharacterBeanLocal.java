package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbDiseaseCharacter;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 05.08.13
 * Time: 16:00
 * To change this template use File | Settings | File Templates.
 */

@Local
public interface DbRbDiseaseCharacterBeanLocal {

    /**
     * Получение характера заболевания по идентификатору обращения
     * @param id Идентификатор характера заболевания
     * @return Характер заболевания как RbDiseaseCharacter
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    RbDiseaseCharacter getDiseaseCharacterById(int id) throws CoreException;

}
