package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbService;
import ru.korus.tmis.core.entity.model.RbServiceUET;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 20.01.14, 20:48 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbServiceUetBeanLocal {

    /**
     * Получение числа УЕТ услуги в зависимости от возраста (RbServiceUET) по ее идентификатору
     * @param id идентификатор
     * @return  RbServiceUET \ NULL
     */
    RbServiceUET getById(final Integer id);

    /**
     * Получение числа УЕТ услуги в зависимости от возраста (RbServiceUET) по услуге
     * @param service услуга
     * @return  список зависимостей \ пустой список
     */
    List<RbServiceUET> getByService(final RbService service);

}
