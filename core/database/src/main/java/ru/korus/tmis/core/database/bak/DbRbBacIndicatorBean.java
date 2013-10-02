package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.RbBacIndicator;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        03.10.13, 1:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbRbBacIndicatorBean implements DbRbBacIndicatorBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbRbBacIndicatorBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;


    @Override
    public void add(RbBacIndicator rbBacIndicator) {
        final RbBacIndicator response = em.find(RbBacIndicator.class, rbBacIndicator.getId());
        if (response == null) {
            em.persist(response);
            logger.info("create BbtOrganismSensValues {}", response);
        } else {
            logger.info("find BbtOrganismSensValues {}", response);
        }
    }

    @Override
    public RbBacIndicator get(Integer id) {
        return em.find(RbBacIndicator.class, id);
    }
}
