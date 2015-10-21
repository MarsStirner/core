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
    CasResp createToken(String login, String password);

    CasResp checkToken(String token);

    CasResp checkTokenWithoutProlong(String token);

}
