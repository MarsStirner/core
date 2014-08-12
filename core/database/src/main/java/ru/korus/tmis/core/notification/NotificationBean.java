package ru.korus.tmis.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.Singleton;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.08.14, 11:21 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Singleton
public class NotificationBean implements NotificationBeanLocal {

    private static final Logger logger = LoggerFactory.getLogger(NotificationBeanLocal.class);

    private Map<String, Set<String>> flatCodeListeners = new HashMap<String, Set<String>>();

    @Override
    public void addListener(String flatCode, String modulePath) {
        if (flatCodeListeners.get(flatCode) == null) {
            flatCodeListeners.put(flatCode, new HashSet<String>());
        }
        flatCodeListeners.get(flatCode).add(modulePath);
    }

    @Override
    public void sendNotification(Action action) {
        if (action != null && action.getActionType() != null &&
                flatCodeListeners.get(action.getActionType().getFlatCode()) != null) {
            final String flatCode = action.getActionType().getFlatCode();
            for (String basePath : flatCodeListeners.get(flatCode)) {
                send(basePath, flatCode, action);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

        }
    }

    private void send(final String basePath, final String flatCode, final Action action) {
        //TODO: use asynch REST client
        new Thread() {
            public void run() {
                try {
                    final String urlPath = ConfigManager.Common().ServerUrl() + basePath + "/" + flatCode + "/" + action.getId();
                    logger.info("Send notification to: " + urlPath);
                    final URL url = new URL(urlPath);
                    final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setDoOutput(true);
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setRequestMethod("PUT");
                    int code = conn.getResponseCode();
                    logger.info("Response code: " + code);

                } catch (Throwable ex) {
                    logger.error("cannot to send a notification", ex);
                }
            }
        }.start();
    }
}
