package ru.korus.tmis.pix.sda;

/**
* Author:      Sergey A. Zagrebelny <br>
* Date:        28.01.14, 10:22 <br>
* Company:     Korus Consulting IT<br>
* Description:  <br>
*/
public class CodeNameSystem {

    private final String code;

    private final String name;

    private final String codingSystem;

    //TODO remove! use  CodeNameSystem.newInstance()
    @Deprecated
    CodeNameSystem(String code, String name) {
        this.code = code;
        this.name = name;
        this.codingSystem = null;
    }

    private CodeNameSystem(String code, String name, String codingSystem) {
        this.code = code;
        this.name = name;
        this.codingSystem = codingSystem;
    }

    public static CodeNameSystem newInstance(String code, String name, String codingSystem) {
        if(code == null && name == null && codingSystem == null) {
            return null;
        }
        return new CodeNameSystem(code, name, codingSystem);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getCodingSystem() {
        return codingSystem;
    }
}
