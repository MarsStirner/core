package ru.korus.tmis.core.database.bak;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        12.11.13, 17:33 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Диагноз для БАК лаборатории<br>
 */
public class BakDiagnosis {
    /**
     * Код диагноза
     */
    private String code;
    /**
     * Описание диагноза
     */
    private String name;

    public BakDiagnosis(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BakDiagnosis{");
        sb.append("code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
