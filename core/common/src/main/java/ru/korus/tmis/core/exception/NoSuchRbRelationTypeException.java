package ru.korus.tmis.core.exception;

public class NoSuchRbRelationTypeException extends CoreException {
    final int rbRelationTypeId;

    public int getRbDocumentTypeId() {
        return rbRelationTypeId;
    }

    public NoSuchRbRelationTypeException(final int errorCode,
                                         final int rbRelationTypeId,
                                         final String message) {
        super(errorCode, message);
        this.rbRelationTypeId = rbRelationTypeId;
    }
}