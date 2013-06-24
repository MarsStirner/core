package ru.korus.tmis.pix.sda;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.06.2013, 11:47:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import ru.korus.tmis.pix.sda.ws.Address;
import ru.korus.tmis.pix.sda.ws.ArrayOfAddressAddress;
import ru.korus.tmis.pix.sda.ws.ArrayOfEncounterEncounter;
import ru.korus.tmis.pix.sda.ws.ArrayOfPatientNumberPatientNumber;
import ru.korus.tmis.pix.sda.ws.City;
import ru.korus.tmis.pix.sda.ws.ContactInfo;
import ru.korus.tmis.pix.sda.ws.Container;
import ru.korus.tmis.pix.sda.ws.Encounter;
import ru.korus.tmis.pix.sda.ws.Gender;
import ru.korus.tmis.pix.sda.ws.Name;
import ru.korus.tmis.pix.sda.ws.Organization;
import ru.korus.tmis.pix.sda.ws.Patient;
import ru.korus.tmis.pix.sda.ws.PatientNumber;
import ru.korus.tmis.pix.sda.ws.State;
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

    static public Container toSda(ClientInfo clientInfo, EventInfo eventInfo) {
        final Container res = new Container();
        final Patient patient = new Patient();
        final String orgName = eventInfo == null || eventInfo.getOrgName() == null || "".equals(eventInfo.getOrgName())  ? "Не задано" : eventInfo.getOrgName();
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
            encounter.setEncounterNumber("" + eventInfo.getId());
            if (eventInfo.getBegDate() != null) {
                encounter.setFromTime(eventInfo.getBegDate());
            }
            if (eventInfo.getEndDate() != null) {
                encounter.setEndTime(eventInfo.getEndDate());
            }
            encounter.setEncounterType(eventInfo.isInpatient() ? "I" : "O");
            res.setEncounters(new ArrayOfEncounterEncounter());
            res.getEncounters().getEncounter().add(encounter);
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
        if (value != null && !"".equals(value) ) {
            final PatientNumber patientNumberSnils = new PatientNumber();
            patientNumbers.getPatientNumber().add(patientNumberSnils);
            patientNumberSnils.setNumberType(type.name());
            patientNumberSnils.setNumber(value);
        }

    }
}
