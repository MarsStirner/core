package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.F002Smo;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 14:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class F002DAO implements F002DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(F002DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public boolean isExist(final String id) {
        return em.find(F002Smo.class, id) != null;
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void insert(final F002Smo item) {
        em.persist(item);
    }
}
