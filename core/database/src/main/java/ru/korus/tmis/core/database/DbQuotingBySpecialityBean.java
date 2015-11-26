package ru.korus.tmis.core.database;


import ru.korus.tmis.core.entity.model.Organisation;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Speciality;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * User: EUpatov<br>
 * Date: 16.01.13 at 17:53<br>
 * Company Korus Consulting IT<br>
 */
@Stateless
public class DbQuotingBySpecialityBean implements DbQuotingBySpecialityBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;


    @Override
    public List<QuotingBySpeciality> getQuotingByOrganisation(final String organisationUid) {
        return em.createNamedQuery("QuotingBySpeciality.findByOrganizationInfis", QuotingBySpeciality.class)
                .setParameter("infisCode", organisationUid).getResultList();
    }

    @Override
    public List<QuotingBySpeciality> getQuotingBySpecialityAndOrganisation
            (final Speciality speciality, final String organisationInfisCode) {
        return em.createNamedQuery("QuotingBySpeciality.findByOrganizationInfisAndSpeciality", QuotingBySpeciality.class)
                .setParameter("infisCode", organisationInfisCode)
                .setParameter("speciality", speciality)
                .getResultList();
    }

    @Override
    public List<QuotingBySpeciality> getQuotingBySpecialityAndOrganisation
            (final Speciality speciality, final Organisation organisation) {
        return em.createNamedQuery("QuotingBySpeciality.findByOrganisationAndSpeciality", QuotingBySpeciality.class)
                .setParameter("organisation", organisation)
                .setParameter("speciality", speciality)
                .getResultList();
    }

    @Override
    public List<QuotingBySpeciality> getQuotingBySpeciality(final Speciality speciality) {
        return em.createNamedQuery("QuotingBySpeciality.findBySpeciality", QuotingBySpeciality.class)
                .setParameter("speciality", speciality).getResultList();
    }

    @Override
    public boolean incrementRemainingCoupons(QuotingBySpeciality toIncrement) {
        int oldValue = toIncrement.getCouponsRemaining();
        toIncrement.setCouponsRemaining(oldValue + 1);
        em.merge(toIncrement);
        em.flush();
        return oldValue + 1 == toIncrement.getCouponsRemaining();
    }

    @Override
    public boolean decrementRemainingCoupons(QuotingBySpeciality toDecrement) {
        int oldValue = toDecrement.getCouponsRemaining();
        toDecrement.setCouponsRemaining(oldValue - 1);
        em.merge(toDecrement);
        em.flush();
        return oldValue - 1 == toDecrement.getCouponsRemaining();
    }

    /**
     * Получение списка спецаильностей. которые не квотируются при записи из других ЛПУ
     *
     * @return список специальностей, которые не квотируются \ пустой список
     */
    @Override
    public List<Speciality> getUnquotedSpecialities() {
       return em.createNamedQuery("Speciality.getUnquoted", Speciality.class).getResultList();
    }


}

