package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbFinance1C;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.10.13, 16:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbRbFinance1CBeanLocal {
    RbFinance1C getByFianceId(Integer id);
}
