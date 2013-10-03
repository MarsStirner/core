package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResponse;
import ru.korus.tmis.core.entity.model.bak.BbtResultTable;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBbtResultTableBeanLocal {

    void add(BbtResultTable bbtResultTable);

    BbtResultTable get(final Integer id);
}
