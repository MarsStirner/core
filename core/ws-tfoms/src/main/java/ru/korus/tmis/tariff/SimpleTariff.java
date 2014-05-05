package ru.korus.tmis.tariff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.tariff.thriftgen.*;
import ru.korus.tmis.tariff.thriftgen.Error;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 25.11.13, 14:28 <br>
 * Company: Korus Consulting IT <br>
 * Description: Обычный тариф <br>
 */
public class SimpleTariff extends TariffEntry {

    private static final Logger logger = LoggerFactory.getLogger(SimpleTariff.class);

    public SimpleTariff(Tariff currentTariff) {
        super(currentTariff);
    }

    @Override
    public boolean fillNewTariffs(final Contract contract) {
        final List<RbService> services = TariffServer.getSpecificBean().getServicesForSimpleTariff(ctar, medicalKind);
        if (services.isEmpty()) {
            logger.warn("No one rbService founded.");
            result.setError(TariffError.NO_SERVICE.getError());
            return false;
        } else if (logger.isDebugEnabled()) {
            //trace to log
            logger.debug("## rbServices Data: ##");
            for (RbService currentService : services) {
                logger.debug(currentService.toString());
            }
        }

        for (RbService currentService : services) {
            final List<RbServiceUET> rbServiceUetList = TariffServer.getRbServiceUetBean().getByService(currentService);
            final List<Double> uetList = new ArrayList<Double>(rbServiceUetList.size());
            if (rbServiceUetList.isEmpty()) {
                uetList.add(currentService.getUet());
                logger.debug("For rbService[{}] not founded rbServiceUET", currentService.getId());
            } else {
                final String age = sexAndAgeList.get(0).getAge();
                logger.debug("::: RbServiceUET :::");
                for (RbServiceUET currentServiceUET : rbServiceUetList) {
                    if (age.equalsIgnoreCase(currentServiceUET.getAge())) {
                        uetList.add(currentServiceUET.getUet());
                        logger.debug("UET={}", currentServiceUET.getUet());
                    }
                }
                if (uetList.isEmpty()) {
                    uetList.add(currentService.getUet());
                }
            }
            for (Double currentUet : uetList) {
                if (tariffCode != null) {
                    logger.debug("TariffCode IS NOT NULL");
                    final Helper.SexAndAge sexAndAge = sexAndAgeList.get(0);
                    tariffList.add(
                            fillNewTariff(contract,
                                    null,
                                    currentService,
                                    tariffCode,
                                    begDate,
                                    endDate,
                                    sexAndAge.getSex(),
                                    sexAndAge.getAge(),
                                    aidUnit,
                                    1,
                                    currentUet,
                                    currentUet == 0 ? summ : summ * currentUet,
                                    "",
                                    serviceFinance
                            )
                    );
                } else {
                    logger.debug("TariffCode IS NULL");
                    final List<EventType> eventTypeList = TariffServer.getContractSpecificationBean()
                            .getEventTypeListByContract(contract);
                    if (eventTypeList.isEmpty()) {
                        logger.warn("#{} has empty EventType->ContractSpecification.", currentTariffNumber);
                        result.setError(TariffError.CONTRACT_SPECIFICATION_NOT_FOUND.getError());
                        return false;
                    } else if(logger.isDebugEnabled()) {
                        logger.debug("## EventType List ##");
                        for(EventType currentEventType : eventTypeList){
                            logger.debug(currentEventType.toString());
                        }
                    }
                    for (EventType currentEventType : eventTypeList) {
                        RbTariffType tariffType = TariffServer.getMedicalKindUnitBean()
                                .getTariffTypeByEventTypeAndMedicalKindAndMedicalAidUnit(currentEventType, medicalKind, aidUnit);
                        if (tariffType != null) {
                            final Helper.SexAndAge sexAndAge = sexAndAgeList.get(0);
                            tariffList.add(
                                    fillNewTariff(contract,
                                            currentEventType,
                                            currentService,
                                            Short.parseShort(tariffType.getCode()),
                                            begDate,
                                            endDate,
                                            sexAndAge.getSex(),
                                            sexAndAge.getAge(),
                                            aidUnit,
                                            1,
                                            currentUet,
                                            currentUet == 0 ? summ : summ * currentUet,
                                            "",
                                            serviceFinance
                                    )
                            );
                        } else {
                            logger.warn("#{} has empty EventType[{}]->rbTariffType.", currentTariffNumber, currentEventType.getId());
                        }
                    }  //END OF EventType FOR
                }  // END OF TariffCode IS NULL
            }  //END OF UETList
        }    //END OF ServiceList
        if(tariffList.isEmpty()){
            result.setError(new Error().setMessage("No one tariff formed"));
        }
        return !tariffList.isEmpty();
    }

}
