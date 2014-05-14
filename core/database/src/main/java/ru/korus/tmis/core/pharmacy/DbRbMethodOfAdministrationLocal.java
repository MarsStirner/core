package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.RbMethodOfAdministration;

import javax.ejb.Local;

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
