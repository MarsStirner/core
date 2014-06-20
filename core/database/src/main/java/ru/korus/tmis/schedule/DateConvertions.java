package ru.korus.tmis.schedule;

import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.TimeZone;

/**
 * Класс для преобразования Дат, использующих текущую временную зону машины, в миллисекунды от начала эпохи (UTC)
 * и из миллисекунд от начала эпохи к локальным датам
 */
public final class DateConvertions {

    private static final Logger logger = LoggerFactory.getLogger(DateConvertions.class);
    private static final LocalDateTime JAN_1_1970 = new LocalDateTime(1970, 1, 1, 0, 0);

    //////////////////// Даты в миллисекунды
    /**
     * конвертация локальной даты в миллисекунды от начала эпохи
     *
     * @param date Дата, использующая текущую временную зону машины.
     * @return
     */
    public static long convertDateToUTCMilliseconds(final Date date) {
        //Возвращаем кол-во UTC миллисекунд из даты (преобразованной к локальному часовому поясу)
        return new LocalDateTime(date).toDateTime(DateTimeZone.UTC).getMillis();
    }

    public static long convertLocalDateToUTCMilliseconds(final LocalDate date) {
        return date.toDateTimeAtStartOfDay(DateTimeZone.UTC).getMillis();
    }

    public static long convertLocalTimeToUTCMilliseconds(final LocalTime time) {
        return time.getMillisOfDay();
    }

    public static long convertLocalDateTimeToUTCMilliseconds(final LocalDateTime dateTime) {
        return dateTime.toDateTime(DateTimeZone.UTC).getMillis();
    }

    //////////////////// миллисекунды в Даты
    /**
     * Конвертация миллисекунд от начала эпохи в локальную дату
     *
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return util.Date дата соответствующая миллисекундам от начала эпохи
     */
    public static Date convertUTCMillisecondsToDate(final long millisecondsCount) {
        return new LocalDateTime(millisecondsCount, DateTimeZone.UTC).toDate();
    }

    /**
     * Конвертация миллисекунд от начала эпохи в локальную дату
     *
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return jodatime LocalDate дата соответствующая миллисекундам от начала эпохи
     */
    public static LocalDate convertUTCMillisecondsToLocalDate(final long millisecondsCount) {
        return new LocalDate(millisecondsCount, DateTimeZone.UTC);
    }

    /**
     * Конвертация миллисекунд от начала эпохи в локальную дату
     *
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return jodatime LocalDateTime дата+время соответствующая миллисекундам от начала эпохи
     */
    public static LocalDateTime convertUTCMillisecondsToLocalDateTime(final long millisecondsCount) {
        return new LocalDateTime(millisecondsCount, DateTimeZone.UTC);
    }

    /**
     * Конвертация миллисекунд от начала эпохи в локальное время
     *
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return jodatime LocalTime время соответствующее миллисекундам от начала эпохи
     */
    public static LocalTime convertUTCMillisecondsToLocalTime(final long millisecondsCount){
        return new LocalTime(millisecondsCount, DateTimeZone.UTC);
    }


}