package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.Action;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.11.13, 0:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface DbCustomQueryBeanLocal {

    BakDiagnosis getBakDiagnosis(final Action action);
}
