package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Setting;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DbSettingsBeanLocal {
    @Deprecated
    void init() throws CoreException;

    Setting getSettingByPath(String path);

    List<Setting> getAllSettings();
}
