package ru.korus.tmis.tfoms;

import java.util.Date;
import java.util.TimeZone;

/**
 * Класс для преобразования Дат, использующих текущую временную зону машины, в миллисекунды от начала эпохи (UTC)
 * и из миллисекунд от начала эпохи к локальным датам
 */
public final class DateConvertions {
    /**
     * конвертация локальной даты в миллисекунды от начала эпохи
     * @param date Дата, использующая текущую временную зону машины.
     * @return  Количество UTC миллисекунд от начала эпохи до локальной даты
     */
    public static long convertDateToUTCMilliseconds(final Date date) {
        if (date == null) {
            return 0L;
        } else {
            //Возвращаем кол-во миллисекунд из даты (преобразованной к локальному часовому поясу)
            // + смещение этой даты (В текущем часовом поясе машины) от UTC
            return date.getTime() + TimeZone.getDefault().getOffset(date.getTime());
        }
    }

    /**
     * Конвертация миллисекунд от начала эпохи в локальную дату
     * @param millisecondsCount Миллисекунды от полночи 1-го января 1970 года
     * @return Локальная дата, которая соответсвует UTC-дате построенной от миллисекунд начала эпохи
     */
    public static Date convertUTCMillisecondsToLocalDate(final long millisecondsCount) {
        return new Date(millisecondsCount - TimeZone.getDefault().getOffset(millisecondsCount));
    }
}