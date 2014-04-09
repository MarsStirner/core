package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        31.01.14, 17:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbQueryBean implements DbQueryBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    /**
     * @since
     */
    @NotNull
    @Override
    public String getBedProfileName(Action action) {
        final List<String> nameList = em.createNativeQuery("SELECT rbhbp.name FROM " +
                "Action a JOIN ActionProperty ap ON ap.action_id = a.id " +
                "JOIN ActionPropertyType apt ON ap.type_id = apt.id " +
                "JOIN ActionProperty_HospitalBedProfile aphbp ON ap.id = aphbp.id " +
                "JOIN rbHospitalBedProfile rbhbp ON aphbp.value = rbhbp.id " +
                "WHERE a.id = ? AND apt.code = 'hospitalBedProfile'").setParameter(1, action.getId()).getResultList();
        return !nameList.isEmpty() ? nameList.get(0) : null;
    }

    /**
     * Кол-во госпитализаций в текущем году с данным диагнозом
     */
    @Override
    public long countAdmissionsThisYear(final Event event, final Diagnosis diagnosis) {
        final Mkb mkb = diagnosis.getMkb();
        final String diagName = mkb == null ? null : mkb.getDiagName();

               /*
        em.createNativeQuery("SELECT * FROM Event e JOIN EventType et ON et.id = e.id JOIN rbRequestType rrt ON et.requestType_id = rrt.id " +
                " JOIN WHERE  client_id = ? " +
                "   AND rrt.code IN ( 'clinic', 'hospital', 'stationary' ) " +
                "   AND (e.setDate BETWEEN '2013-01-01 00:00:00' AND NOW()) ");
                 */
        return 0;
    }


}
