package ru.korus.tmis.pix.sda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.scala.util.ConfigManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        04.02.14, 11:09 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RbManager {

    private static class ReferenceProp {

        private final String tableName;

        private final String fieldName;

        public ReferenceProp(String tableName, String fieldName) {
            this.tableName = tableName;
            this.fieldName = fieldName;
        }
    }

    static final Logger logger = LoggerFactory.getLogger(RbManager.class);

    public static enum RbType {
        rbSocStatusType,
        Organisation,
        rbOKVED,
        rbDocumentType,
        KLD116,
        rbSpeciality,
        rbPost,
        rbFinance,
        PRK371,
        STR464,
        C42007,
        rbResult,
        rbAcheResult,
        MKB308,
        rbTraumaType,
        SST365;
    };

    static Map<RbType, ReferenceProp> referenceMap = new HashMap() {{
        put(RbType.rbSocStatusType, new ReferenceProp("rbSocStatusType", "code"));
        put(RbType.Organisation, new ReferenceProp("Organisation", "infisCode"));
        put(RbType.rbOKVED, new ReferenceProp("rbOKVED", "code"));
        put(RbType.rbDocumentType, new ReferenceProp("rbDocumentType", "code"));
        put(RbType.KLD116, new ReferenceProp("KLD116", "code"));
        put(RbType.rbSpeciality, new ReferenceProp("rbSpeciality", "code"));
        put(RbType.rbPost, new ReferenceProp("rbPost", "code"));
        put(RbType.rbFinance, new ReferenceProp("rbFinance", "code"));
        put(RbType.PRK371, new ReferenceProp("PRK371", "id"));
        put(RbType.STR464, new ReferenceProp("STR464", "id"));
        put(RbType.C42007, new ReferenceProp("C42007", "code"));
        put(RbType.rbResult, new ReferenceProp("rbResult", "code"));
        put(RbType.rbAcheResult, new ReferenceProp("rbAcheResult", "code"));
        put(RbType.MKB308, new ReferenceProp("MKB308", "mkb_code"));
        put(RbType.rbTraumaType, new ReferenceProp("rbTraumaType", "code"));
        put(RbType.SST365, new ReferenceProp("SST365", "code"));
    }};

    public static CodeNameSystem get(RbType rbType, CodeNameSystem code) {
        final ReferenceProp referenceName = referenceMap.get(rbType);
        if(referenceMap == null) {
            logger.info("RbManager: Unknown reference: {}", rbType.toString());
            return null;
        }
        return get(referenceName.tableName, referenceName.fieldName, code);
    }

    private static CodeNameSystem get(String tableName, String fieldName, CodeNameSystem code) {
        if(ConfigManager.RbManagerSetting().isDebugDemoMode()) {
            return code;
        }
        return get(tableName, fieldName, code.getCode());
    }

    private static CodeNameSystem get(String tableName, String fieldName, String value) {
        try {
            String baseUrl = ConfigManager.RbManagerSetting().ServiceUrl();
            logger.info("Send to RNM. tableName: '" + tableName + "' fieldName: '" + fieldName  + "' value: '" + value);
            if(value == null) {
                return null;
            }
            final String requestUrl = baseUrl + "/api/hs/" + tableName + "/" + fieldName + "/" + URLEncoder.encode(value, "UTF-8");
            logger.info("RBM request:" + requestUrl);
            URL url = new URL(requestUrl);
            HttpURLConnection conn = openConnection(url);
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            int code = getResponseCode(conn);
            String resData = getResponseData(conn, code);
            logger.info("RBM response status: " + code + "; response data : " + resData);
            if(code == 200) {
                RbManager.Response response = toJson(resData);
                CodeNameSystem res = CodeNameSystem.newInstance(response.getCode(), response.getName(), response.getCodingSystem());
                return res;
            } else {
                logger.error("referencebook manager return bad status : HTTP respose code: " + code + " data: " + resData);
                return null;
            }
        } catch (ProtocolException ex ) {
            logger.error("Not valid http protocol: ", ex);
            return null;
        } catch ( MalformedURLException ex ) {
            logger.error("Not valid URL: ", ex);
            return null;
        } catch (IOException ex ) {
            logger.error("Cannot read response: ", ex);
            return null;
        }
    }

    public static  RbManager.Response toJson(String json){
        return (new com.google.gson.Gson()).fromJson(json, RbManager.Response.class);
    }

    private static HttpURLConnection openConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoInput(true);
        return conn;
    }

    private static int getResponseCode(HttpURLConnection conn) throws IOException {
        int code = conn.getResponseCode();
        System.out.println("Response code: " + code);
        String msg = conn.getResponseMessage();
        System.out.println("Response message: " + msg);
        return code;
    }

    private static String getResponseData(HttpURLConnection conn, int code) throws IOException {
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

    public static class Response {
        private String oid;
        private Map<String, String> data;

        public String getCodingSystem() {
            return oid;
        }

        public String getCode() {
            return data.get("code");
        }

        public String getName() {
            return data.get("name");
        }
    }
}
