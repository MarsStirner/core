package ru.korus.tmis.dao;

import ru.korus.tmis.entity.F015FedOkr;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 14:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface F015DAOLocal {

    boolean isExist(long id);

    void insert(final F015FedOkr item);
}
