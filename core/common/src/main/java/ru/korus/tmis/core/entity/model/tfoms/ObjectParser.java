package ru.korus.tmis.core.entity.model.tfoms;

import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 03.04.2014, 17:42 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class ObjectParser {
    //// Integer
    public static Integer getIntegerValue(Object arg) {
        return arg != null && arg instanceof Number ? ((Number)arg).intValue() : null;
    }

    public static Integer getIntegerValue(Integer arg) {
        return arg;
    }

    public static Integer getIntegerValue(Long arg) {
        return arg.intValue();
    }

    public static Integer getIntegerValue(String arg){
        try {
           return Integer.valueOf(arg);
        } catch (NumberFormatException e){
           return null;
        }
    }

    //// Short
    public static Short getShortValue(Object arg) {
        return arg != null && arg instanceof Number ? ((Number)arg).shortValue() : null;
    }

    public static Short getShortValue(Integer arg) {
        return arg.shortValue();
    }

    public static Short getShortValue(Long arg) {
        return arg.shortValue();
    }

    public static Short getShortValue(Short arg) {
        return arg;
    }

    public static Short getShortValue(String arg){
        try {
            return Short.valueOf(arg);
        } catch (NumberFormatException e){
            return null;
        }
    }

    //// String
    public static String getStringValue(Object arg) {
        return arg != null && arg instanceof  String ? (String)arg : null;
    }

    public static String getStringValue(String arg) {
        return arg;
    }

    //// Date
    public static Date getDateValue(Object arg){
        return arg != null && arg instanceof Date ? (Date) arg : null;
    }

    //// Double
    public static Double getDoubleValue(Object arg){
        return arg != null && arg instanceof Number ? ((Number) arg).doubleValue() : null;
    }

    public static Double getDoubleValue(Double arg){
        return arg;
    }

    public static Double getDoubleValue(Float arg){
        return Double.valueOf(arg);
    }

    public static Double getDoubleValue(Integer arg){
        return Double.valueOf(arg);
    }


}
