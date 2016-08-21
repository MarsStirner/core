package ru.korus.tmis.core.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbQuotingBySpecialityBean;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

    @Override
    public DrugChart create(Action action, Integer masterId, Date begDateTime, Date endDateTime, Short status, String note) {
        DrugChart res = new DrugChart();
        res.setAction(action);
        if (masterId != null) {
            DrugChart master = em.find(DrugChart.class, masterId);
            res.setMaster(master);
        }
        res.setBegDateTime(begDateTime);
        res.setEndDateTime(endDateTime);
        res.setStatus(status);
        res.setStatusDateTime(new Date());
        res.setNote(note);
        res.setUuid(UUID.randomUUID());
        em.persist(res);
        em.flush();
        if(masterId == null) {
            create(action, res.getId(), begDateTime, endDateTime, status, note);
        }

        return res;
    }

    @Override
    public void updateStatus(List<Integer> data, Short status) {
        em.createNamedQuery("DrugChart.updateStatus", DrugChart.class)
                .setParameter("status", status)
                .setParameter("intervalIds", data)
                .executeUpdate();
    }

}
