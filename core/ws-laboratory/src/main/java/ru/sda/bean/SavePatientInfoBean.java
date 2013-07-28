package ru.sda.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbClientContactBeanLocal;
import ru.korus.tmis.core.database.DbPatientBeanLocal;
import ru.korus.tmis.core.database.DbRbContactTypeBeanLocal;
import ru.korus.tmis.core.database.DbRbDocumentTypeBeanLocal;
import ru.korus.tmis.core.entity.model.ClientContact;
import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.RbDocumentType;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.validation.Validator;
import ru.sda.data.Patient;
import ru.sda.data.PatientNumber;
import ru.sda.data.validation.PatientNumberValidator;
import ru.sda.data.validation.PatientValidator;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Сервис для сохранения информации об пациенте
 *
 * @author anosov
 *         Date: 27.07.13 21:04
 */
@Stateless
public class SavePatientInfoBean implements SavePatientInfo {
    private static final Logger log = LoggerFactory.getLogger(SavePatientInfoBean.class);

    @EJB
    DbRbDocumentTypeBeanLocal dbDocumentTypeBean;

    @PersistenceContext(unitName = "s11r64")
    private EntityManager em = null;

    @EJB
    DbPatientBeanLocal dbPatientBean;

    @EJB
    DbClientContactBeanLocal dbClientContactBean;

    @EJB
    DbRbContactTypeBeanLocal dbContactTypeBean;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean save(final Patient patient) throws CoreException {
        assert patient != null;
//        log.debug("Save patient with name " + patient.getName().toString());
        final Validator validator = validate(patient);
        if (validator.isValid()) {
            persist(patient);
            return true;
        } else {
            throw new CoreException(validator.getFullMessageError());
        }
    }

    private void persist(final Patient patient) throws CoreException {
        final ru.korus.tmis.core.entity.model.Patient patientDb
                = new ru.korus.tmis.core.entity.model.Patient();
        patientDb.setFirstName(patient.getName().getGivenName());
        patientDb.setLastName(patient.getName().getFamilyName());
        patientDb.setPatrName(patient.getName().getMiddleName());
        patientDb.setBirthDate(patient.getBirthTime());
        patientDb.setSex(patient.getGender().codeShort());

        for (final PatientNumber patientNumber : patient.getPatientNumbers().getPatientNumbers()) {
            if (patientNumber.getNumberType().equalsIgnoreCase("СНИЛС")) {
                patientDb.setSnils(patientNumber.getNumber());
            } else if (patientNumber.getNumberType().equalsIgnoreCase("ОМС")) {
                final ClientPolicy clientPolicy = new ClientPolicy();
                clientPolicy.setSerial(patientNumber.getNumber());
                clientPolicy.setNumber(patientNumber.getNumber());
                insert(clientPolicy); //todo...
                patientDb.addClientPolicies(clientPolicy);
            } else {
                final ClientDocument clientDocument = new ClientDocument();
                final RbDocumentType type = dbDocumentTypeBean.findByName(patientNumber.getNumberType());
                if (type != null) {
                    clientDocument.setDocumentType(type);
                    final String[] serialAndNumber
                            = clientDocument.getNumber().split(" ");
                    clientDocument.setSerial(serialAndNumber[0]);
                    clientDocument.setNumber(serialAndNumber[1]);
                    insert(clientDocument); //todo...

                    patientDb.addClientDocument(clientDocument);
                }
            }
        }

//todo...
//        for (final Address address : patient.getAddresses()) {
//            final ClientAddress clientAddress = new ClientAddress();
//            final ru.korus.tmis.core.entity.model.Address addressDb
//                    = new ru.korus.tmis.core.entity.model.Address();
//            final AddressHouse house = new AddressHouse();
//            addressDb.setHouse(house);
//            clientAddress.setAddress(addressDb);
//        }


        final ClientContact mobileClientContact = new ClientContact();
        mobileClientContact.setContactType(dbContactTypeBean.findByName("мобильный телефон"));
        mobileClientContact.setContact(patient.getContactInfo().getMobilePhoneNumber());
        insert(mobileClientContact);
        patientDb.addClientContact(mobileClientContact);

        final ClientContact homeClientContact = new ClientContact();
        homeClientContact.setContactType(dbContactTypeBean.findByName("домашний телефон"));
        homeClientContact.setContact(patient.getContactInfo().getHomePhoneNumber());
        insert(homeClientContact);
        patientDb.addClientContact(homeClientContact);

        final ClientContact workClientContact = new ClientContact();
        workClientContact.setContactType(dbContactTypeBean.findByName("рабочий телефон"));
        workClientContact.setContact(patient.getContactInfo().getWorkPhoneNumber());
        insert(workClientContact);
        patientDb.addClientContact(workClientContact);

        final ClientContact emailClientContact = new ClientContact();
        emailClientContact.setContactType(dbContactTypeBean.findByName("e-mail"));
        emailClientContact.setContact(patient.getContactInfo().getEmailAddress());
        insert(emailClientContact);
        patientDb.addClientContact(emailClientContact);

        insert(patientDb);
    }

    @TransactionAttribute(value = TransactionAttributeType.REQUIRES_NEW)
    private void insert(Object o) {
        em.persist(o);
    }

    /**
     * Валидация входных данных
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
        patientValidator.validatePatientNumbers(patient.getPatientNumbers().getPatientNumbers());

        final PatientNumberValidator patientNumberValidator = initializePatientNumberValidator(validator);
        patientNumberValidator.validateNumbers(patient.getPatientNumbers().getPatientNumbers());

        return validator;
    }

    private PatientValidator initializePatientValidator(final Validator validator) {
        return new PatientValidator(validator);
    }

    private PatientNumberValidator initializePatientNumberValidator(final Validator validator) {
        final PatientNumberValidator patientNumberValidator = new PatientNumberValidator(validator);
        final List<RbDocumentType> documentTypes;
        try {
            documentTypes = (List<RbDocumentType>) dbDocumentTypeBean.findAllRbDocumentTypes();
            patientNumberValidator.initializeDocumentDictionary(documentTypes);
        } catch (CoreException e) {
            // TODO..
        }
        return patientNumberValidator;
    }

}
