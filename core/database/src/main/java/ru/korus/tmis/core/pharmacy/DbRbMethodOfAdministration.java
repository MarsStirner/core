package ru.korus.tmis.core.pharmacy;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbActionPropertyBeanLocal;
import ru.korus.tmis.core.database.DbActionPropertyTypeBeanLocal;
import ru.korus.tmis.core.database.DbManagerBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.entity.model.pharmacy.PharmacyStatus;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        01.10.13, 17:30 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

@Interceptors(LoggingInterceptor.class)
@Stateless
public class DbRbMethodOfAdministration implements DbRbMethodOfAdministrationLocal {

    private static final Logger logger = LoggerFactory.getLogger(DbRbMethodOfAdministration.class);

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Override
    public RbMethodOfAdministration getById(Integer id) {
        return em.find(RbMethodOfAdministration.class, id);
    }
}
