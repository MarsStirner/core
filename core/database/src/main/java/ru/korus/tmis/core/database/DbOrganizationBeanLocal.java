package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Organisation;

import java.util.List;
import javax.ejb.Local;

@Local
public interface DbOrganizationBeanLocal {
    List<Organisation> getAllOrganizations();
}
