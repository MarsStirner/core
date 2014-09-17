package ru.korus.tmis.util;

import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Author: Nosov Dmitriy
 * Date: 27.09.12
 * Time: 14:57
 */
public class TextUtils {
    /**
     * Заменяем во входных данных "," на "." и возвращаем число
     *
     * @param value исходная строка
     * @return число типа Int
     */
    public static int getRobustInt(final String value) {
        return Integer.parseInt(processString(value));
    }

    /**
     * Заменяем во входных данных "," на "." и возвращаем число
     *
     * @param value исходная строка
     * @return число типа Double
     */
    public static double getRobustDouble(final String value) {
        return Double.parseDouble(processString(value));
    }

    /**
     * Обработка строки, избавление от пробелов, замена , на .
     *
     * @param value исходная строка
     * @return корректная строка
     */
    private static String processString(final String value) {
        return value != null && !"".equals(value) ? value.trim().replaceAll(",", ".") : "0";
    }

    public static String getMD5(final String pass) {
        try {
            byte[] md5sum = MessageDigest.getInstance("MD5").digest(pass.getBytes());
            return (new HexBinaryAdapter()).marshal(md5sum).toLowerCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
