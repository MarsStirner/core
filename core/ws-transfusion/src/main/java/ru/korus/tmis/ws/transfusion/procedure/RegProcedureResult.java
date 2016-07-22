package ru.korus.tmis.ws.transfusion.procedure;

import java.util.Arrays;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbTrfuLaboratoryMeasureTypes;
import ru.korus.tmis.core.entity.model.TrfuFinalVolume;
import ru.korus.tmis.core.entity.model.TrfuLaboratoryMeasure;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.IssueResult;
import ru.korus.tmis.ws.transfusion.Constants;
//import ru.korus.tmis.ws.transfusion.TrfuActionProp;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.order.RegOrderIssueResult;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        21.02.2013, 16:16:25 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
@Stateless
public class RegProcedureResult {

    @EJB
    private Database database;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);
   /*
    public IssueResult save(final PatientCredentials patientCredentials, final ProcedureInfo procedureInfo, final EritrocyteMass eritrocyteMass,
            final List<LaboratoryMeasure> measures, final List<FinalVolume> finalVolume) {
        final IssueResult res = new IssueResult();
        res.setResult(false);
        final Integer requestId = procedureInfo.getId();
        res.setRequestId(requestId);
        final Action action = database.getEntityMgr().find(Action.class, requestId);

        if (action == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The TRFU procedure for ID '%s' has been not found in MIS", "" + requestId));
            res.setResult(false);
            return res;
        }

        if (procedureInfo.getFactDate() == null ) {
            res.setDescription("The procedure factDate is null");
            res.setResult(false);
            return res;
        }

        try {
            if ( !updateProp(action, procedureInfo, eritrocyteMass) ){
                res.setDescription("The result has been already set");
                return res;
            }
            updateMeasures(action, measures);
            updateFinalValue(action, finalVolume);
        } catch (final CoreException ex) {
            logger.error("Cannot update action {} property. Error description: '{}'", action.getId(), ex.getMessage());
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }
        res.setResult(true);
        return res;
    }

    private void updateFinalValue(final Action action, final List<FinalVolume> finalVolumes) {
        for (final FinalVolume finalVolume : finalVolumes) {
            final TrfuFinalVolume trfuFinalValume = new TrfuFinalVolume();
            trfuFinalValume.setAction(action);
            trfuFinalValume.setTime(finalVolume.getTime());
            trfuFinalValume.setAnticoagulantVolume(finalVolume.getAnticoagulantVolume());
            trfuFinalValume.setInletVolume(finalVolume.getInletVolume());
            trfuFinalValume.setPlasmaVolume(finalVolume.getPlasmaVolume());
            trfuFinalValume.setCollectVolume(finalVolume.getCollectVolume());
            trfuFinalValume.setAnticoagulantInCollect(finalVolume.getAnticoagulantInCollect());
            trfuFinalValume.setAnticoagulantInPlasma(finalVolume.getAnticoagulantInPlasma());
            database.getEntityMgr().persist(trfuFinalValume);
        }
        database.getEntityMgr().flush();
    }

    /**
     * @param action
     * @param measures
     */
    private void updateMeasures(final Action action, final List<LaboratoryMeasure> measures) {
        EntityManager em = database.getEntityMgr();
        for (final LaboratoryMeasure curMeasure : measures) {
            final TrfuLaboratoryMeasure trfuLaboratoryMeasure = new TrfuLaboratoryMeasure();
            trfuLaboratoryMeasure.setAction(action);
            trfuLaboratoryMeasure.setBeforeOperation(curMeasure.getBeforeOperation());
            trfuLaboratoryMeasure.setDuringOperation(curMeasure.getDuringOperation());
            trfuLaboratoryMeasure.setInProduct(curMeasure.getInProduct());
            trfuLaboratoryMeasure.setAfterOperation(curMeasure.getAfterOperation());
            if (curMeasure.getId() != null) {
                final List<RbTrfuLaboratoryMeasureTypes> types = em.createQuery("SELECT m FROM RbTrfuLaboratoryMeasureTypes m WHERE m.trfuId = :curTrfuId",
                        RbTrfuLaboratoryMeasureTypes.class).setParameter("curTrfuId", curMeasure.getId()).getResultList();
                if (types.isEmpty()) {
                    logger.error("Cannot find the TRFU laboratory measure type: {}. Action {}", curMeasure.getId(), action.getId());
                } else {
                    trfuLaboratoryMeasure.setRbTrfulaboratoryMeasureTypes(types.get(0));
                }
            } else {
                logger.error("The TRFU laboratory measure type is null for action {}", action.getId());
            }
            em.persist(trfuLaboratoryMeasure);
        }
        em.flush();
    }
    /*
    private boolean updateProp(final Action action, final ProcedureInfo procedureInfo, final EritrocyteMass eritrocyteMass) throws CoreException {
        final TrfuActionProp trfuActionProp =
                new TrfuActionProp(database, action.getActionType().getFlatCode(), Arrays.asList(SendProcedureRequest.propTypes));
        final Integer actionId = action.getId();
        final boolean update = true;

        if (RegOrderIssueResult.alreadySet(actionId, trfuActionProp))  {
            return false;
        }

        trfuActionProp.setProp(procedureInfo.getFactDate(), actionId, Constants.ORDER_ISSUE_RES_TIME, update);

        trfuActionProp.setProp(procedureInfo.getFactDate(), actionId, Constants.ORDER_ISSUE_RES_DATE, update);

        trfuActionProp.setProp(procedureInfo.getContraindication(), actionId, Constants.CONTRAINDICATION, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsPulse(), actionId, Constants.BEFORE_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsPulse(), actionId, Constants.AFTER_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsArterialPressure(), actionId, Constants.BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsArterialPressure(), actionId, Constants.AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsTemperature(), actionId, Constants.BEFORE_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsTemperature(), actionId, Constants.AFTER_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getComplications(), actionId, Constants.COMPLICATIONS, update);

        trfuActionProp.setProp(procedureInfo.getInitialVolume(), actionId, Constants.INITIAL_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeVolume(), actionId, Constants.CHANGE_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getInitialTbv(), actionId, Constants.INITIAL_TBV, update);

        trfuActionProp.setProp(procedureInfo.getChangeTbv(), actionId, Constants.CHANGE_TBV, update);

        trfuActionProp.setProp(procedureInfo.getInitialSpeed(), actionId, Constants.INITIAL_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getChangeSpeed(), actionId, Constants.CHANGE_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getInitialInletAcRatio(), actionId, Constants.INITIAL_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getChangeInletAcRatio(), actionId, Constants.CHANGE_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getInitialTime(), actionId, Constants.INITIAL_TIME, update);

        trfuActionProp.setProp(procedureInfo.getChangeTime(), actionId, Constants.CHANGE_TIME, update);

        trfuActionProp.setProp(procedureInfo.getInitialProductVolume(), actionId, Constants.INITIAL_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeProductVolume(), actionId, Constants.CHANGE_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getAcdLoad(), actionId, Constants.ACD_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getNaClLoad(), actionId, Constants.NACL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getCaLoad(), actionId, Constants.CA_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getOtherLoad(), actionId, Constants.OTHER_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getTotalLoad(), actionId, Constants.TOTAL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getPackRemove(), actionId, Constants.PACK_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getOtherRemove(), actionId, Constants.OTHER_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getTotalRemove(), actionId, Constants.TOTAL_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getBalance(), actionId, Constants.BALANCE, update);

        trfuActionProp.setProp(eritrocyteMass.getMaker(), actionId, Constants.MAKER, update);

        trfuActionProp.setProp(eritrocyteMass.getNumber(), actionId, Constants.NUMBER, update);

        trfuActionProp.setProp(eritrocyteMass.getBloodGroupId(), actionId, Constants.BLOOD_GROUP_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getRhesusFactorId(), actionId, Constants.RHESUS_FACTOR_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getVolume(), actionId, Constants.VOLUME_PROC_RES, update);

        trfuActionProp.setProp(eritrocyteMass.getProductionDate(), actionId, Constants.PRODUCTION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getExpirationDate(), actionId, Constants.EXPIRATION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getHt(), actionId, Constants.HT, update);

        trfuActionProp.setProp(eritrocyteMass.getSalineVolume(), actionId, Constants.SALINE_VOLUME, update);

        trfuActionProp.setProp(eritrocyteMass.getFinalHt(), actionId, Constants.FINAL_HT, update);

        trfuActionProp.setProp(actionId, actionId, Constants.LAB_MEASURE, update);

        trfuActionProp.setProp(actionId, actionId, Constants.FINAL_VOLUME, update);

        return true;
    }
    */
}
