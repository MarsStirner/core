package ru.korus.tmis.communication;

/**
 * User: eupatov<br>
 * Date: 15.03.13 at 11:15<br>
 * Company Korus Consulting IT<br>
 * Перечисление для названия полей в карте документов
 */
public enum DocumentMapFields {
    /**
     * Идентификатор клиента
     */
    CLIENT_ID("client_id"),
    /**
     * Номер полиса\документа
     */
    NUMBER("number"),
    /**
     * Серия полиса\документа
     */
    SERIAL("serial"),
    /**
     * Код документа
     */
    DOCUMENT_CODE("document_code"),
    /**
     * Тип полиса
     */
    POLICY_TYPE("policy_type");

    private String fieldName;

    private DocumentMapFields(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

}