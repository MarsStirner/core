package ru.korus.tmis.ws.webmis.rest;

import com.sun.jersey.spi.container.ResourceFilterFactory;
import com.sun.jersey.spi.container.ResourceFilter;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import com.sun.jersey.api.model.AbstractMethod;

import java.util.Collections;
import java.util.List;
import java.util.Date;
import javax.ws.rs.core.Response;
import javax.ws.rs.WebApplicationException;
import javax.ejb.EJB;

import ru.korus.tmis.core.auth.AuthToken;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBean;
import ru.korus.tmis.ws.webmis.rest.CurrentAuthContext;
import ru.korus.tmis.ws.webmis.rest.ThreadLocalByRequest;

public class AuthentificationRESTFilterFactory implements ResourceFilterFactory {

    @EJB
    private AuthStorageBean authStorage;// = new AuthStorageBean();

    @Override
    public List create(AbstractMethod method) {
        return Collections.singletonList((ResourceFilter) new Filter());
    }

    private class Filter implements ResourceFilter, ContainerRequestFilter {
        protected Filter() {
        }

        public ContainerRequestFilter getRequestFilter() {
            return this;
        }

        public ContainerResponseFilter getResponseFilter() {
            return null;
        }

        public ContainerRequest filter(ContainerRequest request) {

            CurrentAuthContext context = new CurrentAuthContext();
            String token = request.getHeaderValue("cookie");   //посм getCookies()
            if (token != null) {
                token = token.substring(token.indexOf("=") + 1);
            }
            if (token != null) {
                AuthToken authToken = new AuthToken(token);
                //данные об авторизации
                AuthData authData = authStorage.getAuthData(authToken);
                if (authData == null) {
                    //<==данные об авторизации не найдены, сгенерим ошибку, отправим нуль в контекст
                    context.setCurrentAuthContext(authData, CurrentAuthContext.HResult.E_NOAUTHDATA, "NO_AUTHENTIFICATION_DATA");
                    ThreadLocalByRequest.set(context);
                    return request;
                }
                //валидность сертификата по времени
                Date tokenEndDate = authStorage.getAuthDateTime(authToken);
                if (tokenEndDate != null) {
                    if (tokenEndDate.before(new Date())) {
                        context.setCurrentAuthContext(authData, CurrentAuthContext.HResult.E_INVALIDPERIOD, "PERIOD_IS_COMPLETED");
                        ThreadLocalByRequest.set(context);
                        return request;
                    }
                } else {
                    context.setCurrentAuthContext(authData, CurrentAuthContext.HResult.E_NOENDDATE, "NO_END_DATE");
                    ThreadLocalByRequest.set(context);
                    return request;
                }
                //Запишем в контекст данные об авторизации...
                context.setCurrentAuthContext(authData, CurrentAuthContext.HResult.S_OK, "ALL OK!");
                ThreadLocalByRequest.set(context);
                return request;
            } else { //временно чтоб тестить без авторизации
                context.setCurrentAuthContext(null, CurrentAuthContext.HResult.E_NOAUTHDATA, "NO_AUTHENTIFICATION_DATA");
                ThreadLocalByRequest.set(context);
                return request;
            }
            //throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
