package ru.korus.tmis.core.exception;

public class NoSuchClientPolicyException extends CoreException {
    final int clientPolicyId;

    public int getClientPolicyId() {
        return clientPolicyId;
    }

    public NoSuchClientPolicyException(final int errorCode,
                                       final int clientPolicyId,
                                       final String message) {
        super(errorCode, message);
        this.clientPolicyId = clientPolicyId;
    }
}