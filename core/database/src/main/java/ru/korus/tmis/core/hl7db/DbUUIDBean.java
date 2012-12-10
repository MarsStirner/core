package ru.korus.tmis.core.hl7db;

import ru.korus.tmis.core.database.DbManagerBeanLocal;
import ru.korus.tmis.core.entity.model.UUID;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 09.12.12
 * Time: 15:49
 * To change this template use File | Settings | File Templates.
 */
@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbUUIDBean implements DbUUIDBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    private DbManagerBeanLocal dbManager = null;


    /**
     * Возвращает UUID по идентификатору id
     * @param id
     * @return
     */
    @Override
    public String getUUIDById(int id) {
        return em.createQuery("SELECT u.uuid FROM UUID u WHERE u.id = :id", String.class)
                 .setParameter("id",id)
                 .getSingleResult();
    }
}
