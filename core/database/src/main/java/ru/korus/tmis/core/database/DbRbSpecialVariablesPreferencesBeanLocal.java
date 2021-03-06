package ru.korus.tmis.core.database;

import ru.korus.tmis.core.entity.model.RbSpecialVariablesPreferences;
import ru.korus.tmis.core.entity.model.VariablesforSQL;
import ru.korus.tmis.core.entity.model.tfoms.*;

import javax.ejb.Local;
import javax.persistence.Entity;
import java.util.List;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.14, 18:29 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
@Local
public interface DbRbSpecialVariablesPreferencesBeanLocal {

    public RbSpecialVariablesPreferences getById(final Integer id);

    public RbSpecialVariablesPreferences getByName(final String name);

    public List<VariablesforSQL> getVariablesForQuery(final RbSpecialVariablesPreferences query);

    public Map<RbSpecialVariablesPreferences, List<VariablesforSQL>> getQueryWithVariablesByQueryName(final String name);

    public String getNamedQueryWithSettedParams(String name, Map<String, String> params);

    public <T> List<T> executeNamedQuery(String query, Class<T> entityClass);

    public <T> List<T> executeNamedQuery(String query, Class<T> entityClass, String resultSetName);

    public List executeNamedQuery(String query);

    public List<Map> executeNamedQueryForMap(String query);
}
