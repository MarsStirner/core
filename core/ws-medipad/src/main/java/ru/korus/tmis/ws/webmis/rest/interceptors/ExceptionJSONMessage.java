package ru.korus.tmis.ws.webmis.rest.interceptors;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.interceptor.InvocationContext;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.core.exception.AuthenticationException;
import javax.servlet.ServletException;


@XmlType(name = "error")
@XmlRootElement(name = "error")
public class ExceptionJSONMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7378649465312132315L;

	private String methodName;
	private List<String> params = new LinkedList<String>();
	private String errorMessage;
    private int errorCode;
	private Object exception;
    private String validationErrors;
    private String callStack;
	
	public ExceptionJSONMessage(Exception e, InvocationContext ctx){
		this(e);
		if (ctx.getParameters() != null) {
			for (Object obj : ctx.getParameters()) {
				
				params.add(String.valueOf(obj));
			}
		}
		this.methodName = String.valueOf(ctx.getMethod());
	}
	
	public ExceptionJSONMessage(Exception e){
        Throwable rootException = e.getCause(); //Достаем причинный ексепш
        if (rootException != null) {
            if (rootException instanceof AuthenticationException) {
                this.errorCode = ((AuthenticationException) rootException).getId();
            }
            else if (rootException instanceof CoreException) {//instanceof CoreException
                int faultId = ((CoreException) rootException).getId();
                this.errorCode = faultId;
            }
            this.errorMessage = rootException.getMessage();
        }
        this.exception = e.toString();
        this.validationErrors = ""; //TODO Здесь должны быть ошибки клиента (например незаполненные или неправильно заполненные поля)
        StackTraceElement[] stack = e.getStackTrace();
        String stackString = "";
        for (int i = 0; i < stack.length; i++) {
            stackString = String.format("%s %d: %s\n", stackString, i, stack[i].toString());
        }
        this.callStack = stackString;
    }
	
	public ExceptionJSONMessage(){

	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
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
