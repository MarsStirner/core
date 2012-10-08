package ru.korus.tmis.core.database;

import ru.korus.tmis.core.data.RlsDataListFilter;
import ru.korus.tmis.core.entity.model.Nomenclature;
import ru.korus.tmis.core.exception.CoreException;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DbRlsBeanLocal {
    List<Nomenclature> getRlsDrugList()
            throws CoreException;

    long getCountOfRlsRecordsWithFilter(RlsDataListFilter filter)
            throws CoreException;

    List<Nomenclature> getRlsListWithFilter(int page, int limit, String sortingField, String sortingMethod, RlsDataListFilter filter)
            throws CoreException;
}
