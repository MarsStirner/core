package ru.korus.tmis.core.common;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.CommonAttribute;
import ru.korus.tmis.core.data.CommonData;
import ru.korus.tmis.core.data.CommonGroup;
import ru.korus.tmis.core.entity.model.*;
import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.scala.util.StringId;
import scala.Function1;

import java.util.List;
import java.util.Set;
import javax.ejb.Local;

@Local
public interface CommonDataProcessorBeanLocal {

    List<Action> createActionForEventFromCommonData(int eventId,
                                                    CommonData data,
                                                    AuthData userData,
                                                    Staff sraff)
            throws CoreException;

    List<Action> modifyActionFromCommonData(int actionId,
                                            CommonData data,
                                            AuthData userData,
                                            Staff staff)
            throws CoreException;

    boolean changeActionStatus(int eventId, int actionId, short status);

    CommonData fromActionTypes(
            Set<ActionType> types,
            String typeName,
            Function1<ActionPropertyType, CommonAttribute> converter)
            throws CoreException;

    CommonData fromActionTypesForWebClient(
            ActionType actionType,
            String typeName,
            List<StringId> listForSummary,
            List<String> listForConverter,
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

    CustomCalendar defineAgeOfPatient(Patient patient) throws CoreException;

    boolean checkActionPropertyTypeForPatientAgeAndSex(Patient age, ActionPropertyType apt) throws CoreException;
}
