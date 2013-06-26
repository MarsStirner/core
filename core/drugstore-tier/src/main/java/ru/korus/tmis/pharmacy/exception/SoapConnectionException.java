package ru.korus.tmis.pharmacy.exception;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        12.12.12, 9:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
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
