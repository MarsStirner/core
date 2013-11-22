package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.Action;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.11.13, 0:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBakCustomQueryBeanLocal {

    BakDiagnosis getBakDiagnosis(final Action action);
}
