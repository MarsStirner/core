package ru.korus.tmis.vmp.utilities;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


@XmlType(name = "error")
@XmlRootElement(name = "error")
@Deprecated
public class ExceptionJSONMessage implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7378649465312132315L;

    private List<String> params = new LinkedList<String>();
    private String errorMessage;
    private int errorCode;
    private Object exception;
    private String validationErrors;
    private String callStack;

    public ExceptionJSONMessage(Throwable e) {
        Throwable rootException = e.getCause(); //Достаем причинный ексепш
        this.exception = e.toString();
        this.validationErrors = ""; //TODO Здесь должны быть ошибки клиента (например незаполненные или неправильно заполненные поля)
        StackTraceElement[] stack = e.getStackTrace();
        String stackString = "";
        for (int i = 0; i < stack.length; i++) {
            stackString = String.format("%s %d: %s%n", stackString, i, stack[i].toString());
        }
        this.callStack = stackString;
    }

    public ExceptionJSONMessage() {

    }

    public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(String validationErrors) {
        this.validationErrors = validationErrors;
    }

    public String getCallStack() {
        return callStack;
    }

    public void setCallStack(String callStack) {
        this.callStack = callStack;
    }
}
