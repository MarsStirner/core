package ru.korus.tmis.core.exception;

public class NoSuchRbContactTypeException extends CoreException {
    final int rbContactTypeId;

    public int getRbContactTypeId() {
        return rbContactTypeId;
    }

    public NoSuchRbContactTypeException(final int errorCode,
                                        final int rbContactTypeId,
                                        final String message) {
        super(errorCode, message);
        this.rbContactTypeId = rbContactTypeId;
    }
}
