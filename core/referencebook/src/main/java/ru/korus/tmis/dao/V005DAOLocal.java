package ru.korus.tmis.dao;

import ru.korus.tmis.entity.V005Pol;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 11:57 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface V005DAOLocal {

    boolean isExist(long id);

    void insert(final V005Pol v005Pol);
}
