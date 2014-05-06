package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalAidUnit;

import javax.ejb.Local;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 19:27 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbMedicalAidUnitBeanLocal {
    public RbMedicalAidUnit getById(int id);

    /**
     * Получение единицы учета медицинской помощи по ее коду
     * @param code    код единицы учета медицинской помощи
     * @return  единица учета мед помощи \ null
     */
    public RbMedicalAidUnit getByCode(final String code);
}
