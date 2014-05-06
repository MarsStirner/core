package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.ContractTariff;
import ru.korus.tmis.core.entity.model.RbMedicalKind;
import ru.korus.tmis.core.entity.model.RbService;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 15:10 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface TariffSpecificBeanLocal {

    public List<RbService> getRbServiceByMedicalKindCodeAndPartsOfInfisCodes(
            Set<String> infisCodesParts, String medicalKindCode);

    /**
     * Получение списка услуг для диспансеризации по части кода тарифа (для загрузки тарифов из ТФОМС)
     * @param ctar код
     * @return  список услуг \ пустой список
     */
    public List<RbService> getServicesForDispanserization(final String ctar);

    List<RbService> getRbServiceByMedicalKindCodeAndSpecificServiceInfis(int  medicalKindId, String ctar);

    List<ContractTariff> getAlikeTariff(ContractTariff tfomsTariff);

    /**
     * Получение списка услуг для обычных тарифов по части кода тарифа и категории мед помощи (для загрузки тарифов из ТФОМС)
     * @param ctar  код
     * @param medicalKind категория мед помощи
     * @return список услуг \ пустой список
     */
    List<RbService> getServicesForSimpleTariff(final String ctar, final RbMedicalKind medicalKind);
}
