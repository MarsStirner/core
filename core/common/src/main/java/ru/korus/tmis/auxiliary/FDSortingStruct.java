package ru.korus.tmis.auxiliary;


import ru.korus.tmis.core.entity.model.fd.FDFieldValue;
import ru.korus.tmis.core.entity.model.fd.FDRecord;

import java.util.Set;

public class FDSortingStruct implements java.lang.Comparable<Object> {

    private FDRecord flatRecord;
    private java.util.LinkedList<FDFieldValue> flatValues;
    private java.util.LinkedHashMap<Integer, Integer> sorting;

    public FDSortingStruct(FDRecord fdr, java.util.LinkedList<FDFieldValue> that, java.util.LinkedHashMap<Integer, Integer> sorting) {
        this.flatRecord = fdr;
        this.flatValues = that;
        this.sorting = sorting;
    }

    public int compareTo(Object fdss) {

        if (!(fdss instanceof FDSortingStruct) || sorting.size() == 0)
            return 0;
        FDSortingStruct that = (FDSortingStruct) fdss;

        Set<Integer> fieldIds = sorting.keySet();

        for (java.util.Iterator<Integer> it = fieldIds.iterator(); it.hasNext(); ) {
            if (true) {
                int fieldId = it.next().intValue();
                String value_this = getValueByFieldId(this, fieldId);
                String value_that = getValueByFieldId(that, fieldId);
                if (value_this != null && value_that != null) {
                    if (sorting.get(fieldId).intValue() == 0) {  //asc
                        if (value_this.compareTo(value_that) < 0)
                            return -1;
                        else if (value_this.compareTo(value_that) > 0)
                            return 1;
                    } else {
                        if (value_this.compareTo(value_that) < 0)
                            return 1;
                        else if (value_this.compareTo(value_that) > 0)
                            return -1;
                    }
                }

            }
        }

        return 0;
    }

    private String getValueByFieldId(FDSortingStruct that, int fieldId) {

        for (int i = 0; i < that.flatValues.size(); i++) {
            if (this.flatValues.get(i).getFDField().getId().intValue() == fieldId) {
                return that.flatValues.get(i).getValue();
            }
        }
        return null;
    }

    public static FDSortingStruct[] getFDSortingStructArray(java.util.LinkedHashMap<FDRecord, java.util.LinkedList<FDFieldValue>> map,
                                                            java.util.LinkedHashMap<Integer, Integer> sorting) {

        java.util.LinkedList<FDSortingStruct> list = new java.util.LinkedList<FDSortingStruct>();

        Set<FDRecord> records = map.keySet();

        for (java.util.Iterator<FDRecord> it = records.iterator(); it.hasNext(); ) {
            if (true) {
                FDRecord record = it.next();
                list.add(new FDSortingStruct(record, map.get(record), sorting));
            }
        }
        FDSortingStruct[] array = list.toArray(new FDSortingStruct[0]);
        AuxiliaryQuickSort.sort(array);
        return array;
    }

    public static java.util.LinkedHashMap<FDRecord, java.util.LinkedList<FDFieldValue>> toHashMap(FDSortingStruct[] fdss, int begin, int end) {
        java.util.LinkedHashMap<FDRecord, java.util.LinkedList<FDFieldValue>> map =
                new java.util.LinkedHashMap<FDRecord, java.util.LinkedList<FDFieldValue>>();

        int count = (fdss.length < end) ? fdss.length : end;
        for (int i = begin; i < count; i++) {
            map.put(fdss[i].flatRecord, fdss[i].flatValues);
        }
        return map;
    }

}
