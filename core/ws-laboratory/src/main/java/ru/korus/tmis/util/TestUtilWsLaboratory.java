package ru.korus.tmis.util;

import ru.korus.tmis.laboratory.across.AcrossRequest;
import ru.korus.validation.Validator;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/22/14
 * Time: 3:44 PM
 */
public class TestUtilWsLaboratory implements TestUtil {

    public Package[] getPackagesForTest( ) {
        return new Package[]{
                AcrossRequest.class.getPackage(),
                ru.korus.tmis.laboratory.altey.AlteyRequestService.class.getPackage(),
                ru.korus.tmis.laboratory.bak.BakRequestService.class.getPackage(),
                ru.korus.tmis.settings.JsonSetting.class.getPackage(),
                ru.korus.validation.rules.IRule.class.getPackage(),
                Validator.class.getPackage(),
                ru.korus.tmis.laboratory.across.AcrossRequestService.class.getPackage()
        };
    }

}
