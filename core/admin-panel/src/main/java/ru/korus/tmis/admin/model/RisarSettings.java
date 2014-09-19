package ru.korus.tmis.admin.model;

import java.net.URL;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.09.14, 10:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RisarSettings {

    static public enum ValidationState {
        OK, WRONG, UNCHECKED;
    }

    private URL url;

    private ValidationState validationState = ValidationState.UNCHECKED;

    private String errorMsg;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public ValidationState getValidationState() {
        return validationState;
    }

    public void setValidationState(ValidationState validationState) {
        this.validationState = validationState;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
