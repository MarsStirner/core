
package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  Пакет выходной финансовой информации<br>
 */

//TODO удалить, если новый формат прочитает .net
public class OutputFinanceInfo {

    /**
     * Массив пакетов выходной финансовой информации
     */
    private FinanceBean[] financeData;

    /**
     * Сообщение об ошибке. Равно null в случае отсутвия ошибки.
     */
    private String errorMsg;

    public void initErrorMsg(final String errorMessage) {
        this.errorMsg = errorMessage;
        if (financeData == null || financeData.length == 0) {
            setFinanceData(new FinanceBean[1]);
        }
        financeData[0] = new FinanceBean();
        financeData[0].setErrorMsg(errorMsg);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(final String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public FinanceBean[] getFinanceData() {
        return financeData;
    }

    public void setFinanceData(final FinanceBean[] financeData) {
        this.financeData = financeData;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("OutputFinanceInfo");
        sb.append("{financeData=").append(financeData == null ? "null" : financeData.length);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
