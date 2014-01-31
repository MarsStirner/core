package ru.korus.tmis.core.pharmacy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.RbMethodOfAdministration;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        01.10.13, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

//@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbRbMethodOfAdministration implements DbRbMethodOfAdministrationLocal {

    private static final Logger logger = LoggerFactory.getLogger(DbRbMethodOfAdministration.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @Override
    public RbMethodOfAdministration getById(Integer id) {
        return em.find(RbMethodOfAdministration.class, id);
    }
}
