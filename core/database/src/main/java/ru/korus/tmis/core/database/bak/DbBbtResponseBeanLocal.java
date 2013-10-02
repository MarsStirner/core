package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResponse;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public interface DbBbtResponseBeanLocal {

    void add(BbtResponse bbtResponse);

    BbtResponse get(final Integer id);
}
