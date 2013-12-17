package ru.korus.tmis.util.logs;

import org.joda.time.DateTime;

import java.util.StringTokenizer;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        14.02.13, 12:53 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Класс для записи продолжительных операций в виде строки, последовательность отделяется через скобки "[]" <br>
 */
public class ToLog {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss,SS";
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

//    public void add(String toLog) {
//        curr = System.currentTimeMillis();
//        raz = curr - delta;
//        delta = curr;
//        sb.append("[").append(toLog).append("]");
//        if (raz > 0) {
//            sb.append("(").append(String.valueOf(raz)).append("mls) ");
//        }
//    }


    public void add(String toLog, Object... arguments) {
        curr = System.currentTimeMillis();
        raz = curr - delta;
        delta = curr;
        if (arguments != null) {
            int idx = 0;
            final StringBuilder str = new StringBuilder();
            for (StringTokenizer stringTokenizer = new StringTokenizer(toLog, "#", true); stringTokenizer.hasMoreTokens(); ) {
                String s = stringTokenizer.nextToken();
                if (s.equals("#")) {
                    if (idx < arguments.length) {
                        str.append(arguments[idx]);
                        idx++;
                    }
                } else {
                    str.append(s);
                }
            }
            toLog = str.toString();
        }
        sb.append("[").append(toLog).append("]");
        if (raz > 0) {
            sb.append("(").append(String.valueOf(raz)).append("mls) ");
        }
    }

    public void addN(String toLog, Object... arguments) {
        add(toLog, arguments);
        sb.append("\n");
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