package ru.korus.tmis.ws.webmis.rest.providers;

import com.sun.jersey.api.json.JSONWithPadding;
import com.sun.jersey.core.provider.AbstractMessageReaderWriterProvider;
import com.sun.jersey.json.impl.provider.entity.JacksonProviderProxy;
import com.sun.jersey.spi.MessageBodyWorkers;

import javax.ejb.Singleton;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;
import java.util.logging.Logger;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 4/4/14
 * Time: 6:51 PM
 */
@Singleton
@Provider
@Produces({"application/x-javascript", "application/json", "application/xml" , "text/javascript", "application/javascript", "application/ecmascript", "application/x-ecmascript", "*/*", "application/xml"})
public class JSONWithPaddingProvider extends AbstractMessageReaderWriterProvider<JSONWithPadding> {

    private static final Logger LOGGER = Logger.getLogger(JSONWithPaddingProvider.class.getName());

    private final Map<String, Set<String>> javascriptTypes;

    @Context MessageBodyWorkers bodyWorker;

    JacksonProviderProxy pp = new JacksonProviderProxy();


    public JSONWithPaddingProvider() {
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
    public void writeTo(JSONWithPadding t, Class<?> typet, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        Object jsonEntity = t.getJsonSource();
        Type entityGenericType = jsonEntity.getClass();
        Class<?> entityType = jsonEntity.getClass();

        final boolean genericEntityUsed = jsonEntity instanceof GenericEntity;

        if (genericEntityUsed) {
            GenericEntity ge = (GenericEntity)jsonEntity;
            jsonEntity = ge.getEntity();
            entityGenericType = ge.getType();
            entityType = ge.getRawType();
        }

        final boolean isJavaScript = isJavascript(mediaType);
        final MediaType workerMediaType = isJavaScript ? MediaType.APPLICATION_JSON_TYPE : mediaType;

        if (isJavaScript) {
            entityStream.write(t.getCallbackName().getBytes());
            entityStream.write('(');
        }

        if(workerMediaType.equals(MediaType.APPLICATION_XML_TYPE)) {
            try {
                final JAXBContext context = JAXBContext.newInstance(t.getJsonSource().getClass());
                final Marshaller marshaller = context.createMarshaller();
                final StringWriter stringWriter = new StringWriter();
                marshaller.marshal(t.getJsonSource(), stringWriter);
                entityStream.write(stringWriter.toString().getBytes());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
        else
            pp.writeTo(jsonEntity, entityType, entityGenericType, annotations, workerMediaType, httpHeaders, entityStream);

        if (isJavaScript) {
            entityStream.write(')');
        }
    }
}