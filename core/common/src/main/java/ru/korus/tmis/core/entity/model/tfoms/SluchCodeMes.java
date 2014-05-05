package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.*;
import java.util.Date;

/**
 * Author: Upatov Egor <br>
 * Date: 03.03.14, 13:24 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

//@Entity
public class SluchCodeMes implements Informationable{
    //@Id
    @Column(name = "CODE_MES")
    String CODE_MES;

    public SluchCodeMes(String CODE_MES) {
        this.CODE_MES = CODE_MES;
    }

    public SluchCodeMes() {
    }

    public SluchCodeMes(Object[] args) {
        if(args.length >=1){
         this.CODE_MES = (String) args[0];
        }
    }

    @Override
    public String getInfo() {
        return "CODE_MES: " + (CODE_MES != null ? CODE_MES : "NULL");
    }

    public String getCODE_MES() {
        return CODE_MES;
    }

    public void setCODE_MES(String CODE_MES) {
        this.CODE_MES = CODE_MES;
    }
}
