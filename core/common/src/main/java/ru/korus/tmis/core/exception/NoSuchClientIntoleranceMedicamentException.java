package ru.korus.tmis.core.exception;

public class NoSuchClientIntoleranceMedicamentException extends CoreException {
    final int clientIntoleranceMedicamentId;

    public int getClientIntoleranceMedicamentId() {
        return clientIntoleranceMedicamentId;
    }

    public NoSuchClientIntoleranceMedicamentException(final int errorCode,
                                                      final int clientIntoleranceMedicamentId,
                                                      final String message) {
        super(errorCode, message);
        this.clientIntoleranceMedicamentId = clientIntoleranceMedicamentId;
    }
}
