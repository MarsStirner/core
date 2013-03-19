package ru.korus.tmis.pharmacy.exception;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        12.12.12, 9:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class MessageProcessException extends Exception {

    public MessageProcessException() {
        super();
    }

    public MessageProcessException(final String message) {
        super(message);
    }

    public MessageProcessException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MessageProcessException(final Throwable cause) {
        super(cause);
    }

    protected MessageProcessException(
            final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
