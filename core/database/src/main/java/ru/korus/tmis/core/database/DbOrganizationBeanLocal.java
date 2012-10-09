package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Organisation;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import javax.ejb.Local;

@Local
public interface DbOrganizationBeanLocal {
    List<Organisation> getAllOrganizations()
            throws CoreException;

    Organisation getOrganizationById(int id)
            throws CoreException;

    java.util.LinkedList<Object> getAllInsurenceOrganizationsData()
            throws CoreException;

    java.util.LinkedList<Object> getAllTFOMSOrganizationsData()
            throws CoreException;

    long getCountOfOrganizationWithFilter(Object filter)
            throws CoreException;

    java.util.LinkedList<Object> getAllOrganizationWithFilter(int page, int limit, String sortingField, String sortingMethod, Object filter)
            throws CoreException;
}
