package ru.korus.tmis.auxiliary;


import ru.korus.tmis.core.exception.CoreException;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Set;

public class AuxiliaryFunctions {

    //Конвертирование строковой коллекции в целочисленную
    public static java.util.List<Integer> convertStringListTo(java.util.List<String> strList) throws CoreException {

        if (strList != null) {
            java.util.List<Integer> digList = new java.util.LinkedList<Integer>();
            for (int i = 0; i < strList.size(); i++) {
                try {
                    digList.add((strList.get(i).compareTo("*") == 0) ? -1 : Integer.valueOf(strList.get(i)));
                } catch (NumberFormatException ex) {
                    throw new CoreException(0x0106, "Некорректное значение: " + strList.get(i) + " в строке запроса");
                }
            }
            return digList;
        }
        return null;
    }

    //Сбор двумерного массива из url в мар
    public static java.util.LinkedHashMap<Integer, Integer> foldFilterValueToLinkedMapFromQuery(
            String fullQueryPath, String parseTitleBegin, String parseTitleEnd
    ) {
        if (fullQueryPath != null) {
            java.util.LinkedHashMap<Integer, Integer> map = new java.util.LinkedHashMap<Integer, Integer>();

            int pos;
            String parsing = fullQueryPath;
            String apf = "&";

            while ((pos = parsing.indexOf(parseTitleBegin)) >= 0) {
                pos = pos + parseTitleBegin.length();
                parsing = parsing.substring(pos);
                if ((pos = parsing.indexOf(parseTitleEnd)) >= 0) {
                    String strKey = parsing.substring(0, pos);
                    int key;
                    if (((key = Integer.parseInt(strKey)) > 0)) {
                        pos = pos + parseTitleEnd.length();
                        parsing = parsing.substring(pos);

                        String strValue = "";
                        if ((pos = parsing.indexOf(apf)) >= 0) {
                            strValue = parsing.substring(0, pos);
                            pos = pos + apf.length();
                            parsing = parsing.substring(pos);
                        } else {
                            strValue = parsing;
                        }
                        if (strValue.compareTo("desc") == 0) {
                            map.put(key, 1);
                        } else {
                            map.put(key, 0);
                        }

                    }
                } else {
                    break;
                }

            }
            return map;
        }
        return null;
    }

    //Сбор двумерного массива из url в мар
    public static java.util.Map<String, java.util.List<String>> foldFilterValueTo(
            MultivaluedMap<String, String> queryParams, String parseTitleBegin, String parseTitleEnd
    ) {

        if (queryParams != null) {
            java.util.Map<String, java.util.List<String>> map = new java.util.HashMap<>();
            Set<String> paramHandles = queryParams.keySet();
            for (final String paramHandle : paramHandles) {
                int pos, end;
                if ((pos = paramHandle.indexOf(parseTitleBegin)) >= 0) {
                    pos = pos + parseTitleBegin.length();
                    String cutCurrentHandle = paramHandle.substring(pos);
                    if ((end = cutCurrentHandle.indexOf(parseTitleEnd)) >= 0) {
                        String strKey = cutCurrentHandle.substring(0, end);
                        map.put(strKey, queryParams.get(paramHandle));
                    }
                }
            }
            return map;
        }
        return null;
    }

    //аналог trimRight из MFC
    public static String trimRight(String s, char c) {

        StringBuffer r = new StringBuffer(s.length());
        r.setLength(s.length());
        int current = -1;
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != c && current < 0) {
                current = i;
            }
            if (current >= 0) {
                r.setCharAt(i, s.charAt(i));
            }
        }
        return r.substring(0, current + 1);
    }
}