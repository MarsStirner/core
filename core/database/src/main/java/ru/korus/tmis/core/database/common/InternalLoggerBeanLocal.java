package ru.korus.tmis.core.database.common;

import ru.korus.tmis.core.entity.model.ProfileEvent;

import javax.ejb.Local;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Local
public interface InternalLoggerBeanLocal {

    void logFault(String in, String out);

    void logMethodCall(UUID sessionId,
                       int nestedLevel,
                       int number,
                       long time,
                       String className,
                       String methodName);

    Map<ProfileEvent, List<ProfileEvent>> getStatistics(int limit);
}
