package ru.korus.tmis.ws.transfusion.order;

import javax.xml.datatype.DatatypeConfigurationException;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.ws.transfusion.efive.TransfusionMedicalService;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        18.02.2013, 14:56:45 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * 
 */
public interface TrfuPullable {

    /**
     * Поиск новых требований КК и передача их в ТРФУ
     * 
     * @throws CoreException
     * @throws CoreException
     * @throws DatatypeConfigurationException
     */
     void pullDB(TransfusionMedicalService trfuService);
}