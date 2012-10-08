package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbTempInvalidReason;

import javax.ejb.Local;


@Local
public interface DbRbTempInvalidReasonBeanLocal {

    Iterable<RbTempInvalidReason> getAllRbTempInvalidReason();

    RbTempInvalidReason getRbTempInvalidReasonById(int id);

}
