package ru.korus.tmis.core.database;


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
        List<T> result =  em.createNativeQuery(query, entityClass).getResultList();
        for(T current : result){
            em.detach(current);
        }
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
    public List<UploadItem> executeNamedQueryForUploadItem(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<UploadItem> typedResultList = new ArrayList<UploadItem>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new UploadItem(current));
        }
        return typedResultList;
    }

    @Override
    public List<PatientProperties> executeNamedQueryForPatientProperties(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<PatientProperties> typedResultList = new ArrayList<PatientProperties>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new PatientProperties(current));
        }
        return typedResultList;
    }

    @Override
    public List<PatientPolicy> executeNamedQueryForPolicy(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<PatientPolicy> typedResultList = new ArrayList<PatientPolicy>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new PatientPolicy(current));
        }
        return typedResultList;
    }

    @Override
    public List<PatientDocument> executeNamedQueryForDocument(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<PatientDocument> typedResultList = new ArrayList<PatientDocument>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new PatientDocument(current));
        }
        return typedResultList;
    }

    @Override
    public List<UploadItemProperties> executeNamedQueryForUploadItemProperties(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<UploadItemProperties> typedResultList = new ArrayList<UploadItemProperties>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new UploadItemProperties(current));
        }
        return typedResultList;
    }

    @Override
    public List<PatientOKATOAddress> executeNamedQueryForOKATO(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<PatientOKATOAddress> typedResultList = new ArrayList<PatientOKATOAddress>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new PatientOKATOAddress(current));
        }
        return typedResultList;
    }

    @Override
    public List<SluchProperties> executeNamedQueryForSluchProperties(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<SluchProperties> typedResultList = new ArrayList<SluchProperties>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new SluchProperties(current));
        }
        return typedResultList;
    }

    @Override
    public List<UploadUslProperties> executeNamedQueryForUploadUslProperties(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<UploadUslProperties> typedResultList = new ArrayList<UploadUslProperties>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new UploadUslProperties(current));
        }
        return typedResultList;
    }

    @Override
    public List<UploadUslItem> executeNamedQueryForUploadUslItem(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<UploadUslItem> typedResultList = new ArrayList<UploadUslItem>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new UploadUslItem(current));
        }
        return typedResultList;
    }

    @Override
    public List<SluchCodeMes> executeNamedQueryForCodeMes(String query) {
        final List<Object[]> resultSet = em.createNativeQuery(query).getResultList();
        final List<SluchCodeMes> typedResultList = new ArrayList<SluchCodeMes>(resultSet.size());
        for(Object[] current : resultSet){
            typedResultList.add(new SluchCodeMes(current));
        }
        return typedResultList;
    }


}
