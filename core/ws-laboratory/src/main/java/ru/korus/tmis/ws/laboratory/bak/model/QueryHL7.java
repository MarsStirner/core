package ru.korus.tmis.ws.laboratory.bak.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.korus.tmis.util.ConfigManager;
import ru.korus.tmis.util.TextFormat;

import java.util.Map;

/**
 * Модель запроса в лабораторию
 *
 * @author anosov@outlook.com
 *         date: 5/22/13
 */
public class QueryHL7 {
    private static final Logger log = LoggerFactory.getLogger(QueryHL7.class);

    /**
     * Отправляемое сообщение
     */
    private TextFormat assignmentTemplate;

    Map<String, Object> data;

    public QueryHL7(Map<String, Object> _data) {
        this.data = _data;
        //todo из конфига убран шаблон
//        assignmentTemplate = new TextFormat(ConfigManager.getBakAssignmentTemplate());
    }

    /**
     * Преобразование в String запроса в формате HL7
     *
     * @see ConfigManager#getBakAssignmentTemplate
     * @return xml-like строка с актуальными значениями
     */
    public String format(){
        return assignmentTemplate.format(data);
    }
}
