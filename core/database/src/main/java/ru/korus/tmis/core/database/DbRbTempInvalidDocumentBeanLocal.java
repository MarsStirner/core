package ru.korus.tmis.core.database;

import javax.ejb.Local;

import ru.korus.tmis.core.entity.model.RbTempInvalidDocument;

@Local
public interface DbRbTempInvalidDocumentBeanLocal {


    Iterable<RbTempInvalidDocument> getAllRbTempInvalidDocument();

    RbTempInvalidDocument getRbTempInvalidDocumentById(int id);

    RbTempInvalidDocument getRbTempInvalidDocumentByCode(String code);
}
