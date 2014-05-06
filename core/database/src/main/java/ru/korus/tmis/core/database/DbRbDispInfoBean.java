package ru.korus.tmis.core.database;

import org.joda.time.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.RbDispInfo;
import ru.korus.tmis.core.entity.model.RbMedicalKind;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 05.08.13, 13:34 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Stateless
public class DbRbDispInfoBean implements DbRbDispInfoBeanLocal {
    private static final Logger logger = LoggerFactory.getLogger("ru.korus.tmis.tfoms");

    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbDispInfo getById(int id) {
        return em.find(RbDispInfo.class, id);
    }

    @Override
    public List<RbDispInfo> getByCode(String code) {
        return em.createNamedQuery("rbDispInfo.findByCode", RbDispInfo.class)
                .setParameter("code", code).getResultList();
    }

    @Override
    public List<RbDispInfo> getByCodeAndMedicalKind(final String code, final RbMedicalKind medicalKind) {
        return em.createNamedQuery("rbDispInfo.findByCodeAndMedicalKind", RbDispInfo.class)
                .setParameter("code", code).setParameter("medicalKind", medicalKind)
                .getResultList();
    }

    @Override
    public RbDispInfo getBySexAgeAndMedicalKindCode(final short sex,
                                                    final LocalDate birthDate,
                                                    final LocalDate checkDate,
                                                    final String code)
            throws CoreException {
        List<RbDispInfo> dispInfoList = em.createNamedQuery("rbDispInfo.findBySexAndMedicalKindCode", RbDispInfo.class)
                .setParameter("code", code)
                .setParameter("sex", sex).getResultList();
        final int years = Years.yearsBetween(birthDate, checkDate).getYears();
        final int months = Months.monthsBetween(birthDate, checkDate).getMonths();
        final int weeks = Weeks.weeksBetween(birthDate, checkDate).getWeeks();
        final int days = Days.daysBetween(birthDate, checkDate).getDays();
        logger.debug("Patient age is: {} Years, {} Month, {} Weeks, {} Days", years, months, weeks, days);
        for (RbDispInfo current : dispInfoList) {
            if (current.getAge().contains("-")) {
                String firstPart = current.getAge().substring(0, current.getAge().indexOf('-'));
                String secondPart = current.getAge().substring(current.getAge().indexOf('-') + 1);
                logger.debug("ID[{}] FP[{}] SP[{}]", current.getId(), firstPart, secondPart);
                boolean firstPartIsGood = firstPart.isEmpty();
                boolean secondPartIsGood = secondPart.isEmpty();
                //Check the firstPart
                if (!firstPartIsGood) {
                    int timeItems = 0;
                    try {
                        timeItems = Integer.parseInt(firstPart.substring(0, firstPart.length() - 1));
                    } catch (NumberFormatException e) {
                        //skip this row
                        continue;
                    }
                    int comparablePart = -1;
                    if (firstPart.contains("Д") || firstPart.contains("д")) {
                        comparablePart = days;
                    } else if (firstPart.contains("Н") || firstPart.contains("н")) {
                        comparablePart = weeks;
                    } else if (firstPart.contains("М") || firstPart.contains("м")) {
                        comparablePart = months;
                    } else {
                        //ГОД
                        comparablePart = years;
                    }
                    if (comparablePart >= timeItems) {
                        firstPartIsGood = true;
                        logger.debug("First part is true");
                    } else {
                        continue;
                    }
                }
                //Check the secondPart
                if (!secondPartIsGood) {
                    int timeItems = 0;
                    try {
                        timeItems = Integer.parseInt(secondPart.substring(0, secondPart.length() - 1));
                    } catch (NumberFormatException e) {
                        //skip this row
                        continue;
                    }
                    int comparablePart = 9999;
                    if (secondPart.contains("Д") || secondPart.contains("д")) {
                        comparablePart = days;
                    } else if (secondPart.contains("Н") || secondPart.contains("н")) {
                        comparablePart = weeks;
                    } else if (secondPart.contains("М") || secondPart.contains("м")) {
                        comparablePart = months;
                    } else {
                        //ГОД
                        comparablePart = years;
                    }
                    if (comparablePart < timeItems) {
                        secondPartIsGood = true;
                        logger.debug("Second part is true");
                    } else {
                        continue;
                    }
                }
                if (firstPartIsGood && secondPartIsGood) {
                    return current;
                }

            }
        }
        return null;
    }
}
