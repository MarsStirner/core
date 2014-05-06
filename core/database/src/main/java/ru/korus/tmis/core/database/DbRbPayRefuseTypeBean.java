package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbPayRefuseType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 31.10.13, 18:48 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbPayRefuseTypeBean implements DbRbPayRefuseTypeBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbPayRefuseType getById(final int id) {
        return em.find(RbPayRefuseType.class, id);
    }

    @Override
    public RbPayRefuseType getByCode(final String code) {
        final List<RbPayRefuseType> resultList = em.createNamedQuery("RbRefuseType.findByCode", RbPayRefuseType.class)
                .setParameter("code", code)
                .getResultList();
        if(resultList.isEmpty()){
            return null;
        } else {
            return resultList.get(0);
        }
    }
}
