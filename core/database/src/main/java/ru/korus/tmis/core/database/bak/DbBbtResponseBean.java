package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtResponse;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        02.10.13, 18:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbBbtResponseBean implements DbBbtResponseBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(DbBbtResponseBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public void add(final BbtResponse bbtResponse) {
        final BbtResponse response = em.find(BbtResponse.class, bbtResponse.getId());
        if (response == null) {
            em.persist(response);
            logger.info("create BbtResponse {}", response);
        } else {
            logger.info("find BbtResponse {}", response);
        }
    }

    @Override
    public BbtResponse get(final Integer id) {
        return em.find(BbtResponse.class, id);
    }
}
