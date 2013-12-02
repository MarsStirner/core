package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbHospitalBedProfile;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

@Local
public interface DbRbHospitalBedProfileBeanLocal {

    RbHospitalBedProfile getRbHospitalBedProfileByName(String name)
            throws CoreException;

    RbHospitalBedProfile getRbHospitalBedProfileById(int id)
            throws CoreException;

    Iterable<RbHospitalBedProfile> getAllRbHospitalBedProfiles()
            throws CoreException;
}
