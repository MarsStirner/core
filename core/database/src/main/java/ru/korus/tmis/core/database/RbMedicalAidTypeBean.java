package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class RbMedicalAidTypeBean implements RbMedicalAidTypeBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    /**
     * Получить тип мед.помощи по идентификатору
     *
     * @param id
     */
    @Override
    public RbMedicalAidType getProfileById(int id) {
        final List<RbMedicalAidType>
                result = em.createNamedQuery("RbMedicalAidType.getProfileById", RbMedicalAidType.class)
                .setParameter("id", id)
                .getResultList();
        return !result.isEmpty() ? result.get(0) : null;
    }
}
