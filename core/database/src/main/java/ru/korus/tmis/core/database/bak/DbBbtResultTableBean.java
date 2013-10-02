package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtResponse;
import ru.korus.tmis.core.entity.model.bak.BbtResultTable;

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
public class DbBbtResultTableBean implements DbBbtResultTableBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(DbBbtResultTableBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public void add(final BbtResultTable bbtResponse) {
        final BbtResultTable response = em.find(BbtResultTable.class, bbtResponse.getId());
        if (response == null) {
            em.persist(response);
            logger.info("create BbtResponse {}", response);
        } else {
            logger.info("find BbtResponse {}", response);
        }
    }

    @Override
    public BbtResultTable get(final Integer id) {
        return em.find(BbtResultTable.class, id);
    }
}
