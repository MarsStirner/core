package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssignmentsToRemoveDataList;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.JSONCommonData;
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
                                    Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis)
           throws CoreException;

    /**
     * Метод создания направления на лабораторные исследования
     * @param directions Json с данными о лабораторных исследованиях как CommonData
     * @param eventId Идентификатор обращения на госпитализацию, в рамках которой создается исследование.
     * @param title  вспомогательный параметр название действия (Диагнозтика).
     * @param request Параметры запроса с клиента.
     * @param userData Данные пользователя
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JSONCommonData  createDirectionsForEventIdFromCommonData(int eventId,
                                                             CommonData directions,
                                                             String title,
                                                             Object request,
                                                             AuthData userData,
                                                             Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis)
            throws CoreException;

    /**
     * Редактирование направления на лабораторные исследования
     * @param directions Json с данными о лабораторных исследованиях как CommonData
     * @param directionId Идентификатор Направления на исследование.
     * @param title  вспомогательный параметр название действия (Диагнозтика).
     * @param request Параметры запроса с клиента.
     * @param userData Данные пользователя
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    JSONCommonData  modifyDirectionsForEventIdFromCommonData(int directionId,
                                                             CommonData directions,
                                                             String title,
                                                             Object request,
                                                             AuthData userData,
                                                             Function2<JSONCommonData, java.lang.Boolean, JSONCommonData> postProcessingForDiagnosis)
            throws CoreException;

    /**
     * Удаление направлений на лабораторные исследования
     * @param directions Список идентификаторов лабораторных исследований
     * @param userData Данные пользователя
     * @return com.sun.jersey.api.json.JSONWithPadding как Object
     * @throws CoreException
     * @see CoreException
     */
    boolean removeDirections(AssignmentsToRemoveDataList directions, String directionType, AuthData userData) throws CoreException;
}
