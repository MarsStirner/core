package ru.korus.tmis.util;


/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilWsMedipad implements TestUtil {

    public Package[] getPackagesForTest( ) {
        return new Package[]{
                ru.korus.tmis.ws.medipad.AuthenticationWebService.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.AppealRegistryRESTImpl.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.servlet.AuthenticationFilter.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage.class.getPackage(),
                ru.korus.tmis.ws.handlers.AuthenticatingHandler.class.getPackage(),
                ru.korus.tmis.ws.impl.AuthenticationWSImpl.class.getPackage(),

        };
    }

}
