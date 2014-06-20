package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Organisation;
import ru.korus.tmis.core.entity.model.QuotingBySpeciality;
import ru.korus.tmis.core.entity.model.Speciality;
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

    /**
     * Получение квотирования по специальности для заданного инфис-кода организации
     *
     * @param organisationUid инфис-код организации
     * @return Перечень квот \ Пустой список
     */
    public List<QuotingBySpeciality> getQuotingByOrganisation(final String organisationUid);

    /**
     * Получения квотирования по специальности для заданной специальности и инфис-кода организации
     *
     * @param speciality
     * @param organisationInfisCode инфис-код организации
     * @return Перечень квот \ Пустой список
     */
    public List<QuotingBySpeciality> getQuotingBySpecialityAndOrganisation(
            final Speciality speciality,
            final String organisationInfisCode
    );

    /**
     * Получения квотирования по специальности для заданной специальности и организации
     *
     * @param speciality
     * @param organisation организация
     * @return Перечень квот \ Пустой список
     */
    public List<QuotingBySpeciality> getQuotingBySpecialityAndOrganisation(
            final Speciality speciality,
            final Organisation organisation
    );


    /**
     * Получения квотирования по специальности для заданной специальности
     *
     * @param speciality специальность для поиска
     * @return Перечень квот \ Пустой список
     */
    public List<QuotingBySpeciality> getQuotingBySpeciality(final Speciality speciality);

    /**
     * Увеличение оставшегося количества талончиков для квоты по специальности  на один
     *
     * @param toIncrement квота для которой необходимо увеличить количество оставшихся талонов
     * @return статус операции
     */
    public boolean incrementRemainingCoupons(QuotingBySpeciality toIncrement);

    /**
     * Уменьшение оставшегося количества талончиков для квоты по специальности  на один
     *
     * @param toDecrement квота для которой необходимо уменьшить количество оставшихся талонов
     * @return статус операции
     */
    public boolean decrementRemainingCoupons(QuotingBySpeciality toDecrement);

    /**
     * Получение списка спецаильностей. которые не квотируются при записи из других ЛПУ
     * @return  список специальностей, которые не квотируются \ пустой список
     */
    public List<Speciality> getUnquotedSpecialities();
}
