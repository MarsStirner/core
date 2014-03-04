package ru.korus.tmis.laboratory.bak.business;

import javax.ejb.Local;

/**
 * @author: Dmitriy E. Nosov <br>
 * @description: <br>
 */
@Local
public interface LaboratoryProxy {

    void start(int actionId);
}
