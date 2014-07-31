package ru.korus.tmis.testutil;

import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.testng.Assert;
import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.auth.AuthStorageBean;
import ru.korus.tmis.core.auth.AuthStorageBeanLocal;
import ru.korus.tmis.core.common.CommonDataProcessorBean;
import ru.korus.tmis.core.common.CommonDataProcessorBeanLocal;
import ru.korus.tmis.core.database.common.DbSettingsBean;
import ru.korus.tmis.core.database.common.DbSettingsBeanLocal;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.ConfigManager;
import ru.korus.tmis.util.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.06.14, 16:46 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class WebMisBase {
    private final static String BASE_URL_REST = "http://localhost:7713/%s/rest";
    public static final String TST_CALLBACK = "tstCallback";
    public static final Integer TEST_EVENT_ID = 189;
    public static final Integer TEST_CLIENT_ID = 2;

    private static DbUtil dbUtil = new DbUtil();

    public static Archive createArchive(String archiveName) {
        dbUtil.prepare();
        final WebArchive wa = ShrinkWrap.create(WebArchive.class, archiveName + ".war");
        wa.addAsWebInfResource(new File("../common/src/test/resources/META-INF/persistence.xml"), "classes/META-INF/persistence.xml");

        // common -------------------------------------------------------------------
        wa.addPackages(false, (new TestUtilCommon()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilBusiness()).getPackagesForTest());
        wa.addPackages(false, (new TestUtilDatabase()).getPackagesForTest());
        // --------------------------------------------------------------------------

        wa.addPackages(true, (new TestUtilWsLaboratory()).getPackagesForTest());
        wa.addPackages(true, (new TestUtilWsMedipad()).getPackagesForTest());
        wa.addClass(AuthStorageBeanLocal.class);
        wa.addClass(AuthStorageBean.class);

        wa.addClass(CommonDataProcessorBeanLocal.class);
        wa.addClass(CommonDataProcessorBean.class);

        wa.addClass(DbSettingsBeanLocal.class);
        wa.addClass(DbSettingsBean.class);

     /*   wa.addPackage(ru.korus.tmis.ws.webmis.rest.servlet.RestServlet.class.getPackage());
        wa.addPackage(ru.korus.tmis.ws.webmis.rest.WebMisREST.class.getPackage());
        wa.addPackage(ru.korus.tmis.ws.webmis.rest.interceptors.ExceptionJSONMessage.class.getPackage());*/

        wa.addAsWebInfResource(EmptyAsset.INSTANCE, ArchivePaths.create("beans.xml"));
        wa.addAsWebInfResource(new File("./target/test-classes/WEB-INF/web.xml"), "web.xml");
        System.out.println("**************************** createTestArchive medipad");
        return wa;
    }

    public static String getBaseUrlRest(String archName){
        return String.format(BASE_URL_REST, archName);
    }

    public static URL addGetParam(final URL url, final String name, String value) throws MalformedURLException {
        final String beg = url.toString().indexOf('?') < 0 ? "?" : "&";
        return new URL(url + beg + name + "=" + value);
    }

    public static URL addGetParams(final URL url, final String params) throws MalformedURLException {
        final String beg = url.toString().indexOf('?') < 0 ? "?" : "&";
        return new URL(url + beg + params);
    }

    public static String removePadding(String res, String tstCallback) {
        final String prefix = tstCallback + "(";
        Assert.assertTrue(res.startsWith(prefix));
        final String suffix = ")";
        Assert.assertTrue(res.endsWith(suffix));
        return res.substring(prefix.length()).substring(0, res.length() - suffix.length() - prefix.length());
    }

    public static HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        return conn;
    }

    public static int getResponseCode(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        System.out.println("Response code: " + code);
        String msg = conn.getResponseMessage();
        System.out.println("Response message: " + msg);
        return code;
    }

    /**
     * @param conn
     * @param code
     * @return
     * @throws java.io.IOException
     */
    public static String getResponseData(HttpURLConnection conn, int code) throws IOException {
        final InputStream inputStream = code == 200 ? conn.getInputStream() : conn.getErrorStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                inputStream));

        String output;
        System.out.println("Output from Server .... \n");
        String res = "";
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            res += output;
        }
        br.close();
        return res;
    }

    public static HttpURLConnection openConnection(URL url, AuthData authData, String method) throws IOException {
        HttpURLConnection conn = openConnection(url);
        if(authData != null) {
            conn.setRequestProperty("Cookie", "authToken=" + authData.getAuthToken().getId());
        }
        conn.setRequestMethod(method);
        return conn;
    }

    public static void toPostStream(String input, HttpURLConnection conn) throws IOException {
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();
    }

    public static HttpURLConnection openConnectionPost(URL url, AuthData authData) throws IOException {
        return openConnection(url, authData, "POST");
    }

    public static HttpURLConnection openConnectionGet(URL url, AuthData authData) throws IOException {
        return openConnection(url, authData, "GET");
    }

    public static HttpURLConnection openConnectionPut(URL url, AuthData authData) throws IOException {
        return openConnection(url, authData, "PUT");
    }

    public static HttpURLConnection openConnectionDel(URL url, AuthData authData) throws IOException {
        return openConnection(url, authData, "DELETE");
    }

    public static URL addGetParam(URL url, String name, List<String> valueList) throws MalformedURLException {
        for(String value : valueList) {
           url = addGetParam(url, name, value);
        }
        return url;
    }

    public static AuthData auth(AuthStorageBeanLocal authStorageBeanLocal) throws CoreException {
        // авторизация пользователм  'test' с ролью "медсестра приемного отделения”
        ConfigManager.Common().DebugTestMode_$eq("on");
        return authStorageBeanLocal.createToken("utest", "098f6bcd4621d373cade4e832627b4f6" /*MD5 for 'test'*/, 29);
    }
}
