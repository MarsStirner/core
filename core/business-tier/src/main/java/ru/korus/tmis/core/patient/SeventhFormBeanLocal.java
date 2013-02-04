package ru.korus.tmis.core.patient;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ru.korus.tmis.core.data.SeventhFormLinearView;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbHospitalBedProfile;
import ru.korus.tmis.core.exception.CoreException;

@Local
public interface SeventhFormBeanLocal {

    SeventhFormLinearView getForm007LinearView( int departmentId,
                                                Date beginDate,
                                                Date endDate) throws CoreException;

    SeventhFormLinearView fillInSeventhForm(int departmentId,
                                            Date beginDate,
                                            Date endDate)
            throws CoreException;

    //Map<RbHospitalBedProfile, Map<Action, Map<ActionProperty, List<APValue>>>>
    //Map<OrgStructure,Map<Integer,Map<Action, Map<ActionProperty, List<APValue>>>>>
    List<Action>
    getActionsByDepartmentIdAndFlatCodeBetweenDate(int departmentId,
                                                   Set<String> flatCodes,
                                                   Date beginDate,
                                                   Date endDate)
            throws CoreException;

    Map<RbHospitalBedProfile, Object>
    getTransferredMovingActionsByDepartmentIdAndBetweenDate(int departmentId,
                                                            Date beginDate,
                                                            Date endDate,
                                                            boolean isIn)
            throws CoreException;


    Map<RbHospitalBedProfile, Object>
    getReceivedMovingActionsByDepartmentIdAndBetweenDate(int departmentId,
                                                         Date beginDate,
                                                         Date endDate,
                                                         boolean flgDayHospital,
                                                         Date leftLimit,
                                                         boolean flgMore,
                                                         boolean flgIsVillagers)
            throws CoreException;

    Map<RbHospitalBedProfile, Object>
    getDischargedMovingActionsByDepartmentIdAndBetweenDate(int departmentId,
                                                           Date beginDate,
                                                           Date endDate,
                                                           String to)
            throws CoreException;

    Map<RbHospitalBedProfile, Object>
    getLeavedActionsByDepartmentIdAndBetweenDate(int departmentId,
                                                 Date beginDate,
                                                 Date endDate,
                                                 String value)
            throws CoreException;

    Map<RbHospitalBedProfile, Object> getOrgStructureHospitalBedByDepartmentIdBetweenDate(int departmentId,
                                                                                          Date beginDate,
                                                                                          Date endDate)
            throws CoreException;

    Map<RbHospitalBedProfile, Object> getCountsMovingActionStateByDepartmentIdAndDate(int departmentId, Date date)
            throws CoreException;

    Map<RbHospitalBedProfile, Object> getCountsPatronageMovingActionStateByDepartmentIdAndDate(int departmentId, Date date)
            throws CoreException;

    Map<RbHospitalBedProfile, Object> getCountsEmptyHospitalBedsByDepartmentIdAndDate(int departmentId, Date date, String sex)
            throws CoreException;
}
