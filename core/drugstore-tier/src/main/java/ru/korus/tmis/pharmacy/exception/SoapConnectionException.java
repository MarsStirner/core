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

    public SoapConnectionException(final String message) {
        super(message);
    }

    public SoapConnectionException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SoapConnectionException(final Throwable cause) {
        super(cause);
    }

    protected SoapConnectionException(
            final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
