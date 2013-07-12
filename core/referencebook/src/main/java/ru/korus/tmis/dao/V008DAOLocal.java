package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.V008VidMp;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface V008DAOLocal {

    boolean isExist(final long id);

    void insert(final V008VidMp item);
}
