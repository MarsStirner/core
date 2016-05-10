package ru.korus.tmis.hsct.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.hsct.config.entities.Settings;

/**
 * Author: Upatov Egor <br>
 * Date: 05.05.2016, 17:45 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class HsctSettings {
    private static final Logger logger  = LoggerFactory.getLogger("HSCT");
    private static String RECEIVE_PASSWORD;
    private static String RECEIVE_USER;
    private static String RECEIVE_ACTIVE;

    private static String SEND_ACTIVE;
    private static String SEND_USER;
    private static String SEND_PASSWORD;

    private static String SERVICE_URL;

    public HsctSettings(final Settings settings) {
        RECEIVE_USER = settings.getReceiveUser();
        RECEIVE_PASSWORD = settings.getReceivePassword();
        RECEIVE_ACTIVE = settings.getReceiveActive();

        SEND_USER = settings.getSendUser();
        SEND_PASSWORD = settings.getSendPassword();
        SEND_ACTIVE = settings.getSendActive();

        SERVICE_URL = settings.getServiceUrl();
    }

    public static String ReceiveUser() {
        return RECEIVE_USER;
    }

    public static String ReceivePassword() {
        return RECEIVE_PASSWORD;
    }

    public static boolean isReceiveActive() {
        return "ON".equalsIgnoreCase(RECEIVE_ACTIVE);
    }

    public static String ServiceUrl() {
        return SERVICE_URL;
    }

    public static boolean isSendActive() {
        return "ON".equalsIgnoreCase(SEND_ACTIVE);
    }

    public static String SendUser() {
        return SEND_USER;
    }

    public static String SendPassword() {
        return SEND_PASSWORD;
    }

    public static String constructServiceUrl() {
        return SERVICE_URL.replaceFirst("\\{user_name\\}", SEND_USER)
                .replaceFirst("\\{user_token\\}", SEND_PASSWORD);
    }

    public static boolean checkSendConfiguration() {
        if (!HsctSettings.isSendActive()) {
            logger.warn("HsctSettings.sendActive is not \'on\' or is empty");
            return false;
        } else if (StringUtils.isEmpty(SERVICE_URL)) {
            logger.warn("HsctSettings.serviceUrl is empty");
            return false;
        } else if (StringUtils.isEmpty(SEND_USER)) {
            logger.warn("HsctSettings.sendUser is empty");
            return false;
        } else if (StringUtils.isEmpty(SEND_PASSWORD)) {
            logger.warn("HsctSettings.sendPassword is empty");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("HsctSettings{");
        sb.append("SERVICE_URL=\'").append(SERVICE_URL).append("\'\n");
        sb.append("SEND_ACTIVE=\'").append(SEND_ACTIVE).append("\'\n");
        sb.append("SEND_USER=\'").append(SEND_USER).append("\'\n");
        sb.append("SEND_PASSWORD=\'").append(SEND_PASSWORD).append("\'\n");
        sb.append("RECEIVE_ACTIVE=\'").append(RECEIVE_ACTIVE).append("\'\n");
        sb.append("RECEIVE_PASSWORD=\'").append(RECEIVE_PASSWORD).append("\'\n");
        sb.append("RECEIVE_USER=\'").append(RECEIVE_USER).append("\'\n");
        sb.append('}');
        return sb.toString();
    }
}
