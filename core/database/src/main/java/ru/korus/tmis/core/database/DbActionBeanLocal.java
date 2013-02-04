package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssessmentsListRequestData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

/**
 * Методы для работы с данными таблицы БД s11r64.Action
 */
@Local
public interface DbActionBeanLocal {

    /**
     * Получение записи Action по идентификатору
     * @param id Идентификатор действия.
     * @return Action.
     * @throws CoreException
     * @see Action
     */
    Action getActionById(int id)
            throws CoreException;

    /**
     * Создание нового действия
     * @param eventId Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id
     * @param userData Авторизационные данные как AuthData.
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
     * @param id Идентификатор действия (Action).
     * @param version Версия текущего действия (Action).
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
     * @param id Идентификатор действия (Action).
     * @param status Новый статус.
     * @return Отредактированное действие  (Action).
     * @throws CoreException
     * @see Action
     */
    Action updateActionStatus(int id, short status)
            throws CoreException;

    /**
     * Получение действия для обращения заданного типа
     * @param eventId Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id.
     * @return Одно из незакрытых действий заданного типа для заданного обращения.
     * @throws CoreException
     * @see Action
     */
    Action getAppealActionByEventId(int eventId, int actionTypeId)
            throws CoreException;

    /**
     * Получение действия для обращения, заданного номером истории болезни
     * @param externalId Номер истории болезни
     * @return Действие (Action).
     * @throws CoreException
     * @deprecated Использовать метод с фильтром по НИБ {@link #getActionsByEventIdWithFilter(int, ru.korus.tmis.core.auth.AuthData, ru.korus.tmis.core.data.AssessmentsListRequestData)}
     */
    Action getActionByEventExternalId(String externalId)
            throws CoreException;

    /**
     * Получение списка действий с динамической фильтрацией результатов для заданного обращения
     * @param eventId Идентификатор обращения.
     * @param userData Авторизационные данные как AuthData.
     * @param requestData Фильтры значений списка из запроса клиента как AssessmentsListRequestData
     * @return Список действий (Action).
     * @throws CoreException
     * @see Action
     * @see AssessmentsListRequestData
     */
    List<Action> getActionsByEventIdWithFilter(int eventId, AuthData userData, AssessmentsListRequestData requestData)
            throws CoreException;

    /**
     * Получение списка действий по коду типа действия.
     * @param code Код типа действия s11r64.ActionType.code.
     * @return Список действий (Action).
     * @throws CoreException
     * @deprecated Использовать {@link #getActionsByTypeCodeAndEventId(java.util.Set, int, String, ru.korus.tmis.core.auth.AuthData)}
     */
    List<Action> getActionsByTypeCode(String code)
            throws CoreException;

    /**
     * Получение списка действий.
     * @param codes Набор значений кодов типа действия.
     * @param eventId Идентификатор обращения.
     * @param sort Строка сортировки.
     * @param userData Авторизационные данные как AuthData.
     * @return Отсортированный список действий (Action).
     * @throws CoreException
     */
    List<Action> getActionsByTypeCodeAndEventId(Set<String> codes, int eventId, String sort, AuthData userData)
            throws CoreException;

    /**
     * Возвращает идентификатор последнего действия заданного типа из предыдущего обращения для пациента, для которого заведено текущее обращение.<br>
     * "Копирование"
     * @param eventId Идентификатор обращения.
     * @param actionTypeId Идентификатор типа действия s11r64.ActionType.id.
     * @return Возвращает идентификатор действия s11r64.Action.id
     * @throws CoreException
     */
    int getActionIdWithCopyByEventId(int eventId, int actionTypeId) throws CoreException;

    /**
     * Возвращает идентификатор последнего действия заданного типа для текущего обращения
     * @param eventId Идентификатор обращения.
     * @param actionTypeIds Список идентификаторов типа действия s11r64.ActionType.id.
     * @return Возвращает идентификатор действия s11r64.Action.id
     * @throws CoreException
     */
    int getLastActionByActionTypeIdAndEventId(int eventId, Set<Integer> actionTypeIds) throws CoreException;
}
