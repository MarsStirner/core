package ru.korus.tmis.pix.sda;

import ru.korus.tmis.core.database.DbSchemeKladrBeanLocal;
import ru.korus.tmis.core.entity.model.AddressHouse;
import ru.korus.tmis.core.entity.model.ClientAddress;
import ru.korus.tmis.core.entity.model.kladr.Kladr;
import ru.korus.tmis.core.entity.model.kladr.Street;
import ru.korus.tmis.core.exception.CoreException;

/**
* Author:      Sergey A. Zagrebelny <br>
* Date:        29.01.14, 9:28 <br>
* Company:     Korus Consulting IT<br>
* Description:  <br>
*/
public class AddrInfo {
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
     * Код субъекта РФ согласно КЛАДР
     */
    final private String addrStateKladrCode;
    /**
     * Район
     */
    private final String district;

    /**
     * Код населенного пункта согласно КЛАДР
     */
    private final String addrStreetKladr;
    /**
     * Код района (или города субъектного подчинения) согласно КЛАДР
     */
    private final String districtKladr;
    /**
     * Код населенного пункта согласно КЛАДР
     */
    private final String addrCityKladr;
    /**
     * Номер дома
     */
    private final String house;
    /**
     * Номер квартиры
     */
    private final String appartment;
    /**
     * ОКАТО
     */
    private final String okato;

    public AddrInfo(ClientAddress homeAddr, DbSchemeKladrBeanLocal dbSchemeKladrBeanLocal) {
        String addrCity = null;
        String addrState = null;
        String addrStreet = null;
        String addrZip = null;
        String addrStateKladrCode = null;
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
                        addrStateKladrCode = kladr.getCode();
                    }
                    if (street != null) {
                        addrStreet =
                                street.getSocr() + "." + street.getName() + ("".equals(addrHouse.getNumber()) ? "" : (" д." + addrHouse.getNumber()))
                                        + ("".equals(addrHouse.getCorpus()) ? "" : (" корп." + addrHouse.getCorpus()));
                        addrZip = street.getIndex();
                    }
                }

            }
        }
        this.addrCity = addrCity;
        this.addrState = addrState;
        this.addrStreet = addrStreet;
        this.addrZip = addrZip;
        this.addrStateKladrCode = addrStateKladrCode;

        district = //TODO
        districtKladr = //TODO;
        addrStreetKladr = //TODO;
        addrCityKladr = //TODO;
        house = //TODO;
        appartment = //TODO;
        okato = //TODO
    }

    public String getAddrStreet() {
        return addrStreet;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public String getAddrState() {
        return addrState;
    }

    public String getAddrZip() {
        return addrZip;
    }

    public String getAddrStateKladrCode() {
        return addrStateKladrCode;
    }

    public String getDistrict() {
        return district;
    }

    public String getAddrStreetKladr() {
        return addrStreetKladr;
    }

    public String getDistrictKladr() {
        return districtKladr;
    }

    public String getAddrCityKladr() {
        return addrCityKladr;
    }

    public String getHouse() {
        return house;
    }

    public String getAppartment() {
        return appartment;
    }

    public String getOkato() {
        return okato;
    }
}
