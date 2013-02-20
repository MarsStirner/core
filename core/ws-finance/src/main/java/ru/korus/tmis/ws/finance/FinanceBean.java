package ru.korus.tmis.ws.finance;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        25.12.2012, 11:00:00 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Пакет выходной финансовой информации<br>
 */

public class FinanceBean {

    /**
     * ID медкарты пациента
     */
    private String externalId;

    /**
     * Код услуги
     */
    private String codeOfService;

    /**
     * Наименование оказанной услуги
     */
    private String nameOfService;

    /**
     * Код подразделения, где была оказанна услуга
     */
    private String codeOfStruct;

    /**
     * Наименование подразделения, где была оказанна услуга
     */
    private String nameOfStruct;

    /**
     * Дата оказания услуги
     */
    private String endDate;

    /**
     *  Количество
     */
    private double amount;

    /**
     *  Цена за еденицу
     */
    private double price;

    /**
     * Сообщение об ошибке. Равно null в случае отсутвия ошибки.
     */
    private String errorMsg;

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getCodeOfService() {
        return codeOfService;
    }

    public void setCodeOfService(String codeOfService) {
        this.codeOfService = codeOfService;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getNameOfService() {
        return nameOfService;
    }

    public void setNameOfService(String nameOfService) {
        this.nameOfService = nameOfService;
    }

    public String getCodeOfStruct() {
        return codeOfStruct;
    }

    public void setCodeOfStruct(String codeOfStruct) {
        this.codeOfStruct = codeOfStruct;
    }

    public String getNameOfStruct() {
        return nameOfStruct;
    }

    public void setNameOfStruct(String nameOfStruct) {
        this.nameOfStruct = nameOfStruct;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("FinanceBean");
        sb.append("{externalId='").append(externalId).append('\'');
        sb.append(", codeOfService='").append(codeOfService).append('\'');
        sb.append(", nameOfService='").append(nameOfService).append('\'');
        sb.append(", codeOfStruct='").append(codeOfStruct).append('\'');
        sb.append(", nameOfStruct='").append(nameOfStruct).append('\'');
        sb.append(", endDate='").append(endDate).append('\'');
        sb.append(", amount=").append(amount);
        sb.append(", price=").append(price);
        sb.append(", errorMsg='").append(errorMsg).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
