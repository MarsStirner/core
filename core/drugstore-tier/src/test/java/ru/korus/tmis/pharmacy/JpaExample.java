/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.korus.tmis.pharmacy;

import javax.ejb.Stateless;

/**
 *
 * @author SZagrebelny
 */
@Stateless
public class JpaExample implements JpaExampleBean {
    public static final String INFO_MSG = "hello EJB";
    
    @Override
    public String getInfo() {
        return INFO_MSG;
    }
    
}
