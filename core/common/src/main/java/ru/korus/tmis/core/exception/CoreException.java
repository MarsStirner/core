package ru.korus.tmis.core.exception;

import javax.xml.ws.WebFault;

@WebFault(name = "ExceptionFault", faultBean = "ru.korus.tmis.core.exception.FaultBean")
public class CoreException extends Exception {

    private final FaultBean faultBean;

    /**
     * Получение контейнера информации об исключения
     * @return Экземпляр, несущий код исключения и его описание
     */
    public FaultBean getFaultInfo() {
        return faultBean;
    }

    /**
     * Создание исключения в работе логики ядра
     */
    public CoreException() {
        super();
        faultBean = new FaultBean(0, "");
    }

    /**
     * Создание исключения в работе логики ядра
     * @param cause Причина возникновения исключения
     */
    public CoreException(Throwable cause) {
        super(cause);
        faultBean = new FaultBean(0, "");
    }

    /**
     * Создание исключения в работе логики ядра
     * @param id Идентификатор ошибки, см. {@link ru.korus.tmis.scala.util.ConfigManager#ErrorCodes()}
     */
    public CoreException(final int id) {
        faultBean = new FaultBean(id, "");
    }

    /**
     * Создание исключения в работе логики ядра
     * @param id Идентификатор ошибки, см. {@link ru.korus.tmis.scala.util.ConfigManager#ErrorCodes()}
     * @param cause Причина возникновения исключения
     */
    public CoreException(final int id, Throwable cause) {
        super(cause);
        faultBean = new FaultBean(id, "");
    }

    /**
     * Создание исключения в работе логики ядра
     * @param message Описание возникающего исключения
     */
    public CoreException(final String message) {
        super(message);
        faultBean = new FaultBean(0, message);
    }

    /**
     * Создание исключения в работе логики ядра
     * @param message Описание возникающего исключения
     * @param cause Причина возникновения исключения
     */
    public CoreException(final String message, Throwable cause) {
        super(message, cause);
        faultBean = new FaultBean(0, message);
    }

    /**
     * Создание исключения в работе логики ядра
     * @param id Идентификатор исключения, см. {@link ru.korus.tmis.scala.util.ConfigManager#ErrorCodes()}
     * @param message Описание возникающего исключения
     */
    public CoreException(final int id, final String message) {
        super(message);
        faultBean = new FaultBean(id, message);
    }

    /**
     * Создание исключения в работе логики ядра
     * @param id Идентификатор исключения, см. {@link ru.korus.tmis.scala.util.ConfigManager#ErrorCodes()}
     * @param message Описание возникающего исключения
     * @param cause Причина возникновения исключения
     */
    public CoreException(final int id, final String message, Throwable cause) {
        super(message, cause);
        faultBean = new FaultBean(id, message);
    }

    /**
     * Получение идентификатора ошибки
     * @return Идентификатор ошибки, см. {@link ru.korus.tmis.scala.util.ConfigManager#ErrorCodes()}
     */
    public int getId() {
        return faultBean.getId();
    }

    /**
     * Получение текстового описания исключения
     * @return Описание исключения
     */
    @Override
    public String getMessage() {
        return faultBean.getMessage();
    }
}
