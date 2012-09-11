package ru.korus.tmis.drugstore.data;

import org.w3c.dom.Document;
import ru.korus.tmis.core.entity.model.APValue;
import ru.korus.tmis.core.entity.model.Action;
import ru.korus.tmis.core.entity.model.ActionProperty;
import ru.korus.tmis.core.entity.model.AssignmentHour;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.ejb.Local;

@Local
public interface YClinicalDocumentBuilderLocal {
    Document buildCreateDrugRequest(
            Action action,
            Map<ActionProperty, List<APValue>> values);

    Document buildCreateDrugEvent(
            Action oldAction,
            Action newAction,
            Map<ActionProperty, List<APValue>> values);

    Document buildDeleteDrugEvent(
            Action oldAction,
            Action newAction,
            Map<ActionProperty, List<APValue>> values);

    Document buildDeleteDrugRequest(
            Action action,
            Map<ActionProperty, List<APValue>> values);

    Document processCreateDrugRequest(
            Action action,
            Set<AssignmentHour> timing,
            Map<ActionProperty, List<APValue>> values);

    Document processDeleteDrugRequest(
            Action action,
            Set<AssignmentHour> timing,
            Map<ActionProperty, List<APValue>> values);

    Document processCreateDrugEvent(
            Action action,
            Set<AssignmentHour> timing,
            Map<ActionProperty, List<APValue>> values);

    Document processDeleteDrugEvent(
            Action action,
            Set<AssignmentHour> timing,
            Map<ActionProperty, List<APValue>> values);
}
