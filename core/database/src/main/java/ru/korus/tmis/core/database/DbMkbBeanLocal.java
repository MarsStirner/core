package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbMkbBeanLocal {

    Mkb getMkbById(int id) throws CoreException;

    Mkb getMkbByCode(String code) throws CoreException;
}
