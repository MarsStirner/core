package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 17:40<br>
 * Company Korus Consulting IT<br>
 */
@Local
public interface DbQuotingBySpecialityBeanLocal {

    public List<QuotingBySpeciality> getQuotingByOrganisation(String organisationUid) throws CoreException;
}