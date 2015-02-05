package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.Nomenclature;
import ru.korus.tmis.core.entity.model.RlsBalanceOfGood;
import ru.korus.tmis.core.exception.CoreException;

import java.util.List;
import javax.ejb.Local;

@Local
public interface DbRlsBeanLocal {

    Nomenclature getRlsById(int id) throws CoreException;

    List<Nomenclature> getRlsByText(String text) throws CoreException;

    List<RlsBalanceOfGood> getRlsBalanceOfGood(Nomenclature nomen);
}
