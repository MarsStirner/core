package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.logging.LoggingInterceptor;

import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Author: Upatov Egor <br>
 * Date: 26.06.13, 13:58 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Interceptors(value = {LoggingInterceptor.class})
@Stateless
public class MedicalKindUnitBean implements MedicalKindUnitBeanLocal {

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    private static final String queryGetMedicalKindUnitByMedicalKind =
            "SELECT mku FROM MedicalKindUnit mku WHERE mku.eventType IS NULL AND mku.medicalKind = :MEDICALKIND";


    private String queryGetMedicalKindUnitByEventTypeAndUnitCode = "SELECT mku FROM MedicalKindUnit mku "
            +"WHERE mku.eventType.id = :eventTypeId AND mku.medicalAidUnit.code = :unitCode";


    @Override
    public List<MedicalKindUnit> getByEventType(final EventType eventType){
        return
                em.createNamedQuery("MedicalKindUnit.findByEventType", MedicalKindUnit.class)
                        .setParameter("eventType", eventType)
                        .getResultList();
    }

    @Override
    public List<MedicalKindUnit> getByMedicalKind(final RbMedicalKind kind)
            throws CoreException {
        return
                em.createQuery(queryGetMedicalKindUnitByMedicalKind, MedicalKindUnit.class)
                        .setParameter("MEDICALKIND", kind)
                        .getResultList();
    }


    @Override
    public List<MedicalKindUnit> getByEventTypeAndUnitCode(EventType eventType, String unitCode) {
        return
                em.createQuery(queryGetMedicalKindUnitByEventTypeAndUnitCode, MedicalKindUnit.class)
                        .setParameter("eventTypeId", eventType)
                        .setParameter("unitCode", unitCode)
                        .getResultList();
    }

    @Override
    public List<RbTariffType> getTariffTypeListByMedicalKindAndMedicalAidUnit(RbMedicalKind medicalKind, RbMedicalAidUnit aidUnit) {
        return em.createNamedQuery("rbTariffType.findByMedicalKindAndMedicalAidUnit", RbTariffType.class)
                .setParameter("medicalKind", medicalKind).setParameter("medicalAidUnit", aidUnit)
                .getResultList();
    }

    @Override
    public List<MedicalKindUnit> getMedicalKindUnitListByMedicalKindAndMedicalAidUnit(final  RbMedicalKind medicalKind, final RbMedicalAidUnit aidUnit) {
        return em.createNamedQuery("MedicalKindUnit.findByMedicalKindAndMedicalAidUnit", MedicalKindUnit.class)
                .setParameter("medicalKind", medicalKind).setParameter("medicalAidUnit", aidUnit)
                .getResultList();
    }

    @Override
    public RbTariffType getTariffTypeByEventTypeAndMedicalKindAndMedicalAidUnit(
            final EventType eventType,
            final RbMedicalKind medicalKind,
            final RbMedicalAidUnit aidUnit) {
        final List<MedicalKindUnit> medicalKindUnitList = em.createNamedQuery("MedicalKindUnit.findByEventTypeAndMedicalKindAndMedicalAidUnit", MedicalKindUnit.class)
                .setParameter("eventType", eventType)
                .setParameter("medicalKind", medicalKind)
                .setParameter("medicalAidUnit", aidUnit)
                .getResultList();
        // если в списке содержатся различные rbTariffType или он пуст - то возвращается NULL, иначе rbTariffType
        if(!medicalKindUnitList.isEmpty()){
            RbTariffType check = medicalKindUnitList.get(0).getTariffType();
            for(MedicalKindUnit currentMKU : medicalKindUnitList){
                if(!check.equals(currentMKU.getTariffType())){
                    return null;
                }
            }
            return check;
        }
        return null;
    }
}
