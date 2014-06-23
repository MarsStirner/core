package ru.korus.tmis.pharmacy;

import misexchange.MISExchangePortType;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        29.10.12, 17:28 <br>
 * Company:     Korus Consulting IT<br>
 * Description: <br>
 */
@Local
public interface PharmacyBeanLocal {

    /**
     * Полинг базы в поисках событий для 1С Аптеки
     */
    void pooling();

    /**
     * Отправка интервалов назначений.исполнений в 1С
     */
    void  sendPrescriptionTo1C() throws CoreException;

    void setMisExchangeSoap(MISExchangePortType misExchangeSoap);
}
