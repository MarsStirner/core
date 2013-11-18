/**
 * Tindicator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ru.korus.tmis.laboratory.across.ws;

public class Tindicator  implements java.io.Serializable {
    private String indicatorName;

    private String indicatorCode;

    public Tindicator() {
    }

    public Tindicator(
           String indicatorName,
           String indicatorCode) {
           this.indicatorName = indicatorName;
           this.indicatorCode = indicatorCode;
    }


    /**
     * Gets the indicatorName value for this Tindicator.
     *
     * @return indicatorName
     */
    public String getIndicatorName() {
        return indicatorName;
    }


    /**
     * Sets the indicatorName value for this Tindicator.
     *
     * @param indicatorName
     */
    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }


    /**
     * Gets the indicatorCode value for this Tindicator.
     *
     * @return indicatorCode
     */
    public String getIndicatorCode() {
        return indicatorCode;
    }


    /**
     * Sets the indicatorCode value for this Tindicator.
     *
     * @param indicatorCode
     */
    public void setIndicatorCode(String indicatorCode) {
        this.indicatorCode = indicatorCode;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof Tindicator)) return false;
        Tindicator other = (Tindicator) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.indicatorName==null && other.getIndicatorName()==null) ||
             (this.indicatorName!=null &&
              this.indicatorName.equals(other.getIndicatorName()))) &&
            ((this.indicatorCode==null && other.getIndicatorCode()==null) ||
             (this.indicatorCode!=null &&
              this.indicatorCode.equals(other.getIndicatorCode())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getIndicatorName() != null) {
            _hashCode += getIndicatorName().hashCode();
        }
        if (getIndicatorCode() != null) {
            _hashCode += getIndicatorCode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tindicator.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.korusconsulting.ru", "Tindicator"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicatorName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indicatorName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("indicatorCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "indicatorCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           String mechType,
           Class _javaType,
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
