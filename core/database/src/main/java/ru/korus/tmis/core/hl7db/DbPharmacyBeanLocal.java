package ru.korus.tmis.core.hl7db;

import org.joda.time.DateTime;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.pharmacy.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * @author Dmitriy E. Nosov <br>
 * Date: 04.12.12, 18:53 <br>
 * Company: Korus Consulting IT<br>
 * Revision:    \$Id$ <br>
 * Description: Методы для работы с данными таблицы БД s11r64.Pharmacy<br>
 */
@Local
public interface DbPharmacyBeanLocal {

    /**
     * Поиск или создание записи об обращении в таблице Pharmacy
     *
     * @param action
     * @return
     * @throws CoreException
     */
    Pharmacy getOrCreate(Action action) throws CoreException;

    /**
     * Обновление записи
     *
     * @param pharmacy
     * @return
     * @throws CoreException
     */
    Pharmacy updateMessage(Pharmacy pharmacy) throws CoreException;    //todo необходимо убрать

    /**
     * Достает последние по id записи
     *
     * @param limit кол-во записей
     * @return
     */
    List<Action> getLastMaxAction(int limit);

    /**
     * Достает записи после какой-то даты
     *
     * @param after
     * @return
     */
    List<Action> getActionAfterDate(DateTime after);

    /**
     * Достает по Action строку из ранее сформированного Pharmacy
     *
     * @param action
     * @return
     */
    Pharmacy getPharmacyByAction(Action action);
}
