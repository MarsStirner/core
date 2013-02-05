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

import org.slf4j.Logger;

import ru.korus.tmis.core.exception.CoreException;

public class EntityMgr {
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
