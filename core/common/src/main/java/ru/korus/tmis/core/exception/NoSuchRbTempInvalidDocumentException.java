package ru.korus.tmis.core.exception;

public class NoSuchRbTempInvalidDocumentException extends CoreException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    final int rbTempInvalidDocumentId;

    public NoSuchRbTempInvalidDocumentException(final int id,
                                                final int rbTempInvalidDocumentId,
                                                final String message) {
        super(id, message);
        this.rbTempInvalidDocumentId = rbTempInvalidDocumentId;
        // TODO Auto-generated constructor stub
    }

    public int getRbTempInvalidDocumentId() {
        return rbTempInvalidDocumentId;
    }

}
