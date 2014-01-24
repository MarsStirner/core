package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtOrganismSensValues;
import ru.korus.tmis.core.entity.model.bak.BbtResultTable;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBbtOrganismSensValuesBeanLocal {

    void add(BbtOrganismSensValues bbtOrganismSensValues);

    BbtOrganismSensValues get(final Integer id);

    void removeByResultOrganismId(Integer id);
}
