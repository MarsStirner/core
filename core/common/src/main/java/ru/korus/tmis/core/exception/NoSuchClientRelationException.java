package ru.korus.tmis.core.exception;

public class NoSuchClientRelationException extends CoreException {
    final int clientRelationId;

    public int getClientRelationId() {
        return clientRelationId;
    }

    public NoSuchClientRelationException(final int errorCode,
                                         final int clientRelationId,
                                         final String message) {
        super(errorCode, message);
        this.clientRelationId = clientRelationId;
    }
}