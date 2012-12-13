package ru.korus.tmis.core.hl7db;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 04.12.12
 * Time: 18:53
 * To change this template use File | Settings | File Templates.
 */

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.hl7.Pharmacy;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Методы для работы с данными таблицы БД s11r64.Pharmacy
 */
@Local
public interface DbPharmacyBeanLocal {

 //   Pharmacy createMessage(Action action, String flatCode) throws CoreException;

    Pharmacy getOrCreate(Action action) throws CoreException;

    Pharmacy updateMessage(Pharmacy pharmacy) throws CoreException;

    Action getMaxAction();

    /**
     * Достает последние по id записи
     * @param limit кол-во записей
     * @return
     */
    List<Action> getLastMaxAction(int limit);


    List<Action> getActionAfterDate(Date after);

    List<Pharmacy> getAllPharmacy();

    Pharmacy getPharmacyByAction(Action action);
}
