package ru.korus.tmis.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.entity.V005Pol;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        07.05.13, 11:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class V005DAO implements V005DAOLocal {
    private static final Logger logger = LoggerFactory.getLogger(V005DAO.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;


    public boolean isExist(long id) {
        return em.find(V005Pol.class, id) != null;
    }

    public void insert(final V005Pol item) {
        em.persist(item);
    }
}