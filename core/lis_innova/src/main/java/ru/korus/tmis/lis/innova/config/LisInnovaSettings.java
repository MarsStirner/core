package ru.korus.tmis.lis.innova.config;

import ru.korus.tmis.lis.innova.config.entities.Settings;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Author: Upatov Egor <br>
 * Date: 05.05.2016, 17:45 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class LisInnovaSettings {

    private static String LAB_CODE; // = "0101";
    private static URL serviceURL; // = "http://10.1.0.109:9510/lis_service/";


    public LisInnovaSettings(final Settings settings) throws MalformedURLException {
        LAB_CODE = settings.getLAB_CODE();
        serviceURL = new URL(settings.getSERVICE_URL());
    }

    public static String getLabCode() {
        return LAB_CODE;
    }

    public static URL getServiceURL() {
        return serviceURL;
    }

}
