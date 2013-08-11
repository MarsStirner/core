package ru.risar.service;

import ru.korus.tmis.core.database.dbutil.QueryBuilder;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.service.interfaces.StaffService;

import javax.ejb.EJB;
import java.util.Collection;
import java.util.List;

/**
 * @author anosov
 *         Date: 11.08.13 17:12
 */
public class RisarStaffServiceBean implements RisarStaffService {

    @EJB
    StaffService delegate;

    @Override
    public Staff save(Staff object) {
        return delegate.save(object);
    }

    @Override
    public Staff get(Long id) {
        return delegate.get(id);
    }

    @Override
    public void delete(Staff object, boolean flush) {
        delegate.delete(object, flush);
    }

    @Override
    public void delete(Staff object) {
        delegate.delete(object);
    }

    @Override
    public List<Staff> findAllByParams(Class<? extends Collection<Staff>> resultClass, QueryBuilder query) {
        return delegate.findAllByParams(resultClass, query);
    }
}
