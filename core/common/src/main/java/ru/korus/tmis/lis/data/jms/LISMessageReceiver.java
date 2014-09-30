package ru.korus.tmis.lis.data.jms;

import ru.korus.tmis.core.exception.CoreException;
import ru.korus.tmis.lis.data.LaboratoryCreateRequestData;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.lang.annotation.Annotation;

/**
 * Абстактный класс для создания использования на стороне модулей интеграции лабораторий
 * Author: <a href="mailto:alexey.kislin@gmail.com">Alexey Kislin</a>
 * Date: 7/31/14
 * Time: 7:59 PM
 */
public abstract class LISMessageReceiver implements MessageListener {

    public final static String JMS_TYPE = LaboratoryCreateRequestData.JMS_TYPE;
    public final static String JMS_LAB_PROPERTY_NAME = "labName";

    @Resource(lookup = "DefaultConnectionFactory")
    protected ConnectionFactory connectionFactory;

    /**
     * Проверяем, что в классе-наследнике установленны именно те аннотации, которые нам нужны:
     * @MessageDriven(
     *   mappedName = "LaboratoryTopic",
     *   activationConfig = Array(
     *     new ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Topic"))
     * )
     */
    {
        Annotation[] annotations =  this.getClass().getAnnotations();
        boolean hasValidAnnotation = false;
        for(Annotation a : annotations) {
            System.out.println(a);
            boolean hasDestinationTypeProperty = false;
            if(a instanceof MessageDriven) {
                MessageDriven m = (MessageDriven) a;
                if(!m.mappedName().equals("LaboratoryTopic"))
                    throw new CoreException("Invalid MessageDriven mappedName value - should be LaboratoryTopic");
                for(ActivationConfigProperty p : m.activationConfig()) {
                    if(p.propertyName().equals("destinationType")) {
                        hasDestinationTypeProperty = true;
                        if(!p.propertyValue().equals("javax.jms.Topic"))
                            throw new CoreException("Invalid MessageDriven destinationType property value - should be javax.jms.Topic");
                    }
                }
                if(!hasDestinationTypeProperty)
                    throw new CoreException("There is no destinationType property - should be destinationType:javax.jms.Topic");
                hasValidAnnotation = true;
            }
        }
        if(!hasValidAnnotation)
            throw new CoreException("Class " + this.getClass() + " has no @MessageDriven annotation - incorrect implementation");
    }

    public LISMessageReceiver() throws CoreException {}

    /**
     * Метод обработки сообщений, должен отправлять ответ, во всех случаях, когда установленно, что сообщение
     * предназначается данной лаборатории
     * @param m {@link ObjectMessage} с определенным JMSType ({@link LaboratoryCreateRequestData#JMS_TYPE}),
     * прикрепленным объектом типа {@link LaboratoryCreateRequestData}, и StringProperty {@link #JMS_LAB_PROPERTY_NAME},
     * со значением кода лаборатории
     */
    @Override
    public void onMessage(Message m) {
        try {
            if(m == null ||
               !(m instanceof ObjectMessage) ||
               !JMS_TYPE.equals(m.getJMSType()) ||
               !m.getStringProperty(JMS_LAB_PROPERTY_NAME).equals(getLaboratoryCode()))
                return;

            LabModuleSendingResponse response = new LabModuleSendingResponse();
            try {
                ObjectMessage message = (ObjectMessage) m;

                if(!(message.getObject() instanceof LaboratoryCreateRequestData))
                    throw new Exception("Message should contains " + LaboratoryCreateRequestData.class.toString() +
                    ", but it contains " + (message.getObject() == null  ? "null" : message.getObject().getClass()));

                LaboratoryCreateRequestData request = (LaboratoryCreateRequestData) message.getObject();
                response.setActionId(request.getAction().getId());
                sendToLis(request);
                response.setSuccess(true);
            } catch(Throwable t) {
                response.setSuccess(false);
                response.setThrowable(t);
            } finally {
                Connection connection = null;
                Session session = null;
                try {
                    connection = connectionFactory.createConnection();
                    session = connection.createSession(true, Session.SESSION_TRANSACTED);
                    MessageProducer publisher = session.createProducer(m.getJMSReplyTo());
                    ObjectMessage message = session.createObjectMessage();
                    message.setJMSType(LabModuleSendingResponse.JMS_TYPE);
                    message.setObject(response);
                    publisher.send(message);
                }
                catch (Throwable t) {
                    t.printStackTrace();
                }
                finally {
                    if (session != null) try { session.close(); } catch (Throwable t) { t.printStackTrace(); }
                    if (connection != null) try { connection.close(); } catch (Throwable t) { t.printStackTrace(); }
                }
            }

        } catch (JMSException e) { // Unknown internal error
            e.printStackTrace();
            //TODO Error logging
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
     * @throws CoreException в случае возникновения ошибки при отправке
     */
    abstract protected void sendToLis(LaboratoryCreateRequestData data) throws CoreException;

    /**
     * Получение кода лаборатории, который выступает ее идентификатором в системе.
     * Указан в БД в таблице ***
     * @return КОд лаборатории в виде строки в БД
     */
    abstract protected String getLaboratoryCode();

}
