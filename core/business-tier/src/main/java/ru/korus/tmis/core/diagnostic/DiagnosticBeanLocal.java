package ru.korus.tmis.core.diagnostic;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DiagnosticBeanLocal {

    CommonData getDiagnosticTypes(int eventId,Staff staff)
            throws CoreException;

    CommonData getAllDiagnosticTypes()
            throws CoreException;

    CommonData getAllDiagnosticsByEventId(int eventId)
            throws CoreException;

    CommonData getDiagnosticById(int diagnosticId)
            throws CoreException;

    CommonData createDiagnosticForEventId(int eventId,
                                          CommonData diagnostic,
                                          AuthData userData,
                                          Staff staff)
            throws CoreException;

    CommonData modifyDiagnosticById(int diagnosticId,
                                    CommonData diagnostic,
                                    AuthData userData,
                                    Staff staff)
            throws CoreException;

    boolean updateDiagnosticStatusById(int eventId,
                                       int diagnosticId,
                                       short status)
            throws CoreException;
}
