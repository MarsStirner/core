package ru.korus.tmis.core.auth;

import ru.korus.tmis.core.entity.model.Staff;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 18:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface UsersMgrLocal {

    String ROLE_GUEST = "guest";

    String auth(String login, String password);

    List<JsonPerson> getAll();

    JsonPerson getByUUID(String token);

    Staff getStaffByUUID(String token);

    String create(JsonPerson jsonNewPerson);

    boolean isLoginUsed(String login);

    String updateStaff(Staff newStaff, JsonPerson jsonNewPerson);

    void deleteStaff(Staff staff, String token);
}
