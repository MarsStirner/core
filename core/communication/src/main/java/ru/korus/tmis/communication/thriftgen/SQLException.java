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

public class SQLException extends TException implements org.apache.thrift.TBase<SQLException, SQLException._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SQLException");

  private static final org.apache.thrift.protocol.TField ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("error_code", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ERROR_MSG_FIELD_DESC = new org.apache.thrift.protocol.TField("error_msg", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SQLExceptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SQLExceptionTupleSchemeFactory());
  }

  public int error_code; // required
  public String error_msg; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ERROR_CODE((short)1, "error_code"),
    ERROR_MSG((short)2, "error_msg");

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
        case 1: // ERROR_CODE
          return ERROR_CODE;
        case 2: // ERROR_MSG
          return ERROR_MSG;
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
  private static final int __ERROR_CODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("error_code", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ERROR_MSG, new org.apache.thrift.meta_data.FieldMetaData("error_msg", org.apache.thrift.TFieldRequirementType.DEFAULT, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SQLException.class, metaDataMap);
  }

  public SQLException() {
  }

  public SQLException(
    int error_code,
    String error_msg)
  {
    this();
    this.error_code = error_code;
    setError_codeIsSet(true);
    this.error_msg = error_msg;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SQLException(SQLException other) {
    __isset_bitfield = other.__isset_bitfield;
    this.error_code = other.error_code;
    if (other.isSetError_msg()) {
      this.error_msg = other.error_msg;
    }
  }

  public SQLException deepCopy() {
    return new SQLException(this);
  }

  @Override
  public void clear() {
    setError_codeIsSet(false);
    this.error_code = 0;
    this.error_msg = null;
  }

  public int getError_code() {
    return this.error_code;
  }

  public SQLException setError_code(int error_code) {
    this.error_code = error_code;
    setError_codeIsSet(true);
    return this;
  }

  public void unsetError_code() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ERROR_CODE_ISSET_ID);
  }

  /** Returns true if field error_code is set (has been assigned a value) and false otherwise */
  public boolean isSetError_code() {
    return EncodingUtils.testBit(__isset_bitfield, __ERROR_CODE_ISSET_ID);
  }

  public void setError_codeIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ERROR_CODE_ISSET_ID, value);
  }

  public String getError_msg() {
    return this.error_msg;
  }

  public SQLException setError_msg(String error_msg) {
    this.error_msg = error_msg;
    return this;
  }

  public void unsetError_msg() {
    this.error_msg = null;
  }

  /** Returns true if field error_msg is set (has been assigned a value) and false otherwise */
  public boolean isSetError_msg() {
    return this.error_msg != null;
  }

  public void setError_msgIsSet(boolean value) {
    if (!value) {
      this.error_msg = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ERROR_CODE:
      if (value == null) {
        unsetError_code();
      } else {
        setError_code((Integer)value);
      }
      break;

    case ERROR_MSG:
      if (value == null) {
        unsetError_msg();
      } else {
        setError_msg((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR_CODE:
      return Integer.valueOf(getError_code());

    case ERROR_MSG:
      return getError_msg();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ERROR_CODE:
      return isSetError_code();
    case ERROR_MSG:
      return isSetError_msg();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof SQLException)
      return this.equals((SQLException)that);
    return false;
  }

  public boolean equals(SQLException that) {
    if (that == null)
      return false;

    boolean this_present_error_code = true;
    boolean that_present_error_code = true;
    if (this_present_error_code || that_present_error_code) {
      if (!(this_present_error_code && that_present_error_code))
        return false;
      if (this.error_code != that.error_code)
        return false;
    }

    boolean this_present_error_msg = true && this.isSetError_msg();
    boolean that_present_error_msg = true && that.isSetError_msg();
    if (this_present_error_msg || that_present_error_msg) {
      if (!(this_present_error_msg && that_present_error_msg))
        return false;
      if (!this.error_msg.equals(that.error_msg))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(SQLException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    SQLException typedOther = (SQLException)other;

    lastComparison = Boolean.valueOf(isSetError_code()).compareTo(typedOther.isSetError_code());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetError_code()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.error_code, typedOther.error_code);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetError_msg()).compareTo(typedOther.isSetError_msg());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetError_msg()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.error_msg, typedOther.error_msg);
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
    StringBuilder sb = new StringBuilder("SQLException(");
    boolean first = true;

    sb.append("error_code:");
    sb.append(this.error_code);
    first = false;
    if (!first) sb.append(", ");
    sb.append("error_msg:");
    if (this.error_msg == null) {
      sb.append("null");
    } else {
      sb.append(this.error_msg);
    }
    first = false;
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

  private static class SQLExceptionStandardSchemeFactory implements SchemeFactory {
    public SQLExceptionStandardScheme getScheme() {
      return new SQLExceptionStandardScheme();
    }
  }

  private static class SQLExceptionStandardScheme extends StandardScheme<SQLException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SQLException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.error_code = iprot.readI32();
              struct.setError_codeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ERROR_MSG
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.error_msg = iprot.readString();
              struct.setError_msgIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, SQLException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ERROR_CODE_FIELD_DESC);
      oprot.writeI32(struct.error_code);
      oprot.writeFieldEnd();
      if (struct.error_msg != null) {
        oprot.writeFieldBegin(ERROR_MSG_FIELD_DESC);
        oprot.writeString(struct.error_msg);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SQLExceptionTupleSchemeFactory implements SchemeFactory {
    public SQLExceptionTupleScheme getScheme() {
      return new SQLExceptionTupleScheme();
    }
  }

  private static class SQLExceptionTupleScheme extends TupleScheme<SQLException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SQLException struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetError_code()) {
        optionals.set(0);
      }
      if (struct.isSetError_msg()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetError_code()) {
        oprot.writeI32(struct.error_code);
      }
      if (struct.isSetError_msg()) {
        oprot.writeString(struct.error_msg);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SQLException struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.error_code = iprot.readI32();
        struct.setError_codeIsSet(true);
      }
      if (incoming.get(1)) {
        struct.error_msg = iprot.readString();
        struct.setError_msgIsSet(true);
      }
    }
  }

}
