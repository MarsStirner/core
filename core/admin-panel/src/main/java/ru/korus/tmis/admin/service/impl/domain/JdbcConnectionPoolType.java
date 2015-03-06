//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.09.15 at 11:16:43 AM MSK 
//


package ru.korus.tmis.admin.service.impl.domain;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for jdbc-connection-poolType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jdbc-connection-poolType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="property" type="{}propertyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="connection-validation-method" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="datasource-classname" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="is-connection-validation-required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="connection-creation-retry-attempts" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="ping" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="validate-atmost-once-period-in-seconds" type="{http://www.w3.org/2001/XMLSchema}byte" />
 *       &lt;attribute name="res-type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="is-isolation-level-guaranteed" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="pool-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="jndi-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="object-type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jdbc-connection-poolType", propOrder = {
    "property"
})
public class JdbcConnectionPoolType {

    protected List<PropertyType> property;
    @XmlAttribute(name = "connection-validation-method")
    protected String connectionValidationMethod;
    @XmlAttribute(name = "datasource-classname")
    protected String datasourceClassname;
    @XmlAttribute(name = "name")
    protected String name;
    @XmlAttribute(name = "is-connection-validation-required")
    protected String isConnectionValidationRequired;
    @XmlAttribute(name = "connection-creation-retry-attempts")
    protected Byte connectionCreationRetryAttempts;
    @XmlAttribute(name = "ping")
    protected String ping;
    @XmlAttribute(name = "validate-atmost-once-period-in-seconds")
    protected Byte validateAtmostOncePeriodInSeconds;
    @XmlAttribute(name = "res-type")
    protected String resType;
    @XmlAttribute(name = "is-isolation-level-guaranteed")
    protected String isIsolationLevelGuaranteed;
    @XmlAttribute(name = "pool-name")
    protected String poolName;
    @XmlAttribute(name = "jndi-name")
    protected String jndiName;
    @XmlAttribute(name = "object-type")
    protected String objectType;

    /**
     * Gets the value of the property property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the property property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PropertyType }
     * 
     * 
     */
    public List<PropertyType> getProperty() {
        if (property == null) {
            property = new ArrayList<PropertyType>();
        }
        return this.property;
    }

    /**
     * Gets the value of the connectionValidationMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnectionValidationMethod() {
        return connectionValidationMethod;
    }

    /**
     * Sets the value of the connectionValidationMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnectionValidationMethod(String value) {
        this.connectionValidationMethod = value;
    }

    /**
     * Gets the value of the datasourceClassname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatasourceClassname() {
        return datasourceClassname;
    }

    /**
     * Sets the value of the datasourceClassname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatasourceClassname(String value) {
        this.datasourceClassname = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the isConnectionValidationRequired property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsConnectionValidationRequired() {
        return isConnectionValidationRequired;
    }

    /**
     * Sets the value of the isConnectionValidationRequired property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsConnectionValidationRequired(String value) {
        this.isConnectionValidationRequired = value;
    }

    /**
     * Gets the value of the connectionCreationRetryAttempts property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getConnectionCreationRetryAttempts() {
        return connectionCreationRetryAttempts;
    }

    /**
     * Sets the value of the connectionCreationRetryAttempts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setConnectionCreationRetryAttempts(Byte value) {
        this.connectionCreationRetryAttempts = value;
    }

    /**
     * Gets the value of the ping property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPing() {
        return ping;
    }

    /**
     * Sets the value of the ping property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPing(String value) {
        this.ping = value;
    }

    /**
     * Gets the value of the validateAtmostOncePeriodInSeconds property.
     * 
     * @return
     *     possible object is
     *     {@link Byte }
     *     
     */
    public Byte getValidateAtmostOncePeriodInSeconds() {
        return validateAtmostOncePeriodInSeconds;
    }

    /**
     * Sets the value of the validateAtmostOncePeriodInSeconds property.
     * 
     * @param value
     *     allowed object is
     *     {@link Byte }
     *     
     */
    public void setValidateAtmostOncePeriodInSeconds(Byte value) {
        this.validateAtmostOncePeriodInSeconds = value;
    }

    /**
     * Gets the value of the resType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResType() {
        return resType;
    }

    /**
     * Sets the value of the resType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResType(String value) {
        this.resType = value;
    }

    /**
     * Gets the value of the isIsolationLevelGuaranteed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsIsolationLevelGuaranteed() {
        return isIsolationLevelGuaranteed;
    }

    /**
     * Sets the value of the isIsolationLevelGuaranteed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsIsolationLevelGuaranteed(String value) {
        this.isIsolationLevelGuaranteed = value;
    }

    /**
     * Gets the value of the poolName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoolName() {
        return poolName;
    }

    /**
     * Sets the value of the poolName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoolName(String value) {
        this.poolName = value;
    }

    /**
     * Gets the value of the jndiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJndiName() {
        return jndiName;
    }

    /**
     * Sets the value of the jndiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJndiName(String value) {
        this.jndiName = value;
    }

    /**
     * Gets the value of the objectType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObjectType() {
        return objectType;
    }

    /**
     * Sets the value of the objectType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObjectType(String value) {
        this.objectType = value;
    }

}
