package ru.korus.tmis.core.entity.model.tfoms;

import javax.persistence.*;

import static ru.korus.tmis.core.entity.model.tfoms.ObjectParser.*;

/**
 * Author: Upatov Egor <br>
 * Date: 07.02.14, 17:05 <br>
 * Company: Korus Consulting IT <br>
 * Description: Тариф + услуга <br>
 */
//@Entity
public class UploadUslItem implements Informationable{

    //@Id
    //@Column(name = "id")
   // private Integer id;

    @Column (name = "ContractTariff")
    private Integer contractTariff;

    @Column(name = "TARIF")
    private Double TARIF;

    @Column(name = "KOL_USL")
    private Double KOL_USL;

    @Column(name = "rbServiceFinance")
    private Integer finance;

    @Column(name = "CODE_USL")
    private String CODE_USL;

    public UploadUslItem(Object[] args) {
        if(args.length >= 5){
            this.contractTariff = getIntegerValue(args[0]);
            this.TARIF = getDoubleValue(args[1]);
            this.KOL_USL = getDoubleValue(args[2]);
            this.finance = getIntegerValue(args[3]);
            this.CODE_USL = getStringValue(args[4]);
        }
    }


    @Override
    public String getInfo() {
        final StringBuilder result = new StringBuilder("USL_Item[");//.append(id).append("] [ ");
        result.append(" CT=").append(contractTariff);
        result.append(" TARIF=").append(TARIF);
        result.append(" KOL_USL=").append(KOL_USL);
        result.append(" rbServiceFinance=").append(finance) ;
        result.append(" \'").append(CODE_USL).append('\'');
        result.append(" ]");
        return result.toString();
    }

    public UploadUslItem() {
    }

    public Integer getContractTariff() {
        return contractTariff;
    }

    public void setContractTariff(Integer contractTariff) {
        this.contractTariff = contractTariff;
    }

    public Double getTARIF() {
        return TARIF;
    }

    public void setTARIF(Double TARIF) {
        this.TARIF = TARIF;
    }

    public Double getKOL_USL() {
        return KOL_USL;
    }

    public void setKOL_USL(Double KOL_USL) {
        this.KOL_USL = KOL_USL;
    }

    public Integer getFinance() {
        return finance;
    }

    public void setFinance(Integer finance) {
        this.finance = finance;
    }

    public String getCODE_USL() {
        return CODE_USL;
    }

    public void setCODE_USL(String CODE_USL) {
        this.CODE_USL = CODE_USL;
    }
}
