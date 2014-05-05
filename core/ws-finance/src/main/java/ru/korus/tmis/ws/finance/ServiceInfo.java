package ru.korus.tmis.ws.finance;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbService;

import java.math.BigInteger;

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
    private BigInteger idService;
    /**
     * код услуги в МИС (в БД МИС  - rbService.code)
     */
    private String codeService;
    /**
     * количество (в БД МИС - Action.amount)
     */
    private Double amount;

    public ServiceInfo(Action action) {
        idService = BigInteger.valueOf(action.getId());
        final RbService service = action.getActionType().getService();
        codeService = service == null ? null : service.getCode();
        amount = action.getAmount();
    }

    public BigInteger getIdService() {
        return idService;
    }

    public String getCodeService() {
        return codeService;
    }

    public Double getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceInfo)) return false;

        ServiceInfo that = (ServiceInfo) o;

        if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
        if (codeService != null ? !codeService.equals(that.codeService) : that.codeService != null) return false;
        if (idService != null ? !idService.equals(that.idService) : that.idService != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idService != null ? idService.hashCode() : 0;
        result = 31 * result + (codeService != null ? codeService.hashCode() : 0);
        result = 31 * result + (amount != null ? amount.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("ServiceInfo");
        sb.append("{idService=").append(idService);
        sb.append(", codeService='").append(codeService).append('\'');
        sb.append(", amount=").append(amount);
        sb.append('}');
        return sb.toString();
    }

    public void setIdService(BigInteger idService) {
        this.idService = idService;
    }

    public void setCodeService(String codeService) {
        this.codeService = codeService;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
