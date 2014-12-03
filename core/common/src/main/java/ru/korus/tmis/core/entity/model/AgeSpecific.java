package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;

/**
 * Определяет, что сущность может быть применима или нет в зависимости от возраста пациента
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 12/1/14
 * Time: 9:11 PM
 */
public interface AgeSpecific {

    boolean isAgeValid(Date birthDate) throws CoreException;

}
