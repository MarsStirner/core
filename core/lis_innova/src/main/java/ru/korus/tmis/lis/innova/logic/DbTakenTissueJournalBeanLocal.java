package ru.korus.tmis.lis.innova.logic;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.TakenTissue;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 16.05.2016, 15:12 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Local
public interface DbTakenTissueJournalBeanLocal {

    /**
     * Получение биозабора (TTJ) по его идентифкатору  (em.find)
     *
     * @param id идентификатор забора
     * @return Сущность забора \ null если не найдено
     */
    TakenTissue get(int id);

    /**
     * Получения списка диагностических исследований по идентифкатору забора и коду лаборатории
     *
     * @param ttjId   идентифкатор забора
     * @param labCode код лаборатории по которому будут фильтроваться исследования приписанные к забору
     * @return список экшенов - исследований  привязанных к заданному забору и лаборатории
     */
    List<Action> getActionsByTTJAndLaboratoryCode(int ttjId, String labCode);
}
