package ru.korus.tmis.core.database;

import org.apache.commons.lang.NotImplementedException;
import ru.korus.tmis.core.entity.model.ContractTariff;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 18:22 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbContractTariffBean implements DbContractTariffBeanLocal {
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public ContractTariff getById(int id) {
        return em.find(ContractTariff.class, id);
    }

    @Override
    public List<ContractTariff> getByContractId(int contractId) {
        throw new NotImplementedException();
    }

    @Override
    public void persistTariff(ContractTariff toPersist) {
        em.persist(toPersist);
        em.flush();
    }

    @Override
    public int removeOldTariffs(final Date removeBeforeDate) {
        return em.createNamedQuery("ContractTariff.removeBeforeDate", ContractTariff.class)
                .setParameter("removeDate", removeBeforeDate).executeUpdate();
    }

    @Override
    public void updateTariff(ContractTariff tariff) {
        em.merge(tariff);
        em.flush();
    }
}
