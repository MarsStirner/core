package ru.korus.tmis.core.hl7db;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 13.12.12
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public enum PharmacyStatus {
    COMPLETE("complete"),
    ADDED("added");

    private String status;

    PharmacyStatus(String status) {
        this.status = status;
    }
}
