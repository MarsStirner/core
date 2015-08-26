package ru.korus.tmis.core.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionType;
import ru.korus.tmis.core.entity.model.NotificationAction;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.Singleton;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
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

    private <T> void addToMap(T key, String modulePath, Map<T, Set<String>> map) {
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
    public void sendNotification(NotificationAction notificationAction) throws CoreException {
        if (notificationAction != null && notificationAction.getAction() != null) {
            final ActionType actionType = notificationAction.getAction().getActionType();
            if (actionType != null &&
                    flatCodeListeners.get(actionType.getFlatCode()) != null) {
                final String flatCode = actionType.getFlatCode();
                final Set<String> urls = flatCodeListeners.get(flatCode);
                sendToListeners(notificationAction, flatCode, urls, true);
            }
            if (actionType!= null &&
                    actionTypeIdListeners.get(actionType.getId()) != null) {
                final Set<String> urls = actionTypeIdListeners.get(actionType.getId());
                sendToListeners(notificationAction, null, urls, false);
            }
        }
    }

    private void sendToListeners(final NotificationAction notificationAction, String code, Set<String> urls, boolean asynch) throws CoreException {
        for (String basePath : urls) {
            send(basePath, code, notificationAction, asynch);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
    }

    private void send(final String basePath, final String flatCode, final NotificationAction notificationAction, boolean asynch) throws CoreException {
        //TODO: use asynch REST client
        if (asynch) {
            new Thread() {
                public void run() {
                    try {
                        sendThread(flatCode, basePath, notificationAction);
                    } catch (Throwable ex) {
                        logger.error("cannot to send a notification", ex);
                    }
                }
            }.start();
        } else {
            sendThread(flatCode, basePath, notificationAction);
        }
    }

    private void sendThread(String flatCode, String basePath, final NotificationAction notificationAction) throws CoreException {
        try {
            final String flatCodePath = flatCode == null || "".equals(flatCode) ? "" : (flatCode + "/");
            final String urlPath = (basePath.startsWith("http") ? "" : ConfigManager.Common().ServerUrl())
                    + basePath + (basePath.endsWith("/") ? "" : "/") + flatCodePath + notificationAction.getAction().getId();
            logger.info("Send notification to: " + urlPath);
            final URL url;
            url = new URL(urlPath);
            final HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestMethod(notificationAction.getMethod());
            int code = conn.getResponseCode();
            logger.info("Response code: " + code);
            if (code != 200) {
                String msg = conn.getResponseMessage();
                throw new CoreException("Wrong response code: " + code + " url: " + urlPath + " + messege: " + msg);
            }
        } catch (ProtocolException e) {
            throw new CoreException(e);
        } catch (IOException e) {
            throw new CoreException(e);
        }
    }
}
