package ru.korus.tmis.core.patient;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Map;
import java.util.Set;

/**
 * Методы для работы с диагнозами
 * @author idmitriev Systema-Soft
 */
@Local
public interface DiagnosisBeanLocal {

    Object insertOrUpdateDiagnosis(int id, int eventId, String diaTypeFlatCode, int diseaseCharacterId, int mkb, AuthData userData) throws CoreException;

    Set<Object> insertDiagnoses(int eventId, Map<String, Set<Integer>> mkbs, AuthData userData) throws CoreException;

    Set<Object> updateDiagnoses(int eventId, Map<String, Set<Integer>> mkbs, AuthData userData) throws CoreException;
}
