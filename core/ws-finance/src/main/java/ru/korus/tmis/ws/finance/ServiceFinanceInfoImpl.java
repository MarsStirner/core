package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Веб-сервис экономических расчетов  <br>
 */

import javax.jws.WebService;

/**
 * @see ServiceFinanceInfo
 */
@WebService(endpointInterface = "ru.korus.tmis.ws.finance.ServiceFinanceInfo", targetNamespace = "http://korus.ru/tmis/ws/finance",
        serviceName = "tmis-finance", portName = "portFinance", name = "nameFinance")
public class ServiceFinanceInfoImpl implements ServiceFinanceInfo {

    /**
     * @see ServiceFinanceInfo#getFinanceInfo(String)
     */
    @Override
    public FinanceBean[] getFinanceInfo(final String nameOfStructure) {

        final FinanceInfo financeInfo = new FinanceInfo();

        return financeInfo.getFinanceInfo(nameOfStructure);
    }

}
