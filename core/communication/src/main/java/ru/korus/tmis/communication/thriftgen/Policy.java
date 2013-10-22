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

/**
 * Policy
 * Структура с данными о полисе
 * @param serial            1)Серия полиса
 * @param number            2)Номер полиса
 * @param typeCode          3)Код типа полиса
 * @param insurerInfisCode  4)Инфис-код страховой организации
 */
public class Policy implements org.apache.thrift.TBase<Policy, Policy._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Policy");

  private static final org.apache.thrift.protocol.TField SERIAL_FIELD_DESC = new org.apache.thrift.protocol.TField("serial", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField NUMBER_FIELD_DESC = new org.apache.thrift.protocol.TField("number", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TYPE_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("typeCode", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField INSURER_INFIS_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("insurerInfisCode", org.apache.thrift.protocol.TType.STRING, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PolicyStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PolicyTupleSchemeFactory());
  }

  public String serial; // optional
  public String number; // required
  public String typeCode; // required
  public String insurerInfisCode; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SERIAL((short)1, "serial"),
    NUMBER((short)2, "number"),
    TYPE_CODE((short)3, "typeCode"),
    INSURER_INFIS_CODE((short)4, "insurerInfisCode");

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
        case 1: // SERIAL
          return SERIAL;
        case 2: // NUMBER
          return NUMBER;
        case 3: // TYPE_CODE
          return TYPE_CODE;
        case 4: // INSURER_INFIS_CODE
          return INSURER_INFIS_CODE;
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
  private _Fields optionals[] = {_Fields.SERIAL,_Fields.INSURER_INFIS_CODE};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SERIAL, new org.apache.thrift.meta_data.FieldMetaData("serial", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.NUMBER, new org.apache.thrift.meta_data.FieldMetaData("number", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TYPE_CODE, new org.apache.thrift.meta_data.FieldMetaData("typeCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.INSURER_INFIS_CODE, new org.apache.thrift.meta_data.FieldMetaData("insurerInfisCode", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Policy.class, metaDataMap);
  }

  public Policy() {
  }

  public Policy(
    String number,
    String typeCode)
  {
    this();
    this.number = number;
    this.typeCode = typeCode;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Policy(Policy other) {
    if (other.isSetSerial()) {
      this.serial = other.serial;
    }
    if (other.isSetNumber()) {
      this.number = other.number;
    }
    if (other.isSetTypeCode()) {
      this.typeCode = other.typeCode;
    }
    if (other.isSetInsurerInfisCode()) {
      this.insurerInfisCode = other.insurerInfisCode;
    }
  }

  public Policy deepCopy() {
    return new Policy(this);
  }

  @Override
  public void clear() {
    this.serial = null;
    this.number = null;
    this.typeCode = null;
    this.insurerInfisCode = null;
  }

  public String getSerial() {
    return this.serial;
  }

  public Policy setSerial(String serial) {
    this.serial = serial;
    return this;
  }

  public void unsetSerial() {
    this.serial = null;
  }

  /** Returns true if field serial is set (has been assigned a value) and false otherwise */
  public boolean isSetSerial() {
    return this.serial != null;
  }

  public void setSerialIsSet(boolean value) {
    if (!value) {
      this.serial = null;
    }
  }

  public String getNumber() {
    return this.number;
  }

  public Policy setNumber(String number) {
    this.number = number;
    return this;
  }

  public void unsetNumber() {
    this.number = null;
  }

  /** Returns true if field number is set (has been assigned a value) and false otherwise */
  public boolean isSetNumber() {
    return this.number != null;
  }

  public void setNumberIsSet(boolean value) {
    if (!value) {
      this.number = null;
    }
  }

  public String getTypeCode() {
    return this.typeCode;
  }

  public Policy setTypeCode(String typeCode) {
    this.typeCode = typeCode;
    return this;
  }

  public void unsetTypeCode() {
    this.typeCode = null;
  }

  /** Returns true if field typeCode is set (has been assigned a value) and false otherwise */
  public boolean isSetTypeCode() {
    return this.typeCode != null;
  }

  public void setTypeCodeIsSet(boolean value) {
    if (!value) {
      this.typeCode = null;
    }
  }

  public String getInsurerInfisCode() {
    return this.insurerInfisCode;
  }

  public Policy setInsurerInfisCode(String insurerInfisCode) {
    this.insurerInfisCode = insurerInfisCode;
    return this;
  }

  public void unsetInsurerInfisCode() {
    this.insurerInfisCode = null;
  }

  /** Returns true if field insurerInfisCode is set (has been assigned a value) and false otherwise */
  public boolean isSetInsurerInfisCode() {
    return this.insurerInfisCode != null;
  }

  public void setInsurerInfisCodeIsSet(boolean value) {
    if (!value) {
      this.insurerInfisCode = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SERIAL:
      if (value == null) {
        unsetSerial();
      } else {
        setSerial((String)value);
      }
      break;

    case NUMBER:
      if (value == null) {
        unsetNumber();
      } else {
        setNumber((String)value);
      }
      break;

    case TYPE_CODE:
      if (value == null) {
        unsetTypeCode();
      } else {
        setTypeCode((String)value);
      }
      break;

    case INSURER_INFIS_CODE:
      if (value == null) {
        unsetInsurerInfisCode();
      } else {
        setInsurerInfisCode((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SERIAL:
      return getSerial();

    case NUMBER:
      return getNumber();

    case TYPE_CODE:
      return getTypeCode();

    case INSURER_INFIS_CODE:
      return getInsurerInfisCode();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SERIAL:
      return isSetSerial();
    case NUMBER:
      return isSetNumber();
    case TYPE_CODE:
      return isSetTypeCode();
    case INSURER_INFIS_CODE:
      return isSetInsurerInfisCode();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Policy)
      return this.equals((Policy)that);
    return false;
  }

  public boolean equals(Policy that) {
    if (that == null)
      return false;

    boolean this_present_serial = true && this.isSetSerial();
    boolean that_present_serial = true && that.isSetSerial();
    if (this_present_serial || that_present_serial) {
      if (!(this_present_serial && that_present_serial))
        return false;
      if (!this.serial.equals(that.serial))
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

    boolean this_present_typeCode = true && this.isSetTypeCode();
    boolean that_present_typeCode = true && that.isSetTypeCode();
    if (this_present_typeCode || that_present_typeCode) {
      if (!(this_present_typeCode && that_present_typeCode))
        return false;
      if (!this.typeCode.equals(that.typeCode))
        return false;
    }

    boolean this_present_insurerInfisCode = true && this.isSetInsurerInfisCode();
    boolean that_present_insurerInfisCode = true && that.isSetInsurerInfisCode();
    if (this_present_insurerInfisCode || that_present_insurerInfisCode) {
      if (!(this_present_insurerInfisCode && that_present_insurerInfisCode))
        return false;
      if (!this.insurerInfisCode.equals(that.insurerInfisCode))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Policy other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Policy typedOther = (Policy)other;

    lastComparison = Boolean.valueOf(isSetSerial()).compareTo(typedOther.isSetSerial());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSerial()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.serial, typedOther.serial);
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
    lastComparison = Boolean.valueOf(isSetTypeCode()).compareTo(typedOther.isSetTypeCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTypeCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.typeCode, typedOther.typeCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetInsurerInfisCode()).compareTo(typedOther.isSetInsurerInfisCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetInsurerInfisCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.insurerInfisCode, typedOther.insurerInfisCode);
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
    StringBuilder sb = new StringBuilder("Policy(");
    boolean first = true;

    if (isSetSerial()) {
      sb.append("serial:");
      if (this.serial == null) {
        sb.append("null");
      } else {
        sb.append(this.serial);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("number:");
    if (this.number == null) {
      sb.append("null");
    } else {
      sb.append(this.number);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("typeCode:");
    if (this.typeCode == null) {
      sb.append("null");
    } else {
      sb.append(this.typeCode);
    }
    first = false;
    if (isSetInsurerInfisCode()) {
      if (!first) sb.append(", ");
      sb.append("insurerInfisCode:");
      if (this.insurerInfisCode == null) {
        sb.append("null");
      } else {
        sb.append(this.insurerInfisCode);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (number == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'number' was not present! Struct: " + toString());
    }
    if (typeCode == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'typeCode' was not present! Struct: " + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PolicyStandardSchemeFactory implements SchemeFactory {
    public PolicyStandardScheme getScheme() {
      return new PolicyStandardScheme();
    }
  }

  private static class PolicyStandardScheme extends StandardScheme<Policy> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Policy struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SERIAL
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.serial = iprot.readString();
              struct.setSerialIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // NUMBER
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.number = iprot.readString();
              struct.setNumberIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TYPE_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.typeCode = iprot.readString();
              struct.setTypeCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // INSURER_INFIS_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.insurerInfisCode = iprot.readString();
              struct.setInsurerInfisCodeIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Policy struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.serial != null) {
        if (struct.isSetSerial()) {
          oprot.writeFieldBegin(SERIAL_FIELD_DESC);
          oprot.writeString(struct.serial);
          oprot.writeFieldEnd();
        }
      }
      if (struct.number != null) {
        oprot.writeFieldBegin(NUMBER_FIELD_DESC);
        oprot.writeString(struct.number);
        oprot.writeFieldEnd();
      }
      if (struct.typeCode != null) {
        oprot.writeFieldBegin(TYPE_CODE_FIELD_DESC);
        oprot.writeString(struct.typeCode);
        oprot.writeFieldEnd();
      }
      if (struct.insurerInfisCode != null) {
        if (struct.isSetInsurerInfisCode()) {
          oprot.writeFieldBegin(INSURER_INFIS_CODE_FIELD_DESC);
          oprot.writeString(struct.insurerInfisCode);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PolicyTupleSchemeFactory implements SchemeFactory {
    public PolicyTupleScheme getScheme() {
      return new PolicyTupleScheme();
    }
  }

  private static class PolicyTupleScheme extends TupleScheme<Policy> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Policy struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.number);
      oprot.writeString(struct.typeCode);
      BitSet optionals = new BitSet();
      if (struct.isSetSerial()) {
        optionals.set(0);
      }
      if (struct.isSetInsurerInfisCode()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSerial()) {
        oprot.writeString(struct.serial);
      }
      if (struct.isSetInsurerInfisCode()) {
        oprot.writeString(struct.insurerInfisCode);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Policy struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.number = iprot.readString();
      struct.setNumberIsSet(true);
      struct.typeCode = iprot.readString();
      struct.setTypeCodeIsSet(true);
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.serial = iprot.readString();
        struct.setSerialIsSet(true);
      }
      if (incoming.get(1)) {
        struct.insurerInfisCode = iprot.readString();
        struct.setInsurerInfisCodeIsSet(true);
      }
    }
  }

}
