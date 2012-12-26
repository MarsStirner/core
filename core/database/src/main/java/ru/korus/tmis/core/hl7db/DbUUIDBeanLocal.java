package ru.korus.tmis.core.hl7db;


import javax.ejb.Local;

/**
 * @Author: Dmitriy E. Nosov <br>
 * @Date: 09.12.12, 15:49 <br>
 * @Company: Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * @Description: Методы для работы с данными таблицы БД s11r64.UUID<br>
 */
@Local
public interface DbUUIDBeanLocal {

    /**
     * Возвращает UUID по идентификатору id
     *
     * @param id
     * @return
     */
    String getUUIDById(int id);
}
