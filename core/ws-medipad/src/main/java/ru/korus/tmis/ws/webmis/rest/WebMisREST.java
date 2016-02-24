package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.patient.InfectionDrugMonitoring;

import javax.ejb.Local;
import javax.servlet.http.Cookie;
import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;

/**
 * User: idmitriev
 * Date: 3/19/13
 * Time: 12:05 PM
 */
@Local
public interface WebMisREST extends Serializable {

    PatientCardData insertPatient(PatientCardData patientData, AuthData auth) throws CoreException;

    PatientCardData updatePatient(int id, PatientCardData patientData, AuthData auth) throws CoreException;

    PatientData getAllPatients(PatientRequestData requestData, AuthData auth)  throws CoreException;

    PatientCardData getPatientById(int id, AuthData auth)  throws CoreException;

    AppealData insertAppealForPatient(AppealData appealData,  int patientId, AuthData auth) throws CoreException;

    AppealData updateAppeal(AppealData appealData,  int eventId, AuthData auth) throws CoreException;

    AppealData getAppealById(int id, AuthData auth) throws CoreException;

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

    JSONCommonData getStructOfPrimaryMedExam(int actionTypeId, Integer actionId, int eventId, AuthData authData) throws CoreException;

    JSONCommonData getStructOfPrimaryMedExamWithCopy(int actionTypeId, AuthData authData, int eventId) throws CoreException;

    APValue calculateActionPropertyValue(int eventId, int actionTypeId, int actionPropertyId) throws CoreException;

    JSONCommonData insertPrimaryMedExamForPatient(int eventId, JSONCommonData data, AuthData authData, URI baseUri) throws CoreException;

    JSONCommonData modifyPrimaryMedExamForPatient(int actionId, JSONCommonData data, AuthData authData, URI baseUri) throws CoreException;

    JSONCommonData getPrimaryAssessmentById (int assessmentId, AuthData authData) throws CoreException;

    String getAllPatientsForDepartmentIdAndDoctorIdByPeriod(PatientsListRequestData requestData, AuthData auth) throws CoreException;

    AssessmentsListData getListOfAssessmentsForPatientByEvent(AssessmentsListRequestData requestData, AuthData auth) throws CoreException;

    String getPatientToHospitalBedById (int actionId, AuthData authData) throws CoreException;

    String registryPatientToHospitalBed(int eventId, HospitalBedData data, AuthData authData, Staff staff) throws CoreException;

    String getMovingListForEvent(HospitalBedDataListFilter filter, AuthData authData) throws CoreException;

    String modifyPatientToHospitalBed (int actionId, HospitalBedData data,  AuthData authData, Staff staff) throws CoreException;

    boolean callOffHospitalBedForPatient(int actionId, AuthData authData) throws CoreException;

    BedDataListContainer getVacantHospitalBeds(int departmentId, AuthData authData) throws CoreException;

    HospitalBedProfileContainer getBedProfileById(int profileId, AuthData authData) throws CoreException;

    HospitalBedProfilesListContainer getAllAvailableBedProfiles(AuthData authData) throws CoreException;

    Iterable<RbHospitalBedProfile> getAllAvailableBedProfiles() throws CoreException;

    //FormOfAccountingMovementOfPatientsData getFormOfAccountingMovementOfPatients(int departmentId) throws CoreException;

    FormOfAccountingMovementOfPatientsData getForm007( int departmentId,
                                                       long beginDate,
                                                       long endDate,
                                                       java.util.List<Integer> profileBeds,
                                                       AuthData authData) throws CoreException;

    String movingPatientToDepartment(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    String  closeLastMovingOfAppeal(int eventId, AuthData authData, Date date) throws CoreException;

    AllDepartmentsListData getAllDepartments(ListDataRequest requestData) throws CoreException;

    /**
     * Возвращаем все отделения  (Для mediPad)
     * @param hasBeds Фильтр имеет(true) или не имеет(false) койки
     * @return Список отделений как AllDepartmentsListData
     * @throws CoreException
     */
    AllDepartmentsListDataMP getAllDepartmentsByHasBeds(String hasBeds, String hasPatients) throws CoreException;

    /**
     * Сервис получения списка направлений на исследования
     * @param requestData Данные из запроса
     * @param authData Данные о пользователе
     * @return Список направлений как DiagnosticsListData
     * @throws CoreException
     */
    DiagnosticsListData getListOfDiagnosticsForPatientByEvent(DiagnosticsListRequestData requestData, AuthData authData) throws CoreException;

    /**
     * Получение информации по направлению.
     * @param actionId Идентификатор направления (Action)
     * @param authData Данные о пользователе
     * @return Направление как JSONCommonData
     * @throws CoreException
     */
    JSONCommonData getInfoAboutDiagnosticsForPatientByEvent(int actionId, AuthData authData) throws CoreException;

    FreePersonsListData getFreePersons(ListDataRequest requestData, long beginDate) throws CoreException;

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

    JSONCommonData modifyConsultation(ConsultationRequestData request, AuthData authData);

    boolean removeDirection(AssignmentsToRemoveDataList data, String directionType, AuthData auth) throws CoreException;

    void checkCountOfConsultations(int eventId, int pqt, int executorId, long data) throws CoreException;

    ScheduleContainer getPlannedTime(int actionId);

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
     * @param eventId
     * @return JSON - строка как String
     * @throws CoreException
     * @see ListDataRequest
     */
    String getDictionary(ListDataRequest request, String dictName, Integer eventId, AuthData auth) throws CoreException;

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
     * Сервис на получения списка типов обращений.
     * @param request Данные из запроса как ListDataRequest.
     * @param authData Авторизационные данные как AuthData
     * @return JSON - строка как String
     * @throws CoreException
     * @see ListDataRequest
     * @see AuthData
     */
    String getEventTypes(ListDataRequest request, Staff staff) throws CoreException;

    List<ContractContainer> getContracts(int eventTypeId, String eventTypeCode, boolean showDeleted, boolean showExpired);

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
     * Сервис на получение данных о заборе биоматериала
     * @param request Данные из запроса как TakingOfBiomaterialRequesData
     * @param authData Авторизационные данные как AuthData
     * @return TakingOfBiomaterialData
     * @throws CoreException
     */
    TakingOfBiomaterialData getTakingOfBiomaterial(TakingOfBiomaterialRequesData request, AuthData authData) throws CoreException;

    /**
     * Получение объекта JobTicket по идентификатору
     * @param id Идентификатор
     * @param authData Данные авторизации
     * @return Detached-объект JobTicket
     * @throws CoreException
     */
    JobTicket getJobTicketById(int id, AuthData authData) throws CoreException;

    /**
     * Сервис по обновлению статусов JobTicket
     * @param data Список статусов JobTicket как JobTicketStatusDataList
     * @param authData Авторизационные данные как AuthData
     * @return true - редактирование прошло успешно или false - при редактировании возникли ошибки (см. лог)
     * @throws CoreException
     */
    boolean updateJobTicketsStatuses(JobTicketStatusDataList data, AuthData authData) throws CoreException;


    /**
     * Сервис по отправке заданных Action в Лаборатории
     * @param data Список идентифкаторов Action-ов
     * @param authData Авторизационные данные как AuthData
     * @return true - редактирование прошло успешно или false - при редактировании возникли ошибки (см. лог)
     * @throws CoreException
     */
    boolean sendActionsToLaboratory(SendActionsToLaboratoryDataList data, AuthData authData) throws CoreException;

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

    List<List<Object>> getInfectionMonitoring(int eventId, AuthData authData) throws CoreException;

    List<List<Object>> getInfectionDrugMonitoring(int eventId, AuthData authData) throws CoreException;

    List<InfectionDrugMonitoring> getInfectionDrugMonitoringList(int eventId) throws CoreException;

    SurgicalOperationsListData getSurgicalOperationsByAppeal(int eventId, AuthData authData) throws CoreException;

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

    /**
     * Получение результатов исследования БАК-лаборатории
     * @param actionId Идентификатор действия, представляющего лабораторное исследование
     * @param authData Данные авторизации
     * @return Данных о результатах лабораторного исследования
     * @throws CoreException В случае, если в БД отсутствуют результаты исследования БАК лаборатории для
     * исследования с id = actionId
     */
    BakLabResultDataContainer getBakResult(int actionId, AuthData authData) throws CoreException;

    /**
     * Получение версии сборки jenkins
     * @return Нет фиксированного формата возвращаемой версии - следует выводить пользователю как plain text.
     */
    String getBuildVersion();

    /**
     * Получение объектов шаблонов печати по идентификаторам
     * @param ids Идентификаторы запрашиваемых объектов
     * @param authData Данные авторизации
     * @return Список запрашиваемых шаблонов печати
     * @throws CoreException
     */
    List<RbPrintTemplate> getRbPrintTemplatesByIds(List<Integer> ids, AuthData authData) throws CoreException;

    /**
     * Получение шаблонов печати по полю context и с применением фильтрации по другим полям
     * @param contexts Запрашиваемые значения поля context
     * @param authData Данные авторизации
     * @param fields Запрашиваемые поля, если данный параметр задан - все остальные поля будут заполнены null,
     *               применяется для экономии канала передачи данных - некоторые поля (например, default) содержат большие объемы данных
     * @param fRender Фильтрация по значению поля render, фильтрация не производится, если передается null
     * @return Список запрашиваемых шаблонов печати
     * @throws CoreException
     */
    List<RbPrintTemplate> getRbPrintTemplatesByContexts(List<String> contexts, AuthData authData, String[] fields, Integer fRender) throws CoreException;

    /**
     * Получение данных об организации, хранящиеся в БД ФТМИС
     * @param id Идентификатор организации
     * @param authData Данные авторизации
     * @return Объектное представление записи в БД об организации
     * @throws CoreException
     */
    OrganizationContainer getOrganizationById(int id, AuthData authData) throws CoreException;

    /**
     * Проверка аутентификации
     * @param cookies Cookies, передаваемые в запросе
     * @return Данные аутентификации пользователя
     */
    AuthData checkTokenCookies(Cookie[] cookies);

    AuthData getStorageAuthData(AuthToken token);

    /**
     * Пометить action как удаленный
     *
     * @param actionId Идентификатор удаляемого действия
     */
    void removeAction(int actionId, AuthData auth) throws CoreException;

    /**
     * Метод сохранения текста при автосохранении полей ввода
     * @param id Идентификатор поля, строка длинной не более 40 символов
     * @param text Сохраняемая текстовая запись
     * @param auth Данные авторизации пользователя
     * @throws CoreException В случае неверных входящих данных
     */
    Object saveAutoSaveField(String id, String text, AuthData auth) throws CoreException;

    /**
     * Получение сохраненного ранее автосохраняемого текста
     * @param id Идентификатор поля, строка длинной не более 40 символов
     * @param auth Данные авторизации пользователя
     * @return Запрашиваемое текстовое поле
     * @throws CoreException
     */
    AutoSaveOutputDataContainer loadAutoSaveField(String id, AuthData auth) throws CoreException;

    /**
     * Получение сохраненного ранее автосохраняемого текста
     * @param id Идентификатор поля
     * @param auth Данные авторизации пользователя
     * @throws CoreException
     */
    void deleteAutoSaveField(String id, AuthData auth) throws CoreException;

    DrugData getRlsById(int id) throws CoreException;

    List<DrugData> getRlsByText(String text) throws CoreException;

    List<RbLaboratory> getLabs();

    List<TherapyContainer> getTherapiesInfo(int eventId) throws CoreException;

    List<TherapyContainer> getAllTherapiesInfo(int eventId) throws CoreException;

    /**
     * Залочить действие
     *
     * @param actionId - Идентификатор действия
     * @param auth - Данные авторизации пользователя
     * @return - Информацию о новом локе
     * @throws CoreException
     */
    LockData lock(int actionId, AuthData auth) throws CoreException;

    LockData prolongLock(int actionId, AuthData auth) throws CoreException;

    void releaseLock(int actionId, AuthData auth);


}
