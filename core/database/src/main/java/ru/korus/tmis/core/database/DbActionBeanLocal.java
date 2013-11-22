package ru.korus.tmis.core.database;

import com.google.common.collect.ImmutableSet;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssessmentsListRequestData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import scala.Function1;

import javax.ejb.Local;
import java.util.*;

/**
 * Методы для работы с данными таблицы БД s11r64.Action
 */
@Local
public interface DbActionBeanLocal {

    /**
     * Получение записи Action по идентификатору
     *
     * @param id Идентификатор действия.
     * @return Action.
     * @throws CoreException
     * @see Action
     */
    Action getActionById(int id)
            throws CoreException;

    /**
     * Получение записи Action по идентификатору c любым статусом deleted
     *
     * @param id Идентификатор действия.
     * @return Action.
     * @throws CoreException
     * @see Action
     */
    Action getActionByIdWithIgnoreDeleted(int id)
            throws CoreException;

    /**
     * Создание нового действия
     *
     * @param eventId      Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id
     * @param userData     Авторизационные данные как AuthData.
     * @return Созданное действие.
     * @throws CoreException
     * @see ActionType
     * @see AuthData
     * @see Action
     */
    Action createAction(int eventId,
                        int actionTypeId,
                        AuthData userData)
            throws CoreException;

    /**
     * Редактирование действия (Action)
     *
     * @param id       Идентификатор действия (Action).
     * @param version  Версия текущего действия (Action).
     * @param userData Авторизационные данные как AuthData.
     * @return Отредактированное действие  (Action).
     * @throws CoreException
     * @see Action
     */
    Action updateAction(int id,
                        int version,
                        AuthData userData)
            throws CoreException;

    /**
     * Обновление статуса действия  (Action)
     *
     * @param id     Идентификатор действия (Action).
     * @param status Новый статус.
     * @return Отредактированное действие  (Action).
     * @throws CoreException
     * @see Action
     */
    Action updateActionStatus(int id, short status)
            throws CoreException;

    /**
     * Обновление статуса действия  (Action)
     *
     * @param id     Идентификатор действия (Action).
     * @param status Новый статус.
     * @return Отредактированное действие  (Action).
     * @throws CoreException
     * @see Action
     */
    void updateActionStatusWithFlush(int id, short status)
            throws CoreException;
    /**
     * Получение действия для обращения заданного типа
     *
     * @param eventId      Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id.
     * @return Одно из незакрытых действий заданного типа для заданного обращения.
     * @throws CoreException
     * @see Action
     */
    Action getAppealActionByEventId(int eventId, int actionTypeId)
            throws CoreException;

    /**
     * Получение действия для обращения, заданного номером истории болезни
     *
     * @param externalId Номер истории болезни
     * @return Действие (Action).
     * @throws CoreException
     * @deprecated Использовать метод с фильтром по НИБ {@link #getActionsByEventIdWithFilter(int, ru.korus.tmis.core.auth.AuthData, ru.korus.tmis.core.data.AssessmentsListRequestData)}
     */
    Action getActionByEventExternalId(String externalId)
            throws CoreException;

    /**
     * Получение списка действий с динамической фильтрацией результатов для заданного обращения
     *
     * @param userData Авторизационные данные как AuthData.
     * @return Список действий (Action).
     * @throws CoreException
     * @see Action
     * @see AssessmentsListRequestData
     */
    List<Action> getActionsWithFilter(int limit,
                                      int page,
                                      String sorting,
                                      ListDataFilter filter,
                                      Function1<Long, Boolean> setRecCount,
                                      AuthData userData)
            throws CoreException;

    /**
     * Получение списка действий по коду типа действия.
     *
     * @param code     Код типа действия s11r64.ActionType.code.
     * @param userData Авторизационные данные как AuthData.
     * @return Список действий (Action).
     * @throws CoreException
     * @deprecated Использовать {@link #getActionsByTypeCodeAndEventId(java.util.Set, int, String, ru.korus.tmis.core.auth.AuthData)}
     */
    List<Action> getActionsByTypeCode(String code, AuthData userData)
            throws CoreException;

    /**
     * Получение списка действий.
     *
     * @param codes    Набор значений кодов типа действия.
     * @param eventId  Идентификатор обращения.
     * @param sort     Строка сортировки.
     * @param userData Авторизационные данные как AuthData.
     * @return Отсортированный список действий (Action).
     * @throws CoreException
     */
    List<Action> getActionsByTypeCodeAndEventId(Set<String> codes, int eventId, String sort, AuthData userData)
            throws CoreException;

    /**
     * Возвращает идентификатор последнего действия заданного типа из предыдущего обращения (если осмотр) или текущего обращения для пациента, для которого заведено текущее обращение.<br>
     * "Копирование"
     *
     * @param eventId      Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id.
     * @return Возвращает идентификатор действия s11r64.Action.id
     * @throws CoreException
     */
    int getActionIdWithCopyByEventId(int eventId, int actionTypeId) throws CoreException;

    /**
     * Возвращает идентификатор последнего действия заданного типа для текущего обращения
     *
     * @param eventId       Идентификатор обращения.
     * @param actionTypeIds Список идентификаторов типа действия s11r64.ActionType.id.
     * @return Возвращает идентификатор действия s11r64.Action.id
     * @throws CoreException
     */
    int getLastActionByActionTypeIdAndEventId(int eventId, Set<Integer> actionTypeIds) throws CoreException;

    ActionType getActionTypeByCode(String code) throws CoreException;

    Action createAction(ActionType actionType, Event event, Staff person, Date date, String hospitalUidFrom, String note);

    Action getEvent29AndAction19ForAction (Action action) throws CoreException;

    /**
     * Возвращает количество записей для указанного евента и типа PacientInQueueType (срочно и сверх сетки приема)
     * @param eventId идентификатор обращения
     * @param pacientInQueueType какой-то типа акшена (срочно и сверх сетки приема)
     * @return количество записей как int
     */
    long getActionForEventAndPacientInQueueType(int eventId, long date, int pacientInQueueType) throws CoreException;

    long getActionForDateAndPacientInQueueType(long beginDate, int pacientInQueueType) throws CoreException;

    /**
     * Возвращает список действий относящихся к заданному обращению и типу действия(по flatCode)
     * @param eventId идентификатор обращения
     * @param actionTypeFlatCode значение flatCode типа действия
     * @return Список действий
     */
    List<Action> getActionsByTypeFlatCodeAndEventId(int eventId, String actionTypeFlatCode);
}
