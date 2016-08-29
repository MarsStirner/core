package ru.bars.open.pacs.multivox.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import ru.korus.tmis.core.entity.model.multivox.DbConnector;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Author: Upatov Egor <br>
 * Date: 22.08.2016, 12:16 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Stateless
public class DbConnectorDaoImpl {

    private static final Logger LOGGER = LoggerFactory.getLogger("PACS");

    @PersistenceContext(unitName = "multivoxDatasource")
    private EntityManager em;

    public DbConnector createMessageForRIS(final UUID uuid, final String message) {
        if (uuid == null || StringUtils.isEmpty(message)) {
            return null;
        }
        final DbConnector result = new DbConnector();
        result.setoID(UUID.randomUUID().toString());
        result.setUID(uuid.toString());
        result.setReplyUID(null);
        result.setSource(DbConnector.System.MIS);
        result.setDestination(DbConnector.System.RIS);
        result.setType("ORM^O01");
        result.setContentType(null);
        try {
            result.setMessage(message.getBytes("CP1251"));
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("CP1251 translation failed", e);
        }
        result.setSendTime(new Date());
        result.setExchangeTime(null);
        result.setProcTime(null);
        result.setErrorText(null);
        return result;
    }


    public void persist(final DbConnector message) {
        em.persist(message);
    }

    public DbConnector find(String key) {
        return em.find(DbConnector.class, key);
    }

    public List<DbConnector> getUnprocessedMessagesByDestination(final DbConnector.System system) {
        return em.createQuery("SELECT a FROM DbConnector a " +
                                      "WHERE a.destination = :destination " +
                                      "AND a.procTime IS NULL " +
                                      "AND EXISTS (" +
                                      "SELECT b FROM DbConnector b WHERE b.replyUID = a.UID) ", DbConnector.class)
                .setParameter("destination",system).getResultList();
    }

    public List<DbConnector> findByReplyUID(final String uid) {
        return em.createQuery("SELECT a FROM DbConnector a WHERE a.replyUID = :uid", DbConnector.class).setParameter("uid", uid).getResultList();
    }

    public DbConnector setProcessed(final DbConnector message) {
        message.setProcTime(new Date());
        message.setErrorText(null);
        return em.merge(message);
    }
}
