package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
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
}
