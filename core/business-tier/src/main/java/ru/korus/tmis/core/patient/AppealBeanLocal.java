package ru.korus.tmis.core.patient;


import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Список методов для работы с обращениями на госпитализацию
 */
@Local
public interface AppealBeanLocal {

    /**
     * Создание/редактирование обращения на госпитализацию
     * @param appealData Данные о госпитализации как AppealData
     * @param patientId Идентификатор пациента.
     * @param authData  Авторизационные данные о создающем госпитализацию.
     * @return Идентификатор обращения.
     * @throws CoreException
     * @see AppealData
     * @see AuthData
     */
    int insertAppealForPatient(AppealData appealData, int patientId, AuthData authData) throws CoreException;

    int updateAppeal(AppealData appealData, int eventId, AuthData authData) throws CoreException;

    /**
     * Получение данных об обращении на госпитализацию по идентификатору.
     * @param id Идентификатор обращения.
     * @return Данные об обращении на госпитализацию.
     * @throws CoreException
     */
    java.util.HashMap<Event, java.util.Map<Action, java.util.Map<Integer,java.util.List<Object>>>> getAppealById(int id)
        throws CoreException;

    /**
     * Запрос на список обращений по пациенту
     * @param request  Набор фильтров из запроса на список как AppealSimplifiedRequestData.
     * @param authData Авторизационные данные о запросившем список.
     * @return Отсортированный и отфильтрованный список обращений по пациенту.
     * @throws CoreException
     * @see AppealSimplifiedRequestData
     */
    AppealSimplifiedDataList getAllAppealsByPatient(AppealSimplifiedRequestData request, AuthData authData)
        throws CoreException;

    /**
     * Запрос на список поступивших за период (список госпитализаций)
     * @param page Фильтр номера выводимой страницы.
     * @param limit Максимальное количество выводим записей.
     * @param sortingField  Наименование поля для сортировки.
     * @param sortingMethod Метод сортировки.
     * @param filter Фильтр выводимых значений как Object
     * @return Отсортированный и отфильтрованный список обращений на госпитализацию за период.
     * @throws CoreException
     * @since 1.0.0.43
     */
    List<Event> getAllAppealsForReceivedPatientByPeriod(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    /**
     * Метод возвращает количество обращений.
     * @param filter Фильтр значений возвращаемого списка как Object.
     * @return Количество обращений.
     * @throws CoreException
     */
    long getCountOfAppealsForReceivedPatientByPeriod (Object filter) throws CoreException;

   // JSONArray getBasicInfoOfDiseaseHistory(int patientId, String externalId, AuthData authData)
   //         throws CoreException;

    /**
     * Метод проверяет уникальность номера истории болезни пациента..
     * @param number Номер истории болезни.
     * @return Возвращает true, если НИБ свободен.
     * @throws CoreException
     */
    Boolean checkAppealNumber(String number)
        throws CoreException;

    /**
     * Метод закрывает обращение на госпитализацию.
     * @param event Закрываемое обращение.
     * @param resultId Идентификатор результата госпитализации, с которым закрывается обращение.
     * @param authData Авторизационные данные.
     * @return Возвращает закрытое обращение.
     * @throws CoreException
     * @see Event
     * @see AuthData
     */
    Event revokeAppealById(Event event, int resultId, AuthData authData) throws CoreException;

    /**
     * Возвращает список кодов типов обращений (s11r64.EventType.code) по идентификатору плоского справочника (s11r64.FlatDirectory.id).
     * @param id Идентификатору плоского справочника (s11r64.FlatDirectory.id).
     * @return Несортированный список кодов типов обращений.
     * @throws CoreException
     */
    List<String> getAppealTypeCodesWithFlatDirectoryId (int id) throws CoreException;

    /**
     * Возвращает список диагнозов МКВ из Action: 'поступление' для данного обращения на госпитализацию.
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param filter Фильтр диагнозов по значению (LIKE).
     * @return Список диагнозов МКВ по ключам.
     * @throws CoreException
     * @see Mkb
     * @see CoreException
     * @since 1.0.0.43
     */
    HashMap<String, List<Mkb>> getDiagnosisListByAppealId(int eventId, String filter) throws CoreException;

    /**
     * Метод анализирует действия внутри госпитализации и возвращает текущий статус.<br>
     * Описание алгоритма: https://docs.google.com/spreadsheet/ccc?key=0Au-ED6EnawLcdHo0Z3BiSkRJRVYtLUxhaG5uYkNWaGc&pli=1#gid=9
     * @param eventId Идентификатор обращения на госпитализацию.
     * @return Статус госпитализации
     * @throws CoreException
     * @since 1.0.0.43
     */
    String getPatientsHospitalizedStatus(int eventId) throws CoreException;

    /**
     * Метод создает или редактирует талон ВМП (квота)
     * @param dataEntry Данные о квоте как ClientQuoting
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param auth Авторизационные данные.
     * @return талом ВМП (квота)
     * @throws CoreException
     * @since 1.0.0.48
     */
    ClientQuoting insertOrUpdateClientQuoting(QuotaEntry dataEntry, int eventId, AuthData auth) throws CoreException;
}
