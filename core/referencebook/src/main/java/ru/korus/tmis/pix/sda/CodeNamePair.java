package ru.korus.tmis.pix.sda;

/**
* Author:      Sergey A. Zagrebelny <br>
* Date:        28.01.14, 10:22 <br>
* Company:     Korus Consulting IT<br>
* Description:  <br>
*/
public class CodeNamePair {

    private final String code;
    private final String name;

    public CodeNamePair(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
