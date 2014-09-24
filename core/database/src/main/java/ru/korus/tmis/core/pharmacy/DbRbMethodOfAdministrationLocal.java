package ru.korus.tmis.core.pharmacy;

import org.joda.time.DateTime;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbMethodOfAdministration;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        01.10.13, 17:04 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Local
public interface DbRbMethodOfAdministrationLocal {

    RbMethodOfAdministration getById(Integer id);
}
