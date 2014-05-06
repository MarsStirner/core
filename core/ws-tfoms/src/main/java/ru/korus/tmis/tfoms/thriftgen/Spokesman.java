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

/**
 * Представитель пациента
 * @param patientId         1)Идентификатор пациента
 * @param FAM_P             2)Фамилия
 * @param IM_P              3)Имя
 * @param OT_P              4)Отчество
 * @param DR_P              5)Дата рождения
 * @param W_P               6)Пол
 */
public class Spokesman implements org.apache.thrift.TBase<Spokesman, Spokesman._Fields>, java.io.Serializable, Cloneable {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Spokesman");

  private static final org.apache.thrift.protocol.TField PATIENT_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("patientId", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField FAM__P_FIELD_DESC = new org.apache.thrift.protocol.TField("FAM_P", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField IM__P_FIELD_DESC = new org.apache.thrift.protocol.TField("IM_P", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField OT__P_FIELD_DESC = new org.apache.thrift.protocol.TField("OT_P", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField DR__P_FIELD_DESC = new org.apache.thrift.protocol.TField("DR_P", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField W__P_FIELD_DESC = new org.apache.thrift.protocol.TField("W_P", org.apache.thrift.protocol.TType.I16, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new SpokesmanStandardSchemeFactory());
    schemes.put(TupleScheme.class, new SpokesmanTupleSchemeFactory());
  }

  public int patientId; // optional
  public String FAM_P; // optional
  public String IM_P; // optional
  public String OT_P; // optional
  public long DR_P; // optional
  public short W_P; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PATIENT_ID((short)1, "patientId"),
    FAM__P((short)2, "FAM_P"),
    IM__P((short)3, "IM_P"),
    OT__P((short)4, "OT_P"),
    DR__P((short)5, "DR_P"),
    W__P((short)6, "W_P");

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
        case 1: // PATIENT_ID
          return PATIENT_ID;
        case 2: // FAM__P
          return FAM__P;
        case 3: // IM__P
          return IM__P;
        case 4: // OT__P
          return OT__P;
        case 5: // DR__P
          return DR__P;
        case 6: // W__P
          return W__P;
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
  private static final int __PATIENTID_ISSET_ID = 0;
  private static final int __DR_P_ISSET_ID = 1;
  private static final int __W_P_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private _Fields optionals[] = {_Fields.PATIENT_ID,_Fields.FAM__P,_Fields.IM__P,_Fields.OT__P,_Fields.DR__P,_Fields.W__P};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PATIENT_ID, new org.apache.thrift.meta_data.FieldMetaData("patientId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32        , "int")));
    tmpMap.put(_Fields.FAM__P, new org.apache.thrift.meta_data.FieldMetaData("FAM_P", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.IM__P, new org.apache.thrift.meta_data.FieldMetaData("IM_P", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.OT__P, new org.apache.thrift.meta_data.FieldMetaData("OT_P", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DR__P, new org.apache.thrift.meta_data.FieldMetaData("DR_P", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "timestamp")));
    tmpMap.put(_Fields.W__P, new org.apache.thrift.meta_data.FieldMetaData("W_P", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I16        , "tinyint")));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Spokesman.class, metaDataMap);
  }

  public Spokesman() {
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Spokesman(Spokesman other) {
    __isset_bitfield = other.__isset_bitfield;
    this.patientId = other.patientId;
    if (other.isSetFAM_P()) {
      this.FAM_P = other.FAM_P;
    }
    if (other.isSetIM_P()) {
      this.IM_P = other.IM_P;
    }
    if (other.isSetOT_P()) {
      this.OT_P = other.OT_P;
    }
    this.DR_P = other.DR_P;
    this.W_P = other.W_P;
  }

  public Spokesman deepCopy() {
    return new Spokesman(this);
  }

  @Override
  public void clear() {
    setPatientIdIsSet(false);
    this.patientId = 0;
    this.FAM_P = null;
    this.IM_P = null;
    this.OT_P = null;
    setDR_PIsSet(false);
    this.DR_P = 0;
    setW_PIsSet(false);
    this.W_P = 0;
  }

  public int getPatientId() {
    return this.patientId;
  }

  public Spokesman setPatientId(int patientId) {
    this.patientId = patientId;
    setPatientIdIsSet(true);
    return this;
  }

  public void unsetPatientId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __PATIENTID_ISSET_ID);
  }

  /** Returns true if field patientId is set (has been assigned a value) and false otherwise */
  public boolean isSetPatientId() {
    return EncodingUtils.testBit(__isset_bitfield, __PATIENTID_ISSET_ID);
  }

  public void setPatientIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __PATIENTID_ISSET_ID, value);
  }

  public String getFAM_P() {
    return this.FAM_P;
  }

  public Spokesman setFAM_P(String FAM_P) {
    this.FAM_P = FAM_P;
    return this;
  }

  public void unsetFAM_P() {
    this.FAM_P = null;
  }

  /** Returns true if field FAM_P is set (has been assigned a value) and false otherwise */
  public boolean isSetFAM_P() {
    return this.FAM_P != null;
  }

  public void setFAM_PIsSet(boolean value) {
    if (!value) {
      this.FAM_P = null;
    }
  }

  public String getIM_P() {
    return this.IM_P;
  }

  public Spokesman setIM_P(String IM_P) {
    this.IM_P = IM_P;
    return this;
  }

  public void unsetIM_P() {
    this.IM_P = null;
  }

  /** Returns true if field IM_P is set (has been assigned a value) and false otherwise */
  public boolean isSetIM_P() {
    return this.IM_P != null;
  }

  public void setIM_PIsSet(boolean value) {
    if (!value) {
      this.IM_P = null;
    }
  }

  public String getOT_P() {
    return this.OT_P;
  }

  public Spokesman setOT_P(String OT_P) {
    this.OT_P = OT_P;
    return this;
  }

  public void unsetOT_P() {
    this.OT_P = null;
  }

  /** Returns true if field OT_P is set (has been assigned a value) and false otherwise */
  public boolean isSetOT_P() {
    return this.OT_P != null;
  }

  public void setOT_PIsSet(boolean value) {
    if (!value) {
      this.OT_P = null;
    }
  }

  public long getDR_P() {
    return this.DR_P;
  }

  public Spokesman setDR_P(long DR_P) {
    this.DR_P = DR_P;
    setDR_PIsSet(true);
    return this;
  }

  public void unsetDR_P() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DR_P_ISSET_ID);
  }

  /** Returns true if field DR_P is set (has been assigned a value) and false otherwise */
  public boolean isSetDR_P() {
    return EncodingUtils.testBit(__isset_bitfield, __DR_P_ISSET_ID);
  }

  public void setDR_PIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DR_P_ISSET_ID, value);
  }

  public short getW_P() {
    return this.W_P;
  }

  public Spokesman setW_P(short W_P) {
    this.W_P = W_P;
    setW_PIsSet(true);
    return this;
  }

  public void unsetW_P() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __W_P_ISSET_ID);
  }

  /** Returns true if field W_P is set (has been assigned a value) and false otherwise */
  public boolean isSetW_P() {
    return EncodingUtils.testBit(__isset_bitfield, __W_P_ISSET_ID);
  }

  public void setW_PIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __W_P_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PATIENT_ID:
      if (value == null) {
        unsetPatientId();
      } else {
        setPatientId((Integer)value);
      }
      break;

    case FAM__P:
      if (value == null) {
        unsetFAM_P();
      } else {
        setFAM_P((String)value);
      }
      break;

    case IM__P:
      if (value == null) {
        unsetIM_P();
      } else {
        setIM_P((String)value);
      }
      break;

    case OT__P:
      if (value == null) {
        unsetOT_P();
      } else {
        setOT_P((String)value);
      }
      break;

    case DR__P:
      if (value == null) {
        unsetDR_P();
      } else {
        setDR_P((Long)value);
      }
      break;

    case W__P:
      if (value == null) {
        unsetW_P();
      } else {
        setW_P((Short)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PATIENT_ID:
      return Integer.valueOf(getPatientId());

    case FAM__P:
      return getFAM_P();

    case IM__P:
      return getIM_P();

    case OT__P:
      return getOT_P();

    case DR__P:
      return Long.valueOf(getDR_P());

    case W__P:
      return Short.valueOf(getW_P());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PATIENT_ID:
      return isSetPatientId();
    case FAM__P:
      return isSetFAM_P();
    case IM__P:
      return isSetIM_P();
    case OT__P:
      return isSetOT_P();
    case DR__P:
      return isSetDR_P();
    case W__P:
      return isSetW_P();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Spokesman)
      return this.equals((Spokesman)that);
    return false;
  }

  public boolean equals(Spokesman that) {
    if (that == null)
      return false;

    boolean this_present_patientId = true && this.isSetPatientId();
    boolean that_present_patientId = true && that.isSetPatientId();
    if (this_present_patientId || that_present_patientId) {
      if (!(this_present_patientId && that_present_patientId))
        return false;
      if (this.patientId != that.patientId)
        return false;
    }

    boolean this_present_FAM_P = true && this.isSetFAM_P();
    boolean that_present_FAM_P = true && that.isSetFAM_P();
    if (this_present_FAM_P || that_present_FAM_P) {
      if (!(this_present_FAM_P && that_present_FAM_P))
        return false;
      if (!this.FAM_P.equals(that.FAM_P))
        return false;
    }

    boolean this_present_IM_P = true && this.isSetIM_P();
    boolean that_present_IM_P = true && that.isSetIM_P();
    if (this_present_IM_P || that_present_IM_P) {
      if (!(this_present_IM_P && that_present_IM_P))
        return false;
      if (!this.IM_P.equals(that.IM_P))
        return false;
    }

    boolean this_present_OT_P = true && this.isSetOT_P();
    boolean that_present_OT_P = true && that.isSetOT_P();
    if (this_present_OT_P || that_present_OT_P) {
      if (!(this_present_OT_P && that_present_OT_P))
        return false;
      if (!this.OT_P.equals(that.OT_P))
        return false;
    }

    boolean this_present_DR_P = true && this.isSetDR_P();
    boolean that_present_DR_P = true && that.isSetDR_P();
    if (this_present_DR_P || that_present_DR_P) {
      if (!(this_present_DR_P && that_present_DR_P))
        return false;
      if (this.DR_P != that.DR_P)
        return false;
    }

    boolean this_present_W_P = true && this.isSetW_P();
    boolean that_present_W_P = true && that.isSetW_P();
    if (this_present_W_P || that_present_W_P) {
      if (!(this_present_W_P && that_present_W_P))
        return false;
      if (this.W_P != that.W_P)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  public int compareTo(Spokesman other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;
    Spokesman typedOther = (Spokesman)other;

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
    lastComparison = Boolean.valueOf(isSetFAM_P()).compareTo(typedOther.isSetFAM_P());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFAM_P()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.FAM_P, typedOther.FAM_P);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetIM_P()).compareTo(typedOther.isSetIM_P());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetIM_P()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.IM_P, typedOther.IM_P);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetOT_P()).compareTo(typedOther.isSetOT_P());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetOT_P()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.OT_P, typedOther.OT_P);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDR_P()).compareTo(typedOther.isSetDR_P());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDR_P()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.DR_P, typedOther.DR_P);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetW_P()).compareTo(typedOther.isSetW_P());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetW_P()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.W_P, typedOther.W_P);
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
    StringBuilder sb = new StringBuilder("Spokesman(");
    boolean first = true;

    if (isSetPatientId()) {
      sb.append("patientId:");
      sb.append(this.patientId);
      first = false;
    }
    if (isSetFAM_P()) {
      if (!first) sb.append(", ");
      sb.append("FAM_P:");
      if (this.FAM_P == null) {
        sb.append("null");
      } else {
        sb.append(this.FAM_P);
      }
      first = false;
    }
    if (isSetIM_P()) {
      if (!first) sb.append(", ");
      sb.append("IM_P:");
      if (this.IM_P == null) {
        sb.append("null");
      } else {
        sb.append(this.IM_P);
      }
      first = false;
    }
    if (isSetOT_P()) {
      if (!first) sb.append(", ");
      sb.append("OT_P:");
      if (this.OT_P == null) {
        sb.append("null");
      } else {
        sb.append(this.OT_P);
      }
      first = false;
    }
    if (isSetDR_P()) {
      if (!first) sb.append(", ");
      sb.append("DR_P:");
      sb.append(this.DR_P);
      first = false;
    }
    if (isSetW_P()) {
      if (!first) sb.append(", ");
      sb.append("W_P:");
      sb.append(this.W_P);
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

  private static class SpokesmanStandardSchemeFactory implements SchemeFactory {
    public SpokesmanStandardScheme getScheme() {
      return new SpokesmanStandardScheme();
    }
  }

  private static class SpokesmanStandardScheme extends StandardScheme<Spokesman> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Spokesman struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PATIENT_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.patientId = iprot.readI32();
              struct.setPatientIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // FAM__P
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.FAM_P = iprot.readString();
              struct.setFAM_PIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // IM__P
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.IM_P = iprot.readString();
              struct.setIM_PIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // OT__P
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.OT_P = iprot.readString();
              struct.setOT_PIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // DR__P
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.DR_P = iprot.readI64();
              struct.setDR_PIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // W__P
            if (schemeField.type == org.apache.thrift.protocol.TType.I16) {
              struct.W_P = iprot.readI16();
              struct.setW_PIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, Spokesman struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.isSetPatientId()) {
        oprot.writeFieldBegin(PATIENT_ID_FIELD_DESC);
        oprot.writeI32(struct.patientId);
        oprot.writeFieldEnd();
      }
      if (struct.FAM_P != null) {
        if (struct.isSetFAM_P()) {
          oprot.writeFieldBegin(FAM__P_FIELD_DESC);
          oprot.writeString(struct.FAM_P);
          oprot.writeFieldEnd();
        }
      }
      if (struct.IM_P != null) {
        if (struct.isSetIM_P()) {
          oprot.writeFieldBegin(IM__P_FIELD_DESC);
          oprot.writeString(struct.IM_P);
          oprot.writeFieldEnd();
        }
      }
      if (struct.OT_P != null) {
        if (struct.isSetOT_P()) {
          oprot.writeFieldBegin(OT__P_FIELD_DESC);
          oprot.writeString(struct.OT_P);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetDR_P()) {
        oprot.writeFieldBegin(DR__P_FIELD_DESC);
        oprot.writeI64(struct.DR_P);
        oprot.writeFieldEnd();
      }
      if (struct.isSetW_P()) {
        oprot.writeFieldBegin(W__P_FIELD_DESC);
        oprot.writeI16(struct.W_P);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SpokesmanTupleSchemeFactory implements SchemeFactory {
    public SpokesmanTupleScheme getScheme() {
      return new SpokesmanTupleScheme();
    }
  }

  private static class SpokesmanTupleScheme extends TupleScheme<Spokesman> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Spokesman struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      BitSet optionals = new BitSet();
      if (struct.isSetPatientId()) {
        optionals.set(0);
      }
      if (struct.isSetFAM_P()) {
        optionals.set(1);
      }
      if (struct.isSetIM_P()) {
        optionals.set(2);
      }
      if (struct.isSetOT_P()) {
        optionals.set(3);
      }
      if (struct.isSetDR_P()) {
        optionals.set(4);
      }
      if (struct.isSetW_P()) {
        optionals.set(5);
      }
      oprot.writeBitSet(optionals, 6);
      if (struct.isSetPatientId()) {
        oprot.writeI32(struct.patientId);
      }
      if (struct.isSetFAM_P()) {
        oprot.writeString(struct.FAM_P);
      }
      if (struct.isSetIM_P()) {
        oprot.writeString(struct.IM_P);
      }
      if (struct.isSetOT_P()) {
        oprot.writeString(struct.OT_P);
      }
      if (struct.isSetDR_P()) {
        oprot.writeI64(struct.DR_P);
      }
      if (struct.isSetW_P()) {
        oprot.writeI16(struct.W_P);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Spokesman struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      BitSet incoming = iprot.readBitSet(6);
      if (incoming.get(0)) {
        struct.patientId = iprot.readI32();
        struct.setPatientIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.FAM_P = iprot.readString();
        struct.setFAM_PIsSet(true);
      }
      if (incoming.get(2)) {
        struct.IM_P = iprot.readString();
        struct.setIM_PIsSet(true);
      }
      if (incoming.get(3)) {
        struct.OT_P = iprot.readString();
        struct.setOT_PIsSet(true);
      }
      if (incoming.get(4)) {
        struct.DR_P = iprot.readI64();
        struct.setDR_PIsSet(true);
      }
      if (incoming.get(5)) {
        struct.W_P = iprot.readI16();
        struct.setW_PIsSet(true);
      }
    }
  }

}
