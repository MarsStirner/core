package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.DrugChart;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 13:25 <br>
 * Company: Korus Consulting IT <br>
 * Description: Интерфейс бина по работе с Назначениями Препаратов <br>
 */
@Local
public interface DbDrugChartBeanLocal {

    List<DrugChart> getPrescriptionIntervals(int prescriptionActionId);

    List<DrugChart> getExecIntervals(int prescriptionActionId, int masterId);
}
