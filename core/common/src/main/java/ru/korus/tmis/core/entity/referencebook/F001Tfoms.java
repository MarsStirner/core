package ru.korus.tmis.core.entity.referencebook;

import javax.persistence.*;
import java.util.Date;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        30.04.13, 14:47 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Справочник территориальных фондов ОМС (TFOMS)<br>
 */
@Entity
@Table(name = "rb_F001_Tfoms", catalog = "", schema = "")
public class F001Tfoms {

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
    @Column(name = "index")
    private String index;

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
    private String kfTf;

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
    private Date dateBegin;

    /**
     * Дата исключения из справочника
     */
    @Temporal(TemporalType.DATE)
    @Column(name = "d_end")
    private Date dateEnd;


    public String getTfKod() {
        return tfKod;
    }

    public String getTfOkato() {
        return tfOkato;
    }

    public String getTfOgrn() {
        return tfOgrn;
    }

    public String getNameTfp() {
        return nameTfp;
    }

    public String getNameTfk() {
        return nameTfk;
    }

    public String getIndex() {
        return index;
    }

    public String getAddress() {
        return address;
    }

    public String getFamDir() {
        return famDir;
    }

    public String getImDir() {
        return imDir;
    }

    public String getOtDir() {
        return otDir;
    }

    public String getPhone() {
        return phone;
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }

    public String getKfTf() {
        return kfTf;
    }

    public String getWww() {
        return www;
    }

    public Date getDateBegin() {
        return dateBegin;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("F001Tfoms{");
        sb.append("tfKod='").append(tfKod).append('\'');
        sb.append(", tfOkato='").append(tfOkato).append('\'');
        sb.append(", tfOgrn='").append(tfOgrn).append('\'');
        sb.append(", nameTfp='").append(nameTfp).append('\'');
        sb.append(", nameTfk='").append(nameTfk).append('\'');
        sb.append(", index='").append(index).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", famDir='").append(famDir).append('\'');
        sb.append(", imDir='").append(imDir).append('\'');
        sb.append(", otDir='").append(otDir).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", kfTf='").append(kfTf).append('\'');
        sb.append(", www='").append(www).append('\'');
        sb.append(", dateBegin=").append(dateBegin);
        sb.append(", dateEnd=").append(dateEnd);
        sb.append('}');
        return sb.toString();
    }
}
