package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Diagnostic;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Local
public interface DbDiagnosticBeanLocal {
    /**
     * Получение списка диагностик по идентификатору обращения
     * @param eventId Идентификатор обращения
     * @return Список диагностик как Diagnostic
     * @throws CoreException
     */
    List<Diagnostic> getDiagnosticsByEventId(int eventId) throws CoreException;
}
