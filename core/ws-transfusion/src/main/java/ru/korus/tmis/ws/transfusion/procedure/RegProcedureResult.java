package ru.korus.tmis.ws.transfusion.procedure;

import java.util.LinkedList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.RbTrfuLaboratoryMeasureTypes;
import ru.korus.tmis.core.entity.model.TrfuFinalVolume;
import ru.korus.tmis.core.entity.model.TrfuLaboratoryMeasure;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.Database;
import ru.korus.tmis.ws.transfusion.IssueResult;
import ru.korus.tmis.ws.transfusion.PropType;
import ru.korus.tmis.ws.transfusion.efive.PatientCredentials;
import ru.korus.tmis.ws.transfusion.order.SendOrderBloodComponents;
import ru.korus.tmis.ws.transfusion.order.TrfuActionProp;

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

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    private static final Logger logger = LoggerFactory.getLogger(SendOrderBloodComponents.class);

    public IssueResult save(final PatientCredentials patientCredentials, final ProcedureInfo procedureInfo, final EritrocyteMass eritrocyteMass,
            final List<LaboratoryMeasure> measures, final List<FinalVolume> finalVolume) {
        final IssueResult res = new IssueResult();
        res.setResult(false);
        final Integer requestId = procedureInfo.getId();
        res.setRequestId(requestId);
        final Action action = Database.getAction(em, requestId);

        if (action == null) { // требование КК не найдено в базе данных
            res.setDescription(String.format("The TRFU procedure for ID '%s' has been not found in MIS", "" + requestId));
            res.setResult(false);
            return res;
        }

        try {
            updateProp(action, procedureInfo, eritrocyteMass);
            updateMeasures(action, measures);
            updateFinalValue(action, finalVolume);
        } catch (final CoreException ex) {
            logger.error("Cannot update action {} property. Error description: '{}'", action.getId(), ex.getMessage());
            ex.printStackTrace();
            res.setDescription("MIS Internal error");
            return res;
        }
        action.setStatus(Database.ACTION_STATE_FINISHED);
        res.setResult(true);
        return res;
    }

    /**
     * @param action
     * @param finalVolume
     */
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
            em.persist(trfuFinalValume);
        }
        em.flush();
    }

    /**
     * @param action
     * @param measures
     */
    private void updateMeasures(final Action action, final List<LaboratoryMeasure> measures) {
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

    /**
     * @param action
     * @param requestId
     * @param procedureInfo
     * @param eritrocyteMass
     * @throws CoreException
     */
    private void updateProp(final Action action, final ProcedureInfo procedureInfo, final EritrocyteMass eritrocyteMass) throws CoreException {
        final List<PropType> propType = new LinkedList<PropType>();
        propType.addAll(SendProcedureRequest.ProcedurePropType.getPropTypes());
        final TrfuActionProp trfuActionProp = new TrfuActionProp(em, action.getActionType().getFlatCode(), propType);
        final Integer actionId = action.getId();
        final boolean update = true;

        if (procedureInfo.getFactDate() != null) {
            trfuActionProp.setProp(procedureInfo.getFactDate(), em, actionId, PropType.ORDER_ISSUE_RES_TIME, update);
            trfuActionProp.setProp(procedureInfo.getFactDate(), em, actionId, PropType.ORDER_ISSUE_RES_DATE, update);
        }

        trfuActionProp.setProp(procedureInfo.getContraindication(), em, actionId, PropType.COMPLICATIONS, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsPulse(), em, actionId, PropType.BEFORE_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsPulse(), em, actionId, PropType.AFTER_HEMODYNAMICS_PULSE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsArterialPressure(), em, actionId, PropType.BEFORE_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsArterialPressure(), em, actionId, PropType.AFTER_HEMODYNAMICS_ARTERIAL_PRESSURE, update);

        trfuActionProp.setProp(procedureInfo.getBeforeHemodynamicsTemperature(), em, actionId, PropType.BEFORE_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getAfterHemodynamicsTemperature(), em, actionId, PropType.AFTER_HEMODYNAMICS_TEMPERATURE, update);

        trfuActionProp.setProp(procedureInfo.getComplications(), em, actionId, PropType.COMPLICATIONS, update);

        trfuActionProp.setProp(procedureInfo.getInitialVolume(), em, actionId, PropType.INITIAL_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeVolume(), em, actionId, PropType.CHANGE_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getInitialTbv(), em, actionId, PropType.INITIAL_TBV, update);

        trfuActionProp.setProp(procedureInfo.getChangeTbv(), em, actionId, PropType.CHANGE_TBV, update);

        trfuActionProp.setProp(procedureInfo.getInitialSpeed(), em, actionId, PropType.INITIAL_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getChangeSpeed(), em, actionId, PropType.CHANGE_SPEED, update);

        trfuActionProp.setProp(procedureInfo.getInitialInletAcRatio(), em, actionId, PropType.INITIAL_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getChangeInletAcRatio(), em, actionId, PropType.CHANGE_INLETACRATIO, update);

        trfuActionProp.setProp(procedureInfo.getInitialTime(), em, actionId, PropType.INITIAL_TIME, update);

        trfuActionProp.setProp(procedureInfo.getChangeTime(), em, actionId, PropType.CHANGE_TIME, update);

        trfuActionProp.setProp(procedureInfo.getInitialProductVolume(), em, actionId, PropType.INITIAL_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getChangeProductVolume(), em, actionId, PropType.CHANGE_PRODUCT_VOLUME, update);

        trfuActionProp.setProp(procedureInfo.getAcdLoad(), em, actionId, PropType.ACD_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getNaClLoad(), em, actionId, PropType.NACL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getCaLoad(), em, actionId, PropType.CA_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getOtherLoad(), em, actionId, PropType.OTHER_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getTotalLoad(), em, actionId, PropType.TOTAL_LOAD, update);

        trfuActionProp.setProp(procedureInfo.getPackRemove(), em, actionId, PropType.PACK_REMOVE, update);

        trfuActionProp.setProp(eritrocyteMass.getMaker(), em, actionId, PropType.MAKER, update);

        trfuActionProp.setProp(eritrocyteMass.getNumber(), em, actionId, PropType.NUMBER, update);

        trfuActionProp.setProp(eritrocyteMass.getBloodGroupId(), em, actionId, PropType.BLOOD_GROUP_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getRhesusFactorId(), em, actionId, PropType.RHESUS_FACTOR_ID, update);

        trfuActionProp.setProp(eritrocyteMass.getVolume(), em, actionId, PropType.VOLUME_PROC_RES, update);

        trfuActionProp.setProp(eritrocyteMass.getProductionDate(), em, actionId, PropType.PRODUCTION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getExpirationDate(), em, actionId, PropType.EXPIRATION_DATE, update);

        trfuActionProp.setProp(eritrocyteMass.getHt(), em, actionId, PropType.HT, update);

        trfuActionProp.setProp(eritrocyteMass.getSalineVolume(), em, actionId, PropType.SALINE_VOLUME, update);

        trfuActionProp.setProp(eritrocyteMass.getFinalHt(), em, actionId, PropType.FINAL_HT, update);

    }
}
