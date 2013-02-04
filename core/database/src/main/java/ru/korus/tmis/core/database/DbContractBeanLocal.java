package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 31.01.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
public interface DbContractBeanLocal {

    Contract getContractById(int id) throws CoreException;

    Contract getContractForEventType(int eventTypeId, int financeId);
}
