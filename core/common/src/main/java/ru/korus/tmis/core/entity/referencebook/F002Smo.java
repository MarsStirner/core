package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 14:35 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Единый реестр страховых медицинских организаций, осуществляющих деятельность в сфере обязательного
 * медицинского страхования (SMO)<br>
 */
@Entity
@Table(name = "rb_F002_SMO", catalog = "", schema = "")
public class F002Smo {
    /**
     * Код субъекта РФ по ОКАТО, где зарегистрирована СМО
     */
    @Id
    @Column(name = "tf_okato")
    private String tfOkato;

    /**
     * Код СМО в кодировке единого реестра СМО
     */
    @Column(name = "smocod")
    private String smocod;

    /**
     * Наименование СМО (полное)
     */
    @Column(name = "nam_smop")
    private String namSmop;

    /**
     * Наименование СМО (краткое)
     */
    @Column(name = "nam_smok")
    private String namSmok;

    /**
     * Идентификационный номер налогоплательщика
     */
    @Column(name = "inn")
    private String inn;

    /**
     * ОГРН СМО
     */
    @Column(name = "Ogrn")
    private String ogrn;

    /**
     * Код причины постановки на учет налогоплательщика
     */
    @Column(name = "KPP")
    private String kpp;

    /**
     * Почтовый индекс юридического адреса
     */
    @Column(name = "index_j")
    private String indexJ;

    /**
     * Юридический адрес СМО (субъект, район, город, населенный пункт, улица, номер дома (владение), номер корпуса/строения)
     */
    @Column(name = "addr_j")
    private String addrJ;

    /**
     * Почтовый индекс фактического адреса
     */
    @Column(name = "index_f")
    private String indexF;

    /**
     * Фактический адрес СМО (субъект, район, город, населенный пункт, улица, номер дома (владение), номер корпуса/строения)
     */
    @Column(name = "addr_f")
    private String addrF;

    /**
     * Организационно-правовая форма СМО по ОКОПФ
     */
    @Column(name = "okopf")
    private String okopf;

    /**
     * Фамилия руководителя
     */
    @Column(name = "fam_ruk")
    private String famRuk;

    /**
     * Имя руководителя
     */
    @Column(name = "im_ruk")
    private String imRuk;

    /**
     * Отчество руководителя
     */
    @Column(name = "ot_ruk")
    private String otRuk;

    /**
     * Телефон (с кодом города)
     */
    @Column(name = "phone")
    private String phone;

    /**
     * Факс (с кодом города)
     */
    @Column(name = "fax")
    private String fax;

    /**
     * Адрес электронной почты
     */
    @Column(name = "e_mail")
    private String eMail;

    /**
     * Адрес официального сайта в сети Интернет
     */
    @Column(name = "www")
    private String www;

    /**
     * Номер лицензии на осуществление деятельности
     */
    @Column(name = "n_doc")
    private String nDoc;

    /**
     * Дата выдачи лицензии на осуществление деятельности
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_start")
    private Date dStart;

    /**
     * Дата окончания действия лицензии на осуществление деятельности
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "data_e")
    private Date dataE;

    /**
     * Признак подчиненности (головная организация - 1, обособленное подразделение (филиал) - 2)
     */
    @Column(name = "org")
    private long org;

    /**
     * Дата включения в реестр СМО
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_begin")
    private Date dBegin;

    /**
     * Дата исключения из реестра СМО
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_end")
    private Date dEnd;

    /**
     * Причина исключения СМО из реестра СМО
     */
    @Column(name = "name_e")
    private String nameE;

    /**
     * Наличие действующих полисов СМО
     */
    @Column(name = "Nal_p")
    private String nalP;

    /**
     * Дата уведомления об осуществлении деятельности в сфере ОМС
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DUVED")
    private Date duved;

    /**
     * Численность застрахованных лиц в СМО, осуществляющей деятельность в сфере ОМС, на дату подачи уведомления об осуществлении деятельности в сфере ОМС
     */
    @Column(name = "kol_zl")
    private long kolZl;

    /**
     * Дата последнего редактирования
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_edit")
    private Date dEdit;

    public String getTfOkato() {
        return tfOkato;
    }

    public String getSmocod() {
        return smocod;
    }

    public String getNamSmop() {
        return namSmop;
    }

    public String getNamSmok() {
        return namSmok;
    }

    public String getInn() {
        return inn;
    }

    public String getOgrn() {
        return ogrn;
    }

    public String getKpp() {
        return kpp;
    }

    public String getIndexJ() {
        return indexJ;
    }

    public String getAddrJ() {
        return addrJ;
    }

    public String getIndexF() {
        return indexF;
    }

    public String getAddrF() {
        return addrF;
    }

    public String getOkopf() {
        return okopf;
    }

    public String getFamRuk() {
        return famRuk;
    }

    public String getImRuk() {
        return imRuk;
    }

    public String getOtRuk() {
        return otRuk;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String geteMail() {
        return eMail;
    }

    public String getWww() {
        return www;
    }

    public String getnDoc() {
        return nDoc;
    }

    public Date getdStart() {
        return dStart;
    }

    public Date getDataE() {
        return dataE;
    }

    public long getOrg() {
        return org;
    }

    public Date getdBegin() {
        return dBegin;
    }

    public Date getdEnd() {
        return dEnd;
    }

    public String getNameE() {
        return nameE;
    }

    public String getNalP() {
        return nalP;
    }

    public Date getDuved() {
        return duved;
    }

    public long getKolZl() {
        return kolZl;
    }

    public Date getdEdit() {
        return dEdit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F002Smo{");
        sb.append("tfOkato='").append(tfOkato).append('\'');
        sb.append(", smocod='").append(smocod).append('\'');
        sb.append(", namSmop='").append(namSmop).append('\'');
        sb.append(", namSmok='").append(namSmok).append('\'');
        sb.append(", inn='").append(inn).append('\'');
        sb.append(", ogrn='").append(ogrn).append('\'');
        sb.append(", kpp='").append(kpp).append('\'');
        sb.append(", indexJ='").append(indexJ).append('\'');
        sb.append(", addrJ='").append(addrJ).append('\'');
        sb.append(", indexF='").append(indexF).append('\'');
        sb.append(", addrF='").append(addrF).append('\'');
        sb.append(", okopf='").append(okopf).append('\'');
        sb.append(", famRuk='").append(famRuk).append('\'');
        sb.append(", imRuk='").append(imRuk).append('\'');
        sb.append(", otRuk='").append(otRuk).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", eMail='").append(eMail).append('\'');
        sb.append(", www='").append(www).append('\'');
        sb.append(", nDoc='").append(nDoc).append('\'');
        sb.append(", dStart=").append(dStart);
        sb.append(", dataE=").append(dataE);
        sb.append(", org=").append(org);
        sb.append(", dBegin=").append(dBegin);
        sb.append(", dEnd=").append(dEnd);
        sb.append(", nameE='").append(nameE).append('\'');
        sb.append(", nalP='").append(nalP).append('\'');
        sb.append(", duved=").append(duved);
        sb.append(", kolZl=").append(kolZl);
        sb.append(", dEdit=").append(dEdit);
        sb.append('}');
        return sb.toString();
    }
}
