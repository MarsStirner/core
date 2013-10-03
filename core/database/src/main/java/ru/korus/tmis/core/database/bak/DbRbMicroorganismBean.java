package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.RbMicroorganism;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        03.10.13, 1:44 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbRbMicroorganismBean implements DbRbMicroorganismBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbRbMicroorganismBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public void add(RbMicroorganism rbMicroorganism) {
       // final RbMicroorganism response = em.find(RbMicroorganism.class, rbMicroorganism.getId());
      //  if (response == null) {
            em.persist(rbMicroorganism);
            logger.info("create RbMicroorganism {}", rbMicroorganism);
        //} else {
          //  logger.info("find RbMicroorganism {}", response);
       // }
    }

    @Override
    public RbMicroorganism get(Integer id) {
        return em.find(RbMicroorganism.class, id);
    }
}
