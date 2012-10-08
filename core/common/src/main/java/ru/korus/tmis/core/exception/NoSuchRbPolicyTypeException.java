package ru.korus.tmis.core.exception;

public class NoSuchRbPolicyTypeException extends CoreException {
    final int rbPolicyTypeId;

    public int getRbPolicyTypeId() {
        return rbPolicyTypeId;
    }

    public NoSuchRbPolicyTypeException(final int errorCode,
                                       final int rbPolicyTypeId,
                                       final String message) {
        super(errorCode, message);
        this.rbPolicyTypeId = rbPolicyTypeId;
    }
}