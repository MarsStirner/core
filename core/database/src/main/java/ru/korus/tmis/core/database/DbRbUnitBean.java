package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbUnit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 02.09.13, 17:25 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbUnitBean implements DbRbUnitBeanLocal{

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;
    private static final String getByCodeQuery = "SELECT rbu FROM RbUnit rbu WHERE rbu.code = :CODE";

    @Override
    public RbUnit getById(int id) {
        return em.find(RbUnit.class, id);
    }

    @Override
    public RbUnit getByCode(String code) {
        final List<RbUnit> rbUnitList = em.createQuery(getByCodeQuery, RbUnit.class)
                .setParameter("CODE", code)
                .getResultList();
        return rbUnitList.isEmpty() ? null : rbUnitList.get(0);
    }
}
