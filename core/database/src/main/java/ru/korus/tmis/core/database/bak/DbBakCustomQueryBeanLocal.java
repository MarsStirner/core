package ru.korus.tmis.core.database.bak;

import ru.korus.tmis.core.entity.model.Action;

import javax.annotation.Nullable;
import javax.ejb.Local;

/**
 * Author:      Dmitriy E. Nosov <br>
 * Date:        22.11.13, 0:26 <br>
 * Company:     Korus Consulting IT<br>
 * Description: Бин со специфическими запросами, которые, обычно, реализируются нативно<br>
 */
@Local
public interface DbBakCustomQueryBeanLocal {
    /**
     * Получение диагноза для БАК лаборатории
     * @param action событие
     * @return Возвращается диагноз, код и текстовое описание диагноза, иначе возвращается null
     */
    @Nullable
    BakDiagnosis getBakDiagnosis(final Action action);
}
