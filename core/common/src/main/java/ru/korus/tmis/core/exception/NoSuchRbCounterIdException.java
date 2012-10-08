package ru.korus.tmis.core.exception;

public class NoSuchRbCounterIdException extends CoreException {
    final int rbCounterId;

    public int getRbCounterIdId() {
        return rbCounterId;
    }

    public NoSuchRbCounterIdException(final int errorCode,
                                      final int rbCounterId,
                                      final String message) {
        super(errorCode, message);
        this.rbCounterId = rbCounterId;
    }
}
