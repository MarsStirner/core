package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbDiagnosisType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev Systema-Soft
 */
@Local
public interface DbRbDiagnosisTypeBeanLocal {

    RbDiagnosisType getRbDiagnosisTypeById(int id) throws CoreException;

    RbDiagnosisType getRbDiagnosisTypeByFlatCode(String flatCode) throws CoreException;
}
