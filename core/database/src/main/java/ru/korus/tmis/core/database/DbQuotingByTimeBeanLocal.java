package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.QuotingByTime;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * User: eupatov<br>
 * Date: 28.01.13 at 17:12<br>
 * Company Korus Consulting IT<br>
 */

@Local
public interface DbQuotingByTimeBeanLocal {
    /**
     * Получение ограничений для врача
     *
     * @param personId    ИД врача
     * @param date        Дата, на момент которой ищутся ограничения
     * @param quotingType Тип квотирования
     * @return Список ограничений
     */
    public List<QuotingByTime> getQuotingByTimeConstraints(int personId, Date date, int quotingType);
}
