/**
 * PatientInfo.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package ru.korus.tmis.laboratory.across.ws;

public class PatientInfo  implements java.io.Serializable {
    private int patientMisId;

    private String patientFamily;

    private String patientName;

    private String patientPatronum;

    private String patientBirthDate;

    private int patientSex;

    public PatientInfo() {
    }

    public PatientInfo(
           int patientMisId,
           String patientFamily,
           String patientName,
           String patientPatronum,
           String patientBirthDate,
           int patientSex) {
           this.patientMisId = patientMisId;
           this.patientFamily = patientFamily;
           this.patientName = patientName;
           this.patientPatronum = patientPatronum;
           this.patientBirthDate = patientBirthDate;
           this.patientSex = patientSex;
    }


    /**
     * Gets the patientMisId value for this PatientInfo.
     *
     * @return patientMisId
     */
    public int getPatientMisId() {
        return patientMisId;
    }


    /**
     * Sets the patientMisId value for this PatientInfo.
     *
     * @param patientMisId
     */
    public void setPatientMisId(int patientMisId) {
        this.patientMisId = patientMisId;
    }


    /**
     * Gets the patientFamily value for this PatientInfo.
     *
     * @return patientFamily
     */
    public String getPatientFamily() {
        return patientFamily;
    }


    /**
     * Sets the patientFamily value for this PatientInfo.
     *
     * @param patientFamily
     */
    public void setPatientFamily(String patientFamily) {
        this.patientFamily = patientFamily;
    }


    /**
     * Gets the patientName value for this PatientInfo.
     *
     * @return patientName
     */
    public String getPatientName() {
        return patientName;
    }


    /**
     * Sets the patientName value for this PatientInfo.
     *
     * @param patientName
     */
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }


    /**
     * Gets the patientPatronum value for this PatientInfo.
     *
     * @return patientPatronum
     */
    public String getPatientPatronum() {
        return patientPatronum;
    }


    /**
     * Sets the patientPatronum value for this PatientInfo.
     *
     * @param patientPatronum
     */
    public void setPatientPatronum(String patientPatronum) {
        this.patientPatronum = patientPatronum;
    }


    /**
     * Gets the patientBirthDate value for this PatientInfo.
     *
     * @return patientBirthDate
     */
    public String getPatientBirthDate() {
        return patientBirthDate;
    }


    /**
     * Sets the patientBirthDate value for this PatientInfo.
     *
     * @param patientBirthDate
     */
    public void setPatientBirthDate(String patientBirthDate) {
        this.patientBirthDate = patientBirthDate;
    }


    /**
     * Gets the patientSex value for this PatientInfo.
     *
     * @return patientSex
     */
    public int getPatientSex() {
        return patientSex;
    }


    /**
     * Sets the patientSex value for this PatientInfo.
     *
     * @param patientSex
     */
    public void setPatientSex(int patientSex) {
        this.patientSex = patientSex;
    }

    private Object __equalsCalc = null;
    public synchronized boolean equals(Object obj) {
        if (!(obj instanceof PatientInfo)) return false;
        PatientInfo other = (PatientInfo) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true &&
            this.patientMisId == other.getPatientMisId() &&
            ((this.patientFamily==null && other.getPatientFamily()==null) ||
             (this.patientFamily!=null &&
              this.patientFamily.equals(other.getPatientFamily()))) &&
            ((this.patientName==null && other.getPatientName()==null) ||
             (this.patientName!=null &&
              this.patientName.equals(other.getPatientName()))) &&
            ((this.patientPatronum==null && other.getPatientPatronum()==null) ||
             (this.patientPatronum!=null &&
              this.patientPatronum.equals(other.getPatientPatronum()))) &&
            ((this.patientBirthDate==null && other.getPatientBirthDate()==null) ||
             (this.patientBirthDate!=null &&
              this.patientBirthDate.equals(other.getPatientBirthDate()))) &&
            this.patientSex == other.getPatientSex();
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
        _hashCode += getPatientMisId();
        if (getPatientFamily() != null) {
            _hashCode += getPatientFamily().hashCode();
        }
        if (getPatientName() != null) {
            _hashCode += getPatientName().hashCode();
        }
        if (getPatientPatronum() != null) {
            _hashCode += getPatientPatronum().hashCode();
        }
        if (getPatientBirthDate() != null) {
            _hashCode += getPatientBirthDate().hashCode();
        }
        _hashCode += getPatientSex();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PatientInfo.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.korusconsulting.ru", "PatientInfo"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientMisId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientMisId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientFamily");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientFamily"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientPatronum");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientPatronum"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientBirthDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientBirthDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("patientSex");
        elemField.setXmlName(new javax.xml.namespace.QName("", "patientSex"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
