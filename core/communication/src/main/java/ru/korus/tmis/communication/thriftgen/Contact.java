/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.communication.thriftgen;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Contact implements org.apache.thrift.TBase<Contact, Contact._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Contact");

    private static final org.apache.thrift.protocol.TField TYPE_FIELD_DESC = new org.apache.thrift.protocol.TField("type", org.apache.thrift.protocol.TType.STRING, (short) 1);
    private static final org.apache.thrift.protocol.TField CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("code", org.apache.thrift.protocol.TType.STRING, (short) 2);
    private static final org.apache.thrift.protocol.TField CONTACT_FIELD_DESC = new org.apache.thrift.protocol.TField("contact", org.apache.thrift.protocol.TType.STRING, (short) 3);
    private static final org.apache.thrift.protocol.TField NOTE_FIELD_DESC = new org.apache.thrift.protocol.TField("note", org.apache.thrift.protocol.TType.STRING, (short) 4);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new ContactStandardSchemeFactory());
        schemes.put(TupleScheme.class, new ContactTupleSchemeFactory());
    }

    public String type; // optional
    public String code; // optional
    public String contact; // optional
    public String note; // optional

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        TYPE((short) 1, "type"),
        CODE((short) 2, "code"),
        CONTACT((short) 3, "contact"),
        NOTE((short) 4, "note");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch (fieldId) {
                case 1: // TYPE
                    return TYPE;
                case 2: // CODE
                    return CODE;
                case 3: // CONTACT
                    return CONTACT;
                case 4: // NOTE
                    return NOTE;
                default:
                    return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
         * Find the _Fields constant that matches name, or null if its not found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        private final short _thriftId;
        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    // isset id assignments
    private _Fields optionals[] = {_Fields.TYPE, _Fields.CODE, _Fields.CONTACT, _Fields.NOTE};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.TYPE, new org.apache.thrift.meta_data.FieldMetaData("type", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.CODE, new org.apache.thrift.meta_data.FieldMetaData("code", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.CONTACT, new org.apache.thrift.meta_data.FieldMetaData("contact", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.NOTE, new org.apache.thrift.meta_data.FieldMetaData("note", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Contact.class, metaDataMap);
    }

    public Contact() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public Contact(Contact other) {
        if (other.isSetType()) {
            this.type = other.type;
        }
        if (other.isSetCode()) {
            this.code = other.code;
        }
        if (other.isSetContact()) {
            this.contact = other.contact;
        }
        if (other.isSetNote()) {
            this.note = other.note;
        }
    }

    public Contact deepCopy() {
        return new Contact(this);
    }

    @Override
    public void clear() {
        this.type = null;
        this.code = null;
        this.contact = null;
        this.note = null;
    }

    public String getType() {
        return this.type;
    }

    public Contact setType(String type) {
        this.type = type;
        return this;
    }

    public void unsetType() {
        this.type = null;
    }

    /**
     * Returns true if field type is set (has been assigned a value) and false otherwise
     */
    public boolean isSetType() {
        return this.type != null;
    }

    public void setTypeIsSet(boolean value) {
        if (!value) {
            this.type = null;
        }
    }

    public String getCode() {
        return this.code;
    }

    public Contact setCode(String code) {
        this.code = code;
        return this;
    }

    public void unsetCode() {
        this.code = null;
    }

    /**
     * Returns true if field code is set (has been assigned a value) and false otherwise
     */
    public boolean isSetCode() {
        return this.code != null;
    }

    public void setCodeIsSet(boolean value) {
        if (!value) {
            this.code = null;
        }
    }

    public String getContact() {
        return this.contact;
    }

    public Contact setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public void unsetContact() {
        this.contact = null;
    }

    /**
     * Returns true if field contact is set (has been assigned a value) and false otherwise
     */
    public boolean isSetContact() {
        return this.contact != null;
    }

    public void setContactIsSet(boolean value) {
        if (!value) {
            this.contact = null;
        }
    }

    public String getNote() {
        return this.note;
    }

    public Contact setNote(String note) {
        this.note = note;
        return this;
    }

    public void unsetNote() {
        this.note = null;
    }

    /**
     * Returns true if field note is set (has been assigned a value) and false otherwise
     */
    public boolean isSetNote() {
        return this.note != null;
    }

    public void setNoteIsSet(boolean value) {
        if (!value) {
            this.note = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case TYPE:
                if (value == null) {
                    unsetType();
                } else {
                    setType((String) value);
                }
                break;

            case CODE:
                if (value == null) {
                    unsetCode();
                } else {
                    setCode((String) value);
                }
                break;

            case CONTACT:
                if (value == null) {
                    unsetContact();
                } else {
                    setContact((String) value);
                }
                break;

            case NOTE:
                if (value == null) {
                    unsetNote();
                } else {
                    setNote((String) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case TYPE:
                return getType();

            case CODE:
                return getCode();

            case CONTACT:
                return getContact();

            case NOTE:
                return getNote();

        }
        throw new IllegalStateException();
    }

    /**
     * Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise
     */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case TYPE:
                return isSetType();
            case CODE:
                return isSetCode();
            case CONTACT:
                return isSetContact();
            case NOTE:
                return isSetNote();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof Contact)
            return this.equals((Contact) that);
        return false;
    }

    public boolean equals(Contact that) {
        if (that == null)
            return false;

        boolean this_present_type = true && this.isSetType();
        boolean that_present_type = true && that.isSetType();
        if (this_present_type || that_present_type) {
            if (!(this_present_type && that_present_type))
                return false;
            if (!this.type.equals(that.type))
                return false;
        }

        boolean this_present_code = true && this.isSetCode();
        boolean that_present_code = true && that.isSetCode();
        if (this_present_code || that_present_code) {
            if (!(this_present_code && that_present_code))
                return false;
            if (!this.code.equals(that.code))
                return false;
        }

        boolean this_present_contact = true && this.isSetContact();
        boolean that_present_contact = true && that.isSetContact();
        if (this_present_contact || that_present_contact) {
            if (!(this_present_contact && that_present_contact))
                return false;
            if (!this.contact.equals(that.contact))
                return false;
        }

        boolean this_present_note = true && this.isSetNote();
        boolean that_present_note = true && that.isSetNote();
        if (this_present_note || that_present_note) {
            if (!(this_present_note && that_present_note))
                return false;
            if (!this.note.equals(that.note))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(Contact other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        Contact typedOther = (Contact) other;

        lastComparison = Boolean.valueOf(isSetType()).compareTo(typedOther.isSetType());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetType()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.type, typedOther.type);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCode()).compareTo(typedOther.isSetCode());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCode()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.code, typedOther.code);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetContact()).compareTo(typedOther.isSetContact());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetContact()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contact, typedOther.contact);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetNote()).compareTo(typedOther.isSetNote());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetNote()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.note, typedOther.note);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Contact(");
        boolean first = true;

        if (isSetType()) {
            sb.append("type:");
            if (this.type == null) {
                sb.append("null");
            } else {
                sb.append(this.type);
            }
            first = false;
        }
        if (isSetCode()) {
            if (!first) sb.append(", ");
            sb.append("code:");
            if (this.code == null) {
                sb.append("null");
            } else {
                sb.append(this.code);
            }
            first = false;
        }
        if (isSetContact()) {
            if (!first) sb.append(", ");
            sb.append("contact:");
            if (this.contact == null) {
                sb.append("null");
            } else {
                sb.append(this.contact);
            }
            first = false;
        }
        if (isSetNote()) {
            if (!first) sb.append(", ");
            sb.append("note:");
            if (this.note == null) {
                sb.append("null");
            } else {
                sb.append(this.note);
            }
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class ContactStandardSchemeFactory implements SchemeFactory {
        public ContactStandardScheme getScheme() {
            return new ContactStandardScheme();
        }
    }

    private static class ContactStandardScheme extends StandardScheme<Contact> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, Contact struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // TYPE
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.type = iprot.readString();
                            struct.setTypeIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // CODE
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.code = iprot.readString();
                            struct.setCodeIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // CONTACT
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.contact = iprot.readString();
                            struct.setContactIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // NOTE
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.note = iprot.readString();
                            struct.setNoteIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, Contact struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.type != null) {
                if (struct.isSetType()) {
                    oprot.writeFieldBegin(TYPE_FIELD_DESC);
                    oprot.writeString(struct.type);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.code != null) {
                if (struct.isSetCode()) {
                    oprot.writeFieldBegin(CODE_FIELD_DESC);
                    oprot.writeString(struct.code);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.contact != null) {
                if (struct.isSetContact()) {
                    oprot.writeFieldBegin(CONTACT_FIELD_DESC);
                    oprot.writeString(struct.contact);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.note != null) {
                if (struct.isSetNote()) {
                    oprot.writeFieldBegin(NOTE_FIELD_DESC);
                    oprot.writeString(struct.note);
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class ContactTupleSchemeFactory implements SchemeFactory {
        public ContactTupleScheme getScheme() {
            return new ContactTupleScheme();
        }
    }

    private static class ContactTupleScheme extends TupleScheme<Contact> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, Contact struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetType()) {
                optionals.set(0);
            }
            if (struct.isSetCode()) {
                optionals.set(1);
            }
            if (struct.isSetContact()) {
                optionals.set(2);
            }
            if (struct.isSetNote()) {
                optionals.set(3);
            }
            oprot.writeBitSet(optionals, 4);
            if (struct.isSetType()) {
                oprot.writeString(struct.type);
            }
            if (struct.isSetCode()) {
                oprot.writeString(struct.code);
            }
            if (struct.isSetContact()) {
                oprot.writeString(struct.contact);
            }
            if (struct.isSetNote()) {
                oprot.writeString(struct.note);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, Contact struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(4);
            if (incoming.get(0)) {
                struct.type = iprot.readString();
                struct.setTypeIsSet(true);
            }
            if (incoming.get(1)) {
                struct.code = iprot.readString();
                struct.setCodeIsSet(true);
            }
            if (incoming.get(2)) {
                struct.contact = iprot.readString();
                struct.setContactIsSet(true);
            }
            if (incoming.get(3)) {
                struct.note = iprot.readString();
                struct.setNoteIsSet(true);
            }
        }
    }

}

