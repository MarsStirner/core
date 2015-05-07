package ru.korus.tmis.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Staff;

import ru.korus.tmis.core.service.interfaces.StaffService;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author anosov
 *         Date: 08.08.13 0:54
 */
@Stateless
public class StaffServiceBean implements StaffService {

    private static final Logger log = LoggerFactory.getLogger(StaffServiceBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @Override
    public Staff save(Staff staff) {
        if (staff == null) {
            final String errorString = "No save null staff";
            log.error(errorString);
            throw new IllegalStateException(errorString);
        }
        em.persist(staff);
        return staff;
    }

    @Override
    public Staff get(final Long id) throws IllegalStateException {
        final Staff staff = em.find(Staff.class, id);
        if (staff == null) {
            final String errorString = "Not found Staff by ID [" + id + "]";
            log.error(errorString);
            throw new IllegalStateException(errorString);
        }
        return staff;
    }


    @Override
    public void delete(Staff staff) throws IllegalStateException {
        if (staff == null) {
            final String errorString = "When removing staff wasn't be NULL";
            log.error(errorString);
            throw new IllegalStateException(errorString);
        }
        final Staff managedStaff = em.merge(staff);
        managedStaff.setDeleted(true);
    }

    @Override
    public void delete(Staff staff, boolean flush) throws IllegalStateException {
        delete(staff);
        if (flush) {
            em.flush();
        }
    }
}
