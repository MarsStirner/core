package ru.korus.tmis.core.entity.model;

import ru.korus.tmis.core.exception.CoreException;

import java.util.Date;

/**
 * Определяет, что сущность может быть применима или нет в зависимости от пола пациента
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 12/2/14
 * Time: 6:52 PM
 */
public interface SexSpecific {

    boolean isSexValid(Sex sex) throws CoreException;

}
