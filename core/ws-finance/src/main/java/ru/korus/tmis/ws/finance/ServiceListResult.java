package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 11:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ServiceListResult {

    /**
     * ID обращения в МИС (в БД МИС  - Event.id)
     */
    private Integer idTreatment;

    /**
     * массив с информацией о заказанных услугах
     */
    private List<ServiceInfo> listService = new LinkedList<ServiceInfo>();

    public ServiceListResult(Event event, DbActionBeanLocal dbActionBeanLocal) {
        this.idTreatment = event.getId();
        for(Action action : dbActionBeanLocal.getServiceList(event.getId())) {
           listService.add(new ServiceInfo(action));
        }
    }

    public Integer getIdTreatment() {
        return idTreatment;
    }

    public List<ServiceInfo> getListService() {
        return listService;
    }

    public void setIdTreatment(Integer idTreatment) {
        this.idTreatment = idTreatment;
    }

    public void setListService(List<ServiceInfo> listService) {
        this.listService = listService;
    }
}
