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

/**
 * Author: Sergey A. Zagrebelny <br>
 * Date: 28.05.2013, 15:04:07 <br>
 * Company: Korus Consulting IT<br>
 * Description: <br>
 */

@Stateless
public class SyncWith1C {

    /**
     *
     */
    private static final String RLS = "RLS";
    /**
     * Тара
     */
    private static final String UPACK = "UPACK";
    /**
     * 
     */
    private static final String RLS_CLSDRUGFORMS = "RLS_CLSDRUGFORMS";
    /**
     * Упаковка
     */
    private static final String PPACK = "PPACK";
    /**
     * 
     */
    private static final String RLS_CUBICUNITS = "RLS_CUBICUNITS";
    /**
     * 
     */
    private static final String RLS_MASSUNITS = "RLS_MASSUNITS";
    /**
     * 
     */
    private static final String RLS_DRUGPACK = "RLS_DRUGPACK";
    /**
     * 
     */
    private static final String RLS_TRADENAMES = "RLS_TRADENAMES";
    /**
     *
     */
    private static final String RLS_ACTMATTERS = "RLS_ACTMATTERS";

    private static final Logger logger = LoggerFactory.getLogger(SyncWith1C.class);


    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @Schedule(second = "0", minute = "0", hour = "0")
    public void pullDB() {
        update();
    }

    /**
     * @return
     */
    public String update() {
        logger.info("update RLS...start");
        final MISExchange serv = new MISExchange();
        MISExchangePortType ws1C = serv.getMISExchangeSoap();
        DrugList drugList = ws1C.getDrugList();
        logger.info("update RLS...the list of drugs has been recived from 1C. list size: {}", drugList.getDrug().size());
        String res = "";
        for (POCDMT000040LabeledDrug drug : drugList.getDrug()) {
            final Integer drugRlsCode = getDrugRlsCode(drug);
            if (drugRlsCode != null) {

                final RlsDosage rlsDosage = new RlsDosage();
                final RlsFilling rlsFilling = new RlsFilling();
                final RlsForm rlsForm = new RlsForm();
                final RlsInpName rlsInpName = new RlsInpName();
                final RlsPacking rlsPacking = new RlsPacking();
                final RlsTradeName rlsTradeName = new RlsTradeName();
                final RlsNomen rlsNomen = new RlsNomen();

                rlsDosage.setName(getDosageName(drug));
                rlsFilling.setName(getFillingName(drug));
                rlsForm.setName(getFormName(drug));

                final String inpNames[] = splitNames(getInpName(drug));
                rlsInpName.setName(inpNames[0]);
                rlsInpName.setLatName(inpNames[1]);

                rlsPacking.setName(getBoxingName(drug));
                rlsTradeName.setName(getTradeName(drug));

                rlsNomen.setCode(drugRlsCode);

                List<RlsNomen> drugs =
                        em.createQuery("SELECT n FROM RlsNomen n WHERE n.code = :code", RlsNomen.class).setParameter("code", drugRlsCode).getResultList();

                if (drugs.isEmpty()) {
                    saveNewDrug(rlsDosage, rlsFilling, rlsForm, rlsInpName, rlsPacking, rlsTradeName, rlsNomen);
                    String info =  "New drug: #" + drugRlsCode + System.getProperty("line.separator");
                    logger.info(info);
                    res += info;
                } else {
                    String info = verify(drugs.get(0), rlsDosage, rlsFilling, rlsForm, rlsInpName, rlsPacking, rlsTradeName);
                    logger.info(info);
                    res += info;
                }

            }
        }
        logger.info("update RLS...completed");
        return res;
    }

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

    private String getInpName(POCDMT000040LabeledDrug drug) {
            return getTranslationDisplayNameByName(drug, RLS_ACTMATTERS);
    }

    /**
     * @param rlsNomenDb
     * @param rlsDosage
     * @param rlsFilling
     * @param rlsForm
     * @param rlsInpName
     * @param rlsPacking
     * @param rlsTradeName
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
            res += checkDiff(rlsFilling.getName(), rlsNomenDb.getRlsFilling().getName(), "Filling", "Name");
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

        if (!res.equals("")) {
            res = "Warning. The description of drug #" + rlsNomenDb.getCode() + " was different from 1C information: " + res + System.getProperty("line.separator");
        }
        return res;
    }

    /**
     * @param name
     * @param name2
     * @param string
     * @param string2
     * @return
     */
    private String checkDiff(String value1C, String valueDb, String table, String field) {
        if (!valueDb.equals(value1C)) {
            return "1C " + table + ": '" + value1C + "'; rls" + table + "." + field + ": '" + valueDb + "' ";
        }
        return "";
    }

    /**
     * @param rlsDosage
     * @param rlsFilling
     * @param rlsForm
     * @param rlsInpName
     * @param rlsPacking
     * @param rlsTradeName
     * @param rlsNomen
     */
    protected void saveNewDrug(final RlsDosage rlsDosage,
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

    /**
     * @param drug
     * @return
     */
    protected Integer getDrugRlsCode(POCDMT000040LabeledDrug drug) {
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
    private String getBoxingName(POCDMT000040LabeledDrug drug) {
        String res = "";
        final CD translation = getTranslationByCodeSystemNameAndCode(drug.getCode().getTranslation(), RLS, UPACK);
        if (translation != null) {
            // Код тары
            final CD cdPack = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            final String code = cdPack != null ? cdPack.getCode().trim() + " " : "";
            final String count = cdPack != null ? ((String) cdPack.getOriginalText().getContent().get(0)).trim() + " " : "";
            res = (code + count).trim();
        }
        return res;
    }

    /**
     * @param drug
     * @return
     */
    private String getLatName(POCDMT000040LabeledDrug drug) {
        String res = "";
        // форма выпуска
        final CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), RLS_TRADENAMES);
        if (translation != null) {
            res = translation.getDisplayName();
        }
        return res;
    }

    /**
     * @param drug
     * @return
     */
    private String getTradeName(POCDMT000040LabeledDrug drug) {
        return getTranslationCodeByName(drug, RLS_TRADENAMES);
    }

    /**
     * Код формы выпуска лекарства
     * 
     * @param drug
     *            - HL7 описание лекарства
     * @return строку, содержащею код формы выпуска лекарства
     */
    private String getFormName(POCDMT000040LabeledDrug drug) {
        return getTranslationDisplayNameByName(drug, RLS_CLSDRUGFORMS);
    }

    private String getTranslationDisplayNameByName(final POCDMT000040LabeledDrug drug, final String codeSystemName) {
        String res = "";
        final CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), codeSystemName);
        if (translation != null) {
            res = translation.getDisplayName();
        }
        return res;
    }

    /**
     * @param drug
     * @param codeSystemName
     * @return
     */
    private String getTranslationCodeByName(final POCDMT000040LabeledDrug drug, final String codeSystemName) {
        String res = "";
        final CD translation = getTranslationByCodeSystemName(drug.getCode().getTranslation(), codeSystemName);
        if (translation != null) {
            res = translation.getCode();
        }
        return res;
    }

    /**
     * @param drug
     * @return
     */
    private String getFillingName(POCDMT000040LabeledDrug drug) {
        String res = "";
        final CD translation = getTranslationByCodeSystemNameAndCode(drug.getCode().getTranslation(), RLS, PPACK);
        if (translation != null) {
            // Код упаковки
            final CD cdCode = getValueByCodeSystemName(translation.getQualifier(), RLS_DRUGPACK);
            final String code = cdCode != null ? cdCode.getCode().trim() + " " : "";
            // Масса упаковки
            final CD cdMass = getValueByCodeSystemName(translation.getQualifier(), RLS_MASSUNITS);
            final String mass = cdMass != null ? ((String) cdMass.getOriginalText().getContent().get(0)).trim() + " " : "";
            // Ед.изм. массы
            final String massUnit = cdMass != null ?  (cdMass.getCode() + " ") : "";

            final CD volume = getValueByCodeSystemName(translation.getQualifier(), RLS_CUBICUNITS);
            // Объем
            final String volumeValue = volume != null ? ((String) volume.getOriginalText().getContent().get(0)).trim() + " " : "";
            // Ед.изм. объема
            final String volumeUnit = volume != null ? (String) volume.getCode().trim() + " " : "";

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

    /**
     * @param qualifier
     * @param string
     * @return
     */
    private CD getValueByCodeSystemName(List<CR> cont, String codeSystemName) {
        for (CR qualifier : cont) {
            if (qualifier.getValue().getCodeSystemName().equals(codeSystemName)) {
                return qualifier.getValue();
            }
        }
        return null;
    }

    /**
     * @param drug
     * @return
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

    /**
     *
     * @param cont
     * @param codeSystemName
     * @return
     */
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
