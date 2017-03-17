package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssessmentsListRequestData;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import ru.korus.tmis.schedule.QueueActionParam;
import scala.Function1;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
     * @return Созданное действие.
     * @throws CoreException
     * @see ActionType
     * @see AuthData
     * @see Action
     */
    Action createAction(int eventId,
                        int actionTypeId,
                        AuthData authData,
                        Staff staff)
            throws CoreException;

    /**
     * Редактирование действия (Action)
     *
     * @param id       Идентификатор действия (Action).
     * @param version  Версия текущего действия (Action).
     * @return Отредактированное действие  (Action).
     * @throws CoreException
     * @see Action
     */
    Action updateAction(int id,
                        int version,
                        AuthData authData,
                        Staff staff)
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
     * @deprecated Использовать метод с фильтром по НИБ
     */
    Action getActionByEventExternalId(String externalId)
            throws CoreException;

    /**
     * Получение списка действий с динамической фильтрацией результатов для заданного обращения
     *
     * @return Список действий (Action).
     * @throws CoreException
     * @see Action
     * @see AssessmentsListRequestData
     */
    List<Action> getActionsWithFilter(int limit,
                                      int page,
                                      String sorting,
                                      ListDataFilter filter,
                                      Function1<Long, Boolean> setRecCount)
            throws CoreException;


    /**
     * Получение списка действий.
     *
     * @param codes    Набор значений кодов типа действия.
     * @param eventId  Идентификатор обращения.
     * @param sort     Строка сортировки.
     * @return Отсортированный список действий (Action).
     * @throws CoreException
     */
    List<Action> getActionsByTypeCodeAndEventId(Set<String> codes, int eventId, String sort)
            throws CoreException;

    List<Action> getActionsByTypeCodeAndPatientOrderByDate(Set<String> codes, Patient patient)
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

    Action createAction(ActionType actionType, Event event, Staff person, Date date, QueueActionParam queueActionParam);

    Action updateAction(Action action);

    @Deprecated
    Action getEvent29AndAction19ForAction (Action action) throws CoreException;

    /**
     * Возвращает количество записей для указанного евента и типа PacientInQueueType (срочно и сверх сетки приема)
     * @param eventId идентификатор обращения
     * @param pacientInQueueType какой-то типа акшена (срочно и сверх сетки приема)
     * @return количество записей как int
     */
    long getActionForEventAndPatientInQueueType(int eventId, long date, int pacientInQueueType) throws CoreException;

    long getActionForDateAndPacientInQueueType(long beginDate, int pacientInQueueType) throws CoreException;

    /**
     * Возвращает список действий относящихся к заданному обращению и типу действия(по flatCode)
     * @param eventId идентификатор обращения
     * @param actionTypeFlatCode значение flatCode типа действия
     * @return Список действий
     */
    List<Action> getActionsByTypeFlatCodeAndEventId(int eventId, String actionTypeFlatCode);

    Action createAction(ActionType queueActionType, Event queueEvent, Staff doctor, Date paramsDateTime, String hospitalUidFrom, String note);

    /**
     * Возвращает список движений пациента для данного обращения
     * @param eventId идентификатор обращения
     * @return Список движений
     */
    List<Action> getMovings(int eventId);

    /**
     * Пометить action как удаленный
     *
     * @param actionId Идентификатор помечаемого действия
     * @return false если удаление не удалось и true  случае успеха
     */
    void removeAction(int actionId, Staff person) throws CoreException;

    /**
     * Закрытие документов для закрытой истории болезни через определенный в
     * конфигурации срок
     */
    void closeAppealsDocs();

    /**
     * Получить список услуг в рамках одного обращения
     *
     * @param eventId - ID обращения
     * @return список услуг
     */
    List<Action> getServiceList(Integer eventId);

    /**
     * Получить список действий в рамках одного обращения
     *
     * @param eventId - ID обращения
     * @return список услуг
     */
    List<Action> getActionsByEvent(Integer eventId);

    Action getLastActionByEventAndActionTypes(Integer eventId, List<String> flatCodeList);

    Action getLastActionByActionTypesAndClientId(List<String> codeList, Integer clientId);

    /**
     * Получить список действий требуемых типов для обращения
     * @param eventId - обращение
     * @param flatCodeList - массив типов действия ActionType.flatCode
     * @return
     */
    List<Action> getActionsByTypeFlatCodeAndEventId(Integer eventId, List<String> flatCodeList);

    /**
     * Получить новейшее движение пациента для обращения action.eventId
     * @return
     */
    Action getLatestMove(Event event);

    List<Action> getAllActionsOfPatientThatHasActionProperty(int patientId, String actionPropertyCode);

    Action getById(int id);

    /**
     * Получение списка экшенов по ActionType.mnem и Action.status
     * @param actionTypeMnemonic строковая мнемоника типа экшенов для фильтрации
     * @param status статус экшена для фильтрации
     * @return  список экшенов с заданным статусом и ActionType.mnem
     */
    List<Action> getActionsByActionTypFlatCodePrefixAndStatus(final String  actionTypeMnemonic, ActionStatus status);

    /**
     * Получение отделения направления из Action (по отделению врача, указанного как исполнитель [Action.person_id])
     * @param action Action для которого нужно получить отделение направления
     * @return Отделение, куда напрвлен Action \ null
     */
    OrgStructure getOrgStructureDirection(Action action);

    /**
     * Установка примечания и статуса для Экшена (em.merge)
     * @param note прмиечание
     * @param actionStatus статус экшена для установки
     */
    Action setActionNoteAndStatus(Action action, String note, ActionStatus actionStatus);

    Action getByUUID(String uid);
}
