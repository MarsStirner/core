package ru.korus.tmis.admin.controller;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        02.09.14, 12:50 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum ViewState {
    ROOT("/", ""),
    AUTH("/auth", "Авторизация"),
    MAIN("/admin", "Общая информация");

    private final String path;

    private final String title;

    ViewState(String path, String title) {
        this.path = path;
        this.title = title;
    }

    public String getPath() {
        return path;
    }

    public String getJspPath() {
        return path.replace('/', '-');
    }


    public String redirect() {
        return "redirect:" + path;
    }

    public String getTitle() {
        return title;
    }

}
