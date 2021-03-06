package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.database.common.DbEventBeanLocal;
import ru.korus.tmis.core.database.finance.*;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventLocalContract;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.04.14, 11:03 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class PaymentBean implements PaymentBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @EJB
    DbEventBeanLocal dbEventBeanLocal;

    @EJB
    DbActionBeanLocal dbActionBeanLocal;

    @EJB
    DbEventPaymentLocal dbEventPaymentLocal;

    @EJB
    DbEventLocalContractLocal dbEventLocalContractLocal;

    @Override
    public ServiceListResult getServiceList(Integer idTreatment) throws CoreException {
        Event event =  dbEventBeanLocal.getEventById(idTreatment);
        return new ServiceListResult(event, dbActionBeanLocal, em);
    }

    @Override
    public Integer setPaymentInfo(Date datePaid,
                                  String codeContract,
                                  Date dateContract,
                                  Integer idTreatment,
                                  PersonName paidName,
                                  Date birthDate,
                                  List<ServicePaidInfo> listService) throws CoreException {
        if(codeContract == null) {
            throw new CoreException("An input parameter is invalid: codeContract == null" );
        }
        Event event = idTreatment == null ? null : em.find(Event.class, idTreatment);
        EventLocalContract eventLocalContract = codeContract == null ? null : dbEventLocalContractLocal.getByContractNumber(codeContract);
        PersonFIO paidFIO = new PersonFIO(paidName.getFamily(), paidName.getGiven(), paidName.getPartName());
        if ( eventLocalContract != null && eventLocalContract.getEvent() != null && idTreatment != null &&
            !idTreatment.equals(eventLocalContract.getEvent().getId())) { //если не сопадает ID обращение и номер договора не совпадают с данными в БД МИС, то возвращаем ошибку
            throw new CoreException("Incompatible event ID and code of contract: idTreatment = " + idTreatment + " codeContract = " + codeContract
                    + " The code of contract for this event should be equals to: " + eventLocalContract.getEvent().getId());
        } else if (event.getEventLocalContract() != null) {
            event.getEventLocalContract().setDateContract(dateContract);
            event.getEventLocalContract().setNumberContract(codeContract);
            em.merge(event);
            eventLocalContract = event.getEventLocalContract();
        } else if (eventLocalContract == null) { //если в МИС нет договора, то создаем его
            eventLocalContract = dbEventLocalContractLocal.create(codeContract, dateContract, event, paidFIO, birthDate);
        }

        for(ServicePaidInfo servicePaidInfo : listService) {
            Action action = servicePaidInfo.getIdAction() == null ? null : dbActionBeanLocal.getActionById(servicePaidInfo.getIdAction());
            dbEventPaymentLocal.savePaidInfo(event, datePaid, eventLocalContract, paidFIO, action, servicePaidInfo);
        }
        em.flush();
        return idTreatment;
    }

}
