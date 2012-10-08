package ru.korus.tmis.core.exception;

public class NoSuchClientDocumentException extends CoreException {
    final int clientDocumentId;

    public int getClientDocumentId() {
        return clientDocumentId;
    }

    public NoSuchClientDocumentException(final int errorCode,
                                         final int clientDocumentId,
                                         final String message) {
        super(errorCode, message);
        this.clientDocumentId = clientDocumentId;
    }
}