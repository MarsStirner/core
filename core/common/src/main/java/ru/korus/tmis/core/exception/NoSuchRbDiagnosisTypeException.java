package ru.korus.tmis.core.exception;

/**
 * @author Ivan Dmitriev Systema-Soft
 */
public class NoSuchRbDiagnosisTypeException extends CoreException {
    final int rbDiagnosisTypeId;
    final String rbDiagnosisTypeFlatCode;

    public int getRbDiagnosisTypeId() {
        return rbDiagnosisTypeId;
    }

    public String getRbDiagnosisTypeFlatCode() {
        return rbDiagnosisTypeFlatCode;
    }

    public NoSuchRbDiagnosisTypeException(final int errorCode,
                                          final int rbDiagnosisTypeId,
                                          final String message) {
        super(errorCode, message);
        this.rbDiagnosisTypeId = rbDiagnosisTypeId;
        this.rbDiagnosisTypeFlatCode = "";
    }

    public NoSuchRbDiagnosisTypeException(final int errorCode,
                                          final String flatCode,
                                          final String message) {
        super(errorCode, message);
        this.rbDiagnosisTypeId = -1;
        this.rbDiagnosisTypeFlatCode = flatCode;
    }
}
