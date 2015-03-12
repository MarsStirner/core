package ru.korus.tmis.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.Patient;

import ru.korus.tmis.core.database.common.DbUUIDBeanLocal;
import ru.korus.tmis.core.service.interfaces.PatientService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author anosov
 *         Date: 28.08.13 23:34
 */
@Stateless
public class PatientServiceBean implements PatientService {
    private static final Logger log = LoggerFactory.getLogger(PatientServiceBean.class);

    @EJB
    DbUUIDBeanLocal dbUUIDBeanLocal;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;


    @Override
    public Patient save(Patient patient) {
        patient.setUuid(dbUUIDBeanLocal.createUUID());
        patient.setDeleted(false);
        patient.setCreateDatetime(new Date());
        patient.setModifyDatetime(new Date());
        em.persist(patient);
        return patient;
    }

    @Override
    public Patient get(Long id) {
        final Patient patient = em.find(Patient.class, id);
        if (patient == null) {
            final String errorString = "Not found Staff by ID [" + id + "]";
            log.error(errorString);
            throw new IllegalStateException(errorString);
        }
        return patient;
    }

    @Override
    public void delete(Patient patient, boolean flush) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    @Override
    public void delete(Patient patient) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public static class Query extends PatientService.Query {
        public final static String FIND_BY_POLICY =
                "SELECT DISTINCT patient FROM Patient patient INNER JOIN patient.clientPolicies policy " +
                        "WHERE patient.deleted = false AND policy.number = :POLICYNUMBER  " +
                        "AND policy.serial = :POLICYSERIAL AND policy.policyType.id = :POLICYTYPEID AND policy.deleted = false ";

        public final static String FIND_BY_SNILS =
                "SELECT DISTINCT patient FROM Patient patient WHERE patient.deleted = false AND patient.snils = :SNILS ";


        public final static String FIND_BY_PASSPORT =
                "SELECT DISTINCT patient FROM Patient patient " +
                        "INNER JOIN patient.clientDocuments document " +
                        "JOIN RbDocumentType documentType " +
                        "WHERE patient.deleted = false " +
                        "AND document.number = :DOCNUMBER " +
                        "AND document.serial = :DOCSERIAL " +
                        "AND documentType.name = 'ПАСПОРТ РФ' " +
                        "AND document.documentType.id = documentType.id " +
                        "AND document.deleted = false";

        public final static String FIND_BY_BIRTH_CERTIFICATE =
                "SELECT DISTINCT patient FROM Patient patient " +
                        "INNER JOIN patient.clientDocuments document " +
                        "JOIN RbDocumentType documentType " +
                        "WHERE patient.deleted = false " +
                        "AND document.number = :DOCNUMBER " +
                        "AND document.serial = :DOCSERIAL " +
                        "AND documentType.name = 'СВИД О РОЖД' " +
                        "AND document.documentType.id = documentType.id " +
                        "AND document.deleted = false";
    }


    @Override
    public Patient findByPolicy(String number, String serial, Integer typeId) {
        final TypedQuery<Patient> query = em.createQuery(Query.FIND_BY_POLICY, Patient.class);
        query.setParameter("POLICYNUMBER", number);
        query.setParameter("POLICYSERIAL", serial);
        query.setParameter("POLICYTYPEID", typeId);
        return getPatientOrNull(query);
    }

    @Override
    public Patient findBySnils(String number) {
        final TypedQuery<Patient> query = em.createQuery(Query.FIND_BY_SNILS, Patient.class);
        query.setParameter("SNILS", number);
        return getPatientOrNull(query);
    }

    @Override
    public Patient findByPassport(String serial, String number) {
        final TypedQuery<Patient> query =
                em.createQuery(Query.FIND_BY_PASSPORT, Patient.class);
        query.setParameter("DOCSERIAL", serial);
        query.setParameter("DOCNUMBER", number);
        return getPatientOrNull(query);
    }

    @Override
    public Patient findByBirthCertificate(String serial, String number) {
        final TypedQuery<Patient> query =
                em.createQuery(Query.FIND_BY_BIRTH_CERTIFICATE, Patient.class);
        query.setParameter("DOCSERIAL", serial);
        query.setParameter("DOCNUMBER", number);
        return getPatientOrNull(query);
    }

    private Patient getPatientOrNull(TypedQuery<Patient> query) {
        final List<Patient> patients = query.getResultList();
        if (patients.isEmpty() || patients.size() > 1) {
            return null;
        } else {
            return em.merge(patients.get(0));
        }
    }
}
