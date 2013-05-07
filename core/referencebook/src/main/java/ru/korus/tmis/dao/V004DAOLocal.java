package ru.korus.tmis.dao;

import ru.korus.tmis.entity.V004Medspec;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface V004DAOLocal {

    boolean isExist(final long id);

    void insert(final V004Medspec item);
}
