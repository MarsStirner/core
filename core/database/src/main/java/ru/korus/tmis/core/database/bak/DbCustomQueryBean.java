package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.Action;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.11.13, 0:27 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbCustomQueryBean implements DbCustomQueryBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public BakDiagnosis getBakDiagnosis(Action action) {

        final List<Object> mainDiagMkb = em.createNativeQuery("SELECT MKB.DiagID, MKB.DiagName " +
                "FROM Action " +
                "JOIN ActionProperty ON ActionProperty.action_id = Action.id J" +
                "OIN ActionPropertyType ON ActionPropertyType.id = ActionProperty.type_id " +
                "JOIN ActionProperty_MKB ON ActionProperty.id = ActionProperty_MKB.id " +
                "JOIN MKB ON MKB.id = ActionProperty_MKB.value " +
                "WHERE Action.createDatetime <= ? AND Action.event_id = ? AND ActionPropertyType.code = ? AND Action.deleted = 0 " +
                "ORDER BY Action.createDatetime DESC " +
                "LIMIT 0, 1 ")
                .setParameter(1, action.getCreateDatetime())
                .setParameter(2, action.getEvent().getId())
                .setParameter(3, "mainDiagMkb")
                .getResultList();

        return new BakDiagnosis((String) mainDiagMkb.get(0), (String) mainDiagMkb.get(1));
    }
}
