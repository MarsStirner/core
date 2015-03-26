package ru.korus.tmis.core.database.kladr;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.korus.tmis.core.data.DictionaryListRequestDataFilter;
import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;
import ru.korus.tmis.scala.util.ConfigManager;

import javax.ejb.Stateless;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        05.03.2015, 13:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Stateless
public class DbSchemeKladrBean implements DbSchemeKladrBeanLocal {

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class CityKladr {

        private List<Kladr> data;

        public List<Kladr> getData() {
            return data;
        }

        public void setData(List<Kladr> data) {
            this.data = data;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    static class StreetKladr {

        private List<Street> data;

        public List<Street> getData() {
            return data;
        }

        public void setData(List<Street> data) {
            this.data = data;
        }
    }

    static class RequestPrm {

        private String level;

        private String identparent;

        private String is_actual;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getIdentparent() {
            return identparent;
        }

        public void setIdentparent(String identparent) {
            this.identparent = identparent;
        }

        public String getIs_actual() {
            return is_actual;
        }

        public void setIs_actual(String is_actual) {
            this.is_actual = is_actual;
        }
    }

    private static final Map<String, String> levelsMap= new HashMap<String, String>() {{
        put("republic", "1" );
        put("district", "2" );
        put("city", "3" );
        put("locality", "4" ); }};

    @Override
    public Kladr getKladrByCode(String code) throws CoreException {
        String baseUrl = ConfigManager.RbManagerSetting().ServiceUrl();
        RestTemplate restTemplate = new RestTemplate();
        String cityCode = getCityCode(code);
        String url = baseUrl + "/api/kladr/city/" + cityCode + "/";
        try {
            CityKladr res = restTemplate.getForObject(url, DbSchemeKladrBean.CityKladr.class);
            return res.getData().isEmpty() ? null : res.getData().get(0);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return null;
        }

    }

    private List<Street> getStreetByCode(String code, boolean isAll) {
        String baseUrl = ConfigManager.RbManagerSetting().ServiceUrl();
        RestTemplate restTemplate = new RestTemplate();
        String url = baseUrl + "/api/kladr/street/" + ( isAll ?  "search/" + getCityCode(code) + "/" : (getStreetCode(code) + "/"));
        try {
            StreetKladr res = restTemplate.getForObject(url, StreetKladr.class);
            return res.getData();
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return new LinkedList<Street>();
        }
    }


    @Override
    public Street getStreetByCode(String code) throws CoreException {
        List<Street> streetsByCode = getStreetByCode(code, false);
        return streetsByCode.isEmpty() ? null : streetsByCode.get(0);
    }

    @Override
    public long getCountOfKladrRecordsWithFilter(Object filter) throws CoreException {
        List<Object> res = findKladrRecods(filter);
        return res.size();
    }

    private List<Object> findKladrRecods(Object filter) throws CoreException {
         List<Object> res = new LinkedList<Object>();
        if(filter instanceof DictionaryListRequestDataFilter) {
            DictionaryListRequestDataFilter kladrFilter = (DictionaryListRequestDataFilter) filter;
            if(kladrFilter.getLevel().equals("street")) {
                res.addAll(getStreetByCode(kladrFilter.getParent(), true));
            } else {
                RequestPrm requestPrm = new RequestPrm();
                requestPrm.setIs_actual("1");
                requestPrm.setLevel(levelsMap.get(kladrFilter.getLevel()));
                if(requestPrm.level != null) {
                    if( !"1".equals(requestPrm.level)) {
                        requestPrm.setIdentparent(getCityCode(kladrFilter.getParent()));
                    }
                }
                res.addAll(find(requestPrm));
            }
        }
        return res;
    }

    private List<Kladr> find(RequestPrm requestPrm) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<RequestPrm> entity = new HttpEntity<RequestPrm>(requestPrm, headers);
        RestTemplate restTemplate = new RestTemplate();
        String baseUrl = ConfigManager.RbManagerSetting().ServiceUrl();
        String url = baseUrl + "/api/find/KLD172/";
        try {
            CityKladr res = restTemplate.postForObject(url, entity, DbSchemeKladrBean.CityKladr.class);
            return res.getData().isEmpty() ? new LinkedList<Kladr>() : res.getData();
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Object> getAllKladrRecordsWithFilter(int page, int limit, String sorting, ListDataFilter filter) throws CoreException {
        List<Object> res = findKladrRecods(filter);
        if(limit == 0) {
            return res;
        } else {
            return res.subList(page * limit, Math.min(page * limit + limit, res.size()));
        }
    }

    private String getCityCode(String code) {
        int IDENT_CODE_SIZE = 11;
        return getIdentcode(code, IDENT_CODE_SIZE);
    }

    private String getStreetCode(String code) {
        int IDENT_CODE_SIZE = 15;
        return getIdentcode(code, IDENT_CODE_SIZE);
    }

    private String getIdentcode(String code, int IDENT_CODE_SIZE) {
        return code.substring(0, Math.min(IDENT_CODE_SIZE, code.length()));
    }


}
