package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionTypeTissueType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.TakenTissue;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Методы для работы с TakenTissueJournal
 * Author: mmakankov Systema-Soft
 * Date: 2/13/13 2:30 PM
 * Since: 1.0.0.69
 */
@Local
public interface DbTakenTissueBeanLocal {

    /**
     * Создание или редактирование TakenTissue
     * @param id идентификатор TakenTissue, по которому будет производиться поиск
     * @param action Action, для которого будет создаваться TakenTissue
     * @return TakenTissue
     * @throws CoreException
     * @see Event
     * @see TakenTissue
     * @see CoreException
     */
    TakenTissue insertOrUpdateTakenTissue(int id, Action action) throws CoreException;

    /**
     * Запрос на TakenTissue по идентификатору
     * @param id идентификатор TakenTissue, по которому будет производиться поиск
     * @return TakenTissue
     * @throws CoreException
     * @see Event
     * @see TakenTissue
     * @see CoreException
     */
    TakenTissue getTakenTissueById(int id) throws CoreException;

    /**
     * Запрос на TakenTissue (тип биоматериала) по идентификатору
     * @param actionTypeId идентификатор nипа действия, по которому будет производиться поиск
     * @return TakenTissue
     * @throws CoreException
     * @see Event
     * @see TakenTissue
     * @see CoreException
     */
    ActionTypeTissueType getActionTypeTissueTypeByMasterId(int actionTypeId) throws CoreException;
}
