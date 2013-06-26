package ru.korus.tmis.pharmacy.exception;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        12.12.12, 9:01 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class NoSuchOrgStructureException extends Exception {

    public NoSuchOrgStructureException() {
        super();
    }

    public NoSuchOrgStructureException(final String message) {
        super(message);
    }

    public NoSuchOrgStructureException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NoSuchOrgStructureException(final Throwable cause) {
        super(cause);
    }

    protected NoSuchOrgStructureException(
            final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
