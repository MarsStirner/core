package ru.korus.tmis.ws.transfusion.devtest;

import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import ru.korus.tmis.ws.transfusion.devtest.wsimport.EritrocyteMass;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.FinalVolume;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.IssueResult;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.LaboratoryMeasure;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.PatientCredentials;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.ProcedureInfo;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.TransfusionService;
import ru.korus.tmis.ws.transfusion.devtest.wsimport.TransfusionServiceImpl;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.02.2013, 11:46:11 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public class TrfuProcedureTest extends TestBase {

    /**
     * 
     */

    private static final PropType[] propConstants = { PropType.DONOR_ID,
            PropType.CONTRAINDICATION,
            PropType.BEFORE_HEMODYNAMICS_PULSE,
            PropType.AFTER_HEMODYNAMICS_PULSE,
            PropType.BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE,
            PropType.AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE,
            PropType.BEFORE_HEMODYNAMICS_TEMPERATURE,
            PropType.AFTER_HEMODYNAMICS_TEMPERATURE,
            PropType.COMPLICATIONS,
            PropType.INITIAL_VOLUME,
            PropType.CHANGE_VOLUME,
            PropType.INITIAL_TBV,
            PropType.CHANGE_TBV,
            PropType.INITIAL_SPEED,
            PropType.CHANGE_SPEED,
            PropType.INITIAL_INLETACRATIO,
            PropType.CHANGE_INLETACRATIO,
            PropType.INITIAL_TIME,
            PropType.CHANGE_TIME,
            PropType.INITIAL_PRODUCT_VOLUME,
            PropType.CHANGE_PRODUCT_VOLUME,
            PropType.ACD_LOAD,
            PropType.NACL_LOAD,
            PropType.CA_LOAD,
            PropType.OTHER_LOAD,
            PropType.TOTAL_LOAD,
            PropType.PACK_REMOVE,
            PropType.OTHER_REMOVE,
            PropType.TOTAL_REMOVE,
            PropType.BALANCE,
            PropType.MAKER,
            PropType.NUMBER,
            PropType.BLOOD_GROUP_ID,
            PropType.RHESUS_FACTOR_ID,
            PropType.VOLUME_PROC_RES,
            PropType.PRODUCTION_DATE,
            PropType.EXPIRATION_DATE,
            PropType.HT,
            PropType.SALINE_VOLUME,
            PropType.FINAL_HT,
            PropType.ORDER_REQUEST_ID,
            PropType.ORDER_ISSUE_RES_DATE,
            PropType.ORDER_ISSUE_RES_TIME,
    };

    @BeforeClass
    public static void init() {
        initTestCase("trfuProcedure_trfu_id_", propConstants);
    }

    @AfterClass
    public static void freeResource() {
        closeTestCase();
    }

    @Test
    public void createNewProc() {
        try {
            clearDB(propConstants);
            createActionWithProp(propConstants);
            setValue(PropType.PATIENT_ORG_STRUCT, 1);
            String res = waitOrderRequestId();
            AssertJUnit.assertTrue(res != null ? res.indexOf("Получен идентификатор в системе ТРФУ: ") == 0 : false);
            setProcedureRes();
        } catch (final InterruptedException ex) {
            ex.printStackTrace();
            Assert.fail(" Thread.sleep error");
        } catch (SQLException e) {
            e.printStackTrace();
            Assert.fail(" Thread.sleep error");
        }

    }

    private void setProcedureRes() {
        final TransfusionServiceImpl serv = new TransfusionServiceImpl();
        final TransfusionService wsTrfu = serv.getPortTransfusion();
        XMLGregorianCalendar now = null;
        try {
            now = DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar());
        } catch (final DatatypeConfigurationException e) {
            e.printStackTrace();
        }

        List<LaboratoryMeasure> measures = new Vector<LaboratoryMeasure>();
        measures.add(new LaboratoryMeasure());
        measures.get(0).setId(90);
        measures.get(0).setAfterOperation("res after");
        measures.get(0).setBeforeOperation("res befir");
        measures.get(0).setDuringOperation("res during");
        measures.get(0).setInProduct("in product");

        ProcedureInfo procedureInfo = new ProcedureInfo();
        procedureInfo.setId(ACTION_ID);
        procedureInfo.setAcdLoad("acd load");
        procedureInfo.setAfterHemodynamicsArterialPressure("after hemo AD");
        procedureInfo.setAfterHemodynamicsPulse("afte hemo puls");
        procedureInfo.setAfterHemodynamicsTemperature("afte hemo term");
        procedureInfo.setBalance("balance");
        procedureInfo.setBeforeHemodynamicsArterialPressure("before hemo AD");
        procedureInfo.setBeforeHemodynamicsPulse("before hemo puls");
        procedureInfo.setBeforeHemodynamicsTemperature("befoer hemo term");
        procedureInfo.setCaLoad("Ca Load");
        procedureInfo.setChangeInletAcRatio("change InletAcRatio");
        procedureInfo.setChangeProductVolume(0.1);
        procedureInfo.setChangeSpeed("change speed");
        procedureInfo.setChangeTbv("change Tbv");
        procedureInfo.setChangeTime("change time");
        procedureInfo.setChangeVolume(0.2);
        procedureInfo.setComplications("complications");
        procedureInfo.setContraindication("contraindication");
        procedureInfo.setFactDate(now);
        procedureInfo.setInitialInletAcRatio("init InletAcRatio");
        procedureInfo.setInitialProductVolume(0.3);
        procedureInfo.setInitialSpeed("init speed");
        procedureInfo.setInitialTbv("init tbv");
        procedureInfo.setInitialTime("init time");
        procedureInfo.setInitialVolume(0.4);
        procedureInfo.setNaClLoad("Na Cl load");
        procedureInfo.setOtherLoad("other load");
        procedureInfo.setOtherRemove("other remove");
        procedureInfo.setPackRemove("pack remove");
        procedureInfo.setTotalLoad("total load");
        procedureInfo.setTotalRemove("total remove");

        List<FinalVolume> finalVolumeList = new Vector<FinalVolume>();
        finalVolumeList.add(new FinalVolume());
        finalVolumeList.get(0).setAnticoagulantInCollect(0.5);
        finalVolumeList.get(0).setAnticoagulantInPlasma(0.6);
        finalVolumeList.get(0).setAnticoagulantVolume(0.7);
        finalVolumeList.get(0).setCollectVolume(0.8);
        finalVolumeList.get(0).setInletVolume(0.9);
        finalVolumeList.get(0).setPlasmaVolume(1.0);
        finalVolumeList.get(0).setTime(1.1);

        EritrocyteMass eritrocyteMass = new EritrocyteMass();
        eritrocyteMass.setBloodGroupId(1);
        eritrocyteMass.setExpirationDate(now);
        eritrocyteMass.setFinalHt(1.1);
        eritrocyteMass.setHt(1.2);
        eritrocyteMass.setMaker("maker");
        eritrocyteMass.setNumber("number");
        eritrocyteMass.setProductionDate(now);
        eritrocyteMass.setRhesusFactorId(0);
        eritrocyteMass.setSalineVolume(1.3);
        eritrocyteMass.setVolume(1.4);
        PatientCredentials patientCredentials = new PatientCredentials();
        final IssueResult res = wsTrfu.setProcedureResult(patientCredentials, procedureInfo, eritrocyteMass, measures, finalVolumeList);
        // TODO check data base! (table trfuOrderIssueResult)
        System.out.println("setProcedureRes return: " + res);
        Assert.assertTrue(res.isResult());
        AssertJUnit.assertTrue(res.getDescription() == null);
        AssertJUnit.assertTrue(res.getRequestId().equals(ACTION_ID));

    }

}
