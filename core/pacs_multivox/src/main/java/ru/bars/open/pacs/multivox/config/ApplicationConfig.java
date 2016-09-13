package ru.bars.open.pacs.multivox.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;
import ru.bars.open.pacs.multivox.config.entities.Meta;
import ru.bars.open.pacs.multivox.config.entities.Settings;
import ru.bars.open.pacs.multivox.config.entities.SettingsResponse;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.naming.ConfigurationException;
import javax.ws.rs.core.Application;
import java.net.MalformedURLException;

/**
 * Author: Upatov Egor <br>
 * Date: 13.09.2016, 12:45 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
@Startup
@Singleton
public class ApplicationConfig extends Application {
    private static final Logger logger = LoggerFactory.getLogger("PACS");

    public static final String SYS_PROP_NAME_CONFIGURATION_SERVICE_URL = "ConfigServiceUrl.pacs";
    public static final String DEFAULT_VALUE_CONFIGURATION_SERVICE_URL = "http://www.hitsl-config-service.ru";
    public static final String SYS_PROP_NAME_CONFIGURATION_SERVICE_CONFIG_NAME = "ConfigServiceName.pacs";
    public static final String DEFAULT_VALUE_CONFIGURATION_SERVICE_CONFIG_NAME = "PACS";

    @PostConstruct
    public void init() throws ConfigurationException, MalformedURLException {
        logger.info("Start application");
        final String configurationServiceUrl = System.getProperty(SYS_PROP_NAME_CONFIGURATION_SERVICE_URL, DEFAULT_VALUE_CONFIGURATION_SERVICE_URL);
        final String configurationName = System.getProperty(
                SYS_PROP_NAME_CONFIGURATION_SERVICE_CONFIG_NAME, DEFAULT_VALUE_CONFIGURATION_SERVICE_CONFIG_NAME
        );
        final String fullUrl = configurationServiceUrl.concat("/").concat(configurationName);
        logger.info("FULL URL = \'{}\'", fullUrl);
        final RestTemplate template = new RestTemplate();
        final SettingsResponse settingsResponse;
        try {
            settingsResponse = template.getForObject(fullUrl, SettingsResponse.class);
        } catch (Exception e) {
            logger.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'", fullUrl, e);
            throw e;
        }
        if (settingsResponse != null) {
            final Meta meta = settingsResponse.getMeta();
            if (meta != null && HttpStatus.valueOf(meta.getCode()).is2xxSuccessful()) {
                final Settings settings = settingsResponse.getResult();
                if (settings != null) {
                    logger.info("Settings from Configuration_Service: {}", settings);
                    PacsSettings result = new PacsSettings(settings);
                    logger.info("After processing config is: {}", result);
                } else {
                    logger.error("Settings is not parsed correctly.");
                    throw new ConfigurationException("Incorrect settings format");
                }
            } else {
                logger.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'. Meta is not 200 : {}", fullUrl, meta);
                throw new ConfigurationException("Incorrect settings");
            }
        } else {
            logger.error("CRITICAL_ERROR: Cannot initialize settings from URL: \'{}\'. Null response", fullUrl);
            throw new ConfigurationException("Null response");
        }
    }

    @PreDestroy
    public void close(){
        logger.info("End application");
    }
}
