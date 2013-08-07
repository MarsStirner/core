
package ru.cgm.service;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for HL7_document complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HL7_document">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="typeId">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="extention" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="id">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="extention" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="code" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="effectiveTime">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="confidentialityCode">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="codeSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="languageCode">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="versionNumber">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="recordTarget" type="{http://cgm.ru}recordTargetInfo"/>
 *         &lt;element name="author" type="{http://cgm.ru}authorInfo"/>
 *         &lt;element name="componentOf" type="{http://cgm.ru}componentOfInfo"/>
 *         &lt;element name="component" type="{http://cgm.ru}componentInfo"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HL7_document", propOrder = {
    "typeId",
    "id",
    "code",
    "title",
    "effectiveTime",
    "confidentialityCode",
    "languageCode",
    "versionNumber",
    "recordTarget",
    "author",
    "componentOf",
    "component"
})
public class HL7Document {

    @XmlElement(required = true)
    protected HL7Document.TypeId typeId;
    @XmlElement(required = true)
    protected HL7Document.Id id;
    @XmlElement(required = true)
    protected Object code;
    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected HL7Document.EffectiveTime effectiveTime;
    @XmlElement(required = true)
    protected HL7Document.ConfidentialityCode confidentialityCode;
    @XmlElement(required = true)
    protected HL7Document.LanguageCode languageCode;
    @XmlElement(required = true)
    protected HL7Document.VersionNumber versionNumber;
    @XmlElement(required = true)
    protected RecordTargetInfo recordTarget;
    @XmlElement(required = true)
    protected AuthorInfo author;
    @XmlElement(required = true)
    protected ComponentOfInfo componentOf;
    @XmlElement(required = true)
    protected ComponentInfo component;

    /**
     * Gets the value of the typeId property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.TypeId }
     *     
     */
    public HL7Document.TypeId getTypeId() {
        return typeId;
    }

    /**
     * Sets the value of the typeId property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.TypeId }
     *     
     */
    public void setTypeId(HL7Document.TypeId value) {
        this.typeId = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.Id }
     *     
     */
    public HL7Document.Id getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.Id }
     *     
     */
    public void setId(HL7Document.Id value) {
        this.id = value;
    }

    /**
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getCode() {
        return code;
    }

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setCode(Object value) {
        this.code = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the effectiveTime property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.EffectiveTime }
     *     
     */
    public HL7Document.EffectiveTime getEffectiveTime() {
        return effectiveTime;
    }

    /**
     * Sets the value of the effectiveTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.EffectiveTime }
     *     
     */
    public void setEffectiveTime(HL7Document.EffectiveTime value) {
        this.effectiveTime = value;
    }

    /**
     * Gets the value of the confidentialityCode property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.ConfidentialityCode }
     *     
     */
    public HL7Document.ConfidentialityCode getConfidentialityCode() {
        return confidentialityCode;
    }

    /**
     * Sets the value of the confidentialityCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.ConfidentialityCode }
     *     
     */
    public void setConfidentialityCode(HL7Document.ConfidentialityCode value) {
        this.confidentialityCode = value;
    }

    /**
     * Gets the value of the languageCode property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.LanguageCode }
     *     
     */
    public HL7Document.LanguageCode getLanguageCode() {
        return languageCode;
    }

    /**
     * Sets the value of the languageCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.LanguageCode }
     *     
     */
    public void setLanguageCode(HL7Document.LanguageCode value) {
        this.languageCode = value;
    }

    /**
     * Gets the value of the versionNumber property.
     * 
     * @return
     *     possible object is
     *     {@link HL7Document.VersionNumber }
     *     
     */
    public HL7Document.VersionNumber getVersionNumber() {
        return versionNumber;
    }

    /**
     * Sets the value of the versionNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link HL7Document.VersionNumber }
     *     
     */
    public void setVersionNumber(HL7Document.VersionNumber value) {
        this.versionNumber = value;
    }

    /**
     * Gets the value of the recordTarget property.
     * 
     * @return
     *     possible object is
     *     {@link RecordTargetInfo }
     *     
     */
    public RecordTargetInfo getRecordTarget() {
        return recordTarget;
    }

    /**
     * Sets the value of the recordTarget property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecordTargetInfo }
     *     
     */
    public void setRecordTarget(RecordTargetInfo value) {
        this.recordTarget = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link AuthorInfo }
     *     
     */
    public AuthorInfo getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthorInfo }
     *     
     */
    public void setAuthor(AuthorInfo value) {
        this.author = value;
    }

    /**
     * Gets the value of the componentOf property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentOfInfo }
     *     
     */
    public ComponentOfInfo getComponentOf() {
        return componentOf;
    }

    /**
     * Sets the value of the componentOf property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentOfInfo }
     *     
     */
    public void setComponentOf(ComponentOfInfo value) {
        this.componentOf = value;
    }

    /**
     * Gets the value of the component property.
     * 
     * @return
     *     possible object is
     *     {@link ComponentInfo }
     *     
     */
    public ComponentInfo getComponent() {
        return component;
    }

    /**
     * Sets the value of the component property.
     * 
     * @param value
     *     allowed object is
     *     {@link ComponentInfo }
     *     
     */
    public void setComponent(ComponentInfo value) {
        this.component = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="codeSystem" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ConfidentialityCode {

        @XmlAttribute(name = "code")
        protected String code;
        @XmlAttribute(name = "codeSystem")
        protected String codeSystem;

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        /**
         * Gets the value of the codeSystem property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodeSystem() {
            return codeSystem;
        }

        /**
         * Sets the value of the codeSystem property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodeSystem(String value) {
            this.codeSystem = value;
        }

        @Override
        public String toString() {
            return "ConfidentialityCode{" +
                    "code='" + code + '\'' +
                    ", codeSystem='" + codeSystem + '\'' +
                    '}';
        }
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="value" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class EffectiveTime {

        @XmlAttribute(name = "value", required = true)
        protected String value;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "EffectiveTime{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="extention" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Id {

        @XmlAttribute(name = "root")
        protected String root;
        @XmlAttribute(name = "extention")
        protected String extention;

        /**
         * Gets the value of the root property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoot() {
            return root;
        }

        /**
         * Sets the value of the root property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoot(String value) {
            this.root = value;
        }

        /**
         * Gets the value of the extention property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExtention() {
            return extention;
        }

        /**
         * Sets the value of the extention property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExtention(String value) {
            this.extention = value;
        }

        @Override
        public String toString() {
            return "Id{" +
                    "root='" + root + '\'' +
                    ", extention='" + extention + '\'' +
                    '}';
        }
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class LanguageCode {

        @XmlAttribute(name = "code")
        protected String code;

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

        @Override
        public String toString() {
            return "LanguageCode{" +
                    "code='" + code + '\'' +
                    '}';
        }
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="extention" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="root" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TypeId {

        @XmlAttribute(name = "extention")
        protected String extention;
        @XmlAttribute(name = "root")
        protected String root;

        /**
         * Gets the value of the extention property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getExtention() {
            return extention;
        }

        /**
         * Sets the value of the extention property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setExtention(String value) {
            this.extention = value;
        }

        /**
         * Gets the value of the root property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getRoot() {
            return root;
        }

        /**
         * Sets the value of the root property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setRoot(String value) {
            this.root = value;
        }

        @Override
        public String toString() {
            return "TypeId{" +
                    "extention='" + extention + '\'' +
                    ", root='" + root + '\'' +
                    '}';
        }
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="value" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class VersionNumber {

        @XmlAttribute(name = "value")
        protected String value;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "VersionNumber{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }


    @Override
    public String toString() {
        return "HL7Document{" +
                "typeId=" + typeId +
                ", id=" + id +
                ", code=" + code +
                ", title='" + title + '\'' +
                ", effectiveTime=" + effectiveTime +
                ", confidentialityCode=" + confidentialityCode +
                ", languageCode=" + languageCode +
                ", versionNumber=" + versionNumber +
                ", recordTarget=" + recordTarget +
                ", author=" + author +
                ", componentOf=" + componentOf +
                ", component=" + component +
                '}';
    }
}
