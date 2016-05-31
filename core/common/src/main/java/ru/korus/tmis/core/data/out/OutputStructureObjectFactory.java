package ru.korus.tmis.core.data.out;

import ru.korus.tmis.core.data.FieldDescriptionData;
import ru.korus.tmis.core.entity.model.fd.FDField;
import ru.korus.tmis.core.entity.model.fd.FDFieldValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 25.05.2016, 18:47 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class OutputStructureObjectFactory {

    public static FDValue createFDValue(final FDFieldValue source) {
        if (source == null) {
            return null;
        }
        final FDValue result = new FDValue();
        result.setId(source.getId());
        result.setFdFieldId(source.getFDField().getId());
        result.setValue(source.getValue());
        return result;
    }

    public static List<FieldDescriptionData> createFieldDescriptionDataList(final List<FDField> sourceList) {
        if (sourceList == null || sourceList.isEmpty()) {
            return new ArrayList<>(0);
        }
        final List<FieldDescriptionData> result = new ArrayList<>(sourceList.size());
        for (FDField field : sourceList) {
            final FieldDescriptionData entry = createFieldDescriptionData(field);
            if (entry != null) {
                result.add(entry);
            }
        }
        return result;
    }

    private static FieldDescriptionData createFieldDescriptionData(final FDField source) {
        if (source == null) {
            return null;
        }
        final FieldDescriptionData result = new FieldDescriptionData();
        result.setId(source.getId());
        result.setName(source.getName());
        result.setOrder(source.getOrder());
        result.setDescription(source.getDescription());
        return result;
    }


    public static FDRecord createFDRecord(final ru.korus.tmis.core.entity.model.fd.FDRecord source, final List<FDFieldValue> fieldValues) {
        if (source == null) {
            return null;
        }
        final FDRecord result = new FDRecord();
        result.setId(source.getId());
        result.setOrder(source.getOrder());
        if (fieldValues != null && !fieldValues.isEmpty()) {
            final List<FDValue> fdValues = new ArrayList<>(fieldValues.size());
            for (FDFieldValue fieldValue : fieldValues) {
                fdValues.add(createFDValue(fieldValue));
            }
            result.setValues(fdValues);
        }
        return result;
    }

    public static List<FDRecord> createFdRecordList(final Map<ru.korus.tmis.core.entity.model.fd.FDRecord, List<FDFieldValue>> source) {
        if (source == null || source.isEmpty()) {
            return new ArrayList<>(0);
        }
        final List<FDRecord> result  = new ArrayList<>(source.size());
        for (Map.Entry<ru.korus.tmis.core.entity.model.fd.FDRecord, List<FDFieldValue>> entry : source.entrySet()) {
            final FDRecord transformedEntry = createFDRecord(entry.getKey(), entry.getValue());
            if(transformedEntry != null){
                result.add(transformedEntry);
            }
        }
        return result;
    }
}
