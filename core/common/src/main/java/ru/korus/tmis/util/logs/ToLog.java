package ru.korus.tmis.util.logs;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        14.02.13, 12:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Класс для записи продолжительных операций в виде строки, последовательность отделяется через скобки "[]" <br>
 */
public class ToLog {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd hh:mm:ss,SS";
    private StringBuffer sb;
    private long startTime;
    private long delta = 0;
    private long raz = 0;
    private long curr = 0;

    public ToLog(String start) {
        startTime = delta = System.currentTimeMillis();
        this.sb = new StringBuffer("[" + DateTime.now().toString(DATE_TIME_FORMAT) + "][" + start + "]");
    }

    public ToLog() {
        startTime = delta = System.currentTimeMillis();
        this.sb = new StringBuffer("[" + DateTime.now().toString(DATE_TIME_FORMAT) + "]");
    }

    public void add(String toLog) {
        curr = System.currentTimeMillis();
        raz = curr - delta;
        delta = curr;
        sb.append("[").append(toLog).append("]");
        if (raz > 0) {
            sb.append("(").append(String.valueOf(raz)).append("mls) ");
        }
    }

    public void add(String toLog, Object... arguments) {
        curr = System.currentTimeMillis();
        raz = curr - delta;
        delta = curr;
        int i = 0;
        final StringBuilder sb = new StringBuilder();
        for (StringTokenizer stringTokenizer = new StringTokenizer(toLog, "{}", true); stringTokenizer.hasMoreTokens(); i++) {
            String s = stringTokenizer.nextToken();
            if (s.equals("{}")) {
                if (i < arguments.length) {
                    sb.append(arguments[i++]);
                }
            } else {
                sb.append(s);
            }
        }
        sb.append("[").append(sb.toString()).append("]");
        if (raz > 0) {
            sb.append("(").append(String.valueOf(raz)).append("mls) ");
        }
    }

    public void startAdd(String toLog) {
        sb.append("[").append(toLog);
    }

    public void endAdd(String toLog) {
        curr = System.currentTimeMillis();
        raz = curr - delta;
        delta = curr;
        sb.append(" ").append(toLog).append("]");
        if (raz > 0) {
            sb.append("(").append(String.valueOf(raz)).append("mls) ");
        }
    }

    public void addPlain(String toLog) {
        curr = System.currentTimeMillis();
        raz = curr - delta;
        delta = curr;
        sb.append("[").append(toLog.replaceAll("\n", " ")).append("]");
        if (raz > 0) {
            sb.append("(").append(String.valueOf(raz)).append("mls) ");
        }
    }

    public String releaseString() {
        sb.append("(").append((System.currentTimeMillis() - startTime) / 1000).append("sec)");
        return sb.toString();
    }

    public String toString() {
        return sb.toString();
    }
}