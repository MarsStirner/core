package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.entity.model.fd.ClientSocStatus;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Arrays;
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
    private static final String PASSPORT = "14";
    /**
     * Код типа документа - "СВИД О РОЖД"
     */
    private static final String CBT = "03";
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
    private static final String DMS = "vmi";

    /**
     * Номер полиса единого образца
     */
    private static final String CMI_COMMON_ELECTRON = "cmiCommonElectron";
    /**
     * Код типа контакта "домашний телефон"
     */
    private static final String HOME_TEL_CODE = "1";
    /**
     * Код типа контакта "рабочий телефон"
     */
    private static final String WORK_TEL_CODE = "2";
    /**
     * Код типа контакта "мобильный телефон"
     */
    private static final String MOB_TEL_CODE = "3";
    /**
     * Код типа контакта "Номер ребенка при многоплодных родах"
     */
    private static final String BORN_INDEX_CODE = "9";


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
    private static final String CITIZENSHIP_CODE = "4";

    private final Iterable<WorkInfo> workInfo;

    /**
     * Гражданство
     */
    final private CodeNameSystem citizenship;

    final private DocInfo passport;

    final private InsuranceInfo omsInfo;

    /**
     * Группа крови
     */
    private final String bloodGroup;

    /**
     * Резус фактор
     */
    private final Boolean bloodRhesus;

    /**
     * Данные новорожденного
     */
    private final String bornIndex;

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

    public CodeNameSystem getCitizenship() {
        return citizenship;
    }

    public DocInfo getPassport() {
        return passport;
    }

    public InsuranceInfo getOmsInfo() {
        return omsInfo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public Boolean getBloodRhesus() {
        return bloodRhesus;
    }

    public String getBornIndex() {
        return bornIndex;
    }

    /**
     * Пол пациента
     */
    public static enum Gender {
        F,
        M;
    }

    /**
     * Данные о льготах
     */
    public static class Privilege {

        /**
         * Категория льготы
         */
        private final CodeNameSystem category;

        /**
         * Дата начала действия льготы
         */
        private final XMLGregorianCalendar docBegDate;

        /**
         * Дата истечения срока действия льготы
         */
        private final XMLGregorianCalendar expirationDate;

        public Privilege(ru.korus.tmis.core.entity.model.fd.ClientSocStatus clientSocStatus) {
            RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();

            category = RbManager.get(RbManager.RbType.rbSocStatusType, CodeNameSystem.newInstance(socStatusType.getCode(), socStatusType.getName(), null));

            docBegDate = getXmlGregorianCalendar(clientSocStatus.getBegDate());
            expirationDate =getXmlGregorianCalendar(clientSocStatus.getEndDate());
        }

        public XMLGregorianCalendar getDocBegDate() {
            return docBegDate;
        }

        public XMLGregorianCalendar getExpirationDate() {
            return expirationDate;
        }

        public CodeNameSystem getCategory() {
            return category;
        }

    }

    public static class SocialStatus {
        private final CodeNameSystem codeAndName;

        public CodeNameSystem getCodeAndName() {
            return codeAndName;
        }

        public SocialStatus(ru.korus.tmis.core.entity.model.fd.ClientSocStatus clientSocStatus) {
            RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
            codeAndName = RbManager.get(RbManager.RbType.rbSocStatusType,
                    CodeNameSystem.newInstance(socStatusType.getCode(), socStatusType.getName(), "1.2.643.5.1.13.2.1.1.366"));
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
                dwellingType = clientAddress.getLocalityType().equals(1) ? "1" : "2";
            } else if (clientAddress.getAddressType() == 1) { // елси адрес проживания (0-регистрации, 1-проживания)
                actualAddr = new AddrInfo(clientAddress, dbSchemeKladrBeanLocal);
            }
        }
        this.regAddr = regAddr;
        this.actualAddr = actualAddr;

        this.familyName = notEmpty(client.getLastName());
        this.givenName = notEmpty(client.getFirstName());
        this.middleName = notEmpty(client.getPatrName());

        Gender gender = null;
        if (client.getSex() == 1) {
            gender = Gender.M;
        } else if (client.getSex() == 2) {
            gender = Gender.F;
        }

        this.gender = gender;

        this.birthDate = getXmlGregorianCalendar(client.getBirthDate());

        this.tmisId = client.getId();
        this.snils = EmployeeInfo.toHsSnils(notEmpty(client.getSnils()));
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

        final ClientContact homeTel = getContact(client, HOME_TEL_CODE);
        this.homePhoneNumber = homeTel == null ? null : homeTel.getContact();

        final ClientContact workTel = getContact(client, WORK_TEL_CODE);
        this.workPhoneNumber = workTel == null ? null : workTel.getContact();

        final ClientContact mobTel = getContact(client, MOB_TEL_CODE);
        this.mobilePhoneNumber = mobTel == null ? null : mobTel.getContact();

        final ClientContact bornIndexContact = getContact(client, BORN_INDEX_CODE);
        this.bornIndex = bornIndexContact == null ? null : bornIndexContact.getContact();


        this.birthPlace = client.getBirthPlace();

        this.dwellingType = dwellingType;

        privileges = initPriveleges(client);
        socialStatusList = initSocialStatus(client);
        homeless = initIsFromeSocGroup(client, "БОМЖ");
        servicemanFamily = initIsFromeSocGroup(client, "Член семьи военнослужащего");
        citizenship = initCitizenship(client);
        final RbBloodType bloodType = client.getBloodType();
        final List<String> supportedBloodGroupCode = Arrays.asList("1", "2", "3", "4");
        String code = bloodType.getCode();
        bloodGroup = (bloodType == null || code == null || code.length() != 2)  ? null : (
                 supportedBloodGroupCode.contains(bloodType.getCode().substring(0, 1)) ? bloodType.getCode().substring(0, 1) : null ) ;
        final List<String> supportedBloodRhesusCode = Arrays.asList("+", "-");
        bloodRhesus = (bloodGroup == null || bloodType == null || code == null || code.length() != 2) ? null : (
                  supportedBloodRhesusCode.contains(bloodType.getCode().substring(1, 2)) ? "+".equals(bloodType.getCode().substring(1, 2)) : null) ;

        workInfo =  initWorkInfo(client);
    }

    private String notEmpty(String lastName) {
        return "".equals(lastName) ? null : lastName;
    }

    private Iterable<WorkInfo> initWorkInfo(Patient client) {
        List<WorkInfo> res = new LinkedList<WorkInfo>();
        for(ClientWork clientWork : client.getClientWorks()) {
            res.add(new WorkInfo(clientWork));
        }
        return res;
    }

    private CodeNameSystem initCitizenship(Patient client) {
        CodeNameSystem res = null;
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if( CITIZENSHIP_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
                res = new CodeNameSystem(null, socStatusType.getName());
            }
        }
        return res;
    }

    protected static XMLGregorianCalendar getXmlGregorianCalendar(Date birthDate) {
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
            if( ClientSocStatus.PRIVILEGE_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                final Privilege privelege = new Privilege(clientSocStatus);
                if(privelege.getCategory() != null) {
                    res.add(privelege);
                }
            }
        }
        return res;
    }

    private Iterable<SocialStatus> initSocialStatus(Patient client) {
        List<SocialStatus> res = new LinkedList<SocialStatus>();
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            if( ClientSocStatus.SOCIAL_STATUS_CODE.equals(clientSocStatus.getSocStatusClass().getCode()) ) {
                final SocialStatus socStatus = new SocialStatus(clientSocStatus);
                res.add(socStatus);
            }
        }
        return res;
    }

    private boolean initIsFromeSocGroup(Patient client, String name) {
        for (ClientSocStatus clientSocStatus : client.getClientSocStatuses()) {
            final RbSocStatusType socStatusType = clientSocStatus.getSocStatusType();
            if(socStatusType != null && name.equals(socStatusType.getName())) {
                return true;
            }
        }
        return false;
    }


    private ClientContact getContact(Patient client, String type) {
        List<ClientContact> docs = client.getClientContacts();
        for (ClientContact contact : docs) {
            if (!contact.isDeleted() && contact.getContactType() != null &&
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
        assert type != null;
        ClientDocument res = null;
        List<ClientDocument> docs = client.getClientDocuments();
        for (ClientDocument doc : docs) {
            if (!doc.isDeleted() && doc.getDocumentType() != null &&
                    type.equals(doc.getDocumentType().getCode())) {
                res = doc;
                break;
            }
        }
        return res;
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
            if (!policy.isDeleted() && policy.getPolicyType() != null &&
                    code.equals(policy.getPolicyType().getCode()))
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
        ClientPolicy res = null;
        List<ClientPolicy> policies = client.getClientPolicies();
        for (ClientPolicy policy : policies) {
            //[12:06:57] Сергей Загребельный: т.е. все что не ДМС - это ОМС?
            //[12:07:09] Александр Мартынов: ага
            if (!policy.isDeleted() && policy.getPolicyType() != null && !DMS.equals(policy.getPolicyType().getCode()) ) {
                res = policy;
                break;
            }
        }
        return res;
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
        final private CodeNameSystem okved;
        final private String harmful;
        final private String ogrn;

        public WorkInfo(ClientWork clientWork) {
            String name = "".equals(clientWork.getFreeInput()) ? null : clientWork.getFreeInput();
            String ogrn = null;
            final Organisation organisation = clientWork.getOrganisation();
            if(organisation != null) {
                name = organisation.getShortName();
                ogrn = organisation.getOgrn();
            }
            this.name = name;
            this.pos = clientWork.getPost();
            this.ogrn = ogrn;

            //TODO: skype:
            //  [17:46:19] Сергей Загребельный: в БД может быть несколько
            //  [17:47:47] Сергей Загребельный: как быть? какую вредность передавать?
            //  [17:48:21] Александр Мартынов: любую... сомневаюсь что вообще кто то это заполняет
            this.harmful = clientWork.getClientWorkHurts().isEmpty() ? null : clientWork.getClientWorkHurts().get(0).getHurtType().getName();

            this.okved = clientWork.getOkved() == null ? null :
                    RbManager.get(RbManager.RbType.rbOKVED,
                            CodeNameSystem.newInstance(clientWork.getOkved(), null, "1.2.643.5.1.13.2.1.1.62"));
        }

        public String getName() {
            return name;
        }

        public String getPos() {
            return pos;
        }

        public CodeNameSystem getOkved() {
            return okved;
        }

        public String getHarmful() {
            return harmful;
        }

        public String getOgrn() {
            return ogrn;
        }

    }

    public static class DocInfo {

        private final String serial;
        private final String number;
        private final String issued;
        private final XMLGregorianCalendar createDate;
        private final XMLGregorianCalendar expirationDate;
        private final CodeNameSystem docType;

        public DocInfo(ClientDocument passport) {
            serial = passport.getSerial();
            number = passport.getNumber();
            issued = "".equals(passport.getIssued()) ? null : passport.getIssued();
            createDate = getXmlGregorianCalendar(passport.getDate());
            expirationDate =getXmlGregorianCalendar(passport.getEndDate());
            final RbDocumentType documentType = passport.getDocumentType();
            docType = documentType == null ? null :
                    RbManager.get(RbManager.RbType.rbDocumentType,
                            CodeNameSystem.newInstance(documentType.getCode(), documentType.getName(), "1.2.643.5.1.13.2.1.1.498"));
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

        public CodeNameSystem getDocType() {
            return docType;
        }
    }

    public static class InsuranceInfo {
        private final CodeNameSystem organization;
        private final String serial;
        private final String number;
        private final XMLGregorianCalendar begDate;
        private final XMLGregorianCalendar endDate;
        private final CodeNameSystem area;
        private final String okato;
        private final CodeNameSystem policyTypeName;

        public InsuranceInfo(ClientPolicy clientPolicy) {
            final Organisation insurer = clientPolicy.getInsurer();
            organization = RbManager.get(RbManager.RbType.MDN366,
                    CodeNameSystem.newInstance(insurer.getInfisCode(), insurer.getShortName(), "1.2.643.5.1.13.2.1.1.635"));
            serial = clientPolicy.getSerial();
            number = clientPolicy.getNumber();
            begDate = getXmlGregorianCalendar(clientPolicy.getBegDate());
            endDate = getXmlGregorianCalendar(clientPolicy.getEndDate());
            area = RbManager.get(RbManager.RbType.KLD116, CodeNameSystem.newInstance(insurer.getArea(), null, "1.2.643.5.1.13.2.1.1.635"));
            okato = insurer.getOkato();
            final RbPolicyType policyType = clientPolicy.getPolicyType();
            policyTypeName = policyType == null ? null : new CodeNameSystem(null, policyType.getName());
        }

        public CodeNameSystem getOrganization() {
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

        public CodeNameSystem getArea() {
            return area;
        }

        public String getOkato() {
            return okato;
        }

        public CodeNameSystem getPolicyTypeName() {
            return policyTypeName;
        }
    }
}


