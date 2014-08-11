package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.bak.RbMicroorganism;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    public RbMicroorganism add(RbMicroorganism rbMicroorganism) {
        //     _________________
        //    < Please kill me  >
        //     -----------------
        //            \   ^__^
        //             \  (oo)\_______
        //                (__)\       )\/\
        //                    ||----w |
        //                    ||     ||
        //
       // Скажите спасибо консультантам по интеграции БАК, не способным в анализ, документацию и еще во много чего
       //final RbMicroorganism response = get(rbMicroorganism.getCode());
       //if (response == null) {
        em.persist(rbMicroorganism);
        logger.info("create RbMicroorganism {}", rbMicroorganism);
        return rbMicroorganism;
       //} else {
       //     logger.info("find RbMicroorganism {}", response);
       //}
    }

    @Override
    public RbMicroorganism get(String code) {
        List<RbMicroorganism> microList =
                em.createQuery("SELECT a FROM RbMicroorganism a WHERE a.code = :code", RbMicroorganism.class)
                        .setParameter("code", code).getResultList();
        return !microList.isEmpty() ? microList.get(0) : null;

    }
}
