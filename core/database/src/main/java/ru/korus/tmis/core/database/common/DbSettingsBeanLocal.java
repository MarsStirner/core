package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.Setting;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DbSettingsBeanLocal {

    void init();

    /**
     * Получение параметров конфигурации из таблицы с параметрами ядра
     * @param path Текстовый идентификатор записи в таблице конфигурации
     * @return Экземпляр класса {@link ru.korus.tmis.core.entity.model.Setting},
     * если записи нет в БД экземпляр Setting будет пустой.
     */
    Setting getSettingByPath(String path);

    /**
     * Аналогично {@link #getSettingByPath(String) getSettingByPath}, однако
     * значение будет браться из общей таблицы в БД s11r64
     */
    Setting getSettingByPathInMainSettings(String path);

    List<Setting> getAllSettings();

    boolean updateSetting(String path, String value);
}
