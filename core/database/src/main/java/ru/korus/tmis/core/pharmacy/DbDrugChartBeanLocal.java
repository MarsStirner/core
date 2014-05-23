package ru.korus.tmis.core.pharmacy;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.pharmacy.DrugChart;
import ru.korus.tmis.core.entity.model.Event;

import javax.ejb.Local;
import java.util.Date;
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

    Iterable<DrugChart> getIntervalsByEvent(Event event);

    DrugChart create(final Action action, Integer masterId, Date begDateTime, Date endDateTime, Short status, String note);
}
