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

    private Map<Integer, Set<String>> actionTypeIdListeners = new HashMap<Integer, Set<String>>();

    @Override
    public void addListener(String flatCode, String modulePath) {
        final Map<String, Set<String>> map = flatCodeListeners;
        final String key = flatCode;
        addToMap(key, modulePath, map);
    }

    private<T> void addToMap(T key, String modulePath, Map<T, Set<String>> map) {
        if (map.get(key) == null) {
            map.put(key, new HashSet<String>());
        }
        map.get(key).add(modulePath);
    }

    @Override
    public void addListener(Integer actionTypeId, String modulePath) {
        addToMap(actionTypeId, modulePath, actionTypeIdListeners);
    }

    @Override
    public Set<String> getListener(Integer actionTypeId) {
        return actionTypeIdListeners.get(actionTypeId);
    }

    @Override
    public void sendNotification(Action action) {
        if (action != null) {
            if (action.getActionType() != null &&
                    flatCodeListeners.get(action.getActionType().getFlatCode()) != null) {
                final String flatCode = action.getActionType().getFlatCode();
                final Set<String> strings = flatCodeListeners.get(flatCode);
                sendToListeners(action, flatCode, strings);
            }
            if (action.getActionType() != null &&
                    actionTypeIdListeners.get(action.getActionType().getId()) != null) {
                final Set<String> strings = actionTypeIdListeners.get(action.getActionType().getId());
                sendToListeners(action, null, strings);
            }
        }
    }

    private void sendToListeners(Action action, String code, Set<String> strings) {
        for (String basePath : strings) {
            send(basePath, code, action);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private void send(final String basePath, final String flatCode, final Action action) {
        //TODO: use asynch REST client
        new Thread() {
            public void run() {
                try {
                    final String flatCodePath = flatCode == null || "".equals(flatCode) ? "" : flatCode + "/";
                    final String urlPath = ConfigManager.Common().ServerUrl() + basePath + "/" + flatCodePath + action.getId();
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
