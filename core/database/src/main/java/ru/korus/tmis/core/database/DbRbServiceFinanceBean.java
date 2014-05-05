package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbServiceFinance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 19:18 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbServiceFinanceBean implements DbRbServiceFinanceBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbServiceFinance getServiceFinanceById(int id) {
        return em.find(RbServiceFinance.class, id);
    }

    @Override
    public RbServiceFinance getServiceFinanceByCode(String code) {
        List<RbServiceFinance> resultList = em.createNamedQuery("rbServiceFinance.findByCode", RbServiceFinance.class)
                .setParameter("code", code).getResultList();
        if(!resultList.isEmpty()){
            RbServiceFinance serviceFinance = resultList.get(0);
            em.detach(serviceFinance);
            return serviceFinance;
        }
        return null;
    }
}
