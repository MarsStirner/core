package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Diagnosis;
import ru.korus.tmis.core.entity.model.Diagnostic;
import ru.korus.tmis.core.entity.model.Event;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        31.01.14, 17:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbQueryBeanLocal {

    /**
     * Получить название профиля койки
     *
     * @param action
     * @return
     */
    String getBedProfileName(final Action action);


    /**
     * Кол-во госпитализаций в текущем году с данным диагнозом
     */
    long countAdmissionsThisYear(final Event event, final Diagnosis diagnosis);

}
