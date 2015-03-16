package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalKind;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 14:34 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbMedicalKindBean implements DbRbMedicalKindBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbMedicalKind getMedicalKindById(int id) {
        return em.find(RbMedicalKind.class, id);
    }

    @Override
    public RbMedicalKind getMedicalKindByCode(String code) {
        List<RbMedicalKind> resultList = em.createNamedQuery("rbMedicalKind.findByCode", RbMedicalKind.class)
                .setParameter("code", code).getResultList();
        if (!resultList.isEmpty()) {
            final RbMedicalKind result = resultList.get(0);

            return result;
        }
        return null;
    }
}
