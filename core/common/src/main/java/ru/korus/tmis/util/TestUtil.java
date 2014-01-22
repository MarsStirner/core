package ru.korus.tmis.util;

import ru.korus.tmis.core.entity.model.RbFinance;
import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.PublicClonable;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.01.14, 13:12 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtil {

    public static Package[] addBaseEntities( ) {
        final Package[] res = { CoreException.class.getPackage(),
        PublicClonable.class.getPackage(),
        RbFinance.class.getPackage(),
        FDRecord.class.getPackage(), };

        return res;
    }
}
