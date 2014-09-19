package ru.korus.tmis.admin.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.korus.tmis.admin.model.RisarSettings;
import ru.korus.tmis.admin.service.AllSettingsService;
import ru.korus.tmis.admin.service.RisarService;
import ru.korus.tmis.scala.util.ConfigManager;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.09.14, 11:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class RisarServiceImpl implements RisarService {

    private static final Logger logger = LoggerFactory.getLogger(RisarServiceImpl.class);

    @Autowired
    private AllSettingsService allSettingsService;

    private static RisarSettings risarSettings = new RisarSettings();


    @Override
    public RisarSettings getRisarSettings() {
        risarSettings.setUrl(ConfigManager.Risar().ServiceUrl());
        return risarSettings;
    }

    @Override
    public RisarSettings.ValidationState checkUrl(URL url) {
        RestTemplate rest = new RestTemplate();
        try {
            String urlFind = url + "/api/patients/v2/find/";
            logger.info("RISAR base URL test. send GET to: ", urlFind);
            String resultStr = rest.getForObject(urlFind, String.class);
            logger.info("RISAR base URL test. response: ", resultStr);
            risarSettings.setErrorMsg(null);
            return RisarSettings.ValidationState.OK;
        } catch (RestClientException ex ){
            logger.error("RISAR base URL test. error: ", ex);
            return setWrongUrlStatus(url.toString());
        }
    }

    private RisarSettings.ValidationState setWrongUrlStatus(String url) {
        risarSettings.setErrorMsg("wrong url: " + url);
        return RisarSettings.ValidationState.WRONG;
    }

    @Override
    public void updateRisarUrl(URL url) {
        if(checkUrl(url) == RisarSettings.ValidationState.OK) {
            allSettingsService.save("Risar.ServiceUrl", url.toString());
        }
    }

    @Override
    public RisarSettings.ValidationState checkUrl(String url) {
        try {
            return checkUrl(new URL(url));
        } catch (MalformedURLException e) {
            logger.error("RISAR base URL test. error: ", e);
            return setWrongUrlStatus(url);
        }

    }
}
