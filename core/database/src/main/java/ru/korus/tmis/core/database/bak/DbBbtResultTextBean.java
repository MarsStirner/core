package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.BbtResultText;
import ru.korus.tmis.core.entity.model.bak.RbAntibiotic;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        16.10.13, 13:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbBbtResultTextBean implements DbBbtResultTextBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger(DbBbtResultTextBean.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;


    @Override
    public void add(BbtResultText bbtResultText) {
//        final BbtResultText response = get(rbAntibiotic.getCode());
//        if (response == null) {
            em.persist(bbtResultText);
//            logger.info("create RbAntibiotic {}", rbAntibiotic);
//        } else {
//            logger.info("find RbAntibiotic {}", response);
//        }
    }

    @Override
    public BbtResultText get(Integer id) {
        List<BbtResultText> antibioticList =
                em.createQuery("SELECT a FROM BbtResultText a WHERE a.id = :id", BbtResultText.class).setParameter("id", id).getResultList();
        return !antibioticList.isEmpty() ? antibioticList.get(0) : null;
    }
}
