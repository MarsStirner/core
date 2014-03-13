package ru.korus.tmis.util;

import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.database.DbRbResultBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;
import ru.korus.tmis.util.TestUtil;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilWsMedipad implements TestUtil {

    public Package[] getPackagesForTest( ) {
        final Package[] res = new Package[]{
                ru.korus.tmis.ws.medipad.AuthenticationWebService.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.AppealRegistryRESTImpl.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.servlet.RestServlet.class.getPackage(),
                ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage.class.getPackage(),
                ru.korus.tmis.ws.handlers.AuthenticatingHandler.class.getPackage(),
                ru.korus.tmis.ws.impl.AuthenticationWSImpl.class.getPackage(),
        };
        return res;
    }

}
