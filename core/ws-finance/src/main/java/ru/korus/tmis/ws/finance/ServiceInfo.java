package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.entity.model.Action;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.04.14, 11:36 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class ServiceInfo {

    /**
     * ID услуги в МИС (в БД МИС  - Action.id)
     */
    private final Integer idService;
    /**
     * код услуги в МИС (в БД МИС  - rbService.code)
     */
    private final String codeService;
    /**
     * количество (в БД МИС - Action.amount)
     */
    private final Double amount;

    public ServiceInfo(Action action) {
        idService = action.getId();
        codeService = action.getActionType().getService().getCode();
        amount = action.getAmount();
    }

    public Integer getIdService() {
        return idService;
    }

    public String getCodeService() {
        return codeService;
    }

    public Double getAmount() {
        return amount;
    }
}
