/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.pharmacy;

import javax.ejb.Local;

/**
 *
 * @author SZagrebelny
 */
@Local
public interface JpaExampleBean {

    String getInfo();
    
}
