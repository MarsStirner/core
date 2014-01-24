package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtResultOrganism;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        15.10.13, 1:13 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbBbtResultOrganismBean implements DbBbtResultOrganismBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbBbtResultOrganismBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;


    @Override
    public void add(BbtResultOrganism bbtResultOrganism) {
//        final BbtResultOrganism response = get(bbtResultOrganism.getId());
//        if (response == null) {
        em.persist(bbtResultOrganism);
        logger.info("create BbtResultOrganism {}", bbtResultOrganism);
//        } else {
//            logger.info("find BbtResultOrganism {}", response);
//        }
    }

    @Override
    public BbtResultOrganism get(Integer id) {
        List<BbtResultOrganism> resultOrganismList =
                em.createQuery("SELECT a FROM BbtResultOrganism a WHERE a.id = :id", BbtResultOrganism.class).setParameter("id", id).getResultList();
        return !resultOrganismList.isEmpty() ? resultOrganismList.get(0) : null;
    }

    @Override
    public BbtResultOrganism get(Integer organismId, Integer actionId) {
        List<BbtResultOrganism> resultOrganismList =
                em.createQuery("SELECT a FROM BbtResultOrganism a WHERE a.actionId = :actionId AND a.organismId = :organismId", BbtResultOrganism.class)
                        .setParameter("actionId", actionId)
                        .setParameter("organismId", organismId)
                        .getResultList();
        return !resultOrganismList.isEmpty() ? resultOrganismList.get(0) : null;
    }

    @Override
    public List<BbtResultOrganism> getByActionId(int actionId) {
        List<BbtResultOrganism> resultOrganismList =
                em.createQuery("SELECT a FROM BbtResultOrganism a WHERE a.actionId = :actionId", BbtResultOrganism.class)
                        .setParameter("actionId", actionId)
                        .getResultList();
        return resultOrganismList != null ? resultOrganismList : new ArrayList<BbtResultOrganism>(0);
    }

    @Override
    public void remove(Integer id) {
        final BbtResultOrganism bbtResultOrganism = em.find(BbtResultOrganism.class, id);
        if (bbtResultOrganism != null) {
            em.remove(bbtResultOrganism);
            em.flush();
        }
    }
}
