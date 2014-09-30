package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionsTo1C;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        01.10.13, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbPrescriptionsTo1CBean implements DbPrescriptionsTo1CBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @Override
    public Iterable<PrescriptionsTo1C> getPrescriptions() {
        return em.createNamedQuery("PrescriptionsTo1C.findToSend", PrescriptionsTo1C.class)
                .setParameter("now", new Timestamp((new Date()).getTime())).setMaxResults(50).getResultList();
    }

    @Override
    public void remove(PrescriptionsTo1C prescriptionsTo1C) {
        em.remove(prescriptionsTo1C);
    }
}
