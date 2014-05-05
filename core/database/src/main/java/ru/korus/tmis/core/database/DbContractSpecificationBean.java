package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.ContractSpecification;
import ru.korus.tmis.core.entity.model.EventType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 09.08.13, 16:37 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbContractSpecificationBean implements DbContractSpecificationBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public ContractSpecification getById(int id) {
        return em.find(ContractSpecification.class, id);
    }

    @Override
    public List<ContractSpecification> getActiveByContract(final Contract contract) {
        return em.createNamedQuery("ContractSpecification.findByContract", ContractSpecification.class)
                .setParameter("contract", contract)
                .getResultList();
    }

    @Override
    public List<EventType> getEventTypeListByContract(final Contract contract) {
        final List<EventType> result = new ArrayList<EventType>();
        for(ContractSpecification current : getActiveByContract(contract)){
            result.add(current.getEventType());
        }
        return result;
    }
}
