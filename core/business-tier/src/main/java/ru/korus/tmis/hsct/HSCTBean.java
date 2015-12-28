package ru.korus.tmis.hsct;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.common.DbActionBeanLocal;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.hsct.external.HsctExternalRequest;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Author: Upatov Egor <br>
 * Date: 23.12.2015, 16:40 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Stateless
public class HsctBean  {
    private final static Logger LOGGER = LoggerFactory.getLogger("HSCT");

    @EJB
    private DbActionBeanLocal dbAction;

    public HsctResponse sendActionToHsct(final int actionId) {
        try {
            final Action action = dbAction.getActionById(actionId);
            //todo extract to validate method
            if(action == null){
                LOGGER.error("Action[{}] not found", actionId);
                return createErrorResponse("Action[" + actionId + "] not found");
            }
            if(!StringUtils.equals(Constants.ACTION_TYPE_CODE, action.getActionType().getCode())){
                LOGGER.error("Action[{}] has invalid ActionType.code=\'{}\' valid is \'{}\'", actionId, action.getActionType().getCode(), Constants.ACTION_TYPE_CODE);
                return createErrorResponse("Action[" + actionId + "] has invalid ActionType.code=\'"+action.getActionType().getCode()+'\'');
            }
            HsctExternalRequest externalRequest = constructExternalHsctRequest(action);
            return new HsctResponse();
        } catch (Exception e) {
            LOGGER.error("HsctBean.sendActionToHsct error:", e);
            return createErrorResponse(e.getMessage());
        }
    }

    private HsctExternalRequest constructExternalHsctRequest(final Action action) {
        //todo
       final HsctExternalRequest result = new HsctExternalRequest();
        return result;

    }

    private HsctResponse createErrorResponse(final String message) {
        final HsctResponse result = new HsctResponse();
        result.setError(true);
        result.setErrorMessage(message);
        return result;
    }
}
