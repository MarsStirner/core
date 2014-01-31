package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        31.01.14, 17:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbCustomQueryBeanLocal {

    /**
     * Получить название профиля койки
     * @param action
     * @return
     */
   String getBedProfileName(final Action action);

}
