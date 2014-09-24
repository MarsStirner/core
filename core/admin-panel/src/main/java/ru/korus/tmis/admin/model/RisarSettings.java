package ru.korus.tmis.admin.model;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        19.09.14, 10:52 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class RisarSettings implements Serializable {

    static public enum ValidationState {
        OK, WRONG, UNCHECKED;
    }

    private URL url;

    private Integer check = 0;

    private ValidationState validationState = RisarSettings.ValidationState.UNCHECKED;

    private String errorMsg;

    private List<RisarAction> risarActionList = new ArrayList<RisarAction>();

    private List<RisarAction> risarNewActionList = new ArrayList<RisarAction>();

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
        if(errorMsg == null) {
            setValidationState(ValidationState.OK);
        } else {
            setValidationState(ValidationState.WRONG);
        }
        this.errorMsg = errorMsg;
    }

    public Integer getCheck() {
        return check;
    }

    public void setCheck(Integer check) {
        this.check = check;
    }


    public List<RisarAction> getRisarNewActionList() {
        return risarNewActionList;
    }

    public void setRisarNewActionList(List<RisarAction> risarNewActionList) {
        this.risarNewActionList = risarNewActionList;
    }

    public List<RisarAction> getRisarActionList() {
        return risarActionList;
    }

    public void setRisarActionList(List<RisarAction> risarActionList) {
        this.risarActionList = risarActionList;
    }
}
