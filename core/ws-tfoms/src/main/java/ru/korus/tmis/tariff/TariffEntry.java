package ru.korus.tmis.tariff;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.tariff.thriftgen.Result;
import ru.korus.tmis.tariff.thriftgen.Tariff;
import ru.korus.tmis.tfoms.DateConvertions;

import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 08.08.13, 19:16 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public abstract class TariffEntry {
    private static final Logger logger = LoggerFactory.getLogger(TariffEntry.class);

    // Номер тарифа
    protected final int currentTariffNumber;
    // Форматированная строка с данными
    protected final String ctar;
    // Дата начала действия тарифа
    protected final Date begDate;
    // Дата окончания действия тарифа
    protected final Date endDate;
    // Сумма тарифа
    protected final Double summ;

    //Сформированный ответ по этому номер утарифа
    protected final Result result;
    //Список сгенериррованных тарифов
    protected final List<ContractTariff> tariffList;


    //Категория помощи
    protected RbMedicalKind medicalKind;
    //Источник финансирования
    protected RbServiceFinance serviceFinance;
    //Единица учета
    protected RbMedicalAidUnit aidUnit;
    // Код тарифа
    protected Short tariffCode;
    // Список половозрастных групп, на которые этот тариф будет действовать.
    protected final List<Helper.SexAndAge> sexAndAgeList;


    public TariffEntry(final Tariff currentTariff) {
        this.ctar = currentTariff.getC_tar();
        this.currentTariffNumber = currentTariff.getNumber();
        this.begDate = DateConvertions.convertUTCMillisecondsToLocalDate(currentTariff.getDate_b());
        this.endDate = DateConvertions.convertUTCMillisecondsToLocalDate(currentTariff.getDate_e());
        this.summ = currentTariff.getSumm_tar();
        logger.debug("Start processing #{}[{}] Actual from {} to {} Price={}",
                new Object[] { currentTariffNumber, ctar, begDate, endDate, summ });
        this.result = new Result(currentTariffNumber, ctar);
        this.tariffList = new ArrayList<ContractTariff>(4);
        this.sexAndAgeList = new ArrayList<Helper.SexAndAge>(1);
    }


    public boolean fillVariables() {
        if (!checkCtarLenth()) {
            return false;
        }
        final String kindCode = String.valueOf(ctar.charAt(7));
        medicalKind = TariffServer.getRbMedicalKindBean().getMedicalKindByCode(kindCode);
        if (medicalKind == null) {
            logger.warn("#{} has incorrect rbMedicalKind.Code=[{}]", currentTariffNumber, kindCode);
            result.setError(TariffError.MEDICAL_KIND_CODE_INCORRECT.getError());
            //skip current processing
            return false;
        }
        String serviceFinanceCode = String.valueOf(ctar.charAt(17));
        serviceFinance = TariffServer.getRbServiceFinanceBean().getServiceFinanceByCode(serviceFinanceCode);
        if (serviceFinance == null) {
            logger.warn("#{} has incorrect rbServiceFinance.Code=[{}]", currentTariffNumber, serviceFinanceCode);
            result.setError(TariffError.SERVICE_FINANCE_CODE_INCORRECT.getError());
            //skip current processing
            return false;
        }
        String aidUnitCode = String.valueOf(ctar.charAt(6));
        aidUnit = TariffServer.getRbMedicalAidUnitBean().getByCode(aidUnitCode);
        if (aidUnit == null) {
            logger.warn("#{} has incorrect rbMedicalAidUnit.Code=[{}]", currentTariffNumber, aidUnitCode);
            result.setError(TariffError.MEDICAL_AID_UNIT_CODE_INCORRECT.getError());
            //skip current processing
            return false;
        }
        tariffCode = !"HCVZ".contains(kindCode.toUpperCase()) ?
                getTariffCode(medicalKind, aidUnit) : null;
        fillSexAndAges();
        if (sexAndAgeList.isEmpty()) {
            logger.warn("No one sex and age couple. Aborting processing.");
            result.setError(TariffError.SEX_AND_AGE_LIST_EMPTY.getError());
            return false;
        }
        return true;
    }

    /**
     * Заполнение половозрастных групп
     *
     * @return true - группы заполнены, false - не найдено ни одной группы
     */
    protected boolean fillSexAndAges() {
        switch (ctar.charAt(16)) {
            case '1': {
                sexAndAgeList.add(new Helper.SexAndAge((short) 0, "18\u0413-"));
                return true;
            }
            case '2': {
                sexAndAgeList.add(new Helper.SexAndAge((short) 0, "-18\u0413"));
                return true;
            }
            default: {
                logger.error("Incorrect age : {}", ctar.charAt(16));
                return false;
            }
        }
    }

    /**
     * Небольшая валидация входых данных (длина ctar = 18)
     *
     * @return true - валидно, false - некорректные данные
     */
    private boolean checkCtarLenth() {
        //Check C_TAR length
        if (ctar.length() != Helper.C_TAR_SIZE) {
            logger.warn("#{} has incorrect C_TAR[{}] length", currentTariffNumber, ctar);
            result.setError(TariffError.C_TAR_LENGTH.getError());
            return false;
        }
        return true;
    }


    public Result getResult() {
        return result;
    }

    protected ContractTariff fillNewTariff(final Contract contract,
                                           final EventType eventType,
                                           final RbService service,
                                           final Short tariffType,
                                           final Date beginDate,
                                           final Date endDate,
                                           final short sex,
                                           final String age,
                                           final RbMedicalAidUnit unit,
                                           final double amount,
                                           final double uet,
                                           final double price,
                                           final String mkb,
                                           final RbServiceFinance finance) {
        ContractTariff newTariff = new ContractTariff();
        newTariff.setDeleted((short) 0);
        newTariff.setMasterId(contract.getId());
        newTariff.setEventTypeId(eventType != null ? eventType.getId() : null);
        newTariff.setTariffType(tariffType);
        newTariff.setService(service);
        newTariff.setBegDate(beginDate);
        newTariff.setEndDate(endDate);
        newTariff.setSex(sex);
        newTariff.setAge(age);
        newTariff.setUnit(unit);
        newTariff.setAmount(amount);
        newTariff.setUet(uet);
        newTariff.setPrice(price);
        newTariff.setMkb(mkb);
        newTariff.setServiceFinance(finance);
        newTariff.setCreateDatetime(new Date());
        newTariff.setCreatePerson(null);
        newTariff.setModifyDatetime(new Date());
        newTariff.setModifyPerson(null);
        return newTariff;
    }

    protected Set<String> getInfisCodesForService(final String ctarServiceInfisPart) {
        Set<String> infisParts = new HashSet<String>(2);
        if (ctarServiceInfisPart.endsWith("00")) {
            infisParts.add(ctarServiceInfisPart.substring(0, 3).concat("01"));
            infisParts.add(ctarServiceInfisPart.substring(0, 3).concat("02"));
        } else {
            infisParts.add(ctarServiceInfisPart);
        }
        return infisParts;
    }


    private Short getTariffCode(final RbMedicalKind medicalKind, final RbMedicalAidUnit aidUnit) {
        final List<RbTariffType> tariffTypeList = TariffServer.getMedicalKindUnitBean()
                .getTariffTypeListByMedicalKindAndMedicalAidUnit(medicalKind, aidUnit);
        if (!tariffTypeList.isEmpty()) {
            String check = tariffTypeList.get(0).getCode();
            for (RbTariffType tt : tariffTypeList) {
                if (!check.equals(tt.getCode())) {
                    logger.warn("Not all selected MedicalKindUnits has same rbTariffType");
                    return null;
                }
            }
            try{
                return Short.valueOf(check);
            }  catch (NumberFormatException e){
                logger.error("Can't parse RbTariffTypeCode");
                return null;
            }
        }
        logger.warn("tariffCode can't be evaluate cause no such MedicalKindUnit exists");
        return null;
    }

    @Override
    public String toString() {
        return result.toString();
    }

    public void printSummary() {
        logger.debug("Founded values for #{}", currentTariffNumber);
        logger.debug("rbMedicalKind[{}] - {} - {}", new Object[] { medicalKind.getId(), medicalKind.getCode(), medicalKind.getName() });
        logger.debug("SEX and AGE restrictions:");
        for (Helper.SexAndAge currentSexAndAge : sexAndAgeList) {
            logger.debug(currentSexAndAge.toString());
        }
        logger.debug("rbServiceFinance[{}] - {} - {}", new Object[] { serviceFinance.getId(), serviceFinance.getCode(), serviceFinance.getName() });
        logger.debug("rbMedicalAidUnit[{}] - {} - {}", new Object[] { aidUnit.getId(), aidUnit.getCode(), aidUnit.getName() });
    }

    /**
     * Сгенерировать список тарифов
     *
     * @param contract контракт, по которому будут действовать все сгененрированные тарифы
     * @return true - тарифы сгененрированны, false - не сформировано ни одного тарифа
     */
    public abstract boolean fillNewTariffs(final Contract contract);

    public List<ContractTariff> getTariffList() {
        return tariffList;
    }

    public void printFormedTariff() {
        logger.debug("Formed tariffs: {} items", tariffList.size());
        for (ContractTariff currentTariff : tariffList) {
            logger.debug(currentTariff.getInfo());
        }
    }
}
