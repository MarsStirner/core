package ru.korus.tmis.rlsupdate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.ejb.*;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import misexchange.*;

import org.hl7.v3.CD;
import org.hl7.v3.CR;
import org.hl7.v3.CV;
import org.hl7.v3.POCDMT000040LabeledDrug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbOrgStructureBean;
import ru.korus.tmis.core.database.DbOrgStructureBeanLocal;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.util.ConfigManager;

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 28.05.2013, 15:04:07 <br>
 * Company: Korus Consulting IT<br>
 * Description:Синхронизация БД лекарственных средств “РЛС” c информацией 1С
 */
@Stateless
public class SyncWith1C {

    /**
     * Наименование системы кодирования лекарственных средств (1С)
     */
    private static final String CODE_SYSTEM_RLS = "RLS";
    /**
     * Код описания тары в кодовой системе "RLS"
     */
    private static final String UPACK = "UPACK";
    /**
     * Наименование системы кодирования для описания форм выпуска лекарственных средств
     */
    private static final String RLS_CLSDRUGFORMS = "RLS_CLSDRUGFORMS";
    /**
     * Код описания упаковки в кодовой системе "RLS"
     */
    private static final String PPACK = "PPACK";
    /**
     * Наименование системы кодирования объемов лекарственных средств
     */
    private static final String RLS_CUBICUNITS = "RLS_CUBICUNITS";
    /**
     * Наименование системы кодирования массы лекарственных средств
     */
    private static final String RLS_MASSUNITS = "RLS_MASSUNITS";
    /**
     * Наименование системы кодирования упаковок лекарственных средств
     */
    private static final String RLS_DRUGPACK = "RLS_DRUGPACK";
    /**
     * Наименование системы кодирования действующих веществ лекарственных средств
     */
    private static final String RLS_TRADENAMES = "RLS_TRADENAMES";
    /**
     * Наименование системы кодирования дейст лекарственных средст
     */
    private static final String RLS_ACTMATTERS = "RLS_ACTMATTERS";

    private static final Logger logger = LoggerFactory.getLogger(SyncWith1C.class);
    private static final String RLS_UPDATE_IS_DISABLED = "RLS update is disabled. For enable RLS update set Drugstore.UpdateRLS=true in  table 'Setting' (database 'tmis_core').";
    private static final String EOL = System.getProperty("line.separator");

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    private static List<RbStorage> storageUuids = null;

    private static DrugList drugList;

    private Map<Integer, RlsNomen> drugsInDb;

    private static MISExchange serv = null;

    private static MISExchangePortType servPort = null;

    /**
     * Обновление БД - один раз в день в 0 часов 00 мин
     */
    @Schedule(second = "0", minute = "0", hour = "0")
    public void pull1С() {
        if ( ConfigManager.Drugstore().isUpdateRLS()) {
            update();
        }
    }

    /**
     * Обновление таблиц базы данных, описывающих реестр лекарственных средств
     * @return
     */
    public String update() {
        logger.info("update RLS...start");
        if ( !ConfigManager.Drugstore().isUpdateRLS() ) {
            logger.info("update RLS...stoped");
            return RLS_UPDATE_IS_DISABLED;
        }
        MISExchangePortType ws1C = getMisExchangePortType();
        //Справочник лекарственных средсв из системы 1С
        final DrugList drugListRes = ws1C.getDrugList();
        logger.info("update RLS...the list of drugs has been recived from 1C. list size: {}", drugListRes.getDrug().size());
        String res = htmlNewLine("update RLS...start...1C drugs list size: " + drugListRes.getDrug().size() + EOL);
        ObjectFactory factory = new ObjectFactory();
        drugList = factory.createDrugList();
        drugsInDb = new HashMap<Integer, RlsNomen>();
        for (POCDMT000040LabeledDrug drug : drugListRes.getDrug()) {
            final Integer drugRlsCode = getDrugRlsCode(drug);
            if (drugRlsCode != null) { // если задан код лекарственного средства

                RlsNomen rlsNomen = new RlsNomen();
                rlsNomen.setId(drugRlsCode);

                final String dosageValue = getDosageValue(drug);
                    if (dosageValue != null && !"".equals(dosageValue)) {
                        rlsNomen.setDosageValue(dosageValue);
                    }
                rlsNomen.setDosageUnit(getRbUnit(getDosageUnit(drug)));
                rlsNomen.setUnit(getRbUnit(getFillingUnit(drug)));

                rlsNomen.setRlsFilling(initByName(new  RlsFilling(),getFillingName(drug)));
                rlsNomen.setRlsForm(initByName(new RlsForm(), formatForDb(getFormName(drug))));

                final String inpNames[] = splitNames(getInpName(drug));
                final RlsActMatter rlsActMatter = initByLocalName(initByName(new RlsActMatter(), inpNames[1]), inpNames[0]);

                rlsNomen.setRlsPacking(initByName(new RlsPacking(), formatForDb(getPackingName(drug))));
                rlsNomen.setRlsTradeName(initByLocalName(initByName(new RlsTradeName(), getTradeNameLat(drug)), getTradeName(drug)));

                RlsNomen drugDb = em.find(RlsNomen.class, drugRlsCode);

                if (drugDb == null) { // если лекарственного средства нет в БД, то добавляем его в БД
                    saveNewDrug(rlsActMatter, rlsNomen);
                    String info =  "New drug: #" + drugRlsCode + EOL;
                    logger.info(info);
                    res += info;
                }
                else { // если лекарственное средство с кодом drugRlsCode есть, то сравниваем данные ТМИС и 1С
                    String info = verify(drugDb, rlsNomen);
                    if( !info.isEmpty()) {  //если описание лекарственного средства в БД ТМИС и по данным 1С не совпадают
                        logger.info(info);
                        res += info;
                    }
                }

                drugsInDb.put(drugRlsCode, rlsNomen);
                drugList.getDrug().add(drug);
            }
        }
        final String end = htmlNewLine("update RLS...completed");
        res += end;
        logger.info(end);
        return res;
    }

    private String htmlNewLine(String s) {
        return "<p>" + s + " </p>";

    }

    private MISExchangePortType getMisExchangePortType() {
        if (serv == null || servPort == null) {
            serv = new MISExchange();
            servPort = serv.getMISExchangeSoap();
        }
        return servPort;
    }

    private void saveNewDrug(RlsActMatter rlsActMatter, RlsNomen rlsNomen) {
        em.persist(rlsActMatter);
        rlsNomen.setRlsActMatter(rlsActMatter);
        em.persist(rlsNomen);
        em.flush();
    }


    private RbUnit getRbUnit(final String code) {
        if(code == null) {
            return null;
        }
        //final TypedQuery<RbUnit> code1 = em.createNamedQuery("RbUnit.findByCode", RbUnit.class).setParameter("code", code);
        final TypedQuery<RbUnit> code1 = em.createQuery("SELECT u FROM RbUnit u WHERE u.code = :code", RbUnit.class).setParameter("code", code);
        List<RbUnit> units =
                code1.getResultList();

        if(units.isEmpty()) {
            RbUnit res = new RbUnit();
            res.setCode(code);
            res.setName(code);
            em.persist(res);
            return res;
        }
        return units.get(0);
    }

    private <T> T initByName(T t, String name) {
        String columnName = "name";
        T res = getByColumnValue(t, name, columnName);
        ((UniqueName)res).setName(name);
        return res;
    }

    private <T> T initByLocalName(T t, String name) {
        String columnName = "localName";
        T res = getByColumnValue(t, name, columnName);
        ((UniqueLocalName)res).setLocalName(name);
        return res;
    }

    private <T> T getByColumnValue(T t, String name, String columnName) {
        final String fullClassName = t.getClass().getName();
        final String className = fullClassName.substring(fullClassName.lastIndexOf('.') + 1);
        List<Object> rlsFillings =
                (List<Object>) em.createQuery("SELECT f FROM " + className + " f WHERE f." + columnName + " = :name",  t.getClass()).setParameter("name", name).getResultList();
        return rlsFillings.isEmpty() ?  t : (T)rlsFillings.get(0);
    }

    /**
     * Извлечение наименования активного вещества (name) и латинского наименования (lat_name) из полного имени фомата "name (lat_name)"
     * @param inpName
     * @return
     */
    private String[] splitNames(String inpName) {
        String[] res = {inpName, ""};
        int index0 = inpName.indexOf('(');
        int index1 = inpName.indexOf(')');
        if (index0 < 0) {
            return res;
        }
        res[0] = inpName.substring(0, index0 - 1);
        res[1] = inpName.substring(index0 + 1, index1 > 0 ? index1 : inpName.length() );
        return res;
    }

    /**
     * Получение полного наименования активного вещества лекарственного средства
     * @param drug - описание ЛС
     * @return - наименование активного вещества лекарственного средства
     */
    private String getInpName(POCDMT000040LabeledDrug drug) {
            return getTranslationDisplayNameByName(drug, RLS_ACTMATTERS);
    }

    /*
     * Сверка описание лекарственного средства в БД ТМИС и по данным 1С
     *
     * @param rlsNomenDb - описание лекарственного средства в БД ТМИС
     * @param rlsNomenNew - описание лекарственного средства по данным сервиса 1С
     * @param rlsActName - наименование активного вещества по данным сервиса 1С
     * @return -
     *   пустастая строка, если описания в БД ТМИС и 1С совпвдют;
     *   строка с описанием найденных различий, если описания в БД ТМИС и 1С отличаются
     */
    private String verify(RlsNomen rlsNomenDb,
                          RlsNomen rlsNomenNew) {
        String res = "";
        if (rlsNomenDb.getDosageValue() != null) {
            res += checkDiff(rlsNomenNew.getDosageValue(), rlsNomenDb.getDosageValue(), "Dosage", "Name");
        }
        if (rlsNomenDb.getRlsFilling() != null) {
            final String diff = checkDiff(formatForDb(rlsNomenNew.getRlsFilling().getName()), formatForDb(rlsNomenDb.getRlsFilling().getName()), "Filling", "Name");
            if(diff.isEmpty()) {
                rlsNomenNew.setRlsFilling(rlsNomenDb.getRlsFilling());
            }
            res += diff;
        }
        if (rlsNomenDb.getRlsForm() != null) {
            res += checkDiff(rlsNomenNew.getRlsForm().getName(), rlsNomenDb.getRlsForm().getName(), "Form", "Name");
        }
        if (rlsNomenDb.getRlsPacking() != null) {
            res += checkDiff(rlsNomenNew.getRlsPacking().getName(), rlsNomenDb.getRlsPacking().getName(), "Packing", "Name");
        }
        if (rlsNomenDb.getRlsTradeName() != null) {
            res += checkDiff(rlsNomenDb.getRlsTradeName().getName(), rlsNomenDb.getRlsTradeName().getName(), "TradeName", "Name");
        }

        if (!res.equals("")) { //если найдено хоть одно различие
            res = "Warning. The description of drug #" + rlsNomenDb.getId() + " was different from 1C information: " + res + EOL;
        }
        return res;
    }

    private String checkDiff(Object value1C, Object valueDb, String table, String field) {
        if (valueDb != null && !valueDb.equals(value1C)) {
            return "1C " + table + ": '" + value1C + "'; rls" + table + "." + field + ": '" + valueDb + "' ";
        }
        return "";
    }

    private Integer getDrugRlsCode(POCDMT000040LabeledDrug drug) {
        Integer res = null;
        try {
            res = Integer.parseInt(drug.getCode().getCode());
        } catch (NumberFormatException ex) {
            // если код не целое число, то возвращаем значение по умолчанию
        }
        return res;
    }

    /**
     * @param drug
     * @return
     */
    private String getPackingName(POCDMT000040LabeledDrug drug) {
        String res = "";
        final CD translation = getTranslationByCodeSystemNameAndCode(drug.getCode().getTranslation(), CODE_SYSTEM_RLS, UPACK);
        if (translation != null) {
            // Код тары
            final CD cdPack = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            final String code = cdPack != null ? cdPack.getCode().trim() + " " : "";
            final String count = cdPack != null ? ((String) cdPack.getOriginalText().getContent().get(0)).trim() + " " : "";
            res = (code + count).trim();
        }
        return res;
    }


    private String getTradeName(POCDMT000040LabeledDrug drug) {
        return getTranslationCodeByName(drug, RLS_TRADENAMES).replace('_', ' ');
    }

    private String getTradeNameLat(POCDMT000040LabeledDrug drug) {
        return getTranslationDisplayNameByName(drug, RLS_TRADENAMES);
    }

    private String formatForDb(String str) {
        if (str != null) {
            return str.replace(" ","").replace('_', ' ');
        }
        return str;
    }

    /**
     * Код формы выпуска лекарства
     * 
     * @param drug
     *            - 1С описание лекарства
     * @return строку, содержащею код формы выпуска лекарства
     */
    private String getFormName(POCDMT000040LabeledDrug drug) {
        return getTranslationCodeByName(drug, RLS_CLSDRUGFORMS);
    }

    private String getTranslationDisplayNameByName(final POCDMT000040LabeledDrug drug, final String codeSystemName) {
        String res = "";
        final CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), codeSystemName);
        if (translation != null) {
            res = translation.getDisplayName();
        }
        return res;
    }

    private String getTranslationCodeByName(final POCDMT000040LabeledDrug drug, final String codeSystemName) {
        String res = "";
        final CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), codeSystemName);
        if (translation != null) {
            res = translation.getCode();
        }
        return res;
    }

    /**
     * Описание упаковки.
     * Формируется как конкатация кода упаковки, массы, ед. измер. массы, объема, ед. измер. объема, количества таблеток в упаковке и код лекарственной формы
     * @param drug - 1С описание лекарства
     * @return - строку, содержащею описание упаковки
     */
    private String getFillingName(POCDMT000040LabeledDrug drug) {
        String res = "";
        final CD translation = getTranslationByCodeSystemNameAndCode(drug.getCode().getTranslation(), CODE_SYSTEM_RLS, PPACK);
        if (translation != null) {
            // Код упаковки
            final CD cdCode = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            final String code = cdCode != null ? formatForDb(cdCode.getCode().trim()): "";
            // Кол-во в упаковке
            String count = cdCode != null ? ((String) cdCode.getOriginalText().getContent().get(0)).trim() : "";
            if ( "0".equals(count)) {
                count = "";
            }
            // Масса упаковки
            final CD cdMass = getValueByCodeSystemName(translation.getQualifier(), RLS_MASSUNITS);
            final String mass = cdMass != null && !((String)cdMass.getOriginalText().getContent().get(0)).isEmpty() ?
                    ((String)cdMass.getOriginalText().getContent().get(0)).trim() + " " : "";
            // Ед.изм. массы
            final String massUnit = cdMass != null ? cdMass.getCode().trim() : "";

            final CD volume = getValueByCodeSystemName(translation.getQualifier(), RLS_CUBICUNITS);
            // Объем
            final String volumeValue = volume != null && !((String) volume.getOriginalText().getContent().get(0)).isEmpty() ?
                    ((String) volume.getOriginalText().getContent().get(0)).trim() + " " : "";
            // Ед.изм. объема
            final String volumeUnit = volume != null ? formatForDb(volume.getCode().trim()) : "";


            res = (code + mass + massUnit + volumeValue + volumeUnit + count).trim();
        }
        return res;
    }

    /**
     * Ед.Изм. препарата упаковки.
     * Формируется как конкатация кода упаковки, массы, ед. измер. массы, объема, ед. измер. объема, количества таблеток в упаковке и код лекарственной формы
     * @param drug - 1С описание лекарства
     * @return - строку, содержащею описание упаковки
     */
    private String getFillingUnit(POCDMT000040LabeledDrug drug) {
        final CD translation = getTranslationByCodeSystemNameAndCode(drug.getCode().getTranslation(), CODE_SYSTEM_RLS, PPACK);
        if (translation != null) {
            // Код упаковки
            final CD cdCode = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            final String code = cdCode != null ? formatForDb(cdCode.getCode().trim()): "";
            // Кол-во в упаковке
            String count = cdCode != null ? ((String) cdCode.getOriginalText().getContent().get(0)).trim() : "";
            if ( "0".equals(count)) {
                // Ед.изм. массы
                final CD cdMass = getValueByCodeSystemName(translation.getQualifier(), RLS_MASSUNITS);
                final String massUnit = cdMass != null ? cdMass.getCode().trim() : "";
                if("".equals(massUnit)) {
                    final CD volume = getValueByCodeSystemName(translation.getQualifier(), RLS_CUBICUNITS);
                    // Ед.изм. объема
                    return volume != null ? formatForDb(volume.getCode().trim()) : null;

                }  else { // масса
                    return massUnit;
                }

            } else { //измеряется м штуках
                return code;
            }
        }
        return null;
    }



    private CD getValueByCodeSystemName(List<CR> cont, String codeSystemName) {
        for (CR qualifier : cont) {
            if (qualifier.getValue().getCodeSystemName().equals(codeSystemName)) {
                return qualifier.getValue();
            }
        }
        return null;
    }

    /**
     * Доза в единице лекарственной формы
     *
     * @param drug - 1С описание лекарства
     * @return строку, содержащею описание дозировки
     */
    private String getDosageValue(POCDMT000040LabeledDrug drug) {
        CD value = getDrugFormCdValue(drug);
        if(value != null) {
            String val = null;
            if (value.getOriginalText() != null && !value.getOriginalText().getContent().isEmpty()) {
                val = (String) value.getOriginalText().getContent().get(0);
            }
            return (val != null ? (val.replace(",", ".") + " " ) : "");
        }
        return "";
    }

    /**
     * Единицы измерения дозы
     *
     * @param drug - 1С описание лекарства
     * @return строку, содержащею описание дозировки
     */
    private String getDosageUnit(POCDMT000040LabeledDrug drug) {
        CD value = getDrugFormCdValue(drug);
        return value != null ? value.getCode() : null;
    }


    private CD getDrugFormCdValue(POCDMT000040LabeledDrug drug) {
        CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), RLS_CLSDRUGFORMS);
        CD value = null;
        if (translation != null) {
            for (CR qualifier : translation.getQualifier()) {
                final CV name = qualifier.getName();
                if (name != null && name.getCode().startsWith("DF")) { // поиск одного из кодов: "DFACT", "DFCONC", "DFMASS", "DFSIZE"
                    value = qualifier.getValue();
                    break;
                }
            }
        }
        return value;
    }

    private CR getQualifierByCode(List<CR> qualifiers, String code) {
        for (CR qualifier : qualifiers) {
            final CV name = qualifier.getName();
            if (name != null && code.startsWith(name.getCode())) {
                return qualifier;
            }
        }
        return null;
    }


    private CD getTranslationByCodeSystemName(List<CD> cont, String codeSystemName) {
        for (CD translation : cont) {
            if (translation.getCodeSystemName().equals(codeSystemName)) {
                return translation;
            }
        }
        return null;
    }

    private CD getTranslationByCodeSystemNameAndCode(List<CD> cont, String codeSystemName, String code) {
        for (CD translation : cont) {
            if (translation.getCodeSystemName().equals(codeSystemName) &&
                translation.getCode().equals(code)) {
                return translation;
            }
        }
        return null;
    }

    public String updateBalance() {
        return updateBalance(drugList);
    }

    public String updateBalance(DrugList drugList ) {
        logger.info("update RLS balance...start");
        if ( !ConfigManager.Drugstore().isUpdateRLS() ) {
            logger.info("update RLS balance...stoped");
            return RLS_UPDATE_IS_DISABLED;
        }
        String res =  htmlNewLine("update RLS balance...start" + EOL);
        OrgStructure mainOrganization =  em.find(OrgStructure.class, 1);
        if(mainOrganization != null && mainOrganization.getUuid() != null) {
            List<RbStorage> rbStoragesList = getStorageUuid();
            for(RbStorage rbStorages : rbStoragesList) {
                res += updateBalance(drugList, mainOrganization.getUuid().getUuid(), rbStorages);
            }
        }
        return res;
    }

    private List<RbStorage> getStorageUuid() {
        if(storageUuids == null || storageUuids.isEmpty()) {
            storageUuids = new LinkedList<RbStorage>();
            MISExchangePortType servicePort = getMisExchangePortType();
            StorageList storageFrom1C = servicePort.getStorageList();
            for( Storage storage : storageFrom1C.getStorage() ) {
                List<RbStorage> uuids = em.createNamedQuery("rbStorage.findByUUID", RbStorage.class).setParameter("uuid", storage.getRef()).getResultList();
                if(uuids.isEmpty()) {
                    RbStorage storageDb = new RbStorage();
                    storageDb.setName(storage.getDescription());
                    storageDb.setUuid(storage.getRef());
                    em.persist(storageDb);
                    uuids.add(storageDb);
                }
                storageUuids.add(uuids.iterator().next());
            }
        }
        return storageUuids;
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    public String updateBalance(DrugList drugList, String organizationRef, RbStorage rbStorage) {
        logger.info("update RLS balance for UUID {}", rbStorage.getUuid());
        final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Integer updateCount = 0;
        String res = htmlNewLine("update RLS balance for UUID '" + rbStorage.getUuid() + "'");
        MISExchangePortType servicePort = getMisExchangePortType();
        if(!drugList.getDrug().isEmpty() && rbStorage.getUuid() != null) {
            BalanceOfGoods2 balanceOfGoods2 = servicePort.balanceOfGoods(drugList, organizationRef,  rbStorage.getUuid());
            if (balanceOfGoods2 != null) {
                for( BalanceOfGoods2.Storage storages : balanceOfGoods2.getStorage()) {
                    for(BalanceOfGoods2.Storage.Balance balance: storages.getBalance()) {
                        final Integer drugRlsCode = getDrugRlsCode(balance.getDrug());
                        final RlsNomen rlsNomen = drugsInDb == null ? em.find(RlsNomen.class, drugRlsCode) : drugsInDb.get(drugRlsCode);
                        if(rlsNomen != null) {
                            final List<BalanceOfGoods2.Storage.Balance.Goods> goodsList = balance.getGoods();
                            for(BalanceOfGoods2.Storage.Balance.Goods goods : goodsList) {
                                try {
                                    Date bestBefore = dateFormat.parse(goods.getBestBefore());
                                    RlsBalanceOfGood rlsBalanceOfGood = getRlsBalanceOfGood(drugRlsCode, rbStorage.getUuid(), bestBefore);
                                    rlsBalanceOfGood.setRlsNomen(rlsNomen);
                                    rlsBalanceOfGood.setRbStorage(rbStorage);
                                    rlsBalanceOfGood.setValue(Double.parseDouble(goods.getQty().getValue()));
                                    rlsBalanceOfGood.setBestBefore(bestBefore);
                                    rlsBalanceOfGood.setUpdateDateTime(new Date());
                                    em.persist(rlsBalanceOfGood);
                                    em.flush();
                                    ++updateCount;
                                } catch (ParseException ex) {
                                    logger.info("Wrong date format '{}', drug code: '{}', storage: '{}'", goods.getBestBefore(), drugRlsCode, rbStorage.getUuid());
                                } catch (NumberFormatException ex) {
                                    logger.info("Wrong dosage format for drug # {}: {}",drugRlsCode, ex );
                                    res += "Wrong storage value format for drug #" + drugRlsCode + ": " + goods.getQty().getValue() + EOL;
                                }

                            }
                        }
                    }
                }
            }
        }
        res += "<p>balance update count: " +  updateCount +  "</p>" + EOL;
        return res;
    }

    private RlsBalanceOfGood getRlsBalanceOfGood(Integer drugRlsCode, String uuid,  Date bestBefore) {
        List<RlsBalanceOfGood> rlsBalanceOfGoods =
                em.createNamedQuery( "RlsBalanceOfGood.findByCodeAndStore", RlsBalanceOfGood.class).
                        setParameter("code", drugRlsCode).
                        setParameter("uuid", uuid).
                        setParameter("date", bestBefore).getResultList();
        if (rlsBalanceOfGoods.isEmpty()) {
            return new RlsBalanceOfGood();
        } else {
            return rlsBalanceOfGoods.get(0);
        }
    }
}
