package ru.korus.tmis.ws.webmis.rest.test;

import java.io.Serializable;
import java.rmi.NoSuchObjectException;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ru.korus.tmis.core.data.PatientCardData;
import ru.korus.tmis.core.data.PatientData;
import ru.korus.tmis.core.exception.NoSuchClientDocumentException;

import com.sun.jersey.api.json.JSONWithPadding;


@Stateless
@Path("/on-deamand-data/")
@Produces(MediaType.APPLICATION_JSON)
public class OnDeamandData {


    @GET
    @Path("/invoke")
    @Produces(MediaType.APPLICATION_JSON)
    public Object invoke(@QueryParam("clazz") String _clazz) {
        Object result = null;
        Class<?> clazz;
        try {
            clazz = Class.forName(_clazz);
            result = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    @GET
    @Path("/nullPointer")
    @Produces(MediaType.APPLICATION_JSON)
    public Object nullPointerTestMethod() throws Exception {
        throw new NullPointerException("oohhh god");
    }


    @GET
    @Path("/noSuchEntity")
    @Produces(MediaType.APPLICATION_JSON)
    public Object noSuchEntityTestMethod() throws Exception {
        throw new NoSuchClientDocumentException(0, 0, "oohhh god messga");
    }


}
