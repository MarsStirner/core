package ru.korus.tmis.util;

import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal;
import ru.korus.tmis.laboratory.bak.business.BakBusinessBeanLocal;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilLaboratory implements TestUtil {

    public Package[] getPackagesForTest( ) {
        final Package[] res = {
                AcrossBusinessBeanLocal.class.getPackage(),   // ru.korus.tmis.laboratory.across.business
                BakBusinessBeanLocal.class.getPackage(),      // ru.korus.tmis.laboratory.bak.business
        };
        return res;
    }

}
