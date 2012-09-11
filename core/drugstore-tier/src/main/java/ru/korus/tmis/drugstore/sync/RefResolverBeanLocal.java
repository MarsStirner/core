package ru.korus.tmis.drugstore.sync;

import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Organisation;

import javax.ejb.Local;

@Local
public interface RefResolverBeanLocal {
    String getDepartmentRef(OrgStructure orgStructure);

    String getOrganizationRef(Organisation organization);
}
