package ru.korus.tmis.core.database.epgu;

import ru.korus.tmis.core.entity.model.communication.QueueTicket;

import javax.ejb.Local;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 28.10.13, 19:53 <br>
 * Company: Korus Consulting IT <br>
 * Description: Интефейс бина для работы с таблицей EPGUTicket <br>
 */
@Local
public interface EPGUTicketBeanLocal {

    /**
     * Получение еще не отправленных записей\отмен записей на прием
     * @return Список изменений
     */
    List<QueueTicket> pullDatabase();

    /**
     * Изменение статуса талончика
     * @param ticket талончик
     * @param status статус, на который нужно поменять статус талончика
     */
    int changeStatus(QueueTicket ticket, QueueTicket.Status status);
}
