package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.bak.RbMicroorganism;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbRbMicroorganismBeanLocal {

    RbMicroorganism add(RbMicroorganism rbMicroorganism);

    RbMicroorganism get(String code);
}
