package ru.korus.tmis.prescription;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        22.05.14, 9:14 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Способы сортировки назначений лекарственных средств
 */
public enum PrescriptionGroupBy {
    /**
     * Сортировка по типу введения
     */
    moa,

    /**
     * Сортировка по типу действия
     */
    name,

    /**
     * Сортировка по типу интервала
     */
    interval,

    /**
     * Сортировка по пациентам
     */
    client,

    /**
     * Сортировка по назначившим врачам
     */
    createPerson;
}
