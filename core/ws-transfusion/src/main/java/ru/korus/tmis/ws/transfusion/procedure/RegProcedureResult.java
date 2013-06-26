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
import ru.korus.tmis.ws.transfusion.PropType;
import ru.korus.tmis.ws.transfusion.TrfuActionProp;
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
                    trfuLaboratoryMeasure.setRbTrfulaboratoryMeasureTypes(types.iterator().next());
                }
            } else {
                logger.error("The TRFU laboratory measure type is null for action {}", action.getId());
            }
            em.persist(trfuLaboratoryMeasure);
        }
        em.flush();
    }

    private boolean updateProp(final Action action, final ProcedureInfo procedureInfo, final EritrocyteMass eritrocyteMass) throws CoreException {
        final TrfuActionProp trfuActionProp =
                new TrfuActionProp(database, action.getActionType().getFlatCode(), Arrays.asList(SendProcedureRequest.propTypes));
        final Integer actionId = action.getId();
        final boolean update = true;

        if (RegOrderIssueResult.alreadySet(actionId, trfuActionProp))  {
            return false;
        }

        trfuActionProp.setProp(procedureInfo.getFactDate(), actionId, PropType.ORDER_ISSUE_RES_TIME, update);

        trfuActionProp.setProp(procedureInfo.getFactDate(), actionId, PropType.ORDER_ISSUE_RES_DATE, update);

        trfuActionProp.setProp(procedureInfo.getContraindication(), actionId, PropType.CONTRAINDICATION, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsPulse(), actionId, PropType.BEFORE_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsPulse(), actionId, PropType.AFTER_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsArterialPressure(), actionId, PropType.BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsArterialPressure(), actionId, PropType.AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsTemperature(), actionId, PropType.BEFORE_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsTemperature(), actionId, PropType.AFTER_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getComplications(), actionId, PropType.COMPLICATIONS, update);

        trfuActionProp.setProp(procedureInfo.getInitialVolume(), actionId, PropType.INITIAL_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeVolume(), actionId, PropType.CHANGE_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getInitialTbv(), actionId, PropType.INITIAL_TBV, update);

        trfuActionProp.setProp(procedureInfo.getChangeTbv(), actionId, PropType.CHANGE_TBV, update);

        trfuActionProp.setProp(procedureInfo.getInitialSpeed(), actionId, PropType.INITIAL_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getChangeSpeed(), actionId, PropType.CHANGE_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getInitialInletAcRatio(), actionId, PropType.INITIAL_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getChangeInletAcRatio(), actionId, PropType.CHANGE_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getInitialTime(), actionId, PropType.INITIAL_TIME, update);

        trfuActionProp.setProp(procedureInfo.getChangeTime(), actionId, PropType.CHANGE_TIME, update);

        trfuActionProp.setProp(procedureInfo.getInitialProductVolume(), actionId, PropType.INITIAL_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeProductVolume(), actionId, PropType.CHANGE_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getAcdLoad(), actionId, PropType.ACD_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getNaClLoad(), actionId, PropType.NACL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getCaLoad(), actionId, PropType.CA_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getOtherLoad(), actionId, PropType.OTHER_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getTotalLoad(), actionId, PropType.TOTAL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getPackRemove(), actionId, PropType.PACK_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getOtherRemove(), actionId, PropType.OTHER_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getTotalRemove(), actionId, PropType.TOTAL_REMOVE, update);

        trfuActionProp.setProp(procedureInfo.getBalance(), actionId, PropType.BALANCE, update);

        trfuActionProp.setProp(eritrocyteMass.getMaker(), actionId, PropType.MAKER, update);

        trfuActionProp.setProp(eritrocyteMass.getNumber(), actionId, PropType.NUMBER, update);

        trfuActionProp.setProp(eritrocyteMass.getBloodGroupId(), actionId, PropType.BLOOD_GROUP_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getRhesusFactorId(), actionId, PropType.RHESUS_FACTOR_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getVolume(), actionId, PropType.VOLUME_PROC_RES, update);

        trfuActionProp.setProp(eritrocyteMass.getProductionDate(), actionId, PropType.PRODUCTION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getExpirationDate(), actionId, PropType.EXPIRATION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getHt(), actionId, PropType.HT, update);

        trfuActionProp.setProp(eritrocyteMass.getSalineVolume(), actionId, PropType.SALINE_VOLUME, update);

        trfuActionProp.setProp(eritrocyteMass.getFinalHt(), actionId, PropType.FINAL_HT, update);

        trfuActionProp.setProp(actionId, actionId, PropType.LAB_MEASURE, update);

        trfuActionProp.setProp(actionId, actionId, PropType.FINAL_VOLUME, update);

        return true;
    }
}
