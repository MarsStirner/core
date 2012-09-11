package ru.korus.tmis.ws.medipad;

import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.CompactRlsData;
import ru.korus.tmis.core.data.RlsData;
import ru.korus.tmis.core.data.ThesaurusData;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;
import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(
        targetNamespace = "http://korus.ru/tmis/medipad",
        name = "medipad"
)
public interface MedipadWebService {

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
    void revokeTreatment(int eventId, int actionId)
            throws CoreException;
}
