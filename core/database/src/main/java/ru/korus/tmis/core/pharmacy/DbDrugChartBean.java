package ru.korus.tmis.core.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbQuotingBySpecialityBean;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.Event;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 13:44 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbDrugChartBean implements DbDrugChartBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(DbQuotingBySpecialityBean.class);

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;
    private static final String getPrescriptionIntervalsQuery = "SELECT drch FROM DrugChart drch " +
            "WHERE drch.action.id = :ACTIONID AND drch.master IS NULL";
    private static final String getExecutionIntervalsQuery = "SELECT drch FROM DrugChart drch " +
            "WHERE drch.action.id = :ACTIONID AND drch.master.id = :MASTERID";

    @Override
    public List<DrugChart> getPrescriptionIntervals(int prescriptionActionId) {
        return em.createQuery(getPrescriptionIntervalsQuery, DrugChart.class)
                .setParameter("ACTIONID", prescriptionActionId)
                .getResultList();
    }

    @Override
    public List<DrugChart> getExecIntervals(int prescriptionActionId, int masterId) {
        return em.createQuery(getExecutionIntervalsQuery, DrugChart.class)
                .setParameter("ACTIONID", prescriptionActionId)
                .setParameter("MASTERID", masterId)
                .getResultList();
    }

    @Override
    public Iterable<DrugChart> getIntervalsByEvent(Event event) {
        return em.createNamedQuery("DrugChart.findByEvent", DrugChart.class)
                .setParameter("eventId", event.getId()).getResultList();
    }
}
