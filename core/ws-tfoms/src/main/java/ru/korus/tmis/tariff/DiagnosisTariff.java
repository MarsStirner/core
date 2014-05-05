package ru.korus.tmis.tariff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.tariff.thriftgen.Tariff;

import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 25.11.13, 14:28 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class DiagnosisTariff extends TariffEntry {

    private static final Logger logger = LoggerFactory.getLogger(DiagnosisTariff.class);

    public DiagnosisTariff(final Tariff currentTariff) {
        super(currentTariff);
    }

    @Override
    public boolean fillNewTariffs(final Contract contract) {
        if (tariffCode != null) {
            //7.1 Если найденный на шаге 4 параметр tarifCode не равен null, , то добавить тариф следующим образом
            // (передав в него LEFT-TRIM(<с 10-ого по 16-ый символы C_TAR>,’0’)):
            final Helper.SexAndAge sexAndAge = sexAndAgeList.get(0);
            tariffList.add(
                    fillNewTariff(contract,
                            null,
                            null,
                            tariffCode,
                            begDate,
                            endDate,
                            sexAndAge.getSex(),
                            sexAndAge.getAge(),
                            aidUnit,
                            1,
                            0,
                            summ,
                            ctar.substring(9, 16).replaceFirst("^0*", ""),
                            serviceFinance
                    )
            );
            return true;
        } else {
            //7.2 Если же найденный на шаге 4 параметр tarifCode равен null,
            // то тариф нужно добавить запросом из 7.1
            // отдельно для каждого события,
            // объявленного в спецификации договора:
            final List<EventType> eventTypeList = TariffServer.getContractSpecificationBean()
                    .getEventTypeListByContract(contract);
            if (eventTypeList.isEmpty()) {
                logger.warn("#{} has empty EventType->ContractSpecification.", currentTariffNumber);
                result.setError(TariffError.CONTRACT_SPECIFICATION_NOT_FOUND.getError());
                return false;
            }
            for (EventType currentEventType : eventTypeList) {
                RbTariffType tariffType = TariffServer.getMedicalKindUnitBean()
                        .getTariffTypeByEventTypeAndMedicalKindAndMedicalAidUnit(currentEventType, medicalKind, aidUnit);
                if (tariffType != null) {
                    final Helper.SexAndAge sexAndAge = sexAndAgeList.get(0);
                    tariffList.add(
                            fillNewTariff(contract,
                                    currentEventType,
                                    null,
                                    Short.parseShort(tariffType.getCode()),
                                    begDate,
                                    endDate,
                                    sexAndAge.getSex(),
                                    sexAndAge.getAge(),
                                    aidUnit,
                                    1,
                                    0,
                                    summ,
                                    ctar.substring(9, 16).replaceFirst("^0*", ""),
                                    serviceFinance
                            )
                    );
                } else {
                    logger.warn("#{} has empty EventType[{}]->rbTariffType.", currentEventType.getId(), currentTariffNumber);
                }
            }
            return !tariffList.isEmpty();
        }
    }


}
