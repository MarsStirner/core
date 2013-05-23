package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 3/19/13
 * Time: 12:05 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WebMisREST extends Serializable {

    PatientCardData insertPatient(PatientCardData patientData, AuthData auth) throws CoreException;

    PatientCardData updatePatient(int id, PatientCardData patientData, AuthData auth) throws CoreException;

    PatientData getAllPatients(PatientRequestData requestData, AuthData auth)  throws CoreException;

    PatientCardData getPatientById(int id, AuthData auth)  throws CoreException;

    String insertAppealForPatient(AppealData appealData,  int patientId, AuthData auth) throws CoreException;

    String updateAppeal(AppealData appealData,  int eventId, AuthData auth) throws CoreException;

    String getAppealById(int id, AuthData auth) throws CoreException;

    String getAppealPrintFormById(int id, AuthData auth) throws CoreException;

    AppealSimplifiedDataList getAllAppealsByPatient(AppealSimplifiedRequestData requestData, AuthData auth) throws CoreException;

    /**
     * Список поступивших пациентов за период
     * @param requestData Структура с данными о запросе
     * @param auth Структура с авторизационными данными
     * @return Список пациентов в виде Json - строки как Object
     * @throws CoreException
     * @see CoreException
     * @see ReceivedRequestData
     * @see AuthData
     */
    Object getAllAppealsForReceivedPatientByPeriod(ReceivedRequestData requestData, AuthData auth) throws CoreException;

    TrueFalseContainer checkExistanceNumber(String name, int typeId, String number, String serial) throws CoreException;

    JSONCommonData getStructOfPrimaryMedExam(int actionTypeId, AuthData authData) throws CoreException;

    JSONCommonData getStructOfPrimaryMedExamWithCopy(int actionTypeId, AuthData authData, int eventId) throws CoreException;

    JSONCommonData insertPrimaryMedExamForPatient(int eventId, JSONCommonData data, AuthData authData) throws CoreException;

    JSONCommonData modifyPrimaryMedExamForPatient(int actionId, JSONCommonData data, AuthData authData) throws CoreException;

    JSONCommonData getPrimaryAssessmentById (int assessmentId, AuthData authData) throws CoreException;

    String getAllPatientsForDepartmentIdAndDoctorIdByPeriod(PatientsListRequestData requestData, AuthData auth) throws CoreException;

    AssessmentsListData getListOfAssessmentsForPatientByEvent(AssessmentsListRequestData requestData, AuthData auth) throws CoreException;

    String getPatientToHospitalBedById (int actionId, AuthData authData) throws CoreException;

    String registryPatientToHospitalBed(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    String getMovingListForEvent(HospitalBedDataListFilter filter, AuthData authData) throws CoreException;

    String modifyPatientToHospitalBed (int actionId, HospitalBedData data, AuthData authData) throws CoreException;

    boolean callOffHospitalBedForPatient(int actionId, AuthData authData) throws CoreException;

    BedDataListContainer getVacantHospitalBeds(int departmentId, AuthData authData) throws CoreException;

    //FormOfAccountingMovementOfPatientsData getFormOfAccountingMovementOfPatients(int departmentId) throws CoreException;

    FormOfAccountingMovementOfPatientsData getForm007( int departmentId,
                                                       long beginDate,
                                                       long endDate,
                                                       AuthData authData) throws CoreException;

    String movingPatientToDepartment(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    TalonSPODataList getAllTalonsForPatient(TalonSPOListRequestData requestData) throws CoreException;

    AllDepartmentsListData getAllDepartments(ListDataRequest requestData) throws CoreException;

    /**
     * Возвращаем все отделения  (Для mediPad)
     * @param hasBeds Фильтр имеет(true) или не имеет(false) койки
     * @return Список отделений как AllDepartmentsListData
     * @throws CoreException
     */
    AllDepartmentsListDataMP getAllDepartmentsByHasBeds(String hasBeds, String hasPatients) throws CoreException;

    DiagnosticsListData getListOfDiagnosticsForPatientByEvent(DiagnosticsListRequestData requestData) throws CoreException;

    JSONCommonData getInfoAboutDiagnosticsForPatientByEvent(int actionId) throws CoreException;

    FreePersonsListData getFreePersons(ListDataRequest requestData) throws CoreException;

    /**
     * Получение справочника типов действий плоской структурой либо структуры CommonData для нужного ActionType
     * @param request Данные из запроса как Object (JSONCommonData либо String)
     * @param patientId Идентификатор пациента, из которого достанем пол и возраст
     * @return Информация справочника ActionType как String
     * @throws CoreException
     */
    Object getListOfActionTypeIdNames(ListDataRequest request, int patientId) throws CoreException;

    /**
     * Получение справочника типов действий в виде дерева
     * @param request Данные из запроса как ListDataRequest
     * @return Информация справочника ActionType как String
     * @throws CoreException
     */
    String getListOfActionTypes(ListDataRequest request) throws CoreException;

    JSONCommonData insertConsultation(ConsultationRequestData request, AuthData auth) throws CoreException;

    JSONCommonData insertLaboratoryStudies(int eventId, CommonData data, AuthData auth) throws CoreException;

    JSONCommonData modifyLaboratoryStudies(int eventId, CommonData data, AuthData auth) throws CoreException;

    JSONCommonData insertInstrumentalStudies(int eventId, CommonData data, AuthData auth) throws CoreException;

    JSONCommonData modifyInstrumentalStudies(int eventId, CommonData data, AuthData auth) throws CoreException;

    boolean removeDirection(AssignmentsToRemoveDataList data, String directionType, AuthData auth) throws CoreException;

    /**
     * Получение справочника FlatDirectory
     * @param request Данные из запроса как FlatDirectoryRequestData
     * @return Информация справочника FlatDirectory как FlatDirectoryData
     * @throws CoreException
     */
    FlatDirectoryData getFlatDirectories(FlatDirectoryRequestData request) throws CoreException;

    /**
     * Получение справочников MKB
     * @param request Данные из запроса как ListDataRequest.
     * @param auth Авторизационные данные как AuthData.
     * @return Справочник МКВ в виде JSON-строки как String
     * @throws CoreException
     * @see ListDataRequest
     * @see AuthData
     */
    String getAllMkbs(ListDataRequest request, AuthData auth) throws CoreException;

    /**
     * Получения справочников Thesaurus
     * @param request Данные из запроса как ListDataRequest.
     * @param auth Авторизационные данные как AuthData.
     * @return Справочник Thesaurus как ThesaurusListData
     * @throws CoreException
     * @see ListDataRequest
     * @see ThesaurusListData
     */
    ThesaurusListData getThesaurusList(ListDataRequest request, AuthData auth) throws CoreException;

    /**
     * Запрос на список персонала
     * @param requestData Данные из запроса как ListDataRequest.
     * @return Список персонала как AllPersonsListData
     * @throws CoreException
     * @see ListDataRequest
     * @see AllPersonsListData
     */
    AllPersonsListData getAllPersons(ListDataRequest requestData) throws CoreException;

    /**
     * Универсальный сервис на получение справочников простой структуры
     * @param request  Данные из запроса как ListDataRequest.
     * @param dictName Наименование запрашиваемого справочника.
     * @return JSON - строка как String
     * @throws CoreException
     * @see ListDataRequest
     */
    String getDictionary(ListDataRequest request, String dictName) throws CoreException;

    /**
     * Создание нового назначения
     * @param assignmentData Информация о назначении как контейнер AssignmentData
     * @param eventId Идентификатор обращения, в рамках которого создается назначение.
     * @param auth Авторизационные данные как AuthData.
     * @return Информацию о созданном назначении как контейнер AssignmentData.
     * @throws CoreException
     */
    AssignmentData insertAssignment(AssignmentData assignmentData, int eventId, AuthData auth) throws CoreException;

    /**
     * Получение информации о назначении по идентификатору.
     * @param actionId Идентификатор назначения.
     * @param auth Авторизационные данные как AuthData.
     * @return Информацию о назначении как контейнер AssignmentData.
     * @throws CoreException
     * @see AssignmentData
     * @see AuthData
     */
    AssignmentData getAssignmentById(int actionId, AuthData auth) throws CoreException;

    /**
     * Получение справочника Rls
     * @param request Данные из запроса как RlsDataListRequestData
     * @return Список Rls как RlsDataList
     * @throws CoreException
     * @see RlsDataList
     */
    RlsDataList getFilteredRlsList(RlsDataListRequestData request) throws CoreException;

    /**
     * Сервис на получения списка типов обращений.
     * @param request Данные из запроса как ListDataRequest.
     * @param authData Авторизационные данные как AuthData
     * @return JSON - строка как String
     * @throws CoreException
     * @see ListDataRequest
     * @see AuthData
     */
    String getEventTypes(ListDataRequest request, AuthData authData) throws CoreException;

    /**
     * Сервис на получении списка пациентов из открытых госпитализаций, которые лежат на койке
     * @param departmentId Идентификатор отделения
     * @return Список текущих пациентов отделения как CommonData
     * @throws CoreException
     * @see CommonData
     * @see AuthData
     */
    CommonData getPatientsFromOpenAppealsWhatHasBedsByDepartmentId (int departmentId) throws CoreException;

    /**
     * Сервис на создание/редактирование квоты
     * @param quotaData Данные о сохраняемой/редактируемой квоте как QuotaData
     * @param eventId Идентификатор обращения
     * @param auth Авторизационные данные как AuthData
     * @return JSON - строка как String
     * @throws CoreException
     * @see QuotaData
     * @see AuthData
     */
    String insertOrUpdateQuota(QuotaData quotaData, int eventId, AuthData auth) throws CoreException;

    /**
     * Сервис на получение истории квот
     * @param appealId Идентификатор обращения
     * @return JSON - строка как String
     * @throws CoreException
     */
    String getQuotaHistory(int appealId, QuotaRequestData request) throws CoreException;

    /**
     * Сервис на получение списка справочника типов квот
     * @param request Данные из запроса как ListDataRequest
     * @return Список типов квот как GroupTypesListData
     * @throws CoreException
     * @see ListDataRequest
     * @see GroupTypesListData
     */
    GroupTypesListData getQuotaTypes(ListDataRequest request) throws CoreException;

    /**
     * Сервис на получение данных о заборе биоматериала
     * @param request Данные из запроса как TakingOfBiomaterialRequesData
     * @param authData Авторизационные данные как AuthData
     * @return TakingOfBiomaterialData
     * @throws CoreException
     */
    TakingOfBiomaterialData getTakingOfBiomaterial(TakingOfBiomaterialRequesData request, AuthData authData) throws CoreException;

    /**
     * Сервис по обновлению статусов JobTicket
     * @param data Список статусов JobTicket как JobTicketStatusDataList
     * @param authData Авторизационные данные как AuthData
     * @return true - редактирование прошло успешно или false - при редактировании возникли ошибки (см. лог)
     * @throws CoreException
     */
    boolean updateJobTicketsStatuses(JobTicketStatusDataList data, AuthData authData) throws CoreException;

    /**
     * Удаление всей информации о пациенте  (для юнит-тестов)
     * @param id Идентификатор пациента
     * @return true/false
     * @throws CoreException
     */
    Boolean deletePatientInfo(int id) throws CoreException;

    /**
     * Сервис на получение списка диагнозов внутри госпитализации
     * @param id Идентификатор обращения на госпитализацию
     * @param authData Авторизационные данные как AuthData
     * @return Список диагнозов как DiagnosesListData
     * @throws CoreException
     */
    DiagnosesListData getDiagnosesByAppeal(int id, AuthData authData) throws CoreException;

    /**
     * История изменения группы крови у пациента
     * @param patientId Идентификатор пациента
     * @param authData Авторизационные данные
     * @return Истории как BloodHistoryListData
     * @throws CoreException
     */
    BloodHistoryListData getBloodTypesHistory (int patientId, AuthData authData) throws CoreException;

    /**
     * Внести запись о группе крови
     * @param patientId Идентификатор пациента
     * @param data json с информацией о группе крови
     * @param authData Авторизационные данные
     * @return Информация о группе крови
     * @throws CoreException
     */
    BloodHistoryData insertBloodTypeForPatient(int patientId, BloodHistoryData data, AuthData authData) throws CoreException;

    /**
     * Список информации о иследованиях
     * @param eventId Идентификатор обращения
     * @param condition Условие выборки
     * @param authData Авторизационные данные
     * @return MonitoringInfoListData
     * @throws CoreException
     */
    MonitoringInfoListData getMonitoringInfoByAppeal(int eventId, int condition, AuthData authData) throws CoreException;

    /**
     * Назначение ответственного врача
     * @param eventId Идентификатор обращения
     * @param personId Идентификатор пациента
     * @param authData Авторизационные данные
     * @return true - назначен, false - не назначен
     * @throws CoreException
     */
    Boolean setExecPersonForAppeal(int eventId, int personId, AuthData authData) throws CoreException;

    LayoutAttributeListData getLayoutAttributes() throws CoreException;
}
