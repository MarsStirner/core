package ru.korus.tmis.ws.webmis.rest;

//Контейнер тредлокала для пользовательского запроса
public class ThreadLocalByRequest {

    public static final ThreadLocal currentThreadLocal = new ThreadLocal();

    public static void set(CurrentAuthContext currCtx) {
        currentThreadLocal.set(currCtx);
    }

    public static void unset() {
        currentThreadLocal.remove();
    }

    public static CurrentAuthContext get() {
        return (CurrentAuthContext) currentThreadLocal.get();
    }
}

