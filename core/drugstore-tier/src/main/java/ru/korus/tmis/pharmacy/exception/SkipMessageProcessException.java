package ru.korus.tmis.pharmacy.exception;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        12.12.12, 9:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class SkipMessageProcessException extends Exception {

    public SkipMessageProcessException() {
        super();
    }

    public SkipMessageProcessException(final String message) {
        super(message);
    }

    public SkipMessageProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SkipMessageProcessException(final Throwable cause) {
        super(cause);
    }

    protected SkipMessageProcessException(
            final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
