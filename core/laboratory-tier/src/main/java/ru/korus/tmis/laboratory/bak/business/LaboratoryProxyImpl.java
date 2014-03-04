package ru.korus.tmis.laboratory.bak.business;

import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * @author: Dmitriy E. Nosov <br>
 * @description: <br>
 */
@Stateless
public class LaboratoryProxyImpl implements LaboratoryProxy {

    @EJB
    private BakBusinessBeanLocal bakLab;

    @Override
    public void start(int actionId) {
        try {
            bakLab.sendLisAnalysisRequest(actionId);
        } catch (CoreException e) {
            e.printStackTrace();
        }
    }
}
