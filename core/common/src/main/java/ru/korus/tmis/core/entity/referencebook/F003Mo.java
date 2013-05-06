package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        06.05.13, 14:54 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Единый реестр медицинских организаций, осуществляющих деятельность в сфере обязательного
 * медицинского страхования (MO)<br>
 */
@Entity
@Table(name = "rb_F003_MO", catalog = "", schema = "")
public class F003Mo {
    /**
     * Код субъекта РФ по ОКАТО, где расположена МО
     */
    @Id
    @Column(name = "tf_okato")
    private String tfOkato;

    /**
     * Код МО в кодировке единого реестра МО
     */
    @Column(name = "mcod")
    private String mcod;

    /**
     * Код МО в кодировке НСИ здравоохранения Самарской области
     */
    @Column(name = "lpu")
    private int lpu;

    /**
     * Наименование МО (полное)
     */
    @Column(name = "nam_mop")
    private String namMop;

    /**
     * Наименование МО (краткое)
     */
    @Column(name = "nam_mok")
    private String namMok;

    /**
     * Идентификационный номер налогоплательщика
     */
    @Column(name = "inn")
    private String inn;

    /**
     * ОГРН МО
     */
    @Column(name = "Ogrn")
    private String ogrn;

    /**
     * Код причины постановки на учет налогоплательщика
     */
    @Column(name = "KPP")
    private String kpp;

    /**
     * Почтовый индекс по адресу (месту) нахождения МО
     */
    @Column(name = "index_j")
    private String indexJ;

    /**
     * Адрес (место) нахождения МО (Субъект, район, город, населенный пункт, улица, номер дома (владение), номер корпуса/строения)
     */
    @Column(name = "addr_j")
    private String addrJ;

    /**
     * Организационно-правовая форма МО по ОКОПФ
     */
    @Column(name = "okopf")
    private String okopf;

    /**
     * Код ведомственной принадлежности МО
     */
    @Column(name = "vedpri")
    private long vedpri;

    /**
     * Признак подчиненности (головная организация-1, филиал -2)
     */
    @Column(name = "org")
    private long org;

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
     * Номер документа, дающего право в соответствии с законодательством РФ на осуществление медицинской деятельности
     */
    @Column(name = "n_doc")
    private String nDoc;

    /**
     * Дата выдачи документа, дающего право в соответствии с законодательством РФ на осуществление медицинской деятельности
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_start")
    private Date dStart;

    /**
     * Дата окончания срока действия документа, дающего право в соответствии с законодательством РФ на осуществление медицинской деятельности
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "data_e")
    private Date dataE;

    /**
     * Виды медицинской помощи, оказываемой медицинской организацией в рамках территориальной программы ОМС
     */
    @Column(name = "mp")
    private String mp;

    /**
     * Адрес официального сайта в сети Интернет
     */
    @Column(name = "www")
    private String www;

    /**
     * Дата включения в реестр МО
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_begin")
    private Date dBegin;

    /**
     * Дата исключения из реестра МО
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_end")
    private Date dEnd;

    /**
     * Причина исключения МО из реестра МО
     */
    @Column(name = "name_e")
    private String nameE;

    /**
     * Дата уведомления об осуществлении деятельности в сфере ОМС
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "DUVED")
    private Date duved;

    /**
     * Дата последнего редактирования реестра МО
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_edit")
    private Date dEdit;

    public String getTfOkato() {
        return tfOkato;
    }

    public String getMcod() {
        return mcod;
    }

    public String getNamMop() {
        return namMop;
    }

    public String getNamMok() {
        return namMok;
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

    public String getOkopf() {
        return okopf;
    }

    public long getVedpri() {
        return vedpri;
    }

    public long getOrg() {
        return org;
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

    public String getnDoc() {
        return nDoc;
    }

    public Date getdStart() {
        return dStart;
    }

    public Date getDataE() {
        return dataE;
    }

    public String getMp() {
        return mp;
    }

    public String getWww() {
        return www;
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

    public Date getDuved() {
        return duved;
    }

    public Date getdEdit() {
        return dEdit;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F003Mo{");
        sb.append("tfOkato='").append(tfOkato).append('\'');
        sb.append(", mcod='").append(mcod).append('\'');
        sb.append(", namMop='").append(namMop).append('\'');
        sb.append(", namMok='").append(namMok).append('\'');
        sb.append(", inn='").append(inn).append('\'');
        sb.append(", ogrn='").append(ogrn).append('\'');
        sb.append(", kpp='").append(kpp).append('\'');
        sb.append(", indexJ='").append(indexJ).append('\'');
        sb.append(", addrJ='").append(addrJ).append('\'');
        sb.append(", okopf='").append(okopf).append('\'');
        sb.append(", vedpri=").append(vedpri);
        sb.append(", org=").append(org);
        sb.append(", famRuk='").append(famRuk).append('\'');
        sb.append(", imRuk='").append(imRuk).append('\'');
        sb.append(", otRuk='").append(otRuk).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", eMail='").append(eMail).append('\'');
        sb.append(", nDoc='").append(nDoc).append('\'');
        sb.append(", dStart=").append(dStart);
        sb.append(", dataE=").append(dataE);
        sb.append(", mp='").append(mp).append('\'');
        sb.append(", www='").append(www).append('\'');
        sb.append(", dBegin=").append(dBegin);
        sb.append(", dEnd=").append(dEnd);
        sb.append(", nameE='").append(nameE).append('\'');
        sb.append(", duved=").append(duved);
        sb.append(", dEdit=").append(dEdit);
        sb.append('}');
        return sb.toString();
    }
}
