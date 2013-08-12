package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.F008TipOMS;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 14:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface F008DAOLocal {

    boolean isExist(long id);

    void insert(final F008TipOMS item);
}
