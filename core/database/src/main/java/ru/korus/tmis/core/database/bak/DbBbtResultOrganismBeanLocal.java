package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResultOrganism;
import ru.korus.tmis.core.entity.model.bak.BbtResultTable;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBbtResultOrganismBeanLocal {

    void add(BbtResultOrganism bbtResultOrganism);

    BbtResultOrganism get(final Integer id);

    BbtResultOrganism get(Integer organismId, Integer actionId);
}
