package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.RbAntibiotic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        03.10.13, 1:40 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbRbAntibioticBean implements DbRbAntibioticBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbRbAntibioticBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public void add(RbAntibiotic rbAntibiotic) {
        final RbAntibiotic response = em.find(RbAntibiotic.class, rbAntibiotic.getId());
        if (response == null) {
            em.persist(rbAntibiotic);
            logger.info("create RbAntibiotic {}", rbAntibiotic);
        } else {
            logger.info("find RbAntibiotic {}", response);
        }
    }

    @Override
    public RbAntibiotic get(Integer id) {
        return em.find(RbAntibiotic.class, id);
    }
}
