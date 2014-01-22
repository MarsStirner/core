package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @Author: Dmitriy E. Nosov <br>
 * @Date: 09.12.12, 15:49 <br>
 * @Company: Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * @Description: <br>
 */
//@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbUUIDBean implements DbUUIDBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    private DbManagerBeanLocal dbManager = null;


    /**
     * Возвращает UUID по идентификатору id
     *
     * @param id
     * @return
     */
    @Override
    public String getUUIDById(final int id) {
        return em.createQuery("SELECT u.uuid FROM UUID u WHERE u.id = :id", String.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public UUID createUUID() {
      return new UUID(java.util.UUID.randomUUID().toString());
    }
}
