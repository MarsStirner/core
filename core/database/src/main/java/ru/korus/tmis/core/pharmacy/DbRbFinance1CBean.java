package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.database.DbRbFinance1CBeanLocal;
import ru.korus.tmis.core.entity.model.RbFinance1C;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.10.13, 16:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbRbFinance1CBean implements DbRbFinance1CBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public RbFinance1C getByFianceId(Integer id) {
        final List<RbFinance1C> financeIds = em.createNamedQuery("RbFinance1C.getByFinanceId", RbFinance1C.class).setParameter("financeId", id).getResultList();
        if (financeIds.isEmpty()) {
            return null;
        }
        return financeIds.iterator().next();
    }
}
