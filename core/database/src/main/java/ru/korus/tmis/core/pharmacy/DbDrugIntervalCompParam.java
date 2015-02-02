package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.DrugIntervalCompParam;

import javax.ejb.EJB;
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

    @EJB
    DbRbMethodOfAdministrationLocal dbRbMethodOfAdministrationLocal;

    @Override
    public DrugIntervalCompParam create(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa, Integer moa) {
        DrugIntervalCompParam res = new DrugIntervalCompParam();
        res.setDrugChart(drugChart);
        res.setDrugComponent(drugComponent);
        res.setDose(dose);
        res.setVoa(voa);
        if(moa != null && moa > 0) {
            res.setMoa(dbRbMethodOfAdministrationLocal.getById(moa));
        }
        em.persist(res);
        em.flush();
        return res;
    }

    @Override
    public void update(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa, Integer moa) {
        List<DrugIntervalCompParam> params = em.createNamedQuery("DrugIntervalCompParam.getByDrugChartAndComp", DrugIntervalCompParam.class)
                .setParameter("drugChartId", drugChart.getId())
                .setParameter("drugComponentId", drugComponent.getId()).getResultList();

        for(DrugIntervalCompParam compParamn : params) {
            compParamn.setDose(dose);
            compParamn.setVoa(voa);
            if(moa > 0) {
                compParamn.setMoa(dbRbMethodOfAdministrationLocal.getById(moa));
            }
            em.merge(compParamn);
        }
    }

    @Override
    public List<DrugIntervalCompParam> getCompParamByInterval(DrugChart drugChart) {
        return em.createNamedQuery("DrugIntervalCompParam.getByDrugChart", DrugIntervalCompParam.class)
                .setParameter("drugChartId", drugChart.getId()).getResultList();
    }


}
