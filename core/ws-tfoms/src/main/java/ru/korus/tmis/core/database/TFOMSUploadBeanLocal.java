package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.*;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * User: EUpatov<br>
 * Date: 24.05.13 at 18:24<br>
 * Company Korus Consulting IT<br>
 */
@Local
public interface TFOMSUploadBeanLocal {
    public void clearSerial();
}
