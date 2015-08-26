package ru.korus.tmis.ws.risar;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        15.08.14, 9:58 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
//{"result":"failed","code":"0","errorMessage":"Ошибка сохранения: [] - [Уже существует посещение на выбранные дату и время.]"}
@JsonIgnoreProperties(ignoreUnknown=true)
public class RisarResponse {

    private String result;

    private Integer code;

    private String message;

    private String errorMessage;

    private String patientId;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public boolean isOk() {
        return "success".equals(result);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer();
        sb.append("RisarResponse");
        sb.append("{result='").append(result).append('\'');
        sb.append(", code=").append(code);
        sb.append(", message='").append(message).append('\'');
        sb.append(", errorMessage='").append(errorMessage).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
