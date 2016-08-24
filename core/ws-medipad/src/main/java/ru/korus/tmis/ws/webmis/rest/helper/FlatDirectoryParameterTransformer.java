package ru.korus.tmis.ws.webmis.rest.helper;

import org.apache.commons.lang.StringUtils;
import ru.korus.tmis.auxiliary.AuxiliaryFunctions;
import ru.korus.tmis.core.entity.model.fd.FDField;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;

/**
 * Author: Upatov Egor <br>
 * Date: 26.05.2016, 19:18 <br>
 * Company: hitsl (Hi-Tech Solutions) <br>
 * Description: <br>
 */
public class FlatDirectoryParameterTransformer {

    public static List<FDField> getRequestedFields(final List<FDField> fieldList, final String fields) {
        if (StringUtils.isEmpty(fields)) {
            //Фильтр по полям пуст - значит запрашиваем все поля
            return new ArrayList<>(fieldList);
        } else {
            // Фильтр по полям не пуст - сопоставляем список кодов полей со списком полей справочника
            final List<String> codes = Arrays.asList(StringUtils.split(fields, ","));
            final List<FDField> result = new ArrayList<>(codes.size());
            for (FDField field : fieldList) {
                if (codes.contains(field.getName())) {
                    result.add(field);
                }
            }
            return result;
        }
    }

    public static Map<FDField, List<String>> getFilterFields(final List<FDField> fieldList, final MultivaluedMap<String, String> queryParameters) {
        final Map<String, List<String>> requestedFilterFields = AuxiliaryFunctions.foldFilterValueTo(queryParameters, "filter[", "]");
        if (requestedFilterFields == null || requestedFilterFields.isEmpty()) {
            return null;
        }
        final Map<FDField, List<String>> result = new HashMap<>(requestedFilterFields.size());
        for (FDField field : fieldList) {
            final String key = field.getName();
            if (StringUtils.isNotEmpty(key)) {
                final List<String> values = requestedFilterFields.get(key);
                if (values != null) {
                    result.put(field, values);
                }
            }
        }
        return result.isEmpty() ? null : result;
    }

    public static int getOffset(final Integer offset) {
        return offset == null || offset < 0 ? 0 : offset;
    }


    public static int getLimit(final Integer limit) {
        return limit == null || limit < 0 ? -1 : limit;
    }
}
