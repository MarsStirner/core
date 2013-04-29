package ru.korus.tmis.ws.users.devtest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Test;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.02.2013, 12:10:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class RegUserMgrTest {

    @Test
    public void getAllUsers() {
        String correctResponse =
                "[{\"fname\":\"\",\"pname\":\"\",\"lname\":\"гость\",\"position\":\"Прочий персонал\",\"roles\":[],\"login\":\"гость\"},{\"fname\":\"корус\",\"pname\":\"\",\"lname\":\"корус\",\"position\":\"Прочий персонал\",\"roles\":[],\"login\":\"корус\"},{\"fname\":\"Врач\",\"pname\":\"Врач\",\"lname\":\"Врач\",\"position\":\"Главный врач (директор,завед.,начальник)\",\"subdivision\":\"c61f87ae-8786-41a0-b9ca-8fbe86e3c751\",\"roles\":[\"doctor\"],\"login\":\"doctor1\"}]";

        URL url;
        try {

            System.out.println("========= Test GET /api/users: ");
            url = new URL("http://localhost:8080/tmis-ws-users/api/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            System.out.println("Response code: " + code);
            String msg = conn.getResponseMessage();
            System.out.println("Response message: " + msg);

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

    @Test
    public void auth() {
        String correctResponse =
                "{\"OK\":True, \"type\": \"Basic\", \"token\": \"1d78aee7-f7e7-413d-b18f-559c022e4958\" }";

        URL url;
        try {

            System.out.println("========= TestPOST /api/users/auth: ");
            final String login = "корус";
            final String pass = "363";
            String res = auth(login, pass);
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
     * @param login
     * @param pass
     * @return
     * @throws MalformedURLException
     * @throws IOException
     * @throws ProtocolException
     */
    private String auth(final String login, final String pass) throws MalformedURLException, IOException, ProtocolException {
        URL url;
        url = new URL("http://localhost:8080/tmis-ws-users/api/users/auth");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        String input = "{\"login\":\"" + login + "\",\"password\":\"" + pass + "\"}";
        OutputStream outputStream = conn.getOutputStream();
        outputStream.write(input.getBytes());
        outputStream.flush();

        int code = conn.getResponseCode();
        System.out.println("Response code: " + code);
        String msg = conn.getResponseMessage();
        System.out.println("Response message: " + msg);

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

        Assert.assertTrue(code == 200);
        return res;
    }

    @Test
    public void newUser() {

        URL url;
        try {

            System.out.println("========= TestPOST /api/users: ");
            url = new URL("http://localhost:8080/tmis-ws-users/api/users");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"fname\": \"Тимофеев\"," + "\"pname\": \"Дмитрий\"," + "\"lname\": \"Сергеевич\"," + "\"position\": \"Прочий персонал\"," +
                    "\"subdivision\": \"c61f87ae-8786-41a0-b9ca-8fbe86e3c751\"," + "\"roles\": [\"operator\", \"statist\"]," + "\"login\": \"ДТимофеев\"," +
                    "\"password\": \"363\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = conn.getResponseCode();
            System.out.println("Response code: " + code);
            String msg = conn.getResponseMessage();
            System.out.println("Response message: " + msg);

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

    @Test
    public void getUser() {
        String correctResponse =
                "{\"fname\":\"корус\",\"pname\":\"\",\"lname\":\"корус\",\"position\":\"Прочий персонал\",\"roles\":[],\"login\":\"корус\"}";

        URL url;
        try {

            System.out.println("========= Test GET /api/users/<person_uuid>: ");
            url = new URL("http://localhost:8080/tmis-ws-users/api/users/1d78aee7-f7e7-413d-b18f-559c022e4958");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            int code = conn.getResponseCode();
            System.out.println("Response code: " + code);
            String msg = conn.getResponseMessage();
            System.out.println("Response message: " + msg);

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

    @Test
    public void updateUser() {
        String correctResponse =
                "{“OK”: True}";

        URL url;
        try {

            System.out.println("========= Test PUT /api/users: ");
            String uuid = auth("ДТимофеев", "363");
            uuid = uuid.substring(0, uuid.lastIndexOf("\""));
            uuid = uuid.substring(uuid.lastIndexOf("\"") + 1);
            url = new URL("http://localhost:8080/tmis-ws-users/api/users/" + uuid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("Content-Type", "application/json");

            String input = "{\"fname\": \"Тимофеев\"," + "\"pname\": \"Дмитрий\"," + "\"lname\": \"Сергеевич\"," + "\"position\": \"Прочий персонал\"," +
                    "\"subdivision\": \"c61f87ae-8786-41a0-b9ca-8fbe86e3c751\"," + "\"roles\": [\"operator\", \"statist\"]," + "\"login\": \"ДТ\"," +
                    "\"password\": \"363\"}";

            OutputStream outputStream = conn.getOutputStream();
            outputStream.write(input.getBytes());
            outputStream.flush();

            int code = conn.getResponseCode();
            System.out.println("Response code: " + code);
            String msg = conn.getResponseMessage();
            System.out.println("Response message: " + msg);

            final InputStream inputStream = code == 200 ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String output;
            System.out.println("Output from Server .... \n");
            String res = "";
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                res += output;
            }

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

    @Test
    public void deleteUser() {
        String correctResponse =
                "{“OK”: True}";

        URL url;
        try {

            System.out.println("========= Test PUT /api/users: ");
            String uuid = auth("ДТ", "363");
            uuid = uuid.substring(0, uuid.lastIndexOf("\""));
            uuid = uuid.substring(uuid.lastIndexOf("\"") + 1);
            url = new URL("http://localhost:8080/tmis-ws-users/api/users/" + uuid);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("DELETE");
            conn.setRequestProperty("Content-Type", "application/json");

            int code = conn.getResponseCode();
            System.out.println("Response code: " + code);
            String msg = conn.getResponseMessage();
            System.out.println("Response message: " + msg);

            final InputStream inputStream = code == 200 ? conn.getInputStream() : conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String output;
            System.out.println("Output from Server .... \n");
            String res = "";
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                res += output;
            }

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

}
