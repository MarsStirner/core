package ru.korus.tmis.core.exception;

public class NoSuchRbBloodTypeException extends CoreException {
    final int rbBloodTypeId;

    public int getRbBloodTypeId() {
        return rbBloodTypeId;
    }

    public NoSuchRbBloodTypeException(final int errorCode,
                                      final int rbBloodTypeId,
                                      final String message) {
        super(errorCode, message);
        this.rbBloodTypeId = rbBloodTypeId;
    }
}
