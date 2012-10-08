package ru.korus.tmis.core.exception;

public class NoSuchEntityException extends CoreException {

    final int entityId;


    public NoSuchEntityException(final int errorCode,
                                 final int entityId,
                                 final String message) {
        super(errorCode, message);
        this.entityId = entityId;
    }
}