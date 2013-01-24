/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.communication.thriftgen;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import java.util.*;

public class ExtendedTicketsAvailability implements org.apache.thrift.TBase<ExtendedTicketsAvailability, ExtendedTicketsAvailability._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ExtendedTicketsAvailability");

    private static final org.apache.thrift.protocol.TField PERSON_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("personId", org.apache.thrift.protocol.TType.I32, (short) 1);
    private static final org.apache.thrift.protocol.TField DATE_FIELD_DESC = new org.apache.thrift.protocol.TField("date", org.apache.thrift.protocol.TType.I64, (short) 2);
    private static final org.apache.thrift.protocol.TField TICKETS_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("ticketsInfo", org.apache.thrift.protocol.TType.STRUCT, (short) 3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new ExtendedTicketsAvailabilityStandardSchemeFactory());
        schemes.put(TupleScheme.class, new ExtendedTicketsAvailabilityTupleSchemeFactory());
    }

    public int personId; // required
    public long date; // optional
    public TicketsAvailability ticketsInfo; // required

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        PERSON_ID((short) 1, "personId"),
        DATE((short) 2, "date"),
        TICKETS_INFO((short) 3, "ticketsInfo");

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
                case 1: // PERSON_ID
                    return PERSON_ID;
                case 2: // DATE
                    return DATE;
                case 3: // TICKETS_INFO
                    return TICKETS_INFO;
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
    private static final int __PERSONID_ISSET_ID = 0;
    private static final int __DATE_ISSET_ID = 1;
    private byte __isset_bitfield = 0;
    private _Fields optionals[] = {_Fields.DATE};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.PERSON_ID, new org.apache.thrift.meta_data.FieldMetaData("personId", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.DATE, new org.apache.thrift.meta_data.FieldMetaData("date", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64, "timestamp")));
        tmpMap.put(_Fields.TICKETS_INFO, new org.apache.thrift.meta_data.FieldMetaData("ticketsInfo", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TicketsAvailability.class)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ExtendedTicketsAvailability.class, metaDataMap);
    }

    public ExtendedTicketsAvailability() {
    }

    public ExtendedTicketsAvailability(
            int personId,
            TicketsAvailability ticketsInfo) {
        this();
        this.personId = personId;
        setPersonIdIsSet(true);
        this.ticketsInfo = ticketsInfo;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public ExtendedTicketsAvailability(ExtendedTicketsAvailability other) {
        __isset_bitfield = other.__isset_bitfield;
        this.personId = other.personId;
        this.date = other.date;
        if (other.isSetTicketsInfo()) {
            this.ticketsInfo = new TicketsAvailability(other.ticketsInfo);
        }
    }

    public ExtendedTicketsAvailability deepCopy() {
        return new ExtendedTicketsAvailability(this);
    }

    @Override
    public void clear() {
        setPersonIdIsSet(false);
        this.personId = 0;
        setDateIsSet(false);
        this.date = 0;
        this.ticketsInfo = null;
    }

    public int getPersonId() {
        return this.personId;
    }

    public ExtendedTicketsAvailability setPersonId(int personId) {
        this.personId = personId;
        setPersonIdIsSet(true);
        return this;
    }

    public void unsetPersonId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PERSONID_ISSET_ID);
    }

    /**
     * Returns true if field personId is set (has been assigned a value) and false otherwise
     */
    public boolean isSetPersonId() {
        return EncodingUtils.testBit(__isset_bitfield, __PERSONID_ISSET_ID);
    }

    public void setPersonIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PERSONID_ISSET_ID, value);
    }

    public long getDate() {
        return this.date;
    }

    public ExtendedTicketsAvailability setDate(long date) {
        this.date = date;
        setDateIsSet(true);
        return this;
    }

    public void unsetDate() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATE_ISSET_ID);
    }

    /**
     * Returns true if field date is set (has been assigned a value) and false otherwise
     */
    public boolean isSetDate() {
        return EncodingUtils.testBit(__isset_bitfield, __DATE_ISSET_ID);
    }

    public void setDateIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATE_ISSET_ID, value);
    }

    public TicketsAvailability getTicketsInfo() {
        return this.ticketsInfo;
    }

    public ExtendedTicketsAvailability setTicketsInfo(TicketsAvailability ticketsInfo) {
        this.ticketsInfo = ticketsInfo;
        return this;
    }

    public void unsetTicketsInfo() {
        this.ticketsInfo = null;
    }

    /**
     * Returns true if field ticketsInfo is set (has been assigned a value) and false otherwise
     */
    public boolean isSetTicketsInfo() {
        return this.ticketsInfo != null;
    }

    public void setTicketsInfoIsSet(boolean value) {
        if (!value) {
            this.ticketsInfo = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case PERSON_ID:
                if (value == null) {
                    unsetPersonId();
                } else {
                    setPersonId((Integer) value);
                }
                break;

            case DATE:
                if (value == null) {
                    unsetDate();
                } else {
                    setDate((Long) value);
                }
                break;

            case TICKETS_INFO:
                if (value == null) {
                    unsetTicketsInfo();
                } else {
                    setTicketsInfo((TicketsAvailability) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case PERSON_ID:
                return Integer.valueOf(getPersonId());

            case DATE:
                return Long.valueOf(getDate());

            case TICKETS_INFO:
                return getTicketsInfo();

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
            case PERSON_ID:
                return isSetPersonId();
            case DATE:
                return isSetDate();
            case TICKETS_INFO:
                return isSetTicketsInfo();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof ExtendedTicketsAvailability)
            return this.equals((ExtendedTicketsAvailability) that);
        return false;
    }

    public boolean equals(ExtendedTicketsAvailability that) {
        if (that == null)
            return false;

        boolean this_present_personId = true;
        boolean that_present_personId = true;
        if (this_present_personId || that_present_personId) {
            if (!(this_present_personId && that_present_personId))
                return false;
            if (this.personId != that.personId)
                return false;
        }

        boolean this_present_date = true && this.isSetDate();
        boolean that_present_date = true && that.isSetDate();
        if (this_present_date || that_present_date) {
            if (!(this_present_date && that_present_date))
                return false;
            if (this.date != that.date)
                return false;
        }

        boolean this_present_ticketsInfo = true && this.isSetTicketsInfo();
        boolean that_present_ticketsInfo = true && that.isSetTicketsInfo();
        if (this_present_ticketsInfo || that_present_ticketsInfo) {
            if (!(this_present_ticketsInfo && that_present_ticketsInfo))
                return false;
            if (!this.ticketsInfo.equals(that.ticketsInfo))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(ExtendedTicketsAvailability other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        ExtendedTicketsAvailability typedOther = (ExtendedTicketsAvailability) other;

        lastComparison = Boolean.valueOf(isSetPersonId()).compareTo(typedOther.isSetPersonId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPersonId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.personId, typedOther.personId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetDate()).compareTo(typedOther.isSetDate());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetDate()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.date, typedOther.date);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetTicketsInfo()).compareTo(typedOther.isSetTicketsInfo());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetTicketsInfo()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ticketsInfo, typedOther.ticketsInfo);
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
        StringBuilder sb = new StringBuilder("ExtendedTicketsAvailability(");
        boolean first = true;

        sb.append("personId:");
        sb.append(this.personId);
        first = false;
        if (isSetDate()) {
            if (!first) sb.append(", ");
            sb.append("date:");
            sb.append(this.date);
            first = false;
        }
        if (!first) sb.append(", ");
        sb.append("ticketsInfo:");
        if (this.ticketsInfo == null) {
            sb.append("null");
        } else {
            sb.append(this.ticketsInfo);
        }
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // alas, we cannot check 'personId' because it's a primitive and you chose the non-beans generator.
        if (ticketsInfo == null) {
            throw new org.apache.thrift.protocol.TProtocolException("Required field 'ticketsInfo' was not present! Struct: " + toString());
        }
        // check for sub-struct validity
        if (ticketsInfo != null) {
            ticketsInfo.validate();
        }
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

    private static class ExtendedTicketsAvailabilityStandardSchemeFactory implements SchemeFactory {
        public ExtendedTicketsAvailabilityStandardScheme getScheme() {
            return new ExtendedTicketsAvailabilityStandardScheme();
        }
    }

    private static class ExtendedTicketsAvailabilityStandardScheme extends StandardScheme<ExtendedTicketsAvailability> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, ExtendedTicketsAvailability struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // PERSON_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.personId = iprot.readI32();
                            struct.setPersonIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // DATE
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.date = iprot.readI64();
                            struct.setDateIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // TICKETS_INFO
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.ticketsInfo = new TicketsAvailability();
                            struct.ticketsInfo.read(iprot);
                            struct.setTicketsInfoIsSet(true);
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
            if (!struct.isSetPersonId()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'personId' was not found in serialized data! Struct: " + toString());
            }
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, ExtendedTicketsAvailability struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(PERSON_ID_FIELD_DESC);
            oprot.writeI32(struct.personId);
            oprot.writeFieldEnd();
            if (struct.isSetDate()) {
                oprot.writeFieldBegin(DATE_FIELD_DESC);
                oprot.writeI64(struct.date);
                oprot.writeFieldEnd();
            }
            if (struct.ticketsInfo != null) {
                oprot.writeFieldBegin(TICKETS_INFO_FIELD_DESC);
                struct.ticketsInfo.write(oprot);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class ExtendedTicketsAvailabilityTupleSchemeFactory implements SchemeFactory {
        public ExtendedTicketsAvailabilityTupleScheme getScheme() {
            return new ExtendedTicketsAvailabilityTupleScheme();
        }
    }

    private static class ExtendedTicketsAvailabilityTupleScheme extends TupleScheme<ExtendedTicketsAvailability> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, ExtendedTicketsAvailability struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            oprot.writeI32(struct.personId);
            struct.ticketsInfo.write(oprot);
            BitSet optionals = new BitSet();
            if (struct.isSetDate()) {
                optionals.set(0);
            }
            oprot.writeBitSet(optionals, 1);
            if (struct.isSetDate()) {
                oprot.writeI64(struct.date);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, ExtendedTicketsAvailability struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            struct.personId = iprot.readI32();
            struct.setPersonIdIsSet(true);
            struct.ticketsInfo = new TicketsAvailability();
            struct.ticketsInfo.read(iprot);
            struct.setTicketsInfoIsSet(true);
            BitSet incoming = iprot.readBitSet(1);
            if (incoming.get(0)) {
                struct.date = iprot.readI64();
                struct.setDateIsSet(true);
            }
        }
    }

}

