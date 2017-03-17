package ru.korus.tmis.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.exception.CoreException;

/**
 * Author:      Sergey A. Zagrebelny <br>
 * Date:        24.12.13, 18:24 <br>
 * Company:     Korus Consulting IT<br>
 * Description:  <br>
 */
public enum PatientInQueueType {
    QUEUE((short) 0),
    URGENT((short) 1),
    OVERQUEUE((short) 2);

    private final Short value;

    private static final Logger logger = LoggerFactory.getLogger(PatientInQueueType.class);

    PatientInQueueType(Short value) {
        this.value = value;
    }

    public Short getValue() {
        return value;
    }

    public static PatientInQueueType newInstance(Short patientInQueueType) throws CoreException {
        for (PatientInQueueType p : QUEUE.getDeclaringClass().getEnumConstants()) {
            if (p.getValue().equals(patientInQueueType)) {
                return p;
            }
        }
        logger.error("No one times in AP. Throws NotFoundException.");
        throw new CoreException();
    }
}
