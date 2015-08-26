package ru.korus.tmis.core.ext.utilities;

import com.google.gson.Gson;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        07.05.2015, 20:42 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class TestUtil {
    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private static Gson gson = new Gson();
    public static byte[] toJson(Object object) throws UnsupportedEncodingException {
        return gson.toJson(object).getBytes("UTF-8");
    }

    public static <T> T fromJsonWithPadding(String res, java.lang.Class<T> classOfT) {
        int begIndex = res.indexOf("(");
        int endIndex = res.indexOf(")");
        if(begIndex >= 0 && endIndex > begIndex ) {
            String jsonString = res.substring(begIndex + 1, endIndex);
            return gson.fromJson(jsonString, classOfT);
        }
        return null;
    }
}
