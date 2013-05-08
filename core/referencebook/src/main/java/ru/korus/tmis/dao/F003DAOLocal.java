package ru.korus.tmis.dao;

import ru.korus.tmis.entity.F003Mo;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 15:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface F003DAOLocal {

    boolean isExist(final String id);

    void insert(final F003Mo item);

}
