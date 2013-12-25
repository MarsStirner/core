package ru.korus.tmis.core.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.QuotingByTime;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: eupatov<br>
 * Date: 28.01.13 at 17:25<br>
 * Company Korus Consulting IT<br>
 */
@Interceptors(value = {LoggingInterceptor.class})
@Stateless
public class DbQuotingByTimeBean implements DbQuotingByTimeBeanLocal {
    final static Logger logger = LoggerFactory.getLogger(DbQuotingByTimeBean.class);

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    private final String getQuotingByTimeConstraintsQuery =
            " SELECT qbt FROM QuotingByTime qbt " +
                    " WHERE  qbt.doctor.id = :PERSONID " +
                    " AND qbt.quotingDate = :DATE " +
                    " AND qbt.quotingType = :QUOTINGTYPE ";

    /**
     * Получение ограничений для врача
     *
     * @param personId    ИД врача
     * @param date        Дата, на момент которой ищутся ограничения
     * @param quotingType Тип квотирования
     * @return Список ограничений
     */
    @Override
    public List<QuotingByTime> getQuotingByTimeConstraints(int personId, Date date, int quotingType) {
        List<QuotingByTime> result = em.createQuery(getQuotingByTimeConstraintsQuery, QuotingByTime.class).setParameter("PERSONID", personId)
                .setParameter("DATE", date, TemporalType.DATE).setParameter("QUOTINGTYPE", quotingType).getResultList();
        if (result == null) {
            result = new ArrayList<QuotingByTime>(0);
        }
        logger.debug("quotingByTimeConstraints list.size()={}", result.size());
        return result;
    }
}
