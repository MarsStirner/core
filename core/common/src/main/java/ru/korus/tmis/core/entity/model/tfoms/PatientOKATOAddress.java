package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Author: Upatov Egor <br>
 * Date: 05.02.14, 18:53 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
//@Entity
public class PatientOKATOAddress implements Informationable {
    //@Id
    @Column(name = "OKATO")
    String OKATO;

    public PatientOKATOAddress() {
    }

    public PatientOKATOAddress(String OKATO) {

        this.OKATO = OKATO;
    }

    public PatientOKATOAddress(Object[] args) {
        if (args.length >= 1) {
            this.OKATO = (String) args[0];
        }
    }

    public String getOKATO() {
        return OKATO;
    }

    public void setOKATO(String OKATO) {
        this.OKATO = OKATO;
    }


    @Override
    public String getInfo() {
        return "OKATO=".concat(OKATO);
    }
}
