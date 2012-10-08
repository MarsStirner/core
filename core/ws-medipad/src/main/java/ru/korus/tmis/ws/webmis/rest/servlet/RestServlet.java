package ru.korus.tmis.ws.webmis.rest.servlet;

import java.io.IOException;
import java.net.URI;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.map.ObjectMapper;

import ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage;

import com.sun.jersey.json.impl.BaseJSONMarshaller;
import com.sun.jersey.json.impl.writer.JsonEncoder;
import com.sun.jersey.spi.container.servlet.ServletContainer;

public class RestServlet extends ServletContainer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5202199725028362647L;

	public RestServlet() {
		// TODO Auto-generated constructor stub
	}

	public RestServlet(Class<? extends Application> appClass) {
		super(appClass);
		// TODO Auto-generated constructor stub
	}

	public RestServlet(Application app) {
		super(app);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int service(URI baseUri, URI requestUri, HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int result = 200;
		try{
			
			result = super.service(baseUri, requestUri, request, response);
		}catch (Exception ee){
			ee.printStackTrace();
			responseJsonError(ee, response);
		}
		return result;
	}
	
	
	public void responseJsonError(Exception ee, HttpServletResponse response) {
		try{
			
			ObjectMapper ob = new ObjectMapper();
			String s = 
					ob.writeValueAsString(new ExceptionJSONMessage(ee));
            response.setContentType(MediaType.APPLICATION_JSON + "; charset=UTF8"); //правильная кодировка
			response.getWriter().write(s);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	

}
