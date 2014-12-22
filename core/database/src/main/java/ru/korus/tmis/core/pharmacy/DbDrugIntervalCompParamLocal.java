package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.pharmacy.DrugComponent;
import ru.korus.tmis.core.entity.model.pharmacy.DrugIntervalCompParam;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.12.2014, 17:08 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbDrugIntervalCompParamLocal {
    DrugIntervalCompParam create(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa);

    void update(DrugChart drugChart, DrugComponent drugComponent, Double dose, Double voa);

    List<DrugIntervalCompParam> getCompParamByInterval(DrugChart drugChart);
}
