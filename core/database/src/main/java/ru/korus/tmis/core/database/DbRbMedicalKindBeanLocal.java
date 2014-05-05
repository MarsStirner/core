package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbMedicalKind;

import javax.ejb.Local;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 14:32 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbMedicalKindBeanLocal {
    public RbMedicalKind getMedicalKindById(int id);

    /**
     * Получение  вида медицинской помощи по ее коду
     * @param code   код вида мед помощи
     * @return   вид помощи \ null
     */
    public RbMedicalKind getMedicalKindByCode(String code);
}
