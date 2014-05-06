package ru.korus.tmis.tariff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.tariff.thriftgen.Tariff;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 25.11.13, 14:26 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class DispanserizationTariff extends TariffEntry {

    private static final Logger logger = LoggerFactory.getLogger(DispanserizationTariff.class);

    public DispanserizationTariff(final Tariff currentTariff) {
        super(currentTariff);
    }

    @Override
    public boolean fillSexAndAges() {
        //Нужно сохранить набор полученных пар: (<sex>,<age>)
        //Map<Short[sex], String[age]> sexAndAge
        sexAndAgeList.addAll(getSexAndAgeForDispanserization(ctar, medicalKind));
        return (sexAndAgeList.size() > 0);
    }

    private List<Helper.SexAndAge> getSexAndAgeForDispanserization(
            final String ctar,
            final RbMedicalKind medicalKind) {
        List<Helper.SexAndAge> result = new ArrayList<Helper.SexAndAge>();
        switch (ctar.charAt(6)) {
            case '2': {
                //1)	Если <7-ой символ C_TAR> = 2, выполняем запрос :
                //SELECT sex, age FROM rbDispInfo
                //WHERE rbDispInfo.code = <с 9-ого по 11-ый символы C_TAR>.
                //Результатов может быть несколько.
                // Если нет ни одного - вывести ошибку,
                // что по коду тарифа не определена половозрастная группа
                // (с указанием C_TAR и номера записи, как обычно).Дальнейшее не выполнять.
                final List<RbDispInfo> rbDispInfos = TariffServer.getRbDispInfoBean()
                        .getByCodeAndMedicalKind(ctar.substring(8, 11), medicalKind);
                for (RbDispInfo currentDispInfo : rbDispInfos) {
                    result.add(new Helper.SexAndAge(currentDispInfo.getSex(), currentDispInfo.getAge()));
                }
                break;
            }
            case '3': {
                // //Если <7-ой символ C_TAR> = 3, будет один только набор:
                //(0, IF(<предпоследний символ C_TAR> = 1, ‘18Г-’, ‘-18Г’))
                switch (ctar.charAt(16)) {
                    case '1': {
                        //Т.е. либо (0, ‘18Г-’) (если предпоследний символ = 1),
                        result.add(new Helper.SexAndAge((short) 0, "18\u0413-"));
                        break;
                    }
                    case '2': {
                        // либо (0, ‘-18Г’) (если предпоследний символ = 2)
                        result.add(new Helper.SexAndAge((short) 0, "-18\u0413"));
                        break;
                    }
                    default: {
                        logger.error("Step 4.2: not valid C_TAR[17]={}", ctar.charAt(16));
                    }
                } //End of switch C_TAR[17]
                break;
            }
            default: {
                logger.error("Step 4.2: not valid C_TAR[7]={}", ctar.charAt(6));
            }
        }//End of switch C_TAR[7]
        return result;
    }


    @Override
    public boolean fillNewTariffs(final Contract contract) {
        final List<RbService> serviceList = TariffServer.getSpecificBean().getServicesForDispanserization(ctar);
        if (serviceList.isEmpty()) {
            logger.error("No one rbService found. Aborting processing.");
            result.setError(TariffError.SERVICES_EMPTY.getError());
            return false;
        } else {
            //To Log
            if (logger.isDebugEnabled()) {
                logger.debug("Selected services:");
                for (RbService currentService : serviceList) {
                    logger.debug("RbService[{}] infis = {} name = {}",
                            currentService.getId(), currentService.getInfis(), currentService.getName());
                }
            }
        }
        List<MedicalKindUnit> mkuList = TariffServer.getMedicalKindUnitBean()
                .getMedicalKindUnitListByMedicalKindAndMedicalAidUnit(medicalKind, aidUnit);
        if (mkuList.isEmpty()) {
            logger.error("No one MedicalKindUnit found. Aborting processing.");
            result.setError(TariffError.MKU_EMPTY.getError());
            return false;
        } else {
            //To Log
            if (logger.isDebugEnabled()) {
                for (MedicalKindUnit currentMku : mkuList) {
                    logger.debug("MKU[{}] medicalKind={} eventType={} aidUnit={} paytype={} tariffType={}",
                            currentMku.getId(),
                            currentMku.getMedicalKind().getCode(),
                            currentMku.getEventType().getId(),
                            currentMku.getMedicalAidUnit().getCode(),
                            currentMku.getPayType().getCode(),
                            currentMku.getTariffType().getCode());
                }
            }
        }

        for (RbService currentService : serviceList) {
            for (Helper.SexAndAge currentSexAndAge : sexAndAgeList) {
                for (MedicalKindUnit currentMku : mkuList) {
                    tariffList.add(
                            fillNewTariff(contract,
                                    currentMku.getEventType(),
                                    currentService,
                                    Short.valueOf(currentMku.getTariffType().getCode()),
                                    begDate,
                                    endDate,
                                    currentSexAndAge.getSex(),
                                    currentSexAndAge.getAge(),
                                    aidUnit,
                                    1,
                                    0,
                                    summ,
                                    "",
                                    serviceFinance
                            )
                    );
                }
            }
        }
        return !tariffList.isEmpty();
    }

}
