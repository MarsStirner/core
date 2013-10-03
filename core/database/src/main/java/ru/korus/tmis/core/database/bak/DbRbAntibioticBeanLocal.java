package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.BbtResultTable;
import ru.korus.tmis.core.entity.model.bak.RbAntibiotic;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbRbAntibioticBeanLocal {

    void add(final RbAntibiotic rbAntibiotic);

    RbAntibiotic get(final String code);
}
