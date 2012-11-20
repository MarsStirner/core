package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.indicators.IndicatorValue;
import scala.Function1;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

@Local
public interface DbCustomQueryLocal {

    TakenTissue getTakenTissueByBarcode(int barcode, int period)
            throws CoreException;

    List<Event> getActiveEventsForDoctor(int id)
            throws CoreException;

    List<Event> getActiveEventsForDepartment(int id)
            throws CoreException;

    List<Event> getActiveEventsForDepartmentAndDoctor(int page, int limit, String sortField, String sortMethod, Object filter)
            throws CoreException;

    long getCountActiveEventsForDepartmentAndDoctor(Object filter)
            throws CoreException;

    Map<Event, Action> getAdmissionsByEvents(List<Event> events)
            throws CoreException;

    Map<Event, ActionProperty> getHospitalBedsByEvents(List<Event> events)
            throws CoreException;

    Map<Event, ActionProperty> getAnamnesesByEvents(List<Event> events)
            throws CoreException;

    Map<Event, ActionProperty> getAllergoAnamnesesByEvents(List<Event> events)
            throws CoreException;

    Map<Event, ActionProperty> getDiagnosesByEvents(List<Event> events)
            throws CoreException;

    Map<Event, Action> getDiagnosisSubstantiationByEvents(List<Event> events)
            throws CoreException;

    List<Action> getAllAssessmentsByEventId(int eventId)
            throws CoreException;

    Map<Event, Action> getLastAssessmentByEvents(List<Event> events)
            throws CoreException;

    List<Action> getAllDiagnosticsByEventId(int eventId)
            throws CoreException;

    long getCountDiagnosticsWithFilter(Object filter)
            throws CoreException;

    List<Action> getAllDiagnosticsWithFilter(int page, int limit, String sortField, String sortMethod, Object filter)
            throws CoreException;

    List<IndicatorValue<Double>>
    getIndicatorsByEventIdAndIndicatorNameAndDates(
            int eventId,
            String indicatorName,
            Date beginDate,
            Date endDate)
            throws CoreException;

    List<Action> getTreatmentInfo(int eventId,
                                  Date beginDate,
                                  Date endTime)
            throws CoreException;

    List<Action> getTreatmentInfo(int eventId,
                                  int actionTypeId,
                                  Date beginDate,
                                  Date endTime)
            throws CoreException;

    RbUnit getUnitByCode(String code)
            throws CoreException;

    Map<Event, Object> getAllAppealsWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter, Function1<Long, Boolean> postProcessing)
            throws CoreException;

    Mkb getDiagnosisForMainDiagInAppeal(int appealId)
            throws CoreException;

    Double getHeightForPatient(Patient patient)
            throws CoreException;

    Double getWeightForPatient(Patient patient)
            throws CoreException;

    @Deprecated
    long getCountOfAppealsWithFilter(Object filter)
            throws CoreException;

    List<Mkb> getAllMkbsWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    Map<String, Map<String, String>> getDistinctMkbsWithFilter(String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    long getCountOfMkbsWithFilter(Object filter)
            throws CoreException;

    long getCountOfThesaurusWithFilter(Object filter)
            throws CoreException;

    List<Thesaurus> getAllThesaurusWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;

    Action getLastActionByTypeCodeAndAPTypeName(int eventId, String code, String aptName)
            throws CoreException;

    /**
     * Получить код источника финансирования
     *
     * @param e - карточка пациента
     * @return - код источника финансирования
     * @throws CoreException
     */
    Integer getFinanceId(Event e) throws CoreException;
}
