package ru.korus.tmis.core.exception;

public class NoSuchRbDocumentTypeException extends CoreException {
    final int rbDocumentTypeId;

    public int getRbDocumentTypeId() {
        return rbDocumentTypeId;
    }

    public NoSuchRbDocumentTypeException(final int errorCode,
                                         final int rbDocumentTypeId,
                                         final String message) {
        super(errorCode, message);
        this.rbDocumentTypeId = rbDocumentTypeId;
    }
}