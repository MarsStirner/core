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
import java.util.List;

/**
 * Сервис для сохранения информации об пациенте
 *
 * @author anosov
 *         Date: 27.07.13 21:04
 */
@Stateless
public class RegistrationPatientBean implements RegistrationPatient {
    private static final Logger log = LoggerFactory.getLogger(RegistrationPatientBean.class);
    private final String WHITESPACE = " ";

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
//        dbPatientBean.findPatientByDocument() // todo....
        final ru.korus.tmis.core.entity.model.Patient patientDb
                = dbPatientBean.insertOrUpdatePatient(-1,
                patient.getName().getGivenName(),
                patient.getName().getMiddleName(),
                patient.getName().getFamilyName(),
                patient.getBirthTime(),
                WHITESPACE,
                patient.getGender().code().toLowerCase(),
                "",
                "",
                null,
                null,
                -1,
                "",
                "",
                null,
                -1
        );

        for (final PatientNumber patientNumber : patient.getPatientNumberList().getPatientNumbers()) {
            if (patientNumber.getNumberType().equalsIgnoreCase("СНИЛС")) {
                patientDb.setSnils(patientNumber.getNumber());
            } else if (patientNumber.getNumberType().equalsIgnoreCase("ОМС")) {
                // todo Что такое серия и номер у ОМС где он передается, и как определяется его тип
                final ClientPolicy clientPolicy = dbClientPolicyBean.insertOrUpdateClientPolicy(
                        -1, 1, -1, patientNumber.getNumber(), patientNumber.getNumber(), null, null, "", "", patientDb, null
                );
                patientDb.addClientPolicies(clientPolicy);
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

        for (final ru.risar.data.Address address : patient.getAddressesList().getAddresses()) {
            final ClientAddress clientAddress = new ClientAddress();
            final ru.korus.tmis.core.entity.model.Address addressDb
                    = new ru.korus.tmis.core.entity.model.Address();

            final StringBuilder addressString = new StringBuilder(address.getState()).append(", ");
            addressString.append(address.getCity()).append(", ");
            addressString.append(address.getStreet()).append(", ");
            addressString.append(address.getZip());

            clientAddress.setFreeInput(addressString.toString());
            clientAddress.setAddress(addressDb);
        }


        final ClientContact mobileClientContact = dbClientContactBean.insertOrUpdateClientContact(
                -1, dbContactTypeBean.findByName("мобильный телефон").getId(),
                patient.getContactInfo().getMobilePhoneNumber(), "", patientDb, null
        );
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

        insert(patientDb);
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
        try {
            documentTypes = (List<RbDocumentType>) dbDocumentTypeBean.findAllRbDocumentTypes();
            patientNumberValidator.initializeDocumentDictionary(documentTypes);
        } catch (CoreException e) {
            // TODO..
        }
        return patientNumberValidator;
    }

}
