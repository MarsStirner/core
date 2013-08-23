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
 * Description:  <br>
 */
@Startup
@Singleton
@LocalBean
public class PrescriptoinServerBean {
    static final Logger logger = LoggerFactory.getLogger(PrescriptoinServerBean.class);

    private PrescriptoinServer prescriptoinServer;

    public PrescriptoinServerBean() {
    }

    @PostConstruct
    public void init() {
        logger.info("Start prescription service...");
//        try {

//            prescriptoinServer = new PrescriptoinServer();
//            prescriptoinServer.startServer(
//                    ConfigManager.Prescription().Port(),
//                    ConfigManager.Prescription().MaxThreads());

//        } catch (Exception e) {
//            logger.error("Exception while init service: " + e, e);
//        } finally {
//            logger.info("..end of post construct");
//        }
    }

    @PreDestroy
    public void destroy() {
        prescriptoinServer.stopServer();
        logger.info("...Stop prescription service");
    }
}
