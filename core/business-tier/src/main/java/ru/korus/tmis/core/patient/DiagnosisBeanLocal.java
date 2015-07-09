package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.DiagnosesListData;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Методы для работы с диагнозами
 * Примерная спецификация: https://docs.google.com/document/d/1bC63TjdR9wwv4IRgGu2ExX0mGG1Wx1ubvzmYPUtZN8M/edit#heading=h.48a8yvbnbm1c
 * @author idmitriev Systema-Soft
 */
@Local
public interface DiagnosisBeanLocal {

    /**
     * Запись или редактирование диагноза
     * @param id Идентификатор Diagnostic.id
     * @param eventId Идентификатор обращения
     * @param diaTypeFlatCode Код типа диагноза
     * @param diseaseCharacterId Идентификатор характера заболевания
     * @param description Примечание
     * @param mkb Идентификатор диагноза по МКБ
     * @param userData Авторизационные данные.
     * @return Диагноз как (Diagnostic, Diagnosis)
     * @throws CoreException
     */
    Object insertDiagnosis(int id, int eventId, Action action, String diaTypeFlatCode, int diseaseCharacterId, String description, int mkb, int diseaseStageId, Staff userData) throws CoreException;

    /**
     * Запись или редактирование списка диагнозов.
     * @param eventId Идентификатор обращения на госпитализацию.
     * @param mkbs Набор данных для записи как (идентификатор диагностики (Diagnostic.id), примечание к диагностике (Diagnostic.note), идентификатор диагноза по МКБ (Mkb.id))
     * @param userData Авторизационные данные.
     * @return Список диагнозов в виде entities (Diagnostic и/или Diagnosis)
     * @throws CoreException
     */
    List<Object> insertDiagnoses(int eventId, Action action,  Map<String, Set<Object>> mkbs, Staff userData) throws CoreException;


    DiagnosesListData getDiagnosesByAppeal (int eventId) throws CoreException;

    Boolean deleteDiagnosis(int eventId, String diaTypeFlatCode) throws CoreException;
}
