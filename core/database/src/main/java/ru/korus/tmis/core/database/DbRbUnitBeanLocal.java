package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbUnit;

import javax.ejb.Local;

/**
 * Author: Upatov Egor <br>
 * Date: 02.09.13, 17:20 <br>
 * Company: Korus Consulting IT <br>
 * Description: Бин по работе со справочником rbUnit <br>
 */
@Local
public interface DbRbUnitBeanLocal {
    /**
     * Получение единицы измерения по идентификатору
     * @param id идентификатор
     * @return Единица измерения
     */
    public RbUnit getById(int id);

    /**
     * Получение единицы измерения по коду
     * @param code код единицы измерения
     * @return Единица измерения
     */
    public RbUnit getByCode(String code);
}
