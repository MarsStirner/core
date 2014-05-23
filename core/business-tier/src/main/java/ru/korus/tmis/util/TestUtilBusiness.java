package ru.korus.tmis.util;

import ru.korus.tmis.core.assessment.AssessmentBeanLocal;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal;
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal;
import ru.korus.tmis.laboratory.bak.business.BakBusinessBeanLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;
import ru.korus.tmis.prescription.PrescriptionBeanLocal;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilBusiness implements TestUtil {

    public Package[] getPackagesForTest( ) {
        return new Package[]{
                AppealBean.class.getPackage(),                // ru.korus.tmis.core.patient
                CommonDataProcessorBean.class.getPackage(),   // ru.korus.tmis.core.common
                AuthStorageBeanLocal.class.getPackage(),      // ru.korus.tmis.core.auth
                AssessmentBeanLocal.class.getPackage(),       // ru.korus.tmis.core.assessment
                DiagnosticBeanLocal.class.getPackage(),       // ru.korus.tmis.core.diagnostic
                ThesaurusBeanLocal.class.getPackage(),        // ru.korus.tmis.core.thesaurus
                PrescriptionBeanLocal.class.getPackage()      // ru.korus.tmis.prescription
        };
    }

}
