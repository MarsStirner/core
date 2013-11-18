/**
 * BiomaterialInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ru.korus.tmis.laboratory.across.ws;

public class BiomaterialInfo  implements java.io.Serializable {
    private String orderBiomaterialCode;

    private String orderBiomaterialName;

    private int orderPrefBarCode;

    private String orderBarCode;

    private java.util.Calendar orderProbeDate;

    private String orderBiomaterialComment;

    public BiomaterialInfo() {
    }

    public BiomaterialInfo(
           String orderBiomaterialCode,
           String orderBiomaterialName,
           int orderPrefBarCode,
           String orderBarCode,
           java.util.Calendar orderProbeDate,
           String orderBiomaterialComment) {
           this.orderBiomaterialCode = orderBiomaterialCode;
           this.orderBiomaterialName = orderBiomaterialName;
           this.orderPrefBarCode = orderPrefBarCode;
           this.orderBarCode = orderBarCode;
           this.orderProbeDate = orderProbeDate;
           this.orderBiomaterialComment = orderBiomaterialComment;
    }


    /**
     * Gets the orderBiomaterialCode value for this BiomaterialInfo.
     *
     * @return orderBiomaterialCode
     */
    public String getOrderBiomaterialCode() {
        return orderBiomaterialCode;
    }


    /**
     * Sets the orderBiomaterialCode value for this BiomaterialInfo.
     *
     * @param orderBiomaterialCode
     */
    public void setOrderBiomaterialCode(String orderBiomaterialCode) {
        this.orderBiomaterialCode = orderBiomaterialCode;
    }


    /**
     * Gets the orderBiomaterialName value for this BiomaterialInfo.
     *
     * @return orderBiomaterialName
     */
    public String getOrderBiomaterialName() {
        return orderBiomaterialName;
    }


    /**
     * Sets the orderBiomaterialName value for this BiomaterialInfo.
     *
     * @param orderBiomaterialName
     */
    public void setOrderBiomaterialName(String orderBiomaterialName) {
        this.orderBiomaterialName = orderBiomaterialName;
    }


    /**
     * Gets the orderPrefBarCode value for this BiomaterialInfo.
     *
     * @return orderPrefBarCode
     */
    public int getOrderPrefBarCode() {
        return orderPrefBarCode;
    }


    /**
     * Sets the orderPrefBarCode value for this BiomaterialInfo.
     *
     * @param orderPrefBarCode
     */
    public void setOrderPrefBarCode(int orderPrefBarCode) {
        this.orderPrefBarCode = orderPrefBarCode;
    }


    /**
     * Gets the orderBarCode value for this BiomaterialInfo.
     *
     * @return orderBarCode
     */
    public String getOrderBarCode() {
        return orderBarCode;
    }


    /**
     * Sets the orderBarCode value for this BiomaterialInfo.
     *
     * @param orderBarCode
     */
    public void setOrderBarCode(String orderBarCode) {
        this.orderBarCode = orderBarCode;
    }


    /**
     * Gets the orderProbeDate value for this BiomaterialInfo.
     *
     * @return orderProbeDate
     */
    public java.util.Calendar getOrderProbeDate() {
        return orderProbeDate;
    }


    /**
     * Sets the orderProbeDate value for this BiomaterialInfo.
     *
     * @param orderProbeDate
     */
    public void setOrderProbeDate(java.util.Calendar orderProbeDate) {
        this.orderProbeDate = orderProbeDate;
    }


    /**
     * Gets the orderBiomaterialComment value for this BiomaterialInfo.
     *
     * @return orderBiomaterialComment
     */
    public String getOrderBiomaterialComment() {
        return orderBiomaterialComment;
    }


    /**
     * Sets the orderBiomaterialComment value for this BiomaterialInfo.
     *
     * @param orderBiomaterialComment
     */
    public void setOrderBiomaterialComment(String orderBiomaterialComment) {
        this.orderBiomaterialComment = orderBiomaterialComment;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof BiomaterialInfo)) return false;
        BiomaterialInfo other = (BiomaterialInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            ((this.orderBiomaterialCode==null && other.getOrderBiomaterialCode()==null) ||
             (this.orderBiomaterialCode!=null &&
              this.orderBiomaterialCode.equals(other.getOrderBiomaterialCode()))) &&
            ((this.orderBiomaterialName==null && other.getOrderBiomaterialName()==null) ||
             (this.orderBiomaterialName!=null &&
              this.orderBiomaterialName.equals(other.getOrderBiomaterialName()))) &&
            this.orderPrefBarCode == other.getOrderPrefBarCode() &&
            ((this.orderBarCode==null && other.getOrderBarCode()==null) ||
             (this.orderBarCode!=null &&
              this.orderBarCode.equals(other.getOrderBarCode()))) &&
            ((this.orderProbeDate==null && other.getOrderProbeDate()==null) ||
             (this.orderProbeDate!=null &&
              this.orderProbeDate.equals(other.getOrderProbeDate()))) &&
            ((this.orderBiomaterialComment==null && other.getOrderBiomaterialComment()==null) ||
             (this.orderBiomaterialComment!=null &&
              this.orderBiomaterialComment.equals(other.getOrderBiomaterialComment())));
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
        if (getOrderBiomaterialCode() != null) {
            _hashCode += getOrderBiomaterialCode().hashCode();
        }
        if (getOrderBiomaterialName() != null) {
            _hashCode += getOrderBiomaterialName().hashCode();
        }
        _hashCode += getOrderPrefBarCode();
        if (getOrderBarCode() != null) {
            _hashCode += getOrderBarCode().hashCode();
        }
        if (getOrderProbeDate() != null) {
            _hashCode += getOrderProbeDate().hashCode();
        }
        if (getOrderBiomaterialComment() != null) {
            _hashCode += getOrderBiomaterialComment().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BiomaterialInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.korusconsulting.ru", "BiomaterialInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderBiomaterialCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBiomaterialCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderBiomaterialName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBiomaterialName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderPrefBarCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderPrefBarCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderBarCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBarCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderProbeDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderProbeDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("orderBiomaterialComment");
        elemField.setXmlName(new javax.xml.namespace.QName("", "orderBiomaterialComment"));
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
