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
    MAIN("/admin", "Общая информация"),
    ALL_SETTINGS("/admin/settings", "Все настройки", false),
    COMMON("/admin/common", "Общие настройки ЛПУ"),
    RISAR("/admin/risar", "Интеграция с РИСАР", false);

    private final String path;

    private final String title;

    private final Boolean isShowTitle;

    ViewState(String path, String title) {
        this(path, title, true);
    }

    ViewState(String path, String title, Boolean isShowTitle) {
        this.path = path;
        this.title = title;
        this.isShowTitle = isShowTitle;
    }

    public Boolean isShowTitle() {
        return isShowTitle;
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
