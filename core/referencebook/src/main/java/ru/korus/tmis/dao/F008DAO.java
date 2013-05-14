package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.F008TipOMS;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 22:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class F008DAO implements F008DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(F008DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public boolean isExist(long id) {
        return em.find(F008TipOMS.class, id) != null;
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void insert(F008TipOMS item) {
        em.persist(item);
    }
}
