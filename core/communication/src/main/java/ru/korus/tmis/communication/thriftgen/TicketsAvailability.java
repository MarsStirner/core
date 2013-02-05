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

public class TicketsAvailability implements org.apache.thrift.TBase<TicketsAvailability, TicketsAvailability._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TicketsAvailability");

    private static final org.apache.thrift.protocol.TField TOTAL_FIELD_DESC = new org.apache.thrift.protocol.TField("total", org.apache.thrift.protocol.TType.I32, (short) 1);
    private static final org.apache.thrift.protocol.TField FREE_FIELD_DESC = new org.apache.thrift.protocol.TField("free", org.apache.thrift.protocol.TType.I32, (short) 2);
    private static final org.apache.thrift.protocol.TField AVAILABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("available", org.apache.thrift.protocol.TType.I32, (short) 3);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new TicketsAvailabilityStandardSchemeFactory());
        schemes.put(TupleScheme.class, new TicketsAvailabilityTupleSchemeFactory());
    }

    public int total; // required
    public int free; // required
    public int available; // required

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        TOTAL((short) 1, "total"),
        FREE((short) 2, "free"),
        AVAILABLE((short) 3, "available");

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
                case 1: // TOTAL
                    return TOTAL;
                case 2: // FREE
                    return FREE;
                case 3: // AVAILABLE
                    return AVAILABLE;
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
    private static final int __TOTAL_ISSET_ID = 0;
    private static final int __FREE_ISSET_ID = 1;
    private static final int __AVAILABLE_ISSET_ID = 2;
    private byte __isset_bitfield = 0;
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.TOTAL, new org.apache.thrift.meta_data.FieldMetaData("total", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.FREE, new org.apache.thrift.meta_data.FieldMetaData("free", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.AVAILABLE, new org.apache.thrift.meta_data.FieldMetaData("available", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TicketsAvailability.class, metaDataMap);
    }

    public TicketsAvailability() {
    }

    public TicketsAvailability(
            int total,
            int free,
            int available) {
        this();
        this.total = total;
        setTotalIsSet(true);
        this.free = free;
        setFreeIsSet(true);
        this.available = available;
        setAvailableIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public TicketsAvailability(TicketsAvailability other) {
        __isset_bitfield = other.__isset_bitfield;
        this.total = other.total;
        this.free = other.free;
        this.available = other.available;
    }

    public TicketsAvailability deepCopy() {
        return new TicketsAvailability(this);
    }

    @Override
    public void clear() {
        setTotalIsSet(false);
        this.total = 0;
        setFreeIsSet(false);
        this.free = 0;
        setAvailableIsSet(false);
        this.available = 0;
    }

    public int getTotal() {
        return this.total;
    }

    public TicketsAvailability setTotal(int total) {
        this.total = total;
        setTotalIsSet(true);
        return this;
    }

    public void unsetTotal() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TOTAL_ISSET_ID);
    }

    /**
     * Returns true if field total is set (has been assigned a value) and false otherwise
     */
    public boolean isSetTotal() {
        return EncodingUtils.testBit(__isset_bitfield, __TOTAL_ISSET_ID);
    }

    public void setTotalIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TOTAL_ISSET_ID, value);
    }

    public int getFree() {
        return this.free;
    }

    public TicketsAvailability setFree(int free) {
        this.free = free;
        setFreeIsSet(true);
        return this;
    }

    public void unsetFree() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FREE_ISSET_ID);
    }

    /**
     * Returns true if field free is set (has been assigned a value) and false otherwise
     */
    public boolean isSetFree() {
        return EncodingUtils.testBit(__isset_bitfield, __FREE_ISSET_ID);
    }

    public void setFreeIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FREE_ISSET_ID, value);
    }

    public int getAvailable() {
        return this.available;
    }

    public TicketsAvailability setAvailable(int available) {
        this.available = available;
        setAvailableIsSet(true);
        return this;
    }

    public void unsetAvailable() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __AVAILABLE_ISSET_ID);
    }

    /**
     * Returns true if field available is set (has been assigned a value) and false otherwise
     */
    public boolean isSetAvailable() {
        return EncodingUtils.testBit(__isset_bitfield, __AVAILABLE_ISSET_ID);
    }

    public void setAvailableIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __AVAILABLE_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case TOTAL:
                if (value == null) {
                    unsetTotal();
                } else {
                    setTotal((Integer) value);
                }
                break;

            case FREE:
                if (value == null) {
                    unsetFree();
                } else {
                    setFree((Integer) value);
                }
                break;

            case AVAILABLE:
                if (value == null) {
                    unsetAvailable();
                } else {
                    setAvailable((Integer) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case TOTAL:
                return Integer.valueOf(getTotal());

            case FREE:
                return Integer.valueOf(getFree());

            case AVAILABLE:
                return Integer.valueOf(getAvailable());

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
            case TOTAL:
                return isSetTotal();
            case FREE:
                return isSetFree();
            case AVAILABLE:
                return isSetAvailable();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof TicketsAvailability)
            return this.equals((TicketsAvailability) that);
        return false;
    }

    public boolean equals(TicketsAvailability that) {
        if (that == null)
            return false;

        boolean this_present_total = true;
        boolean that_present_total = true;
        if (this_present_total || that_present_total) {
            if (!(this_present_total && that_present_total))
                return false;
            if (this.total != that.total)
                return false;
        }

        boolean this_present_free = true;
        boolean that_present_free = true;
        if (this_present_free || that_present_free) {
            if (!(this_present_free && that_present_free))
                return false;
            if (this.free != that.free)
                return false;
        }

        boolean this_present_available = true;
        boolean that_present_available = true;
        if (this_present_available || that_present_available) {
            if (!(this_present_available && that_present_available))
                return false;
            if (this.available != that.available)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(TicketsAvailability other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        TicketsAvailability typedOther = (TicketsAvailability) other;

        lastComparison = Boolean.valueOf(isSetTotal()).compareTo(typedOther.isSetTotal());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetTotal()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.total, typedOther.total);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetFree()).compareTo(typedOther.isSetFree());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetFree()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.free, typedOther.free);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetAvailable()).compareTo(typedOther.isSetAvailable());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetAvailable()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.available, typedOther.available);
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
        StringBuilder sb = new StringBuilder("TicketsAvailability(");
        boolean first = true;

        sb.append("total:");
        sb.append(this.total);
        first = false;
        if (!first) sb.append(", ");
        sb.append("free:");
        sb.append(this.free);
        first = false;
        if (!first) sb.append(", ");
        sb.append("available:");
        sb.append(this.available);
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // alas, we cannot check 'total' because it's a primitive and you chose the non-beans generator.
        // alas, we cannot check 'free' because it's a primitive and you chose the non-beans generator.
        // alas, we cannot check 'available' because it's a primitive and you chose the non-beans generator.
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

    private static class TicketsAvailabilityStandardSchemeFactory implements SchemeFactory {
        public TicketsAvailabilityStandardScheme getScheme() {
            return new TicketsAvailabilityStandardScheme();
        }
    }

    private static class TicketsAvailabilityStandardScheme extends StandardScheme<TicketsAvailability> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, TicketsAvailability struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // TOTAL
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.total = iprot.readI32();
                            struct.setTotalIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // FREE
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.free = iprot.readI32();
                            struct.setFreeIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // AVAILABLE
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.available = iprot.readI32();
                            struct.setAvailableIsSet(true);
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
            if (!struct.isSetTotal()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'total' was not found in serialized data! Struct: " + toString());
            }
            if (!struct.isSetFree()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'free' was not found in serialized data! Struct: " + toString());
            }
            if (!struct.isSetAvailable()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'available' was not found in serialized data! Struct: " + toString());
            }
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, TicketsAvailability struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(TOTAL_FIELD_DESC);
            oprot.writeI32(struct.total);
            oprot.writeFieldEnd();
            oprot.writeFieldBegin(FREE_FIELD_DESC);
            oprot.writeI32(struct.free);
            oprot.writeFieldEnd();
            oprot.writeFieldBegin(AVAILABLE_FIELD_DESC);
            oprot.writeI32(struct.available);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class TicketsAvailabilityTupleSchemeFactory implements SchemeFactory {
        public TicketsAvailabilityTupleScheme getScheme() {
            return new TicketsAvailabilityTupleScheme();
        }
    }

    private static class TicketsAvailabilityTupleScheme extends TupleScheme<TicketsAvailability> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, TicketsAvailability struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            oprot.writeI32(struct.total);
            oprot.writeI32(struct.free);
            oprot.writeI32(struct.available);
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, TicketsAvailability struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            struct.total = iprot.readI32();
            struct.setTotalIsSet(true);
            struct.free = iprot.readI32();
            struct.setFreeIsSet(true);
            struct.available = iprot.readI32();
            struct.setAvailableIsSet(true);
        }
    }

}
