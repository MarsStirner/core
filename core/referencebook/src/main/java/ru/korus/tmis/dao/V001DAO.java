package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.V001Nomerclr;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 11:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class V001DAO implements V001DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(V001DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public boolean isExist(long id) {
        return em.find(V001Nomerclr.class, id) != null;
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void insert(final V001Nomerclr item) {
        em.persist(item);
    }

}
