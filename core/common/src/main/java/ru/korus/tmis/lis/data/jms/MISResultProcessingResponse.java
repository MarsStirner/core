package ru.korus.tmis.lis.data.jms;

import java.io.Serializable;

/**
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 8/18/14
 * Time: 2:36 PM
 */
public class MISResultProcessingResponse implements Serializable {

    public final static String JMS_TYPE = "LabProcessingResponse";

    public static final long serialVersionUID = 1L;

    private Throwable throwable;

    private boolean isSuccess;

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
}
