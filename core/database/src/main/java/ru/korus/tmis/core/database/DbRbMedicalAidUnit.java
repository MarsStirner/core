package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidUnit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 19:28 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbMedicalAidUnit implements DbRbMedicalAidUnitBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbMedicalAidUnit getById(int id) {
        return em.find(RbMedicalAidUnit.class, id);
    }

    @Override
    public RbMedicalAidUnit getByCode(final String code) {
        List<RbMedicalAidUnit> resultList = em.createNamedQuery("rbMedicalAidUnit.findByCode", RbMedicalAidUnit.class)
                .setParameter("code", code).getResultList();
        if(!resultList.isEmpty()){
            final RbMedicalAidUnit medicalAidUnit = resultList.get(0);

            return medicalAidUnit;
        }
        return null;
    }
}
