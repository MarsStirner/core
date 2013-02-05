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

public class Address implements org.apache.thrift.TBase<Address, Address._Fields>, java.io.Serializable, Cloneable {
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Address");

    private static final org.apache.thrift.protocol.TField ORG_STRUCTURE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("orgStructureId", org.apache.thrift.protocol.TType.I32, (short) 1);
    private static final org.apache.thrift.protocol.TField POINT_KLADR_FIELD_DESC = new org.apache.thrift.protocol.TField("pointKLADR", org.apache.thrift.protocol.TType.STRING, (short) 2);
    private static final org.apache.thrift.protocol.TField STREET_KLADR_FIELD_DESC = new org.apache.thrift.protocol.TField("streetKLADR", org.apache.thrift.protocol.TType.STRING, (short) 3);
    private static final org.apache.thrift.protocol.TField NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("number", org.apache.thrift.protocol.TType.STRING, (short) 4);
    private static final org.apache.thrift.protocol.TField CORPUS_FIELD_DESC = new org.apache.thrift.protocol.TField("corpus", org.apache.thrift.protocol.TType.STRING, (short) 5);
    private static final org.apache.thrift.protocol.TField FIRST_FLAT_FIELD_DESC = new org.apache.thrift.protocol.TField("firstFlat", org.apache.thrift.protocol.TType.I32, (short) 6);
    private static final org.apache.thrift.protocol.TField LAST_FLAT_FIELD_DESC = new org.apache.thrift.protocol.TField("lastFlat", org.apache.thrift.protocol.TType.I32, (short) 7);

    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new AddressStandardSchemeFactory());
        schemes.put(TupleScheme.class, new AddressTupleSchemeFactory());
    }

    public int orgStructureId; // required
    public String pointKLADR; // required
    public String streetKLADR; // required
    public String number; // optional
    public String corpus; // optional
    public int firstFlat; // optional
    public int lastFlat; // optional

    /**
     * The set of fields this struct contains, along with convenience methods for finding and manipulating them.
     */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        ORG_STRUCTURE_ID((short) 1, "orgStructureId"),
        POINT_KLADR((short) 2, "pointKLADR"),
        STREET_KLADR((short) 3, "streetKLADR"),
        NUMBER((short) 4, "number"),
        CORPUS((short) 5, "corpus"),
        FIRST_FLAT((short) 6, "firstFlat"),
        LAST_FLAT((short) 7, "lastFlat");

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
                case 1: // ORG_STRUCTURE_ID
                    return ORG_STRUCTURE_ID;
                case 2: // POINT_KLADR
                    return POINT_KLADR;
                case 3: // STREET_KLADR
                    return STREET_KLADR;
                case 4: // NUMBER
                    return NUMBER;
                case 5: // CORPUS
                    return CORPUS;
                case 6: // FIRST_FLAT
                    return FIRST_FLAT;
                case 7: // LAST_FLAT
                    return LAST_FLAT;
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
    private static final int __ORGSTRUCTUREID_ISSET_ID = 0;
    private static final int __FIRSTFLAT_ISSET_ID = 1;
    private static final int __LASTFLAT_ISSET_ID = 2;
    private byte __isset_bitfield = 0;
    private _Fields optionals[] = {_Fields.NUMBER, _Fields.CORPUS, _Fields.FIRST_FLAT, _Fields.LAST_FLAT};
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.ORG_STRUCTURE_ID, new org.apache.thrift.meta_data.FieldMetaData("orgStructureId", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.POINT_KLADR, new org.apache.thrift.meta_data.FieldMetaData("pointKLADR", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.STREET_KLADR, new org.apache.thrift.meta_data.FieldMetaData("streetKLADR", org.apache.thrift.TFieldRequirementType.REQUIRED,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.NUMBER, new org.apache.thrift.meta_data.FieldMetaData("number", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.CORPUS, new org.apache.thrift.meta_data.FieldMetaData("corpus", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.FIRST_FLAT, new org.apache.thrift.meta_data.FieldMetaData("firstFlat", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.LAST_FLAT, new org.apache.thrift.meta_data.FieldMetaData("lastFlat", org.apache.thrift.TFieldRequirementType.OPTIONAL,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Address.class, metaDataMap);
    }

    public Address() {
    }

    public Address(
            int orgStructureId,
            String pointKLADR,
            String streetKLADR) {
        this();
        this.orgStructureId = orgStructureId;
        setOrgStructureIdIsSet(true);
        this.pointKLADR = pointKLADR;
        this.streetKLADR = streetKLADR;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public Address(Address other) {
        __isset_bitfield = other.__isset_bitfield;
        this.orgStructureId = other.orgStructureId;
        if (other.isSetPointKLADR()) {
            this.pointKLADR = other.pointKLADR;
        }
        if (other.isSetStreetKLADR()) {
            this.streetKLADR = other.streetKLADR;
        }
        if (other.isSetNumber()) {
            this.number = other.number;
        }
        if (other.isSetCorpus()) {
            this.corpus = other.corpus;
        }
        this.firstFlat = other.firstFlat;
        this.lastFlat = other.lastFlat;
    }

    public Address deepCopy() {
        return new Address(this);
    }

    @Override
    public void clear() {
        setOrgStructureIdIsSet(false);
        this.orgStructureId = 0;
        this.pointKLADR = null;
        this.streetKLADR = null;
        this.number = null;
        this.corpus = null;
        setFirstFlatIsSet(false);
        this.firstFlat = 0;
        setLastFlatIsSet(false);
        this.lastFlat = 0;
    }

    public int getOrgStructureId() {
        return this.orgStructureId;
    }

    public Address setOrgStructureId(int orgStructureId) {
        this.orgStructureId = orgStructureId;
        setOrgStructureIdIsSet(true);
        return this;
    }

    public void unsetOrgStructureId() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID);
    }

    /**
     * Returns true if field orgStructureId is set (has been assigned a value) and false otherwise
     */
    public boolean isSetOrgStructureId() {
        return EncodingUtils.testBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID);
    }

    public void setOrgStructureIdIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ORGSTRUCTUREID_ISSET_ID, value);
    }

    public String getPointKLADR() {
        return this.pointKLADR;
    }

    public Address setPointKLADR(String pointKLADR) {
        this.pointKLADR = pointKLADR;
        return this;
    }

    public void unsetPointKLADR() {
        this.pointKLADR = null;
    }

    /**
     * Returns true if field pointKLADR is set (has been assigned a value) and false otherwise
     */
    public boolean isSetPointKLADR() {
        return this.pointKLADR != null;
    }

    public void setPointKLADRIsSet(boolean value) {
        if (!value) {
            this.pointKLADR = null;
        }
    }

    public String getStreetKLADR() {
        return this.streetKLADR;
    }

    public Address setStreetKLADR(String streetKLADR) {
        this.streetKLADR = streetKLADR;
        return this;
    }

    public void unsetStreetKLADR() {
        this.streetKLADR = null;
    }

    /**
     * Returns true if field streetKLADR is set (has been assigned a value) and false otherwise
     */
    public boolean isSetStreetKLADR() {
        return this.streetKLADR != null;
    }

    public void setStreetKLADRIsSet(boolean value) {
        if (!value) {
            this.streetKLADR = null;
        }
    }

    public String getNumber() {
        return this.number;
    }

    public Address setNumber(String number) {
        this.number = number;
        return this;
    }

    public void unsetNumber() {
        this.number = null;
    }

    /**
     * Returns true if field number is set (has been assigned a value) and false otherwise
     */
    public boolean isSetNumber() {
        return this.number != null;
    }

    public void setNumberIsSet(boolean value) {
        if (!value) {
            this.number = null;
        }
    }

    public String getCorpus() {
        return this.corpus;
    }

    public Address setCorpus(String corpus) {
        this.corpus = corpus;
        return this;
    }

    public void unsetCorpus() {
        this.corpus = null;
    }

    /**
     * Returns true if field corpus is set (has been assigned a value) and false otherwise
     */
    public boolean isSetCorpus() {
        return this.corpus != null;
    }

    public void setCorpusIsSet(boolean value) {
        if (!value) {
            this.corpus = null;
        }
    }

    public int getFirstFlat() {
        return this.firstFlat;
    }

    public Address setFirstFlat(int firstFlat) {
        this.firstFlat = firstFlat;
        setFirstFlatIsSet(true);
        return this;
    }

    public void unsetFirstFlat() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FIRSTFLAT_ISSET_ID);
    }

    /**
     * Returns true if field firstFlat is set (has been assigned a value) and false otherwise
     */
    public boolean isSetFirstFlat() {
        return EncodingUtils.testBit(__isset_bitfield, __FIRSTFLAT_ISSET_ID);
    }

    public void setFirstFlatIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FIRSTFLAT_ISSET_ID, value);
    }

    public int getLastFlat() {
        return this.lastFlat;
    }

    public Address setLastFlat(int lastFlat) {
        this.lastFlat = lastFlat;
        setLastFlatIsSet(true);
        return this;
    }

    public void unsetLastFlat() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __LASTFLAT_ISSET_ID);
    }

    /**
     * Returns true if field lastFlat is set (has been assigned a value) and false otherwise
     */
    public boolean isSetLastFlat() {
        return EncodingUtils.testBit(__isset_bitfield, __LASTFLAT_ISSET_ID);
    }

    public void setLastFlatIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __LASTFLAT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case ORG_STRUCTURE_ID:
                if (value == null) {
                    unsetOrgStructureId();
                } else {
                    setOrgStructureId((Integer) value);
                }
                break;

            case POINT_KLADR:
                if (value == null) {
                    unsetPointKLADR();
                } else {
                    setPointKLADR((String) value);
                }
                break;

            case STREET_KLADR:
                if (value == null) {
                    unsetStreetKLADR();
                } else {
                    setStreetKLADR((String) value);
                }
                break;

            case NUMBER:
                if (value == null) {
                    unsetNumber();
                } else {
                    setNumber((String) value);
                }
                break;

            case CORPUS:
                if (value == null) {
                    unsetCorpus();
                } else {
                    setCorpus((String) value);
                }
                break;

            case FIRST_FLAT:
                if (value == null) {
                    unsetFirstFlat();
                } else {
                    setFirstFlat((Integer) value);
                }
                break;

            case LAST_FLAT:
                if (value == null) {
                    unsetLastFlat();
                } else {
                    setLastFlat((Integer) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case ORG_STRUCTURE_ID:
                return Integer.valueOf(getOrgStructureId());

            case POINT_KLADR:
                return getPointKLADR();

            case STREET_KLADR:
                return getStreetKLADR();

            case NUMBER:
                return getNumber();

            case CORPUS:
                return getCorpus();

            case FIRST_FLAT:
                return Integer.valueOf(getFirstFlat());

            case LAST_FLAT:
                return Integer.valueOf(getLastFlat());

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
            case ORG_STRUCTURE_ID:
                return isSetOrgStructureId();
            case POINT_KLADR:
                return isSetPointKLADR();
            case STREET_KLADR:
                return isSetStreetKLADR();
            case NUMBER:
                return isSetNumber();
            case CORPUS:
                return isSetCorpus();
            case FIRST_FLAT:
                return isSetFirstFlat();
            case LAST_FLAT:
                return isSetLastFlat();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof Address)
            return this.equals((Address) that);
        return false;
    }

    public boolean equals(Address that) {
        if (that == null)
            return false;

        boolean this_present_orgStructureId = true;
        boolean that_present_orgStructureId = true;
        if (this_present_orgStructureId || that_present_orgStructureId) {
            if (!(this_present_orgStructureId && that_present_orgStructureId))
                return false;
            if (this.orgStructureId != that.orgStructureId)
                return false;
        }

        boolean this_present_pointKLADR = true && this.isSetPointKLADR();
        boolean that_present_pointKLADR = true && that.isSetPointKLADR();
        if (this_present_pointKLADR || that_present_pointKLADR) {
            if (!(this_present_pointKLADR && that_present_pointKLADR))
                return false;
            if (!this.pointKLADR.equals(that.pointKLADR))
                return false;
        }

        boolean this_present_streetKLADR = true && this.isSetStreetKLADR();
        boolean that_present_streetKLADR = true && that.isSetStreetKLADR();
        if (this_present_streetKLADR || that_present_streetKLADR) {
            if (!(this_present_streetKLADR && that_present_streetKLADR))
                return false;
            if (!this.streetKLADR.equals(that.streetKLADR))
                return false;
        }

        boolean this_present_number = true && this.isSetNumber();
        boolean that_present_number = true && that.isSetNumber();
        if (this_present_number || that_present_number) {
            if (!(this_present_number && that_present_number))
                return false;
            if (!this.number.equals(that.number))
                return false;
        }

        boolean this_present_corpus = true && this.isSetCorpus();
        boolean that_present_corpus = true && that.isSetCorpus();
        if (this_present_corpus || that_present_corpus) {
            if (!(this_present_corpus && that_present_corpus))
                return false;
            if (!this.corpus.equals(that.corpus))
                return false;
        }

        boolean this_present_firstFlat = true && this.isSetFirstFlat();
        boolean that_present_firstFlat = true && that.isSetFirstFlat();
        if (this_present_firstFlat || that_present_firstFlat) {
            if (!(this_present_firstFlat && that_present_firstFlat))
                return false;
            if (this.firstFlat != that.firstFlat)
                return false;
        }

        boolean this_present_lastFlat = true && this.isSetLastFlat();
        boolean that_present_lastFlat = true && that.isSetLastFlat();
        if (this_present_lastFlat || that_present_lastFlat) {
            if (!(this_present_lastFlat && that_present_lastFlat))
                return false;
            if (this.lastFlat != that.lastFlat)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public int compareTo(Address other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;
        Address typedOther = (Address) other;

        lastComparison = Boolean.valueOf(isSetOrgStructureId()).compareTo(typedOther.isSetOrgStructureId());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetOrgStructureId()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.orgStructureId, typedOther.orgStructureId);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetPointKLADR()).compareTo(typedOther.isSetPointKLADR());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetPointKLADR()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.pointKLADR, typedOther.pointKLADR);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetStreetKLADR()).compareTo(typedOther.isSetStreetKLADR());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetStreetKLADR()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.streetKLADR, typedOther.streetKLADR);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetNumber()).compareTo(typedOther.isSetNumber());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetNumber()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.number, typedOther.number);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCorpus()).compareTo(typedOther.isSetCorpus());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCorpus()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.corpus, typedOther.corpus);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetFirstFlat()).compareTo(typedOther.isSetFirstFlat());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetFirstFlat()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.firstFlat, typedOther.firstFlat);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetLastFlat()).compareTo(typedOther.isSetLastFlat());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetLastFlat()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.lastFlat, typedOther.lastFlat);
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
        StringBuilder sb = new StringBuilder("Address(");
        boolean first = true;

        sb.append("orgStructureId:");
        sb.append(this.orgStructureId);
        first = false;
        if (!first) sb.append(", ");
        sb.append("pointKLADR:");
        if (this.pointKLADR == null) {
            sb.append("null");
        } else {
            sb.append(this.pointKLADR);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("streetKLADR:");
        if (this.streetKLADR == null) {
            sb.append("null");
        } else {
            sb.append(this.streetKLADR);
        }
        first = false;
        if (isSetNumber()) {
            if (!first) sb.append(", ");
            sb.append("number:");
            if (this.number == null) {
                sb.append("null");
            } else {
                sb.append(this.number);
            }
            first = false;
        }
        if (isSetCorpus()) {
            if (!first) sb.append(", ");
            sb.append("corpus:");
            if (this.corpus == null) {
                sb.append("null");
            } else {
                sb.append(this.corpus);
            }
            first = false;
        }
        if (isSetFirstFlat()) {
            if (!first) sb.append(", ");
            sb.append("firstFlat:");
            sb.append(this.firstFlat);
            first = false;
        }
        if (isSetLastFlat()) {
            if (!first) sb.append(", ");
            sb.append("lastFlat:");
            sb.append(this.lastFlat);
            first = false;
        }
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // alas, we cannot check 'orgStructureId' because it's a primitive and you chose the non-beans generator.
        if (pointKLADR == null) {
            throw new org.apache.thrift.protocol.TProtocolException("Required field 'pointKLADR' was not present! Struct: " + toString());
        }
        if (streetKLADR == null) {
            throw new org.apache.thrift.protocol.TProtocolException("Required field 'streetKLADR' was not present! Struct: " + toString());
        }
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

    private static class AddressStandardSchemeFactory implements SchemeFactory {
        public AddressStandardScheme getScheme() {
            return new AddressStandardScheme();
        }
    }

    private static class AddressStandardScheme extends StandardScheme<Address> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, Address struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // ORG_STRUCTURE_ID
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.orgStructureId = iprot.readI32();
                            struct.setOrgStructureIdIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // POINT_KLADR
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.pointKLADR = iprot.readString();
                            struct.setPointKLADRIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // STREET_KLADR
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.streetKLADR = iprot.readString();
                            struct.setStreetKLADRIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 4: // NUMBER
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.number = iprot.readString();
                            struct.setNumberIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 5: // CORPUS
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.corpus = iprot.readString();
                            struct.setCorpusIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 6: // FIRST_FLAT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.firstFlat = iprot.readI32();
                            struct.setFirstFlatIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 7: // LAST_FLAT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.lastFlat = iprot.readI32();
                            struct.setLastFlatIsSet(true);
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
            if (!struct.isSetOrgStructureId()) {
                throw new org.apache.thrift.protocol.TProtocolException("Required field 'orgStructureId' was not found in serialized data! Struct: " + toString());
            }
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, Address struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            oprot.writeFieldBegin(ORG_STRUCTURE_ID_FIELD_DESC);
            oprot.writeI32(struct.orgStructureId);
            oprot.writeFieldEnd();
            if (struct.pointKLADR != null) {
                oprot.writeFieldBegin(POINT_KLADR_FIELD_DESC);
                oprot.writeString(struct.pointKLADR);
                oprot.writeFieldEnd();
            }
            if (struct.streetKLADR != null) {
                oprot.writeFieldBegin(STREET_KLADR_FIELD_DESC);
                oprot.writeString(struct.streetKLADR);
                oprot.writeFieldEnd();
            }
            if (struct.number != null) {
                if (struct.isSetNumber()) {
                    oprot.writeFieldBegin(NUMBER_FIELD_DESC);
                    oprot.writeString(struct.number);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.corpus != null) {
                if (struct.isSetCorpus()) {
                    oprot.writeFieldBegin(CORPUS_FIELD_DESC);
                    oprot.writeString(struct.corpus);
                    oprot.writeFieldEnd();
                }
            }
            if (struct.isSetFirstFlat()) {
                oprot.writeFieldBegin(FIRST_FLAT_FIELD_DESC);
                oprot.writeI32(struct.firstFlat);
                oprot.writeFieldEnd();
            }
            if (struct.isSetLastFlat()) {
                oprot.writeFieldBegin(LAST_FLAT_FIELD_DESC);
                oprot.writeI32(struct.lastFlat);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class AddressTupleSchemeFactory implements SchemeFactory {
        public AddressTupleScheme getScheme() {
            return new AddressTupleScheme();
        }
    }

    private static class AddressTupleScheme extends TupleScheme<Address> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, Address struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            oprot.writeI32(struct.orgStructureId);
            oprot.writeString(struct.pointKLADR);
            oprot.writeString(struct.streetKLADR);
            BitSet optionals = new BitSet();
            if (struct.isSetNumber()) {
                optionals.set(0);
            }
            if (struct.isSetCorpus()) {
                optionals.set(1);
            }
            if (struct.isSetFirstFlat()) {
                optionals.set(2);
            }
            if (struct.isSetLastFlat()) {
                optionals.set(3);
            }
            oprot.writeBitSet(optionals, 4);
            if (struct.isSetNumber()) {
                oprot.writeString(struct.number);
            }
            if (struct.isSetCorpus()) {
                oprot.writeString(struct.corpus);
            }
            if (struct.isSetFirstFlat()) {
                oprot.writeI32(struct.firstFlat);
            }
            if (struct.isSetLastFlat()) {
                oprot.writeI32(struct.lastFlat);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, Address struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            struct.orgStructureId = iprot.readI32();
            struct.setOrgStructureIdIsSet(true);
            struct.pointKLADR = iprot.readString();
            struct.setPointKLADRIsSet(true);
            struct.streetKLADR = iprot.readString();
            struct.setStreetKLADRIsSet(true);
            BitSet incoming = iprot.readBitSet(4);
            if (incoming.get(0)) {
                struct.number = iprot.readString();
                struct.setNumberIsSet(true);
            }
            if (incoming.get(1)) {
                struct.corpus = iprot.readString();
                struct.setCorpusIsSet(true);
            }
            if (incoming.get(2)) {
                struct.firstFlat = iprot.readI32();
                struct.setFirstFlatIsSet(true);
            }
            if (incoming.get(3)) {
                struct.lastFlat = iprot.readI32();
                struct.setLastFlatIsSet(true);
            }
        }
    }

}
