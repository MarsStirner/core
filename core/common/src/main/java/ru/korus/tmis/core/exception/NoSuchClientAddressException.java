package ru.korus.tmis.core.exception;

public class NoSuchClientAddressException extends CoreException {
    final int clientAddressId;

    public int getClientAddressId() {
        return clientAddressId;
    }

    public NoSuchClientAddressException(final int errorCode,
                                        final int clientAddressId,
                                        final String message) {
        super(errorCode, message);
        this.clientAddressId = clientAddressId;
    }
}
