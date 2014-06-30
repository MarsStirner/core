package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.api.json.JSONWithPadding;
import com.sun.jersey.json.impl.provider.entity.JSONWithPaddingProvider;
import com.sun.jersey.spi.MessageBodyWorkers;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;

import javax.ejb.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 6/26/14
 * Time: 6:03 PM
 */
@Singleton
@Produces({"application/x-javascript", "application/json", "application/xml"})
@Provider
public class CustomJSONWithPaddingProvider extends JSONWithPaddingProvider {

    private final Map<String, Set<String>> javascriptTypes;

    public CustomJSONWithPaddingProvider() {
        javascriptTypes = new HashMap<String, Set<String>>();
        // application/javascript, application/x-javascript, text/ecmascript, application/ecmascript, text/jscript
        javascriptTypes.put("application", new HashSet<String>(Arrays.asList("x-javascript", "ecmascript", "javascript")));
        javascriptTypes.put("text", new HashSet<String>(Arrays.asList("ecmascript", "jscript")));
    }

    private boolean isJavascript(MediaType m) {
        Set<String> subtypes = javascriptTypes.get(m.getType());
        if (subtypes == null) return false;

        return subtypes.contains(m.getSubtype());
    }

    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return false;
    }

    @Override
    public JSONWithPadding readFrom(Class<JSONWithPadding> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        throw new UnsupportedOperationException("Not supported by design.");
    }

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return type == JSONWithPadding.class;
    }

    @Override
    public void writeTo(JSONWithPadding t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {

        /**
         * Плохой Provider, надо было лучше делать и ставить модификатор protected, а не private и default
         */
        MessageBodyWorkers bodyWorkers = null;
        try {
            Field f = this.getClass().getSuperclass().getDeclaredField("bodyWorker");
            f.setAccessible(true);
            bodyWorkers = (MessageBodyWorkers) f.get(this);
        } catch (Exception e) {
            throw new IOException(e);
        }

        // В Glassfish 4 инъекция не сработает и будет null - если не null, значит мы на Glassfish 3
        // На 3 остается старое поведение
        if(bodyWorkers != null) {
            super.writeTo(t, type, genericType, annotations, mediaType, httpHeaders, entityStream);
        // А на 4 будем писать данные вручную
        } else {
            final boolean isJavaScript = isJavascript(mediaType);

            if (isJavaScript) {
                entityStream.write(t.getCallbackName().getBytes());
                entityStream.write('(');
            }

            if (mediaType.equals(MediaType.APPLICATION_XML_TYPE)) {
                try {
                    JAXBContext context = JAXBContext.newInstance(t.getJsonSource().getClass());
                    Marshaller m = context.createMarshaller();
                    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
                    m.marshal(t.getJsonSource(), entityStream);
                } catch (Exception e) {
                    throw new WebApplicationException(e);
                }
            } else {
                try {
                    new JacksonJaxbJsonProvider().writeTo(t, type, genericType, annotations, mediaType, httpHeaders, entityStream);
                } catch (Exception e) {
                    throw new IOException(e);
                }
            }

            if (isJavaScript) {
                entityStream.write(')');
            }
        }
    }
}
