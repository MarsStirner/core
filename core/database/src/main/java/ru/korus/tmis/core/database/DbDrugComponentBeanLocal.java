package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.DrugComponent;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 30.08.13, 18:12 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbDrugComponentBeanLocal {
    public List<DrugComponent> getComponentsByPrescriptionAction(int prescriptionActionId);
}
