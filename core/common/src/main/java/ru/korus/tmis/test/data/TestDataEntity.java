package ru.korus.tmis.test.data;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 8/2/13
 * Time: 10:44 AM
 * Класс для формирования тестовых mock
 */
public interface TestDataEntity extends Serializable {

    Action getTestDefaultAction(int actionId);
    Action getTestDefaultAction(int actionId, ActionType at);
    Action getTestAction(int actionId, Event event, Staff assigner, ActionType atype, TakenTissue ttissue, boolean urgent);

    Event getTestDefaultEvent();
    Event getTestDefaultEvent(int eventId);
    //Event getTestEvent(int eventId, int patientId, String externalId);

    Staff getTestDefaultStaff();
    Staff getTestDefaultStaff(int personId);

    ActionType getTestDefaultActionType();
    ActionType getTestDefaultActionType(int id, String flatCode);

    RbTestTubeType getTestDefaultTestTubeType();

    TakenTissue getTestDefaultTakenTissue();
    TakenTissue getTestDefaultTakenTissue(int id);

    Patient getTestDefaultPatient();
    Patient getTestDefaultPatient(int patientId);

    JobTicket getTestDefaultJobTicket();
    JobTicket getTestDefaultJobTicket(int id);
    JobTicket getTestJobTicket(int JobTicketId, Job job, Date date, int status, String label, String note);

    Job getTestDefaultJob();
    Job getTestDefaultJob(int id);
    Job getTestJob(int id, OrgStructure org);

    OrgStructure getTestDefaultOrgStructure();
    OrgStructure getTestDefaultOrgStructure(int id);

    ActionTypeTissueType getTestDefaultActionTypeTissueType();
    ActionTypeTissueType getTestDefaultActionTypeTissueType(int id);
    ActionTypeTissueType getTestActionTypeTissueType(int id, int amount, RbTissueType tissueType, RbUnit unit);

    RbTissueType getTestDefaultRbTissueType();
    RbTissueType getTestDefaultRbTissueType(int id);
    RbTissueType getTestRbTissueType(int id, String name);

    RbUnit getTestDefaultRbUnit();
    RbUnit getTestDefaultRbUnit(int id);
    RbUnit getTestRbUnit(int id, String name);


    OrgStructureHospitalBed getTestDefaultOrgStructureHospitalBed();
    OrgStructureHospitalBed getTestDefaultOrgStructureHospitalBed(int id);

    ActionProperty getTestDefaultActionProperty();
    ActionProperty getTestDefaultActionProperty(int id);
    ActionProperty getTestDefaultActionProperty(int id, ActionPropertyType apt);
    Object getTestDefaultActionPropertyWithValues();
    Object getTestDefaultActionPropertyWithValues(int id, List<APValue> values);

    APValueHospitalBed getTestDefaultAPValueHospitalBed();
    APValueHospitalBed getTestDefaultAPValueHospitalBed(int id);

    APValueMKB getTestDefaultAPValueMkb();
    APValueMKB getTestDefaultAPValueMkb(int id);

    APValueOrgStructure getTestDefaultAPValueOrgStructure();
    APValueOrgStructure getTestDefaultAPValueOrgStructure(int id);

    APValueTime getTestDefaultAPValueTime();
    APValueTime getTestDefaultAPValueTime(Date time);

    APValueString getTestDefaultAPValueString();
    APValueString getTestDefaultAPValueString(String str);

    ActionPropertyType getTestDefaultActionPropertyType(int id, String code);

    EventPerson getTestDefaultEventPerson(int id, Event event, Staff person);

    Diagnostic getTestDefaultDiagnostic();
    Diagnostic getTestDefaultDiagnostic(int id);
    RbDiagnosisType getTestDefaultDiagnosisType();
    RbTraumaType getTestDefaultRbTraumaType();
    Diagnosis getTestDefaultDiagnosis();
    Mkb getTestDefaultMkb();
    Mkb getTestDefaultMkb(int id);

    BloodHistory getTestDefaultBloodHistory(int id, RbBloodType bloodType, Staff person);
    RbBloodType getTestDefaultRbBloodType(int id);
    AuthData getTestDefaultAuthData(Staff user);
}
