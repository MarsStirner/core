package ru.korus.tmis.core.database;

import org.joda.time.DateMidnight;
import org.joda.time.LocalDate;
import ru.korus.tmis.core.entity.model.RbDispInfo;
import ru.korus.tmis.core.entity.model.RbMedicalKind;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 05.08.13, 13:25 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbDispInfoBeanLocal {
    public RbDispInfo getById(int id);

    public List<RbDispInfo> getByCode(String code);

    /**
     * Получение половозрастных групп диспансеризации по коду и категории мед помощи
     * @param code код половозрастных групп
     * @param medicalKind категория помощи
     * @return список найденных групп\ пустой список
     */
    public List<RbDispInfo> getByCodeAndMedicalKind(final String code, final RbMedicalKind medicalKind);

    public RbDispInfo getBySexAgeAndMedicalKindCode(final short sex,
                                                    final LocalDate birthDate,
                                                    final LocalDate checkDate,
                                                    final String code)
            throws CoreException;
}
