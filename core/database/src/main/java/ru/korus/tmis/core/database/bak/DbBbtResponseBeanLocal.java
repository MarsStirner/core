package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResponse;

import javax.annotation.CheckForNull;
import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBbtResponseBeanLocal {

    void add(final BbtResponse bbtResponse);

    @CheckForNull
    BbtResponse get(final Integer id);

    void remove(final int id);
}
