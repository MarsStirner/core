package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.TableCol;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.Diagnosis;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * Методы для работы с таблицей Diagnosis
 * @author Ivan Dmitriev
 */
@Local
public interface DbDiagnosisBeanLocal {
    /**
     * Получение списка диагностик по идентификатору обращения
     * @param id Идентификатор диагноза
     * @return Список диагностик как Diagnosis
     * @throws CoreException
     */
    Diagnosis getDiagnosisById(int id) throws CoreException;

    /**
     * Создание/Редактирование записи в таблице Diagnosis
     * @param id Идентификатор.
     * @param clientId Идентификатор пациента.
     * @param mkbId Идентификатор диагноза по МКБ (MKB.id).
     * @param diagnosisTypeFlatCode Код-идентификатор типа диагноза (rbDiagnosisType.flatCode).
     * @param diseaseCharacterId  Идентификатор характера заболевания (rbDiseaseCharacter.id)
     * @return Новая запись в таблице Diagnostic
     * @throws CoreException
     */
    Diagnosis insertOrUpdateDiagnosis(int id,
                                      int clientId,
                                      String diagnosisTypeFlatCode,
                                      int diseaseCharacterId,
                                      int mkbId,
                                      Staff staff) throws CoreException;

    Diagnosis createDiagnosis(ActionProperty actionProperty, TableCol tableCol, Staff staff);
}
