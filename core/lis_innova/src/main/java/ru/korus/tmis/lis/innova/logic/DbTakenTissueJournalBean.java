package ru.korus.tmis.lis.innova.logic;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.TakenTissue;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 16.05.2016, 15:21 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
@Stateless
public class DbTakenTissueJournalBean implements DbTakenTissueJournalBeanLocal{

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    /**
     * Получение биозабора (TTJ) по его идентифкатору  (em.find)
     *
     * @param id идентификатор забора
     * @return Сущность забора \ null если не найдено
     */
    @Override
    public TakenTissue get(final int id) {
        return id > 0 ? em.find(TakenTissue.class, id) : null;
    }

    /**
     * Получения списка диагностических исследований по идентифкатору забора и коду лаборатории
     *
     * @param ttjId   идентифкатор забора
     * @param labCode код лаборатории по которому будут фильтроваться исследования приписанные к забору
     * @return список экшенов - исследований  привязанных к заданному забору и лаборатории
     */
    @Override
    public List<Action> getActionsByTTJAndLaboratoryCode(final int ttjId, final String labCode) {
        return em.createNamedQuery("TTJ.native.GET_ACTIONS_BY_TTJ_AND_LAB_CODE", Action.class).setParameter(1, ttjId).setParameter(2, labCode).getResultList();
    }
}
