package ru.korus.tmis.util;

import ru.korus.tmis.laboratory.across.AcrossRequest;
import ru.korus.tmis.laboratory.across.AcrossRequestService;
import ru.korus.tmis.laboratory.altey.AlteyRequestService;
import ru.korus.tmis.laboratory.bak.BakRequestService;
import ru.korus.tmis.settings.JsonSetting;
import ru.korus.validation.Validator;
import ru.korus.validation.rules.IRule;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 5/22/14
 * Time: 3:44 PM
 */
public class TestUtilWsLaboratory implements TestUtil {

    public Package[] getPackagesForTest( ) {
        return new Package[]{
                AcrossRequest.class.getPackage(),
                AlteyRequestService.class.getPackage(),
                BakRequestService.class.getPackage(),
                JsonSetting.class.getPackage(),
                IRule.class.getPackage(),
                Validator.class.getPackage(),
                AcrossRequestService.class.getPackage()
        };
    }

}
