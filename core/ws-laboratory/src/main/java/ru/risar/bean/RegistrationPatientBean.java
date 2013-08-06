package ru.risar.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.*;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.validation.Validator;
import ru.risar.data.Patient;
import ru.risar.data.PatientNumber;
import ru.risar.data.validation.PatientNumberValidator;
import ru.risar.data.validation.PatientValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.risar.RegistrationPatientHelper.getClientPolicy;
import static ru.risar.RegistrationPatientHelper.getPatientFromDb;

/**
 * Сервис для сохранения информации об пациенте
 *
 * @author anosov
 *         Date: 27.07.13 21:04
 */
@Stateless
public class RegistrationPatientBean implements RegistrationPatient {
    private static final Logger log = LoggerFactory.getLogger(RegistrationPatientBean.class);
    private static final String ERROR_MESSAGE_OMS_NOT_FOUND = "Для пациента не задан полис ОМС";

    @EJB
    DbRbDocumentTypeBeanLocal dbDocumentTypeBean;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    DbPatientBeanLocal dbPatientBean;

    @EJB
    DbClientContactBeanLocal dbClientContactBean;

    @EJB
    DbClientDocumentBeanLocal dbClientDocumentBean;

    @EJB
    DbRbContactTypeBeanLocal dbContactTypeBean;

    @EJB
    DbClientPolicyBeanLocal dbClientPolicyBean;

    @EJB
    DbRbPolicyTypeBeanLocal dbRbPolicyTypeBean;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean register(final Patient patient) throws CoreException {
        assert patient != null;
//        log.debug("Save patient with name " + patient.getName().toString());
        final Validator validator = validate(patient);
        if (validator.isValid()) {
            final Object newPatient = isNewPatient(patient);
            if (newPatient != null) {
                persist(patient, ((ru.korus.tmis.core.entity.model.Patient) newPatient).getId());
            } else {
                persistNew(patient);
            }
            return true;
        } else {
            throw new CoreException(validator.getFullMessageError());
        }
    }

    private void persistNew(Patient patient) throws CoreException {
        persist(patient, -1);
    }

    private void persist(final Patient patient, Integer patientDbId) throws CoreException {
        ru.korus.tmis.core.entity.model.Patient patientDb = getPatientFromDb(dbPatientBean, patient, patientDbId);
        patientDb = update(patientDb);

        for (final PatientNumber patientNumber : patient.getPatientNumberList().getPatientNumbers()) {
            persistPatientDocuments(patientDb, patientNumber);
        }

        for (final ru.risar.data.Address address : patient.getAddressesList().getAddresses()) {
            saveAddress(address, patientDb);
        }

        saveContacts(patient, patientDb);
    }

    private void saveContacts(Patient patient, ru.korus.tmis.core.entity.model.Patient patientDb) throws CoreException {
        final ClientContact mobileClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("мобильный телефон").getId(),
                patient.getContactInfo().getMobilePhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(mobileClientContact);

        final ClientContact homeClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("домашний телефон").getId(),
                patient.getContactInfo().getHomePhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(homeClientContact);

        final ClientContact workClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("рабочий телефон").getId(),
                patient.getContactInfo().getWorkPhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(workClientContact);

        final ClientContact emailClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("e-mail").getId(),
                patient.getContactInfo().getEmailAddress(), "", patientDb, null);
        patientDb.addClientContact(emailClientContact);
    }

    private void saveAddress(ru.risar.data.Address address, ru.korus.tmis.core.entity.model.Patient patientDb) {
        final ClientAddress clientAddress = new ClientAddress();
        final ru.korus.tmis.core.entity.model.Address addressDb
                = new ru.korus.tmis.core.entity.model.Address();

        final StringBuilder addressString = new StringBuilder(address.getState()).append(", ");
        addressString.append(address.getCity()).append(", ");
        addressString.append(address.getStreet()).append(", ");
        addressString.append(address.getZip());
        addressDb.setCreateDatetime(new Date());
        addressDb.setModifyDatetime(new Date());
        addressDb.setDeleted(false);
        addressDb.setFlat("");
        final AddressHouse house = new AddressHouse();
        house.setKLADRCode("");
        house.setCreateDatetime(new Date());
        house.setModifyDatetime(new Date());
        house.setDeleted(false);
        house.setKLADRStreetCode("");
        house.setCorpus("");
        house.setNumber("");
        addressDb.setHouse(house);

        clientAddress.setFreeInput(addressString.toString());
        clientAddress.setCreateDatetime(new Date());
        clientAddress.setModifyDatetime(new Date());
        clientAddress.setDeleted(false);

        patientDb.addClientAddress(clientAddress);
    }

    private void persistPatientDocuments(ru.korus.tmis.core.entity.model.Patient patientDb, PatientNumber patientNumber) throws CoreException {
        if (patientNumber.getNumberType().equalsIgnoreCase("СНИЛС")) {
            final String snils = patientNumber.getNumber().replaceAll("[-\\s]", "");
            patientDb.setSnils(snils);
        } else if (patientNumber.getNumberType().equalsIgnoreCase("ОМС")) {
            final RbPolicyType typeOMS = getPolicyType(patientNumber);
            // todo Что такое серия и номер у ОМС где он передается, и как определяется его тип
            final ClientPolicy policy = dbClientPolicyBean.findBySerialAndNumberAndType(patientNumber.getPolicySerial(), patientNumber.getNumber(), typeOMS.getId());
            ClientPolicy clientPolicyDb;
            if (policy == null) {
                clientPolicyDb = getClientPolicy(dbClientPolicyBean, patientDb, patientNumber, typeOMS, -1);
                insert(clientPolicyDb);
                patientDb.addClientPolicies(clientPolicyDb);
                update(clientPolicyDb);
            } else {
                clientPolicyDb = getClientPolicy(dbClientPolicyBean, patientDb, patientNumber, typeOMS, policy.getId());
                update(clientPolicyDb);
            }
            em.flush();
        } else {
            final RbDocumentType type = dbDocumentTypeBean.findByName(patientNumber.getNumberType());
            if (type != null) {
                final String[] serialAndNumber
                        = patientNumber.getNumber().split(" ");
                final ClientDocument clientDocument = dbClientDocumentBean.insertOrUpdateClientDocument(
                        -1, type.getId(), "", serialAndNumber[1], serialAndNumber[0], null, null, patientDb, null
                );
                patientDb.addClientDocument(clientDocument);
                update(patientDb);
            }
        }
    }


    Object isNewPatient(final Patient patient) throws CoreException {
        final PatientNumber oms = getOMS(patient);
        final RbPolicyType policyType = getPolicyType(oms);

        final Map<String, String> params = new HashMap<String, String>();
        params.put("lastName", patient.getName().getFamilyName());
        params.put("firstName", patient.getName().getGivenName());
        params.put("patrName", patient.getName().getMiddleName());
        params.put("sex", patient.getGender().codeShort().toString());
        params.put("birthDate", patient.getBirthTime().getTime() + "");

        final List<ru.korus.tmis.core.entity.model.Patient> patientByPolicy
                = dbPatientBean.findPatientByPolicy(params, oms.getPolicySerial(), oms.getNumber(), policyType.getId());
        if (patientByPolicy == null || patientByPolicy.isEmpty()) {
            return null;
        } else {
            return patientByPolicy.get(0);
        }
    }


    private PatientNumber getOMS(final Patient patient) throws CoreException {
        for (final PatientNumber patientNumber : patient.getPatientNumberList().getPatientNumbers()) {
            final String numberType = patientNumber.getNumberType();
            if (numberType.equalsIgnoreCase("ОМС")) {
                return patientNumber;
            }
        }
        throw new CoreException(ERROR_MESSAGE_OMS_NOT_FOUND);
    }

    private RbPolicyType getPolicyType(final PatientNumber oms) throws CoreException {
        final Iterable<RbPolicyType> policyTypes = dbRbPolicyTypeBean.getAllRbPolicyTypes();
        for (RbPolicyType policyType : policyTypes) {
            if (oms.getPolicyType().equalsIgnoreCase(policyType.getName())) {
                return policyType;
            }
        }
        throw new CoreException(ERROR_MESSAGE_OMS_NOT_FOUND);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    private void insert(Object o) {
        em.persist(o);
    }

    private <T> T update(T o) {
        return em.merge(o);
    }

    /**
     * Валидация входных данных
     *
     * @param patient - входные данные
     * @return validator - объект хранящий информацию о валидации данных
     */
    @Override
    public Validator validate(final Patient patient) {
        final Validator validator = new Validator();

        final PatientValidator patientValidator = initializePatientValidator(validator);
        patientValidator.validateName(patient.getName());
        patientValidator.validateGender(patient.getGender());
        patientValidator.validateBirthTime(patient.getBirthTime());
        patientValidator.validatePatientNumbers(patient.getPatientNumberList().getPatientNumbers());

        final PatientNumberValidator patientNumberValidator = initializePatientNumberValidator(validator);
        patientNumberValidator.validateNumbers(patient.getPatientNumberList().getPatientNumbers());

        return validator;
    }

    private PatientValidator initializePatientValidator(final Validator validator) {
        return new PatientValidator(validator);
    }

    private PatientNumberValidator initializePatientNumberValidator(final Validator validator) {
        final PatientNumberValidator patientNumberValidator = new PatientNumberValidator(validator);
        final List<RbDocumentType> documentTypes;
        final List<RbPolicyType> policyTypes;
        try {
            documentTypes = (List<RbDocumentType>) dbDocumentTypeBean.findAllRbDocumentTypes();
            patientNumberValidator.initializeDocumentDictionary(documentTypes);

            policyTypes = (List<RbPolicyType>) dbRbPolicyTypeBean.getAllRbPolicyTypes();
            patientNumberValidator.initializePolicyTypeDictionary(policyTypes);
        } catch (CoreException e) {
            // TODO..
        }
        return patientNumberValidator;
    }

}
