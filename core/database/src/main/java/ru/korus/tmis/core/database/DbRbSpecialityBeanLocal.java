package ru.korus.tmis.core.database;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.filter.ListDataFilter;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: mmakankov
 * Date: 28.09.12
 * Time: 11:36
 * To change this template use File | Settings | File Templates.
 */

@Local
public interface DbRbSpecialityBeanLocal {

    long getCountOfBloodTypesWithFilter(Object filter) throws CoreException;

    java.util.LinkedList<Object> getAllSpecialitiesWithFilter(int page, int limit, String sorting, ListDataFilter filter)
            throws CoreException;
}
