package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.O003Okved;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 22:43 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class O003DAO implements O003DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(O003DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public boolean isExist(String id) {
        return em.find(O003Okved.class, id) != null;
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void insert(O003Okved item) {
        em.persist(item);
    }
}
