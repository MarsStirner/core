package ru.korus.tmis.core.assessment;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;
import javax.ejb.Local;

@Local
public interface AssessmentBeanLocal {

    CommonData getAssessmentTypes(int eventId, Staff userData)
            throws CoreException;

    CommonData getAllAssessmentTypes()
            throws CoreException;

    CommonData getAllAssessmentsByEventId(int eventId)
            throws CoreException;

    CommonData getAssessmentById(int assessmentId)
            throws CoreException;

    CommonData getIndicators(int eventId, Date beginDate, Date endDate)
            throws CoreException;

    CommonData createAssessmentForEventId(int eventId,
                                          CommonData assessment,
                                          AuthData userData,
                                          Staff staff)
            throws CoreException;

    CommonData modifyAssessmentById(int assessmentId,
                                    CommonData assessment,
                                    AuthData userData,
                                    Staff staff)
            throws CoreException;
}
