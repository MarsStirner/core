package ru.korus.tmis.rlsupdate;

import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import misexchange.DrugList;
import misexchange.MISExchange;
import misexchange.MISExchangePortType;

import org.hl7.v3.CD;
import org.hl7.v3.CR;
import org.hl7.v3.POCDMT000040LabeledDrug;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.RlsDosage;
import ru.korus.tmis.core.entity.model.RlsFilling;
import ru.korus.tmis.core.entity.model.RlsForm;
import ru.korus.tmis.core.entity.model.RlsInpName;
import ru.korus.tmis.core.entity.model.RlsNomen;
import ru.korus.tmis.core.entity.model.RlsPacking;
import ru.korus.tmis.core.entity.model.RlsTradeName;
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


    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

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
            return "RLS update is disabled. For enable RLS update set Drugstore.UpdateRLS=true in  table 'Setting' (database 'tmis_core').";
        }
        final MISExchange serv = new MISExchange();
        MISExchangePortType ws1C = serv.getMISExchangeSoap();
        //Справочник лекарственных средсв из системы 1С
        DrugList drugList = ws1C.getDrugList();
        logger.info("update RLS...the list of drugs has been recived from 1C. list size: {}", drugList.getDrug().size());
        String res = "update RLS...start...1C drugs list size: " + drugList.getDrug().size() +  System.getProperty("line.separator");
        for (POCDMT000040LabeledDrug drug : drugList.getDrug()) {
            final Integer drugRlsCode = getDrugRlsCode(drug);
            if (drugRlsCode != null) { // если задан код лекарственного средства
                final RlsDosage rlsDosage = new RlsDosage();
                final RlsFilling rlsFilling = new RlsFilling();
                final RlsForm rlsForm = new RlsForm();
                final RlsInpName rlsInpName = new RlsInpName();
                final RlsPacking rlsPacking = new RlsPacking();
                final RlsTradeName rlsTradeName = new RlsTradeName();
                final RlsNomen rlsNomen = new RlsNomen();

                rlsDosage.setName(getDosageName(drug).replace('_', ' '));
                rlsFilling.setName(getFillingName(drug));
                rlsForm.setName(formatForDb(getFormName(drug)));

                final String inpNames[] = splitNames(getInpName(drug));
                rlsInpName.setName(inpNames[0]);
                rlsInpName.setLatName(inpNames[1]);

                rlsPacking.setName(formatForDb(getPackingName(drug)));

                rlsTradeName.setName(getTradeName(drug).replace('_', ' '));
                rlsTradeName.setLatName(getTradeNameLat(drug));

                rlsNomen.setCode(drugRlsCode);

                List<RlsNomen> drugs =
                        em.createQuery("SELECT n FROM RlsNomen n WHERE n.code = :code", RlsNomen.class).setParameter("code", drugRlsCode).getResultList();

                if (drugs.isEmpty()) { // если лекарственного средства нет в БД, то добавляем его в БД
                    saveNewDrug(rlsDosage, rlsFilling, rlsForm, rlsInpName, rlsPacking, rlsTradeName, rlsNomen);
                    String info =  "New drug: #" + drugRlsCode + System.getProperty("line.separator");
                    logger.info(info);
                    res += info;
                } else { // если лекарственное средство с кодом drugRlsCode есть, то сравниваем данные ТМИС и 1С
                    RlsNomen lastVer = getMaxVersion(drugs);
                    String info = verify(lastVer, rlsDosage, rlsFilling, rlsForm, rlsInpName, rlsPacking, rlsTradeName);
                    if(!info.isEmpty()) { //если описание лекарственного средства в БД ТМИС и по данным 1С не совпадают, то добавляем
                        rlsNomen.setVersion(lastVer.getVersion() + 1);
                        saveNewDrug(rlsDosage, rlsFilling, rlsForm, rlsInpName, rlsPacking, rlsTradeName, rlsNomen);
                        logger.info(info);
                        res += info;
                    }
                }
            }
        }
        final String end = "update RLS...completed";
        res += end;
        logger.info(end);
        return res;
    }

    private RlsNomen getMaxVersion(List<RlsNomen> drugs) {
        RlsNomen res = drugs.get(0);
        Integer max = res.getVersion();
        for(RlsNomen drug : drugs) {
           if (max < drug.getVersion()) {
               max = drug.getVersion();
               res = drug;
           }
        }
        return res;
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
     * @param rlsDosage - дозировка по данным 1С
     * @param rlsFilling - упаковка по данным 1С
     * @param rlsForm - форма выпуска по данным 1С
     * @param rlsInpName - активное вещество по данным 1С
     * @param rlsPacking - тара по данным 1С
     * @param rlsTradeName - торговое наименование по данным 1С
     * @return -
     *   пустастая строка, если описания в БД ТМИС и 1С совпвдют;
     *   строка с описанием найденных различий, если описания в БД ТМИС и 1С отличаются
     */
    private String verify(RlsNomen rlsNomenDb,
            RlsDosage rlsDosage,
            RlsFilling rlsFilling,
            RlsForm rlsForm,
            RlsInpName rlsInpName,
            RlsPacking rlsPacking,
            RlsTradeName rlsTradeName) {
        String res = "";
        if (rlsNomenDb.getRlsDosage() != null) {
            res += checkDiff(rlsDosage.getName(), rlsNomenDb.getRlsDosage().getName(), "Dosage", "Name");
        }
        if (rlsNomenDb.getRlsFilling() != null) {
            final String diff = checkDiff(formatForDb(rlsFilling.getName()), formatForDb(rlsNomenDb.getRlsFilling().getName()), "Filling", "Name");
            if(diff.isEmpty()) {
                rlsFilling.setName(rlsNomenDb.getRlsFilling().getName());
            }
            res += diff;
        }
        if (rlsNomenDb.getRlsForm() != null) {
            res += checkDiff(rlsForm.getName(), rlsNomenDb.getRlsForm().getName(), "Form", "Name");
        }
        if (rlsNomenDb.getRlsInpName() != null) {
            res += checkDiff(rlsInpName.getName(), rlsNomenDb.getRlsInpName().getName(), "InpName", "Name");
            res += checkDiff(rlsInpName.getLatName(), rlsNomenDb.getRlsInpName().getLatName(), "InpLatName", "LatName");
        }
        if (rlsNomenDb.getRlsPacking() != null) {
            res += checkDiff(rlsPacking.getName(), rlsNomenDb.getRlsPacking().getName(), "Packing", "Name");
        }
        if (rlsNomenDb.getRlsTradeName() != null) {
            res += checkDiff(rlsTradeName.getName(), rlsNomenDb.getRlsTradeName().getName(), "TradeName", "Name");
        }

        if (!res.equals("")) { //если найдено хоть одно различие
            res = "Warning. The description of drug #" + rlsNomenDb.getCode() + " was different from 1C information: " + res + System.getProperty("line.separator");
        }
        return res;
    }

    private String checkDiff(String value1C, String valueDb, String table, String field) {
        if (!valueDb.equals(value1C)) {
            return "1C " + table + ": '" + value1C + "'; rls" + table + "." + field + ": '" + valueDb + "' ";
        }
        return "";
    }


    private void saveNewDrug(final RlsDosage rlsDosage,
            final RlsFilling rlsFilling,
            final RlsForm rlsForm,
            final RlsInpName rlsInpName,
            final RlsPacking rlsPacking,
            final RlsTradeName rlsTradeName,
            final RlsNomen rlsNomen) {
        rlsNomen.setRlsTradeName(rlsTradeName);
        rlsNomen.setRlsInpName(rlsInpName);
        rlsNomen.setRlsForm(rlsForm);
        rlsNomen.setRlsDosage(rlsDosage);
        rlsNomen.setRlsFilling(rlsFilling);
        rlsNomen.setRlsPacking(rlsPacking);

        em.persist(rlsDosage);
        em.persist(rlsFilling);
        em.persist(rlsForm);
        em.persist(rlsInpName);
        em.persist(rlsPacking);
        em.persist(rlsTradeName);
        em.persist(rlsNomen);
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
        return getTranslationCodeByName(drug, RLS_TRADENAMES);
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

            final CD cdCount = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            // Кол-во в упаковке
            String count = cdCount != null ? ((String) cdCount.getOriginalText().getContent().get(0)).trim() : "";
            if ( "0".equals(count)) {
                count = "";
            }

            res = (code + mass + massUnit + volumeValue + volumeUnit + count).trim();
        }
        return res;
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
     * Дозировка. Формируется как конкатация числового значение дозировки и наименования единиц измерения
     * @param drug - 1С описание лекарства
     * @return строку, содержащею описание дозировки
     */
    private String getDosageName(POCDMT000040LabeledDrug drug) {
        CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), RLS_CLSDRUGFORMS);
        if (translation != null && !translation.getQualifier().isEmpty()) {
            CD value = translation.getQualifier().get(0).getValue();
            String val = null;
            if (value.getOriginalText() != null && !value.getOriginalText().getContent().isEmpty()) {
                val = (String) value.getOriginalText().getContent().get(0);
            }
            String unit = value.getCode();
            return (val != null ? (val + " " ) : "") + (unit != null ? unit : "");
        }
        return "";
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
}
