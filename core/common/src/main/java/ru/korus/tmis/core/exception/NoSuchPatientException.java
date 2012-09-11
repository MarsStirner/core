package ru.korus.tmis.core.exception;

public class NoSuchPatientException extends CoreException {
    final int patientId;

    public int getPatientId() {
        return patientId;
    }

    public NoSuchPatientException(final int errorCode,
                                  final int patientId,
                                  final String message) {
        super(errorCode, message);
        this.patientId = patientId;
    }
}
