package ru.korus.tmis.tfoms.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.tfoms.Informationable;
import ru.korus.tmis.tfoms.DateConvertions;
import ru.korus.tmis.tfoms.TFOMSServer;
import ru.korus.tmis.tfoms.thriftgen.Sluch;
import ru.korus.tmis.tfoms.thriftgen.SluchOptionalFields;
import ru.korus.tmis.tfoms.thriftgen.Usl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Author: Upatov Egor <br>
 * Date: 19.05.2014, 17:44 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class AdditionalXMLSluch implements Informationable {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(XMLSluch.class);
    //Формат даты, которая будет подставляться в качестве параметра
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("''yyyy-MM-dd HH:mm:ss''");
    private static int identifier = 0;


    private final int id;
    private final int action;
    private final XMLSluch masterSluch;
    private double SUMV = 0.0;

    private final List<AdditionalUploadRow> itemList;


    public AdditionalXMLSluch(final AdditionalUploadRow item, final XMLSluch masterSluch) {
        this.itemList = new ArrayList<AdditionalUploadRow>();
        itemList.add(item);
        this.id = ++identifier;
        this.masterSluch = masterSluch;
        this.action = item.getAction();
        if (item.getTARIF() != null && item.getED_COL() != null) {
            SUMV += item.getTARIF() * item.getED_COL();
        }
    }


    public int getId() {
        return id;
    }

    public int getAction() {
        return action;
    }


    public double getSUMV() {
        return SUMV;
    }

    public int getPatient() {
        return masterSluch.getPatient();
    }

    @Override
    public String getInfo() {
        return toString();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AdditionalXMLSluch[");
        sb.append(id).append("-").append(action).append("]");
        for (AdditionalUploadRow item : itemList) {
            sb.append("\n").append(item);
        }
        return sb.toString();
    }

    public void addItem(final AdditionalUploadRow item) {
        itemList.add(item);
        SUMV += item.getTARIF() * item.getED_COL();
    }


    public List<Sluch> formSluchStructure(
            final Account account,
            final Set<SluchOptionalFields> sluchOptionalFields,
            final String infisCode) {
        final List<Sluch> resultList = new ArrayList<Sluch>(1);
        final AdditionalUploadRow item = itemList.iterator().next();
        final Sluch result = new Sluch();
        result.setUSL_OK(masterSluch.getResult().getUSL_OK());
        result.setVIDPOM(masterSluch.getResult().getVIDPOM());
        result.setFOR_POM(masterSluch.getResult().getFOR_POM());
        result.setLPU(infisCode);
        result.setPROFIL(getShortValue(item.getPROFIL()));

        result.setNHISTORY(masterSluch.getResult().getNHISTORY());

        result.setDATE_1(getLongValue(item.getDATE_1()));
        result.setDATE_2(getLongValue(item.getDATE_2()));

        if (item.getDS1() != null) {
            result.setDS1(item.getDS1());
        }

        result.setRSLT(getShortValue(item.getRSLT()));
        result.setISHOD(getShortValue(item.getISHOD()));

        result.setPRVS(getShortValue(item.getPRVS()));
        result.setIDDOKT(item.getIDDOKT() != null ? item.getIDDOKT() : "");

        result.setIDSP(masterSluch.getResult().getIDSP());
        result.setED_COL(item.getED_COL());
        //TODO уточнить статус полей ВМП
        result.setVID_HMP(masterSluch.getResult().getVID_HMP());
        result.setMETOD_HMP(masterSluch.getResult().getMETOD_HMP());
        //Заполнение дополнительных полей
        for (SluchOptionalFields optionalField : sluchOptionalFields) {
            switch (optionalField) {
                case NPR_MO: {
                    //TODO NPR_MO
//                    if (item.getNPR_MO() != null) {
//                        result.setNPR_MO(item.getNPR_MO());
//                    }
                    break;
                }
                case EXTR: {
                    result.setEXTR(getShortValue(item.getEXTR()));
                    break;
                }
                case LPU_1: {
                    result.setLPU_1(masterSluch.getResult().getLPU_1());
                    break;
                }
                case PODR: {
                    if (item.getPODR() != null && !item.getPODR().isEmpty()) {
                        result.setPODR(item.getPODR());
                    }
                    break;
                }
                case DET: {
                    if (item.getDET() != null) {
                        result.setDET(item.getDET() > 0);
                    }
                    break;
                }
                case DS0: {
                    if (item.getDS0() != null) {
                        result.setDS0(item.getDS0());
                    }
                    break;
                }
                case DS2: {
                    if (item.getDS2() != null) {
                        result.setDS2(item.getDS2());
                    }
                    break;
                }
                case CODE_MES1: {
                    //TODO
//                    if (item.getCODE_MES1() != null && !item.getCODE_MES1().isEmpty()) {
//                        result.setCODE_MES1(item.getCODE_MES1());
//                    } else {
//                        logger.warn("CODE_MES1: not founded");
//                    }
                    break;
                }
                case CODE_MES2: {
                    //TODO
//                    if (item.getCODE_MES2() != null && !item.getCODE_MES2().isEmpty()) {
//                        result.setCODE_MES2(item.getCODE_MES2());
//                    } else {
//                        logger.warn("CODE_MES2: not founded");
//                    }
                    break;
                }
                case OS_SLUCH: {
                    List<Integer> a = new ArrayList<Integer>(2);
                    if (!item.getOS_SLUCH().isEmpty()) {
                        for (String b : item.getOS_SLUCH().split(",")) {
                            try {
                                a.add(Integer.parseInt(b));
                            } catch (NumberFormatException e) {
                                logger.debug("OS_SLUCH: Cannot parse[{}] to integer", b);
                            }
                        }
                    } else {
                        a.add(0);
                    }
                    result.setOS_SLUCH(a);
                    break;
                }
                default: {
                    logger.warn("Unknown optional Field with name \'{}\'", optionalField.name());
                    break;
                }
            }
        }
        result.setPatient(masterSluch.getResult().getPatient());
        result.setUSL(formUslStructure());
        //Установка общей суммы всех услуг в случае
        result.setSUMV(SUMV);
        result.setIDCASE(insertAccountItem(account, result, item));
        resultList.add(result);
        return resultList;
    }

    private List<Usl> formUslStructure() {
        final List<Usl> resultList = new ArrayList<Usl>(itemList.size());
        for (AdditionalUploadRow uslItem : itemList) {
            Usl current = new Usl();
            current.setCODE_USL(uslItem.getCODE_USL());
            current.setContract_TariffId(uslItem.getTariffId() != null ? uslItem.getTariffId() : -1);
            current.setKOL_USL(uslItem.getED_COL());
            current.setTARIF(uslItem.getTARIF());
            resultList.add(current);
        }
        return resultList;
    }

    /**
     * Сохранение позиции счета и проставление тега IDCASE
     *
     * @param account счет, к которому надо привязать создаваемую позицию счета
     * @param sluch   случай, для которого выставляется позиция счета
     * @return IDCASE
     */
    public int insertAccountItem(final Account account, final Sluch sluch, final AdditionalUploadRow item) {
        final ru.korus.tmis.core.entity.model.AccountItem newItem = new ru.korus.tmis.core.entity.model.AccountItem();
        newItem.setDeleted(false);
        newItem.setMaster(account);
        newItem.setServiceDate(item.getDATE_2());
        newItem.setClient(new ru.korus.tmis.core.entity.model.Patient(masterSluch.getPatient()));
        newItem.setEvent(new Event(item.getEvent()));
        newItem.setAction(new Action(item.getAction()));
        newItem.setPrice(sluch.getSUMV());
        newItem.setUnit(new RbMedicalAidUnit(item.getRbMedicalAidUnitId()));
        newItem.setAmount(item.getED_COL());
        newItem.setSum(sluch.getSUMV());
        newItem.setDate(null);
        newItem.setNumber(""); //Появится уже после загрузки ответа из ТФОМС
        newItem.setRefuseType(null);
        newItem.setNote("Выгрузка в ТФОМС: Дополнительный случай");
        newItem.setTariff(null);
        newItem.setService(new RbService(item.getRbService()));
        newItem.setNotUploadAnymore(false);
        final ru.korus.tmis.core.entity.model.AccountItem accountItem = TFOMSServer.getAccountItemBean().persistNewItem(newItem);
        logger.debug("FOR Action[{}] NEW AccountItem[{}]", item.getAction(), accountItem.getId());
        //Перевыстваление позиции
        final int reexposeItemCount = TFOMSServer.getAccountItemBean().reexposeItems(accountItem.getId(), item.getAction());
        logger.debug("reexposed {} items", reexposeItemCount);
        return newItem.getId();
    }

    private long getLongValue(final Date param) {
        return DateConvertions.convertDateToUTCMilliseconds(param);
    }

    private short getShortValue(final Integer param) {
        if (param > Short.MIN_VALUE || param < Short.MAX_VALUE) {
            return param.shortValue();
        } else {
            logger.error("Value[{}] is out of range for shortType. return -1;", param);
            return -1;
        }
    }

    private short getShortValue(final String param) {
        try {
            return Short.parseShort(param);
        } catch (NumberFormatException e) {
            logger.error("Cannot parse string[{}] to short. return -1;", param);
            return -1;
        }
    }


}
