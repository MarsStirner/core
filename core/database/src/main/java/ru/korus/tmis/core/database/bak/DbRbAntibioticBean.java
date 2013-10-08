package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.RbAntibiotic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        final RbAntibiotic response = get(rbAntibiotic.getCode());
        if (response == null) {
            em.persist(rbAntibiotic);
            logger.info("create RbAntibiotic {}", rbAntibiotic);
        } else {
            logger.info("find RbAntibiotic {}", response);
        }
    }

    @Override
    public RbAntibiotic get(String code) {
        List<RbAntibiotic> antibioticList =
                em.createQuery("SELECT a FROM RbAntibiotic a WHERE a.code = :code", RbAntibiotic.class).setParameter("code", code).getResultList();
        return !antibioticList.isEmpty() ? antibioticList.get(0) : null;
    }
}
