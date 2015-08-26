package ru.korus.tmis.core.database;


import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.config.ResultType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.entity.model.RbSpecialVariablesPreferences;
import ru.korus.tmis.core.entity.model.VariablesforSQL;
import ru.korus.tmis.core.entity.model.tfoms.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 03.02.14, 18:34 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */

@Stateless
public class DbRbSpecialVariablesPreferencesBean implements DbRbSpecialVariablesPreferencesBeanLocal {
    static final Logger logger = LoggerFactory.getLogger("ru.korus.tmis.tfoms");
    @PersistenceContext(unitName = "s11r64")
    EntityManager em = null;

    @Override
    public RbSpecialVariablesPreferences getById(Integer id) {
        return em.find(RbSpecialVariablesPreferences.class, id);
    }

    @Override
    public RbSpecialVariablesPreferences getByName(String name) {
        List<RbSpecialVariablesPreferences> result = em.createNamedQuery("rbSpecialVariablesPreferences.getByName", RbSpecialVariablesPreferences.class)
                .setParameter("name", name)
                .getResultList();
        if (!result.isEmpty()) {
            return result.get(0);
        }
        return null;
    }

    @Override
    public List<VariablesforSQL> getVariablesForQuery(RbSpecialVariablesPreferences query) {
        return em.createNamedQuery("VariablesforSQL.findSpecialVar", VariablesforSQL.class)
                .setParameter("specialVar", query)
                .getResultList();
    }

    @Override
    public Map<RbSpecialVariablesPreferences, List<VariablesforSQL>> getQueryWithVariablesByQueryName(String name) {
        final RbSpecialVariablesPreferences query = getByName(name);
        if (query != null) {
            final Map<RbSpecialVariablesPreferences, List<VariablesforSQL>> result = new HashMap<RbSpecialVariablesPreferences, List<VariablesforSQL>>();
            result.put(query, getVariablesForQuery(query));
            return result;
        }
        return null;
    }

    @Override
    public String getNamedQueryWithSettedParams(String name, Map<String, String> params) {
        Map<RbSpecialVariablesPreferences, List<VariablesforSQL>> queryWithParams = getQueryWithVariablesByQueryName(name);
        if (queryWithParams != null) {
            //Единственный элемент списка
            final Map.Entry<RbSpecialVariablesPreferences, List<VariablesforSQL>> entry = queryWithParams.entrySet().iterator().next();
            //Результат работы метода (на данном этапе - запрос с параметрами)
            String result = entry.getKey().getQuery();
            for (VariablesforSQL currentVariable : entry.getValue()) {
                String parameterName = currentVariable.getName();
                String value = params.get(parameterName);
                //Подстановка параметра в запрос
                result = result.replaceAll("::".concat(parameterName), value != null ? value : "NULL");
            }
            return result;
        }
        return "";
    }

    @Override
    public <T> List<T> executeNamedQuery(String query, Class<T> entityClass) {
        //adding detach
        List<T> result = em.createNativeQuery(query, entityClass).getResultList();

        return result;

    }

    @Override
    public <T> List<T> executeNamedQuery(String query, Class<T> entityClass, String resultSetName) {
        return em.createNativeQuery(query, resultSetName).getResultList();
    }

    @Override
    public List executeNamedQuery(String query) {
        return em.createNativeQuery(query).getResultList();
    }

    @Override
    public List<Map> executeNamedQueryForMap(String query){
        return em.createNativeQuery(query).setHint(QueryHints.RESULT_TYPE, ResultType.Map).getResultList();
    }
}
