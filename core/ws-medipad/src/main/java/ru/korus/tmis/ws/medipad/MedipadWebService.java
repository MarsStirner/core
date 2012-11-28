package ru.korus.tmis.ws.medipad;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.*;
import ru.korus.tmis.core.exception.CoreException;

import java.io.Serializable;
import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;

@WebService(
        targetNamespace = "http://korus.ru/tmis/medipad",
        name = "medipad"
)
public interface MedipadWebService extends Serializable {

    ////////////////////////////////////////////////////////////////////////////
    // Patient view
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod
    CommonData getCurrentPatients()
            throws CoreException;

    ////////////////////////////////////////////////////////////////////////////
    // Assessments
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod
    CommonData getAssessmentTypes(String globalVersion,
                                  int eventId)
            throws CoreException;

    @WebMethod
    CommonData getAllAssessmentTypes(String globalVersion)
            throws CoreException;

    @WebMethod
    CommonData getAllAssessmentsForPatient(int eventId)
            throws CoreException;

    @WebMethod
    CommonData getAssessmentForPatient(int eventId, int assessmentId)
            throws CoreException;

    @WebMethod
    CommonData getIndicators(int eventId, Date beginDate, Date endDate)
            throws CoreException;

    @WebMethod
    CommonData createAssessmentForPatient(int eventId,
                                          CommonData assessment)
            throws CoreException;

    @WebMethod
    CommonData modifyAssessmentForPatient(int eventId,
                                          int assessmentId,
                                          CommonData assessment)
            throws CoreException;

    ////////////////////////////////////////////////////////////////////////////
    // Diagnostics
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod
    CommonData getDiagnosticTypes(String globalVersion,
                                  int eventId)
            throws CoreException;

    @WebMethod
    CommonData getAllDiagnosticTypes(String globalVersion)
            throws CoreException;

    @WebMethod
    CommonData getAllDiagnosticsForPatient(int eventId)
            throws CoreException;

    @WebMethod
    CommonData getDiagnosticForPatient(int eventId, int diagnosticId)
            throws CoreException;

    @WebMethod
    CommonData createDiagnosticForPatient(int eventId,
                                          CommonData diagnostic)
            throws CoreException;

    @WebMethod
    CommonData modifyDiagnosticForPatient(int eventId,
                                          int diagnosticId,
                                          CommonData diagnostic)
            throws CoreException;

    @WebMethod
    boolean callOffDiagnosticForPatient(int eventId,
                                        int diagnosticId)
            throws CoreException;

    ////////////////////////////////////////////////////////////////////////////
    // Thesaurus and MKB
    ////////////////////////////////////////////////////////////////////////////
    @WebMethod
    ThesaurusData getThesaurus(String globalVersion)
            throws CoreException;

    @WebMethod
    ThesaurusData getThesaurusByCode(String globalVersion, int code)
            throws CoreException;

    @WebMethod
    ThesaurusData getMkb(String globalVersion)
            throws CoreException;

    ////////////////////////////////////////////////////////////////////////////
    // Treatment
    ////////////////////////////////////////////////////////////////////////////

    @WebMethod
    RlsData getRlsList(String globalVersion)
            throws CoreException;

    @WebMethod
    CompactRlsData getCompactRlsList(String globalVersion)
            throws CoreException;

    /**
     * Получить список типов назначений по идентификатору обращения.
     *
     * @param globalVersion глобальная версия справочников ТМИС
     * @param eventId       идентификатор обращения
     *
     * @return список назначений
     */
    @WebMethod
    CommonData getTreatmentTypes(String globalVersion, int eventId)
            throws CoreException;

    /**
     * Получить список всех возможных типов назначений.
     *
     * @return список назначений
     */
    @WebMethod
    CommonData getAllTreatmentTypes(String globalVersion)
            throws CoreException;

    @WebMethod
    CommonData createTreatmentForPatient(int eventId,
                                         CommonData treatment)
            throws CoreException;

    @WebMethod
    CommonData modifyTreatmentForPatient(int eventId,
                                         int treatmentId,
                                         CommonData treatment)
            throws CoreException;

    /**
     * Получить информацию по назначениям.
     *
     * @param eventId      идентификатор обращения
     * @param actionTypeId идентификатор типа назначения
     * @param beginDate    дата и время начала запрашиваемого интервала времени
     * @param endTime      дата и время окончания запрашиваемого интервала
     *                     времени
     *
     * @return список назначений заданного типа за интересующий интервал времени
     *         для данного обращения
     */
    @WebMethod
    CommonData getTreatmentInfo(int eventId,
                                Integer actionTypeId,
                                Date beginDate,
                                Date endTime)
            throws CoreException;

    @WebMethod
    CommonData getTreatmentForPatient(int eventId,
                                      int treatmentId)
            throws CoreException;

    @WebMethod
    CommonData verifyDrugTreatment(int eventId, int actionId, int drugId)
            throws CoreException;

    /**
     * Отменить назначение.
     * <p/>
     * Отмена назначения заключается в установке статуса "закончено" для
     * назначения
     *
     * @param eventId
     * @param actionId
     */
    @WebMethod
    void revokeTreatment(int eventId, int actionId) throws CoreException;

    ////////////////////////////////////////////////////////////////////////////
    // Core Services
    ////////////////////////////////////////////////////////////////////////////

    @WebMethod
    PatientCardData insertPatient(PatientCardData patientData, AuthData auth) throws CoreException;

    @WebMethod
    PatientCardData updatePatient(PatientCardData patientData, AuthData auth) throws CoreException;

    @WebMethod
    PatientData getAllPatients(PatientRequestData requestData, AuthData auth)  throws CoreException;

    @WebMethod
    PatientCardData getPatientById(int id, AuthData auth)  throws CoreException;

    @WebMethod
    String insertAppealForPatient(AppealData appealData,  int patientId, AuthData auth) throws CoreException;

    @WebMethod
    String getAppealById(int id, AuthData auth) throws CoreException;

    @WebMethod
    String getAppealPrintFormById(int id, AuthData auth) throws CoreException;

    @WebMethod
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
    @WebMethod
    Object getAllAppealsForReceivedPatientByPeriod(ReceivedRequestData requestData, AuthData auth) throws CoreException;

    @WebMethod
    TrueFalseContainer checkExistanceNumber(String name, int typeId, String number, String serial) throws CoreException;

    @WebMethod
    JSONCommonData getStructOfPrimaryMedExam(int actionTypeId, AuthData authData) throws CoreException;

    @WebMethod
    JSONCommonData getStructOfPrimaryMedExamWithCopy(int actionTypeId, AuthData authData, int eventId) throws CoreException;

    @WebMethod
    JSONCommonData insertPrimaryMedExamForPatient(int eventId, JSONCommonData data, AuthData authData) throws CoreException;

    @WebMethod
    JSONCommonData modifyPrimaryMedExamForPatient(int actionId, JSONCommonData data, AuthData authData) throws CoreException;

    @WebMethod
    JSONCommonData getPrimaryAssessmentById (int assessmentId, AuthData authData) throws CoreException;

    @WebMethod
    String getAllPatientsForDepartmentIdAndDoctorIdByPeriod(PatientsListRequestData requestData, int role, AuthData auth) throws CoreException;

    @WebMethod
    AssessmentsListData getListOfAssessmentsForPatientByEvent(AssessmentsListRequestData requestData, AuthData auth) throws CoreException;

    @WebMethod
    String getPatientToHospitalBedById (int actionId, AuthData authData) throws CoreException;

    @WebMethod
    String registryPatientToHospitalBed(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    @WebMethod
    String getMovingListForEvent(HospitalBedDataRequest request, AuthData authData) throws CoreException;

    @WebMethod
    String modifyPatientToHospitalBed (int actionId, HospitalBedData data, AuthData authData) throws CoreException;

    @WebMethod
    boolean callOffHospitalBedForPatient(int actionId, AuthData authData) throws CoreException;

    @WebMethod
    FormOfAccountingMovementOfPatientsData getFormOfAccountingMovementOfPatients(int departmentId) throws CoreException;

    @WebMethod
    String movingPatientToDepartment(int eventId, HospitalBedData data, AuthData authData) throws CoreException;

    @WebMethod
    TalonSPODataList getAllTalonsForPatient(TalonSPOListRequestData requestData) throws CoreException;

    @WebMethod
    AllDepartmentsListData getAllDepartments(ListDataRequest requestData) throws CoreException;

    @WebMethod
    DiagnosticsListData getListOfDiagnosticsForPatientByEvent(DiagnosticsListRequestData requestData) throws CoreException;

    @WebMethod
    JSONCommonData getInfoAboutDiagnosticsForPatientByEvent(int actionId) throws CoreException;

    @WebMethod
    AllPersonsListData getFreePersons(ListDataRequest requestData) throws CoreException;

    @WebMethod
    Object getListOfActionTypeIdNames(ListDataRequest request) throws CoreException;

    @WebMethod
    JSONCommonData insertConsultation(ConsultationRequestData request) throws CoreException;

    @WebMethod
    JSONCommonData insertLaboratoryStudies(int eventId, CommonData data) throws CoreException;

    @WebMethod
    FlatDirectoryData getFlatDirectories(FlatDirectoryRequestData request) throws CoreException;

    @WebMethod
    String getAllMkbs(ListDataRequest request, AuthData auth) throws CoreException;

    @WebMethod
    ThesaurusListData getThesaurusList(ListDataRequest request, AuthData auth) throws CoreException;

    @WebMethod
    AllPersonsListData getAllPersons(ListDataRequest requestData) throws CoreException;

    @WebMethod
    String getDictionary(ListDataRequest request, String dictName) throws CoreException;

    @WebMethod
    AssignmentData insertAssignment(AssignmentData assignmentData, int eventId, AuthData auth) throws CoreException;

    @WebMethod
    AssignmentData getAssignmentById(int actionId, AuthData auth) throws CoreException;

    @WebMethod
    RlsDataList getFilteredRlsList(RlsDataListRequestData request) throws CoreException;

    @WebMethod
    String getEventTypes(ListDataRequest request, AuthData authData) throws CoreException;
}
