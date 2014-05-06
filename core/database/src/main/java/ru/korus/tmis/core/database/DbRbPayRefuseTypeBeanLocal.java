package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbPayRefuseType;

import javax.ejb.Local;

/**
 * Author: Upatov Egor <br>
 * Date: 31.10.13, 18:42 <br>
 * Company: Korus Consulting IT <br>
 * Description:  Бин для работы с причинами отказа в оплате<br>
 */
@Local
public interface DbRbPayRefuseTypeBeanLocal {
    /**
     * Получение причины отказа в оплате по идентификатору
     * @param id идентификатор
     * @return причина отказа в оплате \ null если не найдено
     */
    RbPayRefuseType getById(int id);

    /**
     * Получение причины отказа в оплате по коду
     * @param code код причины отказа в оплате
     * @return причина отказа в оплате \ null если не найдено
     */
    RbPayRefuseType getByCode(String code);
}
