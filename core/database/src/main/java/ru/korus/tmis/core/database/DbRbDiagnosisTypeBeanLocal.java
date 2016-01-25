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

    /**
     * Возвращает тип даигноза \ диагностики по заданному коду
     * @param flatCode полский код типа дагноза\диагностики
     * @return тип диагноза \ диагностики с заданным кодом. null если по коду ничего не нашли
     */
    RbDiagnosisType getByFlatCode(final String flatCode);

    /**
     *  Возвращает тип даигноза \ диагностики по заданному идентификатору
     * @param id  идентифкатор сущности
     * @return тип диагноза \ диагностики с заданным идентификатором. null если по коду ничего не нашли
     */
    RbDiagnosisType getById(final int id);
}
