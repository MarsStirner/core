package ru.korus.tmis.core.hl7db;


import javax.ejb.Local;

/**
 * Методы для работы с данными таблицы БД s11r64.UUID
 */
@Local
public interface DbUUIDBeanLocal {

    /**
     * Возвращает UUID по идентификатору id
     * @param id
     * @return
     */
    String getUUIDById(int id);


}
