package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.RbAntibiotic;
import ru.korus.tmis.core.entity.model.bak.RbBacIndicator;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbRbBacIndicatorBeanLocal {

    void add(RbBacIndicator rbBacIndicator);

    RbBacIndicator get(final Integer id);
}
