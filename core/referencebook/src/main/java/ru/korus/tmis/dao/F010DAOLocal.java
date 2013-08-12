package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.F010Subekti;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 14:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface F010DAOLocal {

    boolean isExist(String id);

    void insert(final F010Subekti item);
}
