package ru.korus.tmis.core.database;

import javax.ejb.Local;

import ru.korus.tmis.core.entity.model.RbTempInvalidReason;


@Local
public interface DbRbTempInvalidReasonBeanLocal {

    Iterable<RbTempInvalidReason> getAllRbTempInvalidReason();

    RbTempInvalidReason getRbTempInvalidReasonById(int id);

    RbTempInvalidReason getRbTempInvalidReasonByCode(String code);
}
