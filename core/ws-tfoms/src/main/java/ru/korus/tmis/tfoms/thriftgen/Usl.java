/**
 * Autogenerated by Thrift Compiler (0.9.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package ru.korus.tmis.tfoms.thriftgen;

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

public class Usl implements org.apache.thrift.TBase<Usl, Usl._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Usl");

  private static final org.apache.thrift.protocol.TField IDSERV_FIELD_DESC = new org.apache.thrift.protocol.TField("IDSERV", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField CODE__USL_FIELD_DESC = new org.apache.thrift.protocol.TField("CODE_USL", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField KOL__USL_FIELD_DESC = new org.apache.thrift.protocol.TField("KOL_USL", org.apache.thrift.protocol.TType.DOUBLE, (short)3);
  private static final org.apache.thrift.protocol.TField TARIF_FIELD_DESC = new org.apache.thrift.protocol.TField("TARIF", org.apache.thrift.protocol.TType.DOUBLE, (short)4);
  private static final org.apache.thrift.protocol.TField CONTRACT__TARIFF_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("contract_TariffId", org.apache.thrift.protocol.TType.I32, (short)5);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new UslStandardSchemeFactory());
    schemes.put(TupleScheme.class, new UslTupleSchemeFactory());
  }

  public int IDSERV; // required
  public String CODE_USL; // required
  public double KOL_USL; // required
  public double TARIF; // required
  public int contract_TariffId; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    IDSERV((short)1, "IDSERV"),
    CODE__USL((short)2, "CODE_USL"),
    KOL__USL((short)3, "KOL_USL"),
    TARIF((short)4, "TARIF"),
    CONTRACT__TARIFF_ID((short)5, "contract_TariffId");

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
      switch(fieldId) {
        case 1: // IDSERV
          return IDSERV;
        case 2: // CODE__USL
          return CODE__USL;
        case 3: // KOL__USL
          return KOL__USL;
        case 4: // TARIF
          return TARIF;
        case 5: // CONTRACT__TARIFF_ID
          return CONTRACT__TARIFF_ID;
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
  private static final int __IDSERV_ISSET_ID = 0;
  private static final int __KOL_USL_ISSET_ID = 1;
  private static final int __TARIF_ISSET_ID = 2;
  private static final int __CONTRACT_TARIFFID_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.IDSERV, new org.apache.thrift.meta_data.FieldMetaData("IDSERV", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.CODE__USL, new org.apache.thrift.meta_data.FieldMetaData("CODE_USL", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.KOL__USL, new org.apache.thrift.meta_data.FieldMetaData("KOL_USL", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.TARIF, new org.apache.thrift.meta_data.FieldMetaData("TARIF", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.DOUBLE)));
    tmpMap.put(_Fields.CONTRACT__TARIFF_ID, new org.apache.thrift.meta_data.FieldMetaData("contract_TariffId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Usl.class, metaDataMap);
  }

  public Usl() {
    this.IDSERV = -1;

  }

  public Usl(
    int IDSERV,
    String CODE_USL,
    double KOL_USL,
    double TARIF,
    int contract_TariffId)
  {
    this();
    this.IDSERV = IDSERV;
    setIDSERVIsSet(true);
    this.CODE_USL = CODE_USL;
    this.KOL_USL = KOL_USL;
    setKOL_USLIsSet(true);
    this.TARIF = TARIF;
    setTARIFIsSet(true);
    this.contract_TariffId = contract_TariffId;
    setContract_TariffIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Usl(Usl other) {
    __isset_bitfield = other.__isset_bitfield;
    this.IDSERV = other.IDSERV;
    if (other.isSetCODE_USL()) {
      this.CODE_USL = other.CODE_USL;
    }
    this.KOL_USL = other.KOL_USL;
    this.TARIF = other.TARIF;
    this.contract_TariffId = other.contract_TariffId;
  }

  public Usl deepCopy() {
    return new Usl(this);
  }

  @Override
  public void clear() {
    this.IDSERV = -1;

    this.CODE_USL = null;
    setKOL_USLIsSet(false);
    this.KOL_USL = 0.0;
    setTARIFIsSet(false);
    this.TARIF = 0.0;
    setContract_TariffIdIsSet(false);
    this.contract_TariffId = 0;
  }

  public int getIDSERV() {
    return this.IDSERV;
  }

  public Usl setIDSERV(int IDSERV) {
    this.IDSERV = IDSERV;
    setIDSERVIsSet(true);
    return this;
  }

  public void unsetIDSERV() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __IDSERV_ISSET_ID);
  }

  /** Returns true if field IDSERV is set (has been assigned a value) and false otherwise */
  public boolean isSetIDSERV() {
    return EncodingUtils.testBit(__isset_bitfield, __IDSERV_ISSET_ID);
  }

  public void setIDSERVIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __IDSERV_ISSET_ID, value);
  }

  public String getCODE_USL() {
    return this.CODE_USL;
  }

  public Usl setCODE_USL(String CODE_USL) {
    this.CODE_USL = CODE_USL;
    return this;
  }

  public void unsetCODE_USL() {
    this.CODE_USL = null;
  }

  /** Returns true if field CODE_USL is set (has been assigned a value) and false otherwise */
  public boolean isSetCODE_USL() {
    return this.CODE_USL != null;
  }

  public void setCODE_USLIsSet(boolean value) {
    if (!value) {
      this.CODE_USL = null;
    }
  }

  public double getKOL_USL() {
    return this.KOL_USL;
  }

  public Usl setKOL_USL(double KOL_USL) {
    this.KOL_USL = KOL_USL;
    setKOL_USLIsSet(true);
    return this;
  }

  public void unsetKOL_USL() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __KOL_USL_ISSET_ID);
  }

  /** Returns true if field KOL_USL is set (has been assigned a value) and false otherwise */
  public boolean isSetKOL_USL() {
    return EncodingUtils.testBit(__isset_bitfield, __KOL_USL_ISSET_ID);
  }

  public void setKOL_USLIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __KOL_USL_ISSET_ID, value);
  }

  public double getTARIF() {
    return this.TARIF;
  }

  public Usl setTARIF(double TARIF) {
    this.TARIF = TARIF;
    setTARIFIsSet(true);
    return this;
  }

  public void unsetTARIF() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TARIF_ISSET_ID);
  }

  /** Returns true if field TARIF is set (has been assigned a value) and false otherwise */
  public boolean isSetTARIF() {
    return EncodingUtils.testBit(__isset_bitfield, __TARIF_ISSET_ID);
  }

  public void setTARIFIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TARIF_ISSET_ID, value);
  }

  public int getContract_TariffId() {
    return this.contract_TariffId;
  }

  public Usl setContract_TariffId(int contract_TariffId) {
    this.contract_TariffId = contract_TariffId;
    setContract_TariffIdIsSet(true);
    return this;
  }

  public void unsetContract_TariffId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CONTRACT_TARIFFID_ISSET_ID);
  }

  /** Returns true if field contract_TariffId is set (has been assigned a value) and false otherwise */
  public boolean isSetContract_TariffId() {
    return EncodingUtils.testBit(__isset_bitfield, __CONTRACT_TARIFFID_ISSET_ID);
  }

  public void setContract_TariffIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CONTRACT_TARIFFID_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case IDSERV:
      if (value == null) {
        unsetIDSERV();
      } else {
        setIDSERV((Integer)value);
      }
      break;

    case CODE__USL:
      if (value == null) {
        unsetCODE_USL();
      } else {
        setCODE_USL((String)value);
      }
      break;

    case KOL__USL:
      if (value == null) {
        unsetKOL_USL();
      } else {
        setKOL_USL((Double)value);
      }
      break;

    case TARIF:
      if (value == null) {
        unsetTARIF();
      } else {
        setTARIF((Double)value);
      }
      break;

    case CONTRACT__TARIFF_ID:
      if (value == null) {
        unsetContract_TariffId();
      } else {
        setContract_TariffId((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case IDSERV:
      return Integer.valueOf(getIDSERV());

    case CODE__USL:
      return getCODE_USL();

    case KOL__USL:
      return Double.valueOf(getKOL_USL());

    case TARIF:
      return Double.valueOf(getTARIF());

    case CONTRACT__TARIFF_ID:
      return Integer.valueOf(getContract_TariffId());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case IDSERV:
      return isSetIDSERV();
    case CODE__USL:
      return isSetCODE_USL();
    case KOL__USL:
      return isSetKOL_USL();
    case TARIF:
      return isSetTARIF();
    case CONTRACT__TARIFF_ID:
      return isSetContract_TariffId();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Usl)
      return this.equals((Usl)that);
    return false;
  }

  public boolean equals(Usl that) {
    if (that == null)
      return false;

    boolean this_present_IDSERV = true;
    boolean that_present_IDSERV = true;
    if (this_present_IDSERV || that_present_IDSERV) {
      if (!(this_present_IDSERV && that_present_IDSERV))
        return false;
      if (this.IDSERV != that.IDSERV)
        return false;
    }

    boolean this_present_CODE_USL = true && this.isSetCODE_USL();
    boolean that_present_CODE_USL = true && that.isSetCODE_USL();
    if (this_present_CODE_USL || that_present_CODE_USL) {
      if (!(this_present_CODE_USL && that_present_CODE_USL))
        return false;
      if (!this.CODE_USL.equals(that.CODE_USL))
        return false;
    }

    boolean this_present_KOL_USL = true;
    boolean that_present_KOL_USL = true;
    if (this_present_KOL_USL || that_present_KOL_USL) {
      if (!(this_present_KOL_USL && that_present_KOL_USL))
        return false;
      if (this.KOL_USL != that.KOL_USL)
        return false;
    }

    boolean this_present_TARIF = true;
    boolean that_present_TARIF = true;
    if (this_present_TARIF || that_present_TARIF) {
      if (!(this_present_TARIF && that_present_TARIF))
        return false;
      if (this.TARIF != that.TARIF)
        return false;
    }

    boolean this_present_contract_TariffId = true;
    boolean that_present_contract_TariffId = true;
    if (this_present_contract_TariffId || that_present_contract_TariffId) {
      if (!(this_present_contract_TariffId && that_present_contract_TariffId))
        return false;
      if (this.contract_TariffId != that.contract_TariffId)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Usl other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Usl typedOther = (Usl)other;

    lastComparison = Boolean.valueOf(isSetIDSERV()).compareTo(typedOther.isSetIDSERV());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIDSERV()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.IDSERV, typedOther.IDSERV);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCODE_USL()).compareTo(typedOther.isSetCODE_USL());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCODE_USL()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.CODE_USL, typedOther.CODE_USL);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetKOL_USL()).compareTo(typedOther.isSetKOL_USL());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetKOL_USL()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.KOL_USL, typedOther.KOL_USL);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTARIF()).compareTo(typedOther.isSetTARIF());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTARIF()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.TARIF, typedOther.TARIF);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetContract_TariffId()).compareTo(typedOther.isSetContract_TariffId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetContract_TariffId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.contract_TariffId, typedOther.contract_TariffId);
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
    StringBuilder sb = new StringBuilder("Usl(");
    boolean first = true;

    sb.append("IDSERV:");
    sb.append(this.IDSERV);
    first = false;
    if (!first) sb.append(", ");
    sb.append("CODE_USL:");
    if (this.CODE_USL == null) {
      sb.append("null");
    } else {
      sb.append(this.CODE_USL);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("KOL_USL:");
    sb.append(this.KOL_USL);
    first = false;
    if (!first) sb.append(", ");
    sb.append("TARIF:");
    sb.append(this.TARIF);
    first = false;
    if (!first) sb.append(", ");
    sb.append("contract_TariffId:");
    sb.append(this.contract_TariffId);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'IDSERV' because it's a primitive and you chose the non-beans generator.
    if (CODE_USL == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'CODE_USL' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'KOL_USL' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'TARIF' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'contract_TariffId' because it's a primitive and you chose the non-beans generator.
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

  private static class UslStandardSchemeFactory implements SchemeFactory {
    public UslStandardScheme getScheme() {
      return new UslStandardScheme();
    }
  }

  private static class UslStandardScheme extends StandardScheme<Usl> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Usl struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // IDSERV
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.IDSERV = iprot.readI32();
              struct.setIDSERVIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // CODE__USL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.CODE_USL = iprot.readString();
              struct.setCODE_USLIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // KOL__USL
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.KOL_USL = iprot.readDouble();
              struct.setKOL_USLIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TARIF
            if (schemeField.type == org.apache.thrift.protocol.TType.DOUBLE) {
              struct.TARIF = iprot.readDouble();
              struct.setTARIFIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // CONTRACT__TARIFF_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.contract_TariffId = iprot.readI32();
              struct.setContract_TariffIdIsSet(true);
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
      if (!struct.isSetIDSERV()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'IDSERV' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetKOL_USL()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'KOL_USL' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTARIF()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'TARIF' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetContract_TariffId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'contract_TariffId' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Usl struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(IDSERV_FIELD_DESC);
      oprot.writeI32(struct.IDSERV);
      oprot.writeFieldEnd();
      if (struct.CODE_USL != null) {
        oprot.writeFieldBegin(CODE__USL_FIELD_DESC);
        oprot.writeString(struct.CODE_USL);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(KOL__USL_FIELD_DESC);
      oprot.writeDouble(struct.KOL_USL);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TARIF_FIELD_DESC);
      oprot.writeDouble(struct.TARIF);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(CONTRACT__TARIFF_ID_FIELD_DESC);
      oprot.writeI32(struct.contract_TariffId);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class UslTupleSchemeFactory implements SchemeFactory {
    public UslTupleScheme getScheme() {
      return new UslTupleScheme();
    }
  }

  private static class UslTupleScheme extends TupleScheme<Usl> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Usl struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.IDSERV);
      oprot.writeString(struct.CODE_USL);
      oprot.writeDouble(struct.KOL_USL);
      oprot.writeDouble(struct.TARIF);
      oprot.writeI32(struct.contract_TariffId);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Usl struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.IDSERV = iprot.readI32();
      struct.setIDSERVIsSet(true);
      struct.CODE_USL = iprot.readString();
      struct.setCODE_USLIsSet(true);
      struct.KOL_USL = iprot.readDouble();
      struct.setKOL_USLIsSet(true);
      struct.TARIF = iprot.readDouble();
      struct.setTARIFIsSet(true);
      struct.contract_TariffId = iprot.readI32();
      struct.setContract_TariffIdIsSet(true);
    }
  }

}
