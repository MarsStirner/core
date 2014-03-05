package ru.korus.tmis.util;

import ru.korus.tmis.core.assessment.AssessmentBeanLocal;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.database.DbRbResultBeanLocal;
import ru.korus.tmis.core.database.bak.DbBbtResultTextBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.database.kladr.DbSchemeKladrBean;
import ru.korus.tmis.core.diagnostic.DiagnosticBeanLocal;
import ru.korus.tmis.core.patient.AppealBean;
import ru.korus.tmis.core.thesaurus.ThesaurusBeanLocal;
import ru.korus.tmis.core.treatment.TreatmentBeanLocal;
import ru.korus.tmis.laboratory.across.business.AcrossBusinessBeanLocal;
import ru.korus.tmis.laboratory.bak.business.BakBusinessBeanLocal;
import ru.korus.tmis.schedule.PersonScheduleBeanLocal;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        27.02.14, 13:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtilBusiness implements TestUtil {

    public Package[] getPackagesForTest( ) {
        final Package[] res = {
                DbRbResultBeanLocal.class.getPackage(),       // ru.korus.tmis.core.database
                DbEventBeanLocal.class.getPackage(),          // ru.korus.tmis.core.database.common
                DbSchemeKladrBean.class.getPackage(),         // ru.korus.tmis.core.database.kladr
                Database.class.getPackage(),                  // ru.korus.tmis.core.database.dbutil
                AppealBean.class.getPackage(),                // ru.korus.tmis.core.patient
                CommonDataProcessorBean.class.getPackage(),   // ru.korus.tmis.core.common
                AcrossBusinessBeanLocal.class.getPackage(),   // ru.korus.tmis.laboratory.across.business
                PersonScheduleBeanLocal.class.getPackage(),   // ru.korus.tmis.schedule
                AuthStorageBeanLocal.class.getPackage(),      // ru.korus.tmis.core.auth
                AssessmentBeanLocal.class.getPackage(),       // ru.korus.tmis.core.assessment
                DiagnosticBeanLocal.class.getPackage(),       // ru.korus.tmis.core.diagnostic
                ThesaurusBeanLocal.class.getPackage(),        // ru.korus.tmis.core.thesaurus
                DbBbtResultTextBeanLocal.class.getPackage(),  // ru.korus.tmis.core.database.bak
                TreatmentBeanLocal.class.getPackage(),        // ru.korus.tmis.core.treatment
                BakBusinessBeanLocal.class.getPackage()       // ru.korus.tmis.laboratory.bak.business
        };
        return res;
    }

}
