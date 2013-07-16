package ru.korus.tmis.ws.transfusion;

import java.util.Date;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        16.05.2013, 12:26:17 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Выгрузка данных из БД для передачи в подсистему ТРФУ
 */
public class SenderUtils {

    /**
     * Идентификатор врача, назначившего трансфузию
     * 
     * @param action
     *            - действие, передаваемое в подсистему ТРФУ
     * @param trfuActionProp
     *            - доступ к типам свойств действия
     * @return Идентификатор врача, назначившего трансфузию
     * @throws CoreException
     *             - если не найден идентификатор врача, назначившего трансфузию
     */
    public Staff getAssigner(final Action action, TrfuActionProp trfuActionProp) throws CoreException {
        final Staff assigner = action.getAssigner();
        if (assigner == null) {
            trfuActionProp.setErrorState(action, "Ошибка: Не задан врач, назначивший трансфузию");
        }
        return assigner;
    }

    /**
     * Планируемая дата трансфузии
     * 
     * @param action
     *            - действие, передаваемое в подсистему ТРФУ
     * @return планируемая дата трансфузии
     * @throws CoreException
     *             - если не задана планируемая дата трансфузии
     */
    public Date getPlannedData(final Action action, TrfuActionProp trfuActionProp) throws CoreException {
        final Date plannedEndDate = action.getPlannedEndDate();
        if (plannedEndDate == null) {
            trfuActionProp.setErrorState(action, "Ошибка: Не задана планируемая дата трансфузии");
        }
        return plannedEndDate;
    }

    /**
     * Номер истории болезни
     * 
     * @param action
     *            - действие, передаваемое в подсистему ТРФУ
     * @param event
     *            - событие действия
     * @return номер истории болезни
     * @throws CoreException
     *             - если не задан омер истории болезни
     */
    public String getIbNumbre(final Action action, final Event event, TrfuActionProp trfuActionProp) throws CoreException {
        String ibNumber = event.getExternalId();
        if (ibNumber == null || "".equals(ibNumber.trim())) {
            trfuActionProp.setErrorState(action, "Ошибка: Для выбранного пациента нет номера истории болезни");
        }
        return ibNumber.trim();
    }

}
