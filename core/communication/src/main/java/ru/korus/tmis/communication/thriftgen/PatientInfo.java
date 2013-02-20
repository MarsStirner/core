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

public class PatientInfo implements org.apache.thrift.TBase<PatientInfo, PatientInfo._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PatientInfo");

    private static final org.apache.thrift.protocol.TField LAST_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("lastName", org.apache.thrift.protocol.TType.STRING, (short) 1);
    private static final org.apache.thrift.protocol.TField FIRST_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("firstName", org.apache.thrift.protocol.TType.STRING, (short) 2);
    private static final org.apache.thrift.protocol.TField PATR_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("patrName", org.apache.thrift.protocol.TType.STRING, (short) 3);
    private static final org.apache.thrift.protocol.TField BIRTH_DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("birthDate", org.apache.thrift.protocol.TType.I64, (short) 4);
    private static final org.apache.thrift.protocol.TField SEX_FIELD_DESC = new org.apache.thrift.protocol.TField("sex", org.apache.thrift.protocol.TType.I32, (short) 5);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new PatientInfoStandardSchemeFactory());
        schemes.put(TupleScheme.class, new PatientInfoTupleSchemeFactory());
    }

    public String lastName; // optional
    public String firstName; // optional
    public String patrName; // optional
    public long birthDate; // optional
    public int sex; // optional

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        LAST_NAME((short) 1, "lastName"),
        FIRST_NAME((short) 2, "firstName"),
        PATR_NAME((short) 3, "patrName"),
        BIRTH_DATE((short) 4, "birthDate"),
        SEX((short) 5, "sex");

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
                case 1: // LAST_NAME
                    return LAST_NAME;
                case 2: // FIRST_NAME
                    return FIRST_NAME;
                case 3: // PATR_NAME
                    return PATR_NAME;
                case 4: // BIRTH_DATE
                    return BIRTH_DATE;
                case 5: // SEX
                    return SEX;
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
    private static final int __BIRTHDATE_ISSET_ID = 0;
    private static final int __SEX_ISSET_ID = 1;
    private byte __isset_bitfield = 0;
    private _Fields optionals[] = {_Fields.LAST_NAME, _Fields.FIRST_NAME, _Fields.PATR_NAME, _Fields.BIRTH_DATE, _Fields.SEX};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.LAST_NAME, new org.apache.thrift.meta_data.FieldMetaData("lastName", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.FIRST_NAME, new org.apache.thrift.meta_data.FieldMetaData("firstName", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.PATR_NAME, new org.apache.thrift.meta_data.FieldMetaData("patrName", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.BIRTH_DATE, new org.apache.thrift.meta_data.FieldMetaData("birthDate", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64, "timestamp")));
        tmpMap.put(_Fields.SEX, new org.apache.thrift.meta_data.FieldMetaData("sex", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PatientInfo.class, metaDataMap);
    }

    public PatientInfo() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public PatientInfo(PatientInfo other) {
        __isset_bitfield = other.__isset_bitfield;
        if (other.isSetLastName()) {
            this.lastName = other.lastName;
        }
        if (other.isSetFirstName()) {
            this.firstName = other.firstName;
        }
        if (other.isSetPatrName()) {
            this.patrName = other.patrName;
        }
        this.birthDate = other.birthDate;
        this.sex = other.sex;
    }

    public PatientInfo deepCopy() {
        return new PatientInfo(this);
    }

    @Override
    public void clear() {
        this.lastName = null;
        this.firstName = null;
        this.patrName = null;
        setBirthDateIsSet(false);
        this.birthDate = 0;
        setSexIsSet(false);
        this.sex = 0;
    }

    public String getLastName() {
        return this.lastName;
    }

    public PatientInfo setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void unsetLastName() {
        this.lastName = null;
    }

    /**
     * Returns true if field lastName is set (has been assigned a value) and false otherwise
     */
    public boolean isSetLastName() {
        return this.lastName != null;
    }

    public void setLastNameIsSet(boolean value) {
        if (!value) {
            this.lastName = null;
        }
    }

    public String getFirstName() {
        return this.firstName;
    }

    public PatientInfo setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void unsetFirstName() {
        this.firstName = null;
    }

    /**
     * Returns true if field firstName is set (has been assigned a value) and false otherwise
     */
    public boolean isSetFirstName() {
        return this.firstName != null;
    }

    public void setFirstNameIsSet(boolean value) {
        if (!value) {
            this.firstName = null;
        }
    }

    public String getPatrName() {
        return this.patrName;
    }

    public PatientInfo setPatrName(String patrName) {
        this.patrName = patrName;
        return this;
    }

    public void unsetPatrName() {
        this.patrName = null;
    }

    /**
     * Returns true if field patrName is set (has been assigned a value) and false otherwise
     */
    public boolean isSetPatrName() {
        return this.patrName != null;
    }

    public void setPatrNameIsSet(boolean value) {
        if (!value) {
            this.patrName = null;
        }
    }

    public long getBirthDate() {
        return this.birthDate;
    }

    public PatientInfo setBirthDate(long birthDate) {
        this.birthDate = birthDate;
        setBirthDateIsSet(true);
        return this;
    }

    public void unsetBirthDate() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BIRTHDATE_ISSET_ID);
    }

    /**
     * Returns true if field birthDate is set (has been assigned a value) and false otherwise
     */
    public boolean isSetBirthDate() {
        return EncodingUtils.testBit(__isset_bitfield, __BIRTHDATE_ISSET_ID);
    }

    public void setBirthDateIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BIRTHDATE_ISSET_ID, value);
    }

    public int getSex() {
        return this.sex;
    }

    public PatientInfo setSex(int sex) {
        this.sex = sex;
        setSexIsSet(true);
        return this;
    }

    public void unsetSex() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __SEX_ISSET_ID);
    }

    /**
     * Returns true if field sex is set (has been assigned a value) and false otherwise
     */
    public boolean isSetSex() {
        return EncodingUtils.testBit(__isset_bitfield, __SEX_ISSET_ID);
    }

    public void setSexIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __SEX_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case LAST_NAME:
                if (value == null) {
                    unsetLastName();
                } else {
                    setLastName((String) value);
                }
                break;

            case FIRST_NAME:
                if (value == null) {
                    unsetFirstName();
                } else {
                    setFirstName((String) value);
                }
                break;

            case PATR_NAME:
                if (value == null) {
                    unsetPatrName();
                } else {
                    setPatrName((String) value);
                }
                break;

            case BIRTH_DATE:
                if (value == null) {
                    unsetBirthDate();
                } else {
                    setBirthDate((Long) value);
                }
                break;

            case SEX:
                if (value == null) {
                    unsetSex();
                } else {
                    setSex((Integer) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case LAST_NAME:
                return getLastName();

            case FIRST_NAME:
                return getFirstName();

            case PATR_NAME:
                return getPatrName();

            case BIRTH_DATE:
                return Long.valueOf(getBirthDate());

            case SEX:
                return Integer.valueOf(getSex());

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
            case LAST_NAME:
                return isSetLastName();
            case FIRST_NAME:
                return isSetFirstName();
            case PATR_NAME:
                return isSetPatrName();
            case BIRTH_DATE:
                return isSetBirthDate();
            case SEX:
                return isSetSex();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof PatientInfo)
            return this.equals((PatientInfo) that);
        return false;
    }

    public boolean equals(PatientInfo that) {
        if (that == null)
            return false;

        boolean this_present_lastName = true && this.isSetLastName();
        boolean that_present_lastName = true && that.isSetLastName();
        if (this_present_lastName || that_present_lastName) {
            if (!(this_present_lastName && that_present_lastName))
                return false;
            if (!this.lastName.equals(that.lastName))
                return false;
        }

        boolean this_present_firstName = true && this.isSetFirstName();
        boolean that_present_firstName = true && that.isSetFirstName();
        if (this_present_firstName || that_present_firstName) {
            if (!(this_present_firstName && that_present_firstName))
                return false;
            if (!this.firstName.equals(that.firstName))
                return false;
        }

        boolean this_present_patrName = true && this.isSetPatrName();
        boolean that_present_patrName = true && that.isSetPatrName();
        if (this_present_patrName || that_present_patrName) {
            if (!(this_present_patrName && that_present_patrName))
                return false;
            if (!this.patrName.equals(that.patrName))
                return false;
        }

        boolean this_present_birthDate = true && this.isSetBirthDate();
        boolean that_present_birthDate = true && that.isSetBirthDate();
        if (this_present_birthDate || that_present_birthDate) {
            if (!(this_present_birthDate && that_present_birthDate))
                return false;
            if (this.birthDate != that.birthDate)
                return false;
        }

        boolean this_present_sex = true && this.isSetSex();
        boolean that_present_sex = true && that.isSetSex();
        if (this_present_sex || that_present_sex) {
            if (!(this_present_sex && that_present_sex))
                return false;
            if (this.sex != that.sex)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(PatientInfo other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        PatientInfo typedOther = (PatientInfo) other;

        lastComparison = Boolean.valueOf(isSetLastName()).compareTo(typedOther.isSetLastName());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetLastName()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastName, typedOther.lastName);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetFirstName()).compareTo(typedOther.isSetFirstName());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetFirstName()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.firstName, typedOther.firstName);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetPatrName()).compareTo(typedOther.isSetPatrName());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPatrName()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.patrName, typedOther.patrName);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetBirthDate()).compareTo(typedOther.isSetBirthDate());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetBirthDate()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.birthDate, typedOther.birthDate);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetSex()).compareTo(typedOther.isSetSex());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetSex()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.sex, typedOther.sex);
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
        StringBuilder sb = new StringBuilder("PatientInfo(");
        boolean first = true;

        if (isSetLastName()) {
            sb.append("lastName:");
            if (this.lastName == null) {
                sb.append("null");
            } else {
                sb.append(this.lastName);
            }
            first = false;
        }
        if (isSetFirstName()) {
            if (!first) sb.append(", ");
            sb.append("firstName:");
            if (this.firstName == null) {
                sb.append("null");
            } else {
                sb.append(this.firstName);
            }
            first = false;
        }
        if (isSetPatrName()) {
            if (!first) sb.append(", ");
            sb.append("patrName:");
            if (this.patrName == null) {
                sb.append("null");
            } else {
                sb.append(this.patrName);
            }
            first = false;
        }
        if (isSetBirthDate()) {
            if (!first) sb.append(", ");
            sb.append("birthDate:");
            sb.append(this.birthDate);
            first = false;
        }
        if (isSetSex()) {
            if (!first) sb.append(", ");
            sb.append("sex:");
            sb.append(this.sex);
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
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class PatientInfoStandardSchemeFactory implements SchemeFactory {
        public PatientInfoStandardScheme getScheme() {
            return new PatientInfoStandardScheme();
        }
    }

    private static class PatientInfoStandardScheme extends StandardScheme<PatientInfo> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, PatientInfo struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // LAST_NAME
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.lastName = iprot.readString();
                            struct.setLastNameIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // FIRST_NAME
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.firstName = iprot.readString();
                            struct.setFirstNameIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // PATR_NAME
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.patrName = iprot.readString();
                            struct.setPatrNameIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // BIRTH_DATE
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.birthDate = iprot.readI64();
                            struct.setBirthDateIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // SEX
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.sex = iprot.readI32();
                            struct.setSexIsSet(true);
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

        public void write(org.apache.thrift.protocol.TProtocol oprot, PatientInfo struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.lastName != null) {
                if (struct.isSetLastName()) {
                    oprot.writeFieldBegin(LAST_NAME_FIELD_DESC);
                    oprot.writeString(struct.lastName);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.firstName != null) {
                if (struct.isSetFirstName()) {
                    oprot.writeFieldBegin(FIRST_NAME_FIELD_DESC);
                    oprot.writeString(struct.firstName);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.patrName != null) {
                if (struct.isSetPatrName()) {
                    oprot.writeFieldBegin(PATR_NAME_FIELD_DESC);
                    oprot.writeString(struct.patrName);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.isSetBirthDate()) {
                oprot.writeFieldBegin(BIRTH_DATE_FIELD_DESC);
                oprot.writeI64(struct.birthDate);
                oprot.writeFieldEnd();
            }
            if (struct.isSetSex()) {
                oprot.writeFieldBegin(SEX_FIELD_DESC);
                oprot.writeI32(struct.sex);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class PatientInfoTupleSchemeFactory implements SchemeFactory {
        public PatientInfoTupleScheme getScheme() {
            return new PatientInfoTupleScheme();
        }
    }

    private static class PatientInfoTupleScheme extends TupleScheme<PatientInfo> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, PatientInfo struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetLastName()) {
                optionals.set(0);
            }
            if (struct.isSetFirstName()) {
                optionals.set(1);
            }
            if (struct.isSetPatrName()) {
                optionals.set(2);
            }
            if (struct.isSetBirthDate()) {
                optionals.set(3);
            }
            if (struct.isSetSex()) {
                optionals.set(4);
            }
            oprot.writeBitSet(optionals, 5);
            if (struct.isSetLastName()) {
                oprot.writeString(struct.lastName);
            }
            if (struct.isSetFirstName()) {
                oprot.writeString(struct.firstName);
            }
            if (struct.isSetPatrName()) {
                oprot.writeString(struct.patrName);
            }
            if (struct.isSetBirthDate()) {
                oprot.writeI64(struct.birthDate);
            }
            if (struct.isSetSex()) {
                oprot.writeI32(struct.sex);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, PatientInfo struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(5);
            if (incoming.get(0)) {
                struct.lastName = iprot.readString();
                struct.setLastNameIsSet(true);
            }
            if (incoming.get(1)) {
                struct.firstName = iprot.readString();
                struct.setFirstNameIsSet(true);
            }
            if (incoming.get(2)) {
                struct.patrName = iprot.readString();
                struct.setPatrNameIsSet(true);
            }
            if (incoming.get(3)) {
                struct.birthDate = iprot.readI64();
                struct.setBirthDateIsSet(true);
            }
            if (incoming.get(4)) {
                struct.sex = iprot.readI32();
                struct.setSexIsSet(true);
            }
        }
    }

}

