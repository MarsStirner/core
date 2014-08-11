package ru.korus.tmis.laboratory;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.data.LaboratoryCreateRequestData;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

/**
 * Абстактный класс для создания использования на стороне модулей интеграции лабораторий
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 7/31/14
 * Time: 7:59 PM
 */
public abstract class LISMessageReceiver implements MessageListener {

    public final static String JMS_TYPE = "LabResearchRequest";
    public final static String JMS_LAB_PROPERTY_NAME = "labName";

    {
        //TODO check @MessageDriven annotation: if no - throw exception
    }

    @Override
    public void onMessage(Message message) {
        Object result = null;
        try {
            if(message.getJMSType().equals(JMS_TYPE)
            && message instanceof ObjectMessage
            && message.getStringProperty(JMS_LAB_PROPERTY_NAME).equals(getLaboratoryCode())) {
                ObjectMessage objectMessage = (ObjectMessage) message;
                if(objectMessage.getObject() instanceof LaboratoryCreateRequestData) {
                    sendToLis((LaboratoryCreateRequestData) objectMessage.getObject());
                }
            }
            // TODO set success message to result object
        } catch (Throwable t) {
            // TODO set error message to result object
        } finally {
            // TODO Send jms-result to core
        }
    }

    /**
     * Метод отправки результатов лабораторного исследования к МИС.
     * Его требуется вызывать при получении данных от лаборатории
     * @throws CoreException при ошибке отправки данных к МИС
     */
    public void sendResultOfResearchRequestToMIS(Object results) throws CoreException {
        // TODO implement sending results
    }

    /**
     * Отправка запроса на проведение лабораторного исследования в лабораторию
     * @param data Данные для создания запроса лабораторного исследования
     * @return Результаты отправки данных в лабораторию
     * @throws CoreException в случае возникновения ошибки при отправке
     */
    abstract protected Object sendToLis(LaboratoryCreateRequestData data) throws CoreException;

    /**
     * Получение кода лаборатории, который выступает ее идентификатором в системе.
     * Указан в БД в таблице ***
     * @return КОд лаборатории в виде строки в БД
     */
    abstract protected String getLaboratoryCode();

}
