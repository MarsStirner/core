package ru.korus.tmis.util.logs.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.params.HttpConnectionParams;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Upatov Egor <br>
 * Date: 16.12.13, 13:37 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class RestfullLoggingSubsystemAppender extends AppenderBase<ILoggingEvent> {


    //Адрес сервиса куда отправляем запросы на логирование
    private String url;

    //Адрес для записей новых событий в подсистему журналирования
    private URI entryURI;
    //Адрес для получения списка логируемых уровней
    private URI levelURI;
    //Адрес для проверки доступности сервиса
    private URI stateURI;

    //Указанная кодировка
    private String charset;
    //Заголовок с типом содержимого
    private String contentType;

    //таймаут соединения & ожидания ответа
    private Integer timeout;
    private static final Integer DEFAULT_TIMEOUT = 3000;

    //Внтутренний список заголовков запроса
    private Header[] headers = new Header[2];


    //Layout возвращает строку в нужном формате,
    // в данном случае необходимо получить строковое отображение JSON-овского объекта
    private Layout<ILoggingEvent> layout;


    @Override
    public void start() {
        super.start();
        checkUrl();
        checkContentType();
        checkCharset();
        checkLayout();
        checkTimeout();
    }

    private void checkTimeout() {
        if(timeout != null && timeout > 0){
            addInfo(String.format("LoggingSubsystemAppender: Timeout is set to %d milliseconds", timeout));
        } else {
            addWarn("LoggingSubsystemAppender: Timeout is not set. Set it to 3000 milliseconds");
            timeout = DEFAULT_TIMEOUT;
        }
    }

    /**
     * Проверка заполненности charset
     */
    private void checkCharset() {
        if (charset != null && !charset.isEmpty()) {
            addInfo(String.format("LoggingSubsystemAppender: Charset is set to \"%s\"", charset));
        } else {
            contentType = "UTF8";
            addWarn(String.format("LoggingSubsystemAppender: Charset is not present. Set it to \"%s\"", charset));
        }
        headers[0] = new BasicHeader("charset", charset);
    }


    /**
     * Проверка Layout-а
     */
    private void checkLayout() {
        if (layout != null && layout.isStarted()) {
            addInfo(String.format("LoggingSubsystemAppender: layout is initialized and has type \"%s\"", layout.getClass().getName()));
        } else {
            addError("LoggingSubsystem: layout is not initialized. Appender not started");
            stop();
        }
    }


    /**
     * Проверка заполненности ContentType
     */
    private void checkContentType() {
        if (contentType != null && !contentType.isEmpty()) {
            addInfo(String.format("LoggingSubsystemAppender: Content-Type=\"%s\"", contentType));
        } else {
            contentType = "application/json";
            addWarn(String.format("LoggingSubsystemAppender: Content-Type is not present. Set it to \"%s\"", contentType));
        }
        headers[1] = new BasicHeader("content-type", contentType);
    }

    /**
     * Проверка заполненности URL
     */
    private void checkUrl() {
        if (url != null && !url.isEmpty()) {
            addInfo(String.format("LoggingSubsystemAppender: URL=\"%s\"", url));
            addInfo(String.format("LoggingSubsystemAppender: LevelURL=\"%s\"", levelURI.toString()));
            addInfo(String.format("LoggingSubsystemAppender: StateURL=\"%s\"", stateURI.toString()));
            addInfo(String.format("LoggingSubsystemAppender: EntryURL=\"%s\"", entryURI.toString()));
            HttpClient httpClient = new DefaultHttpClient();
            final HttpGet stateRequest = new HttpGet(stateURI);
            try {
                final HttpResponse stateResponse = httpClient.execute(stateRequest);
                addInfo(String.format("LoggingSubsystem: checkState return \"%s\"", convertStreamToString(stateResponse.getEntity().getContent())));
                addError(String.format("STATUS LINE %d %s", stateResponse.getStatusLine().getStatusCode(), stateResponse.getStatusLine().getReasonPhrase()));
            } catch (IOException e) {
                addError("LoggingSubsystemAppender: State request IO exception. Appender not started.", e);
                stop();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
            httpClient = new DefaultHttpClient();
            final HttpGet levelRequest = new HttpGet(levelURI);
            try {
                final HttpResponse levelResponse = httpClient.execute(levelRequest);
                addInfo(String.format("LoggingSubsystem: checkLevels return \"%s\"", convertStreamToString(levelResponse.getEntity().getContent())));
            } catch (IOException e) {
                addError("LoggingSubsystemAppender: Level request IO exception. Appender not started.", e);
                stop();
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
        } else {
            addError("LoggingSubsystemAppender: URL is not present. Appender not started.");
            stop();
        }
    }


    @Override
    protected void append(final ILoggingEvent event) {
        addWarn("######### NEW REQUEST");
        boolean result = sendToLoggingSubsystem(event);
        appendSendStatusToFile(event);
    }

    private void appendSendStatusToFile(final ILoggingEvent event) {
        addWarn("## APPEND TO SOME LOG_FILE");
    }

    private boolean sendToLoggingSubsystem(final ILoggingEvent event) {
        final HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), timeout);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), timeout);
        if (event != null || event.getMessage().length() > 1) {
            final HttpPost request = new HttpPost(entryURI);
            final String data;
            try {
                data = layout.doLayout(event);
                addInfo(data);
                request.setHeaders(headers);
                request.setEntity(new StringEntity(data, charset));
                final HttpResponse response = httpClient.execute(request);
                addWarn(response.toString());
            } catch (UnsupportedEncodingException e) {
                addError("Encoding exception", e);
            } catch (ClientProtocolException e) {
                addError("CP exception", e);
            } catch (IOException e) {
                addError("IO exception", e);
            } catch (Exception e) {
                addError("Exception", e);
            } finally {
                httpClient.getConnectionManager().shutdown();
            }
        }
        return true;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        try {
            this.entryURI = new URI(url.concat("/api/entry/"));
            this.levelURI = new URI(url.concat("/api/level/"));
            this.stateURI = new URI(url.concat("/api/"));
        } catch (URISyntaxException e) {
            addError("LoggingSubsystemAppender: URL can't parse to URI", e);
        }
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Layout getLayout() {
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    private static String convertStreamToString(final java.io.InputStream is) {
        final java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
}
