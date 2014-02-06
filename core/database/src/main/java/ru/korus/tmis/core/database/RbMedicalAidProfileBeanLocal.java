package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidProfile;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:29 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface RbMedicalAidProfileBeanLocal {

    /**
     * Получить профиль мед.помощи по идентификатору
     */
    RbMedicalAidProfile getProfileById(final int id);
}
