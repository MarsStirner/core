package ru.korus.tmis.core.database;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Organisation;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Speciality;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 17:53<br>
 * Company Korus Consulting IT<br>
 */
@Interceptors(value = {LoggingInterceptor.class})
@Stateless
public class DbQuotingBySpecialityBean implements DbQuotingBySpecialityBeanLocal {

    final static Logger logger = LoggerFactory.getLogger(DbQuotingBySpecialityBean.class);

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;


    @Override
    public List<QuotingBySpeciality> getQuotingByOrganisation(final String organisationUid) throws CoreException {
        return em.createQuery(getQuotingByOrganizationQuery, QuotingBySpeciality.class)
                .setParameter("HOSPITALUIDFROM", organisationUid).getResultList();
    }
    private String getQuotingByOrganizationQuery =
            "SELECT q FROM QuotingBySpeciality q where q.organisation.infisCode = :HOSPITALUIDFROM";

    @Override
    public List<QuotingBySpeciality> getQuotingBySpecialityAndOrganisation
            (final Speciality speciality, final String organisationInfisCode) {
        return em.createQuery(getQuotingBySpecialityAndOrganizationQuery, QuotingBySpeciality.class)
                .setParameter("ORGINFISCODE", organisationInfisCode)
                .setParameter("SPECIALITY",speciality)
                .getResultList();
    }
    private String getQuotingBySpecialityAndOrganizationQuery=
            "SELECT q FROM QuotingBySpeciality q where q.organisation.infisCode = :ORGINFISCODE " +
                    "AND q.speciality = :SPECIALITY";
}

