package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbService;
import ru.korus.tmis.core.entity.model.RbServiceUET;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.14, 20:52 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbServiceUetBean implements DbRbServiceUetBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbServiceUET getById(final Integer id) {
        return em.find(RbServiceUET.class, id);
    }

    @Override
    public List<RbServiceUET> getByService(final RbService service) {
        return em.createNamedQuery("rbServiceUET.findByService", RbServiceUET.class)
                .setParameter("service", service)
                .getResultList();
    }
}
