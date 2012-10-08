package ru.korus.tmis.core.exception;

public class NoSuchRbTempInvalidReasonException extends CoreException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final int rbTempInvalidId;

    public int getRbTempInvalidId() {
        return rbTempInvalidId;
    }

    public NoSuchRbTempInvalidReasonException(final int id,
                                              final int rbTempInvalidId,
                                              final String message
    ) {
        super(id, message);
        this.rbTempInvalidId = rbTempInvalidId;
        // TODO Auto-generated constructor stub
    }

}
