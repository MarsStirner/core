package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtResponse;
import ru.korus.tmis.core.entity.model.bak.RbMicroorganism;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
        final BbtResponse response = get(bbtResponse.getId());
        if (response == null) {
            em.persist(bbtResponse);
            logger.info("create BbtResponse {}", bbtResponse);
        } else {
            logger.info("find BbtResponse {}", response);
        }
    }

    @Override
    public BbtResponse get(final Integer id) {
        List<BbtResponse> responseList =
                em.createQuery("SELECT a FROM BbtResponse a WHERE a.id = :id", BbtResponse.class)
                        .setParameter("id", id).getResultList();
        return !responseList.isEmpty() ? responseList.get(0) : null;
    }

    @Override
    public void remove(int id) {
        final BbtResponse bbtResponse = em.find(BbtResponse.class, id);
        em.remove(bbtResponse);
        em.flush();
    }


}
