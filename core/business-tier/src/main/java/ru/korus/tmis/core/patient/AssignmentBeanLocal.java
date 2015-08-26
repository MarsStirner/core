package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.AssignmentData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface AssignmentBeanLocal {

    Action insertAssignmentForPatient(AssignmentData assignmentData, int eventId, AuthData authData, Staff staff) throws CoreException;

    AssignmentData getAssignmentById(int actionId) throws CoreException;
}
