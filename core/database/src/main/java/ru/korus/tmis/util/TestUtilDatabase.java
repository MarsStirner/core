package ru.korus.tmis.util;

import ru.korus.tmis.core.database.DbRbResultBeanLocal;
import ru.korus.tmis.core.database.bak.DbBbtResultTextBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.database.finance.DbEventLocalContractLocal;

import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.notification.NotificationBeanLocal;
import ru.korus.tmis.core.pharmacy.DbDrugChartBeanLocal;
import ru.korus.tmis.core.transmit.TransmitterLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilDatabase implements TestUtil {

    public Package[] getPackagesForTest( ) {
        return new Package[]{
                DbRbResultBeanLocal.class.getPackage(),       // ru.korus.tmis.core.database
                DbEventBeanLocal.class.getPackage(),          // ru.korus.tmis.core.database.common
                DbSchemeKladrBean.class.getPackage(),         // ru.korus.tmis.core.database.kladr
                Database.class.getPackage(),                  // ru.korus.tmis.core.database.dbutil
                PersonScheduleBeanLocal.class.getPackage(),   // ru.korus.tmis.schedule
                DbBbtResultTextBeanLocal.class.getPackage(),  // ru.korus.tmis.core.database.bak
                DbDrugChartBeanLocal.class.getPackage(),      // ru.korus.tmis.core.pharmacy
                NotificationBeanLocal.class.getPackage(),     // ru.korus.tmis.core.notification
                TransmitterLocal.class.getPackage(),          // ru.korus.tmis.core.transmit
                DbEventLocalContractLocal.class.getPackage()
        };
    }

}
