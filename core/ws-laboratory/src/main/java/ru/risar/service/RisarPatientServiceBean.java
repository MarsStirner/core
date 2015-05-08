package ru.risar.service;

import ru.korus.tmis.core.entity.model.AccountingSystem;
import ru.korus.tmis.core.entity.model.ClientIdentification;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.service.interfaces.PatientService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author anosov
 *         Date: 28.08.13 23:56
 */
@Stateless
public class RisarPatientServiceBean implements RisarPatientService {

    @EJB
    private PatientService delegate;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em;

    @Override
    public Patient save(Patient patient) {
        return delegate.save(patient);
    }

    @Override
    public Patient get(Long id) {
        return delegate.get(id);
    }

    @Override
    public PatientService delegate() {
        return delegate;
    }

    @Override
    public void delete(Patient patient, boolean flush) {
        delegate.delete(patient, flush);
    }

    @Override
    public void delete(Patient patient) {
        delegate.delete(patient);
    }

    @Override
    public Patient findByPolicy(String number, String serial, Integer typeId) {
        return delegate.findByPolicy(number, serial, typeId);
    }

    @Override
    public Patient findBySnils(String number) {
        return delegate.findBySnils(number);
    }

    @Override
    public Patient findByPassport(String serial, String number) {
        return delegate.findByPassport(serial, number);
    }

    @Override
    public Patient findByBirthCertificate(String serial, String number) {
        return delegate.findByBirthCertificate(serial, number);
    }

    public static class Query extends PatientService.Query {
        public final static String FIND_CLIENT_IDENTIFIER =
                "SELECT clientident FROM ClientIdentification clientident WHERE clientident.identifier = :IDENTIFIER";
    }


    //    @Override
//    public Patient findClientIdentificator(String identifier) {
//        final javax.persistence.Query query = em.createNativeQuery(Query.FIND_BY_IDENTIFIER, Patient.class);
//        return getPatientOrNull(query);
//    }
    @Override
    public ClientIdentification findClientIdentificator(String identifier) {
        final TypedQuery<ClientIdentification> query =
                em.createQuery(Query.FIND_CLIENT_IDENTIFIER, ClientIdentification.class);
        query.setParameter("IDENTIFIER", identifier);
        final List<ClientIdentification> clientIdentication = query.getResultList();
        if (clientIdentication.isEmpty() || clientIdentication.size() > 1) {
            return null;
        } else {
            return em.merge(clientIdentication.get(0));
        }
    }

    @Override
    public Patient findBySnilsAndBirthdate(String number, Date birthdate) {
        final Patient patient = delegate.findBySnils(number);
        if (patient != null && patient.getBirthDate().getTime() == birthdate.getTime()) {
            return patient;
        } else {
            return null;
        }
    }

    @Override
    public Patient findByBirthCertificateAndBirthdate(String serial, String number, Date birthdate) {
        final Patient patient = delegate.findByBirthCertificate(serial, number);
        if (patient != null && patient.getBirthDate().getTime() == birthdate.getTime()) {
            return patient;
        } else {
            return null;
        }
    }

    @Override
    public Patient findByPassportAndBirthdate(String serial, String number, Date birthdate) {
        final Patient patient = delegate.findByPassport(serial, number);
        if (patient != null && patient.getBirthDate().getTime() == birthdate.getTime()) {
            return patient;
        } else {
            return null;
        }
    }

    @Override
    public void createPatientIdentifier(Patient patient, String identifier) {
        final ClientIdentification identification = new ClientIdentification();
        identification.setClient(patient);
        identification.setIdentifier(identifier);
        identification.setCreateDatetime(new Date());
        identification.setModifyDatetime(new Date());
        identification.setDeleted(false);
        identification.setCheckDate(new Date());
        final AccountingSystem accountingSystem = findOrCreateAccountingSystem();
        identification.setAccountingSystem(accountingSystem);
        em.persist(identification);
    }

    private AccountingSystem findOrCreateAccountingSystem() {
        final TypedQuery<AccountingSystem> query =
                em.createQuery("SELECT accountingSystem FROM AccountingSystem accountingSystem WHERE accountingSystem.code = 'rs'", AccountingSystem.class);
        AccountingSystem accountingSystem = query.getResultList().size() > 0 ? query.getSingleResult() : null;
        if (accountingSystem == null) {
            accountingSystem = new AccountingSystem();
            accountingSystem.setCode("rs");
            accountingSystem.setName("Рисар");
            accountingSystem.setEditable(false);
            accountingSystem.setShowInClientInfo(false);
            em.persist(accountingSystem);
        }
        return accountingSystem;
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