package ru.korus.tmis.pix.sda;

import java.util.Date;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.database.dbutil.Database;
import ru.korus.tmis.core.entity.model.AddressHouse;
import ru.korus.tmis.core.entity.model.ClientAddress;
import ru.korus.tmis.core.entity.model.ClientContact;
import ru.korus.tmis.core.entity.model.ClientDocument;
import ru.korus.tmis.core.entity.model.ClientPolicy;
import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;

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

    /**
     * Пол пациента
     */
    public static enum Gender {
        F,
        M;
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
     * Номер полиса ОМС
     */
    final private String policyNumber;
    /**
     * Серия/номер паспорта в формате "NNNN NNNNNN"
     */
    final private String passNumber;
    /**
     * Серия/номер свидетельства о рождении (например "V-32 886888")
     */
    final private String birthCertificateNumber;
    /**
     * Улица и дом (например ул. Ленина, д.1)
     */
    final private String addrStreet;
    /**
     * Город
     */
    final private String addrCity;
    /**
     * Область
     */
    final private String addrState;
    /**
     * Почтовый индекс
     */
    final private String addrZip;
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

    public ClientInfo(Patient client, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal) {
        String addrCity = null;
        String addrState = null;
        String addrStreet = null;
        String addrZip = null;
        // Список адресов пациента
        List<ClientAddress> addresses = client.getActiveClientAddresses();
        if (!addresses.isEmpty()) {
            ClientAddress homeAddr = addresses.get(0);
            // Поиск адреса регистрации
            for (ClientAddress clientAddress : addresses) {
                if (clientAddress.getAddressType() == 0) { // елси адрес регистрации (0-регистрации, 1-проживания)
                    homeAddr = clientAddress;
                    break;
                }
            }
            if (homeAddr.getAddress() != null && homeAddr.getAddress().getHouse() != null) {
                AddressHouse addrHouse = homeAddr.getAddress().getHouse();
                final String kladrCode = addrHouse.getKLADRCode();
                if (kladrCode != null && kladrCode.length() > 1) { // если задан код КЛАДР
                    Kladr kladr = null;
                    Kladr kladrState = null;
                    Street street = null;
                    try {
                        kladr = dbSchemeKladrBeanLocal.getKladrByCode(kladrCode);
                        // Регион пациента. Определяется по двум старшим цифрам кода КЛАДР
                        kladrState = dbSchemeKladrBeanLocal.getKladrByCode(kladrCode.substring(0, 2) + "00000000000");
                        street = dbSchemeKladrBeanLocal.getStreetByCode(addrHouse.getKLADRStreetCode());
                    } catch (CoreException e) {
                    }
                    if (kladr != null) {
                        // Населенный пункт. Формируется как сокращенное наименования тип населённого пункта (г.) и названия
                        addrCity = kladr.getSocr() + "." + kladr.getName();
                        if (kladrState != null) {
                            addrState = kladrState.getSocr() + "." + kladrState.getName();
                        }
                        if (street != null) {
                            addrStreet =
                                    street.getOcatd() + "." + street.getName() + ("".equals(addrHouse.getNumber()) ? "" : addrHouse.getNumber())
                                            + addrHouse.getCorpus();
                            addrZip = street.getIndex();
                        }
                    }

                }
            }
        }
        this.addrCity = addrCity;
        this.addrState = addrState;
        this.addrStreet = addrStreet;
        this.addrZip = addrZip;

        this.familyName = client.getLastName();
        this.givenName = client.getFirstName();
        this.middleName = client.getPatrName();

        if (client.getSex() == 1) {
            this.gender = Gender.F;
        } else if (client.getSex() == 2) {
            this.gender = Gender.M;
        } else {
            this.gender = null;
        }

        XMLGregorianCalendar birth = null;
        Date birthDate = client.getBirthDate();
        if (birthDate != null) {
            try {
                birth = Database.toGregorianCalendar(birthDate);
            } catch (DatatypeConfigurationException e) {
            }
        }
        this.birthDate = birth;

        this.tmisId = client.getId();
        this.snils = client.getSnils();
        ClientPolicy clientPolicy = getOMS(client);
        this.policyNumber = clientPolicy == null ? null : (clientPolicy.getNumber() + " " + clientPolicy.getSerial());

        final ClientDocument passport = getDoc(client, PASSPORT);
        this.passNumber = passport == null ? null : (passport.getSerial() + " " + passport.getNumber());

        final ClientDocument cbt = getDoc(client, CBT);
        this.birthCertificateNumber = cbt == null ? null : (cbt.getSerial() + " " + cbt.getNumber());

        final ClientContact homeTel = getContact(client, HOME_TEL);
        this.homePhoneNumber = homeTel == null ? null : homeTel.getContact();

        final ClientContact workTel = getContact(client, WORK_TEL);
        this.workPhoneNumber = workTel == null ? null : workTel.getContact();

        final ClientContact mobTel = getContact(client, MOB_TEL);
        this.mobilePhoneNumber = mobTel == null ? null : mobTel.getContact();

    }

    /**
     * @param client
     * @param homeTel
     * @return
     */
    private ClientContact getContact(Patient client, String type) {
        List<ClientContact> docs = client.getClientContacts();
        for (ClientContact contact : docs) {
            if (contact.isDeleted() &&
                    contact.getContactType().getCode().equals(type))
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
            if (doc.isDeleted() &&
                    doc.getDocumentType().getCode().equals(type))
                return doc;
        }
        return null;
    }

    /**
     * @param client
     * @return
     */
    private ClientPolicy getOMS(Patient client) {
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
     * @return the policyNumber
     */
    public String getPolicyNumber() {
        return policyNumber;
    }

    /**
     * @return the addrStreet
     */
    public String getAddrStreet() {
        return addrStreet;
    }

    /**
     * @return the addrCity
     */
    public String getAddrCity() {
        return addrCity;
    }

    /**
     * @return the addrState
     */
    public String getAddrState() {
        return addrState;
    }

    /**
     * @return the addrZip
     */
    public String getAddrZip() {
        return addrZip;
    }

    /**
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
     * @return the passNumber
     */
    public String getPassNumber() {
        return passNumber;
    }

    /**
     * @return the birthCertificateNumber
     */
    public String getBirthCertificateNumber() {
        return birthCertificateNumber;
    }
}
