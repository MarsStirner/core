package ru.korus.tmis.dao;

import ru.korus.tmis.core.entity.model.referencebook.V009Rezult;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 12:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface V009DAOLocal {

    boolean isExist(final long id);

    void insert(final V009Rezult item);
}
