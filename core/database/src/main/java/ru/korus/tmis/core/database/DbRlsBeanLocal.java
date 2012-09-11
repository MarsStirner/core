package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Nomenclature;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import javax.ejb.Local;

@Local
public interface DbRlsBeanLocal {
    List<Nomenclature> getRlsDrugList()
            throws CoreException;
}
