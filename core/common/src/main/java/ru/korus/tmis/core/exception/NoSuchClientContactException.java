package ru.korus.tmis.core.exception;

public class NoSuchClientContactException extends CoreException {
    final int clientContactId;

    public int getClientContactId() {
        return clientContactId;
    }

    public NoSuchClientContactException(final int errorCode,
                                        final int clientContactId,
                                        final String message) {
        super(errorCode, message);
        this.clientContactId = clientContactId;
    }
}
