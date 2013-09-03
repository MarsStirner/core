package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.DrugComponent;

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
}
