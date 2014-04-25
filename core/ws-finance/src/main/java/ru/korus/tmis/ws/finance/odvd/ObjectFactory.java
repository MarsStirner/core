
package ru.korus.tmis.ws.finance.odvd;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ru.korus.tmis.ws.finance.odvd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ru.korus.tmis.ws.finance.odvd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PutServiceResponse }
     * 
     */
    public PutServiceResponse createPutServiceResponse() {
        return new PutServiceResponse();
    }

    /**
     * Create an instance of {@link PutService }
     * 
     */
    public PutService createPutService() {
        return new PutService();
    }

    /**
     * Create an instance of {@link TableName }
     * 
     */
    public TableName createTableName() {
        return new TableName();
    }

    /**
     * Create an instance of {@link Table }
     * 
     */
    public Table createTable() {
        return new Table();
    }

    /**
     * Create an instance of {@link PutTreatment }
     * 
     */
    public PutTreatment createPutTreatment() {
        return new PutTreatment();
    }

    /**
     * Create an instance of {@link PutTreatmentResponse }
     * 
     */
    public PutTreatmentResponse createPutTreatmentResponse() {
        return new PutTreatmentResponse();
    }

    /**
     * Create an instance of {@link RowTableName }
     * 
     */
    public RowTableName createRowTableName() {
        return new RowTableName();
    }

    /**
     * Create an instance of {@link RowTable }
     * 
     */
    public RowTable createRowTable() {
        return new RowTable();
    }

}
