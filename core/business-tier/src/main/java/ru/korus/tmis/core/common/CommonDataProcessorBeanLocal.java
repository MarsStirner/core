package ru.korus.tmis.core.common;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonAttribute;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.CommonGroup;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.util.StringId;
import scala.Function1;
import scala.Function2;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public interface CommonDataProcessorBeanLocal {

    List<Action> createActionForEventFromCommonData(int eventId,
                                                    CommonData data,
                                                    AuthData userData)
            throws CoreException;

    List<Action> modifyActionFromCommonData(int actionId,
                                            CommonData data,
                                            AuthData userData)
            throws CoreException;

    boolean changeActionStatus(int eventId, int actionId, short status);

    CommonData fromActionTypes(
            Set<ActionType> types,
            String typeName,
            Function1<ActionPropertyType, CommonAttribute> converter)
            throws CoreException;

    CommonData fromActionTypesForWebClient(
            Set<ActionType> types,
            String typeName,
            List<StringId> listForSummary,
            List<String> listForConverter,
            Function2<List<String>, ActionPropertyType, CommonAttribute> converter,
            Patient patient)
            throws CoreException;

    CommonData fromActions(
            List<Action> actions,
            String actionName,
            List<Function1<Action, CommonGroup>> converters)
            throws CoreException;

    CommonGroup addAttributes(CommonGroup group,
                              ActionWrapper wrapper,
                              List<StringId> attributeNames)
            throws CoreException;

    Calendar defineAgeOfPatient(Patient patient) throws CoreException;

    boolean checkActionPropertyTypeForPatientAge(Calendar age, ActionPropertyType apt) throws CoreException;
}
