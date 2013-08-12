package ru.korus.tmis.util;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.01.13, 12:08 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;

import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;
import java.util.GregorianCalendar;

public class EntityMgr {

    /**
     * Статус действия: Начато {Action.status}
     */
    public static final short ACTION_STATE_STARTED = 0;

    /**
     * Статус действия: Ожидание {Action.status}
     */
    public static final short ACTION_STATE_WAIT = 1;

    /**
     * Статус действия: Закончено {Action.status}
     */
    public static final short ACTION_STATE_FINISHED = 2;

    /**
     * Имя ресурса JDBC для доступа к БД s11r64
     */
    private static final String JNDI_NAME = "s11r64";

    private static EntityManagerFactory entityManagerFactory = null;
    
    public static EntityManager getEntityManagerForS11r64(final Logger logger) throws CoreException {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            entityManagerFactory = Persistence.createEntityManagerFactory(JNDI_NAME);
        }
        if (entityManagerFactory == null) {
            if (logger != null) {
                logger.error("The persistence unit '{}' is not found. Probably corresponding JDBC resource is not available", JNDI_NAME);
            }
            throw new CoreException();
        }
        EntityManager res = entityManagerFactory.createEntityManager();
        if (res == null) {
            throw new RuntimeException("configuration error: em == null!");
        }
        return res;
    }

    /**
     * Преобразовать Date в XMLGregorianCalendar
     *
     * @param date
     * @return XMLGregorianCalendar соответсвующий <code>date</code>
     * @throws javax.xml.datatype.DatatypeConfigurationException
     *             если не возможно создать экземпляр XMLGregorianCalendar (@see {@link javax.xml.datatype.DatatypeFactory#newInstance()})
     */
    public static XMLGregorianCalendar toGregorianCalendar(final Date date) throws DatatypeConfigurationException {
        if (date == null) {
            throw new DatatypeConfigurationException();
        }
        final GregorianCalendar planedDateCalendar = new GregorianCalendar();
        planedDateCalendar.setTime(date);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(planedDateCalendar);
    }
    
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory == null) {
            throw new IllegalStateException();
        }
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
            
    }

    public static <T> T getSafe(T obj) throws CoreException {
        if (obj == null) {
            throw new CoreException("The instance of input object is null");
        }
        return obj;
    }



}
