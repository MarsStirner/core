package ru.korus.tmis.pix.sda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(AddrInfo.class);

    /**
     * Корпус
     */
    private final String block;

    public AddrInfo(final ClientAddress homeAddr, final DbSchemeKladrBeanLocal dbSchemeKladrBean) {
        String addrCity = null;
        String addrState = null;
        String addrStreet = null;
        String addrZip = null;
        String addrStateKladrCode = null;
        String district = null;
        String districtKladr = null;
        String addrStreetKladr = null;
        String addrCityKladr = null;
        String house = null;
        String appartment = null;
        String okato = null;
        String block = null;
        /**
         * [20.03.14 17:49:15] Александр Мартынов:
         * при занесении адреса в ClientAddress.freeInput формировать его в тег <street>...</street>
         * ...........
         * только street, остальных не должно быть
         * короче, если ClentAddress.address_id=NULL и ClentAddress.freeInput не пустой
         */
        if (homeAddr.getAddress() == null && homeAddr.getFreeInput() != null && !homeAddr.getFreeInput().isEmpty()) {
            addrStreet = homeAddr.getFreeInput();
        } else {
            if (homeAddr.getAddress() != null && homeAddr.getAddress().getHouse() != null) {
                AddressHouse addrHouse = homeAddr.getAddress().getHouse();
                final String kladrCode = addrHouse.getKLADRCode();
                if (kladrCode != null && kladrCode.length() > 1) { // если задан код КЛАДР
                    Kladr kladr = null;
                    Kladr kladrState = null;
                    Street street = null;
                    try {
                        kladr = dbSchemeKladrBean.getKladrByCode(kladrCode);
                        logger.info("Found kladr {}", street == null ? null : kladr.print());
                        // Регион пациента. Определяется по двум старшим цифрам кода КЛАДР
                        kladrState = dbSchemeKladrBean.getKladrByCode(kladrCode.substring(0, 2) + "00000000000");
                        logger.info("Found kladr state {}", street == null ? null : kladrState.print());
                        street = dbSchemeKladrBean.getStreetByCode(addrHouse.getKLADRStreetCode());
                        logger.info("Found street {}", street == null ? null : street.print());
                    } catch (CoreException e) {
                        logger.error("CoreException: " + e, e);
                    }

                    if (kladr != null) {
                        // Населенный пункт. Формируется как сокращенное наименования тип населённого пункта (г.) и названия
                        addrCity = kladr.getSocr() + "." + kladr.getName();
                        if (kladrState != null) {
                            addrState = kladrState.getSocr() + "." + kladrState.getName();
                            addrStateKladrCode = kladrState.getCode();
                        }
                        if (street != null) {
                            addrStreet =
                                    street.getSocr() + "." + street.getName();
                            addrZip = street.getIndex();
                        }
                        /**
                         * [20.03.14 16:58:12] Александр Мартынов:
                         * Структура кодового обозначения в блоке "Код":
                         СС РРР ГГГ ППП АА, где
                         СС – код субъекта Российской Федерации (региона), коды регионов представлены в Приложении 2 к Описанию классификатора адресов Российской Федерации (КЛАДР);
                         РРР – код района;
                         ГГГ – код города;
                         ППП – код населенного пункта,
                         АА – признак актуальности наименования адресного объекта.
                         т.е. если PPP не нули, то это район
                         */
                        //Для "районных" населенных пунктов в элементах <district>...</district> и <districtKladr>...</districtKladr> нужно указывать район.
                        if (kladr.getCode() != null && !kladr.getCode().matches("^[0-9]{2}000.*")) {
                            final String districtCode = kladr.getCode().substring(0, 5).concat("00000000");
                            logger.debug("### DISTRICT CODE: {}", districtCode);
                            Kladr districtKladrObject = kladr;
                            try {
                                districtKladrObject = dbSchemeKladrBean.getKladrByCode(districtCode);
                            } catch (CoreException e) {
                                logger.warn("Cannot find district kladr[{}].", districtCode);
                            }
                            // Район
                            district = districtKladrObject.getName();
                            // Код района (или города субъектного подчинения) согласно КЛАДР
                            districtKladr = districtCode;
                        }
                        // Код населенного пункта согласно КЛАДР
                        addrStreetKladr = homeAddr.getAddress().getHouse().getKLADRStreetCode();
                        // Код населенного пункта согласно КЛАДР
                        addrCityKladr = homeAddr.getAddress().getHouse().getKLADRCode();
                        // Номер дома
                        house = homeAddr.getAddress().getHouse().getNumber();
                        //Корпус
                        block = homeAddr.getAddress().getHouse().getCorpus();
                        // Номер квартиры
                        appartment = homeAddr.getAddress().getFlat();
                        // ОКАТО
                        okato = kladr.getOcatd();
                    }
                }
            }
        }
        this.addrCity = addrCity;
        this.addrState = addrState;
        this.addrStreet = addrStreet;
        this.addrZip = addrZip;
        this.addrStateKladrCode = addrStateKladrCode;
        this.district = district;
        this.districtKladr = districtKladr;
        this.addrStreetKladr = addrStreetKladr;
        this.addrCityKladr = addrCityKladr;
        this.house = house;
        this.appartment = appartment;
        this.okato = okato;
        this.block = block;
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

    public String getBlock() {
        return block;
    }
}
