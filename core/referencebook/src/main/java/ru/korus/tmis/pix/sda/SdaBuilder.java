package ru.korus.tmis.pix.sda;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.06.2013, 11:47:41 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.pix.sda.ws.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 */
public class SdaBuilder {

    static final Logger logger = LoggerFactory.getLogger(SdaBuilder.class);

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
                                  List<DisabilitiesInfo> disabilitiesInfo,
                                  List<EpicrisisInfo> epicrisisInfo,
                                  List<ServiceInfo> servicesInfo) {
        Container res = new Container();

        // Уникальный идентификатор пациента в МИС и краткое наименование ЛПУ
        res.setFacilityCode(eventInfo.getOrgOid() == null ? "unknown_org_oid" : eventInfo.getOrgOid().getCode());
        res.setPatientMRN(clientInfo.getTmisId().toString());

        final Patient patient = createPatientWithOrgInfo(eventInfo);
        res.setPatient(patient);
        initPationInfo(clientInfo, patient);

        // <Encounters> – блок данных по «эпизодам», т.е. по амбулаторным приемам и по законченным случаям лечения в стационаре.
        if (eventInfo != null) {
            addEncouter(eventInfo, res);
        }

        //Раздел "Диагнозы"
        if (!diagnosesInfo.isEmpty()) {
            res = addDiagnosis(res, eventInfo, diagnosesInfo);
        }

        //Раздел "Инвалидность"
        if (!disabilitiesInfo.isEmpty()) {
            res = addDisabilities(res, disabilitiesInfo);
        }

        if (!allergies.isEmpty()) {
            res = addAllergies(res, eventInfo, allergies);
        }


        if (!epicrisisInfo.isEmpty()) {
            res = addEpicrisis(res, eventInfo, epicrisisInfo);
        }

        if (!servicesInfo.isEmpty() )  {
            res = addServices(res, eventInfo, servicesInfo);
        }


        return res;
    }


    private static void addEncouter(EventInfo eventInfo, Container res) {
        Encounter encounter = SDAFactory.createEncounter();
        if(eventInfo.getEventId() == null) {
            return;
        }
        //Идентификатор в МИС
        encounter.setExtId(eventInfo.getEventId());

        //Автор записи
        if (eventInfo.getAutorInfo() != null) {
            Employee autor = toSdaEmployee(eventInfo.getAutorInfo());
            if (autor != null) {
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
        if (eventInfo.getFinanceType() != null) {
            encounter.setPaymentType(getCodeAndName(eventInfo.getFinanceType()));
        }

        //Признак на дому
        encounter.setIsAtHome(eventInfo.isAtHome());

        //Подразделение МО лечения из регионального справочника
        if (eventInfo.getOrgStructure() != null) {
            encounter.setFacilityDept(getCodeAndName(eventInfo.getOrgStructure()));
        }

        //Данные о госпитализации
        if (eventInfo.getAdmissionInfo() != null) {
            encounter.setAdmissionInfo(toSdaAdmission(eventInfo.getAdmissionInfo()));
        }

        //Результат обращения/госпитализации
        if (eventInfo.getEncounterResult() != null) {
            encounter.setEncounterResult(getCodeAndName(eventInfo.getEncounterResult()));
        }

        res.setEncounters(SDAFactory.createArrayOfencounterEncounter());
        res.getEncounters().getEncounter().add(encounter);
    }

    private static Admission toSdaAdmission(AdmissionInfo admissionInfo) {
        Admission res = SDAFactory.createAdmission();
        //Доставлен в стационар по экстренным показаниям
        res.setIsUrgentAdmission(admissionInfo.isUrgent());
        //Время, прошедшее c начала заболевания (получения травмы) до госпитализации
        if (admissionInfo.getTimeAftreFalling() != null) {
            res.setTimeAfterFallingIll(getCodeAndName(admissionInfo.getTimeAftreFalling()));
        }
        //Виды транспортировки (на каталке, на кресле, может идти)
        if (admissionInfo.getTransportType() != null) {
            res.setTransportType(getCodeAndName(admissionInfo.getTransportType()));
        }
        //Отделение
        if (admissionInfo.getDepartment() != null) {
            res.setDepartment(getCodeAndName(admissionInfo.getDepartment()));
        }
        //Переведен в отделение
        if (admissionInfo.getFinalDepartment() != null) {
            res.setFinalDepartment(getCodeAndName(admissionInfo.getFinalDepartment()));
        }
        //Палата
        if (admissionInfo.getWard() != null) {
            res.setWard(admissionInfo.getWard());
        }
        //Проведено койко-дней в стационаре
        if (admissionInfo.getBedDayCount() != null) {
            res.setBedDayCount(admissionInfo.getBedDayCount());
        }
        //Врач приемного отделения
        if (admissionInfo.getAdmittingDoctor() != null) {
            res.setAttendingDoctor(toSdaEmployee(admissionInfo.getAttendingDoctor()));
        }
        //Лечащий врач
        if (admissionInfo.getAttendingDoctor() != null) {
            res.setAttendingDoctor(toSdaEmployee(admissionInfo.getAttendingDoctor()));
        }
        //Кем доставлен
        if (admissionInfo.getAdmissionReferral() != null) {
            res.setAdmissionReferral(getCodeAndName(admissionInfo.getAdmissionReferral()));
        }
        //Кол-во госпитализации в текущем году
        if (admissionInfo.getAdmissionsThisYear() != null) {
            res.setAdmissionsThisYear(getCodeAndName(admissionInfo.getAdmissionsThisYear()));
        }

        return res;
    }

    private static Employee toSdaEmployee(EmployeeInfo autorInfo) {
        Employee res = null;
        //Код врача
        if (autorInfo.getCode() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setCode(autorInfo.getCode());
        }
        //Специальность
        if (autorInfo.getSpesialty() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setSpecialty(getCodeAndName(autorInfo.getSpesialty()));
        }
        //Должность
        if (autorInfo.getRole() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setRole(getCodeAndName(autorInfo.getRole()));
        }
        //Снилс
        if (autorInfo.getSnils() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setSnils(autorInfo.getSnils());
        }
        //Фамилия
        if (autorInfo.getFamily() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setFamilyName(autorInfo.getFamily());
        }
        //Имя
        if (autorInfo.getGiven() != null) {
            res = res == null ? SDAFactory.createEmployee() : res;
            res.setGivenName(autorInfo.getGiven());
        }
        //Отчество
        if (autorInfo.getMiddleName() != null) {
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
        if (clientInfo.getBirthPlace() != null) {
            final Address birthAddress = SDAFactory.createAddress();
            birthAddress.setCity(clientInfo.getBirthPlace());
            patient.setBirthAddress(birthAddress);
        }

        // СНИЛС
        if (clientInfo.getSnils() != null) {
            patient.setSnils(clientInfo.getSnils());
        }

        // Номер полиса единого образца
        if (clientInfo.getEnpNumber() != null) {
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
        if (clientInfo.getDwellingType() != null) {
            patient.setDwellingType(clientInfo.getDwellingType());
        }

        //Данные о льготах
        for (ClientInfo.Privilege curPrivilege : clientInfo.getPrivileges()) {
            final Privilege priv = toSdaPrivilege(curPrivilege);
            if (priv != null) {
                patient.getPrivilege().add(priv);
            }
        }

        //Социальный статус
        for (ClientInfo.SocialStatus socialStatus : clientInfo.getSocialStatus()) {
            final CodeAndName codeAndName = getCodeAndName(socialStatus.getCodeAndName());
            if (codeAndName != null) {
                patient.getSocialStatus().add(codeAndName);
            }
        }

        //Признак лица без определенного места жительства
        patient.setIsHomeless(clientInfo.isHomeless());

        //Признак лица без определенного места жительства
        patient.setIsServicemanFamily(clientInfo.isServicemanFamily());

        //Место работы/учебы, профессия, должность
        for (ClientInfo.WorkInfo workInfo : clientInfo.getWorkInfo()) {
            Occupation occupation = toSdaOccupation(workInfo);
            if (occupation != null) {
                patient.getOccupation().add(occupation);
            }
        }

        //Гражданство
        if (clientInfo.getCitizenship() != null) {
            final CodeAndName codeAndName = getCodeAndName(clientInfo.getCitizenship());
            if (codeAndName != null) {
                patient.setCitizenship(codeAndName);
            }
        }

        //Документ, удостоверяющий личность
        if (clientInfo.getPassport() != null) {
            final IdentityDocument dcc = toSdaIdentityDoc(clientInfo.getPassport());
            if (dcc != null) {
                patient.setIdentityDocument(dcc);
            }
        }

        //Свидетельство о рождении
        if (clientInfo.getPassport() != null) {
            final IdentityDocument doc = toSdaIdentityDoc(clientInfo.getBirthCertificate());
            if (doc != null) {
                patient.setBirthCertificate(doc);
            }
        }

        //Данные полиса ОМС
        if (clientInfo.getOmsInfo() != null) {
            final Insurance insurance = toSdaInsurance(clientInfo.getOmsInfo());
            if (insurance != null) {
                patient.setOmsInsurance(insurance);
            }
        }

        //Данные полиса ДМС
        if (clientInfo.getDmsInfo() != null) {
            final Insurance insurance = toSdaInsurance(clientInfo.getDmsInfo());
            if (insurance != null) {
                patient.getDmsInsurance().add(insurance);
            }
        }

        // Группа крови и резус фактор
        if(clientInfo.getBloodGroup() != null && clientInfo.getBloodRhesus() != null) {
            if(clientInfo.getBloodGroup() != null ) {
                patient.setBloodGroup(clientInfo.getBloodGroup());
            }
            if(clientInfo.getBloodRhesus() != null) {
                patient.setRhesusFactor( clientInfo.getBloodRhesus() );
            }
        }

        //Номера телефонов пациента
        final ContactInfo contactInfo = getContactInfo(clientInfo);
        if (clientInfo != null) {
            patient.setContactInfo(contactInfo);
        }
    }

    private static ContactInfo getContactInfo(ClientInfo clientInfo) {
        ContactInfo contactInfo = null;
        if (clientInfo.getHomePhoneNumber() != null) {
            contactInfo = contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setHomePhone(clientInfo.getHomePhoneNumber());
        }
        if (clientInfo.getWorkPhoneNumber() != null) {
            contactInfo = contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setWorkPhone(clientInfo.getWorkPhoneNumber());
        }
        if (clientInfo.getMobilePhoneNumber() != null) {
            contactInfo = contactInfo == null ? SDAFactory.createContactInfo() : contactInfo;
            contactInfo.setCellPhone(clientInfo.getMobilePhoneNumber());
        }
        return contactInfo;
    }

    private static Insurance toSdaInsurance(ClientInfo.InsuranceInfo insuranceInfo) {
        Insurance res = null;
        if (insuranceInfo.getOrganization() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompany(getCodeAndName(insuranceInfo.getOrganization()));
        }
        if (insuranceInfo.getOkato() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompanyOkato(insuranceInfo.getOkato());
        }
        if (insuranceInfo.getPolicyTypeName() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicyType(getCodeAndName(insuranceInfo.getPolicyTypeName()));
        }
        if (insuranceInfo.getSerial() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicySer(insuranceInfo.getSerial());
        }
        if (insuranceInfo.getNumber() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setPolicyNum(insuranceInfo.getNumber());
        }
        if (insuranceInfo.getBegDate() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setEffectiveDate(insuranceInfo.getBegDate());
        }
        if (insuranceInfo.getEndDate() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setExpirationDate(insuranceInfo.getEndDate());
        }
        if (insuranceInfo.getArea() != null) {
            res = res == null ? SDAFactory.createInsurance() : res;
            res.setInsuranceCompanyKladr(getCodeAndName(insuranceInfo.getArea()));
        }

        return res;
    }

    private static IdentityDocument toSdaIdentityDoc(ClientInfo.DocInfo passport) {
        IdentityDocument res = null;
        res.setDocType(getCodeAndName(passport.getDocType()));
        if (passport.getNumber() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocNum(passport.getNumber());
        }

        if (passport.getSerial() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocSer(passport.getSerial());
        }

        if (passport.getIssued() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocSource(passport.getIssued());
        }

        if (passport.getCreateDate() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setDocDate(passport.getCreateDate());
        }

        if (passport.getExpirationDate() != null) {
            res = res == null ? SDAFactory.createIdentityDocument() : res;
            res.setExpirationDate(passport.getExpirationDate());
        }

        return res;
    }

    private static Occupation toSdaOccupation(ClientInfo.WorkInfo workInfo) {
        Occupation res = null;
        //Наименование места работы/учебы/службы
        if (workInfo.getName() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setOrganizationName(workInfo.getName());
        }

        //Должность/звание
        if (workInfo.getPos() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setPosition(workInfo.getPos());
        }

        //ОКВЭД
        if (workInfo.getOkved() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setOkved(getCodeAndName(workInfo.getOkved()));
        }

        //Профессиональная вредность
        if (workInfo.getHarmful() != null) {
            res = res == null ? SDAFactory.createOccupation() : res;
            res.setHarmfulConditions(workInfo.getHarmful());
        }

        return res;
    }

    private static Privilege toSdaPrivilege(ClientInfo.Privilege privilege) {
        Privilege res = null;
        final CodeAndName codeAndName = getCodeAndName(privilege.getCategory());
        if (codeAndName != null) {
            res = res == null ? SDAFactory.createPrivilege() : res;
            res.setCategory(codeAndName);
        }
        final PrivilegeDocument privilegeDocument = SDAFactory.createPrivilegeDocument();
        final XMLGregorianCalendar docBegDate = privilege.getDocBegDate();
        if (docBegDate != null) {
            privilegeDocument.setDocDate(docBegDate);
        }
        final XMLGregorianCalendar expirationDate = privilege.getExpirationDate();
        if (expirationDate != null) {
            privilegeDocument.setExpirationDate(expirationDate);
        }
        if (docBegDate != null || expirationDate != null) {
            res = res == null ? SDAFactory.createPrivilege() : res;
            res.setDocument(privilegeDocument);
        }
        return res;
    }

    private static CodeAndName getCodeAndName(CodeNameSystem codeAndName) {
        final String code = codeAndName.getCode();
        final CodeAndName res = SDAFactory.createCodeAndName();
        if (code != null) {
            res.setCode(code);
        }
        final String name = codeAndName.getName();
        if (name != null) {
            res.setName(name);
        }
        if (codeAndName.getCodingSystem() != null) {
            res.setCodingSystem(codeAndName.getCodingSystem());
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
        if (regAddr.getAddrCityKladr() != null) {
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
        if (regAddr.getHouse() != null) {
            addr = getAddrInstance(addr);
            addr.setHouse(regAddr.getHouse());
        }

        //Номер квартиры
        if (regAddr.getAppartment() != null) {
            addr = getAddrInstance(addr);
            addr.setApartment(regAddr.getAppartment());
        }

        //ОКАТО
        if (regAddr.getOkato() != null) {
            addr = getAddrInstance(addr);
            addr.setOkato(regAddr.getOkato());
        }

        return addr;

    }

    private static Patient createPatientWithOrgInfo(EventInfo eventInfo) {
        final Patient patient = new Patient();
        final CodeNameSystem codeNameSystem = eventInfo.getOrgOid();
        final CodeAndName codeName = getCodeAndName(codeNameSystem);
        patient.setBaseClinic(codeName);
        return patient;
    }

    /**
     * @param res
     * @param epicrisisInfo
     * @return
     */
    private static Container addEpicrisis(Container res, EventInfo eventInfo, List<EpicrisisInfo> epicrisisInfo) {
        res.setDocuments(new ArrayOfdocumentDocument());
        for (EpicrisisInfo epInfo : epicrisisInfo) {
            Document doc = null;
            //Идентификатор в МИС и Номер документа
            if(epInfo.getId() != null ) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setExtId(epInfo.getId());
                doc.setDocNum(epInfo.getId());
            }
            //Код эпизода
            if (eventInfo.getEventId() != null) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setEncounterCode(eventInfo.getEventId());
            }

            //Автор записи (врач)
            if (epInfo.getCreatedPerson() != null ) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setEnteredBy(toSdaEmployee(epInfo.getCreatedPerson()));
            }

            //Дата/время ввода записи в МИС и Дата документа
            if(epInfo.getEndDate() != null) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setEnteredOn(epInfo.getEndDate());
                doc.setDocDate(epInfo.getEndDate());
            }

            //Тип документа
            if(epInfo.getDocType() != null) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setDocType(getCodeAndName(epInfo.getDocType()));
            }

            //Название документа
            if(epInfo.getDocName() != null) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                doc.setDocName(epInfo.getDocName());
            }

            if(epInfo.getText() != null ) {
                doc = doc == null ? SDAFactory.createDocument() : doc;
                final String charsetName = "UTF-8";
                try {
                    logger.info("Epicrisis id: " + epInfo.getId() + "  Epicrisis text: " + epInfo.getText());
                    doc.setStream(epInfo.getText().getBytes(charsetName));
                    doc.setFileType("text/html");
                } catch (UnsupportedEncodingException e) {
                    logger.error("Unsupported encoding: '" + charsetName + "' :", e);  //To change body of catch statement use File | Settings | File Templates.
                }
            }

            if (doc != null) {
                res.getDocuments().getDocument().add(doc);
            }
        }
        return res;
    }

    private static Container addDiagnosis(Container res, EventInfo eventInfo, List<DiagnosisInfo> diagisesInfo) {
        res.setDiagnoses(SDAFactory.createArrayOfdiagnosisDiagnosis());
        for (DiagnosisInfo diagInfo : diagisesInfo) {
            Diagnosis diagnosis = toSdaDiagnosis(eventInfo, diagInfo);
            if (diagnosis != null) {
                res.getDiagnoses().getDiagnosis().add(diagnosis);
            }
        }
        return res;
    }

    /**
     * Раздел "Инвалидность"
     * @param res
     * @param disabilitiesInfo
     * @return
     */
    private static Container addDisabilities(Container res, List<DisabilitiesInfo> disabilitiesInfo) {
        res.setDisabilities(SDAFactory.createArrayOfdisabilityDisability());
        for (DisabilitiesInfo disability : disabilitiesInfo) {
            Disability sdaDisability = toSdaDisability(disability);
            if(sdaDisability != null) {
                res.getDisabilities().getDisability().add(sdaDisability);
            }
        }
        return res;
    }

    private static Disability toSdaDisability(DisabilitiesInfo disability) {
        Disability res = null;

        //Идентификатор в МИС
        if(disability.getId() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setExtId(disability.getId());
        }

        //Дата/время ввода записи в МИС
        if(disability.getCreateDate() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setEnteredOn(disability.getCreateDate());
        }

        //Группа инвалидности
        if(disability.getGroup() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setDisabilityGroup(disability.getGroup());
        }

        //Признак ребенок-инвалид
        if(disability.getIsChild() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setIsDisabledChild(disability.getIsChild());
        }

        //Установлена впервые в жизни
        if(disability.getIsFirstTime() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setIsFirstTime(disability.getIsFirstTime());
        }

        //Действует с
        if(disability.getBegDate() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setFromTime(disability.getBegDate());
        }

        //Действует по
        if(disability.getEndDate() != null) {
            res = res == null ? SDAFactory.createDisability() : res;
            res.setFromTime(disability.getEndDate());
        }

        return res;
    }

    private static Diagnosis toSdaDiagnosis(EventInfo eventInfo, DiagnosisInfo diagInfo) {
        Diagnosis diagnosis = null;
        //Идентификатор в МИС
        if (diagInfo.getDiagId() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setExtId(diagInfo.getDiagId());
        }
        //Идентификатор текущего обращения
        if (eventInfo.getEventId() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setEncounterCode(eventInfo.getEventId());
        }
        //Врач
        if (diagInfo.getEnteredPerson() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setEnteredBy(toSdaEmployee(diagInfo.getEnteredPerson()));
        }
        //Дата/время ввода записи в МИС
        if (diagInfo.getCreateDate() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setEnteredOn(diagInfo.getCreateDate());
        }
        // Диагноз (код МКБ-10 и расшифровка)
        if (diagInfo.getMkb() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setDiagnosis(getCodeAndName(diagInfo.getMkb()));
        }
        // Тип диагноза
        if (diagInfo.getDiagType() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setDiagnosisType(getCodeAndName(diagInfo.getDiagType()));
        }

        //Вид диагноза
        //TODO ????

        //Острое или хроническое заболевание
        if (diagInfo.getAcuteOrChronic() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setAcuteOrChronic(diagInfo.getAcuteOrChronic() ? "ACUTE" : "CHRONIC");
        }

        //Диспансерное наблюдение
        if (diagInfo.getDispensarySuperVision() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setDispensarySupervision(toSdaDispensarySuperVision(diagInfo.getDispensarySuperVision()));
        }

        //Вид травмы
        if(diagInfo.getTraumaType() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setTraumaType(getCodeAndName(diagInfo.getTraumaType()));
        }

        //Кол-во госпитализаций в текущем году с данным диагнозом
        if(diagInfo.getCountAdmissionsThisYear() != null) {
            diagnosis = diagnosis == null ? SDAFactory.createDiagnosis() : diagnosis;
            diagnosis.setAdmissionsThisYear(diagInfo.getCountAdmissionsThisYear());
        }

        return diagnosis;
    }

    private static DispensarySupervision toSdaDispensarySuperVision(DispensaryInfo dispensarySuperVision) {
        DispensarySupervision res = null;
        if( dispensarySuperVision.isStarted() == null ) {
            return null;
        }
        if (dispensarySuperVision.isStarted()) {
            //Дата постановки на диспансерное наблюдение
            if (dispensarySuperVision.getDate() != null) {
                res = res == null ? SDAFactory.createDispensarySupervision() : res;
                res.setStartDate(dispensarySuperVision.getDate());
            }
            //Кто осуществил постановку на диспансерное наблюдение
            if (dispensarySuperVision.getPerson() != null) {
                res = res == null ? SDAFactory.createDispensarySupervision() : res;
                res.setStartedBy(toSdaEmployee(dispensarySuperVision.getPerson()));
            }

        } else {
            //Дата снятия с диспансерного наблюдения
            if (dispensarySuperVision.getDate() != null) {
                res = res == null ? SDAFactory.createDispensarySupervision() : res;
                res.setStopDate(dispensarySuperVision.getDate());
            }
            //Кто осуществил снятие с диспансерного наблюдения
            if (dispensarySuperVision.getPerson() != null) {
                res = res == null ? SDAFactory.createDispensarySupervision() : res;
                res.setStoppedBy(toSdaEmployee(dispensarySuperVision.getPerson()));
            }
        }
        return res;
    }

    /**
     * @param res
     * @param allergies
     * @return
     */
    private static Container addAllergies(Container res, EventInfo eventInfo, List<AllergyInfo> allergies) {
        res.setAllergies(new ArrayOfallergyAllergy());
        for (AllergyInfo allergyInfo : allergies) {
            Allergy allergy = getAllergy(eventInfo, allergyInfo);
            if (allergy != null) {
                res.getAllergies().getAllergy().add(allergy);
            }
        }
        return res;
    }

    private static Allergy getAllergy(EventInfo eventInfo, AllergyInfo allergyInfo) {
        Allergy allergy = null;
        //Идентификатор в МИС
        if(allergyInfo.getId() != null) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setExtId(allergyInfo.getId());
        }
        //Код эпизода
        if(eventInfo != null) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setEncounterCode(eventInfo.getEventId());
        }

        //Автор записи (Врач)
        if(allergyInfo.getCreatedPerson() != null) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setEnteredBy(toSdaEmployee(allergyInfo.getCreatedPerson()));
        }

        //Дата/время ввода записи в МИС
        if (allergyInfo.getCreateDate() != null) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setEnteredOn(allergyInfo.getCreateDate());
        }

        //Аллергия/непереносимость (название препарата, шерсть животных, продукт питания, пыль, ...)
        if(allergyInfo.getNameSubstance() != null) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setAllergy(getCodeAndName(CodeNameSystem.newInstance(null, allergyInfo.getNameSubstance(), null)));
        }

        //Дополнительная информация
        if (allergyInfo.getNote() != null ) {
            allergy = allergy == null ? SDAFactory.createAllergy() : allergy;
            allergy.setComments(allergyInfo.getNote());
        }
        return allergy;
    }

    /**
     * Раздел "Медицинские услуги"
     * @param res
     * @param eventInfo
     * @param servicesInfo
     * @return
     */

    private static Container addServices(Container res, EventInfo eventInfo, List<ServiceInfo> servicesInfo) {
        res.setServices(SDAFactory.createArrayOfserviceMedService());
        for (ServiceInfo serviceInfo : servicesInfo) {
            MedService service = getMedService(eventInfo, serviceInfo);
            if (service != null) {
                res.getServices().getService().add(service);
            }
        }
        return res;
    }

    private static MedService getMedService(EventInfo eventInfo, ServiceInfo serviceInfo) {
        MedService res = null;
        //Идентификатор в МИС
        if(serviceInfo.getId() != null){
            res = res == null ? SDAFactory.createMedService() : res;
            res.setExtId(serviceInfo.getId());
        }
        //Код эпизода
        if(eventInfo.getEventId() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setEncounterCode(eventInfo.getEventId());
        }

        //Автор записи (врач)
        if(serviceInfo.getCreatedPerson() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setEnteredBy(toSdaEmployee(serviceInfo.getCreatedPerson()));
        }

        //Дата/время ввода записи в МИС и Дата/время оказания
        if(serviceInfo.getCreateDate() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setEnteredOn(serviceInfo.getCreateDate());
            res.setRenderedOn(serviceInfo.getCreateDate());
        }

        //Код и название услуги
        if(serviceInfo.getServiceCode() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setService(getCodeAndName(serviceInfo.getServiceCode()));
        }

        //Диагноз (код МКБ-10 и расшифровка)
        if(serviceInfo.getDiagnosis() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setDiagnosis(getCodeAndName(serviceInfo.getDiagnosis()));
        }

        //Кол-во оказанных услуг
        if(serviceInfo.getAmount() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setQuantity(new BigDecimal(serviceInfo.getAmount()));
        }

        //Исполнитель действия
        if(serviceInfo.getPerson() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setRenderedBy(toSdaEmployee(serviceInfo.getPerson()));
        }

        //Вид медицинской помощи
        if(serviceInfo.getServType() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setServCareType(getCodeAndName(serviceInfo.getServType()));
        }

        //Отделение МО лечения из регионального справочник
        if(serviceInfo.getOrgStruct() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setFacilityDept(getCodeAndName(serviceInfo.getOrgStruct()));
        }

        //Профиль оказанной медицинской помощи
        if(serviceInfo.getServiceProfile() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setCareProfile(getCodeAndName(serviceInfo.getServiceProfile()));
        }

        //Признак детского профиля
        if(serviceInfo.getIsChildProfile() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setIsChildProfile(serviceInfo.getIsChildProfile());
        }

        //Профиль койки
        if(serviceInfo.getBedProfile() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setBedProfile(getCodeAndName(new CodeNameSystem(null, serviceInfo.getBedProfile())));
        }

        //Способ оплаты медицинской помощи
        if(eventInfo.getFinanceType() != null) {
            res = res == null ? SDAFactory.createMedService() : res;
            res.setServPaymentType(getCodeAndName(eventInfo.getFinanceType()));
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
