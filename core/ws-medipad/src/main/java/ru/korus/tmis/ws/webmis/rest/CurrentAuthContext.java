package ru.korus.tmis.ws.webmis.rest;

import ru.korus.tmis.core.auth.AuthData;

//Храним данные о пользователе для текущего запроса
public class CurrentAuthContext {

    public enum HResult {
        S_OK,                       //Все корректно
        E_NOAUTHDATA,               //Нету валидных данных по авторизации
        E_INVALIDPERIOD,            //Период действия сертификата истек
        E_NOENDDATE,                //Нету даты окончания действия сертификата
        E_FAIL                      //Неустановленная ошибка
    }

    private AuthData currentUserAuthData = null;
    private String description = null;
    private HResult hResult = HResult.E_FAIL;

    public boolean securityChecksDisabled = true;

    //setters
    public void setCurrentAuthContext(AuthData authData, HResult hRes, String desc) {
        this.currentUserAuthData = authData;
        this.description = desc;
        this.hResult = hRes;
    }

    /*
    //reserved
    public void setCurrentUserAuthData(AuthData authData) {
        this.currentUserAuthData =  authData;
    }
    public void setDescription(String desc) {
        this.description =  desc;
    }
    public void setHResult(HResult hRes) {
        this.hResult =  hRes;
    }
    */
    //getters
    public AuthData getCurrentUserAuthData() {
        return this.currentUserAuthData;
    }

    public String getDescription() {
        return this.description;
    }

    public HResult getHResult() {
        return this.hResult;
    }

    //clean this context
    public void clean() {
        this.currentUserAuthData = null;
        this.description = null;
    }
}

