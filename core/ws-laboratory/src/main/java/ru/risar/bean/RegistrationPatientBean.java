package ru.risar.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbClientContactBeanLocal;
import ru.korus.tmis.core.database.DbClientDocumentBeanLocal;
import ru.korus.tmis.core.database.DbClientPolicyBeanLocal;
import ru.korus.tmis.core.database.common.DbPatientBeanLocal;
import ru.korus.tmis.core.database.DbRbContactTypeBeanLocal;
import ru.korus.tmis.core.database.DbRbDocumentTypeBeanLocal;
import ru.korus.tmis.core.database.DbRbPolicyTypeBeanLocal;
import ru.korus.tmis.core.entity.model.AddressHouse;
import ru.korus.tmis.core.entity.model.ClientAddress;
import ru.korus.tmis.core.entity.model.ClientContact;
import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.ClientIdentification;
import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.RbDocumentType;
import ru.korus.tmis.core.entity.model.RbPolicyType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.validation.Validator;
import ru.risar.data.ContactInfo;
import ru.risar.data.Patient;
import ru.risar.data.PatientNumber;
import ru.risar.data.validation.PatientNumberValidator;
import ru.risar.data.validation.PatientValidator;
import ru.risar.exception.RisarCoreException;
import ru.risar.service.RisarPatientService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static ru.risar.RegistrationPatientHelper.getClientPolicy;

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

    @EJB
    RisarPatientService patientService;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean register(final Patient patientParams) throws CoreException {
        assert patientParams != null;
//        log.debug("Save patientParams with name " + patientParams.getName().toString());
        final Validator validator = validate(patientParams);
        if (validator.isValid()) {
            final ru.korus.tmis.core.entity.model.Patient patient = findPatient(patientParams);
            if (patient == null) {
                createNewPatient(patientParams);
            }
            return true;
        } else {
            final RisarCoreException exception = new RisarCoreException();
            exception.description = validator.getFullMessageError();
            throw exception;
        }
    }

    private void createNewPatient(Patient patientParams) throws CoreException {
        final ru.korus.tmis.core.entity.model.Patient patient = new ru.korus.tmis.core.entity.model.Patient();

        setGeneralInfo(patient, patientParams);

        patientService.save(patient);

        setDocumentInfo(patient, patientParams);
        setContactInfo(patient, patientParams);
        setAddressInfo(patient, patientParams);

        if (patientParams.getIdentifier() != null) {
            setIdentifier(patient, patientParams.getIdentifier());
        }
    }

    private void setIdentifier(ru.korus.tmis.core.entity.model.Patient patient, String identifier) {
        patientService.createPatientIdentifier(patient,identifier);
    }

    private void setGeneralInfo(ru.korus.tmis.core.entity.model.Patient patient, Patient patientParams) {
        patient.setFirstName(patientParams.getName().getGivenName());
        patient.setPatrName(patientParams.getName().getMiddleName());
        patient.setLastName(patientParams.getName().getFamilyName());
        patient.setBirthPlace("");
        patient.setBloodNotes("");
        patient.setHeight("");
        patient.setWeight("");
        patient.setNotes("");
        patient.setBirthDate(patientParams.getBirthTime());
        patient.setSex(patientParams.getGender().codeShort());
    }

    private void setDocumentInfo(ru.korus.tmis.core.entity.model.Patient patient, Patient patientParams) throws CoreException {
        for (final PatientNumber patientNumber : patientParams.getPatientNumberList().getPatientNumbers()) {
            persistPatientDocuments(patient, patientNumber);
        }
    }

    private void setContactInfo(ru.korus.tmis.core.entity.model.Patient patient, Patient patientParams) throws CoreException {
        final ContactInfo contacts = patientParams.getContactInfo();
        saveContacts(contacts, patient);
    }


    private void setAddressInfo(ru.korus.tmis.core.entity.model.Patient patient, Patient patientParams) {
        for (final ru.risar.data.Address address : patientParams.getAddressesList().getAddresses()) {
            saveAddress(address, patient);
        }
    }


    private ru.korus.tmis.core.entity.model.Patient findPatient(final Patient patientParams) throws CoreException {
        ru.korus.tmis.core.entity.model.Patient patient = null;
        if (patientParams.getIdentifier() != null) {
            final ClientIdentification clientIdentificator = patientService.findClientIdentificator(patientParams.getIdentifier());
            patient = clientIdentificator.getPatient();
        }
        if (patient == null) {
            patient = findByOneOfDocumentAndBirthdate(patientParams.getPatientNumberList().getPatientNumbers(), patientParams.getBirthTime());
        }
        return patient;
    }

    private ru.korus.tmis.core.entity.model.Patient findByOneOfDocumentAndBirthdate(final List<PatientNumber> patientNumbers, Date birthdate) {
        for (final PatientNumber patientNumber : patientNumbers) {
            if (patientNumber.getNumberType().equalsIgnoreCase("СНИЛС")) {
                final String snils = patientNumber.getNumber().replaceAll("[-\\s]", "");
                return patientService.findBySnilsAndBirthdate(snils, birthdate);
            }
        }
        for (final PatientNumber patientNumber : patientNumbers) {
            if (patientNumber.getNumberType().equalsIgnoreCase("СВИД О РОЖД")) {
                DocumentNumber documentNumber = new DocumentNumber(patientNumber).parse();
                String serial = documentNumber.getSerial();
                String number = documentNumber.getNumber();
                return patientService.findByBirthCertificateAndBirthdate(serial, number, birthdate);
            }
        }
        for (final PatientNumber patientNumber : patientNumbers) {
            if (patientNumber.getNumberType().equalsIgnoreCase("ПАСПОРТ РФ")) {
                DocumentNumber documentNumber = new DocumentNumber(patientNumber).parse();
                String serial = documentNumber.getSerial();
                String number = documentNumber.getNumber();
                return patientService.findByPassportAndBirthdate(serial, number, birthdate);
            } else {
                return null;
            }
        }
        return null;
    }

    private void saveContacts(ContactInfo contactInfo, ru.korus.tmis.core.entity.model.Patient patientDb) throws CoreException {
        final ClientContact mobileClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("мобильный телефон").getId(),
                contactInfo.getMobilePhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(mobileClientContact);

        final ClientContact homeClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("домашний телефон").getId(),
                contactInfo.getHomePhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(homeClientContact);

        final ClientContact workClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("рабочий телефон").getId(),
                contactInfo.getWorkPhoneNumber(), "", patientDb, null);
        patientDb.addClientContact(workClientContact);

        final ClientContact emailClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("e-mail").getId(),
                contactInfo.getEmailAddress(), "", patientDb, null);
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
            } else {
                getClientPolicy(dbClientPolicyBean, patientDb, patientNumber, typeOMS, policy.getId());
            }
        } else {
            final RbDocumentType type = dbDocumentTypeBean.findByName(patientNumber.getNumberType());
            if (type != null) {
                final String[] serialAndNumber
                        = patientNumber.getNumber().split(" ");
                final ClientDocument clientDocument = dbClientDocumentBean.insertOrUpdateClientDocument(
                        -1, type.getId(), "", serialAndNumber[1], serialAndNumber[0], null, null, patientDb, null
                );
                patientDb.addClientDocument(clientDocument);
            }
        }
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

    /**
     * Объект для хранения и оперирование документами пациентами
     */
    private class DocumentNumber {
        private PatientNumber patientNumber;
        private String serial;
        private String number;

        public DocumentNumber(PatientNumber patientNumber) {
            this.patientNumber = patientNumber;
        }

        public String getSerial() {
            return serial;
        }

        public String getNumber() {
            return number;
        }

        public DocumentNumber parse() {
            final String[] serialAndNumber
                    = patientNumber.getNumber().split(" ");
            serial = serialAndNumber[0];
            number = serialAndNumber[1];
            return this;
        }
    }
}
