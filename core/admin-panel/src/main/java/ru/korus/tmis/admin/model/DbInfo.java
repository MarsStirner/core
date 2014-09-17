package ru.korus.tmis.admin.model;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.09.14, 12:55 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public class DbInfo {

    private final String url;

    private final String name;

    public DbInfo() {
        url = null;
        name = null;
    }

    public DbInfo(String url, String name) {
        this.url = url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
