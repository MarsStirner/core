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

public class Ticket implements org.apache.thrift.TBase<Ticket, Ticket._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Ticket");

    private static final org.apache.thrift.protocol.TField TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("time", org.apache.thrift.protocol.TType.I64, (short) 1);
    private static final org.apache.thrift.protocol.TField FREE_FIELD_DESC = new org.apache.thrift.protocol.TField("free", org.apache.thrift.protocol.TType.I32, (short) 2);
    private static final org.apache.thrift.protocol.TField AVAILABLE_FIELD_DESC = new org.apache.thrift.protocol.TField("available", org.apache.thrift.protocol.TType.I32, (short) 3);
    private static final org.apache.thrift.protocol.TField PATIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("patientId", org.apache.thrift.protocol.TType.I32, (short) 4);
    private static final org.apache.thrift.protocol.TField PATIENT_INFO_FIELD_DESC = new org.apache.thrift.protocol.TField("patientInfo", org.apache.thrift.protocol.TType.STRING, (short) 5);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new TicketStandardSchemeFactory());
        schemes.put(TupleScheme.class, new TicketTupleSchemeFactory());
    }

    public long time; // optional
    public int free; // optional
    public int available; // optional
    public int patientId; // optional
    public String patientInfo; // optional

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        TIME((short) 1, "time"),
        FREE((short) 2, "free"),
        AVAILABLE((short) 3, "available"),
        PATIENT_ID((short) 4, "patientId"),
        PATIENT_INFO((short) 5, "patientInfo");

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
                case 1: // TIME
                    return TIME;
                case 2: // FREE
                    return FREE;
                case 3: // AVAILABLE
                    return AVAILABLE;
                case 4: // PATIENT_ID
                    return PATIENT_ID;
                case 5: // PATIENT_INFO
                    return PATIENT_INFO;
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
    private static final int __TIME_ISSET_ID = 0;
    private static final int __FREE_ISSET_ID = 1;
    private static final int __AVAILABLE_ISSET_ID = 2;
    private static final int __PATIENTID_ISSET_ID = 3;
    private byte __isset_bitfield = 0;
    private _Fields optionals[] = {_Fields.TIME, _Fields.FREE, _Fields.AVAILABLE, _Fields.PATIENT_ID, _Fields.PATIENT_INFO};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.TIME, new org.apache.thrift.meta_data.FieldMetaData("time", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64, "timestamp")));
        tmpMap.put(_Fields.FREE, new org.apache.thrift.meta_data.FieldMetaData("free", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.AVAILABLE, new org.apache.thrift.meta_data.FieldMetaData("available", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.PATIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("patientId", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.PATIENT_INFO, new org.apache.thrift.meta_data.FieldMetaData("patientInfo", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Ticket.class, metaDataMap);
    }

    public Ticket() {
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public Ticket(Ticket other) {
        __isset_bitfield = other.__isset_bitfield;
        this.time = other.time;
        this.free = other.free;
        this.available = other.available;
        this.patientId = other.patientId;
        if (other.isSetPatientInfo()) {
            this.patientInfo = other.patientInfo;
        }
    }

    public Ticket deepCopy() {
        return new Ticket(this);
    }

    @Override
    public void clear() {
        setTimeIsSet(false);
        this.time = 0;
        setFreeIsSet(false);
        this.free = 0;
        setAvailableIsSet(false);
        this.available = 0;
        setPatientIdIsSet(false);
        this.patientId = 0;
        this.patientInfo = null;
    }

    public long getTime() {
        return this.time;
    }

    public Ticket setTime(long time) {
        this.time = time;
        setTimeIsSet(true);
        return this;
    }

    public void unsetTime() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIME_ISSET_ID);
    }

    /**
     * Returns true if field time is set (has been assigned a value) and false otherwise
     */
    public boolean isSetTime() {
        return EncodingUtils.testBit(__isset_bitfield, __TIME_ISSET_ID);
    }

    public void setTimeIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIME_ISSET_ID, value);
    }

    public int getFree() {
        return this.free;
    }

    public Ticket setFree(int free) {
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

    public Ticket setAvailable(int available) {
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

    public int getPatientId() {
        return this.patientId;
    }

    public Ticket setPatientId(int patientId) {
        this.patientId = patientId;
        setPatientIdIsSet(true);
        return this;
    }

    public void unsetPatientId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PATIENTID_ISSET_ID);
    }

    /**
     * Returns true if field patientId is set (has been assigned a value) and false otherwise
     */
    public boolean isSetPatientId() {
        return EncodingUtils.testBit(__isset_bitfield, __PATIENTID_ISSET_ID);
    }

    public void setPatientIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PATIENTID_ISSET_ID, value);
    }

    public String getPatientInfo() {
        return this.patientInfo;
    }

    public Ticket setPatientInfo(String patientInfo) {
        this.patientInfo = patientInfo;
        return this;
    }

    public void unsetPatientInfo() {
        this.patientInfo = null;
    }

    /**
     * Returns true if field patientInfo is set (has been assigned a value) and false otherwise
     */
    public boolean isSetPatientInfo() {
        return this.patientInfo != null;
    }

    public void setPatientInfoIsSet(boolean value) {
        if (!value) {
            this.patientInfo = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case TIME:
                if (value == null) {
                    unsetTime();
                } else {
                    setTime((Long) value);
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

            case PATIENT_ID:
                if (value == null) {
                    unsetPatientId();
                } else {
                    setPatientId((Integer) value);
                }
                break;

            case PATIENT_INFO:
                if (value == null) {
                    unsetPatientInfo();
                } else {
                    setPatientInfo((String) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case TIME:
                return Long.valueOf(getTime());

            case FREE:
                return Integer.valueOf(getFree());

            case AVAILABLE:
                return Integer.valueOf(getAvailable());

            case PATIENT_ID:
                return Integer.valueOf(getPatientId());

            case PATIENT_INFO:
                return getPatientInfo();

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
            case TIME:
                return isSetTime();
            case FREE:
                return isSetFree();
            case AVAILABLE:
                return isSetAvailable();
            case PATIENT_ID:
                return isSetPatientId();
            case PATIENT_INFO:
                return isSetPatientInfo();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof Ticket)
            return this.equals((Ticket) that);
        return false;
    }

    public boolean equals(Ticket that) {
        if (that == null)
            return false;

        boolean this_present_time = true && this.isSetTime();
        boolean that_present_time = true && that.isSetTime();
        if (this_present_time || that_present_time) {
            if (!(this_present_time && that_present_time))
                return false;
            if (this.time != that.time)
                return false;
        }

        boolean this_present_free = true && this.isSetFree();
        boolean that_present_free = true && that.isSetFree();
        if (this_present_free || that_present_free) {
            if (!(this_present_free && that_present_free))
                return false;
            if (this.free != that.free)
                return false;
        }

        boolean this_present_available = true && this.isSetAvailable();
        boolean that_present_available = true && that.isSetAvailable();
        if (this_present_available || that_present_available) {
            if (!(this_present_available && that_present_available))
                return false;
            if (this.available != that.available)
                return false;
        }

        boolean this_present_patientId = true && this.isSetPatientId();
        boolean that_present_patientId = true && that.isSetPatientId();
        if (this_present_patientId || that_present_patientId) {
            if (!(this_present_patientId && that_present_patientId))
                return false;
            if (this.patientId != that.patientId)
                return false;
        }

        boolean this_present_patientInfo = true && this.isSetPatientInfo();
        boolean that_present_patientInfo = true && that.isSetPatientInfo();
        if (this_present_patientInfo || that_present_patientInfo) {
            if (!(this_present_patientInfo && that_present_patientInfo))
                return false;
            if (!this.patientInfo.equals(that.patientInfo))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(Ticket other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        Ticket typedOther = (Ticket) other;

        lastComparison = Boolean.valueOf(isSetTime()).compareTo(typedOther.isSetTime());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetTime()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.time, typedOther.time);
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
        lastComparison = Boolean.valueOf(isSetPatientId()).compareTo(typedOther.isSetPatientId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPatientId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.patientId, typedOther.patientId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetPatientInfo()).compareTo(typedOther.isSetPatientInfo());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPatientInfo()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.patientInfo, typedOther.patientInfo);
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
        StringBuilder sb = new StringBuilder("Ticket(");
        boolean first = true;

        if (isSetTime()) {
            sb.append("time:");
            sb.append(this.time);
            first = false;
        }
        if (isSetFree()) {
            if (!first) sb.append(", ");
            sb.append("free:");
            sb.append(this.free);
            first = false;
        }
        if (isSetAvailable()) {
            if (!first) sb.append(", ");
            sb.append("available:");
            sb.append(this.available);
            first = false;
        }
        if (isSetPatientId()) {
            if (!first) sb.append(", ");
            sb.append("patientId:");
            sb.append(this.patientId);
            first = false;
        }
        if (isSetPatientInfo()) {
            if (!first) sb.append(", ");
            sb.append("patientInfo:");
            if (this.patientInfo == null) {
                sb.append("null");
            } else {
                sb.append(this.patientInfo);
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
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private static class TicketStandardSchemeFactory implements SchemeFactory {
        public TicketStandardScheme getScheme() {
            return new TicketStandardScheme();
        }
    }

    private static class TicketStandardScheme extends StandardScheme<Ticket> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, Ticket struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // TIME
                        if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
                            struct.time = iprot.readI64();
                            struct.setTimeIsSet(true);
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
                    case 4: // PATIENT_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.patientId = iprot.readI32();
                            struct.setPatientIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // PATIENT_INFO
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.patientInfo = iprot.readString();
                            struct.setPatientInfoIsSet(true);
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

        public void write(org.apache.thrift.protocol.TProtocol oprot, Ticket struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.isSetTime()) {
                oprot.writeFieldBegin(TIME_FIELD_DESC);
                oprot.writeI64(struct.time);
                oprot.writeFieldEnd();
            }
            if (struct.isSetFree()) {
                oprot.writeFieldBegin(FREE_FIELD_DESC);
                oprot.writeI32(struct.free);
                oprot.writeFieldEnd();
            }
            if (struct.isSetAvailable()) {
                oprot.writeFieldBegin(AVAILABLE_FIELD_DESC);
                oprot.writeI32(struct.available);
                oprot.writeFieldEnd();
            }
            if (struct.isSetPatientId()) {
                oprot.writeFieldBegin(PATIENT_ID_FIELD_DESC);
                oprot.writeI32(struct.patientId);
                oprot.writeFieldEnd();
            }
            if (struct.patientInfo != null) {
                if (struct.isSetPatientInfo()) {
                    oprot.writeFieldBegin(PATIENT_INFO_FIELD_DESC);
                    oprot.writeString(struct.patientInfo);
                    oprot.writeFieldEnd();
                }
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class TicketTupleSchemeFactory implements SchemeFactory {
        public TicketTupleScheme getScheme() {
            return new TicketTupleScheme();
        }
    }

    private static class TicketTupleScheme extends TupleScheme<Ticket> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, Ticket struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetTime()) {
                optionals.set(0);
            }
            if (struct.isSetFree()) {
                optionals.set(1);
            }
            if (struct.isSetAvailable()) {
                optionals.set(2);
            }
            if (struct.isSetPatientId()) {
                optionals.set(3);
            }
            if (struct.isSetPatientInfo()) {
                optionals.set(4);
            }
            oprot.writeBitSet(optionals, 5);
            if (struct.isSetTime()) {
                oprot.writeI64(struct.time);
            }
            if (struct.isSetFree()) {
                oprot.writeI32(struct.free);
            }
            if (struct.isSetAvailable()) {
                oprot.writeI32(struct.available);
            }
            if (struct.isSetPatientId()) {
                oprot.writeI32(struct.patientId);
            }
            if (struct.isSetPatientInfo()) {
                oprot.writeString(struct.patientInfo);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, Ticket struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(5);
            if (incoming.get(0)) {
                struct.time = iprot.readI64();
                struct.setTimeIsSet(true);
            }
            if (incoming.get(1)) {
                struct.free = iprot.readI32();
                struct.setFreeIsSet(true);
            }
            if (incoming.get(2)) {
                struct.available = iprot.readI32();
                struct.setAvailableIsSet(true);
            }
            if (incoming.get(3)) {
                struct.patientId = iprot.readI32();
                struct.setPatientIdIsSet(true);
            }
            if (incoming.get(4)) {
                struct.patientInfo = iprot.readString();
                struct.setPatientInfoIsSet(true);
            }
        }
    }

}

