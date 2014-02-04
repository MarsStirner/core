package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidType;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        04.02.14, 17:45 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface RbMedicalAidTypeBeanLocal {
    /**
     * Получить тип мед.помощи по идентификатору
     */
    RbMedicalAidType getProfileById(final int id);

}
