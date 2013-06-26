package ru.korus.tmis.ws.transfusion;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.01.2013, 17:01:19 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */

/**
 * Результат регистрации в МИС извещения о выполнении требования КК системой ТРФУ
 */
public class IssueResult {
    /**
     * результат регистрации итогов выполнения требования
     */
    private Boolean result = true;

    /**
     * текстовое описание ошибки (только при отрицательном результате)
     */
    private String description;

    /**
     * идентификатор требования на выдачу КК в МИС (Actoin.id)
     */
    private Integer requestId;

    /**
     * @return the result
     */
    public Boolean getResult() {
        return result;
    }

    /**
     * @param result
     *            the result to set
     */
    public void setResult(final Boolean result) {
        this.result = result;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * @return the requestId
     */
    public Integer getRequestId() {
        return requestId;
    }

    /**
     * @param requestId
     *            the requestId to set
     */
    public void setRequestId(final Integer requestId) {
        this.requestId = requestId;
    }

}
