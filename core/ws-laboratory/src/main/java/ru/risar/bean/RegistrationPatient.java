package ru.risar.bean;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.validation.Validator;
import ru.risar.data.Patient;

import javax.ejb.Local;

/**
 * Сервис для сохранения информации о пациенте
 *
 * @author anosov
 *         Date: 27.07.13 21:06
 */
@Local
public interface RegistrationPatient {
    /**
     * Метод сохраняет личные данные пациента в БД МИС
     *
     * @param patient - личные данные пациента
     * @return true - если "удача", false - ошибка сохранения
     * @throws CoreException
     */
    boolean register(final Patient patient) throws CoreException;

    /**
     * Валидация входных данных
     * @param patient - входные данные
     * @return validator - объект хранящий информацию о валидации данных
     */
    Validator validate(final Patient patient);
}
