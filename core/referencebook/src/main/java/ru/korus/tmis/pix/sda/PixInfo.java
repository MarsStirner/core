package ru.korus.tmis.pix.sda;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.06.2013, 11:47:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import java.util.List;

import ru.korus.tmis.pix.sda.ws.Address;
import ru.korus.tmis.pix.sda.ws.Allergy;
import ru.korus.tmis.pix.sda.ws.AllergyCode;
import ru.korus.tmis.pix.sda.ws.ArrayOfAddressAddress;
import ru.korus.tmis.pix.sda.ws.ArrayOfAllergyAllergy;
import ru.korus.tmis.pix.sda.ws.ArrayOfDiagnosisDiagnosis;
import ru.korus.tmis.pix.sda.ws.ArrayOfDocumentDocument;
import ru.korus.tmis.pix.sda.ws.ArrayOfEncounterEncounter;
import ru.korus.tmis.pix.sda.ws.ArrayOfPatientNumberPatientNumber;
import ru.korus.tmis.pix.sda.ws.CareProvider;
import ru.korus.tmis.pix.sda.ws.City;
import ru.korus.tmis.pix.sda.ws.ContactInfo;
import ru.korus.tmis.pix.sda.ws.Container;
import ru.korus.tmis.pix.sda.ws.Diagnosis;
import ru.korus.tmis.pix.sda.ws.DiagnosisCode;
import ru.korus.tmis.pix.sda.ws.Document;
import ru.korus.tmis.pix.sda.ws.DocumentType;
import ru.korus.tmis.pix.sda.ws.Encounter;
import ru.korus.tmis.pix.sda.ws.Gender;
import ru.korus.tmis.pix.sda.ws.Name;
import ru.korus.tmis.pix.sda.ws.Organization;
import ru.korus.tmis.pix.sda.ws.Patient;
import ru.korus.tmis.pix.sda.ws.PatientNumber;
import ru.korus.tmis.pix.sda.ws.Severity;
import ru.korus.tmis.pix.sda.ws.State;
import ru.korus.tmis.pix.sda.ws.User;
import ru.korus.tmis.pix.sda.ws.Zip;

/**
 * 
 */
public class PixInfo {

    static public enum EventType {
        I, // стационар
        O, // амбулаторный прием
        UPDATE_INFO; // создание/изменение карточки пациента

    }

    static private enum NumberType {
        MRN,
        SSN,
        DL,
        PPN,
        BCT,

    }

    static public Container toSda(ClientInfo clientInfo,
            EventInfo eventInfo,
            List<AllergyInfo> allergies,
            List<DiagnosisInfo> diagnosesInfo,
            List<EpicrisisInfo> epicrisisInfo) {
        Container res = new Container();
        final Patient patient = new Patient();
        final String orgName = eventInfo == null || eventInfo.getOrgName() == null || "".equals(eventInfo.getOrgName()) ? "Не задано" : eventInfo.getOrgName();
        res.setSendingFacility(orgName);
        res.setPatient(patient);
        final Name name = new Name();
        // ФИО
        name.setFamilyName(emptyToNull(clientInfo.getFamilyName()));
        name.setGivenName(emptyToNull(clientInfo.getGivenName()));
        name.setMiddleName(emptyToNull(clientInfo.getMiddleName()));
        patient.setName(name);
        // Пол
        patient.setGender(new Gender());
        patient.getGender().setCode(clientInfo.getGender().name());
        // Дата и время рождения пациента
        patient.setBirthTime(clientInfo.getBirthDate());

        final ArrayOfPatientNumberPatientNumber patientNumbers = new ArrayOfPatientNumberPatientNumber();
        patient.setPatientNumbers(patientNumbers);

        // Уникальный идентификатор пациента в МИС и краткое наименование ЛПУ
        final PatientNumber patientNumberTmisId = new PatientNumber();
        patientNumbers.getPatientNumber().add(patientNumberTmisId);
        patientNumberTmisId.setNumberType(NumberType.MRN.name());
        patientNumberTmisId.setNumber("" + clientInfo.getTmisId());
        final Organization organization = new Organization();
        patientNumberTmisId.setOrganization(organization);
        organization.setCode(orgName);

        // СНИЛС
        addNumbre(NumberType.SSN, clientInfo.getSnils(), patientNumbers);

        // Номер полиса ОМС
        addNumbre(NumberType.DL, clientInfo.getPolicyNumber(), patientNumbers);

        // Паспорт
        addNumbre(NumberType.PPN, clientInfo.getPassNumber(), patientNumbers);

        // Серия/номер свидетельства о рождении
        addNumbre(NumberType.BCT, clientInfo.getBirthCertificateNumber(), patientNumbers);

        // Адрес пациента
        ArrayOfAddressAddress address = new ArrayOfAddressAddress();
        Address addr = null;
        if (clientInfo.getAddrStreet() != null) {
            addr = getAddrInstance(addr);
            addr.setStreet(clientInfo.getAddrStreet());
        }
        if (clientInfo.getAddrCity() != null) {
            addr = getAddrInstance(addr);
            addr.setCity(new City());
            addr.getCity().setCode(clientInfo.getAddrCity());
        }
        if (clientInfo.getAddrState() != null) {
            addr = getAddrInstance(addr);
            addr.setState(new State());
            addr.getState().setCode(clientInfo.getAddrState());
        }
        if (clientInfo.getAddrZip() != null) {
            addr = getAddrInstance(addr);
            addr.setZip(new Zip());
            addr.getZip().setCode(clientInfo.getAddrZip());
        }
        if (addr != null) {
            patient.setAddresses(address);
            address.getAddress().add(addr);
        }

        // Номера телефонов пациента
        patient.setContactInfo(new ContactInfo());
        patient.getContactInfo().setHomePhoneNumber(clientInfo.getHomePhoneNumber());
        patient.getContactInfo().setWorkPhoneNumber(clientInfo.getWorkPhoneNumber());
        patient.getContactInfo().setMobilePhoneNumber(clientInfo.getMobilePhoneNumber());

        // <Encounters> – блок данных по «эпизодам», т.е. по амбулаторным приемам и по законченным случаям лечения в стационаре.
        if (eventInfo != null) {
            Encounter encounter = new Encounter();
            encounter.setEncounterNumber("" + eventInfo.getUuid());
            if (eventInfo.getBegDate() != null) {
                encounter.setFromTime(eventInfo.getBegDate());
            }
            if (eventInfo.getEndDate() != null) {
                encounter.setToTime(eventInfo.getEndDate());
            }
            encounter.setEncounterType(eventInfo.isInpatient() ? "I" : "O");
            res.setEncounters(new ArrayOfEncounterEncounter());
            res.getEncounters().getEncounter().add(encounter);
        }

        if (!allergies.isEmpty()) {
            res = addAllergies(res, allergies);
        }

        if (!diagnosesInfo.isEmpty()) {
            res = addDiagnosis(res, diagnosesInfo);
        }

        if (!epicrisisInfo.isEmpty()) {
            res = addepicrisis(res, epicrisisInfo);
        }

        return res;
    }

    /**
     * @param res
     * @param epicrisisInfo
     * @return
     */
    private static Container addepicrisis(Container res, List<EpicrisisInfo> epicrisisInfo) {
        res.setDocuments(new ArrayOfDocumentDocument());
        for (EpicrisisInfo epInfo : epicrisisInfo) {
            final Document doc = new Document();
            boolean addNew = false;
            if (epInfo.getEventUuid() != null) {
                addNew = true;
                doc.setEncounterNumber(epInfo.getEventUuid());
            }
            if (epInfo.getCode() != null && !epInfo.getCode().isEmpty() ||
                    epInfo.getDocName() != null && !epInfo.getDocName().isEmpty()) {
                DocumentType docType = new DocumentType();
                if (epInfo.getCode() != null && !epInfo.getCode().isEmpty()) {
                    docType.setCode(epInfo.getCode());
                }
                if (epInfo.getDocName() != null && !epInfo.getDocName().isEmpty()) {
                    docType.setCode(epInfo.getDocName());
                }
                doc.setDocumentType(docType);
            }
            if (epInfo.getCreateDate() != null) {
                doc.setEnteredOn(epInfo.getCreateDate());
                addNew = true;
            }
            if (epInfo.getText() != null && !epInfo.getText().isEmpty()) {
                doc.setNoteText(epInfo.getText());
                addNew = true;
            }
            final boolean isNameSet = epInfo.getFamilyName() != null && !epInfo.getFamilyName().isEmpty() ||
                    epInfo.getGivenName() != null && !epInfo.getGivenName().isEmpty() ||
                    epInfo.getMiddleName() != null && !epInfo.getMiddleName().isEmpty();
            if (epInfo.getPersonCreatedId() != null || isNameSet) {

                CareProvider careProvider = new CareProvider();

                if (epInfo.getPersonCreatedId() != null) {
                    careProvider.setCode(String.valueOf(epInfo.getPersonCreatedId()));
                }
                if (isNameSet) {
                    Name name = new Name();
                    if (epInfo.getFamilyName() != null && !epInfo.getFamilyName().isEmpty()) {
                        name.setFamilyName(epInfo.getFamilyName());
                    }
                    if (epInfo.getGivenName() != null && !epInfo.getGivenName().isEmpty()) {
                        name.setGivenName(epInfo.getGivenName());
                    }
                    if (epInfo.getMiddleName() != null && !epInfo.getMiddleName().isEmpty()) {
                        name.setMiddleName(epInfo.getMiddleName());
                    }
                    careProvider.setName(name);
                }

                doc.setClinician(careProvider);
            }

            if (addNew) {
                res.getDocuments().getDocument().add(doc);
            }
        }
        return res;
    }

    private static Container addDiagnosis(Container res, List<DiagnosisInfo> diagisesInfo) {
        res.setDiagnoses(new ArrayOfDiagnosisDiagnosis());
        for (DiagnosisInfo diagInfo : diagisesInfo) {
            final Diagnosis diagnosis = new Diagnosis();
            boolean addNew = false;
            if (diagInfo.getEventUuid() != null) {
                addNew = true;
                diagnosis.setEncounterNumber(diagInfo.getEventUuid());
            }
            if (diagInfo.getPersonCreatedId() != null || (diagInfo.getPersonCreatedName() != null && !diagInfo.getPersonCreatedName().isEmpty())) {
                User createdPerson = new User();
                if (diagInfo.getPersonCreatedId() != null) {
                    createdPerson.setCode(String.valueOf(diagInfo.getPersonCreatedId()));
                }
                if (diagInfo.getPersonCreatedName() != null && !diagInfo.getPersonCreatedName().isEmpty()) {
                    createdPerson.setDescription(diagInfo.getPersonCreatedName());
                }
                diagnosis.setEnteredBy(createdPerson);
                addNew = true;
            }
            if (diagInfo.getCreateDate() != null) {
                diagnosis.setEnteredOn(diagInfo.getCreateDate());
                addNew = true;
            }
            if (diagInfo.getMkb() != null || (diagInfo.getDiagName() != null && !diagInfo.getDiagName().isEmpty())) {
                DiagnosisCode diagCode = new DiagnosisCode();
                if (diagInfo.getMkb() != null) {
                    diagCode.setCode(diagInfo.getMkb());
                }
                if (diagInfo.getDiagName() != null && !diagInfo.getDiagName().isEmpty()) {
                    diagCode.setDescription(diagInfo.getDiagName());
                }
                diagnosis.setDiagnosis(diagCode);
                addNew = true;
            }
            if (addNew) {
                res.getDiagnoses().getDiagnosis().add(diagnosis);
            }
        }
        return res;
    }

    /**
     * @param res
     * @param allergies
     * @return
     */
    private static Container addAllergies(Container res, List<AllergyInfo> allergies) {
        res.setAllergies(new ArrayOfAllergyAllergy());
        for (AllergyInfo allergyInfo : allergies) {
            final Allergy allergy = new Allergy();
            boolean addNew = false;
            if (allergyInfo.getOrgName() != null) {
                Organization organisation = new Organization();
                organisation.setCode(allergyInfo.getOrgName());
                allergy.setEnteredAt(organisation);
                addNew = true;
            }
            if (allergyInfo.getCreateDate() != null) {
                allergy.setEnteredOn(allergyInfo.getCreateDate());
                addNew = true;
            }
            if (allergyInfo.getNameSubstance() != null) {
                AllergyCode allergyCode = new AllergyCode();
                allergyCode.setDescription(allergyInfo.getNameSubstance());
                allergy.setAllergy(allergyCode);
                addNew = true;
            }
            if (allergyInfo.getSeverityCode() != null) {
                Severity severity = new Severity();
                severity.setCode(String.valueOf(allergyInfo.getSeverityCode().toString()));
                if (allergyInfo.getSeverityDescription() != null) {
                    severity.setDescription(allergyInfo.getSeverityDescription());
                }
                allergy.setSeverity(severity);
                addNew = true;
            }

            if (addNew) {
                res.getAllergies().getAllergy().add(allergy);
            }
        }
        return res;
    }

    private static String emptyToNull(String in) {
        return "".equals(in) ? null : in;
    }

    /**
     * @param addr
     * @return
     */
    private static Address getAddrInstance(Address addr) {
        return addr == null ? new Address() : addr;
    }

    /**
     * @param type
     * @param value
     * @param patientNumbers
     */
    private static void addNumbre(NumberType type, String value, ArrayOfPatientNumberPatientNumber patientNumbers) {
        if (value != null && !"".equals(value)) {
            final PatientNumber patientNumberSnils = new PatientNumber();
            patientNumbers.getPatientNumber().add(patientNumberSnils);
            patientNumberSnils.setNumberType(type.name());
            patientNumberSnils.setNumber(value);
        }

    }
}
