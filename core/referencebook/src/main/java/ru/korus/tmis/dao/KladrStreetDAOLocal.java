package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.KladrStreet;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 14:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface KladrStreetDAOLocal {

    boolean isExist(String id);

    void insert(final KladrStreet item);
}
