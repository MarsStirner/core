package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        10.01.2013, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Подготовка данных для экономических расчетов<br>
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;

import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.ContractTariff;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventType;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.RbFinance;
import ru.korus.tmis.core.entity.model.RbService;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.util.EntityMgr;
import ru.korus.tmis.core.exception.CoreException;

public class FinanceInfo {

    /**
     * Текст сообщения об ошибки о неправильном имени подразделения
     */
    private static final String ILLEGAL_NAME_OF_STRUCT = "illegal name of structure";

    private static final Logger logger = LoggerFactory.getLogger(FinanceInfo.class);

    /**
     * Тип финансирования "Платные услуги"
     */
    private static final Integer PAID_PATIENT = 4;

    /**
     * @see ServiceFinanceInfo#getFinanceInfo(String)
     */
    public FinanceBean[] getFinanceInfo(final String nameOfStructure) {
        try {
            logger.info("Input parameters are equals to: nameOfStructure = '{}' ", nameOfStructure);

            EntityManager em = EntityMgr.getEntityManagerForS11r64(logger);

            if (isNameOfStructureNotValid(nameOfStructure, em)) {
                // если null или подразделения нет в БД
                logger.info("Return error:Illegal name of structure: nameOfStruct = '{}'", nameOfStructure);
                final OutputFinanceInfo res = new OutputFinanceInfo();
                res.initErrorMsg(ILLEGAL_NAME_OF_STRUCT);
                return res.getFinanceData();
            }

            // получаем платные услуги из Action с установленным типом
            // финансирования
            List<Action> actions = getPaidActions(em);
            logger.info("The count of paid action is equal to '{}'", actions.size());
            // добавляем платные услуги с неустановленным типом финансирования и
            // вызванными платными Event
            actions.addAll(getPaidActionsByEvent(em));
            // фильтруем по имени подразделения
            actions = filterByStruct(actions, nameOfStructure);

            logger.info("The count of paid action with by event is equal to '{}'", actions.size());
            logger.debug("The count of paid action by event is equal to '{}'", actions);

            return initOutputData(actions, em).getFinanceData();
        } catch (CoreException ex) {
            throw new RuntimeException("configuration error: em == null!");
        }
    }

    /**
     * Получить все платные услуги с заданным типом финансирования в
     * ActionType.finance_id
     *
     * @param em
     * @return список платных услуг с заданным типом финансирования в
     *         ActionType.finance_id
     */
    private List<Action> getPaidActions(final EntityManager em) {
        // Получаем все платные действия (ActionType.finance_id = PAID_PATIENT)
        // с заданным типом услуги (ActionType.srvice_id is not null)
        // TODO
        return em.createQuery("SELECT a FROM Action a JOIN a.actionType at WHERE at.service IS NOT NULL AND a.financeId = :paid", Action.class)
                .setParameter("paid", PAID_PATIENT).getResultList();
    }

    /**
     * Получить все платные услуги с заданным типом финансирования в
     * EventType.finance_id
     *
     * @param em
     * @return список платных услуг с заданным типом финансирования в
     *         EventType.finance_id
     * @throws IllegalAccessException - см. Class..newInstance()
     * @throws InstantiationException - см. Class..newInstance()
     */
    private List<Action> getPaidActionsByEvent(final EntityManager em) {
        // Получаем все действия с неиницализировнным ActionType.finance_id и
        // с заданным типом услуги (ActionType.srvice_id is not null)
        final List<Action> actions = em.createQuery("SELECT a FROM Action a JOIN a.actionType at WHERE at.service IS NOT NULL AND a.financeId IS NULL",
                Action.class).getResultList();

        final List<Action> result = new LinkedList<Action>();

        // Выбираем платные действия по типу события
        for (Action action : actions) {
            final Event event = action.getEvent();
            if (event != null) {
                final EventType eventType = event.getEventType();
                if (eventType != null) {
                    final RbFinance finance = eventType.getFinance();
                    if (finance != null) {
                        if (finance.getId().equals(PAID_PATIENT)) {
                            // Если текущее действие привязано к платному
                            // событию.
                            result.add(action);
                        }
                    }
                }
            }
        }

        return result;
    }

    private List<Action> filterByStruct(final List<Action> actions, final String nameOfStructure) {
        final List<Action> result = new LinkedList<Action>();
        for (final Action action : actions) {
            final Staff createPerson = action.getCreatePerson();
            if (createPerson != null) {
                final OrgStructure orgStructure = createPerson.getOrgStructure();
                if (orgStructure != null) {
                    if ((nameOfStructure.length() == 0 || orgStructure.getName().equals(nameOfStructure))) {
                        result.add(action);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Получиение стоимости услуги
     *
     * @param serviceId - ID услуги
     * @param em        - доступ к БД s11r64
     * @param endDate   - дата оказания услуги
     * @return стоимость услуги на указанную дату, либо первую найденную если
     *         дата не установлена (endData == null)
     */
    private double getPriceForService(final Integer serviceId, final EntityManager em, final Date endDate) {
        double res = 0;
        List<ContractTariff> contractTariffs = em.createQuery("SELECT ct FROM ContractTariff ct WHERE ct.serviceId = :serviceId", ContractTariff.class)
                .setParameter("serviceId", serviceId).getResultList();
        if (contractTariffs.isEmpty()) { // цена для услуги не найдена
            logger.warn("Tariff for service {} was not found", serviceId);
        } else if (endDate == null) { // дата оказания услуги не установлена
            logger.warn("The end of action is not set for service {}", serviceId);
            res = contractTariffs.get(0).getPrice();
        } else { // поиск цены на задданую дату
            ContractTariff tariffForService = null;
            for (ContractTariff curTariff : contractTariffs) {
                final Date tariffBegDate = curTariff.getBegDate();
                final Date tariffEndDate = curTariff.getEndDate();
                Interval curInterval = new Interval(tariffBegDate != null ? tariffBegDate.getTime() : 0, tariffEndDate != null
                        ? tariffEndDate.getTime() : Long.MAX_VALUE); // строим
                // интервал
                // [begData,
                // endData]
                if (curInterval.contains(endDate.getTime())) { // стоимость
                    // услуги на
                    // заданнyю дату
                    // найдена
                    tariffForService = curTariff;
                    break;
                }
            }

            if (tariffForService == null) {
                logger.warn("The tariff for service {} and date {} is not found", serviceId, endDate);
            } else {
                res = tariffForService.getPrice();
            }
        }
        return res;
    }

    private OutputFinanceInfo initOutputData(final List<Action> actions, final EntityManager em) {
        final OutputFinanceInfo result = new OutputFinanceInfo();
        result.setFinanceData(new FinanceBean[actions.size()]);
        logger.info("The array for output has been allocated. (size = '{}')", actions.size());
        int index = 0;
        for (final Action action : actions) {
            try {
                result.getFinanceData()[index] = new FinanceBean();
                final FinanceBean cur = result.getFinanceData()[index];
                ++index;
                final Event event = EntityMgr.getSafe(action.getEvent());
                cur.setExternalId(event.getExternalId());
                final Date endDate = action.getEndDate();
                final ActionType actionType = EntityMgr.getSafe(action.getActionType());
                final RbService service = EntityMgr.getSafe(actionType.getService());
                cur.setCodeOfService(service.getCode());
                cur.setNameOfService(service.getName());
                cur.setPrice(getPriceForService(service.getId(), em, endDate));

                final Staff executor = EntityMgr.getSafe(action.getExecutor());
                final OrgStructure orgStructure = EntityMgr.getSafe(executor.getOrgStructure());
                cur.setCodeOfStruct(orgStructure.getCode());
                cur.setNameOfStruct(orgStructure.getName());

                if (endDate != null) {
                    final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    cur.setEndDate(dateFormat.format(endDate));
                }
                cur.setAmount(action.getAmount());
            } catch (CoreException ex) {
                logger.info("Cannot prepare input data for action {}. Error description: {}", action.getId(), ex.getMessage());
            }
        }
        logger.info("Exit from OutputFinanceInfo.initOutputData");
        return result;
    }

    private boolean isNameOfStructureNotValid(final String nameOfStructure, final EntityManager em) {
        if (nameOfStructure == null) {
            return true;
        }
        if (nameOfStructure.length() == 0) {
            return false;
        }
        // Получаем описание подразделения
        final List<OrgStructure> orgStructure = em.createQuery("SELECT s FROM OrgStructure s WHERE s.name = :nameOfStruct", OrgStructure.class)
                .setParameter("nameOfStruct", nameOfStructure).getResultList();
        logger.info("The count of Structure is equal to '{}'", orgStructure.size());
        // если подразделение с имененм nameOfStructure не найдено,
        // то возвращаем false
        return orgStructure.isEmpty();
    }

}
