package ru.korus.tmis.core.database;

import java.util.Date;

import javax.ejb.Local;

import ru.korus.tmis.core.entity.model.Patient;
import ru.korus.tmis.core.entity.model.Staff;
import ru.korus.tmis.core.entity.model.TempInvalid;

@Local
public interface DbTempInvalidBeanLocal {

    TempInvalid getTempInvalidByEventId(int eventId);

    TempInvalid getTempInvalidById(int id);

    TempInvalid insertOrUpdateTempInvalid(
            TempInvalid tempInvalid,
            Date beginDate,
            Date endDate,
            short docType,
            String reasonCode,
            Date caseBegDate,
            String serail,
            String number,
            short sex,
            byte age,
            int duration,
            short closed,
            Patient patient,
            Staff sessionUser
    );


}
