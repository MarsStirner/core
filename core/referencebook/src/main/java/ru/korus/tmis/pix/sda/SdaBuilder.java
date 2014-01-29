package ru.korus.tmis.pix.sda;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.06.2013, 11:47:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import java.util.List;

import ru.korus.tmis.pix.sda.ws.*;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 */
public class SdaBuilder {

    private static final String MDR308 = "1.2.643.5.1.13.2.1.1.178";

    static public enum EventType {
        I, // стационар
        O, // амбулаторный прием
        UPDATE_INFO; // создание/изменение карточки пациента

    }

    private static ru.korus.tmis.pix.sda.ws.ObjectFactory SDAFactory = new ru.korus.tmis.pix.sda.ws.ObjectFactory();

    static public Container toSda(ClientInfo clientInfo,
            EventInfo eventInfo,
            List<AllergyInfo> allergies,
            List<DiagnosisInfo> diagnosesInfo,
            List<EpicrisisInfo> epicrisisInfo) {
        Container res = new Container();

        // Уникальный идентификатор пациента в МИС и краткое наименование ЛПУ
        res.setFacilityCode(eventInfo.getOrgOid());
        res.setPatientMRN(clientInfo.getTmisId().toString());

        final Patient patient = createPatientWithOrgInfo(eventInfo);
        res.setPatient(patient);
        initPationInfo(clientInfo, patient);

        // <Encounters> – блок данных по «эпизодам», т.е. по амбулаторным приемам и по законченным случаям лечения в стационаре.
        if (eventInfo != null) {
            addEncouter(eventInfo, res);
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

    private static void addEncouter(EventInfo eventInfo, Container res) {
        Encounter encounter = SDAFactory.createEncounter();
        //Идентификатор в МИС
        encounter.setExtId(eventInfo.getUuid());

        //Автор записи
        if(eventInfo.getAutorInfo() != null) {
            Employee autor = toSdaEmployee(eventInfo.getAutorInfo());
            if(autor != null) {
                encounter.setEnteredBy(autor);
            }
        }

        if (eventInfo.getBegDate() != null) {
            //Дата/время ввода записи в МИС
            encounter.setEnteredOn(eventInfo.getBegDate());
            //Дата/время начала лечения
            encounter.setFromTime(eventInfo.getBegDate());
        }

        //Дата/время окончания лечения
        if (eventInfo.getEndDate() != null) {
            encounter.setToTime(eventInfo.getEndDate());
        }
        //Тип эпизода (амбулаторный=O/стационар=I)
        encounter.setEncType(eventInfo.isInpatient() ? "I" : "O");

        //Вид оплаты (ОМС, бюджет, платные услуги, ДМС, ...)
        if(eventInfo.getFinanceType() != null) {
            encounter.setPaymentType(getCodeAndName(eventInfo.getFinanceType(), "1.2.643.5.1.13.2.1.1.528"));
        }

        //Признак на дому
        encounter.setIsAtHome(eventInfo.isAtHome());

        //Подразделение МО лечения из регионального справочника
        if(eventInfo.getOrgStructure() != null) {
            encounter.setFacilityDept(getCodeAndName(eventInfo.getOrgStructure(), null));
        }

        //Данные о госпитализации
        if(eventInfo.getAdmissionInfo() != null ) {
            encounter.setAdmissionInfo(toSdaAdmission(eventInfo.getAdmissionInfo()));
        }

        //Результат обращения/госпитализации
        if(eventInfo.getEncounterResult() != null) {
            encounter.setEncounterResult(getCodeAndName(eventInfo.getEncounterResult(), "1.2.643.5.1.13.2.1.1.551"));
        }

        res.setEncounters(SDAFactory.createArrayOfencounterEncounter());
        res.getEncounters().getEncounter().add(encounter);
    }

    private static Admission toSdaAdmission(AdmissionInfo admissionInfo) {
        Admission res = SDAFactory.createAdmission();
        //Доставлен в стационар по экстренным показаниям
        res.setIsUrgentAdmission(admissionInfo.isUrgent());
        //Время, прошедшее c начала заболевания (получения травмы) до госпитализации
        if(admissionInfo.getTimeAftreFalling() != null ) {
            res.setTimeAfterFallingIll(getCodeAndName(admissionInfo.getTimeAftreFalling(),"1.2.643.5.1.13.2.1.1.537"));
        }
        //Виды транспортировки (на каталке, на кресле, может идти)
        if(admissionInfo.getTransportType() != null) {
            res.setTransportType(getCodeAndName(admissionInfo.getTransportType(), null));
        }
        //Отделение
        if(admissionInfo.getDepartment() != null) {
            res.setDepartment(getCodeAndName(admissionInfo.getDepartment(), null));
        }
        //Переведен в отделение
        if(admissionInfo.getFinalDepartment() != null) {
            res.setFinalDepartment(getCodeAndName(admissionInfo.getFinalDepartment(), null));
        }
        //Палата
        if(admissionInfo.getWard() != null) {
            res.setWard(admissionInfo.getWard());
        }
        //Проведено койко-дней в стационаре
        if(admissionInfo.getBedDayCount() != null) {
            res.setBedDayCount(admissionInfo.getBedDayCount());
        }
        //Врач приемного отделения
        if(admissionInfo.getAdmittingDoctor() != null) {
            res.setAttendingDoctor(toSdaEmployee(admissionInfo.getAttendingDoctor()));
        }
        //Лечащий врач
        if(admissionInfo.getAttendingDoctor() != null) {
            res.setAttendingDoctor(toSdaEmployee(admissionInfo.getAttendingDoctor()));
        }
        //Кем доставлен
        if(admissionInfo.getAdmissionReferral() != null) {
            res.setAdmissionReferral(getCodeAndName(admissionInfo.getAdmissionReferral(), "1.2.643.5.1.13.2.1.1.281"));
        }
        //Кол-во госпитализации в текущем году
        if(admissionInfo.getAdmissionsThisYear() != null) {
            res.setAdmissionsThisYear(getCodeAndName(admissionInfo.getAdmissionsThisYear(), "1.2.643.5.1.13.2.1.1.109"));
        }

        return res;
    }

    private static Employee toSdaEmployee(EmployeeInfo autorInfo) {
        Employee res = null;
        //Код врача
        if(autorInfo.getCode() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setCode(autorInfo.getCode());
        }
        //Специальность
        if(autorInfo.getSpesialty() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setSpecialty(getCodeAndName(autorInfo.getSpesialty(), "1.2.643.5.1.13.2.1.1.181"));
        }
        //Должность
        if(autorInfo.getRole() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setRole(getCodeAndName(autorInfo.getRole(), "1.2.643.5.1.13.2.1.1.607"));
        }
        //Снилс
        if(autorInfo.getSnils() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setSnils(autorInfo.getSnils());
        }
        //Фамилия
        if(autorInfo.getFamily() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setFamilyName(autorInfo.getFamily());
        }
        //Имя
        if(autorInfo.getGiven() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setGivenName(autorInfo.getGiven());
        }
        //Отчество
        if(autorInfo.getMiddleName() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setMiddleName(autorInfo.getMiddleName());
        }

        return res;
    }


    private static void initPationInfo(ClientInfo clientInfo, Patient patient) {

        // ФИО
        patient.setFamilyName(emptyToNull(clientInfo.getFamilyName()));
        patient.setGivenName(emptyToNull(clientInfo.getGivenName()));
        patient.setMiddleName(emptyToNull(clientInfo.getMiddleName()));
        // Пол
        patient.setGender(clientInfo.getGender().name());
        // Дата и время рождения пациента
        patient.setDob(clientInfo.getBirthDate());

        //Место рождения
        if(clientInfo.getBirthPlace() != null) {
            final Address birthAddress = SDAFactory.createAddress();
            birthAddress.setCity(clientInfo.getBirthPlace());
            patient.setBirthAddress(birthAddress);
        }

        // СНИЛС
        if(clientInfo.getSnils() != null) {
            patient.setSnils(clientInfo.getSnils());
        }

        // Номер полиса единого образца
        if(clientInfo.getEnpNumber() != null) {
            patient.setEnp(clientInfo.getEnpNumber());
        }

        // Адрес регистрации пациента
        final Address regAddr = toSdaAddr(clientInfo.getRegAddr());
        if (regAddr != null) {
            patient.setLegalAddress(regAddr);
        }

        // Адрес проживания пациента
        final Address actualAddr = toSdaAddr(clientInfo.getActualAddr());
        if (actualAddr != null) {
            patient.setActualAddress(actualAddr);
        }

        // Признак городского/сельского жителя
        if(clientInfo.getDwellingType() != null) {
            patient.setDwellingType(clientInfo.getDwellingType());
        }

        //Данные о льготах
        for(ClientInfo.Privilege curPrivilege : clientInfo.getPrivileges()) {
            final Privilege priv = toSdaPrivilege(curPrivilege);
            if(priv != null) {
                patient.getPrivilege().add(priv);
            }
        }

        //Социальный статус
        for(ClientInfo.SocialStatus socialStatus : clientInfo.getSocialStatus()) {
            String codeSystem = "1.2.643.5.1.13.2.1.1.366";
            final CodeAndName codeAndName = getCodeAndName(socialStatus.getCodeAndName(), codeSystem);
            if(codeAndName != null) {
                patient.getSocialStatus().add(codeAndName);
            }
        }

        //Признак лица без определенного места жительства
        patient.setIsHomeless(clientInfo.isHomeless());

        //Признак лица без определенного места жительства
        patient.setIsServicemanFamily(clientInfo.isServicemanFamily());

        //Место работы/учебы, профессия, должность
        for(ClientInfo.WorkInfo workInfo : clientInfo.getWorkInfo()) {
            Occupation occupation = toSdaOccupation(workInfo);
            if(occupation != null) {
                patient.getOccupation().add(occupation);
            }
        }

        //Гражданство
        if(clientInfo.getCitizenship() != null) {
            final CodeAndName codeAndName = getCodeAndName(clientInfo.getCitizenship(), null);
            if(codeAndName != null ) {
                patient.setCitizenship(codeAndName);
            }
        }

        //Документ, удостоверяющий личность
        if(clientInfo.getPassport() != null) {
            final IdentityDocument dcc = toSdaIdentityDoc(clientInfo.getPassport());
            if(dcc != null) {
                patient.setIdentityDocument(dcc);
            }
        }

        //Свидетельство о рождении
        if(clientInfo.getPassport() != null) {
            final IdentityDocument doc = toSdaIdentityDoc(clientInfo.getBirthCertificate());
            if(doc != null) {
                patient.setBirthCertificate(doc);
            }
        }

        //Данные полиса ОМС
        if(clientInfo.getOmsInfo() != null) {
            final Insurance insurance = toSdaInsurance(clientInfo.getOmsInfo());
            if(insurance != null) {
                patient.setOmsInsurance(insurance);
            }
        }

        //Данные полиса ДМС
        if(clientInfo.getDmsInfo() != null) {
            final Insurance insurance = toSdaInsurance(clientInfo.getDmsInfo());
            if(insurance != null) {
                patient.setOmsInsurance(insurance);
            }
        }

        // Группа крови и резус фактор
       /* if(clientInfo.getBloodGroup() != null) {
            patient.setBloodGroup(clientInfo.getBloodGroup());
            patient.setRhesusFactor(  );
        }*/

        //Номера телефонов пациента
        final ContactInfo contactInfo = getContactInfo(clientInfo);
        if(clientInfo != null) {
            patient.setContactInfo(contactInfo);
        }
    }

    private static ContactInfo getContactInfo(ClientInfo clientInfo) {
        ContactInfo contactInfo = null;
        if(clientInfo.getHomePhoneNumber() != null) {
            contactInfo =  contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setHomePhone(clientInfo.getHomePhoneNumber());
        }
        if(clientInfo.getWorkPhoneNumber() != null) {
            contactInfo =  contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setWorkPhone(clientInfo.getWorkPhoneNumber());
        }
        if(clientInfo.getMobilePhoneNumber() != null) {
            contactInfo =  contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setCellPhone(clientInfo.getMobilePhoneNumber());
        }
        return contactInfo;
    }

    private static Insurance toSdaInsurance(ClientInfo.InsuranceInfo insuranceInfo) {
        Insurance res = null;
        if(insuranceInfo.getOrganization() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompany(getCodeAndName(insuranceInfo.getOrganization(), "1.2.643.5.1.13.2.1.1.635"));
        }
        if(insuranceInfo.getOkato() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompanyOkato(insuranceInfo.getOkato());
        }
        if(insuranceInfo.getPolicyTypeName() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicyType(getCodeAndName(insuranceInfo.getPolicyTypeName(), null));
        }
        if (insuranceInfo.getSerial() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicySer(insuranceInfo.getSerial());
        }
        if(insuranceInfo.getNumber() != null ) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicyNum(insuranceInfo.getNumber());
        }
        if(insuranceInfo.getBegDate() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setEffectiveDate(insuranceInfo.getBegDate());
        }
        if(insuranceInfo.getEndDate() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setExpirationDate(insuranceInfo.getEndDate());
        }
        if (insuranceInfo.getArea() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompanyKladr(getCodeAndName(insuranceInfo.getArea(), "1.2.643.5.1.13.2.1.1.196"));
        }

        return res;
    }

    private static IdentityDocument toSdaIdentityDoc(ClientInfo.DocInfo passport) {
        IdentityDocument res = null;
        res.setDocType(getCodeAndName(new CodeNamePair("21", "Паспорт гражданина РФ"), "1.2.643.5.1.13.2.1.1.498"));
        if(passport.getNumber() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocNum(passport.getNumber());
        }

        if (passport.getSerial() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocSer(passport.getSerial());
        }

        if(passport.getIssued() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocSource(passport.getIssued());
        }

        if(passport.getCreateDate() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocDate(passport.getCreateDate());
        }

        if(passport.getExpirationDate() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setExpirationDate(passport.getExpirationDate());
        }

        return res;
    }

    private static Occupation toSdaOccupation(ClientInfo.WorkInfo workInfo) {
        Occupation res = null;
        //Наименование места работы/учебы/службы
        if(workInfo.getName() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setOrganizationName(workInfo.getName());
        }

        //Должность/звание
        if(workInfo.getPos() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setPosition(workInfo.getPos());
        }

        //ОКВЭД
        if(workInfo.getOkved() != null ) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setOkved(getCodeAndName(workInfo.getOkved(), "1.2.643.5.1.13.2.1.1.62"));
        }

        //Профессиональная вредность
        if(workInfo.getHarmful() != null ) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setHarmfulConditions(workInfo.getHarmful());
        }

        return res;
    }

    private static Privilege toSdaPrivilege(ClientInfo.Privilege privilege) {
        Privilege res = null;
        final String codeSystem = "1.2.643.5.1.13.2.1.1.358";
        final CodeAndName codeAndName = getCodeAndName(privilege.getCodeAndName(), codeSystem);
        if(codeAndName != null) {
            res = res == null ? SDAFactory.createPrivilege() : res;
            res.setCategory(codeAndName);
        }
        final PrivilegeDocument privilegeDocument = SDAFactory.createPrivilegeDocument();
        final XMLGregorianCalendar docBegDate = privilege.getDocBegDate();
        if(docBegDate != null) {
            privilegeDocument.setDocDate(docBegDate);
        }
        final XMLGregorianCalendar expirationDate = privilege.getExpirationDate();
        if(expirationDate != null) {
            privilegeDocument.setExpirationDate(expirationDate);
        }
        if(docBegDate != null || expirationDate != null) {
            res = res == null ? SDAFactory.createPrivilege() : res;
            res.setDocument(privilegeDocument);
        }
        return res;
    }

    private static CodeAndName getCodeAndName(CodeNamePair codeAndName, String codeSystem) {
        final String code = codeAndName.getCode();
        final CodeAndName res = SDAFactory.createCodeAndName();
        if(code != null) {
            res.setCode(code);
        }
        final String name = codeAndName.getName();
        if(name != null) {
            res.setName(name);
        }
        if(codeSystem != null) {
            res.setCodingSystem(codeSystem);
        }
        return (code == null && name == null) ? null : res;
    }

    private static Address toSdaAddr(AddrInfo regAddr) {
        Address addr = null;

        // Область/край/республика/... (субъект РФ)
        if (regAddr.getAddrState() != null) {
            addr = getAddrInstance(addr);
            addr.setRegion(regAddr.getAddrState());
        }

        //Код субъекта РФ согласно КЛАДР
        if (regAddr.getAddrStateKladrCode() != null) {
            addr = getAddrInstance(addr);
            addr.setRegionKladr(regAddr.getAddrStateKladrCode());
        }

        // Почтовый индекс
        if (regAddr.getAddrZip() != null) {
            addr = getAddrInstance(addr);
            addr.setPostalCode(regAddr.getAddrZip());
        }

        //Район
        if (regAddr.getDistrict() != null) {
            addr = getAddrInstance(addr);
            addr.setDistrict(regAddr.getDistrict());
        }

        //Код района (или города субъектного подчинения) согласно КЛАДР
        if (regAddr.getDistrictKladr() != null) {
            addr = getAddrInstance(addr);
            addr.setDistrictKladr(regAddr.getDistrictKladr());
        }

        //Населенный пункт
        if (regAddr.getAddrCity() != null) {
            addr = getAddrInstance(addr);
            addr.setCity(regAddr.getAddrCity());
        }

        //Код населенного пункта согласно КЛАДР
        if(regAddr.getAddrCityKladr() != null) {
            addr = getAddrInstance(addr);
            addr.setCityKladr(regAddr.getAddrCityKladr());
        }

        //Название улицы
        if (regAddr.getAddrStreet() != null) {
            addr = getAddrInstance(addr);
            addr.setStreet(regAddr.getAddrStreet());
        }

        //Код улицы согласно КЛАДР
        if (regAddr.getAddrStreetKladr() != null) {
            addr = getAddrInstance(addr);
            addr.setStreetKladr(regAddr.getAddrStreetKladr());
        }

        //Номер дома
        if (regAddr.getHouse() != null ) {
            addr = getAddrInstance(addr);
            addr.setHouse(regAddr.getHouse());
        }

        //Номер квартиры
        if (regAddr.getAppartment() != null) {
            addr = getAddrInstance(addr);
            addr.setHouse(regAddr.getAppartment());
        }

        //ОКАТО
        if (regAddr.getOkato() != null) {
            addr = getAddrInstance(addr);
            addr.setHouse(regAddr.getOkato());
        }

        return addr;

    }

    private static Patient createPatientWithOrgInfo(EventInfo eventInfo) {
        final Patient patient = new Patient();
        CodeAndName baseClinic = SDAFactory.createCodeAndName();
        baseClinic.setCode(eventInfo.getOrgOid());
        baseClinic.setCodingSystem(MDR308);
        baseClinic.setName(eventInfo.getOrgOid());
        patient.setBaseClinic(baseClinic);
        return patient;
    }

    /**
     * @param res
     * @param epicrisisInfo
     * @return
     */
    private static Container addepicrisis(Container res, List<EpicrisisInfo> epicrisisInfo) {
        res.setDocuments(new ArrayOfdocumentDocument());
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
        res.setDiagnoses(new ArrayOfdiagnosisDiagnosis());
        for (DiagnosisInfo diagInfo : diagisesInfo) {
            Diagnosis diagnosis = null;
            if (diagInfo.getEventUuid() != null) {
                diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
                diagnosis.setEncounterNumber(diagInfo.getEventUuid());
            }
            if (diagInfo.getPersonCreatedId() != null || (diagInfo.getPersonCreatedName() != null && !diagInfo.getPersonCreatedName().isEmpty())) {
                diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
                User createdPerson = new User();
                if (diagInfo.getPersonCreatedId() != null) {
                    createdPerson.setCode(String.valueOf(diagInfo.getPersonCreatedId()));
                }
                if (diagInfo.getPersonCreatedName() != null && !diagInfo.getPersonCreatedName().isEmpty()) {
                    createdPerson.setDescription(diagInfo.getPersonCreatedName());
                }
                diagnosis.setEnteredBy(createdPerson);
            }
            if (diagInfo.getCreateDate() != null) {
                diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
                diagnosis.setEnteredOn(diagInfo.getCreateDate());
            }
            if (diagInfo.getMkb() != null || (diagInfo.getDiagName() != null && !diagInfo.getDiagName().isEmpty())) {
                diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
                DiagnosisCode diagCode = new DiagnosisCode();
                if (diagInfo.getMkb() != null) {
                    diagCode.setCode(diagInfo.getMkb());
                }
                if (diagInfo.getDiagName() != null && !diagInfo.getDiagName().isEmpty()) {
                    diagCode.setDescription(diagInfo.getDiagName());
                }
                diagnosis.setDiagnosis(diagCode);
            }
            if (diagInfo.getDiagTypeCode() != null || diagInfo.getDiagTypeName() != null) {
                diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
                DiagnosisType diagType = new DiagnosisType();
                if (diagInfo.getDiagTypeCode() != null) {
                    diagType.setCode(diagInfo.getDiagTypeCode());
                }
                if (diagInfo.getDiagTypeName() != null) {
                    diagType.setDescription(diagInfo.getDiagTypeName());
                }
                diagnosis.setDiagnosisType(diagType);
            }

            if (diagnosis != null) {
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
        res.setAllergies(new ArrayOfallergyAllergy());
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
        return addr == null ? SDAFactory.createAddress() : addr;
    }


}
