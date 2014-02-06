package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidProfile;

import javax.annotation.Nullable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class RbMedicalAidProfileBean implements RbMedicalAidProfileBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    /**
     * Получить профиль мед.помощи по идентификатору
     *
     * @param id
     */
    @Nullable
    @Override
    public RbMedicalAidProfile getProfileById(int id) {
        final List<RbMedicalAidProfile>
                result = em.createNamedQuery("RbMedicalAidProfile.getProfileById", RbMedicalAidProfile.class)
                .setParameter("id", id)
                .getResultList();
        return !result.isEmpty() ? result.get(0) : null;
    }
}
