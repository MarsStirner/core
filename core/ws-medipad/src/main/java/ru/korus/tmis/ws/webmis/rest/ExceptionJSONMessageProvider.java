package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.core.provider.AbstractMessageReaderWriterProvider;
import org.codehaus.jackson.map.ObjectMapper;
import ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage;

import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 9/3/14
 * Time: 12:59 PM
 */

@Stateless
@Produces({"application/javascript", "application/x-javascript", "application/json", "application/xml"})
@Provider
public class ExceptionJSONMessageProvider extends AbstractMessageReaderWriterProvider<ExceptionJSONMessage> {
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    @Override
    public ExceptionJSONMessage readFrom(Class<ExceptionJSONMessage> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        throw new UnsupportedOperationException("Not supported by design.");
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == ExceptionJSONMessage.class;
    }

    @Override
    public void writeTo(ExceptionJSONMessage exceptionJSONMessage, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        ObjectMapper ob = new ObjectMapper();
        String s = ob.writeValueAsString(exceptionJSONMessage);
        entityStream.write(s.getBytes(Charset.defaultCharset()));
    }
}
