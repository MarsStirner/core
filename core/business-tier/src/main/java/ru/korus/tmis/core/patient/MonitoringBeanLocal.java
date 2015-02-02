package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        26.12.2014, 12:18 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface MonitoringBeanLocal {

    List<InfectionDrugMonitoring> getInfectionDrugMonitoring(Event event) throws CoreException;
}
