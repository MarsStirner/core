package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 31.01.13
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DbContractBeanLocal {

    Contract getContractById(int id) throws CoreException;

    /**
     * Поведение данного метода неверно - контрактов для одного типа событий
     * может быть несколько. Используйте {@link #getContractsByEventTypeId(int, int, boolean, boolean) getContractsByEventTypeId}
     * @param eventType Тип события
     * @return Первый контракт из списка или null, если таковых не нашлось
     */
    @Deprecated
    Contract getContractForEventType(EventType eventType);

    List<Contract> getContractsByEventTypeId(int eventTypeId, int financeId, boolean showDeleted, boolean showExpired);
}
