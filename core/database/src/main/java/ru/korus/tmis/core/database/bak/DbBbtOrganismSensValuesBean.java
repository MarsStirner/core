package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtOrganismSensValues;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        03.10.13, 1:34 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbBbtOrganismSensValuesBean implements DbBbtOrganismSensValuesBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbBbtOrganismSensValuesBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;


    @Override
    public void add(BbtOrganismSensValues bbtOrganismSensValues) {
        final BbtOrganismSensValues response = get(bbtOrganismSensValues.getId());
        if (response == null) {
            em.persist(bbtOrganismSensValues);
            logger.info("create BbtOrganismSensValues {}", bbtOrganismSensValues);
        } else {
            logger.info("find BbtOrganismSensValues {}", response);
        }
    }

    @Override
    public BbtOrganismSensValues get(Integer id) {
        List<BbtOrganismSensValues> responseList =
                em.createQuery("SELECT a FROM BbtOrganismSensValues a WHERE a.id = :id", BbtOrganismSensValues.class)
                        .setParameter("id", id).getResultList();
        return !responseList.isEmpty() ? responseList.get(0) : null;
    }
}