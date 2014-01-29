package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        06.06.2013, 13:19:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 *
 */
public class ClientInfo {
    /**
     * Код типа документа - "ПАСПОРТ РФ"
     */
    private static final String PASSPORT = "1";
    /**
     * Код типа документа - "СВИД О РОЖД"
     */
    private static final String CBT = "1";
    /**
     * Код типа полис медицинского страхования - "ОМС Производственный"
     */
    private static final String OMS_WORK = "1";
    /**
     * Код типа полис медицинского страхования - "ОМС Территориальный"
     */
    private static final String OMS_LOC = "0";
    /**
     *   Код типа полис медицинского страхования - "ДМС"
     */
    private static final String DMS = "4";

    /**
     * Номер полиса единого образца
     */
    private static final String CMI_COMMON_ELECTRON = "cmiCommonElectron";
    /**
     * Код типа контакта "домашний телефон"
     */
    private static final String HOME_TEL = "01";
    /**
     * Код типа контакта "рабочий телефон"
     */
    private static final String WORK_TEL = "02";
    /**
     * Код типа контакта "мобильный телефон"
     */
    private static final String MOB_TEL = "03";

    private static final String PRIVILEGE_CODE = "5";

    private static final String SOCIAL_STATUS_CODE = "33";

    /**
     * Признак городского/сельского жителя
     */
    final private String dwellingType;

    /**
     * Данные о льготах
     */
    final private Iterable<Privilege> privileges;

    final private Iterable<SocialStatus> socialStatusList;

    /**
     * Признак лица без определенного места жительства
     */
    final private boolean homeless;
    private static final String CITIZENSHIP_CODE = "35";

    private Iterable<WorkInfo> workInfo;

    /**
     * Гражданство
     */
    final private CodeNamePair citizenship;

    final private DocInfo passport;
    final private InsuranceInfo omsInfo;

    public InsuranceInfo getDmsInfo() {
        return dmsInfo;
    }

    private final InsuranceInfo dmsInfo;

    public boolean isServicemanFamily() {
        return servicemanFamily;
    }

    /**
     * Признак лица без определенного места жительства
     */
    private final boolean servicemanFamily;

    public String getDwellingType() {
        return dwellingType;
    }

    public Iterable<Privilege> getPrivileges() {
        return privileges;
    }

    public Iterable<? extends SocialStatus> getSocialStatus() {
        return socialStatusList;
    }

    public boolean isHomeless() {
        return homeless;
    }

    public Iterable<? extends WorkInfo> getWorkInfo() {
        return workInfo;
    }

    public CodeNamePair getCitizenship() {
        return citizenship;
    }

    public DocInfo getPassport() {
        return passport;
    }

    public InsuranceInfo getOmsInfo() {
        return omsInfo;
    }

    /**
     * Пол пациента
     */
    public static enum Gender {
        F,
        M;
    }

    public static class Privilege {
        public CodeNamePair getCodeAndName() {
            return codeAndName;
        }

        private final CodeNamePair codeAndName;
        private final XMLGregorianCalendar docBegDate;
        private final XMLGregorianCalendar expirationDate;

        public Privilege(ru.korus.tmis.core.entity.model.fd.ClientSocStatus clientSocStatus) {
            RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
            codeAndName = new CodeNamePair(socStatusType.getCode(), socStatusType.getName());
            docBegDate = getXmlGregorianCalendar(clientSocStatus.getBegDate());
            expirationDate =getXmlGregorianCalendar(clientSocStatus.getEndDate());
        }

        public XMLGregorianCalendar getDocBegDate() {
            return docBegDate;
        }

        public XMLGregorianCalendar getExpirationDate() {
            return expirationDate;
        }
    }

    public static class SocialStatus {
        private final CodeNamePair codeAndName;

        public CodeNamePair getCodeAndName() {
            return codeAndName;
        }

        public SocialStatus(ru.korus.tmis.core.entity.model.fd.ClientSocStatus clientSocStatus) {
            RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
            codeAndName = new CodeNamePair(socStatusType.getCode(), socStatusType.getName());
        }
    }


    /**
     * Фамилия
     */
    final private String familyName;
    /**
     * Имя
     */
    final private String givenName;
    /**
     * Отчество
     */
    final private String middleName;
    /**
     * Пол
     */
    final private Gender gender;
    /**
     * Дата и время рождения
     */
    final private XMLGregorianCalendar birthDate;
    /**
     * Уникальный идентификатор пациента в МИС
     */
    final private Integer tmisId;
    /**
     * СНИЛС
     */
    final private String snils;
    /**
     * Номер полиса единого образца
     */
    final private String enpNumber;
    /**
     * Cвидетельства о рождении (например "V-32 886888")
     */
    final private DocInfo birthCertificate;
    /**
     * Домашний телефон
     */
    final private String homePhoneNumber;
    /**
     * Рабочий телефон
     */
    final private String workPhoneNumber;
    /**
     * Мобильный телефон
     */
    final private String mobilePhoneNumber;
    /**
     * Место рождения
     */
    final private String birthPlace;
    /**
     * Адрес регистрации
     */
    final private AddrInfo regAddr;
    /**
     * Адрес проживания
     */
    private final AddrInfo actualAddr;


    public ClientInfo(Patient client, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal) {
        AddrInfo regAddr = null;
        AddrInfo actualAddr = null;
        String dwellingType = null;
        // Список адресов пациента
        List<ClientAddress> addresses = client.getActiveClientAddresses();
        for (ClientAddress clientAddress : addresses) {
            if (clientAddress.getAddressType() == 0) { // елси адрес регистрации (0-регистрации, 1-проживания)
                regAddr = new AddrInfo(clientAddress, dbSchemeKladrBeanLocal);
                dwellingType = clientAddress.getLocalityType().toString();
            } else if (clientAddress.getAddressType() == 1) { // елси адрес проживания (0-регистрации, 1-проживания)
                actualAddr = new AddrInfo(clientAddress, dbSchemeKladrBeanLocal);
            }
        }
        this.regAddr = regAddr;
        this.actualAddr = actualAddr;

        this.familyName = client.getLastName();
        this.givenName = client.getFirstName();
        this.middleName = client.getPatrName();

        if (client.getSex() == 1) {
            this.gender = Gender.M;
        } else if (client.getSex() == 2) {
            this.gender = Gender.F;
        } else {
            this.gender = null;
        }

        this.birthDate = getXmlGregorianCalendar(client.getBirthDate());

        this.tmisId = client.getId();
        this.snils = client.getSnils();
        ClientPolicy clientPolicyEnp = getEnp(client);
        this.enpNumber = clientPolicyEnp == null ? null : clientPolicyEnp.getNumber();

        ClientPolicy clientPolicyOms = getOMS(client);
        this.omsInfo = clientPolicyOms == null ? null : new InsuranceInfo(clientPolicyOms);

        ClientPolicy clientPolicyDms = getDms(client);
        this.dmsInfo = clientPolicyDms == null ? null : new InsuranceInfo(clientPolicyDms);


        final ClientDocument passport = getDoc(client, PASSPORT);
        this.passport = passport == null ? null : new DocInfo(passport);

        final ClientDocument cbt = getDoc(client, CBT);
        this.birthCertificate = cbt == null ? null : new DocInfo(cbt);

        final ClientContact homeTel = getContact(client, HOME_TEL);
        this.homePhoneNumber = homeTel == null ? null : homeTel.getContact();

        final ClientContact workTel = getContact(client, WORK_TEL);
        this.workPhoneNumber = workTel == null ? null : workTel.getContact();

        final ClientContact mobTel = getContact(client, MOB_TEL);
        this.mobilePhoneNumber = mobTel == null ? null : mobTel.getContact();

        this.birthPlace = client.getBirthPlace();

        this.dwellingType = dwellingType;

        privileges = initPriveleges(client);
        socialStatusList = initSocialStatus(client);
        homeless = initIsFromeSocGroup("БОМЖ");
        servicemanFamily = initIsFromeSocGroup("Член семьи военнослужащего")
        citizenship = initCitizenship(client);
        final RbBloodType bloodType = client.getBloodType();
        bloodGroup = bloodType.getId() > 0 ? bloodType.getCode() : null;
    }

    private CodeNamePair initCitizenship(Patient client) {
        CodeNamePair res = null;
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if( CITIZENSHIP_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
                res = new CodeNamePair(null, socStatusType.getName());
            }
        }
        return res;
    }

    static private  XMLGregorianCalendar getXmlGregorianCalendar(Date birthDate) {
        XMLGregorianCalendar birth = null;
        if (birthDate != null) {
            try {
                birth = Database.toGregorianCalendar(birthDate);
            } catch (DatatypeConfigurationException e) {
            }
        }
        return birth;
    }

    private Iterable<Privilege> initPriveleges(Patient client) {
        List<Privilege> res = new LinkedList<Privilege>();
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if( PRIVILEGE_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                final Privilege privelege = new Privilege(clientSocStatus);
                res.add(privelege);
            }
        }
    }

    private Iterable<SocialStatus> initSocialStatus(Patient client) {
        List<SocialStatus> res = new LinkedList<Privilege>();
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if( SOCIAL_STATUS_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                final SocialStatus socStatus = new SocialStatus(clientSocStatus);
                res.add(socStatus);
            }
        }
    }

    private boolean initIsFromeSocGroup(Patient client, String name) {
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if(name.equals(clientSocStatus.clientSocStatus.getSocStatusType().getName())) {
                return true;
            }
        }
        return false;
    }


    private ClientContact getContact(Patient client, String type) {
        List<ClientContact> docs = client.getClientContacts();
        for (ClientContact contact : docs) {
            if (contact.isDeleted() && contact.getContactType() != null &&
                    type.equals(contact.getContactType().getCode()))
                return contact;
        }
        return null;
    }

    /**
     * @param client
     * @return
     */
    private ClientDocument getDoc(final Patient client, final String type) {
        List<ClientDocument> docs = client.getClientDocuments();
        for (ClientDocument doc : docs) {
            if (doc.isDeleted() && doc.getDocumentType() != null &&
                    doc.getDocumentType().getCode().equals(type))
                return doc;
        }
        return null;
    }

    private ClientPolicy getDms(Patient client) {
        return getClientPolicyByCode(client, DMS);
    }


    private ClientPolicy getEnp(Patient client) {
        return getClientPolicyByCode(client, CMI_COMMON_ELECTRON);
    }

    private ClientPolicy getClientPolicyByCode(Patient client, String code) {
        List<ClientPolicy> policies = client.getClientPolicies();
        for (ClientPolicy policy : policies) {
            if (policy.isDeleted() && policy.getPolicyType() != null &&
                    policy.getPolicyType().getCode().equals(code))
                return policy;
        }
        return null;
    }

    /**
     * @param client
     * @return
     */
    private ClientPolicy getOMS(Patient client) {
        //TODO: use getClientPolicyByCode !
        List<ClientPolicy> policies = client.getClientPolicies();
        for (ClientPolicy policy : policies) {
            if (policy.isDeleted() &&
                    (policy.getPolicyType().getCode().equals(OMS_LOC) ||
                            policy.getPolicyType().getCode().equals(OMS_WORK)))
                return policy;
        }
        return null;
    }


    /**
     * @return the familyName
     */
    public String getFamilyName() {
        return familyName;
    }

    /**
     * @return the givenName
     */
    public String getGivenName() {
        return givenName;
    }

    /**
     * @return the middleName
     */
    public String getMiddleName() {
        return middleName;
    }

    /**
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * @return the birhtDate
     */
    public XMLGregorianCalendar getBirthDate() {
        return birthDate;
    }

    /**
     * @return the tmisId
     */
    public Integer getTmisId() {
        return tmisId;
    }

    /**
     * @return the snils
     */
    public String getSnils() {
        return snils;
    }

    /**
     * @return the enpNumber
     */
    public String getEnpNumber() {
        return enpNumber;
    }

    /**
     * /**
     *
     * @return the homePhoneNumber
     */
    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    /**
     * @return the workPhoneNumber
     */
    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    /**
     * @return the mobilePhoneNumber
     */
    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    /**
     * @return the birthCertificateNumber
     */
    public DocInfo getBirthCertificate() {
        return birthCertificate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public AddrInfo getRegAddr() {
        return regAddr;
    }

    public AddrInfo getActualAddr() {
        return actualAddr;
    }

    public class WorkInfo {
        final private String name;
        final private String pos;
        final private CodeNamePair okved;
        final private String harmful;

        public WorkInfo(ClientWork clientWork) {
            String name = clientWork.getFreeInput();
            //TODO: Implements: Organisation.shortName<=Organisation.id<=ClientWork.org_id/ClientWork.freeInput
            this.name = name;
            this.pos = clientWork.getPost();
            this.okved = new CodeNamePair(null, clientWork.getOkved());
            this.harmful = null; //TODO: Implements: rbHurtType.name<=rbHurtType.id<=ClientWork_Hurt.hurtType_id
        }

        public String getName() {
            return name;
        }

        public String getPos() {
            return pos;
        }

        public CodeNamePair getOkved() {
            return okved;
        }

        public String getHarmful() {
            return harmful;
        }
    }

    public static class DocInfo {

        private final String serial;
        private final String number;
        private final String issued;
        private final XMLGregorianCalendar createDate;
        private final XMLGregorianCalendar expirationDate;

        public DocInfo(ClientDocument passport) {
            serial = passport.getSerial();
            number = passport.getNumber();
            issued = passport.getIssued();
            createDate = getXmlGregorianCalendar(passport.getDate());
            expirationDate =getXmlGregorianCalendar(passport.getEndDate());
        }

        public String getSerial() {
            return serial;
        }

        public String getNumber() {
            return number;
        }

        public String getIssued() {
            return issued;
        }

        public XMLGregorianCalendar getCreateDate() {
            return createDate;
        }

        public XMLGregorianCalendar getExpirationDate() {
            return expirationDate;
        }
    }

    public static class InsuranceInfo {
        private final CodeNamePair organization;
        private final String serial;
        private final String number;
        private final XMLGregorianCalendar begDate;
        private final XMLGregorianCalendar endDate;
        private final CodeNamePair area;
        private final String okato;
        private final CodeNamePair policyTypeName;

        public InsuranceInfo(ClientPolicy clientPolicy) {
            final Organisation insurer = clientPolicy.getInsurer();
            organization = new CodeNamePair(insurer.getInfisCode(), insurer.getShortName());
            serial = clientPolicy.getSerial();
            number = clientPolicy.getNumber();
            begDate = getXmlGregorianCalendar(clientPolicy.getBegDate());
            endDate = getXmlGregorianCalendar(clientPolicy.getEndDate());
            String regName = null;//TODO: вычислить по КЛАДР территории страхования insurer.getArea()
            area = new CodeNamePair(insurer.getArea(), regName);
            okato = insurer.getOkato();
            final RbPolicyType policyType = clientPolicy.getPolicyType();
            policyTypeName = policyType == null ? null : new CodeNamePair(null, policyType.getName());
        }

        public CodeNamePair getOrganization() {
            return organization;
        }

        public String getSerial() {
            return serial;
        }

        public String getNumber() {
            return number;
        }

        public XMLGregorianCalendar getBegDate() {
            return begDate;
        }

        public XMLGregorianCalendar getEndDate() {
            return endDate;
        }

        public CodeNamePair getArea() {
            return area;
        }

        public String getOkato() {
            return okato;
        }

        public CodeNamePair getPolicyTypeName() {
            return policyTypeName;
        }
    }
}


