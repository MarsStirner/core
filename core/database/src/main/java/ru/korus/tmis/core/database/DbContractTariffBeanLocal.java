package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ContractTariff;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 17:38 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbContractTariffBeanLocal {
    public ContractTariff getById(int id);
    public List<ContractTariff> getByContractId(int contractId);
    public void persistTariff(ContractTariff toPersist);
    public int removeOldTariffs(final Date removeBeforeDate);

    public void updateTariff(ContractTariff tariff);
}
