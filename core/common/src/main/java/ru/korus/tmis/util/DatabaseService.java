package ru.korus.tmis.util;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Remote;
import javax.persistence.EntityManager;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        09.08.13, 12:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Remote
public interface DatabaseService {
    @SuppressWarnings("unchecked")
    <T> T getSingleProp(@SuppressWarnings("rawtypes") Class classType,
                        int actionId, int propTypeId) throws CoreException;

    <T> T getSingleProp(@SuppressWarnings("rawtypes") Class classType,
                        int actionId,
                        int propTypeId,
                        T defaultVal);

    <T> int addSinglePropBasic(T value,
                               @SuppressWarnings("rawtypes") Class classType,
                               int actionId,
                               int propTypeId,
                               boolean isUpdate) throws CoreException;

    List<Action> getNewActionByFlatCode(String flatCode);

    List<ActionProperty> getActionProp(int actionId, int propTypeId);

    Staff getCoreUser();
}
