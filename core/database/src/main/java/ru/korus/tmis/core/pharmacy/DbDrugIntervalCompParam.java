package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.DrugIntervalCompParam;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 18:13 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbDrugIntervalCompParam implements DbDrugIntervalCompParamLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em;


    @Override
    public DrugIntervalCompParam create(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa) {
        DrugIntervalCompParam res = new DrugIntervalCompParam();
        res.setDrugChart(drugChart);
        res.setDrugComponent(drugComponent);
        res.setDose(dose);
        res.setVoa(voa);
        em.persist(res);
        em.flush();
        return res;
    }

    @Override
    public void update(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa) {
        List<DrugIntervalCompParam> params = em.createNamedQuery("DrugIntervalCompParam.getByDrugChartAndComp", DrugIntervalCompParam.class)
                .setParameter("drugChartId", drugChart.getId())
                .setParameter("drugComponentId", drugComponent.getId()).getResultList();

        for(DrugIntervalCompParam compParamn : params) {
            compParamn.setDose(dose);
            compParamn.setVoa(voa);
            em.merge(compParamn);
        }
    }

    @Override
    public List<DrugIntervalCompParam> getCompParamByInterval(DrugChart drugChart) {
        return em.createNamedQuery("DrugIntervalCompParam.getByDrugChart", DrugIntervalCompParam.class)
                .setParameter("drugChartId", drugChart.getId()).getResultList();
    }


}
