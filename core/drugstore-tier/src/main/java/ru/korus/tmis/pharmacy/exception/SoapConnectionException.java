package ru.korus.tmis.pharmacy.exception;

/**
 * Created with IntelliJ IDEA.
 * User: nde
 * Date: 12.12.12
 * Time: 9:01
 * To change this template use File | Settings | File Templates.
 */
public class SoapConnectionException extends Exception {

    public SoapConnectionException() {
        super();
    }

    public SoapConnectionException(String message) {
        super(message);
    }

    public SoapConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SoapConnectionException(Throwable cause) {
        super(cause);
    }

    protected SoapConnectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
