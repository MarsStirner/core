package ru.korus.tmis.prescription;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.util.ConfigManager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.08.13, 17:39 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Бин для старта Thrift-сервиса по работе с листами назначений <br>
 */
@Startup
@Singleton
@LocalBean
public class PrescriptionServerBean {
    static final Logger logger = LoggerFactory.getLogger(PrescriptionServerBean.class);

    private PrescriptionServer server;

    public PrescriptionServerBean() {
        logger.info("PrescriptionServerBean simple constructor called.");
    }

    @PostConstruct
    public void init() {
        logger.info("PrescriptionServerBean starting post construct.");
        int port = ConfigManager.Prescription().Port();
        int maxThreadCount = ConfigManager.Prescription().MaxThreads();
        try {
            server = PrescriptionServer.getInstance(port, maxThreadCount);
            server.startServer();

        } catch (Exception exception) {
            logger.error("Exception while initialize PrescriptionServerBean", exception);
        }
        logger.info("PrescriptionServerBean end of post construct");

    }

    @PreDestroy
    public void destroy() {
        logger.warn("PreDestroy called around PrescriptionServerBean.");
        server.endWork();
    }
}
