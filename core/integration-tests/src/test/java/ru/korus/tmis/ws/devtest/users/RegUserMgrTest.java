package ru.korus.tmis.ws.devtest.users;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.sql.SQLException;
import java.sql.Statement;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.codec.binary.Base64;
import org.testng.Assert;
import org.testng.annotations.Test;

import ru.korus.tmis.ws.transfusion.devtest.TestBase;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.02.2013, 12:10:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class RegUserMgrTest extends TestBase {

    private static final String BASE_URL = "https://localhost:8181/tmis-ws-users-mgr";

    static {
        disableSSL();
        initConnection();
    }

    @Test
    public void getAllUsers() {

        try {
            String correctResponse =
                    "{\"fname\":\"\",\"pname\":\"\",\"lname\":\"гость\",\"position\":\"Прочий персонал\",\"roles\":[],\"login\":\"гость\"}";

            System.out.println("========= getAllUsers: GET /api/users: ");
            HttpsURLConnection conn = openConnection(new URL(BASE_URL + "/api/users"));

            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains(correctResponse));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(groups = "a")
    public void auth() {
        String correctResponse =
                "{\"OK\":\"True\", \"type\": \"Basic\", \"token\": \"1d78aee7-f7e7-413d-b18f-559c022e4958\" }";
        try {

            System.out.println("========= auth: POST /api/users/auth: ");
            final String login = "корус";
            final String pass = "363";
            String res = auth(login, pass);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Assert.fail();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(groups = "a")
    public void getUser() {
        String correctResponse =
                "{\"fname\":\"корус\",\"pname\":\"\",\"lname\":\"корус\",\"position\":\"Прочий персонал\",\"roles\":[],\"login\":\"корус\"}";

        URL url;
        try {

            System.out.println("========= getUser: GET /api/users/<person_uuid>: ");
            url = new URL(BASE_URL + "/api/users/1d78aee7-f7e7-413d-b18f-559c022e4958");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "new", dependsOnGroups = "a")
    public void newUser() {
        URL url;
        try {

            try {
                final Statement s = conn.createStatement();
                s.executeUpdate(String.format("DELETE FROM Person WHERE lastName='%s'", "Тимофеев"));
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("========= newUser: POST /api/users: ");
            url = new URL(BASE_URL + "/api/users");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"fname\": \"Дмитрий\"," + "\"pname\": \"Сергеевич\"," + "\"lname\": \"Тимофеев\"," + "\"position\": \"Прочий персонал\"," +
                    "\"subdivision\": \"c61f87ae-8786-41a0-b9ca-8fbe86e3c751\"," + "\"roles\": [\"operator\", \"statist\"]," + "\"login\": \"ДТимофеев\"," +
                    "\"password\": \"363\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains("{\"OK\": \"True\""));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "updateUser", dependsOnGroups = "new")
    public void updateUser() {
        String correctResponse =
                "{\"OK\": \"True\"}";

        URL url;
        try {

            System.out.println("========= updateUser: PUT /api/users/<token>: ");
            String uuid = auth("ДТимофеев", "363");
            uuid = getUuid(uuid);
            url = new URL(BASE_URL + "/api/users/" + uuid);
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"fname\": \"Дмитрий\"," + "\"pname\": \"Сергеевич\"," + "\"lname\": \"Тимофеев\"," + "\"position\": \"Прочий персонал\"," +
                    "\"subdivision\": \"c61f87ae-8786-41a0-b9ca-8fbe86e3c751\"," + "\"roles\": [\"vrach_buryak\"]," + "\"login\": \"ДТ\"," +
                    "\"password\": \"363\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(dependsOnGroups = "roles")
    public void deleteUser() {
        try {
            String correctResponse =
                    "{\"OK\": \"True\"}";
            URL url;
            System.out.println("========= deleteUser: DELETE /api/users/<token>: ");
            String uuid = auth("ДТ", "363");
            uuid = getUuid(uuid);
            url = new URL(BASE_URL + "/api/users/" + uuid);
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    /**
     * @param uuid
     * @return
     */
    protected String getUuid(String uuid) {
        uuid = uuid.substring(0, uuid.lastIndexOf("\""));
        uuid = uuid.substring(uuid.lastIndexOf("\"") + 1);
        return uuid;
    }

    /**
     * 
     */
    static private void disableSSL() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        SSLContext sc;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param login
     * @param pass
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ProtocolException
     */
    private String auth(final String login, final String pass) throws MalformedURLException, IOException, ProtocolException {
        URL url;
        url = new URL(BASE_URL + "/api/users/auth");
        HttpsURLConnection conn = openConnection(url);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        String input = "{\"login\":\"" + login + "\",\"password\":\"" + pass + "\"}";
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();

        int code = getResponseCode(conn);

        String res = getResponseData(conn, code);

        Assert.assertTrue(code == 200);
        return res;
    }

    /**
     * @param url
     * @return
     * @throws IOException
     */
    private HttpsURLConnection openConnection(URL url) throws IOException {
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        basicAuthorization(conn);
        return conn;
    }

    /**
     * @param conn
     */
    private void basicAuthorization(HttpsURLConnection conn) {
        String authStringEnc = new String(Base64.encodeBase64("test:test".getBytes()));
        conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
    }

    /**
     * @param conn
     * @return
     * @throws IOException
     */
    private int getResponseCode(HttpsURLConnection conn) throws IOException {
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
     * @throws IOException
     */
    private String getResponseData(HttpsURLConnection conn, int code) throws IOException {
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

    @Test(groups = "a")
    public void getAllRoles() {

        try {
            String correctResponse =
                    "[{\"code\":\"admin\",\"title\":\"Администратор\"},{\"code\":\"personal\",\"title\":\"Отдел кадров\"},{\"code\":\"clinicRegistrator\",\"title\":\"Регистратор поликлиники\"}";

            System.out.println("========= getAllRoles: GET /api/users: ");
            HttpsURLConnection conn = openConnection(new URL(BASE_URL + "/api/roles"));

            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains(correctResponse));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(groups = "a")
    public void newRole() {
        URL url;
        try {

            try {
                final Statement s = conn.createStatement();
                s.executeUpdate(String.format("DELETE FROM rbUserProfile WHERE code='%s'", "vrach_buryak"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("========= newRole: POST /api/users: ");
            url = new URL(BASE_URL + "/api/roles");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"code\": \"vrach_buryak\"," +
                    "\"title\": \"Врач-буряк\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains("{\"OK\": \"True\""));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "roles")
    public void getRole() {
        String correctResponse =
                "{\"code\":\"admin\",\"title\":\"Администратор\"}";

        URL url;
        try {

            System.out.println("========= getRole: GET /api/roles/<role_code>: ");
            url = new URL(BASE_URL + "/api/roles/admin");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "updateRole", dependsOnGroups = "new")
    public void updateRole() {
        String correctResponse =
                "{\"OK\": \"True\"}";

        URL url;
        try {

            System.out.println("========= updateRole: PUT /api/users/<role_code>: ");

            url = new URL(BASE_URL + "/api/roles/vrach_buryak");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"code\": \"vrach_buryak\"," +
                    "\"title\": \"Врач буряк 1\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "deleteRole", dependsOnGroups = "updateRole")
    public void deleteRole() {
        try {
            String correctResponse =
                    "{\"OK\": \"True\"}";
            URL url;
            System.out.println("========= deleteRole: DELETE /api/role/<role_code>: ");
            url = new URL(BASE_URL + "/api/roles/vrach_buryak");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(correctResponse.equals(res));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(dependsOnGroups = "updateRole")
    public void getAllUsersByRole() {
        try {
            String correctResponse = "{\"users\":[{\"fname\":\"Дмитрий\",\"pname\":\"Сергеевич\",\"lname\":\"Тимофеев\"";

            System.out.println("========= getAllUsersByRole: GET /api/roles/<role_code>/user: ");
            HttpsURLConnection conn = openConnection(new URL(BASE_URL + "/api/roles/guest/users"));

            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains(correctResponse));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(groups = "roles", dependsOnGroups = "updateUser")
    public void addRole() {
        URL url;
        try {

            try {
                final Statement s = conn.createStatement();
                s.executeUpdate(String.format("DELETE FROM rbUserProfile WHERE code='%s'", "vrach_buryak"));
            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("========= addRole: POST /api/users/roles: ");
            url = new URL(BASE_URL + "/api/users/roles");
            HttpsURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String uuid = auth("ДТ", "363");
            uuid = getUuid(uuid);
            String input = "{\"role_code\": \"admin\"," +
                    "\"person_uuid\": \"" + uuid + "\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains("{\"OK\": \"True\""));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

    @Test(groups = "roles", dependsOnGroups = "updateUser")
    public void getUserRoles() {
        try {
            String correctResponse = "{\"roles\":[{\"code\":\"";

            System.out.println("========= getUserRoles: GET /api/uesrs/<role_code>/user: ");
            String uuid = auth("ДТ", "363");
            uuid = getUuid(uuid);
            HttpsURLConnection conn = openConnection(new URL(BASE_URL + "/api/users/" + uuid + "/roles/"));

            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains(correctResponse));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test(groups = "roles", dependsOnGroups = "updateUser")
    public void delUserRoles() {
        try {
            String correctResponse = "{\"OK\": \"True\"}";

            System.out.println("========= delUserRoles: DELETE /api/uesrs/<role_code>/user: ");
            String uuid = auth("ДТ", "363");
            uuid = getUuid(uuid);
            HttpsURLConnection conn = openConnection(new URL(BASE_URL + "/api/users/" + uuid + "/roles/guest"));

            conn.setRequestMethod("DELETE");
            int code = getResponseCode(conn);

            String res = getResponseData(conn, code);

            Assert.assertTrue(code == 200);
            Assert.assertTrue(res.contains(correctResponse));

        } catch (MalformedURLException e) {
            Assert.fail();
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

}
