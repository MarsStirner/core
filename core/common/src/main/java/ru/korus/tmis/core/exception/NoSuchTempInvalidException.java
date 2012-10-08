package ru.korus.tmis.core.exception;

public class NoSuchTempInvalidException extends CoreException {
    final int tempInvalidId;


    public NoSuchTempInvalidException(final int errorCode,
                                      final int tempInvalidId,
                                      final String message) {
        super(errorCode, message);
        this.tempInvalidId = tempInvalidId;
    }
}