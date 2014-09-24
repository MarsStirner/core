package ru.korus.tmis.tfoms;


import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.Account;
import ru.korus.tmis.core.entity.model.AccountItem;
import ru.korus.tmis.core.entity.model.Contract;
import ru.korus.tmis.core.entity.model.OrgStructure;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.tfoms.thriftgen.*;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Author: Upatov Egor <br>
 * Date: 06.09.13, 12:41 <br>
 * Company: Korus Consulting IT <br>
 * Description: Класс со статицными методами создания структур THRIFT из сущностей БД ЛПУ <br>
 */
public class ThriftStructBuilder {

    /**
     * Формирование заполненной структуры счета из сущности счета в БД ЛПУ
     *
     * @param account сущность счета из БД ЛПУ
     * @return структура счета
     */
    public static ru.korus.tmis.tfoms.thriftgen.Account createAccount(
            final ru.korus.tmis.core.entity.model.Account account) {
        final ru.korus.tmis.tfoms.thriftgen.Account result = new ru.korus.tmis.tfoms.thriftgen.Account()
                .setId(account.getId())
                .setNumber(account.getNumber())
                .setDate(DateConvertions.convertDateToUTCMilliseconds(account.getDate()))
                .setBegDate(DateConvertions.convertDateToUTCMilliseconds(account.getBegDate()))
                .setEndDate(DateConvertions.convertDateToUTCMilliseconds(account.getEndDate()))
                .setAmount(account.getAmount().intValue())
                .setUet(account.getUet())
                .setSum(account.getSum())
                .setPayedAmount(account.getPayedAmount().intValue())
                .setPayedSum(account.getPayedSum())
                .setRefusedAmount(account.getRefusedAmount().intValue())
                .setRefusedSum(account.getRefusedSum())
                .setContractId(account.getContract().getId());
        if (account.getExposeDate() != null) {
            result.setExposeDate(DateConvertions.convertDateToUTCMilliseconds(account.getExposeDate()));
        }
        return result;
    }

    /**
     * Формирование заполненной структуры позиции счета из сущности позиции счета в БД ЛПУ
     *
     * @param accountItem сущность позиции счета из БД ЛПУ
     * @return структура позиции счета
     */
    public static ru.korus.tmis.tfoms.thriftgen.AccountItem createAccountItem(
            final ru.korus.tmis.core.entity.model.AccountItem accountItem) {
        final ru.korus.tmis.tfoms.thriftgen.AccountItem result = new ru.korus.tmis.tfoms.thriftgen.AccountItem()
                .setId(accountItem.getId())
                .setServiceDate(DateConvertions.convertDateToUTCMilliseconds(accountItem.getServiceDate()));
        final Patient client = accountItem.getClient();
        if (client != null) {
            result.setLastName(client.getLastName())
                    .setFirstName(client.getFirstName())
                    .setPatrName(client.getPatrName() != null || !client.getPatrName().isEmpty() ?
                            client.getPatrName() : "НЕТ")
                    .setBirthDate(DateConvertions.convertDateToUTCMilliseconds(client.getBirthDate()))
                    .setSex(client.getSex());
        } else {
            result.setLastName("")
                    .setFirstName("")
                    .setPatrName("")
                    .setBirthDate(0)
                    .setSex((short) 0);
        }
        result.setPrice(accountItem.getPrice());
        result.setAmount(accountItem.getAmount());
        if (accountItem.getUnit() != null) {
            result.setUnitName(accountItem.getUnit().getName());
        } else {
            result.setUnitName("Не указано");
        }
        if (accountItem.getDate() != null) {
            result.setDate(DateConvertions.convertDateToUTCMilliseconds(accountItem.getDate()));
        }
        result.setFileName(accountItem.getNumber());
        if (accountItem.getRefuseType() != null) {
            result.setRefuseTypeCode(Short.parseShort(accountItem.getRefuseType().getCode()));
            result.setRefuseTypeName(accountItem.getRefuseType().getName());
        }
        if (accountItem.getNote() != null || !accountItem.getNote().isEmpty()) {
            result.setNote(accountItem.getNote());
        }
        result.setDoNotUploadAnymore(accountItem.isNotUploadAnymore());
        return result;
    }

    /**
     * Формирование заполненной структуры подразделения из сущности подразделения в БД ЛПУ
     *
     * @param orgStructure сущность подразделения из БД ЛПУ
     * @return структура подразделения
     */
    public static ru.korus.tmis.tfoms.thriftgen.OrgStructure createOrgStructure(
            final ru.korus.tmis.core.entity.model.OrgStructure orgStructure) {
        final ru.korus.tmis.tfoms.thriftgen.OrgStructure result = new ru.korus.tmis.tfoms.thriftgen.OrgStructure()
                .setId(orgStructure.getId())
                .setCode(orgStructure.getCode())
                .setName(orgStructure.getName());
        if (orgStructure.getParentId() != null) {
            result.setParentId(orgStructure.getParentId());
        }
        result.setType(orgStructure.getType());
        return result;
    }

    /**
     * Формирование заполненной структуры контракта из сущности контракта в БД ЛПУ
     *
     * @param contract сущность контракта из БД ЛПУ
     * @return структура контракта
     */
    public static ru.korus.tmis.tfoms.thriftgen.Contract createContract(
            final ru.korus.tmis.core.entity.model.Contract contract) {
        return new ru.korus.tmis.tfoms.thriftgen.Contract()
                .setId(contract.getId())
                .setNumber(contract.getNumber())
                .setBegDate(DateConvertions.convertDateToUTCMilliseconds(contract.getBegDate()))
                .setEndDate(DateConvertions.convertDateToUTCMilliseconds(contract.getEndDate()))
                .setResolution(contract.getResolution());
    }

    public static Schet createSchet(
            final Organisation organisation,
            final Date formDate,
            final String number,
            final String plat,
            final ru.korus.tmis.core.entity.model.Account account) {
        final Schet result = new Schet();
        result.setCODE((short) 1);
        result.setCODE_MO(organisation.getInfisCode());
        result.setYEAR(Short.valueOf(new SimpleDateFormat("yyyy").format(formDate)));
        result.setMONTH(Short.valueOf(new SimpleDateFormat("MM").format(formDate)));
        result.setNSCHET(number);
        result.setDSCHET(DateConvertions.convertDateToUTCMilliseconds(formDate));
        result.setPLAT(plat);
        result.setSUMMAV(account.getSum());
        return result;
    }

    private ThriftStructBuilder() {
    }
}
