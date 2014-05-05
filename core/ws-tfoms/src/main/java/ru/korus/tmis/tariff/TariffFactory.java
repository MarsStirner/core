package ru.korus.tmis.tariff;

import ru.korus.tmis.tariff.thriftgen.Tariff;

/**
 * Author: Upatov Egor <br>
 * Date: 25.11.13, 14:30 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class TariffFactory {

    private static TariffFactory instance;

    private TariffFactory() {
    }

    /**
     * Получение экземпляра (синглтон)
     * @return экземпляр
     */
    public static TariffFactory getInstance() {
        if (instance == null) {
            instance = new TariffFactory();
        }
        return instance;
    }

    //Получение экземпляра Тарифа нужного типа
    public TariffEntry getTariffEntry(final Tariff tariff){
        //Если <8-ой символ C_TAR> = ‘H’ (или ‘C’, или ‘V’, или ‘Z’), то это отдельный случай – диспансеризация
        if("HCVZ".contains(tariff.getC_tar().substring(7, 8).toUpperCase())){
           return new DispanserizationTariff(tariff);
        } else if(tariff.getC_tar().substring(9,16).replaceFirst("^0*", "").matches("^[A-z]+.*")){
                //Step 7 (Тарифы на диагнозы
                //7	Если часть <с 10-ого по 16-ый символы C_TAR>
                // без нулей слева начинается с буквы,
                // а не с цифры – то выполнить следующее:
            return new DiagnosisTariff(tariff);
        } else {
            return new SimpleTariff(tariff);
        }
    }


}
