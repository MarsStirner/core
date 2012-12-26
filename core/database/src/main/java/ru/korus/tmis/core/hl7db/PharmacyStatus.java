package ru.korus.tmis.core.hl7db;

/**
 * @author Dmitriy E. Nosov <br>
 * Date: 13.12.12, 12:21 <br>
 * Company: Korus Consulting IT<br>
 * Description: Статусы в которых может находится пакет предназначенный для отправки в 1С<br>
 */
public enum PharmacyStatus {
    /**
     * Пакет корректно отправлен в 1С
     */
    COMPLETE("complete"),
    /**
     * Пакет только что добавлен или его передача не удалась
     */
    ADDED("added");

    private String status;

    PharmacyStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
