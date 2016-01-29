package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.data.DiagnosesListData;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Методы для работы с диагнозами
 * Примерная спецификация: https://docs.google.com/document/d/1bC63TjdR9wwv4IRgGu2ExX0mGG1Wx1ubvzmYPUtZN8M/edit#heading=h.48a8yvbnbm1c
 *
 * @author idmitriev Systema-Soft
 */
@Local
public interface DiagnosisBeanLocal {

    /**
     * Получить диагностику по ее идентифкатору
     * @param id  идентифкатор диагностики
     * @return сущность \ null если не найдено
     */
    Diagnostic getDiagnostic(int id);

    /**
     * Запись или редактирование диагноза
     *
     * @param id                 Идентификатор Diagnostic.id
     * @param eventId            Идентификатор обращения
     * @param diaTypeFlatCode    Код типа диагноза
     * @param diseaseCharacterId Идентификатор характера заболевания
     * @param description        Примечание
     * @param mkb                Идентификатор диагноза по МКБ
     * @param userData           Авторизационные данные.
     * @return Диагноз как (Diagnostic, Diagnosis)
     * @throws CoreException
     */
    Object insertDiagnosis(
            int id,
            int eventId,
            Action action,
            String diaTypeFlatCode,
            int diseaseCharacterId,
            String description,
            int mkb,
            int diseaseStageId,
            Staff userData
    ) throws CoreException;

    /**
     * Запись или редактирование списка диагнозов.
     *
     * @param eventId  Идентификатор обращения на госпитализацию.
     * @param mkbs     Набор данных для записи как (идентификатор диагностики (Diagnostic.id), примечание к диагностике (Diagnostic.note), идентификатор диагноза по МКБ (Mkb.id))
     * @param userData Авторизационные данные.
     * @return Список диагнозов в виде entities (Diagnostic и/или Diagnosis)
     * @throws CoreException
     */
    List<Object> insertDiagnoses(int eventId, Action action, Map<String, Set<Object>> mkbs, Staff userData) throws CoreException;


    DiagnosesListData getDiagnosesByAppeal(int eventId) throws CoreException;

    Boolean deleteDiagnosis(int eventId, String diaTypeFlatCode) throws CoreException;

    /**
     * Запись нового диагноза
     *
     * @param staff         Пользователь, создающий диагноз
     * @param client        Пациент, которому ставиться новый диагноз
     * @param diagnosisType Тип диагноза
     * @param character     Характер заболевания
     * @param mkb           Запись из справочника МКБ
     * @return созданный диагноз
     */
    Diagnosis insertDiagnosis(
            final Staff staff,
            final Patient client,
            final RbDiagnosisType diagnosisType,
            final RbDiseaseCharacter character,
            final Mkb mkb
    );

    /**
     * Изменение старого диагноза если требуется
     * @param staff         Пользователь, создающий диагноз
     * @param client        Пациент, которому ставиться новый диагноз
     * @param diagnosisType Тип диагноза
     * @param character     Характер заболевания
     * @param mkb           Запись из справочника МКБ
     * @param oldVersion    Старый вариант диагноза (если null, то создаем новый диагноз @see #insertDiagnosis( Staff, Patient, RbDiagnosisType, RbDiseaseCharacter, Mkb) )
     * @return Изменнеый диагноз (after merge) или oldVersion
     */
    Diagnosis modifyDiagnosis(
            final Staff staff,
            final Patient client,
            final RbDiagnosisType diagnosisType,
            final RbDiseaseCharacter character,
            final Mkb mkb ,
            final Diagnosis oldVersion
    );

    /**
     * Запись новой дианостики
     *
     * @param staff         Пользователь, создающий дианостику
     * @param event         Обращение, в рамках которого создается диагностика
     * @param action        Действие, в рамках которого создается диагностика
     * @param diagnosis     Диагноз на который должна ссылаться диагностика
     * @param diagnosisType Тип диагноза
     * @param character     Характер заболевания
     * @param description   Описание диагностики
     * @return созданный диагностика (after persist)
     */
    Diagnostic insertDiagnostic(
            final Staff staff,
            final Event event,
            final Action action,
            final Diagnosis diagnosis,
            final RbDiagnosisType diagnosisType,
            final RbDiseaseCharacter character,
            final String description
    );

    /**
     * Изменение старой диагностики если требуется
     *
     * @param staff         Пользователь, создающий дианостику
     * @param event         Обращение, в рамках которого создается диагностика
     * @param action        Действие, в рамках которого создается диагностика
     * @param diagnosis     Диагноз на который должна ссылаться диагностика
     * @param diagnosisType Тип диагноза
     * @param character     Характер заболевания
     * @param description   Описание диагностики
     * @param oldVersion    Старый вариант диагноза (если null, то создаем новый диагноз @see #insertDiagnostic )
     * @return измененая диагностика (after merge), или oldVersion
     */
    Diagnostic modifyDiagnostic(
            final Staff staff,
            final Event event,
            final Action action,
            final Diagnosis diagnosis,
            final RbDiagnosisType diagnosisType,
            final RbDiseaseCharacter character,
            final String description,
            final Diagnostic oldVersion
    );

    /**
     * Удаление диагностики и диагноза из обращения
     * @param event обращение в котором надо удалить диагностику и диагноз
     * @param diagnosticId  идентифкатор удаляемой диагностики
     * @return  удаленная сущность / null если не найдено по идентифкатору
     */
    Diagnostic deleteDiagnosis(final Event event, final int diagnosticId);

}
