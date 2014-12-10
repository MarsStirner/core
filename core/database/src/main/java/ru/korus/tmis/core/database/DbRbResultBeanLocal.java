package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.entity.model.RbAcheResult;
import ru.korus.tmis.core.entity.model.RbResult;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 30.07.13
 * Time: 16:36
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DbRbResultBeanLocal {

    RbResult getRbResultById(int id) throws CoreException;

    RbResult getRbResultByCodeAndEventType(EventType et, String code) throws CoreException;

    RbAcheResult getRbAcheResultByCodeAndEventType(EventType et, String code) throws CoreException;
}
