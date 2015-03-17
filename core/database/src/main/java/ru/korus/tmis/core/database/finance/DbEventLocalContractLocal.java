package ru.korus.tmis.core.database.finance;

import ru.korus.tmis.core.data.PayerInfo;
import ru.korus.tmis.core.data.PaymentContractInfo;
import ru.korus.tmis.core.entity.model.Event;
import ru.korus.tmis.core.entity.model.EventLocalContract;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.14, 17:31 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Local
public interface DbEventLocalContractLocal {

    /**
     * Получить договор по ID обращения
     * @param eventId - ID обращения
     * @return Договор или null, если договор не найден
     */
    /*EventLocalContract getByEventId(Integer eventId);*/

    /**
     * Получить договор по номеру
     * @param numberOfContract - номер договора
     * @return Договор или null, если договор не найден
     */
    EventLocalContract getByContractNumber(String  numberOfContract);

    /**
     * Создать новый договор для обращения
     * @param code - номер договора
     * @param dateContract - дата договора
     * @param event - обращение
     * @param paidName - ФИО плательщика
     * @param birthDate - дата рождения плательщика
     * @return Новый договор
     * @throws CoreException если договор
     */
    EventLocalContract create(String code, Date dateContract, Event event, PersonFIO paidName, Date birthDate) throws CoreException;

    EventLocalContract insertOrUpdate(Event event, PayerInfo payerInfo, PaymentContractInfo paymentContractInfo) throws CoreException;

}
