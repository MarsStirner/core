package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.V001Nomerclr;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 12:14 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface V001DAOLocal {

    boolean isExist(long id);

    void insert(final V001Nomerclr item);
}
