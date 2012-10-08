package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbTempInvalidDocument;

import javax.ejb.Local;

@Local
public interface DbRbTempInvalidDocumentBeanLocal {


    Iterable<RbTempInvalidDocument> getAllRbTempInvalidDocument();

    RbTempInvalidDocument getRbTempInvalidDocumentById(int id);

}
