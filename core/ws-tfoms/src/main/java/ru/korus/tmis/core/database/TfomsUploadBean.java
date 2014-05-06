package ru.korus.tmis.core.database;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Author: Upatov Egor <br>
 * Date: 13.02.14, 16:52 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class TfomsUploadBean implements TFOMSUploadBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public void clearSerial() {
        em.createNativeQuery("SET @serial := 0;").executeUpdate();
    }
}
