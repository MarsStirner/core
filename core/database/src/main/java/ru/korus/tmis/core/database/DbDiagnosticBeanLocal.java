package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.Diagnosis;
import ru.korus.tmis.core.entity.model.Diagnostic;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Методы для работы с таблицей Diagnostic
 * @author Ivan Dmitriev
 */
@Local
public interface DbDiagnosticBeanLocal {

    /**
     * Получение списка диагностик по идентификатору обращения
     * @param id Идентификатор диагноза
     * @return Список диагностик как Diagnostic
     * @throws CoreException
     */
    Diagnostic getDiagnosticById(int id) throws CoreException;

    /**
     * Получение списка диагностик по идентификатору обращения
     * @param eventId Идентификатор обращения
     * @return Список диагностик как Diagnostic
     * @throws CoreException
     */
    List<Diagnostic> getDiagnosticsByEventId(int eventId) throws CoreException;

    /**
     * Создание/Редактирование записи в таблице Diagnostic
     * @param id идентификатор
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param diagnosis Диагноза (Diagnosis).
     * @param diagnosisTypeFlatCode Код-идентификатор типа диагноза (rbDiagnosisType.flatCode).
     * @param diseaseCharacterId  Идентификатор характера заболевания (rbDiseaseCharacter.id)
     * @param userData Авторизационные данные
     * @return Новая запись в таблице Diagnostic
     * @throws CoreException
     */
    Diagnostic insertOrUpdateDiagnostic(int id,
                                int eventId,
                                Diagnosis diagnosis,
                                String diagnosisTypeFlatCode,
                                int diseaseCharacterId,
                                String note,
                                AuthData userData) throws CoreException;

    /**
     * Получение именнованного списка диагнозов для выбранной госпитализации
     * @param eventId Идентификатор обращения на госпитализацию
     * @param diagnosisTypeFlatCodes Список типов диагнозов
     * @return
     * @throws CoreException
     */
    List<Diagnostic> getDiagnosticsByEventIdAndTypes(int eventId,
                                                     Set<String> diagnosisTypeFlatCodes) throws CoreException;
}
