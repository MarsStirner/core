package ru.korus.tmis.core.exception;

public class NoSuchClientAllergyException extends CoreException {
    final int clientAllergyId;

    public int getClientAllergyId() {
        return clientAllergyId;
    }

    public NoSuchClientAllergyException(final int errorCode,
                                        final int clientAllergyId,
                                        final String message) {
        super(errorCode, message);
        this.clientAllergyId = clientAllergyId;
    }
}
