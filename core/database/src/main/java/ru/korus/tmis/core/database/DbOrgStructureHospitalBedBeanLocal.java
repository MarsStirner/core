package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.OrgStructureHospitalBed;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: idmitriev
 * Date: 8/19/13
 * Time: 10:59 AM
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface DbOrgStructureHospitalBedBeanLocal {

    List<OrgStructureHospitalBed> getHospitalBedByDepartmentId(int departmentId) throws CoreException;

    List<OrgStructureHospitalBed> getBusyHospitalBedByIds(Collection<Integer> ids) throws CoreException;
}
