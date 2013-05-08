package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.O002Okato;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 22:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class O002DAO implements O002DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(O002DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public boolean isExist(String id) {
        return em.find(O002Okato.class, id) != null;
    }

    @Override
    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public void insert(O002Okato item) {
        em.persist(item);
    }
}
