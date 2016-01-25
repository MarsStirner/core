package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Mkb;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbMkbBeanLocal {

    /**
     * Получение данных о диагнозе по идентификатору диагноза
     * @param id Идентификатор диагноза.
     * @return Данные о обращении как Mkb entity
     * @throws CoreException
     * @see Mkb
    */
    Mkb getMkbById(int id) throws CoreException;

    /**
     * Получение данных о диагнозе по коду диагноза
     * @param code Код МКБ диагноза.
     * @return Данные о обращении как Mkb entity
     * @throws CoreException
     * @see Mkb
     */
    Mkb getMkbByCode(String code) throws CoreException;

    /**
     * Получение МКБ по его коду (без исключений если не найдено МКБ)
     * @see #getMkbByCode(String) То-же самое но с исключением
     * @param code Код МКБ Диагноза
     * @return Данные о обращении как Mkb entity \ null если не найдено
     */
    Mkb getByCode(final String code);

    /**
     * Получение МКБ по его коду (без исключений если не найдено МКБ)
     * @see #getMkbById То-же самое но с исключением
     * @param id Идентификатор диагноза
     * @return Данные о обращении как Mkb entity \ null если не найдено
     */
    Mkb getById(int id);
}
