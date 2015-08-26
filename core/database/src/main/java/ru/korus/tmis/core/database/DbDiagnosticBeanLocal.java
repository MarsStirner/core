package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.TableCol;
import ru.korus.tmis.core.entity.model.*;
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


    public static int INDX_SET_DATE = 0;
    public static int INDX_END_DATE = 1;
    public static int INDX_DIAG_MKB = 2;
    public static int INDX_DIAG_TYPE = 3;
    public static int INDX_DIAG_CHARACTER = 4;
    public static int INDX_DIAG_RESULT = 5;
    public static int INDX_DIAG_ACHE_RESULT = 6;
    public static int INDX_DIAG_PERSON = 7;
    public static int INDX_DIAG_NOTE = 8;

    /**
     * Получение списка диагностик по идентификатору обращения
     * @param id Идентификатор диагноза
     * @return Список диагностик как Diagnostic
     * @throws CoreException
     */
    Diagnostic getDiagnosticById(int id);

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
     * @param diseaseStageId Идентификатор стадии заболевания (rbDiseaseStage.id)
     * @return Новая запись в таблице Diagnostic
     * @throws CoreException
     */
    @Deprecated
    Diagnostic insertOrUpdateDiagnostic(int id,
                                int eventId,
                                Action action,
                                Diagnosis diagnosis,
                                String diagnosisTypeFlatCode,
                                int diseaseCharacterId,
                                int diseaseStageId,
                                String note,
                                Staff staff,
                                Boolean isNewDiag) throws CoreException;

    /**
     * Получение именнованного списка диагнозов для выбранной госпитализации
     * @param eventId Идентификатор обращения на госпитализацию
     * @param diagnosisTypeFlatCodes Список типов диагнозов
     * @return
     * @throws CoreException
     */
    List<Diagnostic> getDiagnosticsByEventIdAndTypes(int eventId,
                                                     Set<String> diagnosisTypeFlatCodes) throws CoreException;

    Diagnostic getLastDiagnosticByEventIdAndType(int eventId, String diagnosisTypeFlatCode) throws CoreException;

    void deleteDiagnostic(Integer id);

    TableCol toTableCol(Diagnostic diagnostic);

    Diagnostic insertOrUpdateDiagnostic(ActionProperty actionProperty, TableCol tableCol, Staff staff);

}
