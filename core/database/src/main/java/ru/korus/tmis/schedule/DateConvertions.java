package ru.korus.tmis.schedule;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;
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
    /**
     * конвертация локальной даты в миллисекунды от начала эпохи
     *
     * @param date Дата, использующая текущую временную зону машины.
     * @return
     */
    public static long convertDateToUTCMilliseconds(final Date date) {
        if (date == null) {
            logger.warn("Date convert method get \"null\" as param. Return \"0\" (Type: java.util.long)");
            return 0l;
        } else {
            //Возвращаем кол-во UTC миллисекунд из даты (преобразованной к локальному часовому поясу)
            return new LocalDateTime(date).toDateTime(DateTimeZone.UTC).getMillis();
        }
    }

    /**
     * Конвертация миллисекунд от начала эпохи в локальную дату
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return
     */
    public static Date convertUTCMillisecondsToLocalDate(final long millisecondsCount) {
        return new LocalDateTime(millisecondsCount, DateTimeZone.UTC).toDate();
    }
}