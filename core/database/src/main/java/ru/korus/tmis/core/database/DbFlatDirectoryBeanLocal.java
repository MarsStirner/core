package ru.korus.tmis.core.database;

import ru.korus.tmis.core.auth.AuthData;
import ru.korus.tmis.core.data.FlatDirectoryRequestData;
import ru.korus.tmis.core.entity.model.fd.FDField;
import ru.korus.tmis.core.entity.model.fd.FDFieldValue;
import ru.korus.tmis.core.entity.model.fd.FDRecord;
import ru.korus.tmis.core.entity.model.fd.FlatDirectory;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Local
public interface DbFlatDirectoryBeanLocal {

    List<FlatDirectory> getFlatDirectories(int page, int limit, String sortField, String sortMethod, Object filter, AuthData userData)
            throws CoreException;

    LinkedHashMap<FlatDirectory, LinkedHashMap<FDRecord, LinkedList<FDFieldValue>>> getFlatDirectoriesWithFilterRecords(
            int page,
            int limit,
            java.util.LinkedHashMap<Integer, Integer> sorting,
            Object filter,
            FlatDirectoryRequestData request,
            AuthData userData
    ) throws CoreException;

    FlatDirectory getByCode(String code);

    /**
     * Получение записей FD (запрошенный кусок (limit+offset)) со значениями заданных полей
     *
     * @param flatDirectory спарвочник, для которого нужно вытащить записи
     * @param fields        заданные поля (в результате будт только значения с этими полями)
     * @return Порция запрошенных записей справочника
     */
    Map<FDRecord, List<FDFieldValue>> getRecordsWithFilter(FlatDirectory flatDirectory, List<FDField> fields);

    Map<FDRecord, List<FDFieldValue>> getRecordsWithFilter(
            FlatDirectory flatDirectory, List<FDField> fields, Map<FDField, List<String>> filter
    );

    Map<FDRecord, List<FDFieldValue>> getRecordsWithFilter(
            FlatDirectory flatDirectory, List<FDField> fields, String filter
    );
}
