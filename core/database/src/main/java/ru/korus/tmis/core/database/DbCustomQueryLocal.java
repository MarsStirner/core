package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.indicators.IndicatorValue;

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

    List<Action> getAllDiagnosticsByEventId(int eventId)
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

    Double getHeightForPatient(Patient patient)
            throws CoreException;

    Double getWeightForPatient(Patient patient)
            throws CoreException;
}
