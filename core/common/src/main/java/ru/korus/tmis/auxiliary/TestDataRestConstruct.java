package ru.korus.tmis.auxiliary;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Создание структур тестовых данных для unit тестов
 * @author idmitriev Systema-Soft
 */
public class TestDataRestConstruct {

   public static String makeTestDataRestResponse(String name, HttpServletRequest request, String response) {

        String methodType = request.getMethod();
        String requestUri = request.getRequestURI();
        String queryString = request.getQueryString();
        String parametersString = "";
        String valuesString = "";

        //int pos;
        Map<String, String> map = new HashMap<String, String>();
        String parsing = queryString;

        if(parsing.length()>0) {
            Boolean flgRead = true;
            while (flgRead) {
                int end = parsing.indexOf("&");
                int begin = parsing.indexOf("=");

                if(end>=0){
                    if(begin>=0 && end>begin) {
                        map.put(parsing.substring(0, begin),parsing.substring(begin + "=".length(), end));
                    }
                    else {
                        map.put(parsing.substring(0, end),"");
                    }
                    parsing = parsing.substring(end + "&".length());
                } else {
                    if(begin>=0) {
                        map.put(parsing.substring(0, begin),parsing.substring(begin + "=".length()));
                    }
                    else {
                        map.put(parsing,"");
                    }
                    flgRead = false;
                }
            }
        }

        Set<String> parameters = map.keySet();
        int counter = 0;
        for (java.util.Iterator<String> it = parameters.iterator(); it.hasNext(); counter++) {
            if (true) {
                String current = it.next();
                if(current.compareTo("test")!=0) {
                    parametersString =  String.format(((!parametersString.isEmpty())? parametersString + "&" : "") + current + "={%d}", counter);
                    valuesString = String.format(((!valuesString.isEmpty())? valuesString + "," : "") + "\"%s\"", map.get(current));
                } else {
                    counter--;
                }
            }
        }

        String testDataRestString = String.format("[\"%s\", \"%s\", \"%s\", \"%s\", [%s], \"%s\"]",name, methodType, requestUri, parametersString, valuesString, response);

        return testDataRestString;
    }
}
