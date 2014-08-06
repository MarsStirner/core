package ru.korus.tmis.lis.data.jms;

import java.io.Serializable;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/5/14
 * Time: 7:43 PM
 */
public class LabModuleSendingResponse implements Serializable {

    public final static String JMS_TYPE = "sendingResponse";

    public static final long serialVersionUID = 1L;

    private Throwable throwable;

    private boolean isSuccess;

    private int actionId;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public int getActionId() {
        return actionId;
    }

    public void setActionId(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public String toString() {
        return actionId + " " + isSuccess + " " + throwable;
    }
}
