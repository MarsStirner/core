package ru.korus.tmis.core.entity.model.tfoms;

import au.id.jericho.lib.html.ParseText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 03.04.2014, 17:42 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class ObjectParser {
    private final static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final static SimpleDateFormat dateTimeFormat  = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //// Integer
    public static Integer getIntegerValue(Object arg) {
        if (arg != null) {
            if (arg instanceof Number) {
                return ((Number) arg).intValue();
            } else if (arg instanceof String) {
                try {
                    return Integer.valueOf((String) arg);
                } catch (NumberFormatException e) {
                    return null;
                }
            }
        }
        return null;
    }

    //// String
    public static String getStringValue(Object arg) {
        if(arg != null) {
            if (arg instanceof String) {
                return (String) arg;
            }
        }
        return null;
    }

    //// Date
    public static Date getDateValue(Object arg) {
        if (arg != null) {
            if (arg instanceof Date) {
                return (Date) arg;
            } else if (arg instanceof String) {
                SimpleDateFormat formatter = dateFormat;
                if(((String) arg).contains(":")) {
                     formatter = dateTimeFormat;
                }
                try {
                    return formatter.parse((String) arg);
                } catch (ParseException e) {
                    return null;
                }
            }
        }
        return null;
    }

    //// Double
    public static Double getDoubleValue(Object arg) {
        return arg != null && arg instanceof Number ? ((Number) arg).doubleValue() : null;
    }

    public static Double getDoubleValue(Double arg) {
        return arg;
    }

    public static Double getDoubleValue(Float arg) {
        return Double.valueOf(arg);
    }

    public static Double getDoubleValue(Integer arg) {
        return Double.valueOf(arg);
    }


}
