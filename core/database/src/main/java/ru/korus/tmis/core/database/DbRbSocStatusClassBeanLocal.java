package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbSocStatusClass;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 11.07.12
 * Time: 17:41
 * To change this template use File | Settings | File Templates.
 */
@javax.ejb.Local
public interface DbRbSocStatusClassBeanLocal {

    RbSocStatusClass getSocStatusClassById(int id);

}
