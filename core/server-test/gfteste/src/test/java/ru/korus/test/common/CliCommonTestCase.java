/*package ru.korus.test.common;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.ws.rs.core.MediaType;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.*;
import org.jboss.arquillian.test.api.ArquillianResource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import ru.korus.tmis.core.data.PatientEntry;
import ru.korus.tmis.ws.webmis.rest.test.OnDeamandData;


@RunWith(org.jboss.arquillian.junit.Arquillian.class)
@RunAsClient
public class CliCommonTestCase {


	 //Создание архива для деплоя тестового приложения

	public static PatientEntry etalonPatientEntry;
	
	@Deployment(name="glassfish",testable = false)
	public static Archive createTestArchive() {

		 // Тестируемые классы и зависимости
		
		Class<?>[] classes = new Class<?>[] {
				OnDeamandData.class
		};

		/*
		 * EnterpriseArchive - ear
		 * WebArchive - war
		 * JavaArchive - jar
		 * используется апи ShrinkWrap
		 * 
		 */
/*		WebArchive wa = ShrinkWrap.create(WebArchive.class,"test.war");
	    wa.addClasses(classes);
		wa.addAsManifestResource(new File("./src/test/resources/META-INF/log4j.properties"));
		wa.setWebXML(new File("./src/test/resources/web.xml"));
		System.out.println(wa.toString());
		 return wa;
	}

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
	}

	@ArquillianResource
	URL deploymentUrl;
	
	static final String REST_PATH = "/test/rest/tms-registry" + "/patients/13";
	static final String TEST_PATH = "/test/rest/on-deamand-data/invoke";

	@Test
	public void testRest() throws Exception{
		Client client = new Client();
		WebResource wr = client.resource(new URL(deploymentUrl, TEST_PATH).toExternalForm());
		wr.accept(MediaType.APPLICATION_JSON);
		String json = null;

		String classToTest = "ru.korus.tmis.core.data.PatientEntry";
		json = wr.queryParam("clazz",classToTest).get(String.class);
		String originJson = ResourceBundle.getBundle("verifyjson").getString(classToTest);
		
		//{...} - убрать этот первый и последний символ
		StringTokenizer stz = new StringTokenizer(originJson.substring(1,originJson.length() - 1), ",");
		while (stz.hasMoreTokens()) {
			//искать вхождения в строке
            String nextToken = stz.nextToken();
			assertTrue("check for <" + nextToken + ">", json.contains(nextToken));//
		}
	}

	static final String NULL_PATH = "/test/rest/on-deamand-data/nullPointer";
	static final String NOSUCH_PATH = "/test/rest/on-deamand-data/noSuchEntity";
	
	@Test
	public void testErrors() throws Exception{
		Client client = new Client();

		WebResource wr = client.resource(new URL(deploymentUrl, NULL_PATH).toExternalForm());
		wr.accept(MediaType.APPLICATION_JSON);
		String json = null;

		json = wr.get(String.class);
		System.out.println(json);
	}
	
	@Test
	public void testCoreErrors() throws Exception{
		Client client = new Client();

		WebResource wr = client.resource(new URL(deploymentUrl, NOSUCH_PATH).toExternalForm());
		wr.accept(MediaType.APPLICATION_JSON);
		String json = null;

		json = wr.get(String.class);
		System.out.println(json);
	}

}*/