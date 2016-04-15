package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;
import scala.Function2;

import javax.ejb.Local;

/**
 * Методы для работы с Направлениями
 * @author mmakankov Systema-Soft
 * @since 1.0.0.70
 */
@Local
public interface DirectionBeanLocal {

    /**
     * Метод получения направления на лабораторное исследование по идентификатору
     * @param directionId id лабораторного исследования
     * @param title  вспомогательный параметр название действия (Диагнозтика).
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JSONCommonData getDirectionById(int directionId,
                                    String title,
                                    Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis,
                                    Staff staff)
           throws CoreException;

    /**
     * Метод создания направления на лабораторные исследования
     * @param directions Json с данными о лабораторных исследованиях как CommonData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается исследование.
     * @param title  вспомогательный параметр название действия (Диагнозтика).
     * @param request Параметры запроса с клиента.
     * @param mnem Мнемоника исследования.
     * @param userData Данные пользователя
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JSONCommonData  createDirectionsForEventIdFromCommonData(int eventId,
                                                             CommonData directions,
                                                             String title,
                                                             Object request,
                                                             String mnem,
                                                             AuthData userData,
                                                             Staff staff,
                                                             Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis)
            throws CoreException;

    /**
     * Редактирование направления на лабораторные исследования
     * @param directions Json с данными о лабораторных исследованиях как CommonData
     * @param directionId Идентификатор Направления на исследование.
     * @param title  вспомогательный параметр название действия (Диагнозтика).
     * @param request Параметры запроса с клиента.
     * @param mnem Мнемоника исследования.
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JSONCommonData  modifyDirectionsForEventIdFromCommonData(int directionId,
                                                             CommonData directions,
                                                             String title,
                                                             Object request,
                                                             String mnem,
                                                             AuthData authData,
                                                             Staff staff,
                                                             Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis)
            throws CoreException;

    /**
     * Запись пациента на прием к врачу (создание направления к врачу)
     */
    int createConsultation(ConsultationRequestData request, AuthData userData, Staff staff)
            throws CoreException;

    /**
     * Удаление направлений на лабораторные исследования
     * @param directions Список идентификаторов лабораторных исследований
     * @param userData Данные пользователя
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    boolean removeDirections(AssignmentsToRemoveDataList directions, String directionType, Staff userData) throws CoreException;

    /**
     * Сервис по обновлению статусов JobTicket
     * @param data Список статусов JobTicket как JobTicketStatusDataList
     * @param authData Авторизационные данные как AuthData
     * @return true - редактирование прошло успешно или false - при редактировании возникли ошибки (см. лог)
     * @throws CoreException
     */
    boolean updateJobTicketsStatuses(JobTicketStatusDataList data, Staff authData) throws CoreException;

    /**
     * Сервис по обновлению статусов JobTicket
     * @param data Список статусов JobTicket как JobTicketStatusDataList
     * @param authData Авторизационные данные как AuthData
     * @return true - редактирование прошло успешно или false - при редактировании возникли ошибки (см. лог)
     * @throws CoreException
     */
    boolean sendActionsToLaboratory(SendActionsToLaboratoryDataList data, Staff authData) throws CoreException;

    void sendActionToLis(int actionId) throws CoreException;

    boolean sendActionToLaboratory(int actionId) throws CoreException;

    void sendJMSLabRequest(int actionId) throws CoreException;
}
