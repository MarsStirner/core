package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbServiceFinance;

import javax.ejb.Local;

/**
 * Author: Upatov Egor <br>
 * Date: 07.08.13, 19:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbServiceFinanceBeanLocal {
    public RbServiceFinance getServiceFinanceById(int id);

    /**
     * Получение источника финансирования по его коду
     * @param code  код источника финансирования
     * @return  источник финансирования \ null
     */
    public RbServiceFinance getServiceFinanceByCode(String code);
}
