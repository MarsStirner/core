package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbDiseaseStage;
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
public interface DbRbDiseaseStageBeanLocal {

    /**
     * Получение стадии болезни по идентификатору обращения
     * @param id Идентификатор стадии болезни
     * @return Стадия болезни как RbDiseaseStage
     * @throws ru.korus.tmis.core.exception.CoreException
     */
    RbDiseaseStage getDiseaseStageById(int id) throws CoreException;
}
