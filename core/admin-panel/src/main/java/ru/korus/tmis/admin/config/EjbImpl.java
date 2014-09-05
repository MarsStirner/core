package ru.korus.tmis.admin.config;

import ru.korus.tmis.core.auth.AuthStorageBeanLocal;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        04.09.14, 9:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class EjbImpl implements EjbLocal {

    @EJB
    AuthStorageBeanLocal authStorageBeanLocal;

    @Override
    public AuthStorageBeanLocal getAuthStorageBeanLocal() {
        return authStorageBeanLocal;
    }
}
