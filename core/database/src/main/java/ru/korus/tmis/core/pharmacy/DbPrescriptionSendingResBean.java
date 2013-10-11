package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.DrugChart;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.PrescriptionSendingRes;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.10.13, 14:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbPrescriptionSendingResBean implements DbPrescriptionSendingResBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public PrescriptionSendingRes getPrescriptionSendingRes(DrugChart drugChart, RlsNomen rlsNomen) {
        List<PrescriptionSendingRes> resList = em.createNamedQuery("PrescriptionSendingRes.findByIntervalAndNomen", PrescriptionSendingRes.class).
                setParameter("intervalId", drugChart.getId()).
                setParameter("nomenId", rlsNomen.getId() ).getResultList();
        if(resList.isEmpty()) {
            final PrescriptionSendingRes prescriptionSendingRes = new PrescriptionSendingRes();
            prescriptionSendingRes.setRlsNomen(rlsNomen);
            prescriptionSendingRes.setDrugChart(drugChart);
            em.persist(prescriptionSendingRes);
            return prescriptionSendingRes;
        }
        return resList.iterator().next();
    }
}
