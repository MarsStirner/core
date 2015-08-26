package ru.korus.tmis.core.ext.utilities;

import com.google.gson.Gson;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        17.04.2015, 15:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class MyJsonUtils {

    private static ObjectMapper mapper = new ObjectMapper();
    private static Gson gson = new Gson();

    static {
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
    }

    @Deprecated
    public static byte[] toJsonWithPadding(String callback, Object object) {
        try {
            //String s = mapper.writeValueAsString(object);
            String s = gson.toJson(object);
            final String res = callback + "(" + s + ")";
            return res.getBytes("UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return exceptionInfo(e);
        }
    }

    @Deprecated
    private static byte[] exceptionInfo(IOException e) {
        String res = ("\"errorMessage\":\"Internal error: cannot create json\"");
        try {
            res = mapper.writeValueAsString(e);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        try {
            return res.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
           return res.getBytes();
        }
    }
}
