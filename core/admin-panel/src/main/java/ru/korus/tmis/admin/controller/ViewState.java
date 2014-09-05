package ru.korus.tmis.admin.controller;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum ViewState {
    ROOT("/"),
    AUTH("/auth"),
    MAIN("/admin");

    private final String path;

    ViewState(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String redirect() {
        return "redirect:" + path;
    }
}
