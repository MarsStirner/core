package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbUnit;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 18:13 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbDrugComponentBean implements DbDrugComponentBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em;
    private String getComponentsByPrescriptionActionQuery = "SELECT dc FROM DrugComponent dc " +
            "WHERE dc.action.id = :ACTIONID";

    @Override
    public List<DrugComponent> getComponentsByPrescriptionAction(int prescriptionActionId) {
        return em.createQuery(getComponentsByPrescriptionActionQuery, DrugComponent.class)
                .setParameter("ACTIONID", prescriptionActionId)
                .getResultList();
    }

    @Override
    public DrugComponent create(final Action action, final Integer nomen, final String name, final Double dose, final Integer unit) {
        DrugComponent res = new DrugComponent();
        res.setCreateDateTime(new Date());
        initDrugComp(action, nomen, name, dose, unit, res);
        em.persist(res);
        return res;
    }

    @Override
    public DrugComponent update(final Action action, DrugComponent res, final Integer nomen, final String name, final Double dose, final Integer unit) {
        initDrugComp(action, nomen, name, dose, unit, res);
        em.merge(res);
        return res;
    }

    private void initDrugComp(Action action, Integer nomen, String name, Double dose, Integer unit, DrugComponent res) {
        res.setAction(action);
        res.setDose(dose);
        res.setName(name);
        final RlsNomen rlsNomen = em.find(RlsNomen.class, nomen);
        res.setNomen(rlsNomen);
        final RbUnit rbUnit = em.find(RbUnit.class, unit);
        res.setUnit(rbUnit);
    }

}
