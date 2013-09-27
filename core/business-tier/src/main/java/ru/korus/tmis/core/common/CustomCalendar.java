package ru.korus.tmis.core.common;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 26.09.13
 * Time: 17:42
 * To change this template use File | Settings | File Templates.
 */
public class CustomCalendar {
    Integer year;
    Integer month;
    Integer week;
    Integer day;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public CustomCalendar(Integer year, Integer month, Integer week, Integer day) {
        this.year = year;
        this.month = month;
        this.week = week;
        this.day = day;
    }
}
