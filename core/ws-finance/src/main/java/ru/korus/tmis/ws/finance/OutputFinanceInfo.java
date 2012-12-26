
package ru.korus.tmis.ws.finance;

import java.util.Arrays;

/**
 * Возвращаемые данные
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

    public void initErrorMsg(String errorMsg) {
        setErrorMsg(errorMsg);
        if(financeData == null || financeData.length == 0) {
            setFinanceData(new FinanceBean[1]);
        }
        financeData[0] = new FinanceBean();
        financeData[0].setErrorMsg(errorMsg);
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public FinanceBean[] getFinanceData() {
        return financeData;
    }

    public void setFinanceData(FinanceBean[] financeData) {
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
