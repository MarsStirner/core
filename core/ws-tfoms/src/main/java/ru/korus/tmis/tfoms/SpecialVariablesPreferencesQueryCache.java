package ru.korus.tmis.tfoms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.core.database.DbRbSpecialVariablesPreferencesBeanLocal;
import ru.korus.tmis.core.entity.model.RbSpecialVariablesPreferences;
import ru.korus.tmis.core.entity.model.VariablesforSQL;
import ru.korus.tmis.core.entity.model.tfoms.ObjectParser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Upatov Egor <br>
 * Date: 20.03.14, 22:29 <br>
 * Company: Korus Consulting IT <br>
 * Description: <br>
 */
public class SpecialVariablesPreferencesQueryCache {
    //Logger
    static final Logger logger = LoggerFactory.getLogger(SpecialVariablesPreferencesQueryCache.class);

    private DbRbSpecialVariablesPreferencesBeanLocal specialVariablesPreferencesBean = null;

    private final List<CachedQuery> cachedQueries;

    public SpecialVariablesPreferencesQueryCache(final DbRbSpecialVariablesPreferencesBeanLocal specialVariablesPreferencesBean) {
        this.cachedQueries = new ArrayList<CachedQuery>();
        this.specialVariablesPreferencesBean = specialVariablesPreferencesBean;
    }

    public void clearCache() {
        this.cachedQueries.clear();
    }

    public void cacheQueries() {
        //Основные запросы
        cacheQuery(Constants.QUERY_NAME_STATIONARY_MAIN);
        cacheQuery(Constants.QUERY_NAME_POLICLINIC_MAIN);
        cacheQuery(Constants.QUERY_NAME_POLICLINIC_ADDITIONAL);
        cacheQuery(Constants.QUERY_NAME_DISPANSERIZATION_ADDITIONAL);
        //PreSelect section
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_MAIN_DIAGNOSIS);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_SECONDARY_DIAGNOSIS);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_RESULT);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_ISHOD);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_CSG);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_HOSPITAL_BED_PROFILE);
        cacheQuery(Constants.QUERY_NAME_STATIONARY_PROPERTY_TYPE_STAGE);

        cacheQuery(Constants.QUERY_NAME_MULTIPLE_BIRTH_CONTACT_TYPE);
        //Проверка позиций счета
        cacheQuery(Constants.QUERY_NAME_ACCOUNTITEM_CHECK_ADDITIONAL);
        cacheQuery(Constants.QUERY_NAME_ACCOUNTITEM_CHECK_PRIMARY);
        // Свойства пациента
        cacheQuery(Constants.QUERY_NAME_PATIENT_PROPERTIES);
        cacheQuery(Constants.QUERY_NAME_PATIENT_DOCUMENT);
        cacheQuery(Constants.QUERY_NAME_PATIENT_POLICY);
        cacheQuery(Constants.QUERY_NAME_PATIENT_OKATOG);
        cacheQuery(Constants.QUERY_NAME_PATIENT_OKATOP);
        cacheQuery(Constants.QUERY_NAME_PATIENT_SPOKESMAN);

        cacheQuery(Constants.OS_SLUCH);

        // PreSelect queries
        cacheQuery(Constants.QUERY_NAME_MOVING_ACTION_TYPE);
    }

    public boolean cacheQuery(final String queryName) {
        final Map<RbSpecialVariablesPreferences, List<VariablesforSQL>> queryWithVariables =
                specialVariablesPreferencesBean.getQueryWithVariablesByQueryName(queryName);
        if (queryWithVariables != null && !queryWithVariables.isEmpty()) {
            //Единственный элемент списка
            final Map.Entry<RbSpecialVariablesPreferences, List<VariablesforSQL>> entry = queryWithVariables.entrySet().iterator().next();
            final CachedQuery newQuery = new CachedQuery(queryName, entry.getKey().getQuery());
            for (VariablesforSQL currentVariable : entry.getValue()) {
                newQuery.addQueryParameter(currentVariable.getName());
            }
            this.cachedQueries.add(newQuery);
            return true;
        } else {
            logger.error("Query with name[{}] not exists", queryName);
            return false;
        }
    }

    public void printSummaryUsage() {
        logger.info("Summary usage of Special Queries:");
        for (CachedQuery current : cachedQueries) {
            logger.info(current.timingSummary());
        }
    }

    public String getQueryWithSettedParams(final CachedQuery query, final Map<String, String> parameters) {
        String result = query.getQuery();
        if (parameters != null) {
            //Если параметры  = NULL то получить только содержимое свободной переменной и ничего не подставлять
            for (String parameterName : query.getQueryParameters()) {
                //Значение параметра из полученной карты
                String value = parameters.get(parameterName);
                //Подстановка параметра в запрос
                result = result.replaceAll("::".concat(parameterName), value != null ? value : "NULL");
            }
        }
        return result;
    }

    public String getQueryWithSettedParams(final String queryName, final Map<String, String> parameters) {
        for (CachedQuery current : cachedQueries) {
            //Имя свободной переменной совпадает с искомым
            if (current.getQueryName().equals(queryName)) {
                String result = current.getQuery();
                if (parameters != null) {
                    //Если параметры  = NULL то получить только содержимое свободной переменной и ничего не подставлять
                    for (String parameterName : current.getQueryParameters()) {
                        //Значение параметра из полученной карты
                        String value = parameters.get(parameterName);
                        //Подстановка параметра в запрос
                        result = result.replaceAll("::".concat(parameterName), value != null ? value : "NULL");
                    }
                }
                return result;
            }
        }
        return null;
    }


    public void setStartedParameters(final Map<String, String> parameters) {
        for (CachedQuery current : cachedQueries) {
            String currentQuery = current.getQuery();
            for (String parameterName : current.getQueryParameters()) {
                String value = parameters.get(parameterName);
                if (value != null) {
                    // Заменяем только вхождения нашего параметра, остальные не трогаем (Не меняем на NULL)
                    currentQuery = currentQuery.replaceAll("::".concat(parameterName), value);
                }
            }
            // сохраняем запрос с частью подставленных параметров
            current.setQuery(currentQuery);
            logger.info(current.toString());
        }
    }

    private CachedQuery getCachedQueryByName(final String queryName) {
        for (CachedQuery current : cachedQueries) {
            if (queryName.equals(current.getQueryName())) {
                return current;
            }
        }
        return null;
    }

    public String executeNamedQueryForSingleStringResult(final String queryName, final Map<String, String> parameters) {
        final CachedQuery neededQuery = getCachedQueryByName(queryName);
        if (neededQuery != null) {
            final String sqlQuery = getQueryWithSettedParams(neededQuery, parameters);
            final long startTime = System.currentTimeMillis();
            final List resultSet = specialVariablesPreferencesBean.executeNamedQuery(sqlQuery);
            neededQuery.addTiming(System.currentTimeMillis() - startTime);
            if (!resultSet.isEmpty()) {
                return resultSet.iterator().next().toString();
            }
        } else {
            logger.info("Query with name={} not exists. Return emptyResultSet.", queryName);
        }
        return null;
    }

    public Integer executeNamedQueryForIntegerResult(final String queryName, final Map<String, String> parameters) {
        final CachedQuery neededQuery = getCachedQueryByName(queryName);
        if (neededQuery != null) {
            final String sqlQuery = getQueryWithSettedParams(neededQuery, parameters);
            final long startTime = System.currentTimeMillis();
            final List resultSet = specialVariablesPreferencesBean.executeNamedQuery(sqlQuery);
            neededQuery.addTiming(System.currentTimeMillis() - startTime);
            if (!resultSet.isEmpty()) {
                return ObjectParser.getIntegerValue(resultSet.iterator().next());
            }
        } else {
            logger.info("Query with name={} not exists. Return emptyResultSet.", queryName);
        }
        return null;
    }

    public <T> List<T> executeNamedQuery(final String queryName, final Map<String, String> parameters, Class<T> entityClass) {
        final CachedQuery neededQuery = getCachedQueryByName(queryName);
        if (neededQuery != null) {
            final String sqlQuery = getQueryWithSettedParams(neededQuery, parameters);
            //logger.debug(sqlQuery);
            final long startTime = System.currentTimeMillis();
            final List<Object[]> resultSet = specialVariablesPreferencesBean.executeNamedQuery(sqlQuery);
            neededQuery.addTiming(System.currentTimeMillis() - startTime);
            final List<T> typedResultList = new ArrayList<T>(resultSet.size());
            for (Object[] current : resultSet) {
                try {
                    typedResultList.add(entityClass.getDeclaredConstructor(Object[].class).newInstance(new Object[]{current}));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
            return typedResultList;
        } else {
            logger.info("Query with name={} not exists. Return emptyResultSet.", queryName);
            return new ArrayList<T>(0);
        }
    }

    public <T> List<T> executeNamedQuery(final String queryName, Class<T> resultClass, Map<String, String> queryParameters, boolean queryToLog) {
        final CachedQuery neededQuery = getCachedQueryByName(queryName);
        if (neededQuery != null) {
            final String sqlQuery = getQueryWithSettedParams(neededQuery, queryParameters);
            if (queryToLog) {
                logger.debug(sqlQuery);
            }
            final long startTime = System.currentTimeMillis();
            final List<Object[]> resultSet = specialVariablesPreferencesBean.executeNamedQuery(sqlQuery);
            neededQuery.addTiming(System.currentTimeMillis() - startTime);
            final List<T> typedResultList = new ArrayList<T>(resultSet.size());
            final Constructor<T> constructor;
            try {
                constructor = resultClass.getDeclaredConstructor(Object[].class);
            } catch (NoSuchMethodException e) {
                logger.error("No such constructor for Object[] args.", e);
                return new ArrayList<T>(0);
            }
            for (Object[] current : resultSet) {
                try {
                    typedResultList.add(constructor.newInstance(new Object[]{current}));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return typedResultList;
        } else {
            logger.info("Query with name={} not exists. Return emptyResultSet.", queryName);
            return new ArrayList<T>(0);
        }
    }

    public <T> List<T> executeNamedQueryWithMap(final String queryName, Class<T> resultClass, Map<String, String> queryParameters, boolean queryToLog) {
        final CachedQuery neededQuery = getCachedQueryByName(queryName);
        if (neededQuery != null) {
            final String sqlQuery = getQueryWithSettedParams(neededQuery, queryParameters);
            if (queryToLog) {
                logger.debug(sqlQuery);
            }
            final long startTime = System.currentTimeMillis();
            final List<Map> resultSet = specialVariablesPreferencesBean.executeNamedQueryForMap(sqlQuery);
            neededQuery.addTiming(System.currentTimeMillis() - startTime);
            final List<T> typedResultList = new ArrayList<T>(resultSet.size());
            final Constructor<T> constructor;
            try {
                constructor = resultClass.getDeclaredConstructor(Map.class);
            } catch (NoSuchMethodException e) {
                logger.error("No such constructor for Object[] args.", e);
                return new ArrayList<T>(0);
            }
            for (Map current : resultSet) {
                try {
                    typedResultList.add(constructor.newInstance(current));
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            return typedResultList;
        } else {
            logger.info("Query with name={} not exists. Return emptyResultSet.", queryName);
            return new ArrayList<T>(0);
        }
    }

    private class CachedQuery {
        private String queryName;
        private String query;
        private List<String> queryParameters;

        private List<Long> timings;

        private CachedQuery(String queryName, String query) {
            this.queryName = queryName;
            this.query = query;
            this.timings = new ArrayList<Long>();
            this.queryParameters = new ArrayList<String>();
        }

        public String getQueryName() {
            return queryName;
        }


        public String getQuery() {
            return query;
        }

        public void setQuery(String query) {
            this.query = query;
        }

        public List<String> getQueryParameters() {
            return queryParameters;
        }

        public void addTiming(long millis) {
            timings.add(millis);
        }

        public String timingSummary() {
            long max = -1;
            long total = 0;
            for (long current : timings) {
                total += current;
                if (max < current) {
                    max = current;
                }
            }
            if (timings.isEmpty()) {
                return "NAME=\'" + queryName + "\' Calls: " + timings.size();
            }
            return "NAME=\'" + queryName + "\' Calls: " + timings.size() + " Average: " + total / timings.size() + " TotalTime: " + total / 1000 + " MAXTime: " + max;
        }

        public void addQueryParameter(final String paramName) {
            queryParameters.add(paramName);
        }

        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder();
            result.append("Query with name [").append(queryName)
                    .append("] cached.\nSQL=\n").append(query);
            if (!queryParameters.isEmpty()) {
                result.append("\n**************************************");
            }
            for (String paramName : queryParameters) {
                result.append("\nPARAM = ").append(paramName);
            }
            result.append("\n------------------------------------------");
            return result.toString();
        }
    }
}
