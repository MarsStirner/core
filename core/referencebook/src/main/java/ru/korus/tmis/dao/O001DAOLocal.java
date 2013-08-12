package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.O001Oksm;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface O001DAOLocal {

    boolean isExist(final String id);

    void insert(final O001Oksm item);
}
