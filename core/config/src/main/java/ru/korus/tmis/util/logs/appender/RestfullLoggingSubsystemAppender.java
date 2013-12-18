package ru.korus.tmis.util.logs.appender;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.Layout;
import ch.qos.logback.core.encoder.LayoutWrappingEncoder;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import ru.korus.tmis.util.logs.layout.JSONLayout;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 16.12.13, 13:37 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class RestfullLoggingSubsystemAppender extends AppenderBase<ILoggingEvent> {

    //Адрес сервиса куда отправляем запросы на логирование
    private String url;
    //Указанная кодировка
    private String charset;
    //Заголовок с типом содержимого
    private String contentType;

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
            addError("LoggingSubsystem: layout is not initialized");
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
        } else {
            addError("LoggingSubsystemAppender: URL is not present.");
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
        if (event != null || event.getMessage().length() > 1) {
            final HttpPost request = new HttpPost(url);
            final String data;
            try {
                data = layout.doLayout(event);
                addInfo(data);
                request.setHeaders(headers);
                request.setEntity(new StringEntity(data));
                final HttpResponse response = httpClient.execute(request);
                addWarn(response.toString());
            } catch (UnsupportedEncodingException e) {
                addError("Encoding exception");
            } catch (ClientProtocolException e) {
                addError("CP exception");
            } catch (IOException e) {
                addError("IO exception");
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
}
