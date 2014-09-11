package ru.korus.tmis.admin.service.impl;

import org.springframework.stereotype.Service;
import ru.korus.tmis.admin.model.DbInfo;
import ru.korus.tmis.admin.service.DbInfoService;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        11.09.14, 13:07 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
@Service
public class DbInfoServiceImpl implements DbInfoService {

    @Override
    public DbInfo getMainDbInfo() {
        return new DbInfo("TODO: imlp!", "name");
    }

    @Override
    public DbInfo getSettingsDbInfo() {
        return new DbInfo("TODO: imlp!", "name");
    }
}
