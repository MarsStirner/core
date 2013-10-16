package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResultOrganism;
import ru.korus.tmis.core.entity.model.bak.BbtResultText;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbBbtResultTextBeanLocal {

    void add(BbtResultText bbtResultText);

    BbtResultText get(final Integer id);
}
