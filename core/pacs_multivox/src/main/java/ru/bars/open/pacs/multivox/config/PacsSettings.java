package ru.bars.open.pacs.multivox.config;


import org.apache.commons.lang.StringUtils;
import ru.bars.open.pacs.multivox.config.entities.Settings;


/**
 * Author: Upatov Egor <br>
 * Date: 05.05.2016, 17:45 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class PacsSettings {


    private static String AT_FLAT_CODE_PREFIX; // "multivox_"
    private static String APT_CODE_SEND_DATE; //"multivox_send_date"
    private static String APT_CODE_SEND_TIME; //"multivox_send_time"
    private static String APT_CODE_RESULT;   //"multivox_result"
    private static String APT_CODE_APP_LINK; //"multivox_app_link"
    private static String APV_APP_LINK;  // "mvox: -cmd:Load -StudyExternalID:M"
    private static String APV_RESULT; // "http://10.1.0.124/webpacs/#/images?StudyExternalID=M"


    public PacsSettings(final Settings settings)  {
        AT_FLAT_CODE_PREFIX = StringUtils.defaultString(settings.getAT_FLAT_CODE_PREFIX(), "multivox_");
        APT_CODE_SEND_DATE = StringUtils.defaultString(settings.getAPT_CODE_SEND_DATE(), "multivox_send_date");
        APT_CODE_SEND_TIME = StringUtils.defaultString(settings.getAPT_CODE_SEND_TIME(), "multivox_send_time");
        APT_CODE_RESULT = StringUtils.defaultString(settings.getAPT_CODE_RESULT(), "multivox_result");
        APT_CODE_APP_LINK = StringUtils.defaultString(settings.getAPT_CODE_APP_LINK(), "multivox_app_link");
        APV_APP_LINK = StringUtils.defaultString(settings.getAPV_APP_LINK(), "mvox: -cmd:Load -StudyExternalID:M");
        APV_RESULT = StringUtils.defaultString(settings.getAPV_RESULT(), "http://10.1.0.124/webpacs/#/images?StudyExternalID=M");
    }


    public static String getAT_FLAT_CODE_PREFIX() {
        return AT_FLAT_CODE_PREFIX;
    }

    public static String getAPT_CODE_SEND_DATE() {
        return APT_CODE_SEND_DATE;
    }

    public static String getAPT_CODE_SEND_TIME() {
        return APT_CODE_SEND_TIME;
    }

    public static String getAPT_CODE_RESULT() {
        return APT_CODE_RESULT;
    }

    public static String getAPT_CODE_APP_LINK() {
        return APT_CODE_APP_LINK;
    }

    public static String getAPV_APP_LINK() {
        return APV_APP_LINK;
    }

    public static String getAPV_RESULT() {
        return APV_RESULT;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PacsSettings{");
        sb.append("AT_FLAT_CODE_PREFIX='").append(AT_FLAT_CODE_PREFIX).append('\'');
        sb.append(", APT_CODE_SEND_DATE='").append(APT_CODE_SEND_DATE).append('\'');
        sb.append(", APT_CODE_SEND_TIME='").append(APT_CODE_SEND_TIME).append('\'');
        sb.append(", APT_CODE_RESULT='").append(APT_CODE_RESULT).append('\'');
        sb.append(", APT_CODE_APP_LINK='").append(APT_CODE_APP_LINK).append('\'');
        sb.append(", APV_APP_LINK='").append(APV_APP_LINK).append('\'');
        sb.append(", APV_RESULT='").append(APV_RESULT).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
