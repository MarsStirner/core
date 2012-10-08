package ru.korus.tmis.core.exception;

public class NoSuchOrganisationException extends CoreException {
    final int organisationId;

    public int getOrganisationId() {
        return organisationId;
    }

    public NoSuchOrganisationException(final int errorCode,
                                       final int organisationId,
                                       final String message) {
        super(errorCode, message);
        this.organisationId = organisationId;
    }
}