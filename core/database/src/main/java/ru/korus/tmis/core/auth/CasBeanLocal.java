package ru.korus.tmis.core.auth;

import javax.ejb.Local;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        29.04.2015, 18:23 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface CasBeanLocal {
    String createToken(String login, String password, String defaultToken);

    Boolean checkToken(String defaultToken);

}
