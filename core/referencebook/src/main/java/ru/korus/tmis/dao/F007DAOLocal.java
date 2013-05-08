package ru.korus.tmis.dao;

import ru.korus.tmis.entity.F007Vedom;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 15:56 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface F007DAOLocal {

    boolean isExist(final long id);

    void insert(final F007Vedom item);
}