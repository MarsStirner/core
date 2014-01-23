package ru.korus.tmis.core.entity.model.referencebook;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Справочник территориальных фондов ОМС (TFOMS)<br>
 * The class implements a Serializable interface, and that
 * allows it to be passed by value through a remote interface.
 */
@Entity
@Table(name = "rb_F001_Tfoms")
public class F001Tfoms implements Serializable {
    /**
     * Код ТФОМС
     */
    @Id
    @Column(name = "tf_kod")
    private String tfKod;

    /**
     * Код субъекта РФ по ОКАТО
     */
    @Column(name = "tf_okato")
    private String tfOkato;

    /**
     * ОГРН ТФОМС
     */
    @Column(name = "tf_ogrn")
    private String tfOgrn;

    /**
     * Наименование ТФОМС (полное)
     */
    @Column(name = "name_tfp")
    private String nameTfp;

    /**
     * Наименование ТФОМС (краткое)
     */
    @Column(name = "name_tfk")
    private String nameTfk;

    /**
     * Почтовый индекс
     */
    @Column(name = "idx")
    private String idx;

    /**
     * Адрес местонахождения ТФОМС (субъект, район,
     * город, населенный пункт, улица, номер дома
     * (владение), номер корпуса/строения)
     */
    @Column(name = "address")
    private String address;

    /**
     * Фамилия исполнительного директора
     */
    @Column(name = "fam_dir")
    private String famDir;

    /**
     * Имя
     */
    @Column(name = "im_dir")
    private String imDir;

    /**
     * Отчество
     */
    @Column(name = "ot_dir")
    private String otDir;

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
    private String email;

    /**
     * Количество филиалов ТФОМС
     */
    @Column(name = "kf_tf")
    private long kfTf;

    /**
     * Адрес официального сайта в сети Интернет
     */
    @Column(name = "www")
    private String www;

    /**
     * Дата последнего редактирования
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_edit")
    private Date dEdit;

    /**
     * Дата исключения из справочника
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_end")
    private Date dEnd;

    public F001Tfoms() {
    }

    public F001Tfoms(final String tfKod, final String tfOkato, final String tfOgrn, final String nameTfp, final String nameTfk,
                     final String idx, final String address, final String famDir, final String imDir, final String otDir,
                     final String phone,
                     final String fax, final String email, final long kfTf, final String www, final Date dEdit, final Date dEnd) {
        this.tfKod = tfKod;
        this.tfOkato = tfOkato;
        this.tfOgrn = tfOgrn;
        this.nameTfp = nameTfp;
        this.nameTfk = nameTfk;
        this.idx = idx;
        this.address = address;
        this.famDir = famDir;
        this.imDir = imDir;
        this.otDir = otDir;
        this.phone = phone;
        this.fax = fax;
        this.email = email;
        this.kfTf = kfTf;
        this.www = www;
        this.dEdit = dEdit;
        this.dEnd = dEnd;
    }


    public String getTfKod() {
        return tfKod;
    }

    public void setTfKod(String tfKod) {
        this.tfKod = tfKod;
    }

    public String getTfOkato() {
        return tfOkato;
    }

    public void setTfOkato(String tfOkato) {
        this.tfOkato = tfOkato;
    }

    public String getTfOgrn() {
        return tfOgrn;
    }

    public void setTfOgrn(String tfOgrn) {
        this.tfOgrn = tfOgrn;
    }

    public String getNameTfp() {
        return nameTfp;
    }

    public void setNameTfp(String nameTfp) {
        this.nameTfp = nameTfp;
    }

    public String getNameTfk() {
        return nameTfk;
    }

    public void setNameTfk(String nameTfk) {
        this.nameTfk = nameTfk;
    }

    public String getIndex() {
        return idx;
    }

    public void setIndex(String index) {
        this.idx = index;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFamDir() {
        return famDir;
    }

    public void setFamDir(String famDir) {
        this.famDir = famDir;
    }

    public String getImDir() {
        return imDir;
    }

    public void setImDir(String imDir) {
        this.imDir = imDir;
    }

    public String getOtDir() {
        return otDir;
    }

    public void setOtDir(String otDir) {
        this.otDir = otDir;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getKfTf() {
        return kfTf;
    }

    public void setKfTf(long kfTf) {
        this.kfTf = kfTf;
    }

    public String getWww() {
        return www;
    }

    public void setWww(String www) {
        this.www = www;
    }

    public Date getDEdit() {
        return dEdit;
    }

    public void setDEdit(Date dateBegin) {
        this.dEdit = dateBegin;
    }

    public Date getDEnd() {
        return dEnd;
    }

    public void setDEnd(Date dateEnd) {
        this.dEnd = dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F001Tfoms{");
        sb.append("tfKod='").append(tfKod).append('\'');
        sb.append(", tfOkato='").append(tfOkato).append('\'');
        sb.append(", tfOgrn='").append(tfOgrn).append('\'');
        sb.append(", nameTfp='").append(nameTfp).append('\'');
        sb.append(", nameTfk='").append(nameTfk).append('\'');
        sb.append(", idx='").append(idx).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", famDir='").append(famDir).append('\'');
        sb.append(", imDir='").append(imDir).append('\'');
        sb.append(", otDir='").append(otDir).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", kfTf=").append(kfTf);
        sb.append(", www='").append(www).append('\'');
        sb.append(", dEdit=").append(dEdit);
        sb.append(", dEnd=").append(dEnd);
        sb.append('}');
        return sb.toString();
    }
}
